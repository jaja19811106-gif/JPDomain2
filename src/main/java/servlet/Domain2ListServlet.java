package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.OrganizationDomainDao;
import model.OrganizationDomain;

@WebServlet("/domain2List")
public class Domain2ListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        OrganizationDomainDao dao = new OrganizationDomainDao();

        // 一覧データ取得
        List<OrganizationDomain> list = dao.findAll();

        // JSP に渡す
        req.setAttribute("domainList", list);

        // 一覧画面へフォワード
        req.getRequestDispatcher("/domain2List.jsp").forward(req, resp);
    }
}