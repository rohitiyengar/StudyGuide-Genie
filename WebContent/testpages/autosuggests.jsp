<%@ page language="java" contentType="text/xml; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*"%>
<jsp:useBean id="autosuggesthelper" class="luceneindex.LuceneIndexHelper"></jsp:useBean>
<suggests>
<%
	try {
		autosuggesthelper.initialize();
		int top = Integer.parseInt(request.getParameter("top"));
		Set<String> result = autosuggesthelper.getAutoSuggests(request.getParameter("q"), top);
		for (String s : result) {
			out.println("<text>"+s+"</text>");
		}
	}
	catch(Exception ex) {
		out.println("Please make sure you pass the query string as ?q=your_query&top=no_of_top_results");
		out.println(ex);
	}
%>
</suggests>