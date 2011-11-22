<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add New Borrower</title>
</head>
<body>
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
	<h2>Add a new borrower</h2>
	<form action="/CrazyCoolLibrary/app/addnewborrowersubmit" method="post">
		<div><label for="name">Name </label><input id="name" name="name" type="text"/></div>
		<div><label for="password">Password </label><input id="password" name="password" type="password"/></div>
		<div><label for="address">Address </label><input id="address" name="address" type="text"/></div>
		<div><label for="phone">Phone </label><input id="phone" name="phone" type="text"/></div>
		<div><label for="email">Email </label><input id="email" name="email" type="text"/></div>
		<div><label for="sin">SIN or Student# </label><input id="sin" name="sin" type="text"/></div>
		<div><label for="type">Type </label><select id="type">
												<option>Student</option>
												<option>Faculty</option>
												<option>Staff</option>
											</select></div>
		<button type="submit">Add borrower</button>
	</form>
</body>
</html>