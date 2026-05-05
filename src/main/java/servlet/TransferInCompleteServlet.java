package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Transfer In 完了Servlet
 * URL: /transferInComplete
 */
@WebServlet("/transferInComplete")
public class TransferInCompleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String domainName = (String) req.getSession().getAttribute("transferredDomainName");
        if (domainName == null) {
            resp.sendRedirect(req.getContextPath() + "/transferInSearch");
            return;
        }

        req.getSession().removeAttribute("transferredDomainName");
        req.setAttribute("domainName", domainName);
        req.getRequestDispatcher("/transferInComplete.jsp").forward(req, resp);
    }
}
