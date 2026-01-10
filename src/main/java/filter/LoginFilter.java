package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ProviderAccount;

@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getServletPath();
        HttpSession session = req.getSession(false);

        // ログイン画面とログイン処理は除外
        if (path.equals("/login.jsp") || path.equals("/login")) {
            chain.doFilter(request, response);
            return;
        }

        // セッションが無い or ログインしていない → ログイン画面へ
        ProviderAccount account = (session == null) ? null : (ProviderAccount) session.getAttribute("provider");

        if (account == null) {
            res.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        // ログイン済み → 通過
        chain.doFilter(request, response);
    }
}