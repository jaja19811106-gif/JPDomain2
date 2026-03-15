package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.OrganizationDomainDao;
import model.OrganizationDomain;

@WebServlet("/organizationDomainRegister")
public class OrganizationDomainRegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String corporateNumber = req.getParameter("corporateNumber");
        String attributeType   = req.getParameter("attributeType");
        String domainName      = req.getParameter("domainName");

        if (isBlank(corporateNumber) || isBlank(attributeType) || isBlank(domainName)) {
            req.setAttribute("errorMessage", "入力に不備があります。");
            req.getRequestDispatcher("/domainRegister.jsp").forward(req, resp);
            return;
        }

        OrganizationDomain domain = new OrganizationDomain();
        domain.setCorporateNumber(corporateNumber);
        domain.setAttributeType(attributeType);
        domain.setDomainName(domainName);

        // ホストアドレスをセット
        domain.setHost1(req.getParameter("host1"));
        domain.setHost2(req.getParameter("host2"));
        domain.setHost3(req.getParameter("host3"));
        domain.setHost4(req.getParameter("host4"));
        domain.setHost5(req.getParameter("host5"));

        // IPアドレス範囲をセット
        domain.setIp1From(req.getParameter("ip1_from")); domain.setIp1To(req.getParameter("ip1_to"));
        domain.setIp2From(req.getParameter("ip2_from")); domain.setIp2To(req.getParameter("ip2_to"));
        domain.setIp3From(req.getParameter("ip3_from")); domain.setIp3To(req.getParameter("ip3_to"));
        domain.setIp4From(req.getParameter("ip4_from")); domain.setIp4To(req.getParameter("ip4_to"));
        domain.setIp5From(req.getParameter("ip5_from")); domain.setIp5To(req.getParameter("ip5_to"));

        OrganizationDomainDao dao = new OrganizationDomainDao();
        try {
            int newId = dao.insert(domain);
            OrganizationDomain saved = dao.findById(newId);
            req.setAttribute("domain", saved);
            req.getRequestDispatcher("/organizationDomainRegisterResult.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
