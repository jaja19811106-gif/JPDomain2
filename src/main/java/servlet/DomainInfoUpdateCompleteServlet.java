package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ドメイン情報変更 完了Servlet
 * URL: /domainInfoUpdateComplete
 */
@WebServlet("/domainInfoUpdateComplete")
public class DomainInfoUpdateCompleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String domainName = (String) req.getSession().getAttribute("updatedDomainName");
        if (domainName == null) {
            resp.sendRedirect(req.getContextPath() + "/domainUpdateSearch");
            return;
        }

        req.getSession().removeAttribute("updatedDomainName");
        req.setAttribute("domainName", domainName);
        req.getRequestDispatcher("/domainInfoUpdateComplete.jsp").forward(req, resp);
    }
}