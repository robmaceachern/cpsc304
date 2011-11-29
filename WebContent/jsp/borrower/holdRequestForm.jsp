<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Place Hold Request</title>
</head>
<body>
<div id="wrap">
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
	<div id="main_content">
    <h1>Place Hold Request</h1>
    <p>Enter your Borrower ID and the call number of the book to request.</p>
    <form action="/CrazyCoolLibrary/app/placeholdsubmit" method="post">
        <div><label for="callNumber">Call Number<em>*</em></label><input id="callNumber" name="callNumber" type="text"/></div>
        <div><label for="bid">Borrower ID<em>*</em></label><input id="bid" name="bid" type="text"/></div>
        <button type="submit">Place Hold Request<em>*</em></button>
    </form>
    </div>
</div>
</body>
</html>