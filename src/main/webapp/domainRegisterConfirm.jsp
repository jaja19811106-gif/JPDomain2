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

    <h3>ネットワークIPアドレス範囲</h3>
    <table border="1" cellpadding="5">
        <tr><th>No.</th><th>FROM</th><th>TO</th></tr>
        <c:forEach begin="1" end="5" var="i">
            <c:set var="from" value="${param['ip' += i += '_from']}" />
            <c:set var="to"   value="${param['ip' += i += '_to']}" />
            <c:if test="${not empty from or not empty to}">
                <tr>
                    <td>${i}</td>
                    <td>${from}</td>
                    <td>${to}</td>
                </tr>
            </c:if>
        </c:forEach>
    </table>

    <!-- hidden で値を保持 -->
    <form action="organizationDomainRegister" method="post">
        <input type="hidden" name="corporateNumber" value="${param.corporateNumber}">
        <input type="hidden" name="attributeType"   value="${param.attributeType}">
        <input type="hidden" name="domainName"       value="${param.domainName}">
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
