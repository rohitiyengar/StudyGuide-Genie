<%@ page language="java" contentType="text/xml; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,
org.springframework.web.context.support.WebApplicationContextUtils,org.springframework.web.context.WebApplicationContext,
lucenelinksrecommender.LuceneLinksRecommender"%>
<recommendations>
<%
try {
		
		//ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		LuceneLinksRecommender luceneLinksBean = (LuceneLinksRecommender)webApplicationContext.getBean("luceneLinksBean");
		String query = request.getParameter("q");
		if (query == null) {
			throw new Exception();
		}
		Set<String> result = luceneLinksBean.getLinksRecommendations(query, 5);
		for (String s : result) {
			out.println("<link>"+s+"</link>");
		}
}
catch (Exception ex) {
	out.println("<error> Please hit the page using getLinkRecommendations.jsp?q=Your_Query</error>");
}
%>
</recommendations>