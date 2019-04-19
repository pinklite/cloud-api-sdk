package global.realid.cloud.sdk.client;

import global.realid.cloud.sdk.bizobj.base.ResponseVO;
import global.realid.cloud.sdk.bizobj.enums.EFileType;
import global.realid.cloud.sdk.bizobj.enums.EYesOrNo;
import global.realid.cloud.sdk.bizobj.resp.BatchFileWatermarkRespVO;
import global.realid.cloud.sdk.bizobj.resp.BusinessCardOcrResult;
import global.realid.cloud.sdk.bizobj.resp.ChinaDrivingLicenceOcrResult;
import global.realid.cloud.sdk.bizobj.resp.ChinaIdCardBackOcrResult;
import global.realid.cloud.sdk.bizobj.resp.ChinaIdCardFrontOcrResult;
import global.realid.cloud.sdk.bizobj.resp.ContourRecognizeAndCropRespVO;
import global.realid.cloud.sdk.bizobj.resp.GeneralPassportOcrResult;
import global.realid.cloud.sdk.bizobj.resp.HongKongIdCardOcrResult;
import global.realid.cloud.sdk.bizobj.resp.IndonesiaIdCardOcrResult;
import global.realid.cloud.sdk.bizobj.resp.SilentImageVerificationRespVo;
import global.realid.cloud.sdk.bizobj.resp.SingaporeDrivingLicenceOcrResult;
import global.realid.cloud.sdk.bizobj.resp.SingaporeIdCardOcrResult;
import global.realid.cloud.sdk.bizobj.resp.SingleFileWatermarkRespVO;
import global.realid.cloud.sdk.bizobj.resp.ThailandIdCardOcrResult;
import global.realid.cloud.sdk.bizobj.resp.VerificationRespVo;
import global.realid.cloud.sdk.client.requester.RealIdCloudApiRequester;
import global.realid.cloud.sdk.enums.ERealIdCloudApis;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
 * implementaion of {@link RealIdCloudApiClient}
 * @author Benjamin
 */

public class CloudApiClient implements RealIdCloudApiClient {

	private String baseUrl;
	private RealIdCloudApiRequester requester;

	/**
	 * construct a client for product environment, using the {@link RealIdCloudApiRequester} specified
	 * @param requester the RealIdCloudApiRequester
	 */
	CloudApiClient(RealIdCloudApiRequester requester) {
		this("https://cloud-api.realid.global", requester);
	}
	
	/**
	 * construct a client for given host, using the {@link RealIdCloudApiRequester} specified
	 * @param baseUrl HTTP URL prefix, in the format of '<code>(http|https)://HOST[:PORT]</code>'
	 * @param requester the RealIdCloudApiRequester
	 */
	CloudApiClient(String baseUrl, RealIdCloudApiRequester requester) {
		super();
		if (baseUrl == null) {
			throw new NullPointerException("baseUrl should not be null");
		}
		this.baseUrl = baseUrl;
		this.requester = requester;
	}
	
	/**
	 * just simply wrap the request method
	 * @param apiEnum the enumeration value of the API
	 * @param fileFields file fields in form
	 * @param textFields text fields in form
	 * @return response object
	 * @throws SocketException exception during socket connection
	 * @throws IOException exception during network I/O
	 */
	private <T> T multipartFormRequest(ERealIdCloudApis apiEnum, Map<String, File> fileFields, Map<String, String> textFields) throws SocketException, IOException {
		return requester.executeMultipartFormRequest(apiEnum.getFullApiUrl(baseUrl), fileFields, textFields, apiEnum.getResponseType());
	}
	
	@Override
	public ResponseVO<VerificationRespVo> certificatePresentImageVerification(String ctfImagePath, String portraitImagePath, EYesOrNo autoRotate) throws SocketException, IOException {
		Map<String, File> fileFields = new HashMap<>();
		fileFields.put("ctfImage", new File(ctfImagePath));
		fileFields.put("portraitImage", new File(portraitImagePath));
		
		Map<String, String> textFields = new HashMap<>();
		if (autoRotate != null) {
			textFields.put("autoRotate", autoRotate.getCode());
		}
		
		return multipartFormRequest(ERealIdCloudApis.A10003, fileFields, textFields);
	}
	
