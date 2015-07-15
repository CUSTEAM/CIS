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
	response.setHeader("Content-Disposition","attachment;filename=list4Counseling.doc");
}
if(request.getParameter("type").equals("excel")){
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-disposition","attachment;filename=list4Counseling.xls");
}

SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd HH:mm");
String date=sf.format(new Date());

%>
<html>
<head>
</head>
<body>
<table>
<!-- 教師衝堂 -->
<c:if test="${not empty Counseling}">
	<tr>
		<td>
			<table border="1" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
				<tr height="20">
					<td align="center" bgcolor="#f0fcd7">班級</td>
					<td align="center" bgcolor="#f0fcd7">學號</td>
					<td align="center" bgcolor="#f0fcd7">姓名</td>
					<td align="center" bgcolor="#f0fcd7">科目名稱</td>
					<td align="center" bgcolor="#f0fcd7">期中考成績</td>
					<td align="center" bgcolor="#f0fcd7">輔導任課老師</td>
					<td align="center" bgcolor="#f0fcd7">期未考成績</td>
					<td align="center" bgcolor="#f0fcd7">輔導次數</td>
					<td align="center" bgcolor="#f0fcd7">輔導紀錄</td>
				</tr>
					<%int i=0;%>
					<c:forEach items="${Counseling}" var="dl">
					<%i=i+1;%>
						<tr height="20">					
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.ClassName}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${dl.student_no}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.student_name}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.chi_name}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.score2}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.cname}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.score3}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.sum}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.content}</td>
						</tr>
					</c:forEach>
			</table>
		</td>
	</tr>
</c:if>
</table>
</body>
</html>