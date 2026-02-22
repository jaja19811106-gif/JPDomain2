package form;

/**
 * ホスト登録フォームBean
 */
public class HostRegisterForm {

    private String hostName;  // FQDN 例: ns1.example.co.jp
    private String ipv4;      // 例: 192.168.1.1
    private String ipv6;      // 例: 2001:db8::1

    public String getHostName() { return hostName; }
    public void setHostName(String hostName) { this.hostName = hostName; }

    public String getIpv4() { return ipv4; }
    public void setIpv4(String ipv4) { this.ipv4 = ipv4; }

    public String getIpv6() { return ipv6; }
    public void setIpv6(String ipv6) { this.ipv6 = ipv6; }
}