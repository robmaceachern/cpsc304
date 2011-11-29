<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Process Return</title>
</head>
<body>
<div id="wrap">
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
	<div id="main_content">
	<h1>Process Return</h1>
	<p>Enter the catalogue number of the book to return.</p>
	<form action="/CrazyCoolLibrary/app/processreturnsubmit" method="post">
		<div><label for="callNumber">Call Number<em>*</em></label><input id="callNumber" name="callNumber" type="text"/></div>
		<div><label for="copyNo">Copy Number<em>*</em></label><input id="copyNo" name="copyNo" type="text"/></div>
<!-- 		<div><label for="borrowerId">Borrower Id</label><input id="borrowerId" name="borrowerId" type="text"/></div>
 -->		<button type="submit">Return</button>
	</form>
	</div>
</div>
</body>
</html>