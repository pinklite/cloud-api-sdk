package global.realid.cloud.sdk.bizobj.base;

import java.io.Serializable;

/**
 * base wrapper of API request
 * @author Benjamin
 */

public class RequestVO<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * version number
	 */
	private String version;

	/**
	 * parameter object
	 */
	private T param;

	public String getVersion() {
		return version;
	}

	/**
	 * set request version
	 * @param version request version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	public T getParam() {
		return param;
	}

	/**
	 * set request parameter object
	 * @param param request parameter
	 */
	public void setParam(T param) {
		this.param = param;
	}

}
