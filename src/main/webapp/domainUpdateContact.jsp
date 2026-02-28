<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, dto.ContactDto, dto.DomainDto" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ドメイン情報変更 - Contact変更</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <jsp:include page="leftMenu.jsp" />

    <div class="content">
        <h2>ドメイン情報変更 - Contact変更</h2>

        <%
            DomainDto domain = (DomainDto) request.getAttribute("domain");
            List<ContactDto> contactList = (List<ContactDto>) request.getAttribute("contactList");
        %>

        <div style="background-color:#f5f5f5; padding:10px; border-radius:5px; margin-bottom:20px;">
            <strong>変更対象ドメイン：</strong><%= domain.getDomainName() %>
        </div>

        <div style="background-color:#f5f5f5; padding:20px; border-radius:5px; margin-bottom:20px;">
            <form method="post" action="${pageContext.request.contextPath}/domainUpdateContact">
                <table border="1" cellpadding="5">
                    <tr>
                        <th>Registrant（登録者）</th>
                        <td>
                            <select name="registrant">
                                <option value="">-- 選択してください --</option>
                                <% for (ContactDto c : contactList) { %>
                                    <option value="<%= c.getContactId() %>">
                                        <%= c.getContactId() %> / <%= c.getName() %>
                                    </option>
                                <% } %>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>Admin Contact（管理担当者）</th>
                        <td>
                            <select name="adminContact">
                                <option value="">-- 選択してください --</option>
                                <% for (ContactDto c : contactList) { %>
                                    <option value="<%= c.getContactId() %>">
                                        <%= c.getContactId() %> / <%= c.getName() %>
                                    </option>
                                <% } %>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>Tech Contact（技術担当者）</th>
                        <td>
                            <select name="techContact">
                                <option value="">-- 選択してください --</option>
                                <% for (ContactDto c : contactList) { %>
                                    <option value="<%= c.getContactId() %>">
                                        <%= c.getContactId() %> / <%= c.getName() %>
                                    </option>
                                <% } %>
                            </select>
                        </td>
                    </tr>
                </table>

                <div style="margin-top:15px;">
                    <button type="submit" style="padding:5px 15px;">次へ（NS変更）</button>
                    <button type="button" style="padding:5px 15px; margin-left:10px;"
                            onclick="location.href='domainUpdateSearch'">戻る</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
