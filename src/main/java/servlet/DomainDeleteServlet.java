package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DomainDao;

@WebServlet("/domainDelete")
public class DomainDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);

        DomainDao dao = new DomainDao();

        try {
            dao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 削除後は一覧へ戻る
        response.sendRedirect("domainInfo");
    }
}