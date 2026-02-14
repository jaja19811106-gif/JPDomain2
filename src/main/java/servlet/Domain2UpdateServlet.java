package servlet;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

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
    
    // 認証コードが必要なステータス（遷移先）
    private static final Set<OrganizationDomain.Status> AUTH_CODE_REQUIRED_STATUSES = 
        EnumSet.of(
            OrganizationDomain.Status.ACTIVE,
            OrganizationDomain.Status.REGISTERED,
            OrganizationDomain.Status.RESERVED,
            OrganizationDomain.Status.PENDING_APPLICATION,
            OrganizationDomain.Status.DOMAIN_ACTIVE
        );
    
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
            // 認証コードが必要なステータス（遷移先）の場合
            if (AUTH_CODE_REQUIRED_STATUSES.contains(statusEnum)) {
                if (authCode != null && !authCode.isEmpty()) {
                    // 認証コードあり → そのまま保持
                    domain.setAuthCode(authCode);
                } else {
                    // 認証コードなし → 新規採番
                    String newCode = AuthCodeUtil.generateAuthCode();
                    domain.setAuthCode(newCode);
                    domain.setAuthCodeExpiresAt(null);
                }
            } 
            // DELETED（遷移元）の場合 → 認証コードと有効期限をクリア
            else if (statusEnum == OrganizationDomain.Status.DELETED) {
                domain.setAuthCode(null);
                domain.setAuthCodeExpiresAt(null);
            }
            
            // 更新実行
            OrganizationDomainDao dao = new OrganizationDomainDao();
            dao.update(domain);
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        req.getRequestDispatcher("/domain2UpdateResult.jsp").forward(req, resp);
    }
}