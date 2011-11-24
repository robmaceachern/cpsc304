<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <c:choose>
        <c:when test="${hasError}">Pay Fine Error</c:when>
        <c:otherwise>Paid Fine</c:otherwise>
    </c:choose>
</head>
<body>
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>

    <c:choose>
        <c:when test="${hasError}">
            <h1>Pay Fine - Error</h1>
            <p>${errorMsg}</p>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${numFinesPaid eq 0}">
                    <h1>Pay Fine Error</h1>
                    <p>No fine with the specified ID found</p>
                </c:when>
                <c:otherwise>
		            <h1>Fine Paid</h1>
		            <table>
		                <thead>
		                    <tr>
		                        <td>Fine ID</td>
		                        <td>Title</td>
		                        <td>Author</td>
		                        <td>Call Number</td>
		                        <td>Amount Paid</td>
		                        <td>Date Issued</td>
		                        <td>Date Paid</td>
		                    </tr>
		                </thead>
		                <c:forEach var="entry" items="${paidFine}" >
		                    <tr>
		                        <td>${entry.fid}</td>
		                        <td>${entry.title}</td>
		                        <td>${entry.mainAuthor}</td>
		                        <td>${entry.callNumber}</td>
		                        <td>${entry.amount}</td>
		                        <td>${entry.issuedDate}</td>
		                        <td>${entry.paidDate}</td>
		                    </tr>
		                </c:forEach>
		            </table>
		        </c:otherwise>
		    </c:choose>
        </c:otherwise>
   </c:choose>
</body>
</html>