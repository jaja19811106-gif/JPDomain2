<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="style.css">
<jsp:include page="leftMenu.jsp" />

<div class="content">

    <h2>属性型JPドメイン 新規登録（完了）</h2>

    <c:if test="${not empty message}">
        <div style="color:green; font-weight:bold;">
            ${message}
        </div>
    </c:if>

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
            <td>登録したドメイン名</td>
            <td>${param.domainName}</td>
        </tr>
    </table>

    <br>

    <form action="domainRegisterInput.jsp" method="get">
        <button type="submit">新規登録へ戻る</button>
    </form>

</div>