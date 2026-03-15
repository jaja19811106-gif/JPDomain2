<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="style.css">
<jsp:include page="leftMenu.jsp" />

<div class="content">

    <h2>属性型JPドメイン 新規登録（確認）</h2>

    <table>
        <tr><td>法人番号（13桁）</td><td>${param.corporateNumber}</td></tr>
        <tr><td>属性型</td>          <td>${param.attributeType}</td></tr>
        <tr><td>登録するドメイン名</td><td>${param.domainName}</td></tr>
    </table>

    <br>

    <h3>ホストアドレス</h3>
    <table border="1" cellpadding="5">
        <tr><th>No.</th><th>IPアドレス</th></tr>
        <tr><td>1</td><td>${param.host1}</td></tr>
        <tr><td>2</td><td>${param.host2}</td></tr>
        <tr><td>3</td><td>${param.host3}</td></tr>
        <tr><td>4</td><td>${param.host4}</td></tr>
        <tr><td>5</td><td>${param.host5}</td></tr>
    </table>

    <br>

    <h3>ネットワークIPアドレス範囲</h3>
    <table border="1" cellpadding="5">
        <tr><th>No.</th><th>FROM</th><th>TO</th></tr>
        <c:if test="${not empty param.ip1_from or not empty param.ip1_to}">
            <tr><td>1</td><td>${param.ip1_from}</td><td>${param.ip1_to}</td></tr>
        </c:if>
        <c:if test="${not empty param.ip2_from or not empty param.ip2_to}">
            <tr><td>2</td><td>${param.ip2_from}</td><td>${param.ip2_to}</td></tr>
        </c:if>
        <c:if test="${not empty param.ip3_from or not empty param.ip3_to}">
            <tr><td>3</td><td>${param.ip3_from}</td><td>${param.ip3_to}</td></tr>
        </c:if>
        <c:if test="${not empty param.ip4_from or not empty param.ip4_to}">
            <tr><td>4</td><td>${param.ip4_from}</td><td>${param.ip4_to}</td></tr>
        </c:if>
        <c:if test="${not empty param.ip5_from or not empty param.ip5_to}">
            <tr><td>5</td><td>${param.ip5_from}</td><td>${param.ip5_to}</td></tr>
        </c:if>
    </table>

    <form action="organizationDomainRegister" method="post">
        <input type="hidden" name="corporateNumber" value="${param.corporateNumber}">
        <input type="hidden" name="attributeType"   value="${param.attributeType}">
        <input type="hidden" name="domainName"       value="${param.domainName}">
        <input type="hidden" name="host1"            value="${param.host1}">
        <input type="hidden" name="host2"            value="${param.host2}">
        <input type="hidden" name="host3"            value="${param.host3}">
        <input type="hidden" name="host4"            value="${param.host4}">
        <input type="hidden" name="host5"            value="${param.host5}">
        <input type="hidden" name="ip1_from" value="${param.ip1_from}">
        <input type="hidden" name="ip1_to"   value="${param.ip1_to}">
        <input type="hidden" name="ip2_from" value="${param.ip2_from}">
        <input type="hidden" name="ip2_to"   value="${param.ip2_to}">
        <input type="hidden" name="ip3_from" value="${param.ip3_from}">
        <input type="hidden" name="ip3_to"   value="${param.ip3_to}">
        <input type="hidden" name="ip4_from" value="${param.ip4_from}">
        <input type="hidden" name="ip4_to"   value="${param.ip4_to}">
        <input type="hidden" name="ip5_from" value="${param.ip5_from}">
        <input type="hidden" name="ip5_to"   value="${param.ip5_to}">
        <br>
        <button type="submit">この内容で登録する</button>
        <button type="button" onclick="history.back()">戻る</button>
    </form>
</div>
