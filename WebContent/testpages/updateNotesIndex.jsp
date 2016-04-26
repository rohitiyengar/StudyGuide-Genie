<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,
org.springframework.web.context.support.WebApplicationContextUtils,org.springframework.web.context.WebApplicationContext,
lucenenotesrecommender.LuceneNotesRecommender,model.Student,model.Topic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Notes Index</title>
</head>
<body>
<%
//ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		LuceneNotesRecommender luceneNotesBean = (LuceneNotesRecommender)webApplicationContext.getBean("luceneNotesBean");
	
		/*ApplicationContext context = RequestContextUtils.getWebApplicationContext(request);
		 info = context.getBean("SessionInfo");*/
		 Student student = (Student)request.getSession().getAttribute("sessionUser");
		 Topic topic = (Topic)request.getSession().getAttribute("topic");
		 
		int topicid = topic.getTopicId();
		String studentid = student.getStudentId();
		String studentnotes = request.getParameter("studentnotes");
		
		luceneNotesBean.updateNotesIndex(topicid, studentid, studentnotes);


%>
</body>
</html>