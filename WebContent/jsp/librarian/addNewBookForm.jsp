<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add New Book</title>
</head>
<body>
<div id="wrap">
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
	<div id="main_content">
	<h1>Add New Book</h1>
	<p>Enter all info for the new book.</p>
	<form action="/CrazyCoolLibrary/app/addnewbooksubmit" method="post">
		<div><label for="isbn"><span>ISBN</span></label><input id="isbn" name="isbn" type="text"/></div>
		<div><label for="title"><span>Title</span></label><input id="title" name="title" type="text"/></div>
		<div><label for="author"><span>Author</span></label><input id="author" name="author" type="text"/></div>
		<div><label for="publisher"><span>Publisher</span></label><input id="publisher" name="publisher" type="text"/></div>
		<div><label for="year"><span>Year</span></label><input id="year" name="year" type="text"/></div>
		<button type="submit">Add book</button>
	</form>
	</div>
</div>
</body>
</html>