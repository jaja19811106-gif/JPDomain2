package epp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class EPPClient {

    private SSLSocket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public void connect(String host, int port) throws Exception {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        socket = (SSLSocket) factory.createSocket(host, port);
        socket.startHandshake();

        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

        readFrame(); // greeting
    }

    public void login(String clID, String password) throws Exception {
        String xml =
                "<epp xmlns='urn:ietf:params:xml:ns:epp-1.0'>"
                        + "<command>"
                        + "<login>"
                        + "<clID>" + clID + "</clID>"
                        + "<pw>" + password + "</pw>"
                        + "</login>"
                        + "<clTRID>LOGIN-" + System.currentTimeMillis() + "</clTRID>"
                        + "</command>"
                        + "</epp>";

        writeFrame(xml);
        readFrame();
    }

    public void logout() throws Exception {
        String xml =
                "<epp xmlns='urn:ietf:params:xml:ns:epp-1.0'>"
                        + "<command>"
                        + "<logout/>"
                        + "<clTRID>LOGOUT-" + System.currentTimeMillis() + "</clTRID>"
                        + "</command>"
                        + "</epp>";

        writeFrame(xml);
        readFrame();
    }

    public void disconnect() throws Exception {
        socket.close();
    }

    public void writeFrame(String xml) throws Exception {
        byte[] data = xml.getBytes("UTF-8");
        int len = data.length + 4;

        writer.write((char) ((len >> 24) & 0xFF));
        writer.write((char) ((len >> 16) & 0xFF));
        writer.write((char) ((len >> 8) & 0xFF));
        writer.write((char) (len & 0xFF));

        writer.write(xml);
        writer.flush();
    }

    public String readFrame() throws Exception {
        int b1 = reader.read();
        int b2 = reader.read();
        int b3 = reader.read();
        int b4 = reader.read();

        int size = ((b1 & 0xFF) << 24)
                | ((b2 & 0xFF) << 16)
                | ((b3 & 0xFF) << 8)
                | (b4 & 0xFF);

        char[] buf = new char[size - 4];
        reader.read(buf);

        return new String(buf);
    }
}