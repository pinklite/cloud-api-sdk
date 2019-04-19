package global.realid.cloud.sdk.enums;

import global.realid.cloud.sdk.bizobj.base.ResponseVO;
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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Real ID Cloud API enumeration
 * @author Benjamin
 */
public enum ERealIdCloudApis {

	/** Portrait Similarity between Present and Certificate */
	A10003("/identity_api/certificate_present_image_verification", new TypeRef<ResponseVO<VerificationRespVo>>(){}),
	
	/** Face Liveness Detection */
	A10004("/liveness_api/silent_detection", new TypeRef<ResponseVO<VerificationRespVo>>(){}),
	
	/** Single File Watermarking */
	A10005("/watermark_api/add_watermark_to_file", new TypeRef<ResponseVO<SingleFileWatermarkRespVO>>(){}),
	
	/** Batch File Watermarking */
	A10006("/watermark_api/add_watermark_batch", new TypeRef<ResponseVO<BatchFileWatermarkRespVO>>(){}),
	
	/** Business Card Recognition */
	A10007("/ocr_api/business_card", new TypeRef<ResponseVO<BusinessCardOcrResult>>(){}),
	
	/** ID Card Recognition(China) */
	A10008("/ocr_api/china_id_card_front", new TypeRef<ResponseVO<ChinaIdCardFrontOcrResult>>(){}),
	
	/** China ID Card Back OCR */
	A10009("/ocr_api/china_id_card_back", new TypeRef<ResponseVO<ChinaIdCardBackOcrResult>>(){}),
	
	/** ID Card Recognition(Hong Kong) */
	A10010("/ocr_api/hong_kong_id_card", new TypeRef<ResponseVO<HongKongIdCardOcrResult>>(){}),
	
	/** ID Card Recognition(Singapore) */
	A10011("/ocr_api/singapore_id_card", new TypeRef<ResponseVO<SingaporeIdCardOcrResult>>(){}),
	
	/** ID Card Recognition(Thailand) */
	A10012("/ocr_api/thailand_id_card", new TypeRef<ResponseVO<ThailandIdCardOcrResult>>(){}),
	
	/** ID Card Recognition(Indonesia) */
	A10013("/ocr_api/indonesia_id_card", new TypeRef<ResponseVO<IndonesiaIdCardOcrResult>>(){}),
	
	/** Driving Licenses Recognition(China) */
	A10019("/ocr_api/china_driving_licence", new TypeRef<ResponseVO<ChinaDrivingLicenceOcrResult>>(){}),
	
	/** Driving Licenses Recognition(Singapore) */
	A10020("/ocr_api/singapore_driving_licence", new TypeRef<ResponseVO<SingaporeDrivingLicenceOcrResult>>(){}),
	
	/** Passport Recognition */
	A10021("/ocr_api/passport", new TypeRef<ResponseVO<GeneralPassportOcrResult>>(){}),
	
	/** Contour Recognize and Crop */
	A10022("/recognize_api/contour_recognize_and_capture", new TypeRef<ResponseVO<ContourRecognizeAndCropRespVO>>(){}),
	
	/** Face Image Verfication Comparing */
	A10023("/identity_api/face_image_verification", new TypeRef<ResponseVO<VerificationRespVo>>(){}),
	
	/** Silent Image Verfication Comparing */
	A10024("/identity_api/silent_image_verification", new TypeRef<ResponseVO<SilentImageVerificationRespVo>>(){}),
	
	/** Passport MRZ OCR */
	A10025("/ocr_api/passport_mrz_recognize", new TypeRef<ResponseVO<GeneralPassportOcrResult>>(){}),
	
	;
	
	
	/**
	 * get URL path of this API
	 * @return the URL path
	 */
	public String getUrlPath() {
		return urlPath;
	}
	
	/**
	 * get full URL for this API, by concatenating <code>urlPath</code> after <code>baseUrl</code>
	 * @param baseUrl the base URL of server
	 * @return full URL for this API
	 */
	public String getFullApiUrl(String baseUrl) {
		return baseUrl + urlPath;
	}
	
	/**
	 * get {@link Type type} of response of this API 
	 * @return type of response
	 */
	public Type getResponseType() {
		return apiResponseType;
	}
	
	/**
	 * get ID of this API in Real ID Cloud System
	 * @return  ID of this API
	 */
	public Integer getApiId() {
		return Integer.valueOf(name().substring(1));
	}

	
	private String urlPath;
	private Type apiResponseType;
	
	private ERealIdCloudApis(String urlPath, TypeRef<?> typeRef) {
		this.urlPath = urlPath;
		this.apiResponseType = typeRef.type;
	}
	
	
	/**
	 * helper class for constructing {@link ERealIdCloudApis}
	 * @author Benjamin
	 */
	private static class TypeRef<T> {
		private Type type;
		
		public TypeRef() {
			Type superClass = getClass().getGenericSuperclass();
			type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
		}
	}
}
