<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add new book</title>
</head>
<body>
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
	<h1>Add a new book</h1>
	<p>This is the new book page, but I don't have anything yet. Don't judge me.</p>
	<form action="/CrazyCoolLibrary/app/addnewbooksubmit" method="post">
		<div><label for="isbn">ISBN</label><input id="isbn" name="isbn" type="text"/></div>
		<div><label for="title">Title</label><input id="title" name="title" type="text"/></div>
		<div><label for="author">Author</label><input id="author" name="author" type="text"/></div>
		<div><label for="publisher">Publisher</label><input id="publisher" name="publisher" type="text"/></div>
		<div><label for="year">Year</label><input id="year" name="year" type="text"/></div>
		<button type="submit">Add book</button>
	</form>
</body>
</html>