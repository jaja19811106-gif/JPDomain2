<%@ page contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" href="style.css">

<jsp:include page="leftMenu.jsp" />

<div class="content">
    <h2>ドメイン登録結果</h2>

    <pre>
<%= request.getAttribute("eppResponse") %>
    </pre>
</div>