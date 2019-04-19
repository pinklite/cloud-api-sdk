package global.realid.cloud.sdk.client.requester;

import global.realid.cloud.sdk.enums.ERequestHeaders;
import global.realid.cloud.sdk.exception.InvalidResponseStatusException;
import global.realid.cloud.sdk.exception.RealIdCloudApiException;
import global.realid.cloud.sdk.provider.http.HttpProvider;
import global.realid.cloud.sdk.provider.http.HttpProviderResult;
import global.realid.cloud.sdk.provider.json.JsonProvider;
import global.realid.cloud.sdk.util.MessageAuthenticateAuxUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * abstract implementation of {@link RealIdCloudApiRequester}
 * @author Benjamin
 */

public abstract class AbstractApiRequester implements RealIdCloudApiRequester {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private HttpProvider httpProvider;
	private JsonProvider jsonProvider;
	
	private boolean validateResponseAuthenticateString = true;

	/**
	 * construct an instance by specifying {@link HttpProvider}, {@link JsonProvider}, and whether validate response message authentication string
	 * @param httpProvider the HttpProvider
	 * @param jsonProvider the JsonProvider
	 * @param validateResponse whether validate response message authentication string
	 */
	AbstractApiRequester(HttpProvider httpProvider, JsonProvider jsonProvider, boolean validateResponse) {
		super();
		this.httpProvider = Objects.requireNonNull(httpProvider);
		this.jsonProvider = Objects.requireNonNull(jsonProvider);
		this.validateResponseAuthenticateString = validateResponse;
	}

	/**
	 * get whether validate response message authentication string
	 * @return whether validate response message authentication string
	 */
	public final boolean isValidateResponseAuthenticateString() {
		return validateResponseAuthenticateString;
	}

	/**
	 * set whether validate response message authentication string
	 * @param validateResponseAuthenticateString <code>true</code> if validation is required
	 */
	public final void setValidateResponseAuthenticateString(boolean validateResponseAuthenticateString) {
		this.validateResponseAuthenticateString = validateResponseAuthenticateString;
	}

	public final <R, T> T executeJsonRequest(String url, R reqData, Type respType) throws SocketException, IOException {
		String jsonString = null;
		if (reqData instanceof String) {
			jsonString = (String) reqData;
		} else {
			jsonString = jsonProvider.serialize(reqData);
		}

		// set up headers
		Map<String, String> header = new HashMap<>();
		String strCurrTimeMills = Long.toString(System.currentTimeMillis());
		String nonce = UUID.randomUUID().toString();
		header.put(ERequestHeaders.Timestamp.name(), strCurrTimeMills);
		header.put(ERequestHeaders.KeyInfo.name(), getKeyInfo());
		header.put(ERequestHeaders.Nonce.name(), nonce);
		String dataAuth;
		try {
			dataAuth = calculateJsonRequestMessageAuthenticationString(jsonString, nonce, strCurrTimeMills);
		} catch (Exception e) {
			throw new RealIdCloudApiException("fail to calculate message authentication string when constructing request...", e);
		}
		header.put(ERequestHeaders.DataAuth.name(), dataAuth);
		// add other necessary headers for subclass
		completeHeaderSetting(header);
		
		logger.debug("executeJsonRequest -> header:{}, body:{}", header, jsonString);
		
		// send request and get response
		HttpProviderResult<String> result = httpProvider.sendJsonPostForStringResponse(url, header, jsonString, StandardCharsets.UTF_8);
		
		return processJsonResponse(result, respType);
	}
	
	/**
	 * check response elements(status code and headers), and convert response result to instance of {@link Type} specified by parameter, if response is valid
	 * @param httpResult response from provider
	 * @param respType result type
	 * @return result object
	 * @throws InvalidResponseStatusException in case of not getting valid response status code 
	 */
	private <T> T processJsonResponse(HttpProviderResult<String> httpResult, Type respType) {
		if (httpResult.getStatusCode() != 200) {
			throw new InvalidResponseStatusException(httpResult, "didn't get valid response, please check request parameters and headers");
		}
		
		String respJson = httpResult.getResponseContent();
		Map<String, String> respHeaders = httpResult.getHeaders();
		logger.debug("response headers: {}", respHeaders);
		
		if (validateResponseAuthenticateString) {
			String respNonse = respHeaders.get(ERequestHeaders.Nonce.name());
			String respTimestamp = respHeaders.get(ERequestHeaders.Timestamp.name());
			// note that keys(client-server) for response DataAuth calculation are intrinsic related 
			String respDataAuth = respHeaders.get(ERequestHeaders.DataAuth.name());
			
			boolean passed;
			try {
				passed = validateResponseMessageAuthenticationString(respDataAuth, respJson, respNonse, respTimestamp);
			} catch (Exception e) {
				throw new RealIdCloudApiException("fail to verify response...", e);
			}
			
			if (!passed) {
				throw new RealIdCloudApiException("authentication string calculated from response does not match that declared in headers, response data is broken!");
			}
		}
		
		logger.debug("response body: {}", respJson);
		
		return jsonProvider.deserialize(respJson, respType);
	}

