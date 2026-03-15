package model;

import java.sql.Timestamp;

public class OrganizationDomain {

    private int       id;
    private String    corporateNumber;
    private String    domainName;
    private String    attributeType;
    private String    authCode;
    private Timestamp authCodeExpiresAt;
    private int       status;

    // IPアドレス範囲1〜5
    private String ip1From; private String ip1To;
    private String ip2From; private String ip2To;
    private String ip3From; private String ip3To;
    private String ip4From; private String ip4To;
    private String ip5From; private String ip5To;

    // ホストアドレス1〜5
    private String host1;
    private String host2;
    private String host3;
    private String host4;
    private String host5;

    // ステータスEnum（内部クラス）
    public enum Status {
        ACTIVE(1, "active"),
        DELETED(2, "delete"),
        REGISTERED(3, "REGISTERED"),
        RESERVED(4, "RESERVED"),
        PENDING_APPLICATION(5, "PENDING_APPLICATION"),
        DOMAIN_ACTIVE(6, "DOMAIN_ACTIVE"),
        TO_BE_SUSPENDED(7, "TO_BE_SUSPENDED"),
        SUSPENDED(8, "SUSPENDED"),
        TO_BE_DELETED(9, "TO_BE_DELETED"),
        DOMAIN_DELETED(10, "DOMAIN_DELETED");

        private final Integer code;
        private final String  label;

        Status(Integer code, String label) {
            this.code  = code;
            this.label = label;
        }

        public Integer getCode()  { return code; }
        public String  getLabel() { return label; }

        public static Status fromCode(Integer code) {
            for (Status status : Status.values()) {
                if (status.code.equals(code)) return status;
            }
            throw new IllegalArgumentException("Invalid status code: " + code);
        }
    }

    public int    getId()                          { return id; }
    public void   setId(int id)                    { this.id = id; }

    public String getCorporateNumber()             { return corporateNumber; }
    public void   setCorporateNumber(String v)     { this.corporateNumber = v; }

    public String getDomainName()                  { return domainName; }
    public void   setDomainName(String v)          { this.domainName = v; }

    public String getAttributeType()               { return attributeType; }
    public void   setAttributeType(String v)       { this.attributeType = v; }

    public String getAuthCode()                    { return authCode; }
    public void   setAuthCode(String v)            { this.authCode = v; }

    public Timestamp getAuthCodeExpiresAt()        { return authCodeExpiresAt; }
    public void      setAuthCodeExpiresAt(Timestamp v) { this.authCodeExpiresAt = v; }

    public int  getStatus()                        { return status; }
    public void setStatus(int status)              { this.status = status; }

    public Status getStatusEnum()                  { return Status.fromCode(this.status); }
    public void   setStatusEnum(Status status)     { this.status = status.getCode(); }

    // IP Getters and Setters
    public String getIp1From() { return ip1From; } public void setIp1From(String v) { this.ip1From = v; }
    public String getIp1To()   { return ip1To; }   public void setIp1To(String v)   { this.ip1To   = v; }
    public String getIp2From() { return ip2From; } public void setIp2From(String v) { this.ip2From = v; }
    public String getIp2To()   { return ip2To; }   public void setIp2To(String v)   { this.ip2To   = v; }
    public String getIp3From() { return ip3From; } public void setIp3From(String v) { this.ip3From = v; }
    public String getIp3To()   { return ip3To; }   public void setIp3To(String v)   { this.ip3To   = v; }
    public String getIp4From() { return ip4From; } public void setIp4From(String v) { this.ip4From = v; }
    public String getIp4To()   { return ip4To; }   public void setIp4To(String v)   { this.ip4To   = v; }
    public String getIp5From() { return ip5From; } public void setIp5From(String v) { this.ip5From = v; }
    public String getIp5To()   { return ip5To; }   public void setIp5To(String v)   { this.ip5To   = v; }

    // Host Getters and Setters
    public String getHost1() { return host1; } public void setHost1(String v) { this.host1 = v; }
    public String getHost2() { return host2; } public void setHost2(String v) { this.host2 = v; }
    public String getHost3() { return host3; } public void setHost3(String v) { this.host3 = v; }
    public String getHost4() { return host4; } public void setHost4(String v) { this.host4 = v; }
    public String getHost5() { return host5; } public void setHost5(String v) { this.host5 = v; }
}
