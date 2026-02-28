<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ドメイン情報変更確認</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <jsp:include page="leftMenu.jsp" />

    <div class="content">
        <h2>ドメイン情報変更確認</h2>
        <p>以下の内容で変更します。よろしければ「変更」ボタンを押してください。</p>

        <%
            form.DomainUpdateForm form = (form.DomainUpdateForm) request.getAttribute("form");
            String registrantName   = (String) request.getAttribute("registrantName");
            String adminContactName = (String) request.getAttribute("adminContactName");
            String techContactName  = (String) request.getAttribute("techContactName");
        %>

        <div style="background-color:#e8f5e9; padding:20px; border-radius:5px; border:1px solid #4caf50; margin-bottom:20px;">
            <table border="1" cellpadding="5">
                <tr><th>ドメイン名</th>           <td><%= form.getDomainName() %></td></tr>
                <tr><th>Registrant</th>            <td><%= form.getRegistrant() %> / <%= registrantName %></td></tr>
                <tr><th>Admin Contact</th>         <td><%= form.getAdminContact() %> / <%= adminContactName %></td></tr>
                <tr><th>Tech Contact</th>          <td><%= form.getTechContact() %> / <%= techContactName %></td></tr>
                <tr><th>ネームサーバー1</th>       <td><%= form.getNs1() %></td></tr>
                <tr><th>ネームサーバー2</th>       <td><%= form.getNs2() != null ? form.getNs2() : "" %></td></tr>
            </table>
        </div>

        <form method="post" action="domainInfoUpdateConfirm">
            <button type="submit" style="padding:5px 15px;">変更</button>
            <button type="button" style="padding:5px 15px; margin-left:10px;"
                    onclick="history.back()">戻る</button>
        </form>
    </div>
</body>
</html>
