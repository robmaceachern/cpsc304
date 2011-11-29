<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Checked out books report</title>
</head>
<body>
<div id="wrap">
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
	<div id="main_content">
	<h1>Checked out books report</h1>
	<!-- I'm using GET for this form because we're not changing the server state 
			based on the subject. It's just a filtering mechanism. -->
	<form action="/CrazyCoolLibrary/app/viewcheckedoutbooksreport" method="get">
		<div><label for="subject">Subject</label><input id="subject" name="subject" type="text"/></div>
		<button type="submit">Generate report</button>
	</form>
	</div>
</div>
</body>
</html>