package global.realid.cloud.sdk.bizobj.base;

import java.io.Serializable;

/**
 * base wrapper for API response
 * @author Benjamin
 */
public class ResponseVO<T> implements Serializable {

	private static final long serialVersionUID = 8360420598413733148L;
	
	/**
	 * request unique identifier
	 */
	private String requestId;

	private Integer code;
	private String message;
	private T result;

	/**
	 * get request unique identifier
	 * @return request unique identifier
	 */
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * get response code
	 * @return response code
	 */
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * get response message
	 * @return response message
	 */
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * get response result object
	 * @return response result
	 */
	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
	
}
