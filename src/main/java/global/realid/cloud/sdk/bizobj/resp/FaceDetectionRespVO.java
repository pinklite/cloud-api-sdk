package global.realid.cloud.sdk.bizobj.resp;

import java.util.List;

/**
 * response result for Face Detection
 * 
 * @author Benjamin
 */
public class FaceDetectionRespVO {

	private String exists;
	private Double detectAngle;
	private List<FaceDetectionResultVO> faces;
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

	public List<FaceDetectionResultVO> getFaces() {
		return faces;
	}

	public void setFaces(List<FaceDetectionResultVO> faces) {
		this.faces = faces;
	}

	public String getDetectImage() {
		return detectImage;
	}

	public void setDetectImage(String detectImage) {
		this.detectImage = detectImage;
	}

	
	/**
	 * face detection result object
	 * 
	 * @author Benjamin
	 */
	public static class FaceDetectionResultVO {
		private Rectangle rect;
		private Float confidence;
		private String faceImage;

		public Rectangle getRect() {
			return rect;
		}

		public void setRect(Rectangle rect) {
			this.rect = rect;
		}

		public Float getConfidence() {
			return confidence;
		}

		public void setConfidence(Float confidence) {
			this.confidence = confidence;
		}

		public String getFaceImage() {
			return faceImage;
		}

		public void setFaceImage(String faceImage) {
			this.faceImage = faceImage;
		}

	}
}
