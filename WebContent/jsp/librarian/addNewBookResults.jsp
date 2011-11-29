<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<c:choose>
	<c:when test="${hasError}"><title>Add Book - Failure</title></c:when>
	<c:otherwise><title>Book Successfully Added</title></c:otherwise>
</c:choose>
</head>
<body>
<div id="wrap">
	<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
	<jsp:include page="/jsp/fragment/navigation.jspf"/>
	<div id="main_content">
	<c:choose>
		<c:when test="${hasError}">
			<h1>Add Book - Failure</h1>
			<p>${errorMsg}</p>
		</c:when>
		<c:otherwise>
			<h1>New Book Successfully Added</h1>
			<p>The following book was added to the system:</p>
			<ul>
				<li>ISBN: ${bookAdded.isbn}</li>
				<li>Title: ${bookAdded.title}</li>
				<li>Author: ${bookAdded.mainAuthor}</li>
				<li>Publisher: ${bookAdded.publisher}</li>
				<li>Year: ${bookAdded.year}</li>
			</ul>
		</c:otherwise>
	</c:choose>
	</div>
</div>
</body>
</html>