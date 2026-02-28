package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DomainDao;
import dto.DomainDto;

/**
 * ドメイン情報変更 検索Servlet
 * URL: /domainUpdateSearch
 */
@WebServlet("/domainUpdateSearch")
public class DomainUpdateSearchServlet extends HttpServlet {

    // GET: 検索画面表示
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/domainUpdateSearch.jsp").forward(req, resp);
    }

    // POST: ドメイン名で検索
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String domainName = req.getParameter("domainName");

        if (domainName == null || domainName.trim().isEmpty()) {
            req.setAttribute("errorMessage", "ドメイン名を入力してください。");
            req.getRequestDispatcher("/domainUpdateSearch.jsp").forward(req, resp);
            return;
        }

        DomainDao dao = new DomainDao();
        try {
            // ドメイン名で完全一致検索
            java.util.List<DomainDto> list = dao.findByDomainName(domainName.trim());
            if (list.isEmpty()) {
                req.setAttribute("errorMessage", "ドメインが見つかりませんでした。");
                req.getRequestDispatcher("/domainUpdateSearch.jsp").forward(req, resp);
                return;
            }

            DomainDto domain = list.get(0);
            req.getSession().setAttribute("updateTargetDomain", domain);
            resp.sendRedirect(req.getContextPath() + "/domainUpdateContact");

        } catch (Exception e) {
            req.setAttribute("errorMessage", "検索処理中にエラーが発生しました: " + e.getMessage());
            req.getRequestDispatcher("/domainUpdateSearch.jsp").forward(req, resp);
        }
    }
}