	@Override
	public ResponseVO<VerificationRespVo> silentDetection(String videoPath, EYesOrNo returnImage) throws SocketException, IOException {
		Map<String, File> fileFields = new HashMap<>();
		fileFields.put("videoFile", new File(videoPath));
		
		Map<String, String> textFields = new HashMap<>();
		if (returnImage != null) {
			textFields.put("returnImage", returnImage.getCode());
		}
		
		return multipartFormRequest(ERealIdCloudApis.A10004, fileFields, textFields);
	}
	
	@Override
	public ResponseVO<SingleFileWatermarkRespVO> singleFileWatermarkProcess(String sourceFilePath, String watermarkImage, 
			EFileType fileType, Double scale, Float alpha, Double rotateAngle) throws SocketException, IOException {
		Map<String, File> fileFields = new HashMap<>();
		fileFields.put("sourceFile", new File(sourceFilePath));
		fileFields.put("watermarkImage", new File(watermarkImage));
		
		Map<String, String> textFields = new HashMap<>();
		textFields.put("fileType", fileType.getCode());
		
		if (scale != null) {
			textFields.put("scale", scale.toString());
		}
		if (alpha != null) {
			textFields.put("alpha", alpha.toString());
		}
		if (rotateAngle != null) {
			textFields.put("rotateAngle", rotateAngle.toString());
		}
		
		return multipartFormRequest(ERealIdCloudApis.A10005, fileFields, textFields);
	}
	
	@Override
	public ResponseVO<BatchFileWatermarkRespVO> batchWatermarkProcess(String archiveFile, String watermarkImage, 
			Double scale, Float alpha, Double rotateAngle) throws SocketException, IOException {
		Map<String, File> fileFields = new HashMap<>();
		fileFields.put("archiveFile", new File(archiveFile));
		fileFields.put("watermarkImage", new File(watermarkImage));
		
		Map<String, String> textFields = new HashMap<>();
		if (scale != null) {
			textFields.put("scale", scale.toString());
		}
		if (alpha != null) {
			textFields.put("alpha", alpha.toString());
		}
		if (rotateAngle != null) {
			textFields.put("rotateAngle", rotateAngle.toString());
		}
		
		return multipartFormRequest(ERealIdCloudApis.A10006, fileFields, textFields);
	}
	
	@Override
	public ResponseVO<BusinessCardOcrResult> businessCardRecognize(String filePath) throws SocketException, IOException {
		Map<String, File> fileFields = new HashMap<>();
		fileFields.put("file", new File(filePath));
		
		return multipartFormRequest(ERealIdCloudApis.A10007, fileFields, null);
	}
	
	@Override
	public ResponseVO<ChinaIdCardFrontOcrResult> chinaIdCardFrontRecognize(String filePath) throws SocketException, IOException {
		Map<String, File> fileFields = new HashMap<>();
		fileFields.put("file", new File(filePath));
		
		return multipartFormRequest(ERealIdCloudApis.A10008, fileFields, null);
	}
	
	@Override
	public ResponseVO<ChinaIdCardBackOcrResult> chinaIdCardBackRecognize(String filePath) throws SocketException, IOException {
		Map<String, File> fileFields = new HashMap<>();
		fileFields.put("file", new File(filePath));
		
		return multipartFormRequest(ERealIdCloudApis.A10009, fileFields, null);
	}
	
	@Override
	public ResponseVO<HongKongIdCardOcrResult> hongKongIdCardRecognize(String filePath) throws SocketException, IOException {
		Map<String, File> fileFields = new HashMap<>();
		fileFields.put("file", new File(filePath));
		
		return multipartFormRequest(ERealIdCloudApis.A10010, fileFields, null);
	}
	
	@Override
	public ResponseVO<SingaporeIdCardOcrResult> singaporeIdCardRecognize(String filePath) throws SocketException, IOException {
		Map<String, File> fileFields = new HashMap<>();
		fileFields.put("file", new File(filePath));
		
		return multipartFormRequest(ERealIdCloudApis.A10011, fileFields, null);
	}
	
