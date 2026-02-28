package servlet;

import constants.SessionKeys;
import dao.DomainDao;
import dto.DomainDto;
import util.AuthCodeUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Transfer Out 確認・実行Servlet
 * URL: /transferOutConfirm
 */
@WebServlet("/transferOutConfirm")
public class TransferOutConfirmServlet extends HttpServlet {

    // GET: 確認画面表示
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        DomainDto domain = (DomainDto) req.getSession().getAttribute(SessionKeys.TRANSFER_OUT_DOMAIN);
        if (domain == null) {
            resp.sendRedirect(req.getContextPath() + "/transferOutSearch");
            return;
        }

        req.setAttribute("domain", domain);
        req.getRequestDispatcher("/transferOutConfirm.jsp").forward(req, resp);
    }

    // POST: Lock解除実行
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        DomainDto domain = (DomainDto) req.getSession().getAttribute(SessionKeys.TRANSFER_OUT_DOMAIN);
        if (domain == null) {
            resp.sendRedirect(req.getContextPath() + "/transferOutSearch");
            return;
        }

        try {
            // 認証コード生成・有効期限設定（14日間）
            String authCode = AuthCodeUtil.generateAuthCode();
            Timestamp expiresAt = Timestamp.valueOf(LocalDateTime.now().plusDays(14));

            domain.setLocked(false);
            domain.setAuthCode(authCode);
            domain.setAuthCodeExpiresAt(expiresAt);

            DomainDao dao = new DomainDao();
            dao.updateForTransferOut(domain);

            // 完了画面用にセッション更新
            req.getSession().setAttribute(SessionKeys.TRANSFER_OUT_DOMAIN, domain);
            resp.sendRedirect(req.getContextPath() + "/transferOutComplete");

        } catch (Exception e) {
            req.setAttribute("errorMessage", "Lock解除処理中にエラーが発生しました: " + e.getMessage());
            req.setAttribute("domain", domain);
            req.getRequestDispatcher("/transferOutConfirm.jsp").forward(req, resp);
        }
    }
}
