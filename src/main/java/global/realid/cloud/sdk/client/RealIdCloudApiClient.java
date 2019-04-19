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

import java.io.Closeable;
import java.io.IOException;
import java.net.SocketException;

/**
 * client for Real ID Cloud API
 * 
 * @author Benjamin
 */

public interface RealIdCloudApiClient extends Closeable {

	/**
	 * Portrait Similarity between Present and Certificate, wrapper for
	 * /identity_api/certificate_present_image_verification
	 * 
	 * @param ctfImagePath certificate image path
	 * @param portraitImagePath portrait image path
	 * @param autoRotate whether rotate the image automatically
	 * @return response object
	 * @throws SocketException when network connection problem occurs
	 * @throws IOException when network I/O problem occurs
	 */
	ResponseVO<VerificationRespVo> certificatePresentImageVerification(String ctfImagePath,
			String portraitImagePath, EYesOrNo autoRotate) throws SocketException, IOException;

	/**
	 * Face Liveness Detection, wrapper for /liveness_api/silent_detection
	 * 
	 * @param videoPath video file path
	 * @param returnImage whether return image snap from video after passing the detection
	 * @return response object
	 * @throws SocketException when network connection problem occurs
	 * @throws IOException when network I/O problem occurs
	 */
	ResponseVO<VerificationRespVo> silentDetection(String videoPath, EYesOrNo returnImage)
			throws SocketException, IOException;

	/**
	 * Single File Watermarking, wrapper for
	 * /watermark_api/add_watermark_to_file
	 * 
	 * @param sourceFilePath source file path
	 * @param watermarkImage watermark image path
	 * @param fileType file type
	 * @param scale scale factor for watermark image
	 * @param alpha transparency for watermark image
	 * @param rotateAngle rotate angle for watermark image, in degrees
	 * @return response object
	 * @throws SocketException when network connection problem occurs
	 * @throws IOException when network I/O problem occurs
	 */
	ResponseVO<SingleFileWatermarkRespVO> singleFileWatermarkProcess(String sourceFilePath,
			String watermarkImage, EFileType fileType, Double scale, Float alpha, Double rotateAngle)
			throws SocketException, IOException;

	/**
	 * Batch File Watermarking, wrapper for /watermark_api/add_watermark_batch
	 * 
	 * @param archiveFile path of archive file of source files
	 * @param watermarkImage watermark image path
	 * @param scale scale factor for watermark image
	 * @param alpha transparency for watermark image
	 * @param rotateAngle rotate angle for watermark image, in degrees
	 * @return response object
	 * @throws SocketException when network connection problem occurs
	 * @throws IOException when network I/O problem occurs
	 */
	ResponseVO<BatchFileWatermarkRespVO> batchWatermarkProcess(String archiveFile,
			String watermarkImage, Double scale, Float alpha, Double rotateAngle)
			throws SocketException, IOException;

	/**
	 * Business Card Recognition, wrapper for /ocr_api/business_card
	 * 
	 * @param filePath source file path
	 * @return response object
	 * @throws SocketException when network connection problem occurs
	 * @throws IOException when network I/O problem occurs
	 */
	ResponseVO<BusinessCardOcrResult> businessCardRecognize(String filePath)
			throws SocketException, IOException;

	/**
	 * ID Card Recognition(China), wrapper for /ocr_api/china_id_card_front
	 * 
	 * @param filePath source file path
	 * @return response object
	 * @throws SocketException when network connection problem occurs
	 * @throws IOException when network I/O problem occurs
	 */
	ResponseVO<ChinaIdCardFrontOcrResult> chinaIdCardFrontRecognize(String filePath)
			throws SocketException, IOException;

	/**
	 * China ID Card Back OCR, wrapper for /ocr_api/china_id_card_back
	 * 
	 * @param filePath source file path
	 * @return response object
	 * @throws SocketException when network connection problem occurs
	 * @throws IOException when network I/O problem occurs
	 */
	ResponseVO<ChinaIdCardBackOcrResult> chinaIdCardBackRecognize(String filePath)
			throws SocketException, IOException;

	/**
	 * ID Card Recognition(Hong Kong), wrapper for /ocr_api/hong_kong_id_card
	 * 
	 * @param filePath source file path
	 * @return response object
	 * @throws SocketException when network connection problem occurs
	 * @throws IOException when network I/O problem occurs
	 */
	ResponseVO<HongKongIdCardOcrResult> hongKongIdCardRecognize(String filePath)
			throws SocketException, IOException;

