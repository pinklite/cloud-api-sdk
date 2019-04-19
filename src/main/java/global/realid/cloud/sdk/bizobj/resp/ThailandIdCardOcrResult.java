package global.realid.cloud.sdk.bizobj.resp;

/**
 * response result for Thailand ID card OCR
 * @author Benjamin
 */

public class ThailandIdCardOcrResult {
	private String lastName;
	private String firstName;
	private String dateOfBirth;
	private String dateOfIssue;
	private String dateOfExpiry;
	private String identityNumber;
	private String fullNameInThai;
	private String dateOfBirthInThai;
	private String portrait;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public String getDateOfExpiry() {
		return dateOfExpiry;
	}

	public void setDateOfExpiry(String dateOfExpiry) {
		this.dateOfExpiry = dateOfExpiry;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	public String getFullNameInThai() {
		return fullNameInThai;
	}

	public void setFullNameInThai(String fullNameInThai) {
		this.fullNameInThai = fullNameInThai;
	}

	public String getDateOfBirthInThai() {
		return dateOfBirthInThai;
	}

	public void setDateOfBirthInThai(String dateOfBirthInThai) {
		this.dateOfBirthInThai = dateOfBirthInThai;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

}
