<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ホスト登録</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <jsp:include page="leftMenu.jsp" />

    <div class="content">
        <h2>ホスト登録</h2>

        <%
            List<String> errorMessages = (List<String>) request.getAttribute("errorMessages");
            if (errorMessages != null && !errorMessages.isEmpty()) {
        %>
            <div style="color:red; font-weight:bold; margin-bottom:10px;">
                <ul>
                <% for (String msg : errorMessages) { %>
                    <li><%= msg %></li>
                <% } %>
                </ul>
            </div>
        <% } %>

        <%
            form.HostRegisterForm form = (form.HostRegisterForm) request.getAttribute("form");
            String hostName = (form != null && form.getHostName() != null) ? form.getHostName() : "";
            String ipv4     = (form != null && form.getIpv4()     != null) ? form.getIpv4()     : "";
            String ipv6     = (form != null && form.getIpv6()     != null) ? form.getIpv6()     : "";
        %>

        <div style="background-color:#f5f5f5; padding:20px; border-radius:5px; margin-bottom:20px;">
            <form method="post" action="hostRegisterConfirm">
                <table border="1" cellpadding="5">
                    <tr>
                        <th>ホスト名（FQDN） *</th>
                        <td><input type="text" name="hostName" value="<%= hostName %>" size="40" maxlength="255" placeholder="例: ns1.example.co.jp"></td>
                    </tr>
                    <tr>
                        <th>IPv4アドレス</th>
                        <td><input type="text" name="ipv4" value="<%= ipv4 %>" size="20" maxlength="15" placeholder="例: 192.168.1.1"></td>
                    </tr>
                    <tr>
                        <th>IPv6アドレス</th>
                        <td><input type="text" name="ipv6" value="<%= ipv6 %>" size="40" maxlength="39" placeholder="例: 2001:db8::1"></td>
                    </tr>
                </table>
                <p style="color:gray; font-size:small;">※ IPv4またはIPv6アドレスを少なくとも1つ入力してください。</p>

                <div style="margin-top:15px;">
                    <button type="submit" style="padding:5px 15px;">確認画面へ</button>
                    <button type="button" style="padding:5px 15px; margin-left:10px;" onclick="location.href='menu.jsp'">キャンセル</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
