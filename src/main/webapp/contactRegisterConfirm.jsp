<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>コンタクト登録確認</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <jsp:include page="leftMenu.jsp" />

    <div class="content">
        <h2>コンタクト登録確認</h2>
        <p>以下の内容で登録します。よろしければ「登録」ボタンを押してください。</p>

        <%
            form.ContactRegisterForm form = (form.ContactRegisterForm) request.getAttribute("form");
            String contactTypeLabel = "";
            if      ("registrant".equals(form.getContactType())) contactTypeLabel = "Registrant（登録者）";
            else if ("admin".equals(form.getContactType()))       contactTypeLabel = "Admin（管理担当者）";
            else if ("tech".equals(form.getContactType()))        contactTypeLabel = "Tech（技術担当者）";
            String langLabel = "ja".equals(form.getLang()) ? "日本語" : "英語";
        %>

        <div style="background-color:#e8f5e9; padding:20px; border-radius:5px; border:1px solid #4caf50; margin-bottom:20px;">
            <table border="1" cellpadding="5">
                <tr><th>コンタクト種別</th><td><%= contactTypeLabel %></td></tr>
                <tr><th>言語種別</th>      <td><%= langLabel %></td></tr>
                <tr><th>氏名</th>          <td><%= form.getName() %></td></tr>
                <tr><th>組織名</th>        <td><%= form.getOrganization() %></td></tr>
                <tr><th>郵便番号</th>      <td><%= form.getPostalCode() %></td></tr>
                <tr><th>都道府県</th>      <td><%= form.getSp() %></td></tr>
                <tr><th>市区町村</th>      <td><%= form.getCity() %></td></tr>
                <tr><th>番地・建物名</th>  <td><%= form.getStreet() %></td></tr>
                <tr><th>国コード</th>      <td><%= form.getCc() %></td></tr>
                <tr><th>電話番号</th>      <td><%= form.getVoice() %></td></tr>
                <tr><th>FAX</th>           <td><%= form.getFax() %></td></tr>
                <tr><th>メールアドレス</th><td><%= form.getEmail() %></td></tr>
            </table>
        </div>

        <form method="post" action="contactRegisterExecute">
            <input type="hidden" name="contactType"  value="<%= form.getContactType() %>">
            <input type="hidden" name="lang"          value="<%= form.getLang() %>">
            <input type="hidden" name="name"          value="<%= form.getName() %>">
            <input type="hidden" name="organization"  value="<%= form.getOrganization() %>">
            <input type="hidden" name="postalCode"    value="<%= form.getPostalCode() %>">
            <input type="hidden" name="sp"            value="<%= form.getSp() %>">
            <input type="hidden" name="city"          value="<%= form.getCity() %>">
            <input type="hidden" name="street"        value="<%= form.getStreet() %>">
            <input type="hidden" name="cc"            value="<%= form.getCc() %>">
            <input type="hidden" name="voice"         value="<%= form.getVoice() %>">
            <input type="hidden" name="fax"           value="<%= form.getFax() %>">
            <input type="hidden" name="email"         value="<%= form.getEmail() %>">
            <input type="hidden" name="aaa"           value="">

            <button type="submit" style="padding:5px 15px;">登録</button>
            <button type="button" style="padding:5px 15px; margin-left:10px;" onclick="history.back()">戻る</button>
        </form>
    </div>
</body>
</html>
