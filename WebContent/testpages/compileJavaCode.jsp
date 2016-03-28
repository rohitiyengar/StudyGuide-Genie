<%@ page language="java" contentType="text/xml; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="compileBean" class="compile.MyJavaCompiler"></jsp:useBean>
<result>
<%
	String code = request.getParameter("source");
	out.println(compileBean.checkValidJavaCode(code));
%>
</result>