<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ page import ="java.util.Date"%>
<%@ page import ="java.text.*"%>
<%@ include file="/taglibs.jsp" %>
<style>
td {mso-number-format:General;}
</style>
<%

response.setContentType("application/vnd.ms-excel");
response.setHeader("Content-disposition","attachment;filename=list4CheckCredit.xls");

%>
<html>
<head>
</head>
<body>
<table>
<c:if test="${not empty reOption}">
	<tr>
		<td>
			
			<table border="1" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">

				<tr height="20">
					<td align="center" bgcolor="#f0fcd7">學號</td>
					<td align="center" bgcolor="#f0fcd7">姓名</td>
					<td align="center" bgcolor="#f0fcd7">暑修班級代碼</td>
					<td align="center" bgcolor="#f0fcd7">暑修課程代碼</td>
					<td align="center" bgcolor="#f0fcd7">平日班級代碼</td>
					<td align="center" bgcolor="#f0fcd7">平日課程代碼</td>
					<td align="center" bgcolor="#f0fcd7">星期</td>
					<td align="center" bgcolor="#f0fcd7">開始節次</td>
					<td align="center" bgcolor="#f0fcd7">結束節次</td>
				</tr>
					<%int i=0;%>
					<c:forEach items="${reOption}" var="dl">
					<%i=i+1;%>
						<tr height="20">
							
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.student_no}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${dl.student_name}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.depart_class}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.cscode}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.ddepart_class}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.dcscode}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.week}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.begin}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.end}</td>
						</tr>
					</c:forEach>
			</table>
		</td>
	</tr>
</c:if>
</table>
</body>
</html>