<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dto.DomainDto" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Transfer Out 完了</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <jsp:include page="leftMenu.jsp" />

    <div class="content">
        <h2>Transfer Out 完了</h2>
        <p>Lock解除が完了しました。認証コードを移管先に提供してください。</p>

        <%
            DomainDto domain = (DomainDto) request.getAttribute("domain");
        %>

        <div style="background-color:#e8f5e9; padding:20px; border-radius:5px; border:1px solid #4caf50; margin-bottom:20px;">
            <table border="1" cellpadding="5">
                <tr><th>ドメイン名</th>    <td><%= domain.getDomainName() %></td></tr>
                <tr><th>Lock状態</th>      <td>解除済み</td></tr>
                <tr><th>認証コード</th>    <td><strong><%= domain.getAuthCode() %></strong></td></tr>
                <tr><th>有効期限</th>      <td><%= domain.getAuthCodeExpiresAt() %></td></tr>
            </table>
        </div>

        <button type="button" style="padding:5px 15px;"
                onclick="location.href='transferOutSearch'">続けてLock解除</button>
        <button type="button" style="padding:5px 15px; margin-left:10px;"
                onclick="location.href='menu.jsp'">メニューへ戻る</button>
    </div>
</body>
</html>
