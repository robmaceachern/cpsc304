<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Most popular books report</title>
</head>
<body>
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
	<h1>Checked out books report</h1>
	<p>That is a lie. This hasn't been implemented yet. It'll look like a pretty table</p>
	<p>If there was an error, it will be printed here</p>
	
	<table>
		<thead>
			<tr>
				<td>Call Number</td>
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
				<td>${item.title}</td>
				<td>${item.outDate}</td>
				<td>${item.dueDate}</td>
				<td>${item.overdue}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>