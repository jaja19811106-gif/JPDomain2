<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Domain Info Result</title>
<link rel="stylesheet" href="style.css">
</head>
<body>

<jsp:include page="leftMenu.jsp" />

<div class="content">
    <h2>Domain Info Result</h2>

    <pre>
${eppResponse}
    </pre>

    <a href="domainInfo.jsp">Back</a>
</div>

</body>
</html>