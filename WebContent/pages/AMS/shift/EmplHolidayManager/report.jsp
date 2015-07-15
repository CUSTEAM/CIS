<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<style>    
td {mso-number-format:General;}    
</style> 
<%response.setContentType("application/vnd.ms-excel");
response.setHeader("Content-disposition","attachment;filename=list4CheckOutGist.xls");%>
<html>
<head>
</head>
<body>
<table width="68%">
	<tr>
		<td>
			<table border="1" align="left" cellpadding="0" cellspacing="1" width="100%">				
				<tr>
					<td align="center" bgcolor="#f0fcd7">姓名</td>
					<td align="center" bgcolor="#f0fcd7">職稱</td>
					<td align="center" bgcolor="#f0fcd7">年資</td>
					<td align="center" bgcolor="#f0fcd7">日數</td>
				</tr>				
				<c:forEach items="${hempl}" var="h" varStatus="h1">				
				<tr <c:if test="${h1.index%2!=0}">bgColor="#f0fcd7"</c:if>>			
					<td align="center">${h.cname}</td>
					<td align="center">${h.sname}</td>
					<td align="center">${h.Adate}</td>
					<td align="center">${h.year}</td>						
				</tr>	
				</c:forEach>
			</table>
		</td>
	</tr>
</table>