	public final <T> T executeMultipartFormRequest(String url, Map<String, File> fileFields, Map<String, String> textFields, Type type) throws SocketException, IOException {
		// set up headers
		Map<String, String> header = new HashMap<>();
		String strCurrTimeMills = Long.toString(System.currentTimeMillis());
		String nonce = UUID.randomUUID().toString();
		header.put(ERequestHeaders.Timestamp.name(), strCurrTimeMills);
		header.put(ERequestHeaders.KeyInfo.name(), getKeyInfo());
		header.put(ERequestHeaders.Nonce.name(), nonce);
		String dataAuth;
		try {
			dataAuth = calculateFormRequestMessageAuthenticationString(fileFields, textFields, nonce, strCurrTimeMills);
		} catch (Exception e) {
			throw new RealIdCloudApiException("fail to calculate message authentication string when constructing request...", e);
		}
		header.put(ERequestHeaders.DataAuth.name(), dataAuth);
		// add other necessary headers for subclass
		completeHeaderSetting(header);
		
		logger.debug("executeMultipartFormRequest -> header:{}", header);
		
		// send request and get response
		HttpProviderResult<String> httpResult = httpProvider.sendMultipartFormPostForStringResponse(url, header, fileFields, textFields, StandardCharsets.UTF_8);
		
		return processJsonResponse(httpResult, type);
	}
	
	/**
	 * convenient method for multipart form request, which is used to generate the string for calculating message authentication string  
	 * @param fileFields file fields in form
	 * @param textFields text fields in form
	 * @return the string from form for calculating message authentication string 
	 */
	protected final String getFormDataStringForMessageAuthentication(Map<String, File> fileFields, Map<String, String> textFields) {
		Map<String, String> formData = new TreeMap<String, String>();
		if (textFields != null) {
			formData.putAll(textFields);
		}
		
		if (fileFields != null) {
			fileFields.forEach((k, v) -> {
				try {
					formData.put(k, MessageAuthenticateAuxUtils.streamMD5Base64(new FileInputStream(v)));
				} catch (Exception e) {
					throw new RealIdCloudApiException(e);
				}
			});
		}
		
		String strFormData = MessageAuthenticateAuxUtils.concatenateFormData(formData);
		return strFormData;
	}
	
	@Override
	public void close() throws IOException {
		httpProvider.close();
	}

	/**
	 * calculate data authentication string for JSON request
	 * @param dataStr data as string
	 * @param nonce Nonce header value
	 * @param timestamp Timestamp header value
	 * @return data authentication string
	 * @throws Exception any exception during calculation
	 */
	public abstract String calculateJsonRequestMessageAuthenticationString(String dataStr, String nonce, String timestamp) throws Exception;
	
	/**
	 * calculate data authentication string for Multipart Form request
	 * @param fileFields file fields in form
	 * @param textFields text fields in form
	 * @param nonce Nonce header value
	 * @param timestamp Timestamp header value
	 * @return data authentication string
	 * @throws Exception any exception during calculation
	 */
	public abstract String calculateFormRequestMessageAuthenticationString(Map<String, File> fileFields, Map<String, String> textFields, String nonce, String timestamp) throws Exception;
	
	/**
	 * check if response data remains untouched, by comparing <code>global.realid.cloud.sdk.enums.ERequestHeaders.DataAuth</code> header value 
	 * and that calculated from response data and other headers
	 * @param responseMessageAuthenticationStr data authentication string declared in response header
	 * @param dataStr response data as string
	 * @param nonce Nonce header value
	 * @param timestamp Timestamp header value
	 * @return whether or not pass through the validation
	 * @throws Exception any exception during calculation
	 */
	public abstract boolean validateResponseMessageAuthenticationString(String responseMessageAuthenticationStr, String dataStr, String nonce, String timestamp) throws Exception;
	
	/**
	 * get <code>global.realid.cloud.sdk.enums.ERequestHeaders.KeyInfo</code> header, subclass should provide corresponding and valid value
	 * @return value for KeyInfo header
	 */
	public abstract String getKeyInfo();
	
	/**
	 * add extra headers if subclass needs
	 * @param header header collection
	 */
	public abstract void completeHeaderSetting(Map<String, String> header);
	
}
