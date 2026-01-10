package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DomainDao;
import dto.DomainDto;

@WebServlet("/domainEdit")
public class DomainEditServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String domainName = request.getParameter("domainName");

        DomainDao dao = new DomainDao();
        DomainDto dto = null;

        try {
            dto = dao.findByDomainName(domainName).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("domain", dto);
        request.getRequestDispatcher("domainEdit.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DomainDto dto = new DomainDto();
        dto.setId(Integer.parseInt(request.getParameter("id")));
        dto.setDomainName(request.getParameter("domainName"));
        dto.setRegistrant(request.getParameter("registrant"));
        dto.setNs1(request.getParameter("ns1"));
        dto.setNs2(request.getParameter("ns2"));
        dto.setPeriod(Integer.parseInt(request.getParameter("period")));

        request.setAttribute("domain", dto);
        request.getRequestDispatcher("domainEdit.jsp").forward(request, response);
    }
}