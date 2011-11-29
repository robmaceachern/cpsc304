<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Checked Out Books Report</title>
</head>
<body>
<div id="wrap">
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
<div id="main_content">
<c:choose>
	<c:when test="${hasError}">
		<h1>Checked Out Books Report - Failure</h1>
		<p>${errorMsg}</p>
	</c:when>
	<c:otherwise>
		<h1>Checked Out Books Report</h1>
		
			<c:choose>
				<c:when test="${empty checkedOutBooks}">
					<p>There were no checked out books that matched your search</p>
				</c:when>
				<c:otherwise>
					<table>
						<thead>
							<tr>
								<td>Call Number</td>
								<td>Copy Number</td>
								<td>Title</td>
								<td>Check Out Date</td>
								<td>Due Date</td>
								<td>Overdue</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${checkedOutBooks}">
							<tr>
								<td>${item.callNumber}</td>
								<td>${item.copyNo}</td>
								<td>${item.title}</td>
								<td>${item.outDate}</td>
								<td>${item.dueDate}</td>
								<td>
									<c:choose>
										<c:when test="${(item.dueDate.time - now.time) < 0}">Yes</c:when>
										<c:otherwise>No</c:otherwise>
									</c:choose>
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
	</c:otherwise>
</c:choose>
</div>
</div>
</body>
</html>