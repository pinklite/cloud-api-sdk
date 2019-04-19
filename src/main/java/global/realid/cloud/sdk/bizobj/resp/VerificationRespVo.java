package global.realid.cloud.sdk.bizobj.resp;

/**
 * response result for verification API
 * @author Benjamin
 */
public class VerificationRespVo {

	private String passed;
	
	private Double score;
	
	private Double imageTimestamp;
	
	private String image;
	
	public VerificationRespVo() {
		
	}
	
	public VerificationRespVo(Double score) {
		this.score = score;
	}

	public String getPassed() {
		return passed;
	}

	public void setPassed(String passed) {
		this.passed = passed;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
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
