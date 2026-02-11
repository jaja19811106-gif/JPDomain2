<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="style.css">
<jsp:include page="leftMenu.jsp" />

<div class="content">

    <h2>属性型JPドメイン 更新（入力）</h2>

    <form action="domain2UpdateCheck" method="post">

        <!-- 更新に必要なのは ID だけ -->
        <input type="hidden" name="id" value="${domain.id}">
        <input type="hidden" name="authCode" value="${domain.authCode}">
        
        <table>

            <tr>
                <td>法人番号</td>
                <td>
                    <input type="text" name="corporateNumber"
                           value="${domain.corporateNumber}" required>
                </td>
            </tr>

            <tr>
                <td>属性型</td>
                <td>
                    <select name="attributeType" required>
                        <option value="co.jp" <c:if test="${domain.attributeType == 'co.jp'}">selected</c:if>>co.jp</option>
                        <option value="or.jp" <c:if test="${domain.attributeType == 'or.jp'}">selected</c:if>>or.jp</option>
                        <option value="ac.jp" <c:if test="${domain.attributeType == 'ac.jp'}">selected</c:if>>ac.jp</option>
                        <option value="ed.jp" <c:if test="${domain.attributeType == 'ed.jp'}">selected</c:if>>ed.jp</option>
                        <option value="ne.jp" <c:if test="${domain.attributeType == 'ne.jp'}">selected</c:if>>ne.jp</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td>ドメイン名</td>
                <td>
                    <input type="text" name="domainName"
                           value="${domain.domainName}" required>
                </td>
            </tr>

            <!-- ★ 認証コード（表示のみ） -->
            <tr>
                <td>認証コード</td>
                <td>
                    <span style="font-weight:bold;">
                        ${domain.authCode}
                    </span>
                </td>
            </tr>

            <!-- ★ ステータス（動的生成） -->
            <tr>
                <td>ステータス</td>
                <td>
                    <select name="status">
                        <c:forEach var="statusEnum" items="${statusList}">
                            <option value="${statusEnum.code}"
                                <c:if test="${domain.status == statusEnum.code}">selected</c:if>>
                                ${statusEnum.name()}
                            </option>
                        </c:forEach>
                    </select>
                </td>
            </tr>

        </table>

        <br>

        <button type="submit">更新内容を確認する</button>
        <button type="button" onclick="history.back()">戻る</button>

    </form>

</div>