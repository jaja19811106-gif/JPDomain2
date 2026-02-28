package servlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.DomainDto;
import form.DomainUpdateForm;

@WebServlet("/domainUpdateNs")
public class DomainUpdateNsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        DomainDto domain = (DomainDto) req.getSession().getAttribute("updateTargetDomain");
        if (domain == null) {
            resp.sendRedirect(req.getContextPath() + "/domainUpdateSearch");
            return;
        }
        req.setAttribute("domain", domain);
        req.getRequestDispatcher("/domainUpdateNs.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        System.out.println("★ NsServlet doPost開始");
        System.out.println("★ session id: " + req.getSession().getId());

        DomainUpdateForm form = (DomainUpdateForm) req.getSession().getAttribute("domainUpdateForm");
        System.out.println("★ form: " + form);

        if (form == null) {
            resp.sendRedirect(req.getContextPath() + "/domainUpdateSearch");
            return;
        }

        form.setNs1(req.getParameter("ns1"));
        form.setNs2(req.getParameter("ns2"));

        List<String> errorMessages = validate(form);
        if (!errorMessages.isEmpty()) {
            req.setAttribute("errorMessages", errorMessages);
            req.setAttribute("domain", req.getSession().getAttribute("updateTargetDomain"));
            req.setAttribute("form", form);
            req.getRequestDispatcher("/domainUpdateNs.jsp").forward(req, resp);
            return;
        }

        req.getSession().setAttribute("domainUpdateForm", form);
        resp.sendRedirect(req.getContextPath() + "/domainInfoUpdateConfirm");
    }

    private List<String> validate(DomainUpdateForm form) {
        List<String> errors = new ArrayList<>();
        if (isBlank(form.getNs1())) errors.add("ネームサーバー1は必須です。");
        return errors;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}