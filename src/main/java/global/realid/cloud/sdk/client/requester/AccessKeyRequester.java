package global.realid.cloud.sdk.client.requester;

import global.realid.cloud.sdk.enums.EHmacAlgorithms;
import global.realid.cloud.sdk.enums.ERequestHeaders;
import global.realid.cloud.sdk.exception.RealIdCloudApiException;
import global.realid.cloud.sdk.provider.http.HttpProvider;
import global.realid.cloud.sdk.provider.json.JsonProvider;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * API requester based on Access Key
 * @author Benjamin
 */

public class AccessKeyRequester extends AbstractApiRequester {
	Logger logger = LoggerFactory.getLogger(getClass());

	private String keyInfoStr;
	private byte[] secretKey;
	private EHmacAlgorithms hmacAlgorithm = EHmacAlgorithms.HMAC_SHA_256;
	

	/**
	 * overrides super class constructor
	 * @param httpProvider the HttpProvider
	 * @param jsonProvider the JsonProvider
	 * @param validateResponse whether validate response message authentication string
	 */
	AccessKeyRequester(HttpProvider httpProvider, JsonProvider jsonProvider,
			boolean validateResponse) {
		super(httpProvider, jsonProvider, validateResponse);
	}

	/**
	 * get corresponding KeyInfo string value
	 * @return corresponding KeyInfo string value
	 */
	public String getKeyInfoStr() {
		return keyInfoStr;
	}

	/**
	 * set corresponding KeyInfo string value
	 * @param keyInfoStr corresponding KeyInfo string value
	 */
	public void setKeyInfoStr(String keyInfoStr) {
		this.keyInfoStr = keyInfoStr;
	}

	/**
	 * get secret key
	 * @return secret key
	 */
	public byte[] getSecretKey() {
		return secretKey;
	}

	/**
	 * set secret key
	 * @param secretKey secret key
	 */
	public void setSecretKey(byte[] secretKey) {
		this.secretKey = secretKey;
	}

	/**
	 * get HMAC algorithm used by the requester
	 * @return HMAC algorithm
	 */
	public EHmacAlgorithms getHmacAlgorithm() {
		return hmacAlgorithm;
	}

	/**
	 * set HMAC algorithm used by the requester
	 * @param hmacAlgorithm HMAC algorithm
	 */
	public void setHmacAlgorithm(EHmacAlgorithms hmacAlgorithm) {
		this.hmacAlgorithm = hmacAlgorithm;
	}
	
	/**
	 * calculate message authentication string using given HMAC Algorithm
	 * @param requestStr request data as string
	 * @param nonce Nonce header value
	 * @param timestamp Timestamp header value
	 * @param hmacAlgorithm HMAC algorithm
	 * @param key HMAC key
	 * @return message authentication string
	 * @throws InvalidKeyException if the given key is inappropriate for initializing the HMAC
	 */
	private String jsonMessageAuthenticationString(String requestStr, String nonce, String timestamp, EHmacAlgorithms hmacAlgorithm, byte[] key) throws InvalidKeyException {
		// concatenate required element to SS
		String format = "data=%s&nonce=%s&timestamp=%s";
		String ss = String.format(format, requestStr, nonce, timestamp);
		
		if (hmacAlgorithm == null) {
			hmacAlgorithm = EHmacAlgorithms.HMAC_SHA_256;
		}
		
		return calculateHmac(hmacAlgorithm.getName(), key, ss);
	}
	
	/**
	 * calculate HMAC string
	 * @param hmacAlgorithm HMAC algorithm
	 * @param key HMAC key
	 * @param originalStr string content to be calculated
	 * @return HMAC string
	 * @throws InvalidKeyException if the given key is inappropriate for initializing the HMAC
	 */
	private String calculateHmac(String hmacAlgorithm, byte[] key, String originalStr)
			throws InvalidKeyException {
		// get BA from SS
		byte[] ba = originalStr.getBytes(StandardCharsets.UTF_8);
		
		// digest using HmacSHA256
		Key sk = new SecretKeySpec(key, hmacAlgorithm);
        Mac mac;
		try {
			mac = Mac.getInstance(sk.getAlgorithm());
		} catch (NoSuchAlgorithmException e) {
			// nearly impossible
			throw new RealIdCloudApiException("unexpected NoSuchAlgorithmException when using EHmacAlgorithms enum, something's wrong!!!???", e);
		}
        mac.init(sk);
        byte[] digest = mac.doFinal(ba);
        
        // encode byte array result to Base64 string
       return Base64.getEncoder().encodeToString(digest);
	}
	
	/**
	 * calculate multipart form message authentication string using given HMAC Algorithm
	 * @param fileFields file fields in form
	 * @param textFields text fields in form
	 * @param nonce Nonce header value
	 * @param timestamp Timestamp header value
	 * @param hmacAlgorithm HMAC algorithm
	 * @param key HMAC key
	 * @return message authentication string
	 * @throws InvalidKeyException if the given key is inappropriate for initializing the HMAC
	 */
	private String formMessageAuthenticationString(Map<String, File> fileFields, Map<String, String> textFields, 
			String nonce, String timestamp, EHmacAlgorithms hmacAlgorithm, byte[] key) throws InvalidKeyException {
		String strFormData = getFormDataStringForMessageAuthentication(fileFields, textFields);
		return jsonMessageAuthenticationString(strFormData, nonce, timestamp, hmacAlgorithm, key);
	}

