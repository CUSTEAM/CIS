<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ page import ="java.util.Date"%>
<%@ page import ="java.text.*"%>
<%@ include file="/taglibs.jsp" %>
<style>
td {mso-number-format:General;}
</style>
<%
if(request.getParameter("type").equals("word")){
	response.setContentType("application/vnd.ms-word");
	response.setHeader("Content-Disposition","attachment;filename=list4CheckCredit.doc");
}
if(request.getParameter("type").equals("excel")){
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-disposition","attachment;filename=list4CheckCredit.xls");
}

SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd HH:mm");
String date=sf.format(new Date());
%>
<html>
<head>
</head>
<body>
<table>
<c:if test="${not empty CheckCredit}">
	<tr>
		<td>
			<table border="0" align="left" cellpadding="0" cellspacing="1" width="100%">
			<tr>
			<td colspan="6" align="center">
			<font face="標楷體" size="+2">學分不足的學生清單</font>
			</td>
			</tr>
			<tr>
			<td colspan="6" align="right">
			<font size="-2">課程管理系統 <%=date%></font>
			</td>
			</tr>
			</table>
			<table border="1" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">

				<tr height="20">
					<td align="center" bgcolor="#f0fcd7">學號</td>
					<td align="center" bgcolor="#f0fcd7">姓名</td>
					<td align="center" bgcolor="#f0fcd7">班級代碼</td>
					<td align="center" bgcolor="#f0fcd7">班級名稱</td>
					<td align="center" bgcolor="#f0fcd7">學分數</td>
					<td align="center" bgcolor="#f0fcd7">時數</td>
				</tr>
					<%int i=0;%>
					<c:forEach items="${CheckCredit}" var="dl">
					<%i=i+1;%>
						<tr height="20">
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${dl.student_no}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.student_name}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.ClassNo}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.ClassName}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.creditLess}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.thourLess}</td>
						</tr>
					</c:forEach>
			</table>
		</td>
	</tr>
</c:if>
</table>
</body>
</html>