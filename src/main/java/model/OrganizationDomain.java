package model;

public class OrganizationDomain {

    private int id;  // ★ 追加
    private String corporateNumber;
    private String domainName;
    private String attributeType;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCorporateNumber() { return corporateNumber; }
    public void setCorporateNumber(String corporateNumber) { this.corporateNumber = corporateNumber; }

    public String getDomainName() { return domainName; }
    public void setDomainName(String domainName) { this.domainName = domainName; }

    public String getAttributeType() { return attributeType; }
    public void setAttributeType(String attributeType) { this.attributeType = attributeType; }
}