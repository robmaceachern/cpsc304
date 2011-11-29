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
            <h1>Check Account Error</h1>
            <p>${errorMsg}</p>
        </c:when>
        <c:otherwise>
		    <h1>Results of Check Account</h1>
		    <h2>Items Currently Borrowed:</h2>
		    <c:choose>
			    <c:when test="${numBorrowedItems eq 0}">
		               <p>There are no currently borrowed items</p>
		           </c:when>
		           <c:otherwise>
				    <table>
				        <thead>
				            <tr>
				                <th>Title</th>
				                <th>Author</th>
				                <th>Call Number</th>
				                <th>Copy Number</th>
				                <th>Check Out Date</th>
				                <th>Borrowing ID</th>
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
		    <h2>Outstanding Fines:</h2>
		    <c:choose>
			    <c:when test="${numOutstandingFines eq 0}">
	                <p>There are no out standing fines</p>
	            </c:when>
	            <c:otherwise>
		            <table>
		                <thead>
		                    <tr>
		                        <th>Title</th>
                                <th>Author</th>
                                <th>Call Number</th>
		                        <th>Amount Owing</th>
		                        <th>Issued Date</th>
		                        <th>Fine ID</th>
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
            <h2>Current Hold Requests:</h2>
            <c:choose>
	            <c:when test="${numCurrentHolds eq 0}">
	                <p>There are no current hold requests</p>
	            </c:when>
	            <c:otherwise>
		            <table>
		                <thead>
		                    <tr>
		                        <th>Title</th>
                                <th>Author</th>
		                        <th>Call Number</th>
		                        <th>Issued Date</th>
		                        <th>Hold Request ID</th>
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