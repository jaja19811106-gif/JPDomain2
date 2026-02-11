<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.OrganizationDomain" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>認証コード照会</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <jsp:include page="leftMenu.jsp" />
    
    <div class="content">
        <h2>認証コード照会</h2>
        
        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
        <% if (errorMessage != null) { %>
            <div style="color:red; font-weight:bold; margin-bottom:10px;">
                <%= errorMessage %>
            </div>
        <% } %>
        
        <div style="background-color:#f5f5f5; padding:20px; border-radius:5px; margin-bottom:20px;">
            <form method="post" action="domainAuthCodeSearch">
                <label for="domainName">ドメイン名:</label><br>
                <input type="text" id="domainName" name="domainName" 
                       placeholder="例: example.com" required
                       style="width:300px; padding:5px; margin-top:5px;">
                <button type="submit" style="padding:5px 15px; margin-left:10px;">検索</button>
            </form>
        </div>
        
        <% OrganizationDomain domain = (OrganizationDomain) request.getAttribute("domain"); %>
        <% if (domain != null) { %>
            <div style="background-color:#e8f5e9; padding:20px; border-radius:5px; border:1px solid #4caf50;">
                <h3>検索結果</h3>
                <table border="1" cellpadding="5">
                    <tr>
                        <th>ドメイン名</th>
                        <td><%= domain.getDomainName() %></td>
                    </tr>
                    <tr>
                        <th>認証コード</th>
                        <td>
                            <% if (domain.getAuthCode() != null && !domain.getAuthCode().isEmpty()) { %>
                                <%= domain.getAuthCode() %>
                            <% } else { %>
                                <em>未設定</em>
                            <% } %>
                        </td>
                    </tr>
                    <tr>
                        <th>有効期限</th>
                        <td>
                            <% if (domain.getAuthCodeExpiresAt() != null) { %>
                                <%= domain.getAuthCodeExpiresAt() %>
                            <% } else { %>
                                <em>未設定</em>
                            <% } %>
                        </td>
                    </tr>
                </table>
            </div>
        <% } %>
    </div>
</body>
</html>