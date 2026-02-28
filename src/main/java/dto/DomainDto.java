package dto;

import java.sql.Timestamp;

/**
 * ドメインDTO
 */
public class DomainDto {

    private int       id;
    private String    domainName;
    private String    registrant;
    private String    adminContact;
    private String    techContact;
    private String    ns1;
    private String    ns2;
    private int       period;
    private boolean   locked;
    private String    authCode;
    private Timestamp authCodeExpiresAt;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDomainName() { return domainName; }
    public void setDomainName(String domainName) { this.domainName = domainName; }

    public String getRegistrant() { return registrant; }
    public void setRegistrant(String registrant) { this.registrant = registrant; }

    public String getAdminContact() { return adminContact; }
    public void setAdminContact(String adminContact) { this.adminContact = adminContact; }

    public String getTechContact() { return techContact; }
    public void setTechContact(String techContact) { this.techContact = techContact; }

    public String getNs1() { return ns1; }
    public void setNs1(String ns1) { this.ns1 = ns1; }

    public String getNs2() { return ns2; }
    public void setNs2(String ns2) { this.ns2 = ns2; }

    public int getPeriod() { return period; }
    public void setPeriod(int period) { this.period = period; }

    public boolean isLocked() { return locked; }
    public void setLocked(boolean locked) { this.locked = locked; }

    public String getAuthCode() { return authCode; }
    public void setAuthCode(String authCode) { this.authCode = authCode; }

    public Timestamp getAuthCodeExpiresAt() { return authCodeExpiresAt; }
    public void setAuthCodeExpiresAt(Timestamp authCodeExpiresAt) { this.authCodeExpiresAt = authCodeExpiresAt; }
}