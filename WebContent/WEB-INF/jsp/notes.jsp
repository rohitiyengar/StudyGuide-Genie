<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>Student Notes</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/prism.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/awesomplete.css" />
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/scripts/awesomplete.js"></script>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css">
<style>
textarea {
	background: url(http://i.stack.imgur.com/ynxjD.png) repeat-y;
	width: 600px;
	height: 300px;
	font-family: 'Handlee', cursive;
	font-weight: bold;
	font-size: 14px;
	border: 0;
	outline: 0;
	line-height: 25px;
	padding: 2px 10px;
	border: solid 1px #ddd;
}

/* .ta5 {
	border: 2px solid #765942;
	border-radius: 10px;
	height: 160px;
	width: 430px;
	font-family:Tahoma;
	font-size:large;
}*/
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
								.bind('input propertychange',
										function() {
											var res = $("#mynotes").val()
													.trim();
											console.log("clicked " + res);
											var topicName =  $("#topicName").val();
											console.log("topicName "+topicName)
											if (res != "") {
												$
														.ajax({
															url : "testpages/notesMatcherPercentage.jsp?topic="+ topicName +"&notes="
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
																if (output
																		.trim() != "") {
																	$(
																			"#matchPercentage")
																			.val(output);
																}
															}
														});

											}
										});

						$('#codeArea').bind(
								'input propertychange',
								function() {
									var val = this.value.length;
									var code = this.value;
									//alert(this.value);
									if (val > 0) {
										$.ajax({
											url : "testpages/compileJavaCode.jsp?source="
													+ code,
											dataType : "xml",
											success : function(result) {
												$xml = $(result);
												var output = $xml
														.find("result").text();
												if (output.trim() == "") {
													$("#compileResult").text(
															"Success");
													$("#compileResult").attr('class','bg-success');
												} else {
													$("#compileResult").text(
															output);
													$("#compileResult").attr('class','bg-danger');
												}
											}
										});
									}
								});
					});
</script>
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
					<li><a href="#"> <span class="glyphicon glyphicon-log-out"></span>
							Logout
					</a></li>
				</ul>
			</div>

		</div>
	</nav>
	<div class="container">
		<div class="row">
			<div class="col-md-3">
				<br>
				<h1>Index:</h1>
			</div>
			<div class="col-md-9">
				<br>
				<h1>Notes:</h1>
				<div class="well">
				<br>
					<c:if test="${not empty notesMessage }">
							<div class="bg-danger">${notesMessage }</div><br>
						</c:if>
					<center>
					<form:form action="${pageContext.request.contextPath}/notes" modelAttribute="notes" method="POST">
					<div>
							<form:input id ="topicid" path="topicid" type="hidden"/>
							<form:input id="topicName" path="topicName" type="hidden"/>
							<form:input id="notesId" path="notesId" type="hidden"/>
					</div>
						<div>
							<h3>Please Enter Your Notes Here:</h3>
							<form:textarea path="topicText" id="mynotes" class="ta5" rows="10" cols="50"></form:textarea>
							<br />
							<form:input id="matchPercentage" path="matchPercentage"/>
						</div>
						<br /> <br />
						<div>

							<h3>Please Write Code Here If Any:</h3>
							<form:textarea path="code" rows="25" cols="100" id="codeArea"
								class="ta5"></form:textarea>
						</div>
						<br> <label id="compileResult"></label>
						<button class="btn btn-success" id="btnSave">Save Notes</button>
					</form:form>
				</center>
				</div>
			</div>
		</div>
	</div>
</body>
</html>