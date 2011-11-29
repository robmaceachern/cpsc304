<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Check Out</title>
</head>
<body>
<div id="wrap">
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
	<div id="main_content">
	<h1>Checkout Book(s)</h1>
	<p>Enter the Borrower ID and a list of call numbers separated by commas.</p> 
	<form action="/CrazyCoolLibrary/app/checkoutbookssubmit" method="post">
		<div><label for="callNumber">Call Number(s)</label><input id="callNumber" name="callNumber" type="text"/></div>
		<div><label for="bid">Borrower Id</label><input id="bid" name="bid" type="text"/></div>
		<button type="submit">Check Out</button>
	</form>
	</div>
</div>
</body>
</html>