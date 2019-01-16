<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Hello world!</h1>

	<P>The time on the server is ${serverTime}.</p>

	<form action="user" method="post">
		userName :<input type="text" name="userName"><br> 
		source:<input type="text" name="source"><br> 
		destination:<input type="text" name="destination"><br> 
		<input
			type="submit" value="Login">
	</form>
</body>
</html>