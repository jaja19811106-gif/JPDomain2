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

@WebServlet("/domain2Update")
public class Domain2UpdateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(req.getParameter("id"));
        String corporateNumber = req.getParameter("corporateNumber");
        String attributeType = req.getParameter("attributeType");
        String domainName = req.getParameter("domainName");

        OrganizationDomain domain = new OrganizationDomain();
        domain.setId(id);
        domain.setCorporateNumber(corporateNumber);
        domain.setAttributeType(attributeType);
        domain.setDomainName(domainName);

        OrganizationDomainDao dao = new OrganizationDomainDao();

        try {
            dao.update(domain);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        req.getRequestDispatcher("/domain2UpdateResult.jsp").forward(req, resp);
    }
}