package global.realid.cloud.sdk.client;

import global.realid.cloud.sdk.client.requester.RealIdCloudApiRequester;
import global.realid.cloud.sdk.client.requester.RealIdCloudApiRequesterBuilder;
import global.realid.cloud.sdk.client.requester.AccessKeyRequester.AccessKeyRequesterBuilder;
import global.realid.cloud.sdk.client.requester.CertificateRequester.CertificateRequesterBuilder;
import global.realid.cloud.sdk.enums.EHmacAlgorithms;
import global.realid.cloud.sdk.provider.cryptokey.CryptoPrivateKeyProvider;
import global.realid.cloud.sdk.provider.cryptokey.CryptoPublicKeyProvider;
import global.realid.cloud.sdk.provider.http.HttpProvider;
import global.realid.cloud.sdk.provider.json.JsonProvider;

/**
 * builder for Real ID Cloud API Client
 * @author Benjamin
 */

public abstract class RealIdCloudApiClientBuilder {

	private String baseUrl;
	
	/**
	 * create an {@link AccessKeyClientBuilder} for client using access key
	 * @return the AccessKeyClientBuilder instance
	 */
	public static AccessKeyClientBuilder createUsingAccessKey() {
		return new AccessKeyClientBuilder();
	}
	
	/**
	 * create a {@link CertificateClientBuilder} for client using digital certificate
	 * @return the CertificateClientBuilder instance
	 */
	public static CertificateClientBuilder createUsingCertificate() {
		return new CertificateClientBuilder();
	}
	
	/**
	 * set base url for server host; ignore this configuration for production environment
	 * @param bu HTTP URL prefix, in the format of '<code>(http|https)://HOST[:PORT]</code>'
	 */
	public void setBaseUrl(String bu) {
		this.baseUrl = bu;
	}
	
	/**
	 * build an instance of {@link RealIdCloudApiClient} using configurations the instance holds
	 * @return the built result object
	 */
	public RealIdCloudApiClient build() {
		if (baseUrl != null) {
			return new CloudApiClient(baseUrl, buildRequester());
		} else {
			return new CloudApiClient(buildRequester());
		}
	}
	
	/**
	 * build the {@link RealIdCloudApiRequester requester} that subclass supports
	 * @return the RealIdCloudApiRequester that subclass could handle
	 */
	protected abstract RealIdCloudApiRequester buildRequester();

	
	
	/**
	 * builder for client using access key
	 * @author Benjamin
	 */
	public static class AccessKeyClientBuilder extends RealIdCloudApiClientBuilder {
		private AccessKeyRequesterBuilder requesterBuilder;
		
		private AccessKeyClientBuilder() {
			this.requesterBuilder = RealIdCloudApiRequesterBuilder.createUsingAccessKey();
		}

		@Override
		protected RealIdCloudApiRequester buildRequester() {
			return requesterBuilder.build();
		}

		/**
		 * set base url for server host
		 * @param bu HTTP URL prefix, in the format of '<code>(http|https)://HOST[:PORT]</code>'
		 * @return the instance itself
		 */
		public AccessKeyClientBuilder baseUrl(String bu) {
			super.setBaseUrl(bu);
			return this;
		}

		/**
		 * specify {@link HttpProvider} for {@link AccessKeyRequesterBuilder}
		 * @param hp the HttpProvider
		 * @return the instance itself
		 */
		public AccessKeyClientBuilder httpProvider(HttpProvider hp) {
			requesterBuilder.httpProvider(hp);
			return this;
		}

		/**
		 * specify {@link JsonProvider} for {@link AccessKeyRequesterBuilder}
		 * @param jp the JsonProvider
		 * @return the instance itself
		 */
		public AccessKeyClientBuilder jsonProvider(JsonProvider jp) {
			requesterBuilder.jsonProvider(jp);
			return this;
		}

		/**
		 * specify <code>validateResponse</code> for {@link AccessKeyRequesterBuilder}
		 * @param vr whether validate response message authentication string
		 * @return the instance itself
		 */
		public AccessKeyClientBuilder validateResponse(boolean vr) {
			requesterBuilder.validateResponse(vr);
			return this;
		}

