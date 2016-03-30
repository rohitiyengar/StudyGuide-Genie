<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="css/navbar.css">
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
          <li><a href="#">About Us</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
          <li>
            <a href="registerUser.html">
              <span class="glyphicon glyphicon-user"></span> Sign Up</a>
          </li>
        </ul>
      </div>

    </div>
  </nav>
  <center>
    <div class="container">

      <img src="images/frontpage.png" />

      <font size="30" color="#1D9269" style = "font-family: 'Tahoma', Geneva, sans-serif;">Login</font>
      <div class="well" style="width:30%">
        <form role="form">
          <div class="form-group">
            <label for="email" style="float:left">Email:</label>

            <input type="email" class="form-control" id="email" placeholder="Email">
          </div>
          <div class="form-group">
            <label for="pwd" style="float:left">Password:</label>
            <input type="password" class="form-control" id="pwd" placeholder="Password">
          </div>
          <div class="checkbox">
            <label style="float:left">
              <input type="checkbox"> Remember me</label>
          </div>
          <br>
          <br>
          <button type="submit" class="btn btn-success">Login</button>
        </form>
      </DIV>
    </div>

  </center>
</body>

</html>