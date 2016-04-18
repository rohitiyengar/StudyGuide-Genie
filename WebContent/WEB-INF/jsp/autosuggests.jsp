<%@ page language="java" contentType="text/xml; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*,
org.springframework.web.context.support.WebApplicationContextUtils,org.springframework.web.context.WebApplicationContext,
luceneindex.LuceneIndexHelper"%>
<suggests>
<%
	
	try {
		
		//ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		LuceneIndexHelper luceneBean = (LuceneIndexHelper)webApplicationContext.getBean("luceneBean");
	
		/*ApplicationContext context = RequestContextUtils.getWebApplicationContext(request);
		 info = context.getBean("SessionInfo");*/
		
		
		int top = Integer.parseInt(request.getParameter("top"));
		Set<String> result = luceneBean.getAutoSuggests(request.getParameter("q"), top);
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