<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<c:choose>
	<c:when test="${hasError}"><title>Remove Book - Failure</title></c:when>
	<c:otherwise><title>Book Successfully Removed</title></c:otherwise>
</c:choose>
</head>
<body>
<div id="wrap">
	<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
	<jsp:include page="/jsp/fragment/navigation.jspf"/>
	<div id="main_content">
	<c:choose>
		<c:when test="${hasError}">
			<h1>Remove Book - Failure</h1>
			<p>${errorMsg}</p>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${numBooksRemoved gt 0}">
					<h1>Book Removed!</h1>
					<p>You have removed the book with call number ${callNumber}. All copys of the book were also deleted</p>
				</c:when>
				<c:otherwise>
					<h1>No Book Removed</h1>
					<p>There were no books with call number ${callNumber}, so no action was taken.</p>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
	</div>
</div>
</body>
</html>