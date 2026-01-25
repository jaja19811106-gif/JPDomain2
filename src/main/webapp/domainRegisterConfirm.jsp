<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="style.css">
<jsp:include page="leftMenu.jsp" />

<div class="content">

    <h2>属性型JPドメイン 新規登録（確認）</h2>

    <table>
        <tr>
            <td>法人番号（13桁）</td>
            <td>${param.corporateNumber}</td>
        </tr>

        <tr>
            <td>属性型</td>
            <td>${param.attributeType}</td>
        </tr>

        <tr>
            <td>登録するドメイン名</td>
            <td>${param.domainName}</td>
        </tr>
    </table>

    <!-- hidden で値を保持 -->
    <form action="organizationDomainRegister" method="post">
        <input type="hidden" name="corporateNumber" value="${param.corporateNumber}">
        <input type="hidden" name="attributeType" value="${param.attributeType}">
        <input type="hidden" name="domainName" value="${param.domainName}">

        <br>
        <button type="submit">この内容で登録する</button>
        <button type="button" onclick="history.back()">戻る</button>
    </form>

</div>