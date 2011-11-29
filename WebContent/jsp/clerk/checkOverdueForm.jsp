<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Checking Overdue</title>
</head>
<body>
<div id="wrap">
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
	<div id="main_content">
	<h1>Overdue Items</h1>
	<p>Optionally enter a Borrower ID to filter the results.</p>
	<form action="/CrazyCoolLibrary/app/viewcheckoverduereport" method="get">
		<div><label for="borrowerId">Borrower Id<em>*</em></label><input id="borrowerId" name="bid" type="text"/></div>
		<button type="submit">Get Overdue Books</button>
	</form>
	</div>
</div>
</body>
</html>