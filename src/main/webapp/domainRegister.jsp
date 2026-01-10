<%@ page contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" href="style.css">

<jsp:include page="leftMenu.jsp" />

<div class="content">
    <h2>ドメイン登録</h2>

    <form action="domainRegister" method="post">
        ドメイン名：
        <input type="text" name="domainName"><br><br>

		<label>登録者名：</label>
		<input type="text" name="registrant"><br><br>


        登録年数：
        <select name="period">
            <option value="1">1年</option>
            <option value="2">2年</option>
            <option value="3">3年</option>
        </select><br><br>

        ネームサーバー1：
        <input type="text" name="ns1"><br><br>

        ネームサーバー2：
        <input type="text" name="ns2"><br><br>

        <input type="submit" value="登録">
    </form>
</div>