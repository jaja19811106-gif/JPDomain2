package servlet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        List<String> errorMessages = new ArrayList<>();

        // ① ホストアドレスバリデーション
        errorMessages.addAll(validateHosts(req));

        // ② IPアドレス範囲バリデーション
        errorMessages.addAll(validateIpRanges(req));

        // ③ 重複チェック（フォーマットエラーがない場合のみ実施）
        if (errorMessages.isEmpty()) {
            errorMessages.addAll(validateDuplicates(req));
        }

        // ④ 既存ドメインチェック
        OrganizationDomainDao dao = new OrganizationDomainDao();
        try {
            boolean exists = dao.existsActiveDomainForOrg(corporateNumber, attributeType);
            if (exists) {
                errorMessages.add("この組織はすでに " + attributeType + " を保持しています。");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        // まとめてエラー表示
        if (!errorMessages.isEmpty()) {
            req.setAttribute("errorMessages",   errorMessages);
            req.setAttribute("corporateNumber", corporateNumber);
            req.setAttribute("attributeType",   attributeType);
            req.setAttribute("domainName",       domainName);
            setHostAttributes(req);
            setIpAttributes(req);
            req.getRequestDispatcher("/domainRegisterInput.jsp").forward(req, resp);
            return;
        }

        // 全チェックOK → 確認画面へ
        req.getRequestDispatcher("/domainRegisterConfirm.jsp").forward(req, resp);
    }

    /**
     * ホストアドレスバリデーション（任意・入力がある場合のみIPv4形式チェック）
     */
    private List<String> validateHosts(HttpServletRequest req) {
        List<String> errors = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            String host = req.getParameter("host" + i);
            if (!isBlank(host) && parseIpv4(host) == null) {
                errors.add("ホストアドレス" + i + "「" + host + "」はIPv4形式ではありません。");
            }
        }
        return errors;
    }

    /**
     * IPアドレス範囲のバリデーション
     */
    private List<String> validateIpRanges(HttpServletRequest req) {
        List<String> errors = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            String from = req.getParameter("ip" + i + "_from");
            String to   = req.getParameter("ip" + i + "_to");
            boolean hasFrom = !isBlank(from);
            boolean hasTo   = !isBlank(to);

            if (!hasFrom && hasTo) {
                errors.add("IPアドレス範囲" + i + "：FROMが入力されていません。");
                continue;
            }
            if (hasFrom && !hasTo) {
                errors.add("IPアドレス範囲" + i + "：TOが入力されていません。");
                continue;
            }
            if (hasFrom && hasTo) {
                InetAddress fromAddr = parseIpv4(from);
                if (fromAddr == null) {
                    errors.add("IPアドレス範囲" + i + "：FROM「" + from + "」はIPv4形式ではありません。");
                    continue;
                }
                InetAddress toAddr = parseIpv4(to);
                if (toAddr == null) {
                    errors.add("IPアドレス範囲" + i + "：TO「" + to + "」はIPv4形式ではありません。");
                    continue;
                }
                if (isFromGreaterThanTo(fromAddr, toAddr)) {
                    errors.add("IPアドレス範囲" + i + "：FROMはTOより小さい値を入力してください。");
                }
            }
        }
        return errors;
    }

    /**
     * 重複チェック
     * IPアドレス範囲FROM/TO同士・およびホストアドレスとの重複を検出する
     * ホストアドレス同士の重複チェックは対象外
     * エラーメッセージにどのアドレスと重複しているかを表示する
     */
    private List<String> validateDuplicates(HttpServletRequest req) {
        List<String>        errors = new ArrayList<>();
        Map<String, String> seen   = new HashMap<>(); // IP → ラベル

        // ホストアドレス1〜5をseenに登録（重複検出の比較対象として使用）
        for (int i = 1; i <= 5; i++) {
            String host = req.getParameter("host" + i);
            if (!isBlank(host)) seen.put(host.trim(), "ホストアドレス" + i);
        }

        // IPアドレス範囲FROM/TOをチェック（ホストアドレスまたは他のFROM/TOと重複していたらエラー）
        for (int i = 1; i <= 5; i++) {
            String from = req.getParameter("ip" + i + "_from");
            String to   = req.getParameter("ip" + i + "_to");
            if (!isBlank(from)) {
                String f     = from.trim();
                String label = "IPアドレス範囲" + i + " FROM";
                if (seen.containsKey(f)) {
                    errors.add(label + "の「" + f + "」は" + seen.get(f) + "と重複しています。");
                } else {
                    seen.put(f, label);
                }
            }
            if (!isBlank(to)) {
                String t     = to.trim();
                String label = "IPアドレス範囲" + i + " TO";
                if (seen.containsKey(t)) {
                    errors.add(label + "の「" + t + "」は" + seen.get(t) + "と重複しています。");
                } else {
                    seen.put(t, label);
                }
            }
        }

        return errors;
    }

    /**
     * IPv4アドレスをパース（不正な形式はnullを返す）
     */
    private InetAddress parseIpv4(String ip) {
        if (isBlank(ip)) return null;
        if (!ip.matches("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$")) return null;
        try {
            return InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            return null;
        }
    }

    /**
     * FROMがTOより大きいか判定（逆転チェック）
     * ByteBufferでIPv4アドレスを32bit整数に変換して比較
     * @return true: FROM > TO（逆転している）、false: FROM <= TO（正常）
     */
    private boolean isFromGreaterThanTo(InetAddress from, InetAddress to) {
        return Integer.compareUnsigned(
            ByteBuffer.wrap(from.getAddress()).getInt(),
            ByteBuffer.wrap(to.getAddress()).getInt()
        ) > 0;
    }

    /**
     * エラー時にホスト入力値をrequestスコープにセット
     */
    private void setHostAttributes(HttpServletRequest req) {
        for (int i = 1; i <= 5; i++) {
            req.setAttribute("host" + i, req.getParameter("host" + i));
        }
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