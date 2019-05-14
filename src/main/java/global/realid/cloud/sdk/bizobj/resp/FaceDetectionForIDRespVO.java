package global.realid.cloud.sdk.bizobj.resp;

/**
 * response result of Face Detection for ID
 * 
 * @author Benjamin
 */

public class FaceDetectionForIDRespVO {

	private String exists;
	private Double detectAngle;
	private Rectangle detectRect;
	private String faceImage;
	private String detectImage;

	public String getExists() {
		return exists;
	}

	public void setExists(String exists) {
		this.exists = exists;
	}

	public Double getDetectAngle() {
		return detectAngle;
	}

	public void setDetectAngle(Double detectAngle) {
		this.detectAngle = detectAngle;
	}

	public Rectangle getDetectRect() {
		return detectRect;
	}

	public void setDetectRect(Rectangle detectRect) {
		this.detectRect = detectRect;
	}

	public String getFaceImage() {
		return faceImage;
	}

	public void setFaceImage(String faceImage) {
		this.faceImage = faceImage;
	}

	public String getDetectImage() {
		return detectImage;
	}

	public void setDetectImage(String detectImage) {
		this.detectImage = detectImage;
	}

}
