package global.realid.cloud.sdk.bizobj.enums;

import global.realid.cloud.sdk.bizobj.base.InternalEnum;

/**
 * Yes or No enum
 *
 * @author Benjamin
 * 
 * 
 */
public enum EYesOrNo implements InternalEnum {

	/** Yes */
	Y("Y", "Yes"),
	/** No */
	N("N", "No"),
	;
	
	/**
	 * find enum by code
	 * @param code the code
	 * @return corresponding enum
	 */
	public static EYesOrNo valueOfCode(String code) {
		EYesOrNo[] values = values();
		for (EYesOrNo val : values) {
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
	
	private EYesOrNo(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
}
