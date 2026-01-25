package servlet;

import java.io.IOException;

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

        // パラメータ取得
        String idStr = req.getParameter("id");
        int id = Integer.parseInt(idStr);

        // DAO でデータ取得
        OrganizationDomainDao dao = new OrganizationDomainDao();
        OrganizationDomain domain = dao.findById(id);

        if (domain == null) {
            req.setAttribute("errorMessage", "指定されたデータが存在しません。");
            req.getRequestDispatcher("/domain2List.jsp").forward(req, resp);
            return;
        }

        // JSP に渡す
        req.setAttribute("domain", domain);

        // 編集画面へ
        req.getRequestDispatcher("/domain2Edit.jsp").forward(req, resp);
    }
}