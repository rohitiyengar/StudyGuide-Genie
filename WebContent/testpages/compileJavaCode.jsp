<%@ page language="java" contentType="text/xml; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="org.springframework.web.context.support.WebApplicationContextUtils,org.springframework.web.context.WebApplicationContext,compile.MyJavaCompiler"%>
<result>
<%
	WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
	MyJavaCompiler compileBean = (MyJavaCompiler)webApplicationContext.getBean("compileBean");
	String code = request.getParameter("source");
	out.println(compileBean.checkValidJavaCode(code));
%>
</result>