	/**
	 * ID Card Recognition(Singapore), wrapper for /ocr_api/singapore_id_card
	 * 
	 * @param filePath source file path
	 * @return response object
	 * @throws SocketException when network connection problem occurs
	 * @throws IOException when network I/O problem occurs
	 */
	ResponseVO<SingaporeIdCardOcrResult> singaporeIdCardRecognize(String filePath)
			throws SocketException, IOException;

	/**
	 * ID Card Recognition(Thailand), wrapper for /ocr_api/thailand_id_card
	 * 
	 * @param filePath source file path
	 * @return response object
	 * @throws SocketException when network connection problem occurs
	 * @throws IOException when network I/O problem occurs
	 */
	ResponseVO<ThailandIdCardOcrResult> thailandIdCardRecognize(String filePath)
			throws SocketException, IOException;

	/**
	 * ID Card Recognition(Indonesia), wrapper for /ocr_api/indonesia_id_card
	 * 
	 * @param filePath source file path
	 * @return response object
	 * @throws SocketException when network connection problem occurs
	 * @throws IOException when network I/O problem occurs
	 */
	ResponseVO<IndonesiaIdCardOcrResult> indonesiaIdCardRecognize(String filePath)
			throws SocketException, IOException;

	/**
	 * Driving Licenses Recognition(China), wrapper for
	 * /ocr_api/china_driving_licence
	 * 
	 * @param filePath source file path
	 * @return response object
	 * @throws SocketException when network connection problem occurs
	 * @throws IOException when network I/O problem occurs
	 */
	ResponseVO<ChinaDrivingLicenceOcrResult> chinaDrivingLicenceRecognize(String filePath)
			throws SocketException, IOException;

	/**
	 * Driving Licenses Recognition(Singapore), wrapper for
	 * /ocr_api/singapore_driving_licence
	 * 
	 * @param filePath source file path
	 * @return response object
	 * @throws SocketException when network connection problem occurs
	 * @throws IOException when network I/O problem occurs
	 */
	ResponseVO<SingaporeDrivingLicenceOcrResult> singaporeDrivingLicenceRecognize(
			String filePath) throws SocketException, IOException;

	/**
	 * Passport Recognition, wrapper for /ocr_api/passport
	 * 
	 * @param filePath source file path
	 * @return response object
	 * @throws SocketException when network connection problem occurs
	 * @throws IOException when network I/O problem occurs
	 */
	ResponseVO<GeneralPassportOcrResult> passportRecognize(String filePath)
			throws SocketException, IOException;

	/**
	 * Contour Recognize and Crop, wrapper for
	 * /recognize_api/contour_recognize_and_capture
	 * 
	 * @param imagePath image file path
	 * @return response object
	 * @throws SocketException when network connection problem occurs
	 * @throws IOException when network I/O problem occurs
	 */
	ResponseVO<ContourRecognizeAndCropRespVO> contourRecognizeAndCrop(String imagePath)
			throws SocketException, IOException;

	/**
	 * Face Image Verfication Comparing, wrapper for
	 * /identity_api/face_image_verification
	 * 
	 * @param firstImagePath the path of the first image
	 * @param secondImagePath the path of the second image
	 * @param autoRotate whether rotate the image automatically
	 * @return response object
	 * @throws SocketException when network connection problem occurs
	 * @throws IOException when network I/O problem occurs
	 */
	ResponseVO<VerificationRespVo> faceImageVerification(String firstImagePath,
			String secondImagePath, EYesOrNo autoRotate) throws SocketException, IOException;

	/**
	 * Silent Image Verfication Comparing, wrapper for
	 * /identity_api/silent_image_verification
	 * 
	 * @param videoFilePath video file path
	 * @param imageFilePath image file path
	 * @param autoRotate whether rotate the image automatically
	 * @param returnImage whether return image snap from video after passing the detection
	 * @return response object
	 * @throws SocketException when network connection problem occurs
	 * @throws IOException when network I/O problem occurs
	 */
	ResponseVO<SilentImageVerificationRespVo> silentImageVerification(String videoFilePath,
			String imageFilePath, EYesOrNo autoRotate, EYesOrNo returnImage)
			throws SocketException, IOException;

	/**
	 * Passport MRZ OCR, wrapper for /ocr_api/passport_mrz_recognize
	 * 
	 * @param filePath source file path
	 * @return response object
	 * @throws SocketException when network connection problem occurs
	 * @throws IOException when network I/O problem occurs
	 */
	ResponseVO<GeneralPassportOcrResult> passportMrzRecognize(String filePath)
			throws SocketException, IOException;
}
