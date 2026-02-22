package form;

/**
 * コンタクト登録フォームBean
 */
public class ContactRegisterForm {

    private String contactType;   // registrant / admin / tech
    private String lang;          // ja / en
    private String name;
    private String organization;
    private String postalCode;
    private String sp;            // 都道府県（state/province）
    private String city;
    private String street;
    private String cc;            // 国コード
    private String voice;         // 電話番号
    private String fax;
    private String email;

    // --- Getters / Setters ---

    public String getContactType() { return contactType; }
    public void setContactType(String contactType) { this.contactType = contactType; }

    public String getLang() { return lang; }
    public void setLang(String lang) { this.lang = lang; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getOrganization() { return organization; }
    public void setOrganization(String organization) { this.organization = organization; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getSp() { return sp; }
    public void setSp(String sp) { this.sp = sp; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCc() { return cc; }
    public void setCc(String cc) { this.cc = cc; }

    public String getVoice() { return voice; }
    public void setVoice(String voice) { this.voice = voice; }

    public String getFax() { return fax; }
    public void setFax(String fax) { this.fax = fax; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}