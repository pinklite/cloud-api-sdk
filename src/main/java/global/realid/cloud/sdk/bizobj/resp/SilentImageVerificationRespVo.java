package global.realid.cloud.sdk.bizobj.resp;

/**
 * response result for silent image verification
 * @author Benjamin
 */
public class SilentImageVerificationRespVo {

	private String passed;
	
	private Double verificationScore;
	
	private Double imageTimestamp;
	
	private String image;
	
	public SilentImageVerificationRespVo() {
		
	}
	
	public SilentImageVerificationRespVo(Double verificationScore) {
		this.verificationScore = verificationScore;
	}

	public String getPassed() {
		return passed;
	}

	public void setPassed(String passed) {
		this.passed = passed;
	}

	public Double getVerificationScore() {
		return verificationScore;
	}

	public void setVerificationScore(Double verificationScore) {
		this.verificationScore = verificationScore;
	}

	public Double getImageTimestamp() {
		return imageTimestamp;
	}

	public void setImageTimestamp(Double imageTimestamp) {
		this.imageTimestamp = imageTimestamp;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
}
