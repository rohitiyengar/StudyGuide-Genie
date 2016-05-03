<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your Generated Cheat Sheet</title>

<!-- References: http://balupton.github.io/jquery-syntaxhighlighter/demo/#install
    http://codepen.io/chrisdpratt/pen/IAymB -->

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="http://balupton.github.com/jquery-syntaxhighlighter/scripts/jquery.syntaxhighlighter.min.js"></script>
    
<style>
.bs-callout {
	padding: 20px;
	margin: 20px 0;
	border: 1px solid #eee;
	border-left-width: 5px;
	border-radius: 3px;
}

.bs-callout h4 {
	margin-top: 0;
	margin-bottom: 5px;
}

.bs-callout p:last-child {
	margin-bottom: 0;
}

.bs-callout code {
	border-radius: 3px;
}

.bs-callout+.bs-callout {
	margin-top: -5px;
}

.bs-callout-default {
	border-left-color: #777;
}

.bs-callout-default h4 {
	color: #777;
}

.bs-callout-primary {
	border-left-color: #428bca;
}

.bs-callout-primary h4 {
	color: #428bca;
}

.bs-callout-success {
	border-left-color: #5cb85c;
}

.bs-callout-success h4 {
	color: #5cb85c;
}

.bs-callout-danger {
	border-left-color: #d9534f;
}

.bs-callout-danger h4 {
	color: #d9534f;
}

.bs-callout-warning {
	border-left-color: #f0ad4e;
}

.bs-callout-warning h4 {
	color: #f0ad4e;
}

.bs-callout-info {
	border-left-color: #5bc0de;
}

.bs-callout-info h4 {
	color: #5bc0de;
}

.jumbotron {
	position: relative;
	background: url('images/greenpage.jpg') no-repeat center center;
	color:white;
	width: 100%;
	height: 100%;
	background-size: 100% 100%;
	font-family: baskerville;
}
</style>

<script type="text/javascript">
		
        $(document)
		.ready(
				function() {
					$.ajax({
						url : "${pageContext.request.contextPath}/cheatSheet",
						dataType : "xml",
						success : function(result) {
							$.SyntaxHighlighter.init();
							myFunction(result);
						}
					});
							
				});
        
        function generate()
        {
          window.print();
        }
        
</script>



</head>
<body>
	<div class="container">
		<br>
		<div>
			
			<a href="${pageContext.request.contextPath}/"><img title ="Dashboard" src="images/cheatsheet.png" width="30%" height="30%"/></a>
				
			<div style="float: right; bottom: 0;">
				<button class="btn btn-danger" onclick="javascript:history.back();" id="back"><span class="glyphicon glyphicon-menu-left" aria-hidden="true"></span>&nbsp;Back</button>
				<button class="btn btn-success" onclick="generate();" id="generate"><span class="glyphicon glyphicon-print" aria-hidden="true"></span>&nbsp;Print</button>
			</div>
		</div>
		<hr></hr>

		<div id="content"></div>

		<script>
		
               function myFunction(xml) {
                var xmlDoc = xml;
                var text = "";
                text += "<div class = 'jumbotron'><center><h1>"+xmlDoc.getElementsByTagName('exam')[0].getAttribute('number')+"</h1></center></div>";

                x = xmlDoc.getElementsByTagName('chapter');
                xLen = x.length;
                for (i = 0; i < xLen; i++) {
                    y = xmlDoc.getElementsByTagName('chapter')[i].getElementsByTagName('topic');
                    yLen = y.length;
                    text += "<h1>" + x[i].getAttribute("name") + "</h1>";

                    for (j = 0; j < yLen; j++) {
                        text += "<div class = 'row'>";
                        text += "<div class = 'col-md-6'>";
                        var mod = j % 5;
                        if (mod == 4) {
                            text += "<div class='bs-callout bs-callout-primary'>";
                            text += "<h4>" + y[j].getAttribute("title") + "</h4>"
                            text += y[j].getElementsByTagName("notes")[0].innerHTML;
                            text += "</div>";
                        } else if (mod == 3) {
                            text += "<div class='bs-callout bs-callout-info'>";
                            text += "<h4>" + y[j].getAttribute("title") + "</h4>"
                            text += y[j].getElementsByTagName("notes")[0].innerHTML;
                            text += "</div>";
                        } else if (mod == 2) {
                            text += "<div class='bs-callout bs-callout-danger'>";
                            text += "<h4>" + y[j].getAttribute("title") + "</h4>"
                            text += y[j].getElementsByTagName("notes")[0].innerHTML;
                            text += "</div>";
                        } else if (mod == 1) {
                            text += "<div class='bs-callout bs-callout-warning'>";
                            text += "<h4>" + y[j].getAttribute("title") + "</h4>"
                            text += y[j].getElementsByTagName("notes")[0].innerHTML;
                            text += "</div>";
                        } else if (mod == 0) {
                            text += "<div class='bs-callout bs-callout-success'>";
                            text += "<h4>" + y[j].getAttribute("title") + "</h4>"
                            text += y[j].getElementsByTagName("notes")[0].innerHTML;
                            text += "</div>";
                        } else {
                            text += "<div class='bs-callout bs-callout-success'>";
                            text += "<h4>" + y[j].getAttribute("title") + "</h4>"
                            text += y[j].getElementsByTagName("notes")[0].innerHTML;
                            text += "</div>";
                        }


                        text += "</div>";
                        var m = y[j].getElementsByTagName("code")[0].innerHTML.length;
                        if (m > 0) {
                            text += "<div class = 'col-md-6'><br>";
                            text += "<pre class='language-java'>";
                            text += y[j].getElementsByTagName("code")[0].innerHTML;
                            text += "</pre>"
                            text += "</div>";
                        }
                        text += "</div>";
                    }
                    text += "<hr>";
                }
                text += "";

                document.getElementById("content").innerHTML =
                    text;
            }
        </script>

	</div>

</body>
</html>