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

<!-- For Radial Graphs -->
<script type="text/javascript" src="scripts/percircle.js"></script>
<link rel="stylesheet" href="css/styles.css">
 <link rel="stylesheet" href="css/percircle.css">

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
		<div class = "jumbotron"><h1>Welcome ${sessionUser.getFname()}!</h1></div>
		
		<center>
			
		</center>



		<center>
			<table>
				<tr>
					<td><h2>Here's your Exam information</h2></td>
					<td>&nbsp;<a href="#"  data-toggle="tooltip" title="The radial indicates the percentage completed." data-placement="right"><span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span></a></td>
				</tr>
			</table>
			<br>
			<div id="loading">
				<img src="images/loading.gif"/>
			</div>
			<div id="exams"></div>
			<br>
			<br>
			<br>
			
			
		</center>
		<script>
			function myFunction(xml) {
				$("#loading").hide();
				var xmlDoc = xml;
				var text = "<div class = 'well'><table width = '100%' ><tr>";
				x = xmlDoc.getElementsByTagName('exam');
				xLen = x.length;
				for (i = 0; i < xLen; i++) {
					y = xmlDoc.getElementsByTagName('exam')[i]
							.getElementsByTagName('chapter');
					yLen = y.length;
					var str = "<b>Contents:</b><br><br>";
					for (j = 0; j < yLen; j++) {
						str += "<span class='glyphicon glyphicon-pencil' aria-hidden='true'></span>"+y[j].getAttribute("name")
								+ "<br>";

					}
					str += "<br>";
					text += "<td align = 'center'>";
					
					var percent = x[i].getAttribute("complete") * 100;
					var circleColor;
					if (percent < 50) {
						circleColor = "big red";
					}
					else if (percent < 100) {
						circleColor = "big yellow";
					}
					else {
						circleColor = "big green";
					}

					//text += "<div class='round-button'><div class='round-button-circle'><a href='${pageContext.request.contextPath}/notes?examName="+x[i].getAttribute("number")+"' class='round-button' data-toggle='tooltip' data-placement='bottom' title = '" + str + "'>"
							//+ (i + 1) + "</a></div></div>";
					var url = "${pageContext.request.contextPath}/notes?examName="+x[i].getAttribute('number');
					
					var find = '\\s';
					var re = new RegExp(find, 'g');
					url = url.replace(re, '%20');
					text += "<table><tr><td><h2 style = 'text-align:center'>"+x[i].getAttribute('number')+" </h2></td></tr><tr><td align='center'><div style = 'cursor: pointer; display: table; margin-right: auto;margin-left: auto;'  id='circle"+(i+1)+"' onclick=redirect('"+url+"') data-percent='"+percent+"' class='"+circleColor+"' ></div></td></tr>";
					text += "<tr><td align = 'center'><div class = 'well'>"+str+"</div></tr></td>";
					text += "</table></td>";
				}
				text += "</tr></table></div>";

				document.getElementById("exams").innerHTML = text;
				
				
			}

			$(document).ready(function() {
				$("#loading").show();
				$.ajax({
					url : "${pageContext.request.contextPath}/allexams",
					dataType : "xml",
					success : function(result) {

						myFunction(result);
						$("[id^='circle']").percircle();
						
						$('[data-toggle="tooltip"]').tooltip({
		                      html: true
		                  });
					}
				});
				  
			});
			
			function redirect(url) {
				window.location = url;
			}
			
		</script>
	</div>
</body>

</html>