<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search for books</title>
</head>
<body>
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
    <h1>Search for books</h1>
    <p>Enter the keyword and select the search category to be used.</p>
    <form action="/CrazyCoolLibrary/app/searchbookssubmit" method="get">
        <div><label for="keyword">Keyword</label><input id="keyword" name="keyword" type="text"/></div>
        <button type="submit">Search for books</button>
        <select name="stype" id="stype">
		  <option value="titles">Titles</option>
		  <option value="authors">Authors</option>
		  <option value="subjects">Subjects</option>
		</select> 
    </form>
</body>
</html>

