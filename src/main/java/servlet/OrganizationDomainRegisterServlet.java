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

        String corporateNumber = req.getParameter("corporateNumber");
        String attributeType   = req.getParameter("attributeType");
        String domainName      = req.getParameter("domainName");

        if (corporateNumber == null || corporateNumber.isEmpty()
                || attributeType == null || attributeType.isEmpty()
                || domainName == null || domainName.isEmpty()) {

            req.setAttribute("errorMessage", "入力に不備があります。");
            req.getRequestDispatcher("/domainRegister.jsp").forward(req, resp);
            return;
        }

        OrganizationDomainDao dao = new OrganizationDomainDao();

        try {
            // ① 登録
            OrganizationDomain domain = new OrganizationDomain();
            domain.setCorporateNumber(corporateNumber);
            domain.setAttributeType(attributeType);
            domain.setDomainName(domainName);

            int newId = dao.insert(domain);

            // ② 登録したデータを取得（auth_code と status を含む）
            OrganizationDomain saved = dao.findById(newId);

            // ③ JSP に渡す
            req.setAttribute("domain", saved);

            req.getRequestDispatcher("/organizationDomainRegisterResult.jsp")
               .forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}