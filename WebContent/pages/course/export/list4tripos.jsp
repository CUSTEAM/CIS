<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<%
if(request.getParameter("type").equals("excel")){
	response.setCharacterEncoding("big5");
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-disposition","attachment;filename=list4CheckOutSeld.xls");
}
%>
<style>

td {mso-number-format:General;}

</style>
<html>
<head>
</head>
<body>
	
<table border="1">

	<tr height="20">
		<td align="center" bgcolor="#f0fcd7">開課班級</td>
		<td align="center" bgcolor="#f0fcd7">科目代碼</td>
		<td align="center" bgcolor="#f0fcd7">科目名稱</td>
		<td align="center" bgcolor="#f0fcd7">教師姓名</td>
		
		<td align="center" bgcolor="#f0fcd7">選課人數</td>
		<td align="center" bgcolor="#f0fcd7">期中未評</td>
		<td align="center" bgcolor="#f0fcd7">期中被當</td>
		<td align="center" bgcolor="#f0fcd7">期中比例</td>
		<td align="center" bgcolor="#f0fcd7">期末未評</td>
		<td align="center" bgcolor="#f0fcd7">期末被當</td>
		<td align="center" bgcolor="#f0fcd7">期末比例</td>
		<td align="center" bgcolor="#f0fcd7">學期未評</td>
		<td align="center" bgcolor="#f0fcd7">學期被當</td>
		<td align="center" bgcolor="#f0fcd7">學期比例</td>			
	</tr>
		<%int i=0;%>
		<c:forEach items="${triposList}" var="dl">
		<%i=i+1;%>
			<tr height="20">
				<td align="left">${dl.ClassName}</td>
				<td align="left">${dl.cscode}</td>
				<td align="left">${dl.chi_name}</td>
				<td align="left">${dl.cname}</td>
				
				<td align="center">${dl.stuSelect}</td>
				<td align="center">${dl.midNon}</td>
				<td align="center">${dl.midCount}</td>
				<td align="center">${dl.midavg}</td>
				<td align="center">${dl.endNon}</td>
				<td align="center">${dl.endCount}</td>
				<td align="center">${dl.endavg}</td>
				<td align="center">${dl.scoreNon}</td>
				<td align="center">${dl.scoreCount}</td>
				<td align="center">${dl.scoreavg}</td>
			</tr>
		</c:forEach>
</table>

</body>
</html>