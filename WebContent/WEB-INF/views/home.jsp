<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>File Downloader System</h1>

	<P>The time on the server is ${serverTime}.</p>

	<form action="download" method="post">
		<TABLE cellpadding="15" border="1" style="background-color: #ffffcc;">
			<TR>
				<TD>User Name</TD>
				<TD><input type="text" name="userName"></TD>
			</TR>
			<TR>
				<TD>User Password</TD>
				<TD><input type="password" name="userPassword"></TD>
			</TR>
			<TR>
				<TD>Source</TD>
				<TD><input type="text" name="source"></TD>
			</TR>
			<TR>
				<TD>Destination</TD>
				<TD><input type="text" name="destination"></TD>
			</TR>

			<TR>
				<TD><input type="submit" value="Download"></TD>
			</TR>
		</TABLE>
	</form>

	<TABLE cellpadding="15" border="1" style="background-color: #ffffcc;">
		<TR>
			<TD>
				<form action="show" method="post">

					<input type="submit" value="Download Status">
				</form>
			</TD>
		</TR>
	</TABLE>

</body>
</html>