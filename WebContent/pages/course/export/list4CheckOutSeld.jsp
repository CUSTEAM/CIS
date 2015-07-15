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
	response.setHeader("Content-Disposition","attachment;filename=list4Dtime.doc");
}
if(request.getParameter("type").equals("excel")){
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-disposition","attachment;filename=list4CheckOutSeld.xls");	
}

SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd HH:mm");
String date=sf.format(new Date());
%>
<html>
<head>
</head>
<body>
<table>
<c:if test="${not empty checkReSelds}">
	<tr>
		<td>
			<table border="0" align="left" cellpadding="0" cellspacing="1" width="100%">
			<tr>
			<td colspan="15" align="center">
			<font face="標楷體" size="+2">學生同時段重複選課清單</font>
			</td>
			</tr>
			<tr>
			<td colspan="15" align="left">
			<font size="-2">課程管理系統 <%=date%></font>
			</td>
			</tr>
			</table>
			<table border="1" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
				
				<tr height="20">
					<td align="center" bgcolor="#f0fcd7"></td>
					<td align="center" bgcolor="#f0fcd7">學生班級</td>
					<td align="center" bgcolor="#f0fcd7">學號</td>
					<td align="center" bgcolor="#f0fcd7">姓名</td>
					<td align="center" bgcolor="#f0fcd7">課程代碼</td>
					<td align="center" bgcolor="#f0fcd7">課程名稱</td>
					<td align="center" bgcolor="#f0fcd7">開課班級</td>
					<td align="center" bgcolor="#f0fcd7">星期</td>
					<td align="center" bgcolor="#f0fcd7">開始節次</td>
					<td align="center" bgcolor="#f0fcd7">結束節次</td>
					<td align="center" bgcolor="#f0fcd7">重複課程</td>
					<td align="center" bgcolor="#f0fcd7">重複課代</td>
					<td align="center" bgcolor="#f0fcd7">開課班級</td>
					<td align="center" bgcolor="#f0fcd7">星期</td>
					<td align="center" bgcolor="#f0fcd7">開始節次</td>
					<td align="center" bgcolor="#f0fcd7">結束節次</td>
				</tr>
					<%int i=0;%>
					<c:forEach items="${checkReSelds}" var="dl">
					<%i=i+1;%>
						<tr height="20">
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${dl.Oid}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${dl.className2}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.student_no}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.student_name}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.cscode}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.chi_name}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.ClassName}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.week}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.begin}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.end}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.chi_name2}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.cscode2}</td>
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.ClassName2}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.week2}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.begin2}</td>
							<td align="center" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.end2}</td>
						</tr>
					</c:forEach>
			</table>
		</td>
	</tr>
</c:if>
</table>
</body>
</html>