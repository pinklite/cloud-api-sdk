package global.realid.cloud.sdk.bizobj.enums;



/**
 * response code enumeration, maybe useful in the future
 * @author Benjamin
 */
public enum EResponseCode {
	OK(0, "OK"), //
	ERROR_CREDENTIAL(101, "credential error"), //
	ERROR_MESSAGE_AUTHENTICATION(102, "message authentication error"), //
	ERROR_DUPLICATE_REQUEST(103, "duplicate requests"), //
	ERROR_PARAMETER(104, "parameter error"), //
	ERROR_INTERNAL_NETWORK(105, "internal network error"), //
	ERROR_INTERNAL(106, "internal error"), //
	ERROR_HEADER(107, "lack of mandatory header or header value invalid"), //
	ERROR_UNSUPPORTED_API(108, "unsupported api"), //
	ERROR_ACC_BAL_INSUFFICIENT(109, "insufficient account balance"), //
	ERROR_FILE_TOO_LARGE(110, "file too large"), //
	ERROR_REQUEST_TOO_OFTEN(111, "request for the api is too often"), //

	ERROR_UNKNOWN(999, "unknown error"),
	;
	
	/**
	 * find enum by code
	 * @param code the code
	 * @return corresponding enum
	 */
	public static EResponseCode valueOfCode(Integer code) {
		EResponseCode[] values = values();
		for (EResponseCode val : values) {
			if (val.code.equals(code)) {
				return val;
			}
		}
		return null;
	}
	
	private Integer code;
	private String message;

	private EResponseCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
