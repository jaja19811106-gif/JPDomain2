package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.OrganizationDomainDao;
import model.OrganizationDomain;

@WebServlet("/domainAuthCodeSearch")
public class DomainAuthCodeSearchServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        req.setCharacterEncoding("UTF-8");
        
        // 検索フォーム表示
        req.getRequestDispatcher("/domainAuthCodeSearch.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        req.setCharacterEncoding("UTF-8");
        
        String domainName = req.getParameter("domainName");
        
        // バリデーション
        if (domainName == null || domainName.trim().isEmpty()) {
            req.setAttribute("errorMessage", "ドメイン名を入力してください。");
            req.getRequestDispatcher("/domainAuthCodeSearch.jsp").forward(req, resp);
            return;
        }
        
        OrganizationDomainDao dao = new OrganizationDomainDao();
        
        try {
            // ドメイン名で検索
            OrganizationDomain domain = dao.findByDomainName(domainName.trim());
            
            if (domain == null) {
                req.setAttribute("errorMessage", "指定されたドメイン名が見つかりませんでした。");
                req.getRequestDispatcher("/domainAuthCodeSearch.jsp").forward(req, resp);
                return;
            }
            
            // ステータスがDELETEDの場合はエラー
            if (domain.getStatusEnum() == OrganizationDomain.Status.DELETED) {
                req.setAttribute("errorMessage", "このドメインは削除済みのため、認証コードを発行できません。");
                req.getRequestDispatcher("/domainAuthCodeSearch.jsp").forward(req, resp);
                return;
            }
            
            // 認証コードが設定されている場合のみ有効期限を更新
            if (domain.getAuthCode() != null && !domain.getAuthCode().isEmpty()) {
                // 有効期限を35日後の23:59:59に更新
                dao.updateAuthCodeExpiresAt(domain.getId());
                
                // 更新後の有効期限をdomainオブジェクトにセット（画面表示用）
                LocalDateTime expiresAt = LocalDateTime.now()
                        .plusDays(35)
                        .with(LocalTime.of(23, 59, 59));
                domain.setAuthCodeExpiresAt(Timestamp.valueOf(expiresAt));
            }
            
            // 検索結果を画面に渡す
            req.setAttribute("domain", domain);
            req.getRequestDispatcher("/domainAuthCodeSearch.jsp").forward(req, resp);
            
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "検索処理中にエラーが発生しました。");
            req.getRequestDispatcher("/domainAuthCodeSearch.jsp").forward(req, resp);
        }
    }
}