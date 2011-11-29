<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <c:choose>
        <c:when test="${hasError}"><title>Hold Request Error</title></c:when>
        <c:otherwise><title>Placed Hold Request</title></c:otherwise>
    </c:choose>
</head>
<body>
<div id="wrap">
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
	<div id="main_content">

    <c:choose>
        <c:when test="${hasError}">
            <h1>Hold Request - Error</h1>
            <p>${errorMsg}</p>
        </c:when>
        <c:otherwise>
           <h1>Hold Request Placed</h1>
           <p>A hold request has been placed for the specified book and borrower.</p>
        </c:otherwise>
   </c:choose>
   </div>
</div>
</body>
</html>