	@Override
	public String calculateJsonRequestMessageAuthenticationString(String dataStr, String nonce,
			String timestamp) throws Exception {
		return jsonMessageAuthenticationString(dataStr, nonce,
				timestamp, hmacAlgorithm, secretKey);
	}

	@Override
	public String calculateFormRequestMessageAuthenticationString(Map<String, File> fileFields,
			Map<String, String> textFields, String nonce, String timestamp) throws Exception {
		return formMessageAuthenticationString(fileFields,
				textFields, nonce, timestamp, hmacAlgorithm, secretKey);
	}

	@Override
	public boolean validateResponseMessageAuthenticationString(
			String responseMessageAuthenticationStr, String dataStr, String nonce, String timestamp)
			throws Exception {
		String calculatedRespMas = jsonMessageAuthenticationString(dataStr, nonce, timestamp, hmacAlgorithm, secretKey);
		return calculatedRespMas.equals(responseMessageAuthenticationStr);
	}

	@Override
	public String getKeyInfo() {
		return keyInfoStr;
	}

	@Override
	public void completeHeaderSetting(Map<String, String> header) {
		header.put(ERequestHeaders.HmacAlgorithm.name(), hmacAlgorithm.getName());
	}

	
	/**
	 * builder for {@link AccessKeyRequester}
	 * @author Benjamin
	 */
	public final static class AccessKeyRequesterBuilder {
		private HttpProvider httpProvider;
		private JsonProvider jsonProvider;
		private boolean validateResponseAuthenticateString = true;
		
		private String accessKey;
		private byte[] secretKey;
		private EHmacAlgorithms hmacAlgorithm = EHmacAlgorithms.HMAC_SHA_256;
		
		AccessKeyRequesterBuilder() {}
		
		/**
		 * construct an instance by specifying {@link HttpProvider}, {@link JsonProvider}, and whether validate response message authentication string
		 * @param httpProvider the HttpProvider
		 * @param jsonProvider the JsonProvider
		 * @param validateResponseAuthenticateString whether validate response message authentication string
		 */
		AccessKeyRequesterBuilder(HttpProvider httpProvider, JsonProvider jsonProvider, boolean validateResponseAuthenticateString) {
			this.httpProvider = httpProvider;
			this.jsonProvider = jsonProvider;
			this.validateResponseAuthenticateString = validateResponseAuthenticateString;
		}
		
		/**
		 * specify {@link HttpProvider}
		 * @param hp the HttpProvider
		 * @return the instance itself
		 */
		public AccessKeyRequesterBuilder httpProvider(HttpProvider hp) {
			this.httpProvider = hp;
			return this;
		}

		/**
		 * specify {@link JsonProvider}
		 * @param jp the JsonProvider
		 * @return the instance itself
		 */
		public AccessKeyRequesterBuilder jsonProvider(JsonProvider jp) {
			this.jsonProvider = jp;
			return this;
		}

		/**
		 * specify whether validate response message authentication string
		 * @param vr whether validate response message authentication string
		 * @return the instance itself
		 */
		public AccessKeyRequesterBuilder validateResponse(boolean vr) {
			this.validateResponseAuthenticateString = vr;
			return this;
		}

		/**
		 * specify access key
		 * @param ak access key
		 * @return the instance itself
		 */
		public AccessKeyRequesterBuilder accessKey(String ak) {
			this.accessKey = ak;
			return this;
		}
		
		/**
		 * specify secret key
		 * @param sk secret key
		 * @return the instance itself
		 */
		public AccessKeyRequesterBuilder secretKey(byte[] sk) {
			this.secretKey = sk;
			return this;
		}

		/**
		 * specify secret key using Base64 encoded string
		 * @param skBase64 secret key using Base64 encoded string
		 * @return the instance itself
		 */
		public AccessKeyRequesterBuilder secretKeyOfBase64(String skBase64) {
			this.secretKey = Base64.getDecoder().decode(skBase64);
			return this;
		}
		
		/**
		 * specify HMAC algorithm
		 * @param hmacAlgo HMAC algorithm
		 * @return the instance itself
		 */
		public AccessKeyRequesterBuilder hmacAlgorithm(EHmacAlgorithms hmacAlgo) {
			this.hmacAlgorithm = hmacAlgo;
			return this;
		}
		
		/**
		 * build {@link AccessKeyRequester} using configurations the instance holds
		 * @return the built result object
		 */
		public AccessKeyRequester build() {
			AccessKeyRequester requester = new AccessKeyRequester(this.httpProvider, this.jsonProvider, this.validateResponseAuthenticateString);
			requester.keyInfoStr = "AK|" + Objects.requireNonNull(this.accessKey);
			requester.secretKey = Objects.requireNonNull(this.secretKey);
			requester.hmacAlgorithm = this.hmacAlgorithm != null ? this.hmacAlgorithm : EHmacAlgorithms.HMAC_SHA_256;
			return requester;
		}
		
	}
}
