package model;

public class ProviderAccount {

    private String providerNo;
    private String loginId;
    private String name;

    public ProviderAccount() {
    }

    public ProviderAccount(String providerNo, String loginId, String name) {
        this.providerNo = providerNo;
        this.loginId = loginId;
        this.name = name;
    }

    public String getProviderNo() {
        return providerNo;
    }

    public void setProviderNo(String providerNo) {
        this.providerNo = providerNo;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}