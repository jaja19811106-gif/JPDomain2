<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>コンタクト登録</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <jsp:include page="leftMenu.jsp" />

    <div class="content">
        <h2>コンタクト登録</h2>

        <%
            List<String> errorMessages = (List<String>) request.getAttribute("errorMessages");
            if (errorMessages != null && !errorMessages.isEmpty()) {
        %>
            <div style="color:red; font-weight:bold; margin-bottom:10px;">
                <ul>
                <% for (String msg : errorMessages) { %>
                    <li><%= msg %></li>
                <% } %>
                </ul>
            </div>
        <% } %>

        <%
            form.ContactRegisterForm form = (form.ContactRegisterForm) request.getAttribute("form");
            String contactType  = (form != null && form.getContactType()  != null) ? form.getContactType()  : "";
            String lang         = (form != null && form.getLang()         != null) ? form.getLang()         : "ja";
            String name         = (form != null && form.getName()         != null) ? form.getName()         : "";
            String organization = (form != null && form.getOrganization() != null) ? form.getOrganization() : "";
            String postalCode   = (form != null && form.getPostalCode()   != null) ? form.getPostalCode()   : "";
            String sp           = (form != null && form.getSp()           != null) ? form.getSp()           : "";
            String city         = (form != null && form.getCity()         != null) ? form.getCity()         : "";
            String street       = (form != null && form.getStreet()       != null) ? form.getStreet()       : "";
            String cc           = (form != null && form.getCc()           != null) ? form.getCc()           : "JP";
            String voice        = (form != null && form.getVoice()        != null) ? form.getVoice()        : "";
            String fax          = (form != null && form.getFax()          != null) ? form.getFax()          : "";
            String email        = (form != null && form.getEmail()        != null) ? form.getEmail()        : "";
        %>

        <div style="background-color:#f5f5f5; padding:20px; border-radius:5px; margin-bottom:20px;">
            <form method="post" action="contactRegisterConfirm">
                <input type="hidden" name="aaa" value="">

                <table border="1" cellpadding="5">
                    <tr>
                        <th>コンタクト種別 *</th>
                        <td>
                            <select name="contactType">
                                <option value="">-- 選択してください --</option>
                                <option value="registrant" <%= "registrant".equals(contactType) ? "selected" : "" %>>Registrant（登録者）</option>
                                <option value="admin"      <%= "admin".equals(contactType)      ? "selected" : "" %>>Admin（管理担当者）</option>
                                <option value="tech"       <%= "tech".equals(contactType)       ? "selected" : "" %>>Tech（技術担当者）</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>言語種別 *</th>
                        <td>
                            <label><input type="radio" name="lang" value="ja" <%= "ja".equals(lang) ? "checked" : "" %>> 日本語</label>
                            &nbsp;
                            <label><input type="radio" name="lang" value="en" <%= "en".equals(lang) ? "checked" : "" %>> 英語</label>
                        </td>
                    </tr>
                    <tr>
                        <th>氏名 *</th>
                        <td><input type="text" name="name" value="<%= name %>" size="40" maxlength="255"></td>
                    </tr>
                    <tr>
                        <th>組織名</th>
                        <td><input type="text" name="organization" value="<%= organization %>" size="40" maxlength="255"></td>
                    </tr>
                    <tr>
                        <th>郵便番号 *</th>
                        <td><input type="text" name="postalCode" value="<%= postalCode %>" size="10" maxlength="10" placeholder="例: 101-0001"></td>
                    </tr>
                    <tr>
                        <th>都道府県 *</th>
                        <td><input type="text" name="sp" value="<%= sp %>" size="20" maxlength="255"></td>
                    </tr>
                    <tr>
                        <th>市区町村 *</th>
                        <td><input type="text" name="city" value="<%= city %>" size="30" maxlength="255"></td>
                    </tr>
                    <tr>
                        <th>番地・建物名 *</th>
                        <td><input type="text" name="street" value="<%= street %>" size="40" maxlength="255"></td>
                    </tr>
                    <tr>
                        <th>国コード *</th>
                        <td>
                            <select name="cc">
                                <option value="JP" <%= "JP".equals(cc) ? "selected" : "" %>>JP（日本）</option>
                                <option value="US" <%= "US".equals(cc) ? "selected" : "" %>>US（アメリカ）</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>電話番号 *</th>
                        <td><input type="text" name="voice" value="<%= voice %>" size="20" maxlength="17" placeholder="例: +81.312345678"></td>
                    </tr>
                    <tr>
                        <th>FAX</th>
                        <td><input type="text" name="fax" value="<%= fax %>" size="20" maxlength="17" placeholder="例: +81.312345679"></td>
                    </tr>
                    <tr>
                        <th>メールアドレス *</th>
                        <td><input type="text" name="email" value="<%= email %>" size="40" maxlength="255"></td>
                    </tr>
                </table>

                <div style="margin-top:15px;">
                    <button type="submit" style="padding:5px 15px;">確認画面へ</button>
                    <button type="button" style="padding:5px 15px; margin-left:10px;" onclick="location.href='menu.jsp'">キャンセル</button>
                </div>

            </form>
        </div>
    </div>
</body>
</html>
