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
<div id="wrap">
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
	<div id="main_content">
	<h1>Add New Borrower</h1>
	<p>Enter all information of the new borrower.</p>
	<form action="/CrazyCoolLibrary/app/addnewborrowersubmit" method="post">
		<div><label for="name">Name<em>*</em></label><input id="name" name="name" type="text"/></div>
		<div><label for="password">Password<em>*</em></label><input id="password" name="password" type="password"/></div>
		<div><label for="address">Address<em>*</em></label><input id="address" name="address" type="text"/></div>
		<div><label for="phone">Phone<em>*</em></label><input id="phone" name="phone" type="text"/></div>
		<div><label for="email">Email<em>*</em></label><input id="email" name="email" type="text"/></div>
		<div><label for="sin">SIN or Student#<em>*</em></label><input id="sin" name="sin" type="text"/></div>
		<div>
			<label>Type</label>
			<select name="btype" id="btype">
				<option value="student">Student</option>
				<option value="faculty">Faculty</option>
				<option value="staff">Staff</option>
			</select></div>
		<button type="submit">Add borrower</button>
	</form>
	</div>
</div>
</body>
</html>