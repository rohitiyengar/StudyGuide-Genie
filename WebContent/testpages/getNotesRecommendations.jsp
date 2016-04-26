<%@ page language="java" contentType="text/xml; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,
org.springframework.web.context.support.WebApplicationContextUtils,org.springframework.web.context.WebApplicationContext,
lucenenotesrecommender.LuceneNotesRecommender,model.Student,model.Topic"%>
<recommendations>
<%
try {
		
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
		
		int noOfReco = 5;
		 
		Set<String> result = luceneNotesBean.getNotesRecommendations(topicid, studentid, studentnotes, noOfReco);
		for (String s : result) {
			out.println("<text>"+s+"</text>");
		}
	}
	catch(Exception ex) {
		//System.out.println(ex);
		out.println("Please make sure you pass the query string as ?studentnotes=<your_notes>");
		//out.println(ex);
	}
%>
</recommendations>