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
<style>body{background-color:#cccccc;}td{font-size:18px;color:#333333;font-family:新細明體;mso-number-format:\\@;}</style>
</head>
<body>
<table>
<c:if test="${not empty CheckCredit}">
	<tr>
		<td>
			<table border="0" align="left" cellpadding="0" cellspacing="1" width="100%">
			<tr>
			<td colspan="6" align="center" bgcolor="#ffffff">
			<font face="標楷體" size="+2">學分異常</font>
			</td>
			</tr>
			<tr>
			<td colspan="6" align="right" bgcolor="#ffffff">
			<font size="-2"><%=date%></font>
			</td>
			</tr>
			</table>
			<table border="1" align="left" cellpadding="0" cellspacing="1" width="100%">


				<tr>
					<td align="center">狀態</td>
					<td align="center">學號</td>
					<td align="center">姓名</td>
					<td align="center">班級代碼</td>
					<td align="center">班級名稱</td>
					<td align="center">已選</td>
					<td align="center">上限</td>
					<td align="center">下限</td>
				</tr>
					<%int i=0;%>
					<c:forEach items="${CheckCredit}" var="dl">
					<%i=i+1;%>
						<tr bgcolor="#ffffff">
							<td align="left" style="mso-number-format:\@">${dl.occur_status}</td>
							<td align="left" style="mso-number-format:\@">${dl.student_no}</td>
							<td align="left" style="mso-number-format:\@">${dl.student_name}</td>
							<td align="left" style="mso-number-format:\@">${dl.ClassNo}</td>
							<td align="left">${dl.ClassName}</td>
							<td align="left">${dl.cnt}</td>
							<td align="left">${dl.max}</td>
							<td align="left">${dl.min}</td>
						</tr>
					</c:forEach>
			</table>
		</td>
	</tr>
</c:if>
</table>
</body>
</html>