package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import form.ContactRegisterForm;

/**
 * コンタクト登録 確認画面へ遷移するServlet
 * URL: /contactRegisterConfirm
 */
@WebServlet("/contactRegisterConfirm")
public class ContactRegisterConfirmServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // フォーム値取得
        ContactRegisterForm form = new ContactRegisterForm();
        form.setContactType(req.getParameter("contactType"));
        form.setLang(req.getParameter("lang"));
        form.setName(req.getParameter("name"));
        form.setOrganization(req.getParameter("organization"));
        form.setPostalCode(req.getParameter("postalCode"));
        form.setSp(req.getParameter("sp"));
        form.setCity(req.getParameter("city"));
        form.setStreet(req.getParameter("street"));
        form.setCc(req.getParameter("cc"));
        form.setVoice(req.getParameter("voice"));
        form.setFax(req.getParameter("fax"));
        form.setEmail(req.getParameter("email"));

        // バリデーション
        List<String> errorMessages = validate(form);
        if (!errorMessages.isEmpty()) {
            req.setAttribute("errorMessages", errorMessages);
            req.setAttribute("form", form);
            req.getRequestDispatcher("/contactRegisterInput.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("form", form);
        req.getRequestDispatcher("/contactRegisterConfirm.jsp").forward(req, resp);
    }

    private List<String> validate(ContactRegisterForm form) {
        List<String> errors = new ArrayList<>();

        if (isBlank(form.getContactType())) errors.add("コンタクト種別を選択してください。");
        if (isBlank(form.getName()))        errors.add("氏名は必須です。");
        if (isBlank(form.getPostalCode()))  errors.add("郵便番号は必須です。");
        if (isBlank(form.getSp()))          errors.add("都道府県は必須です。");
        if (isBlank(form.getCity()))        errors.add("市区町村は必須です。");
        if (isBlank(form.getStreet()))      errors.add("番地・建物名は必須です。");
        if (isBlank(form.getCc()))          errors.add("国コードは必須です。");
        if (isBlank(form.getVoice()))       errors.add("電話番号は必須です。");
        if (isBlank(form.getEmail()))       errors.add("メールアドレスは必須です。");

        return errors;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
