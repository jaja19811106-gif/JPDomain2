<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>コンタクト登録完了</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <jsp:include page="leftMenu.jsp" />

    <div class="content">
        <h2>コンタクト登録完了</h2>
        <p>コンタクトの登録が完了しました。</p>

        <%
            form.ContactRegisterForm form = (form.ContactRegisterForm) request.getAttribute("form");
            String contactId = (String) request.getAttribute("contactId");
            String contactTypeLabel = "";
            if      ("registrant".equals(form.getContactType())) contactTypeLabel = "Registrant（登録者）";
            else if ("admin".equals(form.getContactType()))       contactTypeLabel = "Admin（管理担当者）";
            else if ("tech".equals(form.getContactType()))        contactTypeLabel = "Tech（技術担当者）";
        %>

        <div style="background-color:#e8f5e9; padding:20px; border-radius:5px; border:1px solid #4caf50; margin-bottom:20px;">
            <table border="1" cellpadding="5">
                <tr><th>コンタクトID</th>  <td><%= contactId %></td></tr>
                <tr><th>コンタクト種別</th><td><%= contactTypeLabel %></td></tr>
                <tr><th>氏名</th>          <td><%= form.getName() %></td></tr>
                <tr><th>メールアドレス</th><td><%= form.getEmail() %></td></tr>
            </table>
            <input type="hidden" name="aaa" value="">
        </div>

        <button type="button" style="padding:5px 15px;" onclick="location.href='contactRegisterInput.jsp'">続けて登録</button>
        <button type="button" style="padding:5px 15px; margin-left:10px;" onclick="location.href='menu.jsp'">メニューへ戻る</button>
    </div>
</body>
</html>
