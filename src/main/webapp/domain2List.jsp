<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="style.css">
<jsp:include page="leftMenu.jsp" />

<div class="content">

    <h2>属性型JPドメイン 一覧</h2>

    <c:if test="${not empty message}">
        <div style="color:green; font-weight:bold;">
            ${message}
        </div>
    </c:if>

    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th>
            <th>法人番号</th>
            <th>属性型</th>
            <th>ドメイン名</th>
            <th>操作</th>
        </tr>

        <c:forEach var="d" items="${domainList}">
            <tr>
                <td>${d.id}</td>
                <td>${d.corporateNumber}</td>
                <td>${d.attributeType}</td>
                <td>${d.domainName}</td>
                <td>
                    <!-- 編集ボタン -->
                    <form action="domain2Edit" method="get" style="display:inline;">
                        <input type="hidden" name="id" value="${d.id}">
                        <button type="submit">編集</button>
                    </form>

                    <!-- 削除ボタン（必要なら） -->
                    <form action="domain2Delete" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="${d.id}">
                        <button type="submit"
                                onclick="return confirm('削除しますか？');">
                            削除
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>

    </table>

</div>