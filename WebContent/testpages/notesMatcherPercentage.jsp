<%@ page language="java" contentType="text/xml; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:useBean id="similarityBean"
	class="lucenesimilarity.LuceneSimilarityScoreHelper"></jsp:useBean>
<score> <% 
	String topicName = request.getParameter("topic");
	String noteSentence = request.getParameter("notes");
	out.println(similarityBean.getScore(topicName, noteSentence));
%> </score>