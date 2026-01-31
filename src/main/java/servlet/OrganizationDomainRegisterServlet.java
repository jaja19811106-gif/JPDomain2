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

        // サーバー側バリデーション（必須）
        if (corporateNumber == null || corporateNumber.isEmpty()
                || attributeType == null || attributeType.isEmpty()
                || domainName == null || domainName.isEmpty()) {

            req.setAttribute("errorMessage", "入力に不備があります。");
            req.getRequestDispatcher("/domainRegister.jsp").forward(req, resp);
            return;
        }

        OrganizationDomainDao dao = new OrganizationDomainDao();

        try {
            OrganizationDomain domain = new OrganizationDomain();
            domain.setCorporateNumber(corporateNumber);
            domain.setAttributeType(attributeType);
            domain.setDomainName(domainName);

            dao.insert(domain);

            req.setAttribute("message", "登録が完了しました。");
            req.getRequestDispatcher("/organizationDomainRegisterResult.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}