<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <c:choose>
        <c:when test="${hasError}"><title>Book Search - Error</title></c:when>
        <c:otherwise><title>Book Search</title></c:otherwise>
    </c:choose>
</head>
<body>
<div id="wrap">
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
	<div id="main_content">
    <c:choose>
        <c:when test="${hasError}">
            <h1>Book Search - Error</h1>
            <p>${errorMsg}</p>
        </c:when>
        <c:otherwise>
            <h1>Books Found</h1>
            <table>
                <thead>
                    <tr>
                        <td>Title</td>
                        <td>Main Author</td>
                        <td>Call Number</td>
                        <td>ISBN</td>
                        <td>Publisher</td>
                        <td>Year</td>
                    </tr>
                </thead>
                <c:forEach var="entry" items="${books}" >
                    <tr>
                        <td>${entry.title}</td>
                        <td>${entry.mainAuthor}</td>
                        <td>${entry.callNumber}</td>
                        <td>${entry.isbn}</td>
                        <td>${entry.publisher}</td>
                        <td>${entry.year}</td>
                    </tr>
                </c:forEach>
            </table>
            
        </c:otherwise>
   </c:choose>
   </div>
</div>
</body>
</html>