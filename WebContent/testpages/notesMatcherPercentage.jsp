<%@ page language="java" contentType="text/xml; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="org.springframework.web.context.support.WebApplicationContextUtils,org.springframework.web.context.WebApplicationContext,
luceneindex.LuceneIndexHelper"%>
<score> <% 
	WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
	LuceneIndexHelper luceneBean = (LuceneIndexHelper)webApplicationContext.getBean("luceneBean");
	String topicName = request.getParameter("topic");
	String noteSentence = request.getParameter("notes");
	out.println(luceneBean.getScore(topicName, noteSentence));
%> </score>