package servlet;

import constants.SessionKeys;
import form.TransferInForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Transfer In NS入力Servlet
 * URL: /transferInNs
 */
@WebServlet("/transferInNs")
public class TransferInNsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (req.getSession().getAttribute(SessionKeys.TRANSFER_IN_FORM) == null) {
            resp.sendRedirect(req.getContextPath() + "/transferInSearch");
            return;
        }
        req.getRequestDispatcher("/transferInNs.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        TransferInForm form = (TransferInForm) req.getSession().getAttribute(SessionKeys.TRANSFER_IN_FORM);
        if (form == null) {
            resp.sendRedirect(req.getContextPath() + "/transferInSearch");
            return;
        }

        form.setNs1(req.getParameter("ns1"));
        form.setNs2(req.getParameter("ns2"));

        List<String> errorMessages = validate(form);
        if (!errorMessages.isEmpty()) {
            req.setAttribute("errorMessages", errorMessages);
            req.setAttribute("form", form);
            req.getRequestDispatcher("/transferInNs.jsp").forward(req, resp);
            return;
        }

        req.getSession().setAttribute(SessionKeys.TRANSFER_IN_FORM, form);
        resp.sendRedirect(req.getContextPath() + "/transferInConfirm");
    }

    private List<String> validate(TransferInForm form) {
        List<String> errors = new ArrayList<>();
        if (isBlank(form.getNs1())) errors.add("ネームサーバー1は必須です。");
        return errors;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
