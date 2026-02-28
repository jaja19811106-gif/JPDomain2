package servlet;

import constants.SessionKeys;
import dao.DomainDao;
import dto.DomainDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Transfer Out 検索Servlet
 * URL: /transferOutSearch
 */
@WebServlet("/transferOutSearch")
public class TransferOutSearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 開始時にセッションクリア
        SessionKeys.clearTransferOutSession(req.getSession());
        req.getRequestDispatcher("/transferOutSearch.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String domainName = req.getParameter("domainName");
        if (domainName == null || domainName.trim().isEmpty()) {
            req.setAttribute("errorMessage", "ドメイン名を入力してください。");
            req.getRequestDispatcher("/transferOutSearch.jsp").forward(req, resp);
            return;
        }

        try {
            DomainDao dao = new DomainDao();
            List<DomainDto> list = dao.findByDomainName(domainName.trim());
            if (list.isEmpty()) {
                req.setAttribute("errorMessage", "ドメインが見つかりませんでした。");
                req.getRequestDispatcher("/transferOutSearch.jsp").forward(req, resp);
                return;
            }

            DomainDto domain = list.get(0);

            // すでにLock解除済みの場合
            if (!domain.isLocked()) {
                req.setAttribute("errorMessage", "このドメインはすでにLock解除済みです。");
                req.getRequestDispatcher("/transferOutSearch.jsp").forward(req, resp);
                return;
            }

            req.getSession().setAttribute(SessionKeys.TRANSFER_OUT_DOMAIN, domain);
            resp.sendRedirect(req.getContextPath() + "/transferOutConfirm");

        } catch (Exception e) {
            req.setAttribute("errorMessage", "検索処理中にエラーが発生しました: " + e.getMessage());
            req.getRequestDispatcher("/transferOutSearch.jsp").forward(req, resp);
        }
    }
}
