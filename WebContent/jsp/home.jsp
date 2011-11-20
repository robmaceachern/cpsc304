<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home sweet home</title>
</head>
<body>
<h1>Welcome to the home page of CrazyCoolLibrary</h1>
<h2>${someVariable}</h2>

<p>The system has the following tables:</p>
<ul>
	<c:forEach items="${tables}" var="table">
		<li>${table.tableName} (# of rows: ${table.numRows})</li>
	</c:forEach>
</ul>

</body>
</html>