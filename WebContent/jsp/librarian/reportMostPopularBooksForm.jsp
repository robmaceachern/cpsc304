<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Most popular books report</title>
</head>
<body>
<div id="wrap">
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
	<div id="main_content">
	<h1>Most Popular Books Report</h1>
	<p>Enter the borrowing year and count of books to display.</p>
	<!-- I'm using GET for this form because we're not changing the server state 
			based on the year or limit. It's just a filtering mechanism. -->
	<form action="/CrazyCoolLibrary/app/viewmostpopularbooksreport" method="get">
		<div><label for="year">Year<em>*</em></label><input id="year" name="year" type="text"/></div>
		<div><label for="limit">Results limit<em>*</em></label><input id="limit" name="limit" type="text"/></div>
		<button type="submit">Generate report</button>
	</form>
	</div>
</div>
</body>
</html>