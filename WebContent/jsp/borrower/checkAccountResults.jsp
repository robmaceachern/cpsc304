<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <c:choose>
        <c:when test="${hasError}"><title>Check Account Error</title></c:when>
        <c:otherwise><title>Check Account</title></c:otherwise>
    </c:choose>
</head>
<body>
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
    <c:choose>
        <c:when test="${hasError}">
            <h1>Check Account - Error</h1>
            <p>${errorMsg}</p>
        </c:when>
        <c:otherwise>
		    <h1>Results of Check Account</h1>
		    <p>Items Currently Borrowed:</p>
		    <table>
		        <thead>
		            <tr>
		                <td>Borrowing ID</td>
		                <td>Call Number</td>
		                <td>Copy Number</td>
		                <td>Check Out Date</td>
		            </tr>
		        </thead>
		        <c:forEach var="entry" items="${borrowedItems}" >
			        <tr>
				        <td>${entry.borid}</td>
				        <td>${entry.callNumber}</td>
				        <td>${entry.copyNo}</td>
				        <td>${entry.outDate}</td>
			        </tr>
			    </c:forEach>
		    </table>
		    <p>Outstanding Fines:</p>
            <table>
                <thead>
                    <tr>
                        <td>Fine ID</td>
                        <td>Amount Owing</td>
                        <td>Issued Date</td>
                    </tr>
                </thead>
                <c:forEach var="entry" items="${outstandingFines}" >
                    <tr>
                        <td>${entry.fid}</td>
                        <td>${entry.amount}</td>
                        <td>${entry.issuedDate}</td>
                    </tr>
                </c:forEach>
            </table>
            <p>Current Hold Requests:</p>
            <table>
                <thead>
                    <tr>
                        <td>Hold Request ID</td>
                        <td>Call Number</td>
                        <td>Issued Date</td>
                    </tr>
                </thead>
                <c:forEach var="entry" items="${currentHolds}" >
                    <tr>
                        <td>${entry.hid}</td>
                        <td>${entry.callNumber}</td>
                        <td>${entry.issuedDate}</td>
                    </tr>
                </c:forEach>
            </table>
	    </c:otherwise>
   </c:choose>
</body>
</html>