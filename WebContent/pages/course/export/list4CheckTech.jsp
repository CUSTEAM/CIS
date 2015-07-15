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
	response.setHeader("Content-Disposition","attachment;filename=list4CheckTech.doc");
}
if(request.getParameter("type").equals("excel")){
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-disposition","attachment;filename=list4CheckTech.xls");
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
<c:if test="${not empty CheckTech}">
	<tr>
		<td>
			<table border="1" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
				<tr height="20">
					<td align="center" bgcolor="#f0fcd7">授課教師</td>
					<td align="center" bgcolor="#f0fcd7">開課班級</td>
					<td align="center" bgcolor="#f0fcd7">開課班級</td>
					<td align="center" bgcolor="#f0fcd7">課程名稱</td>
					<td align="center" bgcolor="#f0fcd7">星期</td>
					<td align="center" bgcolor="#f0fcd7">開始節次</td>
					<td align="center" bgcolor="#f0fcd7">結束節次</td>
				</tr>
					<%int i=0;%>
					<c:forEach items="${CheckTech}" var="dl">
					<%i=i+1;%>
						<tr height="20">
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${dl.cname}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${dl.depart_class}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.ClassName}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.chi_name}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.week}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.begin}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.end}</td>
						</tr>
					</c:forEach>
			</table>
		</td>
	</tr>
</c:if>
</table>
</body>
</html>