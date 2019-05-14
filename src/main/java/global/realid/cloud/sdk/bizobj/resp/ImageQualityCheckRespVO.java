package global.realid.cloud.sdk.bizobj.resp;

/**
 * response result of Image Quality Check
 * 
 * @author Benjamin
 */

public class ImageQualityCheckRespVO {

	private Double blurResult;
	private Integer lightspotResult;

	public Double getBlurResult() {
		return blurResult;
	}

	public void setBlurResult(Double blurResult) {
		this.blurResult = blurResult;
	}

	public Integer getLightspotResult() {
		return lightspotResult;
	}

	public void setLightspotResult(Integer lightspotResult) {
		this.lightspotResult = lightspotResult;
	}

}
