package servlet;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ContactDao;
import dto.ContactDto;
import dto.DomainDto;
import form.DomainUpdateForm;

@WebServlet("/domainUpdateContact")
public class DomainUpdateContactServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        DomainDto domain = (DomainDto) req.getSession().getAttribute("updateTargetDomain");
        if (domain == null) {
            resp.sendRedirect(req.getContextPath() + "/domainUpdateSearch");
            return;
        }
        try {
            ContactDao contactDao = new ContactDao();
            List<ContactDto> contactList = contactDao.findAll();
            req.setAttribute("domain", domain);
            req.setAttribute("contactList", contactList);
            req.getRequestDispatcher("/domainUpdateContact.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Contact一覧の取得に失敗しました: " + e.getMessage());
            req.getRequestDispatcher("/domainUpdateContact.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        System.out.println("★ ContactServlet doPost開始");
        System.out.println("★ session id: " + req.getSession().getId());

        DomainDto domain = (DomainDto) req.getSession().getAttribute("updateTargetDomain");
        if (domain == null) {
            resp.sendRedirect(req.getContextPath() + "/domainUpdateSearch");
            return;
        }

        DomainUpdateForm form = (DomainUpdateForm) req.getSession().getAttribute("domainUpdateForm");
        if (form == null) form = new DomainUpdateForm();

        form.setDomainName(domain.getDomainName());
        form.setRegistrant(req.getParameter("registrant"));
        form.setAdminContact(req.getParameter("adminContact"));
        form.setTechContact(req.getParameter("techContact"));

        req.getSession().setAttribute("domainUpdateForm", form);
        System.out.println("★ form保存完了: " + form);
        System.out.println("★ session id: " + req.getSession().getId());

        resp.sendRedirect(req.getContextPath() + "/domainUpdateNs");
    }
}