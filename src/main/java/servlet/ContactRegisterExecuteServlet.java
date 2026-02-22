package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ContactDao;
import dto.ContactDto;
import form.ContactRegisterForm;

/**
 * コンタクト登録 実行Servlet
 * URL: /contactRegisterExecute
 */
@WebServlet("/contactRegisterExecute")
public class ContactRegisterExecuteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // hiddenから値を再取得
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

        try {
            // FormをDtoに詰め替え
            ContactDto dto = new ContactDto();
            dto.setContactType(form.getContactType());
            dto.setLang(form.getLang());
            dto.setName(form.getName());
            dto.setOrganization(form.getOrganization());
            dto.setPostalCode(form.getPostalCode());
            dto.setSp(form.getSp());
            dto.setCity(form.getCity());
            dto.setStreet(form.getStreet());
            dto.setCc(form.getCc());
            dto.setVoice(form.getVoice());
            dto.setFax(form.getFax());
            dto.setEmail(form.getEmail());

            ContactDao dao = new ContactDao();
            String contactId = dao.insert(dto);

            req.setAttribute("form", form);
            req.setAttribute("contactId", contactId);
            req.getRequestDispatcher("/contactRegisterComplete.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("errorMessages", List.of("登録処理中にエラーが発生しました: " + e.getMessage()));
            req.setAttribute("form", form);
            req.getRequestDispatcher("/contactRegisterConfirm.jsp").forward(req, resp);
        }
    }
}