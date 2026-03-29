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
                if (!isValidIpRange(fromAddr, toAddr)) {
                    errors.add("IPアドレス範囲" + i + "：FROMはTOより小さい値を入力してください。");
                }
            }
        }
        return errors;
    }

    /**
     * 重複チェック
     * 以下の2種類のチェックを行う
     * ① IPアドレス範囲FROM/TO同士・およびホストアドレスとの完全一致チェック
     * ② ホストアドレスがIPアドレス範囲（FROM〜TO）に含まれるかチェック
     * ホストアドレス同士の重複チェックは対象外
     */
    private List<String> validateDuplicates(HttpServletRequest req) {
        List<String>        errors  = new ArrayList<>();
        Map<String, String> seen    = new HashMap<>(); // IP → ラベル

        // ホストアドレス1〜5をseenに登録
        for (int i = 1; i <= 5; i++) {
            String host = req.getParameter("host" + i);
            if (!isBlank(host)) seen.put(host.trim(), "ホストアドレス" + i);
        }

        // IPアドレス範囲FROM/TOをチェック
        for (int i = 1; i <= 5; i++) {
            String from = req.getParameter("ip" + i + "_from");
            String to   = req.getParameter("ip" + i + "_to");
            if (isBlank(from) || isBlank(to)) continue;

            String f      = from.trim();
            String t      = to.trim();
            String rangeLabel = "IPアドレス範囲" + i;

            // ① FROM/TOの完全一致チェック
            if (seen.containsKey(f)) {
                errors.add(rangeLabel + " FROMの「" + f + "」は" + seen.get(f) + "と重複しています。");
            } else {
                seen.put(f, rangeLabel + " FROM");
            }
            if (seen.containsKey(t)) {
                errors.add(rangeLabel + " TOの「" + t + "」は" + seen.get(t) + "と重複しています。");
            } else {
                seen.put(t, rangeLabel + " TO");
            }

            // ② ホストアドレスが範囲内に含まれるかチェック
            InetAddress fromAddr = parseIpv4(f);
            InetAddress toAddr   = parseIpv4(t);
            for (int j = 1; j <= 5; j++) {
                String host = req.getParameter("host" + j);
                if (isBlank(host)) continue;
                InetAddress hostAddr = parseIpv4(host.trim());
                if (hostAddr == null) continue;
                if (isInRange(hostAddr, fromAddr, toAddr)) {
                    errors.add("ホストアドレス" + j + "の「" + host.trim() + "」は" + rangeLabel + "（" + f + "〜" + t + "）の範囲内に含まれています。");
                }
            }
        }

        return errors;
    }

    /**
     * IPアドレスが範囲内（FROM〜TO）に含まれるか判定
     * @return true: 範囲内、false: 範囲外
     */
    private boolean isInRange(InetAddress target, InetAddress from, InetAddress to) {
        int t = ByteBuffer.wrap(target.getAddress()).getInt();
        int f = ByteBuffer.wrap(from.getAddress()).getInt();
        int e = ByteBuffer.wrap(to.getAddress()).getInt();
        return Integer.compareUnsigned(t, f) >= 0 && Integer.compareUnsigned(t, e) <= 0;
    }

    /**
     * IPv4アドレスをパース（不正な形式はnullを返す）
     */
    private InetAddress parseIpv4(String ip) {
        if (isBlank(ip)) return null;
        if (!ip.matches("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$")) return null;
        try {
            String[] parts = ip.trim().split("\\.");
            byte[] bytes = new byte[4];
            for (int i = 0; i < 4; i++) {
                int val = Integer.parseInt(parts[i]);
                if (val < 0 || val > 255) return null;
                bytes[i] = (byte) val;
            }
            return InetAddress.getByAddress(bytes);
        } catch (UnknownHostException | NumberFormatException e) {
            return null;
        }
    }

    /**
     * IPアドレス範囲の順序チェック（FROM <= TO）
     * ByteBufferでIPv4アドレスを32bit整数に変換して比較
     * @return true: FROM <= TO（正常）、false: FROM > TO（逆転している）
     */
    private boolean isValidIpRange(InetAddress from, InetAddress to) {
        return Integer.compareUnsigned(
            ByteBuffer.wrap(from.getAddress()).getInt(),
            ByteBuffer.wrap(to.getAddress()).getInt()
        ) <= 0;
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