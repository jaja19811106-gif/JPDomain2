package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.OrganizationDomainDao;

@WebServlet("/domainRegisterCheck")
public class DomainRegisterCheckServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String corporateNumber = req.getParameter("corporateNumber");
        String attributeType   = req.getParameter("attributeType");

        OrganizationDomainDao dao = new OrganizationDomainDao();

        try {
            boolean exists = dao.existsActiveDomainForOrg(corporateNumber, attributeType);

            if (exists) {
                req.setAttribute("errorMessage",
                        "この組織はすでに " + attributeType + " を保持しています。");

                req.getRequestDispatcher("/domainRegisterInput.jsp").forward(req, resp);
                return;
            }

            // チェックOK → 本来の登録画面へ遷移
            req.getRequestDispatcher("/domainRegisterConfirm.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
