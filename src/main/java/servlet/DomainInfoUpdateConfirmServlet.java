package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ContactDao;
import dao.DomainDao;
import dto.ContactDto;
import dto.DomainDto;
import form.DomainUpdateForm;

/**
 * ドメイン情報変更 確認・実行Servlet
 * URL: /domainInfoUpdateConfirm
 */
@WebServlet("/domainInfoUpdateConfirm")
public class DomainInfoUpdateConfirmServlet extends HttpServlet {

    // GET: 確認画面表示
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        DomainUpdateForm form = (DomainUpdateForm) req.getSession().getAttribute("domainUpdateForm");
        if (form == null) {
            resp.sendRedirect(req.getContextPath() + "/domainUpdateSearch");
            return;
        }

        try {
            // contact_idから名前を取得して表示用に渡す
            ContactDao contactDao = new ContactDao();
            ContactDto registrant    = contactDao.findByContactId(form.getRegistrant());
            ContactDto adminContact  = contactDao.findByContactId(form.getAdminContact());
            ContactDto techContact   = contactDao.findByContactId(form.getTechContact());

            req.setAttribute("form", form);
            req.setAttribute("registrantName",   registrant   != null ? registrant.getName()   : "");
            req.setAttribute("adminContactName",  adminContact  != null ? adminContact.getName()  : "");
            req.setAttribute("techContactName",   techContact   != null ? techContact.getName()   : "");
            req.getRequestDispatcher("/domainInfoUpdateConfirm.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("errorMessage", "確認画面の表示に失敗しました: " + e.getMessage());
            req.getRequestDispatcher("/domainInfoUpdateConfirm.jsp").forward(req, resp);
        }
    }

    // POST: DB更新実行
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        DomainUpdateForm form = (DomainUpdateForm) req.getSession().getAttribute("domainUpdateForm");
        DomainDto domain = (DomainDto) req.getSession().getAttribute("updateTargetDomain");

        if (form == null || domain == null) {
            resp.sendRedirect(req.getContextPath() + "/domainUpdateSearch");
            return;
        }

        try {
            // DomainDtoに変更内容を反映
            domain.setRegistrant(form.getRegistrant());
            domain.setAdminContact(form.getAdminContact());
            domain.setTechContact(form.getTechContact());
            domain.setNs1(form.getNs1());
            domain.setNs2(form.getNs2());

            DomainDao dao = new DomainDao();
            dao.update(domain);

            // セッションをクリア
            req.getSession().removeAttribute("domainUpdateForm");
            req.getSession().removeAttribute("updateTargetDomain");

            req.getSession().setAttribute("updatedDomainName", domain.getDomainName());
            resp.sendRedirect(req.getContextPath() + "/domainInfoUpdateComplete");

        } catch (Exception e) {
            req.setAttribute("errorMessage", "更新処理中にエラーが発生しました: " + e.getMessage());
            req.getRequestDispatcher("/domainInfoUpdateConfirm.jsp").forward(req, resp);
        }
    }
}