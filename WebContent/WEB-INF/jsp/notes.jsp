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
<!-- Referenced from : http://jsfiddle.net/q3FhL/ & http://jsfiddle.net/TV47t/1/-->
<style>

textarea {
	background: url(http://i.stack.imgur.com/ynxjD.png) repeat-y;
	width: 500px;
	height: 200px;
	font-family: 'Handlee', cursive;
	font-weight: bold;
	font-size: 14px;
	border: 0;
	outline: 0;
	line-height: 25px;
	padding: 2px 10px;
	border: solid 1px #ddd;
}

#codeArea {
	background: url(http://s24.postimg.org/62v2ipx81/underline.png) repeat;
	background-size:10;
	background-color: #1d9269;
	font-size: 16px;
	color: #ffffff;
	font-family: 'Times New Roman';
	line-height:30px;
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
	$().ready(function() {
						
	var suggests = [];
	var input = document.getElementById("mynotes");
	var autocomplete = new Awesomplete(input, {	autoFirst : true});
	$("#mynotes").keyup(function(e) {
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
												currentWord = currentWord.trim();
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
																if (output
																		.trim() != "") {
																	$(
																			"#matchPercentage")
																			.val(
																					output);
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
																			.append("&nbsp;<span class='glyphicon glyphicon-ok' aria-hidden='true'></span>&nbsp;");
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
																	.append("&nbsp;<span class='glyphicon glyphicon-remove' aria-hidden='true'></span>&nbsp;");
																}
															}
														});
											}
										});	
						
						$
						.ajax({
							url : "${pageContext.request.contextPath}/exam",
							dataType : "xml",
							success : function(
									result) {
								
						
								myFunction(result);
								}
							});
	});
	
	function myFunction(xml) {
        var xmlDoc = xml;
        var txt = "<ul class='nav nav-pills nav-stacked'>";
        x = xmlDoc.getElementsByTagName('chapter');
        xLen = x.length;
        p = 0;
        for (i = 0; i < xLen; i++) {
          txt += "<li role='presentation'><a class = 'panel-default' data-toggle='collapse' href='#topics1" + p + "'><font color='black' size = '4'>" + x[i].getAttribute("title") + "</font></a>";
          y = xmlDoc.getElementsByTagName('chapter')[i].getElementsByTagName('topic');
          yLen = y.length;
          txt += "<div class = 'collapse in' id='topics1" + p + "'>";
          txt += "<ul class='nav nav-pills nav-stacked'>";
          for (j = 0; j < yLen; j++) {
            if (y[j].getAttribute("done") == "y") {
              txt += "<li role='presentation'><a href='${pageContext.request.contextPath}/notes?topicName="+y[j].getAttribute("name")+"'><img src = 'images/checkmark.png' width = '20' height = '20'></img>&nbsp;" + "<font color='green'>" + y[j].getAttribute("name") + "</font></a>";
              txt += "</li>";
            } else if (y[j].getAttribute("done") == "i") {
              txt += "<li role='presentation'><a href='${pageContext.request.contextPath}/notes?topicName="+y[j].getAttribute("name")+"'><img src = 'images/exclaim.png' width = '20' height = '20'></img>&nbsp;" + "<font color='orange'>" + y[j].getAttribute("name") + "</font></a>";
              txt += "</li>";
            } else {

              txt += "<li role='presentation'><a href='${pageContext.request.contextPath}/notes?topicName="+y[j].getAttribute("name")+"'><img src = 'images/transparent.png' width = '20' height = '20'></img>&nbsp;" + "<font color='grey'>" + y[j].getAttribute("name") + "</font></a>";
              txt += "</li>";

            }

          }
          txt += "</ul>";
          txt += "</div>"

          txt += "</li>";
          p++;
        }
        txt += "</ul>";

        document.getElementById("topics").innerHTML =
          txt;
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
				<a class="navbar-brand" href="#"> <b>Study Guide |</b>
				</a>
			</div>
			<div>
				<ul class="nav navbar-nav">
					<li><a href="#">Visualize</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#">
                	 ${sessionUser.getFname()}</a>
                	</li>
					<li><a href="<c:url value= 'j_spring_security_logout' />"> <span class="glyphicon glyphicon-log-out"></span>
							Logout
					</a></li>
				</ul>
			</div>

		</div>
	</nav>
	<div class="container">
		<center><div class = "well"><h1>${sessionUser.getCurrentTopic()}</h1></div></center>
		<div class="row">
			<div class="col-md-4">
			
				<h2> Index: </h2>
        		<div id="topics" class = "well">
        		</div>
			</div>
			<div class="col-md-6">
				
				<h2>Notes:</h2>
				<div >
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
							<br /><br />
							<label for="matchPercentage">Match Percentage:</label>&nbsp;&nbsp;<form:input id="matchPercentage" path="matchPercentage" class="control form-control"/>
						</div>
						<br /> <br />
						<div>

							<h3>Please Write Code Here If Any:</h3>
							<form:textarea onkeydown="if(event.keyCode===9){var v=this.value,s=this.selectionStart,e=this.selectionEnd;this.value=v.substring(0, s)+'\t'+v.substring(e);this.selectionStart=this.selectionEnd=s+1;return false;}" path="code" rows="25" cols="100" id="codeArea"
								class="ta5"></form:textarea>
						</div>
						<br> <label id="compileResult"></label> <br><br>
						<button class="btn btn-success" id="btnSave">Save Notes</button>
					</form:form>
				</center>
				</div>
			</div>
			<div class="col-md-2">
			
				<h2>Recommendations: </h2>
        		<div id="recommendations" class = "well">
        		</div>
			</div>
		</div>
	</div>
	<br><br>
</body>
</html>