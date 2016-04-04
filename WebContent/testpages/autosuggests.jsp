<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*"%>
<jsp:useBean id="autosuggesthelper" class="luceneindex.LuceneIndexHelper"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AutoSuggests</title>
</head>
<body>
<%
	try {
		autosuggesthelper.initialize();
		int top = Integer.parseInt(request.getParameter("top"));
		Set<String> result = autosuggesthelper.getAutoSuggests(request.getParameter("q"), top);
		for (String s : result) {
			out.println(s+"<br/>");
		}
	}
	catch(Exception ex) {
		out.println("Please make sure you pass the query string as ?q=your_query&top=no_of_top_results");
		out.println(ex);
	}
%>
</body>
</html>