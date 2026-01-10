<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ドメイン情報検索</title>
<link rel="stylesheet" href="style.css">
</head>
<body>

	<!-- 左メニュー -->
	<%@ include file="leftMenu.jsp"%>

	<!-- メインコンテンツ（右側） -->
	<div class="content">

		<h2>ドメイン情報検索</h2>

		<form action="domainInfo" method="post">
			<label>ドメイン名：</label> <input type="text" name="domainName">
			<button type="submit">検索</button>
		</form>

		<hr>

		<!-- 検索後だけ表示 -->
		<c:if test="${searched}">
			<c:choose>

				<%-- 検索結果あり（1件以上） --%>
				<c:when test="${not empty domainList}">
					<h3>検索結果（${fn:length(domainList)} 件）</h3>

					<table border="1" cellpadding="5">
						<tr>
							<th>ドメイン名</th>
							<th>登録者</th>
							<th>NS1</th>
							<th>NS2</th>
							<th>登録年数</th>
						</tr>

						<c:forEach var="d" items="${domainList}">
							<tr>
								<td>${d.domainName}</td>
								<td>${d.registrant}</td>
								<td>${d.ns1}</td>
								<td>${d.ns2}</td>
								<td>${d.period}</td>
								<!-- 更新・削除リンク -->
								<td><a href="domainEdit?domainName=${d.domainName}">更新</a>
									&nbsp;|&nbsp; <a href="domainDelete?id=${d.id}"
									onclick="return confirm('本当に削除しますか？');"> 削除 </a></td>

							</tr>
						</c:forEach>
					</table>
				</c:when>

				<%-- 検索結果なし（0件） --%>
				<c:otherwise>
					<p>該当するドメインは見つかりませんでした。</p>
				</c:otherwise>

			</c:choose>
		</c:if>

	</div>
	<!-- /.content -->

	<!-- フッター -->
	<%@ include file="footer.jsp"%>

</body>
</html>