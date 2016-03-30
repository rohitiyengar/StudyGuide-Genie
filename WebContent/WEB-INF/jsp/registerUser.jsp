<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register User</title>
</head>
<body>
	<div id="container">
		<h2>Register</h2>
		<c:if test="${not empty message }">
			<div class="green">${message }</div>
		</c:if>
		<form:form modelAttribute="registerUser" method="POST">
			<label for="userName">Username</label>
			<form:input path="userName" id="userName" type="text"/>
			</br>
			
			<label for="firstName">First Name</label>
			<form:input path="firstName" id="firstName" type="text"/>
			</br>
			
			<label for="lastName">Last Name</label>
			<form:input path="lastName" id="lastName" type="text"/>
			</br>
			
			
			<label for="password">Password</label>
			<form:password path="password" id="password"/>
			</br>
			
			<label for="Email">Email</label>
			<form:input path="email" id="Email"/>
			</br>
			<input type="submit" value="Submit" />
			
			<label for="role">Role</label>
			<form:select path="role" id="role">
			<form:option value="SELECT">SELECT</form:option>
			<c:forEach items="${roleTypes}"
			 var="roleIter">
				<form:option value="${roleIter}">${roleIter}</form:option>
			</c:forEach>
			</form:select>
		</form:form>
	</div>
</body>
</html>