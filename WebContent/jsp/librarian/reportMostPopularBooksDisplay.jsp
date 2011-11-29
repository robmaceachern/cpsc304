<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<c:choose>
	<c:when test="${hasError}"><title>Most Popular Books Report - Failure</title></c:when>
	<c:otherwise><title>Most Popular Books Report</title></c:otherwise>
</c:choose>
</head>
<body>
<div id="wrap">
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
<div id="main_content">
<c:choose>
	<c:when test="${hasError}">
		<h1>Most Popular Books Report - Failure</h1>
		<p>${errorMsg}</p>
	</c:when>
	<c:otherwise>
		<h1>Most Popular Books Report</h1>
		
			<c:choose>
				<c:when test="${empty mostPopularBooks}">
					<!-- We have 0 results -->
					<p>There were no books borrowed that year. Weird!</p>
				</c:when>
				<c:otherwise>
					<table>
						<thead>
							<tr>
								<th>Call Number</th>
								<th>Title</th>
								<th>Main Author</th>
								<th>Borrowed Count</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${mostPopularBooks}">
								<tr>
									<td>${item.callNumber}</td>
									<td>${item.title}</td>
									<td>${item.mainAuthor}</td>
									<td>${item.borrowedCount}</td>
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