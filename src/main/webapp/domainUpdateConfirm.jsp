<%@ page contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" href="style.css">
<jsp:include page="leftMenu.jsp" />

<div class="content">

<h2>更新内容の確認</h2>

<table border="1" cellpadding="5">
    <tr><th>ドメイン名</th><td>${domainName}</td></tr>
    <tr><th>登録者</th><td>${registrant}</td></tr>
    <tr><th>NS1</th><td>${ns1}</td></tr>
    <tr><th>NS2</th><td>${ns2}</td></tr>
    <tr><th>登録年数</th><td>${period}</td></tr>
</table>

<br>

<!-- OK → 更新実行 -->
<form action="domainUpdate" method="post" style="display:inline;">
    <input type="hidden" name="id" value="${id}">
    <input type="hidden" name="domainName" value="${domainName}">
    <input type="hidden" name="registrant" value="${registrant}">
    <input type="hidden" name="ns1" value="${ns1}">
    <input type="hidden" name="ns2" value="${ns2}">
    <input type="hidden" name="period" value="${period}">
    <button type="submit">この内容で更新する</button>
</form>

&nbsp;&nbsp;

<!-- 戻る → 編集画面へ（POSTで値を返す） -->
<form action="domainEdit" method="post" style="display:inline;">
    <input type="hidden" name="id" value="${id}">
    <input type="hidden" name="domainName" value="${domainName}">
    <input type="hidden" name="registrant" value="${registrant}">
    <input type="hidden" name="ns1" value="${ns1}">
    <input type="hidden" name="ns2" value="${ns2}">
    <input type="hidden" name="period" value="${period}">
    <button type="submit">戻る</button>
</form>

</div>