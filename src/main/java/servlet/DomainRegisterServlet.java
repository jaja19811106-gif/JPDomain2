package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.DomainDao;
import dto.DomainDto;
import epp.EPPClientMock;
import util.DomainMapper;

@WebServlet("/domainRegister")
public class DomainRegisterServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(DomainRegisterServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("domainRegister.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String domain = request.getParameter("domainName");
        String period = request.getParameter("period");
        String ns1 = request.getParameter("ns1");
        String ns2 = request.getParameter("ns2");
        String registrant = request.getParameter("registrant");

        DomainDto dto = DomainMapper.fromRequest(request);
        DomainDao dao = new DomainDao();
        try {
            dao.insert(dto);
        } catch (SQLException e) {
            log.error("DB insert error", e);
            request.setAttribute("eppResponse", "DBエラーが発生しました");
            request.getRequestDispatcher("domainRegisterResult.jsp").forward(request, response);
            return;
        }

        log.info("domainRegister called");
        log.debug("domain={}, period={}, ns1={}, ns2={}", domain, period, ns1, ns2);

        String xml =
                "<epp xmlns='urn:ietf:params:xml:ns:epp-1.0' "
                + "     xmlns:domain='urn:ietf:params:xml:ns:domain-1.0'>"
                + "<command>"
                + "  <create>"
                + "    <domain:create>"
                + "      <domain:name>" + domain + "</domain:name>"
                + "      <domain:period unit='y'>" + period + "</domain:period>"
                + "      <domain:registrant>" + registrant + "</domain:registrant>"
                + "      <domain:ns>"
                + "        <domain:hostObj>" + ns1 + "</domain:hostObj>"
                + (ns2 != null && !ns2.isEmpty()
                        ? "        <domain:hostObj>" + ns2 + "</domain:hostObj>"
                        : "")
                + "      </domain:ns>"
                + "    </domain:create>"
                + "  </create>"
                + "  <clTRID>JP-" + System.currentTimeMillis() + "</clTRID>"
                + "</command>"
                + "</epp>";

        try {
            EPPClientMock client = new EPPClientMock();
            String res = client.send(xml);
            request.setAttribute("eppResponse", res);
        } catch (Exception e) {
            log.error("EPP send error", e);
            request.setAttribute("eppResponse", "エラー: " + e.getMessage());
        }

        request.getRequestDispatcher("domainRegisterResult.jsp").forward(request, response);
    }
}