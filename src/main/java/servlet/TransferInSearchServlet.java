package servlet;

import constants.SessionKeys;
import dao.DomainDao;
import dto.DomainDto;
import form.TransferInForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Transfer In 検索Servlet
 * URL: /transferInSearch
 */
@WebServlet("/transferInSearch")
public class TransferInSearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 開始時にセッションクリア
        SessionKeys.clearTransferInSession(req.getSession());
        req.getRequestDispatcher("/transferInSearch.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String domainName = req.getParameter("domainName");
        String authCode   = req.getParameter("authCode");

        if (isBlank(domainName)) {
            req.setAttribute("errorMessage", "ドメイン名を入力してください。");
            req.getRequestDispatcher("/transferInSearch.jsp").forward(req, resp);
            return;
        }
        if (isBlank(authCode)) {
            req.setAttribute("errorMessage", "認証コードを入力してください。");
            req.getRequestDispatcher("/transferInSearch.jsp").forward(req, resp);
            return;
        }

        try {
            DomainDao dao = new DomainDao();
            List<DomainDto> list = dao.findByDomainName(domainName.trim());

            if (list.isEmpty()) {
                req.setAttribute("errorMessage", "ドメインが見つかりませんでした。");
                req.getRequestDispatcher("/transferInSearch.jsp").forward(req, resp);
                return;
            }

            DomainDto domain = list.get(0);

            // ロック中の場合はTransfer不可
            if (domain.isLocked()) {
                req.setAttribute("errorMessage", "このドメインはLockされています。Transfer Outを先に実施してください。");
                req.getRequestDispatcher("/transferInSearch.jsp").forward(req, resp);
                return;
            }

            // 認証コードチェック
            if (!authCode.trim().equals(domain.getAuthCode())) {
                req.setAttribute("errorMessage", "認証コードが正しくありません。");
                req.getRequestDispatcher("/transferInSearch.jsp").forward(req, resp);
                return;
            }

            // 有効期限チェック
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (domain.getAuthCodeExpiresAt() != null && domain.getAuthCodeExpiresAt().before(now)) {
                req.setAttribute("errorMessage", "認証コードの有効期限が切れています。Transfer Outをやり直してください。");
                req.getRequestDispatcher("/transferInSearch.jsp").forward(req, resp);
                return;
            }

            // FormとDomainをセッションに保存
            TransferInForm form = new TransferInForm();
            form.setDomainName(domain.getDomainName());
            form.setAuthCode(authCode.trim());

            req.getSession().setAttribute(SessionKeys.TRANSFER_IN_FORM, form);
            req.getSession().setAttribute(SessionKeys.TRANSFER_IN_DOMAIN, domain);
            resp.sendRedirect(req.getContextPath() + "/transferInContact");

        } catch (Exception e) {
            req.setAttribute("errorMessage", "検索処理中にエラーが発生しました: " + e.getMessage());
            req.getRequestDispatcher("/transferInSearch.jsp").forward(req, resp);
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
