package global.realid.cloud.sdk.client.requester;

import global.realid.cloud.sdk.exception.RealIdCloudApiException;
import global.realid.cloud.sdk.provider.cryptokey.CryptoPrivateKeyProvider;
import global.realid.cloud.sdk.provider.cryptokey.CryptoPublicKeyProvider;
import global.realid.cloud.sdk.provider.http.HttpProvider;
import global.realid.cloud.sdk.provider.json.JsonProvider;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

/**
 * API requester based on digital certificate
 * @author Benjamin
 */

public class CertificateRequester extends AbstractApiRequester {

	private String keyInfoStr;
	private PrivateKey privateKey;
	private PublicKey serverPublicKey;

	/**
	 * overrides super class constructor
	 * @param httpProvider the HttpProvider
	 * @param jsonProvider the JsonProvider
	 * @param validateResponse whether validate response message authentication string
	 */
	CertificateRequester(HttpProvider httpProvider, JsonProvider jsonProvider,
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
	 * get private key
	 * @return the private key
	 */
	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	/**
	 * set private key
	 * @param privateKey the private key
	 */
	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	/**
	 * get server public key
	 * @return the server public key
	 */
	public PublicKey getServerPublicKey() {
		return serverPublicKey;
	}

	/**
	 * set server public key
	 * @param serverPublicKey the server public key
	 */
	public void setServerPublicKey(PublicKey serverPublicKey) {
		this.serverPublicKey = serverPublicKey;
	}

	@Override
	public String calculateJsonRequestMessageAuthenticationString(String dataStr, String nonce,
			String timestamp) throws Exception {
		return calculateStringSignature(dataStr, nonce, timestamp);
	}


	/**
	 * calculate signature of data using private key, and return result in Base64 encoded
	 * @param dataStr data as string
	 * @param nonce Nonce header value
	 * @param timestamp Timestamp header value
	 * @return signature of data, encoded in Base64
	 * @throws NoSuchAlgorithmException if no Provider supports a Signature implementation for the specified algorithm, which means that SHA256with[YOUR_PRIVATE_KEY_ALGORITHM] is not supported
	 * @throws InvalidKeyException if the private key is invalid.
	 * @throws SignatureException if the signature object is not initialized properly or if this signature algorithm is unable to process the input data provided.
	 */
	private String calculateStringSignature(String dataStr, String nonce, String timestamp) throws NoSuchAlgorithmException,
			InvalidKeyException, SignatureException {
		// concatenate required element to SS
		String format = "data=%s&nonce=%s&timestamp=%s";
		String ss = String.format(format, dataStr, nonce, timestamp);
		byte[] reqBytes = ss.getBytes(StandardCharsets.UTF_8);
		// calculate signature
		Signature signature = Signature.getInstance("SHA256with" + privateKey.getAlgorithm());
		signature.initSign(privateKey);
		signature.update(reqBytes);
		// encode in Base64
		return Base64.getEncoder().encodeToString(signature.sign());
	}

	@Override
	public String calculateFormRequestMessageAuthenticationString(Map<String, File> fileFields,
			Map<String, String> textFields, String nonce, String timestamp) throws Exception {
		String strFormData = getFormDataStringForMessageAuthentication(fileFields, textFields);
		return calculateStringSignature(strFormData, nonce, timestamp);
	}

	@Override
	public boolean validateResponseMessageAuthenticationString(String responseMessageAuthenticationStr, String dataStr, String nonce, String timestamp)
			throws Exception {
		byte[] respBytes = dataStr.getBytes(StandardCharsets.UTF_8);
		byte[] respSign = Base64.getDecoder().decode(responseMessageAuthenticationStr);
		
		Signature signature = Signature.getInstance("SHA256with" + serverPublicKey.getAlgorithm());
		signature.initVerify(serverPublicKey);
		signature.update(respBytes);
		
		return signature.verify(respSign);
	}

	@Override
	public String getKeyInfo() {
		return keyInfoStr;
	}

	@Override
	public void completeHeaderSetting(Map<String, String> header) {
		// do nothing
	}

	
	/**
	 * builder for {@link CertificateRequester}
	 * @author Benjamin
	 */
	public static class CertificateRequesterBuilder {
		private HttpProvider httpProvider;
		private JsonProvider jsonProvider;
		private boolean validateResponseAuthenticateString = true;
		
		private Integer certId;
		private CryptoPrivateKeyProvider privateKeyProvider;
		private CryptoPublicKeyProvider serverPublicKeyProvider;
		
		CertificateRequesterBuilder() {}
		
		/**
		 * construct an instance by specifying {@link HttpProvider}, {@link JsonProvider}, and whether validate response message authentication string
		 * @param httpProvider the HttpProvider
		 * @param jsonProvider the JsonProvider
		 * @param validateResponseAuthenticateString whether validate response message authentication string
		 */
		CertificateRequesterBuilder(HttpProvider httpProvider, JsonProvider jsonProvider, boolean validateResponseAuthenticateString) {
			this.httpProvider = httpProvider;
			this.jsonProvider = jsonProvider;
			this.validateResponseAuthenticateString = validateResponseAuthenticateString;
		}
		
		/**
		 * specify {@link HttpProvider}
		 * @param hp the HttpProvider
		 * @return the instance itself
		 */
		public CertificateRequesterBuilder httpProvider(HttpProvider hp) {
			this.httpProvider = hp;
			return this;
		}

		/**
		 * specify {@link JsonProvider}
		 * @param jp the JsonProvider
		 * @return the instance itself
		 */
		public CertificateRequesterBuilder jsonProvider(JsonProvider jp) {
			this.jsonProvider = jp;
			return this;
		}

		/**
		 * specify whether validate response message authentication string
		 * @param vr whether validate response message authentication string
		 * @return the instance itself
		 */
		public CertificateRequesterBuilder validateResponse(boolean vr) {
			this.validateResponseAuthenticateString = vr;
			return this;
		}

		/**
		 * specify certificate ID of the public key paired with the private key used, 
		 * certificate ID can be queried in <a href="https://cloud.realid.global/myprofile/security">Real ID Cloud official site</a>
		 * @param ci the certificate ID
		 * @return the instance itself
		 */
		public CertificateRequesterBuilder certId(Integer ci) {
			if (ci == null) {
				throw new NullPointerException("certificate id should not be null, please check it out on official website of Real ID Cloud");
			}
			
			this.certId = ci;
			return this;
		}
		
		/**
		 * specify {@link CryptoPrivateKeyProvider private key provider}
		 * @param pkp the CryptoPrivateKeyProvider
		 * @return the instance itself
		 */
		public CertificateRequesterBuilder privateKeyProvider(CryptoPrivateKeyProvider pkp) {
			this.privateKeyProvider = pkp;
			return this;
		}
		
		/**
		 * specify {@link CryptoPublicKeyProvider public key provider}
		 * @param pkp the CryptoPublicKeyProvider
		 * @return the instance itself
		 */
		public CertificateRequesterBuilder serverPublicKeyProvider(CryptoPublicKeyProvider pkp) {
			this.serverPublicKeyProvider = pkp;
			return this;
		}
		
		/**
		 * build {@link CertificateRequester} using configurations the instance holds
		 * @return the built result object
		 */
		public CertificateRequester build() {
			PrivateKey privateKey;
			try {
				privateKey = Objects.requireNonNull(this.privateKeyProvider).getPrivateKey();
			} catch (Exception e) {
				throw new RealIdCloudApiException("fail to get private key from provider " + this.privateKeyProvider.getClass().getCanonicalName(), e);
			}
			
			PublicKey serverPublicKey;
			try {
				serverPublicKey = Objects.requireNonNull(this.serverPublicKeyProvider).getPublicKey();
			} catch (Exception e) {
				throw new RealIdCloudApiException("fail to get server public key from provider " + this.serverPublicKeyProvider.getClass().getCanonicalName(), e);
			}
			
			CertificateRequester requester = new CertificateRequester(this.httpProvider, this.jsonProvider, this.validateResponseAuthenticateString);
			requester.privateKey = privateKey;
			requester.serverPublicKey = serverPublicKey;
			requester.keyInfoStr = "CTF|" + Objects.requireNonNull(this.certId);
			
			return requester;
		}
	}
	
}
