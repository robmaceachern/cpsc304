<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<c:choose>
	<c:when test="${hasError}"><title>Remove Borrower - Failure</title></c:when>
	<c:otherwise><title>Borrower Successfully Removed</title></c:otherwise>
</c:choose>
</head>
<body>
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
	<c:choose>
		<c:when test="${hasError}">
			<h1>Remove Borrower - Failure</h1>
			<p>${errorMsg}</p>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${numBorrowersRemoved gt 0}">
					<h1>Borrower Removed!</h1>
					<p>You have removed the borrower with id ${borrowerId}.
					All borrowings, hold requests, and fines associated with the borrower were also removed.</p>
				</c:when>
				<c:otherwise>
					<h1>No Borrower Removed</h1>
					<p>There were no borrowers with id ${borrowerId}, so no action was taken.</p>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
</body>
</html>