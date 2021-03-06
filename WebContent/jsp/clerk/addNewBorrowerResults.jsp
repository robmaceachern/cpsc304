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
	<c:choose>
		<c:when test="${hasError}">
			<h1>Add Borrower Error</h1>
			<p>${errorMsg}</p>
		</c:when>
		<c:otherwise>
			<h1>New Borrower Successfully Added</h1>
			<p>${btype}New borrower ${bname}'s Borrower ID is ${bid}</p>
		</c:otherwise>
	</c:choose>
	</div>
</div>
</body>
</html>