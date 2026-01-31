package model;

public class OrganizationDomain {

    private int id;  // ★ 追加
    private String corporateNumber;
    private String domainName;
    private String attributeType;
    public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	private String authCode;
    private String status;
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCorporateNumber() { return corporateNumber; }
    public void setCorporateNumber(String corporateNumber) { this.corporateNumber = corporateNumber; }

    public String getDomainName() { return domainName; }
    public void setDomainName(String domainName) { this.domainName = domainName; }

    public String getAttributeType() { return attributeType; }
    public void setAttributeType(String attributeType) { this.attributeType = attributeType; }
}