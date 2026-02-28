<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, dto.ContactDto, form.TransferInForm" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Transfer In - Contact選択</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <jsp:include page="leftMenu.jsp" />

    <div class="content">
        <h2>Transfer In - Contact選択</h2>

        <%
            List<ContactDto> contactList = (List<ContactDto>) request.getAttribute("contactList");
            TransferInForm form = (TransferInForm) session.getAttribute("transferInForm");
        %>

        <div style="background-color:#f5f5f5; padding:10px; border-radius:5px; margin-bottom:20px;">
            <strong>移管対象ドメイン：</strong><%= form != null ? form.getDomainName() : "" %>
        </div>

        <div style="background-color:#f5f5f5; padding:20px; border-radius:5px; margin-bottom:20px;">
            <form method="post" action="transferInContact">
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
                    <button type="submit" style="padding:5px 15px;">次へ（NS入力）</button>
                    <button type="button" style="padding:5px 15px; margin-left:10px;"
                            onclick="location.href='transferInSearch'">戻る</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
