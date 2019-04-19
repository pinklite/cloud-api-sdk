package global.realid.cloud.sdk.bizobj.resp;

/**
 * response result for China Driving licence OCR
 * @author Benjamin
 */

public class ChinaDrivingLicenceOcrResult {
	private String cardNumber;
	private String name;
	private String gender;
	private String residentAddress;
	private String dateOfBirth;
	private String initialIssueDate;
	private String permitVehicleModel;
	private String dateOfIssue;
	private String validPeriod;
	private String dateOfExpiry;
	private String portrait;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getResidentAddress() {
		return residentAddress;
	}

	public void setResidentAddress(String residentAddress) {
		this.residentAddress = residentAddress;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getInitialIssueDate() {
		return initialIssueDate;
	}

	public void setInitialIssueDate(String initialIssueDate) {
		this.initialIssueDate = initialIssueDate;
	}

	public String getPermitVehicleModel() {
		return permitVehicleModel;
	}

	public void setPermitVehicleModel(String permitVehicleModel) {
		this.permitVehicleModel = permitVehicleModel;
	}

	public String getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public String getValidPeriod() {
		return validPeriod;
	}

	public void setValidPeriod(String validPeriod) {
		this.validPeriod = validPeriod;
	}

	public String getDateOfExpiry() {
		return dateOfExpiry;
	}

	public void setDateOfExpiry(String dateOfExpiry) {
		this.dateOfExpiry = dateOfExpiry;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

}
