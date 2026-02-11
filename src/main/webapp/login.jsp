<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン</title>
</head>
<body>

<h2>ログイン</h2>
<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>
<form action="login" method="post" autocomplete="off">

    <label>事業者番号：</label>
    <input type="text" name="providerNo" autocomplete="off"><br><br>

    <label>ユーザーID：</label>
    <input type="text" name="userId" autocomplete="username"><br><br>

    <label>パスワード：</label>
    <input type="password" name="password" autocomplete="current-password"><br><br>

    <input type="submit" value="ログイン">
</form>

</body>
</html>