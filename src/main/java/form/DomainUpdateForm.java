package form;

/**
 * ドメイン情報変更フォームBean
 */
public class DomainUpdateForm {

    private String domainName;    // 変更対象ドメイン名
    private String registrant;    // contact_id
    private String adminContact;  // contact_id
    private String techContact;   // contact_id
    private String ns1;           // host_name
    private String ns2;           // host_name

    public String getDomainName()   { return domainName; }
    public void setDomainName(String domainName) { this.domainName = domainName; }

    public String getRegistrant()   { return registrant; }
    public void setRegistrant(String registrant) { this.registrant = registrant; }

    public String getAdminContact() { return adminContact; }
    public void setAdminContact(String adminContact) { this.adminContact = adminContact; }

    public String getTechContact()  { return techContact; }
    public void setTechContact(String techContact) { this.techContact = techContact; }

    public String getNs1()          { return ns1; }
    public void setNs1(String ns1)  { this.ns1 = ns1; }

    public String getNs2()          { return ns2; }
    public void setNs2(String ns2)  { this.ns2 = ns2; }
}