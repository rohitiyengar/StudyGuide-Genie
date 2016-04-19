<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/navbar.css">
    <title>Welcome!</title>
</head>

<body>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">
                    <b>Study Guide |</b>
                </a>
            </div>
            <div>
                <ul class="nav navbar-nav">
                    <li><a href="#">Visualize</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="#">
                            <span class="glyphicon glyphicon-log-out"></span> Logout</a>
                    </li>
                </ul>
            </div>

        </div>
    </nav>
    <div class="container">
        <h2>Welcome ${sessionUser.getFname()}</h2>
        <br>
        <center>
            <div class="well">
            <c:if test="${not empty registerMessage }">
							<div class="bg-danger">${registerMessage }</div><br>
			</c:if>
                <table class="table">
                    <tr>
                        <td><h3>Your role:</h3></td>
                        <td><h3>Student</h3></td>
                    </tr>
                    <tr>
                        <td><h3>Registered for any course</h3></td>
                        <td><h3> ${sessionUser.isRegistered()}</h3></td>
                    </tr>
                </table>
                <a class = "btn btn-danger" href="${pageContext.request.contextPath}/notes">Write notes</a>
                <a class = "btn btn-danger" href="${pageContext.request.contextPath}/exam">Exams</a>
                <a class = "btn btn-danger" href="<c:url value= 'j_spring_security_logout'  />"> Logout</a>
              </div>
            </center>
            </div>
</body>

</html>