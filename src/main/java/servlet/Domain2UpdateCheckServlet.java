package servlet;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.OrganizationDomain;

@WebServlet("/domain2UpdateCheck")
public class Domain2UpdateCheckServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        // 入力値取得
        int id = Integer.parseInt(req.getParameter("id"));
        String corporateNumber = req.getParameter("corporateNumber");
        String attributeType = req.getParameter("attributeType");
        String domainName = req.getParameter("domainName");
        int status = Integer.parseInt(req.getParameter("status"));
        String authCode = req.getParameter("authCode");
        
        // モデルに詰める（確認画面用）
        OrganizationDomain domain = new OrganizationDomain();
        domain.setId(id);
        domain.setCorporateNumber(corporateNumber);
        domain.setAttributeType(attributeType);
        domain.setDomainName(domainName);
        domain.setStatus(status);
        
        // ★ DELETED 以外は認証コードをセット
        if (status != OrganizationDomain.Status.DELETED.getCode()) {
            domain.setAuthCode(authCode);
        }
        
        // JSP に渡す
        req.setAttribute("domain", domain);
        req.getRequestDispatcher("/domain2UpdateConfirm.jsp").forward(req, resp);
    }
}