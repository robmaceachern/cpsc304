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
<div id="wrap">
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
	<div id="main_content">
    <c:choose>
        <c:when test="${hasError}">
            <h1>Check Account - Error</h1>
            <p>${errorMsg}</p>
        </c:when>
        <c:otherwise>
		    <h1>Results of Check Account</h1>
		    <p>Items Currently Borrowed:</p>
		    <c:choose>
			    <c:when test="${numBorrowedItems eq 0}">
		               <p>There are no currently borrowed items</p>
		           </c:when>
		           <c:otherwise>
				    <table>
				        <thead>
				            <tr>
				                <td>Title</td>
				                <td>Author</td>
				                <td>Call Number</td>
				                <td>Copy Number</td>
				                <td>Check Out Date</td>
				                <td>Borrowing ID</td>
				            </tr>
				        </thead>
				        <c:forEach var="entry" items="${borrowedItems}" >
					        <tr>
					            <td>${entry.title}</td>
					            <td>${entry.mainAuthor}</td>
						        <td>${entry.callNumber}</td>
						        <td>${entry.copyNo}</td>
						        <td>${entry.outDate}</td>
						        <td>${entry.borid}</td>
					        </tr>
					    </c:forEach>
				    </table>
				</c:otherwise>
		    </c:choose>
		    <p>Outstanding Fines:</p>
		    <c:choose>
			    <c:when test="${numOutstandingFines eq 0}">
	                <p>There are no out standing fines</p>
	            </c:when>
	            <c:otherwise>
		            <table>
		                <thead>
		                    <tr>
		                        <td>Title</td>
                                <td>Author</td>
                                <td>Call Number</td>
		                        <td>Amount Owing</td>
		                        <td>Issued Date</td>
		                        <td>Fine ID</td>
		                    </tr>
		                </thead>
		                <c:forEach var="entry" items="${outstandingFines}" >
		                    <tr>
		                        <td>${entry.title}</td>
                                <td>${entry.mainAuthor}</td>
                                <td>${entry.callNumber}</td>
		                        <td>${entry.amount}</td>
		                        <td>${entry.issuedDate}</td>
		                        <td>${entry.fid}</td>
		                    </tr>
		                </c:forEach>
		            </table>
	            </c:otherwise>
	        </c:choose>
            <p>Current Hold Requests:</p>
            <c:choose>
	            <c:when test="${numCurrentHolds eq 0}">
	                <p>There are no current hold requests</p>
	            </c:when>
	            <c:otherwise>
		            <table>
		                <thead>
		                    <tr>
		                        <td>Title</td>
                                <td>Author</td>
		                        <td>Call Number</td>
		                        <td>Issued Date</td>
		                        <td>Hold Request ID</td>
		                    </tr>
		                </thead>
		                <c:forEach var="entry" items="${currentHolds}" >
		                    <tr>
		                        <td>${entry.title}</td>
                                <td>${entry.mainAuthor}</td>
		                        <td>${entry.callNumber}</td>
		                        <td>${entry.issuedDate}</td>
		                        <td>${entry.hid}</td>
		                    </tr>
		                </c:forEach>
		            </table>
	             </c:otherwise>
	         </c:choose>
	    </c:otherwise>
    </c:choose>
    </div>
</div>
</body>
</html>