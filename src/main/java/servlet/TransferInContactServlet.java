package servlet;

import constants.SessionKeys;
import dao.ContactDao;
import dto.ContactDto;
import form.TransferInForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Transfer In Contact選択Servlet
 * URL: /transferInContact
 */
@WebServlet("/transferInContact")
public class TransferInContactServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (req.getSession().getAttribute(SessionKeys.TRANSFER_IN_FORM) == null) {
            resp.sendRedirect(req.getContextPath() + "/transferInSearch");
            return;
        }
        try {
            ContactDao contactDao = new ContactDao();
            List<ContactDto> contactList = contactDao.findAll();
            req.setAttribute("contactList", contactList);
            req.getRequestDispatcher("/transferInContact.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Contact一覧の取得に失敗しました: " + e.getMessage());
            req.getRequestDispatcher("/transferInContact.jsp").forward(req, resp);
        }
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

        form.setRegistrant(req.getParameter("registrant"));
        form.setAdminContact(req.getParameter("adminContact"));
        form.setTechContact(req.getParameter("techContact"));

        req.getSession().setAttribute(SessionKeys.TRANSFER_IN_FORM, form);
        resp.sendRedirect(req.getContextPath() + "/transferInNs");
    }
}
