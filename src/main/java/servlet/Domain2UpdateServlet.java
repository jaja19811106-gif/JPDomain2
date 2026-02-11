package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.OrganizationDomainDao;
import model.OrganizationDomain;
import util.AuthCodeUtil;

@WebServlet("/domain2Update")
public class Domain2UpdateServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        req.setCharacterEncoding("UTF-8");
        
        int id = Integer.parseInt(req.getParameter("id"));
        String corporateNumber = req.getParameter("corporateNumber");
        String attributeType = req.getParameter("attributeType");
        String domainName = req.getParameter("domainName");
        int statusCode = Integer.parseInt(req.getParameter("status"));
        String authCode = req.getParameter("authCode"); // hidden で送られる
        
        // Enum に変換
        OrganizationDomain.Status statusEnum = OrganizationDomain.Status.fromCode(statusCode);
        
        OrganizationDomain domain = new OrganizationDomain();
        domain.setId(id);
        domain.setCorporateNumber(corporateNumber);
        domain.setAttributeType(attributeType);
        domain.setDomainName(domainName);
        domain.setStatus(statusCode);
        
        try {
            // ★ ACTIVE の場合
            if (statusEnum == OrganizationDomain.Status.ACTIVE) {
                if (authCode != null && !authCode.isEmpty()) {
                    // ACTIVE → ACTIVE（authCode あり）→ そのまま保持
                    domain.setAuthCode(authCode);
                } else {
                    // ACTIVE → ACTIVE（authCode なし） or DELETED → ACTIVE
                    // → 新規採番
                    String newCode = AuthCodeUtil.generateAuthCode();
                    domain.setAuthCode(newCode);
                    domain.setAuthCodeExpiresAt(null);
                }
            } else if (statusEnum == OrganizationDomain.Status.DELETED) {
                // ★ DELETED のとき → 認証コードと有効期限をクリア
                domain.setAuthCode(null);
                domain.setAuthCodeExpiresAt(null);
            }
            
            // ★ 更新実行（DAO は渡された値をそのまま更新するだけ）
            OrganizationDomainDao dao = new OrganizationDomainDao();
            dao.update(domain);
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        req.getRequestDispatcher("/domain2UpdateResult.jsp").forward(req, resp);
    }
}