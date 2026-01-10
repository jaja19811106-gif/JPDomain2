package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/domainUpdateConfirm")
public class DomainUpdateConfirmServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 編集画面から送られてきた値を取得
        String id = request.getParameter("id");
        String domainName = request.getParameter("domainName");
        String registrant = request.getParameter("registrant");
        String ns1 = request.getParameter("ns1");
        String ns2 = request.getParameter("ns2");
        String period = request.getParameter("period");

        // JSP に渡す
        request.setAttribute("id", id);
        request.setAttribute("domainName", domainName);
        request.setAttribute("registrant", registrant);
        request.setAttribute("ns1", ns1);
        request.setAttribute("ns2", ns2);
        request.setAttribute("period", period);

        request.getRequestDispatcher("domainUpdateConfirm.jsp")
               .forward(request, response);
    }
}