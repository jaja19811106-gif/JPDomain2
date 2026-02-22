package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import form.HostRegisterForm;

/**
 * ホスト登録 確認画面へ遷移するServlet
 * URL: /hostRegisterConfirm
 */
@WebServlet("/hostRegisterConfirm")
public class HostRegisterConfirmServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HostRegisterForm form = new HostRegisterForm();
        form.setHostName(req.getParameter("hostName"));
        form.setIpv4(req.getParameter("ipv4"));
        form.setIpv6(req.getParameter("ipv6"));

        List<String> errorMessages = validate(form);
        if (!errorMessages.isEmpty()) {
            req.setAttribute("errorMessages", errorMessages);
            req.setAttribute("form", form);
            req.getRequestDispatcher("/hostRegisterInput.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("form", form);
        req.getRequestDispatcher("/hostRegisterConfirm.jsp").forward(req, resp);
    }

    private List<String> validate(HostRegisterForm form) {
        List<String> errors = new ArrayList<>();

        if (isBlank(form.getHostName())) {
            errors.add("ホスト名は必須です。");
        }
        // IPv4とIPv6は少なくとも一方必須
        if (isBlank(form.getIpv4()) && isBlank(form.getIpv6())) {
            errors.add("IPv4またはIPv6アドレスを少なくとも1つ入力してください。");
        }

        return errors;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}