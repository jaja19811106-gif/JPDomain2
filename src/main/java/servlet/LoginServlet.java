package servlet;

import java.io.IOException;

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String providerNo = request.getParameter("providerNo");
        String loginId = request.getParameter("userId");
        String password = request.getParameter("password");

        ProviderAccountDao dao = new ProviderAccountDao();
        ProviderAccount account = dao.findByLogin(providerNo, loginId, password);

        
        if (account != null) {
            HttpSession session = request.getSession();
            session.setAttribute("provider", account);
            
            response.sendRedirect("menu.jsp");
        } else {
            request.setAttribute("error", "ログイン情報が正しくありません");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}