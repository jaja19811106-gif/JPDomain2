package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.OrganizationDomainDao;

@WebServlet("/domainRegisterCheck")
public class DomainRegisterCheckServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String corporateNumber = req.getParameter("corporateNumber");
        String attributeType   = req.getParameter("attributeType");
        String domainName      = req.getParameter("domainName");

        // IPアドレスバリデーション
        List<String> errorMessages = validateIpRanges(req);
        if (!errorMessages.isEmpty()) {
            // 入力値を引き継いで入力画面に戻す
            req.setAttribute("errorMessages",   errorMessages);
            req.setAttribute("corporateNumber", corporateNumber);
            req.setAttribute("attributeType",   attributeType);
            req.setAttribute("domainName",       domainName);
            setIpAttributes(req);
            req.getRequestDispatcher("/domainRegisterInput.jsp").forward(req, resp);
            return;
        }

        // 既存ドメインチェック
        OrganizationDomainDao dao = new OrganizationDomainDao();
        try {
            boolean exists = dao.existsActiveDomainForOrg(corporateNumber, attributeType);
            if (exists) {
                req.setAttribute("errorMessage",
                        "この組織はすでに " + attributeType + " を保持しています。");
                req.setAttribute("corporateNumber", corporateNumber);
                req.setAttribute("attributeType",   attributeType);
                req.setAttribute("domainName",       domainName);
                setIpAttributes(req);
                req.getRequestDispatcher("/domainRegisterInput.jsp").forward(req, resp);
                return;
            }

            // チェックOK → 確認画面へ遷移
            req.getRequestDispatcher("/domainRegisterConfirm.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    /**
     * IPアドレス範囲のバリデーション
     * - FROM・TOどちらか一方のみ入力はエラー
     * - FROM > TOはエラー（逆転チェック）
     */
    private List<String> validateIpRanges(HttpServletRequest req) {
        List<String> errors = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            String from = req.getParameter("ip" + i + "_from");
            String to   = req.getParameter("ip" + i + "_to");
            boolean hasFrom = !isBlank(from);
            boolean hasTo   = !isBlank(to);

            // 片方だけ入力チェック
            if (hasFrom && !hasTo) {
                errors.add("IPアドレス範囲" + i + "：TOが入力されていません。");
                continue;
            }
            if (!hasFrom && hasTo) {
                errors.add("IPアドレス範囲" + i + "：FROMが入力されていません。");
                continue;
            }

            // 両方入力されている場合のみ逆転チェック
            if (hasFrom && hasTo) {
                if (!isValidIpv4(from)) {
                    errors.add("IPアドレス範囲" + i + "：FROM「" + from + "」はIPv4形式ではありません。");
                    continue;
                }
                if (!isValidIpv4(to)) {
                    errors.add("IPアドレス範囲" + i + "：TO「" + to + "」はIPv4形式ではありません。");
                    continue;
                }
                if (compareIpv4(from, to) > 0) {
                    errors.add("IPアドレス範囲" + i + "：FROMはTOより小さい値を入力してください。");
                }
            }
        }
        return errors;
    }

    /**
     * IPv4形式チェック（例: 192.168.0.1）
     */
    private boolean isValidIpv4(String ip) {
        if (isBlank(ip)) return false;
        String[] parts = ip.split("\\.", -1);
        if (parts.length != 4) return false;
        for (String part : parts) {
            try {
                int val = Integer.parseInt(part);
                if (val < 0 || val > 255) return false;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * IPv4アドレスの大小比較
     * @return 正: ip1 > ip2、0: 等しい、負: ip1 < ip2
     */
    private int compareIpv4(String ip1, String ip2) {
        String[] parts1 = ip1.split("\\.");
        String[] parts2 = ip2.split("\\.");
        for (int i = 0; i < 4; i++) {
            int v1 = Integer.parseInt(parts1[i]);
            int v2 = Integer.parseInt(parts2[i]);
            if (v1 != v2) return v1 - v2;
        }
        return 0;
    }

    /**
     * エラー時にIP入力値をrequestスコープにセット
     */
    private void setIpAttributes(HttpServletRequest req) {
        for (int i = 1; i <= 5; i++) {
            req.setAttribute("ip" + i + "_from", req.getParameter("ip" + i + "_from"));
            req.setAttribute("ip" + i + "_to",   req.getParameter("ip" + i + "_to"));
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}