package servlet;

import constants.SessionKeys;
import dao.ContactDao;
import dao.DomainDao;
import dto.ContactDto;
import dto.DomainDto;
import form.TransferInForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Transfer In 確認・実行Servlet
 * URL: /transferInConfirm
 */
@WebServlet("/transferInConfirm")
public class TransferInConfirmServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        TransferInForm form = (TransferInForm) req.getSession().getAttribute(SessionKeys.TRANSFER_IN_FORM);
        if (form == null) {
            resp.sendRedirect(req.getContextPath() + "/transferInSearch");
            return;
        }

        try {
            ContactDao contactDao = new ContactDao();
            ContactDto registrant   = contactDao.findByContactId(form.getRegistrant());
            ContactDto adminContact = contactDao.findByContactId(form.getAdminContact());
            ContactDto techContact  = contactDao.findByContactId(form.getTechContact());

            req.setAttribute("form", form);
            req.setAttribute("registrantName",   registrant   != null ? registrant.getName()   : "");
            req.setAttribute("adminContactName",  adminContact != null ? adminContact.getName() : "");
            req.setAttribute("techContactName",   techContact  != null ? techContact.getName()  : "");
            req.getRequestDispatcher("/transferInConfirm.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("errorMessage", "確認画面の表示に失敗しました: " + e.getMessage());
            req.getRequestDispatcher("/transferInConfirm.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        TransferInForm form   = (TransferInForm) req.getSession().getAttribute(SessionKeys.TRANSFER_IN_FORM);
        DomainDto      domain = (DomainDto)      req.getSession().getAttribute(SessionKeys.TRANSFER_IN_DOMAIN);

        if (form == null || domain == null) {
            resp.sendRedirect(req.getContextPath() + "/transferInSearch");
            return;
        }

        try {
            domain.setRegistrant(form.getRegistrant());
            domain.setAdminContact(form.getAdminContact());
            domain.setTechContact(form.getTechContact());
            domain.setNs1(form.getNs1());
            domain.setNs2(form.getNs2());
            domain.setLocked(true);  // 移管完了後は再ロック

            DomainDao dao = new DomainDao();
            dao.updateForTransferIn(domain);

            SessionKeys.clearTransferInSession(req.getSession());
            req.getSession().setAttribute("transferredDomainName", domain.getDomainName());
            resp.sendRedirect(req.getContextPath() + "/transferInComplete");

        } catch (Exception e) {
            req.setAttribute("errorMessage", "移管処理中にエラーが発生しました: " + e.getMessage());
            req.getRequestDispatcher("/transferInConfirm.jsp").forward(req, resp);
        }
    }
}
