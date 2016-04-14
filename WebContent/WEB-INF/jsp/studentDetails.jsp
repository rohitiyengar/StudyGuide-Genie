<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<h2>Welcome ${sessionUser.getFname()}</h2>
<table>
<tr>
<td>Your role:</td>
<td>Student</td>
</tr>
<tr>
<td>Registered for any course</td>
<td> ${sessionUser.isRegistered()}</td>
</tr>
</table>
	<a href="<c:url value="j_spring_security_logout" />"> Logout</a>
</body>
</html>