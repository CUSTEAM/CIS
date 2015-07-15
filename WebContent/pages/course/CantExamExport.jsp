<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<style>
td {mso-number-format:General;}
</style>
<%
if(request.getParameter("type").equals("word")){
	response.setContentType("application/vnd.ms-word");
	response.setHeader("Content-Disposition","attachment;filename=CantExamExport.doc");
}
if(request.getParameter("type").equals("excel")){
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-disposition","attachment;filename=CantExamExport.xls");
}
%>
<html>
<head>
</head>
<body>
<table>
<c:if test="${not empty CantExamList}">
	<tr>
		<td>
			<table border="1" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
				<tr>
				<td colspan="7">${CantExamInit.school_year}學年度第${CantExamInit.school_term}學期 期末扣考名單</td>
				</tr>
				<c:if test="${CantExamInit.sorttype == '0'}">
				<tr height="20">
					<td align="center" bgcolor="#f0fcd7">扣考科目</td>
					<td align="center" bgcolor="#f0fcd7">班級</td>
					<td align="center" bgcolor="#f0fcd7">學號</td>
					<td align="center" bgcolor="#f0fcd7">姓名</td>
					<td align="center" bgcolor="#f0fcd7">每週節數</td>
					<td align="center" bgcolor="#f0fcd7">曠缺節數</td>
					<td align="center" bgcolor="#f0fcd7">扣考節數</td>
				</tr>
					<%int i=0;%>
					<c:forEach items="${CantExamList}" var="dl">
					<%i=i+1;%>
						<tr height="20">
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${dl.subjectName}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.stDeptClassName}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.studentNo}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.studentName}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.period}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.timeOff}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.tfLimit}</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${CantExamInit.sorttype == '1'}">
				<tr height="20">
					<td align="center" bgcolor="#f0fcd7">班級</td>
					<td align="center" bgcolor="#f0fcd7">學號</td>
					<td align="center" bgcolor="#f0fcd7">姓名</td>
					<td align="center" bgcolor="#f0fcd7">扣考科目</td>
					<td align="center" bgcolor="#f0fcd7">每週節數</td>
					<td align="center" bgcolor="#f0fcd7">曠缺節數</td>
					<td align="center" bgcolor="#f0fcd7">扣考節數</td>
				</tr>
					<%int i=0;%>
					<c:forEach items="${CantExamList}" var="dl">
					<%i=i+1;%>
						<tr height="20">
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.stDeptClassName}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.studentNo}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.studentName}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${dl.subjectName}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.period}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.timeOff}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.tfLimit}</td>
						</tr>
					</c:forEach>
				</c:if>
				
			</table>
		</td>
	</tr>
</c:if>
</table>
</body>
</html>