<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ホスト登録完了</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <jsp:include page="leftMenu.jsp" />

    <div class="content">
        <h2>ホスト登録完了</h2>
        <p>ホストの登録が完了しました。</p>

        <%
            form.HostRegisterForm form = (form.HostRegisterForm) request.getAttribute("form");
        %>

        <div style="background-color:#e8f5e9; padding:20px; border-radius:5px; border:1px solid #4caf50; margin-bottom:20px;">
            <table border="1" cellpadding="5">
                <tr><th>ホスト名（FQDN）</th><td><%= form.getHostName() %></td></tr>
                <tr><th>IPv4アドレス</th>    <td><%= form.getIpv4() != null ? form.getIpv4() : "" %></td></tr>
                <tr><th>IPv6アドレス</th>    <td><%= form.getIpv6() != null ? form.getIpv6() : "" %></td></tr>
            </table>
        </div>

        <button type="button" style="padding:5px 15px;" onclick="location.href='hostRegisterInput.jsp'">続けて登録</button>
        <button type="button" style="padding:5px 15px; margin-left:10px;" onclick="location.href='menu.jsp'">メニューへ戻る</button>
    </div>
</body>
</html>
