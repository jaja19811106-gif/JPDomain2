<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, form.TransferInForm" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Transfer In - NS入力</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <jsp:include page="leftMenu.jsp" />

    <div class="content">
        <h2>Transfer In - NS入力</h2>

        <%
            TransferInForm form = (TransferInForm) session.getAttribute("transferInForm");
            String ns1 = (form != null && form.getNs1() != null) ? form.getNs1() : "";
            String ns2 = (form != null && form.getNs2() != null) ? form.getNs2() : "";

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

        <div style="background-color:#f5f5f5; padding:10px; border-radius:5px; margin-bottom:20px;">
            <strong>移管対象ドメイン：</strong><%= form != null ? form.getDomainName() : "" %>
        </div>

        <div style="background-color:#f5f5f5; padding:20px; border-radius:5px; margin-bottom:20px;">
            <form method="post" action="transferInNs">
                <table border="1" cellpadding="5">
                    <tr>
                        <th>ネームサーバー1 *</th>
                        <td><input type="text" name="ns1" value="<%= ns1 %>"
                                   size="40" maxlength="255" placeholder="例: ns1.example.co.jp"></td>
                    </tr>
                    <tr>
                        <th>ネームサーバー2</th>
                        <td><input type="text" name="ns2" value="<%= ns2 %>"
                                   size="40" maxlength="255" placeholder="例: ns2.example.co.jp"></td>
                    </tr>
                </table>
                <div style="margin-top:15px;">
                    <button type="submit" style="padding:5px 15px;">確認画面へ</button>
                    <button type="button" style="padding:5px 15px; margin-left:10px;"
                            onclick="history.back()">戻る</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
