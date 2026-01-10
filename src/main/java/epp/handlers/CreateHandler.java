package epp.handlers;

import epp.EPPHandler;

public class CreateHandler implements EPPHandler {

    @Override
    public String handle(String xml) {

        return
            "<epp xmlns='urn:ietf:params:xml:ns:epp-1.0'>"
          + "  <response>"
          + "    <result code='1000'>"
          + "      <msg>Domain created (mock)</msg>"
          + "    </result>"
          + "  </response>"
          + "</epp>";
    }
}