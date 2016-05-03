<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>Student Notes</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/prism.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/awesomplete.css" />
<script src="https://code.jquery.com/jquery-latest.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/scripts/awesomplete.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/navbar.css">
<!-- Referenced from : http://jsfiddle.net/q3FhL/ & http://jsfiddle.net/TV47t/1/-->
<style>
textarea {
	background: url(http://i.stack.imgur.com/ynxjD.png) repeat-y;
	width: 450px;
	height: 200px;
	font-family: Rockwell;
	font-weight: bold;
	font-size: 14px;
	border: 0;
	outline: 0;
	line-height: 25px;
	padding: 2px 10px;
	border: solid 1px #ddd;
}

#codeArea {
	background: none;
	background-size: 10;
	background-color: #FFFFFF;
	font-size: 14px;
	color: #1d9269;
	font-family: 'Andale Mono', AndaleMono, monospace;
	
}

h2 .btn-group {
	display: inline-block;
}

/* .ta5 {
	border: 2px solid #765942;
	border-radius: 10px;
	height: 160px;
	width: 430px;
	font-family:Tahoma;
	font-size:large;
}*/
#chaptertitle {
	position: relative;
	background: url('images/greenpage.jpg') no-repeat center center;
	width: 100%;
	height: 100%;
	background-size: 100% 100%;
	color: white;
	font-family: baskerville;
}
</style>
</head>
<script>
	var bullet = '\u2022';
	$()
			.ready(
					function() {

						var suggests = [];
						var input = document.getElementById("mynotes");
						var autocomplete = new Awesomplete(input, {
							autoFirst : true
						});
						$("#mynotes")
								.keyup(
										function(e) {
											if (e.keyCode == 32) {
												//space
												autocomplete.list = [];
												return;
											}

											var c = String
													.fromCharCode(e.keyCode);
											var isWordCharacter = c.match(/\w/);
											var isBackspaceOrDelete = (e.keyCode == 8 || e.keyCode == 46);
											var str = $(this).val();
											if (!((isWordCharacter || isBackspaceOrDelete) && str.length > 0)) {
												return;
											}
											var notes = $(this);
											if (notes.val() != bullet
													&& notes.val.length > 0) {
												var words = notes.val().split(
														" ");
												var currentWord = words[words.length - 1];
												currentWord = currentWord
														.trim();
												console.log(currentWord);
												$
														.ajax({
															url : "testpages/autosuggests.jsp?q="
																	+ currentWord
																	+ "&top=5",
															dataType : "xml",
															success : function(
																	result) {
																$xml = $(result);
																var output = $xml
																		.find("text");
																var len = output.length;
																if (len > 0) {
																	suggests = new Array();
																	for (var i = 0; i < len; i++) {
																		suggests
																				.push($(
																						output[i])
																						.text());
																	}
																	autocomplete.list = suggests;
																}
															}
														});
											}
										});

						$("#mynotes")
								.bind(
										'input propertychange',
										function() {
											var res = $("#mynotes").val()
													.trim();
											//console.log("clicked " + res);
											var topicName = $("#topicName")
													.val();
											//console.log("topicName "+topicName)
											if (res != "") {
												$
														.ajax({
															url : "testpages/notesMatcherPercentage.jsp?topic="
																	+ topicName
																	+ "&notes="
																	+ res,
															dataType : "xml",
															success : function(
																	result) {
																$xml = $(result);
																var output = $xml
																		.find(
																				"score")
																		.text();
																console
																		.log(output);
																if (output.trim() != "") 
																{
																	$("#matchPercentage").val(output);
																	if(output >=0 & output <=35)
																	{
																		$("#plagiarismMessage").empty();
																		$("#plagiarismMessage").append("<div class='alert alert-warning alert-dismissible' role='alert'><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button><strong>Hello!</strong> It looks like you haven't followed the topic. Please read through and write more detailed notes.</div>");
																		
																		//$("#plagiarismMessage").css("color","orange");
																		//$("#plagiarismMessage").css("font-family","rockwell");
																		//$("#plagiarismMessage").text("It looks like you haven't followed the topic. Please read through and write more detailed notes.");
																		
																	}
																	else if(output > 35 & output <=85)
																	{
																		$("#plagiarismMessage").empty();
																		$("#plagiarismMessage").append("<div class='alert alert-success alert-dismissible' role='alert'><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button><strong>Great going!</strong>  Seems like you have understood the topic well.</div>");
																		//$("#plagiarismMessage").css("color","green");
																		//$("#plagiarismMessage").css("font-family","rockwell");
																		//$("#plagiarismMessage").text("Great going! Seems like you have understood the topic well.");
																	}
																	else if(output > 85 & output <=100)
																	{
																		$("#plagiarismMessage").empty();
																		$("#plagiarismMessage").append("<div class='alert alert-danger alert-dismissible' role='alert'><button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button><strong>Stop!</strong> Please write notes based on your understanding. This looks like a copy paste of content from the textbook.</div>");

																		//$("#plagiarismMessage").css("color","red");
																		//$("#plagiarismMessage").css("font-family","rockwell");
																		//$("#plagiarismMessage").text("Please write notes based on your understanding. This looks like a copy paste of content from the textbook.");
																	}
																}
															}
														});

											}
										});

						$('#codeArea')
								.bind(
										'input propertychange',
										function() {
											var val = this.value.length;
											var code = this.value;
											//alert(this.value);
											if (val > 0) {
												$
														.ajax({
															url : "testpages/compileJavaCode.jsp?source="
																	+ code,
															dataType : "xml",
															success : function(
																	result) {
																$xml = $(result);
																var output = $xml
																		.find(
																				"result")
																		.text();
																if (output
																		.trim() == "") {
																	$(
																			"#compileResult")

																			.text(
																					"Compiled Successfully");
																	$(
																			"#compileResult")
																			.attr(
																					'class',
																					'bg-success');
																	$(
																			"#compileResult")
																			.append(
																					"&nbsp;<span class='glyphicon glyphicon-ok' aria-hidden='true'></span>&nbsp;");
																} else {
																	$(
																			"#compileResult")
																			.text(
																					output);
																	$(
																			"#compileResult")
																			.attr(
																					'class',
																					'bg-danger');
																	$(
																			"#compileResult")
																			.append(
																					"&nbsp;<span class='glyphicon glyphicon-remove' aria-hidden='true'></span>&nbsp;");
																}
															}
														});
											}
										});

						$.ajax({
							url : "${pageContext.request.contextPath}/exam",
							dataType : "xml",
							success : function(result) {

								myFunction(result);
							}
						});

						$(function() {
							$('[data-toggle="tooltip"]').tooltip()
						});
					});

	function myFunction(xml) {
		var xmlDoc = xml;
		var txt = "<ul class='nav nav-pills nav-stacked'>";
		x = xmlDoc.getElementsByTagName('chapter');
		xLen = x.length;
		p = 0;
		for (i = 0; i < xLen; i++) {
			txt += "<li role='presentation'><a class = 'panel-default' data-toggle='collapse' href='#topics1" + p + "'><font color='black' size = '4'>"
					+ x[i].getAttribute("title") + "</font></a>";
			y = xmlDoc.getElementsByTagName('chapter')[i]
					.getElementsByTagName('topic');
			yLen = y.length;
			txt += "<div class = 'collapse in' id='topics1" + p + "'>";
			txt += "<ul class='nav nav-pills nav-stacked'>";
			for (j = 0; j < yLen; j++) {
				if (y[j].getAttribute("done") == "y") {
					txt += "<li role='presentation'><a href='${pageContext.request.contextPath}/notes?topicName="
							+ y[j].getAttribute("name")
							+ "'><img src = 'images/checkmark.png' width = '20' height = '20'></img>&nbsp;"
							+ "<font color='green'>"
							+ y[j].getAttribute("name") + "</font></a>";
					txt += "</li>";
				} else if (y[j].getAttribute("done") == "i") {
					txt += "<li role='presentation'><a href='${pageContext.request.contextPath}/notes?topicName="
							+ y[j].getAttribute("name")
							+ "'><img src = 'images/exclaim.png' width = '20' height = '20'></img>&nbsp;"
							+ "<font color='orange'>"
							+ y[j].getAttribute("name") + "</font></a>";
					txt += "</li>";
				} else {

					txt += "<li role='presentation'><a href='${pageContext.request.contextPath}/notes?topicName="
							+ y[j].getAttribute("name")
							+ "'><img src = 'images/transparent.png' width = '20' height = '20'></img>&nbsp;"
							+ "<font color='grey'>"
							+ y[j].getAttribute("name")
							+ "</font></a>";
					txt += "</li>";

				}

			}
			txt += "</ul>";
			txt += "</div>"

			txt += "</li>";
			p++;
		}
		txt += "</ul>";

		document.getElementById("topics").innerHTML = txt;
	}
	$('#codeArea').live('keypress', function(e) {
		if (e.keyCode === 9) {
			e.preventDefault();
			// do work
		}
	});
