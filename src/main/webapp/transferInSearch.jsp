<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Transfer In（移管申請）</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <jsp:include page="leftMenu.jsp" />

    <div class="content">
        <h2>Transfer In（移管申請）</h2>

        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
        <% if (errorMessage != null) { %>
            <div style="color:red; font-weight:bold; margin-bottom:10px;">
                <%= errorMessage %>
            </div>
        <% } %>

        <div style="background-color:#f5f5f5; padding:20px; border-radius:5px; margin-bottom:20px;">
            <form method="post" action="transferInSearch">
                <table border="1" cellpadding="5">
                    <tr>
                        <th>ドメイン名 *</th>
                        <td><input type="text" name="domainName"
                                   placeholder="例: example.co.jp" required
                                   style="width:300px; padding:5px;"></td>
                    </tr>
                    <tr>
                        <th>認証コード *</th>
                        <td><input type="text" name="authCode"
                                   placeholder="認証コードを入力" required
                                   style="width:300px; padding:5px;"></td>
                    </tr>
                </table>
                <div style="margin-top:15px;">
                    <button type="submit" style="padding:5px 15px;">次へ（Contact選択）</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
