<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<style>
td {mso-number-format:General;}
</style>
<%
if(request.getParameter("type").equals("word")){
	response.setContentType("application/vnd.ms-word");
	response.setHeader("Content-Disposition","attachment;filename=list4Dtime.doc");
}
if(request.getParameter("type").equals("excel")){
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-disposition","attachment;filename=list4CheckOutSeld.xls");
}
%>
<html>
<head>
</head>
<body>
<table>
<c:if test="${not empty jList}">
	<tr>
		<td>
			<table border="1" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">

				<tr height="20">
					<td align="center" bgcolor="#f0fcd7">班級</td>
					<td align="center" bgcolor="#f0fcd7">學號</td>
					<td align="center" bgcolor="#f0fcd7">姓名</td>
					<td align="center" bgcolor="#f0fcd7">性別</td>
					<td align="center" bgcolor="#f0fcd7">日期</td>
					<td align="center" bgcolor="#f0fcd7">文號</td>
					<td align="center" bgcolor="#f0fcd7">獎懲原因</td>
					<td align="center" bgcolor="#f0fcd7">種類</td>
					<td align="center" bgcolor="#f0fcd7">次數</td>
					<td align="center" bgcolor="#f0fcd7">種類</td>
					<td align="center" bgcolor="#f0fcd7">次數</td>
				</tr>
					<%int i=0;%>
					<c:forEach items="${jList}" var="dl">
					<%i=i+1;%>
						<tr height="20">
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${dl.ClassName}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.student_no}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.student_name}</td>
							<td align="left" style="mso-number-format:\@"<%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.sex}</td>
							<td align="left" style="mso-number-format:\@"<%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.ddate}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.no}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.name}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.n1}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.cnt1}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.n2}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.cnt2}</td>
						</tr>
					</c:forEach>
			</table>
		</td>
	</tr>
</c:if>
</table>
</body>
</html>