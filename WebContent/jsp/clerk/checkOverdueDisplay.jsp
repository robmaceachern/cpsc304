<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Checking Overdue</title>
</head>
<body>
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
<script language="javascript">
function sendEmail(name, email){
		alert('Email has bee sent to '+name+' at '+email+'.');
	}
function sendEmail2(row){
	var name2 = document.getElementById("bname" + row);
	var email2 = document.getElementById("email"+row);
	alert('Email has been sent to ' +name2+' at '+email2+'.');
}
</script>
	<h2>Overdue Items</h2>
	<c:choose>
		<c:when test="${noOverdue != null}">
			<p>${noOverdue}</p>
		</c:when>
		<c:otherwise>
			<table>
				<thead>
					<tr>
						<td><b>Name </b></td>
						<td><b>Email </b></td>
						<td><b>Book </b></td>
						<td></td>
					</tr>
				</thead>
				<tbody>
						<c:forEach var="v" items="${overdue['Name']}" varStatus="stat">
						<tr>
							<c:set var="name" value ="${overdue['Name'][stat.count-1]}" scope="session"/>
							<c:set var="email" value ="${overdue['Email'][stat.count-1]}" scope="session"/>
							<c:set var="book" value ="${overdue['Title'][stat.count-1]}" scope="session"/>
							<td id="bname${stat.count}">${name}</td>
							<td id="email${stat.count}">${email}</td>
							<td>${book}</td>
							<td><input type=button value="Notify" onclick="alert('Email sent to ${name} at ${email}')"></td>
						</tr>
						</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
</body>
</html>