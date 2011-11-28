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
	document.
	alert('Email has bee sent to '+name+' at '+email+'.');
}
function sendEmails(){
// var names = ${overdue['Name']};
	var nameList = 'Emails have ben sent to ';
//	for(i=0; i< names.size(); i++){
	//	if(nameList.search(names[i]) == -1)
	//		nameList = names[i] + ' \n';
//	}
	alert(nameList);
	
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
							<td>${name}</td>
							<td>${email}</td>
							<td>${book}</td>
							<td><input type=button value="Notify" onclick="alert('Email has been sent to ${name} at ${email}')"></td>
						</tr>
						</c:forEach>
				</tbody>
			</table>
			<input type=button value="Notify all" onclick="alert('Emails have been sent to all borrowers.')">
		</c:otherwise>
	</c:choose>
</body>
</html>