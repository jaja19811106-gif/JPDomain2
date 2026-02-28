<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ドメイン情報変更</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <jsp:include page="leftMenu.jsp" />

    <div class="content">
        <h2>ドメイン情報変更</h2>

        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
        <% if (errorMessage != null) { %>
            <div style="color:red; font-weight:bold; margin-bottom:10px;">
                <%= errorMessage %>
            </div>
        <% } %>

        <div style="background-color:#f5f5f5; padding:20px; border-radius:5px; margin-bottom:20px;">
            <form method="post" action="domainUpdateSearch">
                <label for="domainName">ドメイン名:</label><br>
                <input type="text" id="domainName" name="domainName"
                       placeholder="例: example.co.jp" required
                       style="width:300px; padding:5px; margin-top:5px;">
                <button type="submit" style="padding:5px 15px; margin-left:10px;">検索</button>
            </form>
        </div>
    </div>
</body>
</html>
