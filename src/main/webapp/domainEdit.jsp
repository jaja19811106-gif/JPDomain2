<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ドメイン更新</title>
<link rel="stylesheet" href="style.css">
</head>
<body>

	<%@ include file="leftMenu.jsp"%>

	<div class="content">

		<h2>ドメイン情報更新</h2>

		<form action="domainUpdateConfirm" method="post">
			<input type="hidden" name="id" value="${domain.id}">

			<p>
				ドメイン名： <input type="text" name="domainName"
					value="${domain.domainName}">
			</p>

			<p>
				登録者： <input type="text" name="registrant"
					value="${domain.registrant}">
			</p>

			<p>
				NS1： <input type="text" name="ns1" value="${domain.ns1}">
			</p>

			<p>
				NS2： <input type="text" name="ns2" value="${domain.ns2}">
			</p>

			<p>
				登録年数： <input type="number" name="period" value="${domain.period}">
			</p>

			<button type="submit">更新する</button>
			
		</form>

	</div>

	<%@ include file="footer.jsp"%>

</body>
</html>