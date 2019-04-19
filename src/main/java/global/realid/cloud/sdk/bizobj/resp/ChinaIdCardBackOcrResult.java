package global.realid.cloud.sdk.bizobj.resp;

/**
 * response result for back side of China ID card OCR
 * @author Benjamin
 */

public class ChinaIdCardBackOcrResult {
	private String issueAuthority;
	private String limitsToValidity;
	private String dateOfIssue;
	private String dateOfExpiry;

	public String getIssueAuthority() {
		return issueAuthority;
	}

	public void setIssueAuthority(String issueAuthority) {
		this.issueAuthority = issueAuthority;
	}

	public String getLimitsToValidity() {
		return limitsToValidity;
	}

	public void setLimitsToValidity(String limitsToValidity) {
		this.limitsToValidity = limitsToValidity;
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

}
