<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dto.DomainDto" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Transfer Out 確認</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <jsp:include page="leftMenu.jsp" />

    <div class="content">
        <h2>Transfer Out 確認</h2>
        <p>以下のドメインのLockを解除します。よろしければ「Lock解除」ボタンを押してください。</p>

        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
        <% if (errorMessage != null) { %>
            <div style="color:red; font-weight:bold; margin-bottom:10px;">
                <%= errorMessage %>
            </div>
        <% } %>

        <%
            DomainDto domain = (DomainDto) request.getAttribute("domain");
        %>

        <div style="background-color:#fff3e0; padding:20px; border-radius:5px; border:1px solid #ff9800; margin-bottom:20px;">
            <table border="1" cellpadding="5">
                <tr><th>ドメイン名</th><td><%= domain.getDomainName() %></td></tr>
                <tr><th>現在のLock状態</th><td><%= domain.isLocked() ? "ロック中" : "解除済み" %></td></tr>
                <tr><th>Registrant</th><td><%= domain.getRegistrant() != null ? domain.getRegistrant() : "" %></td></tr>
            </table>
            <p style="color:red; margin-top:10px;">
                ※ Lock解除後、認証コードが発行されます。認証コードは移管先に提供してください。
            </p>
        </div>

        <form method="post" action="transferOutConfirm">
            <button type="submit" style="padding:5px 15px;">Lock解除</button>
            <button type="button" style="padding:5px 15px; margin-left:10px;"
                    onclick="location.href='transferOutSearch'">戻る</button>
        </form>
    </div>
</body>
</html>
