<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>About Us</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/navbar.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <style>
        #title {
            font-family: papyrus;
            color: green;
        }

        #about,
        #head {
            font-family: Rockwell;
        }
        .member
        {
          font-family: Garamond;
        }
        #message
        {
          font-family: Brush Script MT;
          font-style: oblique;

        }
    </style>
</head>

<body>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                    <b>Study Guide |</b>
                </a>
            </div>
            <div>

            </div>

        </div>
    </nav>

    <div class="container">
        <center>
            <div >
                <h1 id="head"> CSE 591: Adaptive Web </h1>
                <h3> Course Project </h3></div>
            <img src="images/frontpage.png" width="50%" height="50%" />
            <h2 id="title">Your Java Notes, Simplified!</h2>
            <br>
            <div class="well">
                <h3 id="message">A system to help you organize notes, code and aid learning!</h3>
                <br>
                <h2 id="about">Group</h2>
                <table>
                    <tr><h2>
                        <td><h3 class="member"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></h3></td>
                        <td>&nbsp;&nbsp;</td>
                        <td align="center"><h2 class="member">Vignesh Iyer</h2></td>
                      </h2>
                    </tr>
                    <tr>
                        <td><h3 class="member"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></h3></td>
                        <td>&nbsp;&nbsp;</td>
                        <td align="center"><h2 class="member">Rohit Iyengar</h2></td>
                    </tr>
                        <td><h3 class="member"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></h3></td>
                        <td>&nbsp;&nbsp;</td>
                        <td align="center"><h2 class="member">Abhishek Singh</h2></td>
                    <tr>
                    </tr>
                </table>
                <hr>


            </div>
            <a href="${pageContext.request.contextPath}/" class = "btn btn-success"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> &nbsp;Home</a>
        </center>
    </div>
</body>

</body>
</html>