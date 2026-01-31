<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="style.css">
<jsp:include page="leftMenu.jsp" />

<div class="content">

    <h2>属性型JPドメイン 更新（確認）</h2>

    <form action="domain2Update" method="post">

        <input type="hidden" name="id" value="${domain.id}">
        <input type="hidden" name="corporateNumber" value="${domain.corporateNumber}">
        <input type="hidden" name="attributeType" value="${domain.attributeType}">
        <input type="hidden" name="domainName" value="${domain.domainName}">

        <table>
            <tr><td>ID</td><td>${domain.id}</td></tr>
            <tr><td>法人番号</td><td>${domain.corporateNumber}</td></tr>
            <tr><td>属性型</td><td>${domain.attributeType}</td></tr>
            <tr><td>ドメイン名</td><td>${domain.domainName}</td></tr>
        </table>

        <br>

        <button type="submit">更新する</button>
        <button type="button" onclick="history.back()">戻る</button>

    </form>

</div>