		/**
		 * specify access key for {@link AccessKeyRequesterBuilder}
		 * @param ak access key
		 * @return the instance itself
		 */
		public AccessKeyClientBuilder accessKey(String ak) {
			requesterBuilder.accessKey(ak);
			return this;
		}

		/**
		 * specify secret key for {@link AccessKeyRequesterBuilder}
		 * @param sk secret key
		 * @return the instance itself
		 */
		public AccessKeyClientBuilder secretKey(byte[] sk) {
			requesterBuilder.secretKey(sk);
			return this;
		}

		/**
		 * specify secret key using Base64 encoded string for {@link AccessKeyRequesterBuilder}
		 * @param skBase64 secret key using Base64 encoded string
		 * @return the instance itself
		 */
		public AccessKeyClientBuilder secretKeyOfBase64(String skBase64) {
			requesterBuilder.secretKeyOfBase64(skBase64);
			return this;
		}

		/**
		 * specify HMAC algorithm for {@link AccessKeyRequesterBuilder}
		 * @param hmacAlgo HMAC algorithm
		 * @return the instance itself
		 */
		public AccessKeyClientBuilder hmacAlgorithm(EHmacAlgorithms hmacAlgo) {
			requesterBuilder.hmacAlgorithm(hmacAlgo);
			return this;
		}
	}
	
	/**
	 * builder for client using digital certificate
	 * @author Benjamin
	 */
	public static class CertificateClientBuilder extends RealIdCloudApiClientBuilder {
		private CertificateRequesterBuilder requesterBuilder;
		
		private CertificateClientBuilder() {
			this.requesterBuilder = RealIdCloudApiRequesterBuilder.createUsingCertificate();
		}

		@Override
		protected RealIdCloudApiRequester buildRequester() {
			return requesterBuilder.build();
		}

		/**
		 * set base url for server host
		 * @param bu HTTP URL prefix, in the format of '<code>(http|https)://HOST[:PORT]</code>'
		 * @return the instance itself
		 */
		public CertificateClientBuilder baseUrl(String bu) {
			super.setBaseUrl(bu);
			return this;
		}

		/**
		 * specify {@link HttpProvider} for {@link CertificateRequesterBuilder}
		 * @param hp the HttpProvider
		 * @return the instance itself
		 */
		public CertificateClientBuilder httpProvider(HttpProvider hp) {
			requesterBuilder.httpProvider(hp);
			return this;
		}

		/**
		 * specify {@link JsonProvider} for {@link CertificateRequesterBuilder}
		 * @param jp the JsonProvider
		 * @return the instance itself
		 */
		public CertificateClientBuilder jsonProvider(JsonProvider jp) {
			requesterBuilder.jsonProvider(jp);
			return this;
		}

		/**
		 * specify <code>validateResponse</code> for {@link CertificateRequesterBuilder}
		 * @param vr whether validate response message authentication string
		 * @return the instance itself
		 */
		public CertificateClientBuilder validateResponse(boolean vr) {
			requesterBuilder.validateResponse(vr);
			return this;
		}

		/**
		 * specify the certificate ID for {@link CertificateRequesterBuilder}
		 * @param ci the certificate ID
		 * @return the instance itself
		 */
		public CertificateClientBuilder certId(Integer ci) {
			requesterBuilder.certId(ci);
			return this;
		}

		/**
		 * specify {@link CryptoPrivateKeyProvider private key provider} for {@link CertificateRequesterBuilder}
		 * @param pkp the private key provider
		 * @return the instance itself
		 */
		public CertificateClientBuilder privateKeyProvider(CryptoPrivateKeyProvider pkp) {
			requesterBuilder.privateKeyProvider(pkp);
			return this;
		}

		/**
		 * specify specify {@link CryptoPublicKeyProvider public key provider} for {@link CertificateRequesterBuilder}
		 * @param pkp the public key provider
		 * @return the instance itself
		 */
		public CertificateClientBuilder serverPublicKeyProvider(CryptoPublicKeyProvider pkp) {
			requesterBuilder.serverPublicKeyProvider(pkp);
			return this;
		}
	}
	
}
