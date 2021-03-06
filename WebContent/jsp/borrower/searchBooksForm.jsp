<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/fragment/head.jspf"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search For books</title>
</head>
<body>
<div id="wrap">
<jsp:include page="/jsp/fragment/header.jspf"></jsp:include>
<jsp:include page="/jsp/fragment/navigation.jspf"/>
	<div id="main_content">
    <h1>Search For Books</h1>
    <p>Enter the keyword and select the search category to be used.</p>
    <form action="/CrazyCoolLibrary/app/searchbookssubmit" method="get">
        <div><label for="keyword">Keyword<em>*</em></label><input id="keyword" name="keyword" type="text"/></div>
        <div><label for="stype">Search By</label>
        	<select name="stype" id="stype">
		  		<option value="titles">Title</option>
		  		<option value="authors">Author</option>
		  		<option value="subjects">Subject</option>
			</select> 
        </div>
        <button type="submit">Search for books</button>

    </form>
    </div>
</div>
</body>
</html>

