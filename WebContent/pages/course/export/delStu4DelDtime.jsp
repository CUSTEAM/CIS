<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>

<%
if(request.getParameter("type").equals("word")){
response.setContentType("application/vnd.ms-word");
response.setHeader("Content-Disposition","attachment;filename=delStu4DelDtime.doc");
}
if(request.getParameter("type").equals("excel")){
response.setHeader("Content-disposition","attachment; filename=delStu4DelDtime.xls");
}
%>
<html>
<head>
</head>
<body>
<table>
<c:if test="${not empty delStudents}">
	<tr>
		<td>
			<table border="0" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
				
				<tr height="20">
					<td align="center" bgcolor="#f0fcd7">學生學號</td>
					<td align="center" bgcolor="#f0fcd7">學生姓名</td>
					<td align="center" bgcolor="#f0fcd7">學生班級代碼</td>
					<td align="center" bgcolor="#f0fcd7">學生所屬班級</td>
					<td align="center" bgcolor="#f0fcd7">開課班級代碼</td>
					<td align="center" bgcolor="#f0fcd7">開課班級名稱</td>
					<td align="center" bgcolor="#f0fcd7">課程代碼</td>
					<td align="center" bgcolor="#f0fcd7">課程名稱</td>
					<td align="center" bgcolor="#f0fcd7">學分</td>
					<td align="center" bgcolor="#f0fcd7">時數</td>
				</tr>
					<%int i=0;%>
					<c:forEach items="${delStudents}" var="del">
					<%i=i+1;%>
						<tr height="20">
							<td align="center" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${del.student_no}</td>
							<td align="center" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${del.student_name}</td>
							<td align="center" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${del.ClassNo}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${del.ClassName}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${del.depart_class}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${del.ClassName2}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${del.cscode}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${del.chi_name}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${del.credit}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${del.thour}</td>
						</tr>
					</c:forEach>
			</table>
		</td>
	</tr>
</c:if>
</table>
</body>
</html>