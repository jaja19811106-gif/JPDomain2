package epp.handlers;

import epp.EPPHandler;

public class InfoHandler implements EPPHandler {

    @Override
    public String handle(String xml) {

        // ★ 本物っぽい domain:info レスポンス（モック）
        return
            "<epp xmlns='urn:ietf:params:xml:ns:epp-1.0'>"
          + "  <response>"
          + "    <result code='1000'>"
          + "      <msg>Command completed successfully (mock)</msg>"
          + "    </result>"
          + "    <resData>"
          + "      <domain:infData xmlns:domain='urn:ietf:params:xml:ns:domain-1.0'>"
          + "        <domain:name>example.jp</domain:name>"
          + "        <domain:roid>D0000001-JP</domain:roid>"
          + "        <domain:status s='active'/>"
          + "        <domain:ns>"
          + "          <domain:hostObj>ns1.example.jp</domain:hostObj>"
          + "          <domain:hostObj>ns2.example.jp</domain:hostObj>"
          + "        </domain:ns>"
          + "      </domain:infData>"
          + "    </resData>"
          + "  </response>"
          + "</epp>";
    }
}