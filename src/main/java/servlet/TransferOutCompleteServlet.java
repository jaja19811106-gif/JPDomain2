package servlet;

import constants.SessionKeys;
import dto.DomainDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Transfer Out 完了Servlet
 * URL: /transferOutComplete
 */
@WebServlet("/transferOutComplete")
public class TransferOutCompleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        DomainDto domain = (DomainDto) req.getSession().getAttribute(SessionKeys.TRANSFER_OUT_DOMAIN);
        if (domain == null) {
            resp.sendRedirect(req.getContextPath() + "/transferOutSearch");
            return;
        }

        req.setAttribute("domain", domain);

        // セッションクリア
        SessionKeys.clearTransferOutSession(req.getSession());

        req.getRequestDispatcher("/transferOutComplete.jsp").forward(req, resp);
    }
}
