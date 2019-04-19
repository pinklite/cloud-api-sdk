package global.realid.cloud.sdk.client.requester;

import global.realid.cloud.sdk.client.requester.AccessKeyRequester.AccessKeyRequesterBuilder;
import global.realid.cloud.sdk.client.requester.CertificateRequester.CertificateRequesterBuilder;
import global.realid.cloud.sdk.provider.http.HttpProvider;
import global.realid.cloud.sdk.provider.json.JsonProvider;

/**
 * builder for {@link RealIdCloudApiRequester}
 * @author Benjamin
 */

public class RealIdCloudApiRequesterBuilder {

	private HttpProvider httpProvider;
	private JsonProvider jsonProvider;
	
	private boolean validateResponseAuthenticateString = true;
	
	private RealIdCloudApiRequesterBuilder() {}
	
	/**
	 * create a {@link RealIdCloudApiRequesterBuilder} instance for specifying more configuration
	 * @return the RealIdCloudApiRequesterBuilder
	 */
	public static RealIdCloudApiRequesterBuilder create() {
		return new RealIdCloudApiRequesterBuilder();
	}
	
	/**
	 * create an {@link AccessKeyRequesterBuilder} instance for specifying more configuration
	 * @return the AccessKeyRequesterBuilder
	 */
	public static AccessKeyRequesterBuilder createUsingAccessKey() {
		return new AccessKeyRequesterBuilder();
	}
	
	/**
	 * create a {@link CertificateRequesterBuilder} instance for specifying more configuration
	 * @return the CertificateRequesterBuilder
	 */
	public static CertificateRequesterBuilder createUsingCertificate() {
		return new CertificateRequesterBuilder();
	}
	
	/**
	 * specify {@link HttpProvider}
	 * @param hp the HttpProvider
	 * @return the instance itself
	 */
	public RealIdCloudApiRequesterBuilder httpProvider(HttpProvider hp) {
		this.httpProvider = hp;
		return this;
	}
	
	/**
	 * specify {@link JsonProvider}
	 * @param jp the JsonProvider
	 * @return the instance itself
	 */
	public RealIdCloudApiRequesterBuilder jsonProvider(JsonProvider jp) {
		this.jsonProvider = jp;
		return this;
	}
	
	/**
	 * specify whether validate response message authentication string
	 * @param vr whether validate response message authentication string
	 * @return the instance itself
	 */
	public RealIdCloudApiRequesterBuilder validateResponse(boolean vr) {
		this.validateResponseAuthenticateString = vr;
		return this;
	}
	
	/**
	 * indicates that an {@link AccessKeyRequester} will be created, thus more configuration should be specified besides those specified before
	 * @return the {@link AccessKeyRequesterBuilder} for more configuration
	 */
	public AccessKeyRequesterBuilder usingAccessKey() {
		return new AccessKeyRequesterBuilder(httpProvider, jsonProvider, validateResponseAuthenticateString);
	}
	
	/**
	 * indicates that an {@link CertificateRequester} will be created, thus more configuration should be specified besides those specified before
	 * @return the {@link CertificateRequesterBuilder} for more configuration
	 */
	public CertificateRequesterBuilder usingCertificate() {
		return new CertificateRequesterBuilder(httpProvider, jsonProvider, validateResponseAuthenticateString);
	}
}
