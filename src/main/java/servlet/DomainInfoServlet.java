package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DomainDao;
import dto.DomainDto;

@WebServlet("/domainInfo")
public class DomainInfoServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    request.setAttribute("searched", false);

	    // ★ domainList を空で初期化
	    request.setAttribute("domainList", List.of());

	    request.getRequestDispatcher("domainInfo.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String domainName = request.getParameter("domainName");

		// null 安全 & strip で前後の空白（全角含む）を除去
		if (domainName == null) {
			domainName = "";
		} else {
			domainName = domainName.strip();
		}

		DomainDao dao = new DomainDao();
		List<DomainDto> list = null;

		try {
			list = dao.findByDomainName(domainName);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute("domainList", list);
		request.setAttribute("searched", true);

		// ★ 絶対パスで WEB-INF の JSP を forward
		request.getRequestDispatcher("domainInfo.jsp").forward(request, response);
	}
}