</script>
<body>
	<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="${pageContext.request.contextPath}/"> <b>Study Guide |</b>
			</a>
		</div>
		<div>
			
			<ul class="nav navbar-nav navbar-right">
				<li><a href="${pageContext.request.contextPath}/"><span class="glyphicon glyphicon-home" aria-hidden="true"></span>&nbsp;${sessionUser.getFname()}</a></li>
				<li><a href="<c:url value= 'j_spring_security_logout' />">
						<span class="glyphicon glyphicon-log-out"></span> Logout
				</a></li>
			</ul>
		</div>

	</div>
	</nav>

	<div class="container">
		<center>
			<div class="well" id="chaptertitle">
				<h1>${sessionUser.getCurrentTopic()}</h1>
			</div>
		</center>
		<div class="row">
			<div class="col-md-4">

				<center>
					<h2>Chapters</h2>
				</center>
				<div id="topics" class="well"></div>
			</div>
			<div class="col-md-5">

				<center>
					<h2>Notes</h2>
				</center>
				<div>

					<c:if test="${not empty notesMessage }">
						<div class="bg-danger">${notesMessage }</div>
						<br>
					</c:if>
					<center>
						<form:form action="${pageContext.request.contextPath}/notes"
							modelAttribute="notes" method="POST">
							<div>
								<form:input id="topicid" path="topicid" type="hidden" />
								<form:input id="topicName" path="topicName" type="hidden" />
								<form:input id="notesId" path="notesId" type="hidden" />
							</div>
							<div>
								<br>
								<form:textarea path="topicText" id="mynotes" class="ta5"
									rows="10" cols="50"></form:textarea>
								<br /> 
								<form:input id="matchPercentage" path="matchPercentage"
									class="control form-control" type="hidden" />
							</div>
							<br />
							<div id="plagiarismMessage">

							</div>
							<br />
							<div>

								<h3>Code</h3>
								<form:textarea
									onkeydown="if(event.keyCode===9){var v=this.value,s=this.selectionStart,e=this.selectionEnd;this.value=v.substring(0, s)+'\t'+v.substring(e);this.selectionStart=this.selectionEnd=s+1;return false;}"
									path="code" rows="25" cols="100" id="codeArea" class="ta5"></form:textarea>
							</div>
							<br>
							<label id="compileResult"></label>
							<br>
							<br>
							<button class="btn btn-success" id="btnSave"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>&nbsp;Save Notes</button>
							<a class="btn btn-danger"
								href="${pageContext.request.contextPath}/cheatSheetGenerate"><span class="glyphicon glyphicon-file" aria-hidden="true"></span>&nbsp;Generate
								Cheat Sheet</a>
						</form:form>
					</center>
				</div>
			</div>

			<div class="col-md-3">

				<center>
					<table>
						<tr>
							<td><h2>Recommendations</h2></td>
							<td>&nbsp;<a href="#" data-toggle="tooltip"
								title="Recommendations from Java WikiBooks" data-placement="top"><span
									class="glyphicon glyphicon-question-sign" aria-hidden="true"></span></a></td>
						</tr>
					</table>
				</center>
				<br>
				<div id="recommendations">
					<c:if test="${not empty recommendedLinks}">

						<c:forEach items="${recommendedLinks}" var="linkIter">
							<c:set var="displayName"
								value="${fn:substringAfter(linkIter, '#').replace('_', ' ')}"></c:set>
							<span class="glyphicon glyphicon-hand-right" aria-hidden="true"></span>&nbsp;&nbsp;<a
								href="${linkIter }" target = "_blank">${displayName}</a>
							<br>
							<br>
						</c:forEach>

					</c:if>

				</div>
				<center>
					<table>
						<tr>
							<td><h2>Keywords</h2></td>
							<td>&nbsp;<a href="#" data-toggle="tooltip"
								title="This section contains relevant keywords included by your
						peers that you may have missed"
								data-placement="top"><span
									class="glyphicon glyphicon-question-sign" aria-hidden="true"></span></a></td>
						</tr>
					</table>
				</center>
				<div id="keywords">
					<br>
					<c:if test="${not empty recommendedWords}">

						<c:forEach items="${recommendedWords}" var="recoIter">
							<h4>
								&nbsp;&nbsp;&nbsp;<span class="label label-success"><span
									class="glyphicon glyphicon-tag" aria-hidden="true"></span>&nbsp;&nbsp;${recoIter}</span>
							</h4>
						</c:forEach>
						<!--         		<li>Literals <span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>&nbsp;&nbsp;<span class="glyphicon glyphicon-thumbs-down" aria-hidden="true"></span></li> -->
						<!--         		<li>StringBuilder <span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>&nbsp;&nbsp;<span class="glyphicon glyphicon-thumbs-down" aria-hidden="true"></span></li> -->

					</c:if>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		if (document.getElementById('keywords').innerHTML.trim() === "<br>") {
			var keywordStr = "<i>There are no keywords recommended since no notes exist for this topic.</i>";
			$('#keywords').append(keywordStr);
		}

		if (document.getElementById('recommendations').innerHTML.trim() === "") {
			var recStr = "<i>You need to include some notes for links to be recommended.</i>";
			$('#recommendations').append(recStr);
		}
	</script>
	<br>
	<br>
</body>
</html>