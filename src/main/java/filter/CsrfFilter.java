package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/login")
public class CsrfFilter implements Filter {

    private static final String CSRF_TOKEN_KEY = "csrfToken";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初期化処理なし
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  httpRequest  = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // GETリクエストはチェックしない
        if ("GET".equalsIgnoreCase(httpRequest.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        // POSTリクエストのみCSRFトークン検証
        HttpSession session     = httpRequest.getSession(true);
        String sessionToken     = (String) session.getAttribute(CSRF_TOKEN_KEY);
        String requestToken     = httpRequest.getParameter(CSRF_TOKEN_KEY);

        System.out.println("=== CsrfFilter ===");
        System.out.println("SessionID: " + session.getId());
        System.out.println("Sessionトークン: " + sessionToken);
        System.out.println("Requestトークン: " + requestToken);

        if (sessionToken == null || !sessionToken.equals(requestToken)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "不正なリクエストです。");
            return;
        }

        // 検証OKの場合は次の処理へ
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 終了処理なし
    }
}