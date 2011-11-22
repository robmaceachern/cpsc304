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
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
	<h2>Check out book(s)</h2>
	<form action="/CrazyCoolLibrary/app/checkoutbookssubmit" method="post">
	<fieldset>
	<legend>Check out book(s):</legend>
		<label for="callNumber">Call Number(s)</label><input id="callNumber" name="callNumber" type="text"/><br />
		<label for="borrowerId">Borrower Id</label><input id="bid" name="bid" type="text"/><br />
		<button type="submit">Check Out</button>
	</fieldset>
	</form>
</body>
</html>