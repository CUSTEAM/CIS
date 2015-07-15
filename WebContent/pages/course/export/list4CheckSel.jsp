<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
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
%>
<html>
<head>
</head>
<body>
<table>
<c:if test="${not empty CheckSelimitList}">
	<tr>
		<td>
			<table border="1" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
				<tr height="20">
					<td align="center" bgcolor="#f0fcd7">系所</td>
					<td align="center" bgcolor="#f0fcd7">開課班級</td>
					<td align="center" bgcolor="#f0fcd7">班級代碼</td>
					<td align="center" bgcolor="#f0fcd7">科目名稱</td>
					<td align="center" bgcolor="#f0fcd7">科目代碼</td>
					<td align="center" bgcolor="#f0fcd7">教師姓名</td>
					<td align="center" bgcolor="#f0fcd7">學分數</td>
					<td align="center" bgcolor="#f0fcd7">人數上限</td>
					<td align="center" bgcolor="#f0fcd7">已選人數</td>
				</tr>
					<%int i=0;%>
					<c:forEach items="${CheckSelimitList}" var="dl">
					<%i=i+1;%>
						<tr height="20" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%>>
							<td align="left" style="mso-number-format:\@">${dl.name}</td>
							<td align="left" style="mso-number-format:\@">${dl.ClassName}</td>
							<td align="left" style="mso-number-format:\@">${dl.ClassNo}</td>
							<td align="left">${dl.chi_name}</td>
							<td align="left">${dl.cscode}</td>
							<td align="left">${dl.cname}</td>
							<td align="left">${dl.credit}</td>
							<td align="left">${dl.Select_Limit}</td>
							<td align="left">${dl.count_now}</td>
						</tr>
					</c:forEach>
			</table>
		</td>
	</tr>
</c:if>
</table>
</body>
</html>