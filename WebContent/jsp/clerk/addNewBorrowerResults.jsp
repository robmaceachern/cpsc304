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
	<c:choose>
		<c:when test="${hasError}">
			<h2>Error Adding Borrower</h2>
			<p>${errorMsg}</p>
		</c:when>
		<c:otherwise>
			<h2>New Borrower Successfully Added</h2>
			<p>${btype}New borrower ${bname}'s Borrower ID is ${bid}</p>
		</c:otherwise>
	</c:choose>
</body>
</html>