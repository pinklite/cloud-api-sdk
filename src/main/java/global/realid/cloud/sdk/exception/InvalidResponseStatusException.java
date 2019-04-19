package global.realid.cloud.sdk.exception;

import global.realid.cloud.sdk.provider.http.HttpProviderResult;

/**
 * Subclass of {@link InvalidResponseStatusException} that indicates a response with invalid response status code(that is, status code != 200).
 * Instances of this exception privide the {@link HttpProviderResult} instance related to the request. 
 * @author Benjamin
 */

public class InvalidResponseStatusException extends RealIdCloudApiException {

	private static final long serialVersionUID = 1L;
	
	private HttpProviderResult<?> responseResult;

	/**
	 * get the {@link HttpProviderResult} instance related to the request
	 * @return the {@link HttpProviderResult} instance
	 */
	public HttpProviderResult<?> getResponseResult() {
		return responseResult;
	}

	/**
	 * Constructs a new {@link InvalidResponseStatusException} with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     * @param responseResult the {@link HttpProviderResult} instance related to the request
	 */
	public InvalidResponseStatusException(HttpProviderResult<?> responseResult) {
		super();
		this.responseResult = responseResult;
	}

	/**
      * Constructs a new {@link InvalidResponseStatusException} with the specified detail
     * message, cause, suppression enabled or disabled, and writable
     * stack trace enabled or disabled.
     * 
     * @param responseResult the {@link HttpProviderResult} instance related to the request
     * @param  message the detail message.
     * @param cause the cause.  (A {@code null} value is permitted,
     * and indicates that the cause is nonexistent or unknown.)
     * @param enableSuppression whether or not suppression is enabled
     *                          or disabled
     * @param writableStackTrace whether or not the stack trace should
     *                           be writable
	 */
	public InvalidResponseStatusException(HttpProviderResult<?> responseResult, String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.responseResult = responseResult;
	}

	/**
	 * Constructs a new {@link InvalidResponseStatusException} with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     * 
     * @param responseResult the {@link HttpProviderResult} instance related to the request
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
	 */
	public InvalidResponseStatusException(HttpProviderResult<?> responseResult, String message, Throwable cause) {
		super(message, cause);
		this.responseResult = responseResult;
	}

	/**
	 * Constructs a new {@link InvalidResponseStatusException} with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     * @param responseResult the {@link HttpProviderResult} instance related to the request
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
	 */
	public InvalidResponseStatusException(HttpProviderResult<?> responseResult, String message) {
		super(message);
		this.responseResult = responseResult;
	}

	/**
	 * Constructs a new {@link InvalidResponseStatusException} with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     * 
     * @param responseResult the {@link HttpProviderResult} instance related to the request
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
	 */
	public InvalidResponseStatusException(HttpProviderResult<?> responseResult, Throwable cause) {
		super(cause);
		this.responseResult = responseResult;
	}

	@Override
	public String toString() {
		return super.toString() + " || => status code: " + responseResult.getStatusCode() + ", headers: " + responseResult.getHeaders();
	}

}
