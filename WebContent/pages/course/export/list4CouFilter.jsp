<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<style>
td {mso-number-format:General;}
</style>
<%
if(request.getParameter("type").equals("word")){
	response.setContentType("application/vnd.ms-word");
	response.setHeader("Content-Disposition","attachment;filename=list4CouFilter.doc");
}
if(request.getParameter("type").equals("excel")){
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-disposition","attachment;filename=list4CouFilter.xls");
}
%>
<html>
<head>
</head>
<body>
<table>
<c:if test="${not empty selectFilterCouList}">
	<tr>
		<td>
			<table border="0" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">

				<tr height="20">
					<td align="center" bgcolor="#f0fcd7">班級名稱</td>
					<td align="center" bgcolor="#f0fcd7">班級代碼</td>
					<td align="center" bgcolor="#f0fcd7">科目名稱</td>
					<td align="center" bgcolor="#f0fcd7">科目代碼</td>
					<td align="center" bgcolor="#f0fcd7">教師姓名</td>
					<td align="center" bgcolor="#f0fcd7">選別</td>
					<td align="center" bgcolor="#f0fcd7">學分</td>
					<td align="center" bgcolor="#f0fcd7">時數</td>
					<td align="center" bgcolor="#f0fcd7">篩選人數</td>
					<td align="center" bgcolor="#f0fcd7">上限人數</td>
					<td align="center" bgcolor="#f0fcd7">規則</td>
					<td align="center" bgcolor="#f0fcd7">型態</td>
					<td align="center" bgcolor="#f0fcd7">原因</td>
				</tr>
					<%int i=0;%>
					<c:forEach items="${selectFilterCouList}" var="dl">
					<%i=i+1;%>
						<tr height="20">
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${dl.departClass2}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.departClass}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.chiName2}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.cscode}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.techName}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.opt2}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.credit}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.thour}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.stuSelect}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.Select_Limit}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.openName}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.elearningName}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.type}</td>
						</tr>
					</c:forEach>
			</table>
		</td>
	</tr>
</c:if>
</table>
</body>
</html>