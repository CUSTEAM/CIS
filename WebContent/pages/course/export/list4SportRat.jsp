<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<style>
td {mso-number-format:General;}
</style>
<%
if(request.getParameter("type").equals("word")){
	response.setContentType("application/vnd.ms-word");
	response.setHeader("Content-Disposition","attachment;filename=list4SportRat.doc");
}
if(request.getParameter("type").equals("excel")){
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-disposition","attachment;filename=list4SportRat.xls");
}
%>
<html>
  <body>
	<table>
		<tr>
			<td>
			<%=request.getParameter("type") %> / <%=request.getParameter("oid") %>
			</td>
    	</tr>
	</table>























  </body>
</html>
