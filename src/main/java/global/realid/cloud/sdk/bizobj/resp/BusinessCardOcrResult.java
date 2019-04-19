package global.realid.cloud.sdk.bizobj.resp;

import java.util.List;

/**
 * response result for business card OCR
 * @author Benjamin
 */

public class BusinessCardOcrResult {
	private List<String> name;
	private List<String> position;
	private List<String> department;
	private List<String> mobileNumber;
	private List<String> companyName;
	private List<String> address;
	private List<String> telNumber;
	private List<String> faxNumber;
	private List<String> email;
	private List<String> website;
	private List<String> postalCode;

	public List<String> getName() {
		return name;
	}

	public void setName(List<String> name) {
		this.name = name;
	}

	public List<String> getPosition() {
		return position;
	}

	public void setPosition(List<String> position) {
		this.position = position;
	}

	public List<String> getDepartment() {
		return department;
	}

	public void setDepartment(List<String> department) {
		this.department = department;
	}

	public List<String> getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(List<String> mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public List<String> getCompanyName() {
		return companyName;
	}

	public void setCompanyName(List<String> companyName) {
		this.companyName = companyName;
	}

	public List<String> getAddress() {
		return address;
	}

	public void setAddress(List<String> address) {
		this.address = address;
	}

	public List<String> getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(List<String> telNumber) {
		this.telNumber = telNumber;
	}

	public List<String> getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(List<String> faxNumber) {
		this.faxNumber = faxNumber;
	}

	public List<String> getEmail() {
		return email;
	}

	public void setEmail(List<String> email) {
		this.email = email;
	}

	public List<String> getWebsite() {
		return website;
	}

	public void setWebsite(List<String> website) {
		this.website = website;
	}

	public List<String> getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(List<String> postalCode) {
		this.postalCode = postalCode;
	}

}
