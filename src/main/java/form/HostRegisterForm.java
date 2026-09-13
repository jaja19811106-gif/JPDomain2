package form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ホスト登録フォームBean
 */
public class HostRegisterForm {

    private static final Logger log = LoggerFactory.getLogger(HostRegisterForm.class);

    private String hostName;  // FQDN 例: ns1.example.co.jp
    private String ipv4;      // 例: 192.168.1.1
    private String ipv6;      // 例: 2001:db8::1

    public String getHostName() {
        log.debug("getHostName() start");
        return hostName;
    }
    public void setHostName(String hostName) {
        log.debug("setHostName() start: {}", hostName);
        this.hostName = hostName;
    }

    public String getIpv4() {
        log.debug("getIpv4() start");
        return ipv4;
    }
    public void setIpv4(String ipv4) {
        log.debug("setIpv4() start: {}", ipv4);
        this.ipv4 = ipv4;
    }

    public String getIpv6() {
        log.debug("getIpv6() start");
        return ipv6;
    }
    public void setIpv6(String ipv6) {
        log.debug("setIpv6() start: {}", ipv6);
        this.ipv6 = ipv6;
    }
}