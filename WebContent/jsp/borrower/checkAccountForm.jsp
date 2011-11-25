<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Check your account</title>
</head>
<body>
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
    <h1>Check your account</h1>
    <p>Enter your borrower ID to check the items you currently have checked out, your outstanding fines, and your current hold requests.</p>
    <form action="/CrazyCoolLibrary/app/checkaccountsubmit" method="get">
        <div><label for="bid">Borrower ID</label><input id="bid" name="bid" type="text"/></div>
        <button type="submit">Check Account</button>
    </form>
</body>
</html>
