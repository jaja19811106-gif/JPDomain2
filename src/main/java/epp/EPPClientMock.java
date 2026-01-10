package epp;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import epp.handlers.CreateHandler;
import epp.handlers.InfoHandler;

public class EPPClientMock {

    private static final Logger log = LoggerFactory.getLogger(EPPClientMock.class);

    private final Map<String, EPPHandler> handlers = new HashMap<>();

    public EPPClientMock() {
        handlers.put("create", new CreateHandler());
        handlers.put("info", new InfoHandler());
        // ★ update, delete もここに追加するだけ
    }

    public String send(String xml) {

        log.info("Mock EPP received XML");

        String pretty = xml.replace("><", ">\n<");
        log.debug("XML = \n{}", pretty);

        // ★ XML からコマンド名を抽出
        String command = extractCommand(xml);

        EPPHandler handler = handlers.get(command);

        if (handler == null) {
            return error("Unsupported command: " + command);
        }

        return handler.handle(xml);
    }

    private String extractCommand(String xml) {
        if (xml.contains("<create>")) return "create";
        if (xml.contains("<info>")) return "info";
        if (xml.contains("<update>")) return "update";
        if (xml.contains("<delete>")) return "delete";
        return "unknown";
    }

    private String error(String msg) {
        return
            "<epp><response>"
          + "<result code='2000'><msg>" + msg + "</msg></result>"
          + "</response></epp>";
    }
}