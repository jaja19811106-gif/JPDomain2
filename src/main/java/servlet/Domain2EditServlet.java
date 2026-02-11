package servlet;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.OrganizationDomainDao;
import model.OrganizationDomain;

@WebServlet("/domain2Edit")
public class Domain2EditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        req.setCharacterEncoding("UTF-8");
        
        // パラメータ取得
        int id = Integer.parseInt(req.getParameter("id"));
        
        // DAO で最新データを取得
        OrganizationDomainDao dao = new OrganizationDomainDao();
        OrganizationDomain domain = null;
        
        try {
            domain = dao.findByIdForUpdate(id);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "データ取得中にエラーが発生しました。");
            req.getRequestDispatcher("/domain2List.jsp").forward(req, resp);
            return;
        }
        
        // 将来ロックしたいなら findByIdForUpdate(id) に差し替えるだけ
        if (domain == null) {
            req.setAttribute("errorMessage", "指定されたデータが存在しません。");
            req.getRequestDispatcher("/domain2List.jsp").forward(req, resp);
            return;
        }
        
        // JSP に渡す
        req.setAttribute("domain", domain);
        // ★ ステータス一覧を追加
        req.setAttribute("statusList", OrganizationDomain.Status.values());
        
        // 編集画面へ
        req.getRequestDispatcher("/domain2Edit.jsp").forward(req, resp);
    }
}