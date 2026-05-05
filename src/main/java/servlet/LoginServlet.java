package servlet;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProviderAccountDao;
import model.ProviderAccount;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String CSRF_TOKEN_KEY = "csrfToken";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // キャッシュ無効化
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");

        // CSRFトークン生成 → Sessionに保存 → requestにセット
        String token = generateCsrfToken();
        HttpSession session = request.getSession(true);
        session.setAttribute(CSRF_TOKEN_KEY, token);
        request.setAttribute(CSRF_TOKEN_KEY, token);

        System.out.println("=== doGet ===");
        System.out.println("SessionID: " + session.getId());
        System.out.println("生成トークン: " + token);

        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // CSRFチェックはCsrfFilterで実施済み
        // トークン使い捨て（検証後は即削除）
        HttpSession session = request.getSession(true);
        session.removeAttribute(CSRF_TOKEN_KEY);

        String providerNo = request.getParameter("providerNo");
        String loginId    = request.getParameter("userId");
        String password   = request.getParameter("password");

        ProviderAccountDao dao     = new ProviderAccountDao();
        ProviderAccount    account = dao.findByLogin(providerNo, loginId, password);

        if (account != null) {
            // セッション固定化攻撃対策：Sessionを再生成
            session.invalidate();
            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("provider", account);
            response.sendRedirect(request.getContextPath() + "/menu.jsp");
        } else {
            request.setAttribute("error", "ログイン情報が正しくありません");

            // 再表示用に新しいトークンを生成
            String newToken = generateCsrfToken();
            request.getSession(true).setAttribute(CSRF_TOKEN_KEY, newToken);
            request.setAttribute(CSRF_TOKEN_KEY, newToken);

            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        }
    }

    /**
     * CSRFトークン生成
     * SecureRandomで32バイトのランダム値を生成しBase64エンコード
     */
    private String generateCsrfToken() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}