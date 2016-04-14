<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <link rel="stylesheet" href="navbar.css">
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
            <a href="#">
              <span class="glyphicon glyphicon-user"></span> Sign Up</a>
          </li>
        </ul>
      </div>

    </div>
  </nav>

  <div class="container">
    <div class="row">
      <div class="col-md-7">
        <center>
          <img src="images/frontpage.png" height="95%" width="95%" />
          <br>
          <br>
          <font size="30" color="#1D9269" style="font-family: 'Tahoma', Geneva, sans-serif;">Login</font>
          <div class="well" style="width:50%">
           <c:if test="${not empty loginMessage }">
                  <div class="green">${loginMessage }</div>
             </c:if>
            <form:form modelAttribute="registerUser" method="POST">
            <table>
              <tr>
              <td>
                <label for="userName" style="float:left">User name:</label>
               </td>
               <td>
				<form:input path="userName" id="userName" type="text" required="true" class="control form-control" />
				</td>
              </tr>
              <tr>
              <td>
                <label for="password">Password:</label>&nbsp;&nbsp;
               </td>
               <td>
                <form:password path="password" id="password"  required="true" class="control form-control" />
               </td>
             <tr>
              </table>
                <label style="float:left">
                  <input type="checkbox"> Remember me</label>
              <br>
              <br>
              <button type="submit" class="btn btn-success">Login</button>
            </form:form>
          </div>
          </center>
      </div>

      <div class="col-md-5">
        <center>

              <br>
              <br>
              <div id="container" class="well">
                <font size="30" color="#1D9269" style="font-family: 'Tahoma', Geneva, sans-serif;">Signup</font>
                <br>
                
                <c:if test="${not empty message }">
                  <div class="green">${message }</div>
                </c:if>
                <br>
                <form:form modelAttribute="registerUser" method="POST">
                  <table>
                    <tr>
                      <td>
                        <label for="userName">Username:</label>&nbsp;&nbsp;
                      </td>
                      <td>
                        <form:input path="userName" id="userName" required="true" type="text" class="control form-control" />
                        <br>
                      </td>

                    </tr>
                    <tr>
                      <td>
                        <label for="firstName">First Name:</label>&nbsp;&nbsp;
                      </td>
                      <td>
                        <form:input path="firstName" id="firstName" type="text" class="control form-control" />
                        <br>
                      </td>

                    </tr>
                    <tr>
                      <td>
                        <label for="lastName">Last Name:</label>&nbsp;&nbsp;
                      </td>
                      <td>
                        <form:input path="lastName" id="lastName" type="text" class="control form-control" />
                        <br>
                      </td>

                    </tr>
                    <tr>
                      <td>
                        <label for="password">Password:</label>&nbsp;&nbsp;
                      </td>
                      <td>
                        <form:password path="password" id="password" class="control form-control" />
                        <br>
                      </td>

                    </tr>
                    <tr>
                      <td>
                        <label for="Email">Email:</label>&nbsp;&nbsp;
                      </td>
                      <td>
                        <form:input path="email" id="Email" class="control form-control" required="true" />
                        <br>
                      </td>

                    </tr>
                    <tr>
                    <td><label for="role">Role:</label></td>
                  
                  <td><form:select path="role" id="role" class="control form-control">
                    <form:option value="SELECT">Select</form:option>
                    <c:forEach items="${roleTypes}" var="roleIter">
                      <form:option value="${roleIter}">${roleIter}</form:option>
                    </c:forEach>
                  </form:select><br></td>
                  </tr>
                  </table>
                  <br>
                  <br>
                  <input type="submit" value="Submit" class="btn btn-info" />
                  <br>
                  <br>
                  

                </form:form>
              </div>
            </center>

      </div>
    </div>

  </div>

</body>

</html>