	@Override
	public ResponseVO<ThailandIdCardOcrResult> thailandIdCardRecognize(String filePath) throws SocketException, IOException {
		Map<String, File> fileFields = new HashMap<>();
		fileFields.put("file", new File(filePath));
		
		return multipartFormRequest(ERealIdCloudApis.A10012, fileFields, null);
	}
	
	@Override
	public ResponseVO<IndonesiaIdCardOcrResult> indonesiaIdCardRecognize(String filePath) throws SocketException, IOException {
		Map<String, File> fileFields = new HashMap<>();
		fileFields.put("file", new File(filePath));
		
		return multipartFormRequest(ERealIdCloudApis.A10013, fileFields, null);
	}
	
	@Override
	public ResponseVO<ChinaDrivingLicenceOcrResult> chinaDrivingLicenceRecognize(String filePath) throws SocketException, IOException {
		Map<String, File> fileFields = new HashMap<>();
		fileFields.put("file", new File(filePath));
		
		return multipartFormRequest(ERealIdCloudApis.A10019, fileFields, null);
	}
	
	@Override
	public ResponseVO<SingaporeDrivingLicenceOcrResult> singaporeDrivingLicenceRecognize(String filePath) throws SocketException, IOException {
		Map<String, File> fileFields = new HashMap<>();
		fileFields.put("file", new File(filePath));
		
		return multipartFormRequest(ERealIdCloudApis.A10020, fileFields, null);
	}
	
	@Override
	public ResponseVO<GeneralPassportOcrResult> passportRecognize(String filePath) throws SocketException, IOException {
		Map<String, File> fileFields = new HashMap<>();
		fileFields.put("file", new File(filePath));
		
		return multipartFormRequest(ERealIdCloudApis.A10021, fileFields, null);
	}
	
	@Override
	public ResponseVO<ContourRecognizeAndCropRespVO> contourRecognizeAndCrop(String imagePath) throws SocketException, IOException {
		Map<String, File> fileFields = new HashMap<>();
		fileFields.put("image", new File(imagePath));
		
		return multipartFormRequest(ERealIdCloudApis.A10022, fileFields, null);
	}
	
	@Override
	public ResponseVO<VerificationRespVo> faceImageVerification(String firstImagePath, String secondImagePath, EYesOrNo autoRotate) throws SocketException, IOException {
		Map<String, File> fileFields = new HashMap<>();
		fileFields.put("firstImage", new File(firstImagePath));
		fileFields.put("secondImage", new File(secondImagePath));
		
		Map<String, String> textFields = new HashMap<>();
		if (autoRotate != null) {
			textFields.put("autoRotate", autoRotate.getCode());
		}
		
		return multipartFormRequest(ERealIdCloudApis.A10023, fileFields, textFields);
	}
	
	@Override
	public ResponseVO<SilentImageVerificationRespVo> silentImageVerification(String videoFilePath, String imageFilePath, EYesOrNo autoRotate, EYesOrNo returnImage) throws SocketException, IOException {
		Map<String, File> fileFields = new HashMap<>();
		fileFields.put("videoFile", new File(videoFilePath));
		fileFields.put("imageFile", new File(imageFilePath));
		
		Map<String, String> textFields = new HashMap<>();
		if (autoRotate != null) {
			textFields.put("autoRotate", autoRotate.getCode());
		}
		if (returnImage != null) {
			textFields.put("returnImage", returnImage.getCode());
		}
		
		return multipartFormRequest(ERealIdCloudApis.A10024, fileFields, textFields);
	}
	
	@Override
	public ResponseVO<GeneralPassportOcrResult> passportMrzRecognize(String filePath) throws SocketException, IOException {
		Map<String, File> fileFields = new HashMap<>();
		fileFields.put("file", new File(filePath));
		
		return multipartFormRequest(ERealIdCloudApis.A10025, fileFields, null);
	}

	@Override
	public void close() throws IOException {
		requester.close();
	}
	
}
