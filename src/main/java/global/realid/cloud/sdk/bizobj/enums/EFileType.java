package global.realid.cloud.sdk.bizobj.enums;

import global.realid.cloud.sdk.bizobj.base.InternalEnum;

/**
 * file type enumeration
 * @author Benjamin
 */
public enum EFileType implements InternalEnum {

	/** PDF */
	P("P", "PDF"),
	/** Image */
	I("I", "Image"),
	;
	
	/**
	 * find enum by code
	 * @param code the code
	 * @return corresponding enum
	 */
	public static EFileType valueOfCode(String code) {
		EFileType[] values = values();
		for (EFileType val : values) {
			if (val.code.equals(code)) {
				return val;
			}
		}
		return null;
	}
	
	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}


	private String code;
	private String desc;
	
	private EFileType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
}
