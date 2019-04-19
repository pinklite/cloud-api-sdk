package global.realid.cloud.sdk.bizobj.resp;

/**
 * response result for Hongkong ID card OCR
 * @author Benjamin
 */

public class HongKongIdCardOcrResult {
	private String nameInChinese;
	private String nameInEnglish;
	private String gender;
	private String dateOfBirth;
	private String dateOfIssue;
	private String identityCardNumber;
	private String symbols;
	private String nameInCcc;
	private String nameFromCcc;
	private String nameFromCccCorrected;
	private String portrait;

	public String getNameInChinese() {
		return nameInChinese;
	}

	public void setNameInChinese(String nameInChinese) {
		this.nameInChinese = nameInChinese;
	}

	public String getNameInEnglish() {
		return nameInEnglish;
	}

	public void setNameInEnglish(String nameInEnglish) {
		this.nameInEnglish = nameInEnglish;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public String getIdentityCardNumber() {
		return identityCardNumber;
	}

	public void setIdentityCardNumber(String identityCardNumber) {
		this.identityCardNumber = identityCardNumber;
	}

	public String getSymbols() {
		return symbols;
	}

	public void setSymbols(String symbols) {
		this.symbols = symbols;
	}

	public String getNameInCcc() {
		return nameInCcc;
	}

	public void setNameInCcc(String nameInCcc) {
		this.nameInCcc = nameInCcc;
	}

	public String getNameFromCcc() {
		return nameFromCcc;
	}

	public void setNameFromCcc(String nameFromCcc) {
		this.nameFromCcc = nameFromCcc;
	}

	public String getNameFromCccCorrected() {
		return nameFromCccCorrected;
	}

	public void setNameFromCccCorrected(String nameFromCccCorrected) {
		this.nameFromCccCorrected = nameFromCccCorrected;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

}
