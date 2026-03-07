<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="style.css">

<jsp:include page="leftMenu.jsp" />
<div class="content">
    <h2>属性型JPドメイン 新規登録（入力）</h2>

    <c:if test="${not empty errorMessage}">
        <div style="color: red; font-weight: bold;">${errorMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessages}">
        <div style="color: red; font-weight: bold;">
            <ul>
                <c:forEach var="msg" items="${errorMessages}">
                    <li>${msg}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <form action="domainRegisterCheck" method="post">

        <table>
            <tr>
                <td>法人番号（13桁）</td>
                <td><input type="text" name="corporateNumber"
                    value="${corporateNumber}" maxlength="13" required></td>
            </tr>
            <tr>
                <td>属性型</td>
                <td><select name="attributeType" required>
                    <option value="">選択してください</option>
                    <option value="co.jp"  <c:if test="${attributeType == 'co.jp'}">selected</c:if>>co.jp（会社法人）</option>
                    <option value="or.jp"  <c:if test="${attributeType == 'or.jp'}">selected</c:if>>or.jp（非営利法人）</option>
                    <option value="ac.jp"  <c:if test="${attributeType == 'ac.jp'}">selected</c:if>>ac.jp（大学・高専）</option>
                    <option value="ed.jp"  <c:if test="${attributeType == 'ed.jp'}">selected</c:if>>ed.jp（学校）</option>
                    <option value="ne.jp"  <c:if test="${attributeType == 'ne.jp'}">selected</c:if>>ne.jp（ネットワークサービス）</option>
                </select></td>
            </tr>
            <tr>
                <td>登録するドメイン名</td>
                <td><input type="text" name="domainName" value="${domainName}" required></td>
            </tr>
        </table>

        <br>

        <h3>ネットワークIPアドレス範囲（任意）</h3>
        <p style="color:#666; font-size:0.9em;">
            ※ FROM・TOはどちらか一方だけの入力はできません。両方入力するかどちらも空欄にしてください。
        </p>

        <table border="1" cellpadding="5">
            <tr>
                <th>No.</th>
                <th>FROM</th>
                <th>TO</th>
            </tr>
            <tr>
                <td>1</td>
                <td><input type="text" name="ip1_from" value="${ip1_from}" placeholder="例: 192.168.0.1"   maxlength="15" size="18"></td>
                <td><input type="text" name="ip1_to"   value="${ip1_to}"   placeholder="例: 192.168.0.255" maxlength="15" size="18"></td>
            </tr>
            <tr>
                <td>2</td>
                <td><input type="text" name="ip2_from" value="${ip2_from}" placeholder="例: 192.168.0.1"   maxlength="15" size="18"></td>
                <td><input type="text" name="ip2_to"   value="${ip2_to}"   placeholder="例: 192.168.0.255" maxlength="15" size="18"></td>
            </tr>
            <tr>
                <td>3</td>
                <td><input type="text" name="ip3_from" value="${ip3_from}" placeholder="例: 192.168.0.1"   maxlength="15" size="18"></td>
                <td><input type="text" name="ip3_to"   value="${ip3_to}"   placeholder="例: 192.168.0.255" maxlength="15" size="18"></td>
            </tr>
            <tr>
                <td>4</td>
                <td><input type="text" name="ip4_from" value="${ip4_from}" placeholder="例: 192.168.0.1"   maxlength="15" size="18"></td>
                <td><input type="text" name="ip4_to"   value="${ip4_to}"   placeholder="例: 192.168.0.255" maxlength="15" size="18"></td>
            </tr>
            <tr>
                <td>5</td>
                <td><input type="text" name="ip5_from" value="${ip5_from}" placeholder="例: 192.168.0.1"   maxlength="15" size="18"></td>
                <td><input type="text" name="ip5_to"   value="${ip5_to}"   placeholder="例: 192.168.0.255" maxlength="15" size="18"></td>
            </tr>
        </table>

        <br>
        <button type="submit">登録チェックへ進む</button>

    </form>
</div>
