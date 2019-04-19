package global.realid.cloud.sdk.provider.http;

import java.util.Map;

/**
 * result wrapper for {@link HttpProvider}
 * @author Benjamin
 */

public class HttpProviderResult<T> {

	private T responseContent;
	private int statusCode = 200;
	private Map<String, String> headers;

	/**
	 * get response content
	 * @return response content
	 */
	public T getResponseContent() {
		return responseContent;
	}

	/**
	 * set response content
	 * @param responseContent response content
	 */
	public void setResponseContent(T responseContent) {
		this.responseContent = responseContent;
	}

	/**
	 * get response status code
	 * @return response status code
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * set response status code
	 * @param statusCode response status code
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * get response headers
	 * @return response headers
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * set response headers
	 * @param headers response headers
	 */
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

}
