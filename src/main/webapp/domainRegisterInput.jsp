<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>属性型JPドメイン 新規登録（入力）</title>
</head>
<body>

<h2>属性型JPドメイン 新規登録（入力）</h2>

<c:if test="${not empty errorMessage}">
    <div style="color:red; font-weight:bold;">
        ${errorMessage}
    </div>
</c:if>

<form action="domainRegisterCheck" method="post">

    <table>
        <tr>
            <td>法人番号（13桁）</td>
            <td>
                <input type="text" name="corporateNumber"
                       value="${corporateNumber}" maxlength="13" required>
            </td>
        </tr>

        <tr>
            <td>属性型</td>
            <td>
                <select name="attributeType" required>
                    <option value="">選択してください</option>
                    <option value="co.jp">co.jp（会社法人）</option>
                    <option value="or.jp">or.jp（非営利法人）</option>
                    <option value="ac.jp">ac.jp（大学・高専）</option>
                    <option value="ed.jp">ed.jp（学校）</option>
                    <option value="ne.jp">ne.jp（ネットワークサービス）</option>
                </select>
            </td>
        </tr>

        <tr>
            <td>登録するドメイン名</td>
            <td>
                <input type="text" name="domainName"
                       value="${domainName}" required>
            </td>
        </tr>
    </table>

    <br>

    <button type="submit">登録チェックへ進む</button>

</form>

</body>
</html>