<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Sweet Home</title>
</head>
<body>
<jsp:include page="/jsp/fragment/header.jspf"/>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
<h1>Welcome to the CrazyCoolLibrary</h1>
<p>This is the craziest and coolest library that has ever existed. You will not be disappointed!
Select an action from the navigation panel to start exploring the library</p>

<c:choose>
	<c:when test="${not empty stats}">
		<p>Library statistics</p>
		<ul>
			<li>Number of Borrowers: ${stats.numBorrowers}</li>
			<li>Number of Books: ${stats.numBooks}</li>
			<li>Number of Book Copies: ${stats.numBookCopies}</li>
			<li>Number of Hold Requests: ${stats.numHoldRequests}</li>
		</ul>
	</c:when>
</c:choose>
</body>
</html>