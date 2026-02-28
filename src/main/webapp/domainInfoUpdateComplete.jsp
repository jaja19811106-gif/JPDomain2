<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ドメイン情報変更完了</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <jsp:include page="leftMenu.jsp" />

    <div class="content">
        <h2>ドメイン情報変更完了</h2>
        <p>ドメイン情報の変更が完了しました。</p>

        <%
            String domainName = (String) request.getAttribute("domainName");
        %>

        <div style="background-color:#e8f5e9; padding:20px; border-radius:5px; border:1px solid #4caf50; margin-bottom:20px;">
            <table border="1" cellpadding="5">
                <tr><th>ドメイン名</th><td><%= domainName %></td></tr>
            </table>
        </div>

        <button type="button" style="padding:5px 15px;"
                onclick="location.href='domainUpdateSearch'">続けて変更</button>
        <button type="button" style="padding:5px 15px; margin-left:10px;"
                onclick="location.href='menu.jsp'">メニューへ戻る</button>
    </div>
</body>
</html>
