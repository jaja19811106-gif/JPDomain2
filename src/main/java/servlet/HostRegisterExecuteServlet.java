package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HostDao;
import dto.HostDto;
import form.HostRegisterForm;

/**
 * ホスト登録 実行Servlet
 * URL: /hostRegisterExecute
 */
@WebServlet("/hostRegisterExecute")
public class HostRegisterExecuteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HostRegisterForm form = new HostRegisterForm();
        form.setHostName(req.getParameter("hostName"));
        form.setIpv4(req.getParameter("ipv4"));
        form.setIpv6(req.getParameter("ipv6"));

        try {
            HostDto dto = new HostDto();
            dto.setHostName(form.getHostName());
            dto.setIpv4(form.getIpv4());
            dto.setIpv6(form.getIpv6());

            HostDao dao = new HostDao();
            dao.insert(dto);

            req.setAttribute("form", form);
            req.getRequestDispatcher("/hostRegisterComplete.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("errorMessages", List.of("登録処理中にエラーが発生しました: " + e.getMessage()));
            req.setAttribute("form", form);
            req.getRequestDispatcher("/hostRegisterConfirm.jsp").forward(req, resp);
        }
    }
}