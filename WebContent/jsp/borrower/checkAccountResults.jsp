<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Check Account</title>
</head>
<body>
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
    <h1>Results of Check Account</h1>
    <p>That is a lie. This hasn't been implemented yet.</p>
    <p>If there was an error, it will be printed here</p>
    <table>
        <thead>
            <tr>
                <td>Borrowing ID</td>
                <td>Borrower ID</td>
                <td>Call Number</td>
                <td>Copy Number</td>
                <td>Check Out Date</td>
            </tr>
        </thead>
        <c:forEach var="entry" items="${borrowedItems}" >
	        <tr>
		        <td>${entry.borid}</td>
		        <td>${entry.bid}</td>
		        <td>${entry.callNumber}</td>
		        <td>${entry.copyNo}</td>
		        <td>${entry.outDate}</td>
	        </tr>
	    </c:forEach>
    </table>
</body>
</html>