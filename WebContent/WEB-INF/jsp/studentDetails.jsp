<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/navbar.css">
<title>Welcome!</title>
<!--Reference : http://jsfiddle.net/josedvq/Jyjjx/45/-->
<style>
.round-button {
	width: 70%;
}

.round-button-circle {
	width: 100%;
	height: 0;
	padding-bottom: 100%;
	border-radius: 50%;
	border: 7px solid #015123;
	overflow: hidden;
	background: #1d9269;
	box-shadow: 0 0 3px gray;
}

.round-button-circle:hover {
	background: #197d5a;
}

.round-button a {
	display: block;
	float: left;
	width: 100%;
	padding-top: 50%;
	padding-bottom: 50%;
	line-height: 1em;
	margin-top: -0.5em;
	text-align: center;
	color: #e2eaf3;
	font-family: Verdana;
	font-size: 1.2em;
	font-weight: bold;
	text-decoration: none;
}

.tooltip.bottom {
	padding: 5px 0;
	margin-top: 30px;
	font-size: 20px;
	margin-bottom: 6px;
	font-family: "Comic Sans MS", cursive, sans-serif;
}
</style>



</head>

<body>
	<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="#"> <b>Study Guide |</b>
			</a>
		</div>
		<div>
			<ul class="nav navbar-nav">
				<li><a href="#">Visualize</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="#"> ${sessionUser.getFname()}</a></li>
				<li><a href="<c:url value= 'j_spring_security_logout' />">
						<span class="glyphicon glyphicon-log-out"></span> Logout
				</a></li>
			</ul>
		</div>

	</div>
	</nav>
	<div class="container">
		<h2>Welcome ${sessionUser.getFname()}</h2>
		<br>
		<center>
			<!--  <div class="well">
				<c:if test="${not empty registerMessage }">
					<div class="bg-danger">${registerMessage }</div>
					<br>
				</c:if>
				<table class="table table-bordered table-condensed">
					<tr>
						<td><h3>Your role:</h3></td>
						<td><h3>Student</h3></td>
					</tr>
					<tr>
						<td><h3>Registered for any course</h3></td>
						<td><h3>${sessionUser.isRegistered()}</h3></td>
					</tr>
				</table>
				<a class="btn btn-danger"
					href="${pageContext.request.contextPath}/notes">Write notes</a> <a
					class="btn btn-danger"
					href="${pageContext.request.contextPath}/exam">Exams</a> <a
					class="btn btn-danger"
					href="${pageContext.request.contextPath}/allexams">Get all
					exams</a> <a class="btn btn-danger"
					href="<c:url value= 'j_spring_security_logout'  />"> Logout</a>
			</div>-->
		</center>

		<br>
		<hr></hr>
		<br>


		<center>
			<h3>Exam Information: <small><i>Hover over for details</i></small></h3>
			<br> <br>
			<div id="exams"></div>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			
		</center>
		<script>
			function myFunction(xml) {
				var xmlDoc = xml;
				var text = "<table width = '100%' ><tr>";
				x = xmlDoc.getElementsByTagName('exam');
				xLen = x.length;
				for (i = 0; i < xLen; i++) {
					y = xmlDoc.getElementsByTagName('exam')[i]
							.getElementsByTagName('chapter');
					yLen = y.length;
					var str = x[i].getAttribute("number") + "<hr>";
					for (j = 0; j < yLen; j++) {
						str += (j + 1) + ") " + y[j].getAttribute("name")
								+ "<br>";

					}
					str += "<br>"
					text += "<td align = 'center'>";

					text += "<div class='round-button'><div class='round-button-circle'><a href='${pageContext.request.contextPath}/notes?examName="+x[i].getAttribute("number")+"' class='round-button' data-toggle='tooltip' data-placement='bottom' title = '" + str + "'>"
							+ (i + 1) + "</a></div></div>";

					text += "</td>";
				}
				text += "</tr></table>"

				document.getElementById("exams").innerHTML = text;
			}

			$(document).ready(function() {
				$.ajax({
					url : "${pageContext.request.contextPath}/allexams",
					dataType : "xml",
					success : function(result) {

						myFunction(result);
						$('[data-toggle="tooltip"]').tooltip({
		                      html: true
		                  });
					}
				});
				  
			});
		</script>
	</div>
</body>

</html>