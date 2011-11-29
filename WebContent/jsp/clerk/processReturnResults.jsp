<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Process Return</title>
</head>
<body>
<div id="wrap">
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
	<div id="main_content">
	<c:choose>
		<c:when test="${hasError}">
			<h2>Book Return Error</h2>
			<p>${errorMsg}</p>
		</c:when>
		<c:otherwise>
			<h2>Book Returned</h2>
			<c:choose>
				<c:when test="${DNEerror}">
					<p>${errorMsg}</p>
				</c:when>
				<c:otherwise>
					<c:if test="${onHold}">
						<script language="javascript">
						$(document).ready(function(){
							alert("Notifying ${requesterName} at ${requesterEmail} that this book is now available.");
						});
						</script>
					</c:if>
					<p>The following was checked in:</p>
					Call Number: ${returns['callNumber'][0]}<br>
					Copy: ${returns['copyNo'][0]}
					<p>Thank you for returning your book.</p>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
	</div>
</div>
</body>
</html>