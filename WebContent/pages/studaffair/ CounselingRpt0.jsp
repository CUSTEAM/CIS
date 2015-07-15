<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<style>
td {mso-number-format:General;}
</style>
<%
if(request.getParameter("type").equals("word")){
	response.setContentType("application/vnd.ms-word");
	response.setHeader("Content-Disposition","attachment;filename=AssessPaperReport.doc");
}
if(request.getParameter("type").equals("excel")){
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-disposition","attachment;filename=AssessPaperReport.xls");
}
%>
<html>
<head>
</head>
<body>
<table>
<c:if test="${not empty CounselingReoport}">
	<c:set var="report" value="${CounselingReoport}"/>
	<c:set var="reportType" value="${CounselingReportInit.reportType}" />
	<tr>
		<td>
			<table border="1" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
				<c:if test="${reportType == '6'}">
				<tr><td colspan="6" align="center">
				<font style="font-size:20px; font-weight:500;">中華科技大學&nbsp;&nbsp;&nbsp;老師對學生輔導次數統計表</font><br/>
				學年：${CounselingReportInit.schoolYear}&nbsp;&nbsp;&nbsp;學期： ${CounselingReportInit.schoolTerm}
				</td></tr>
				<tr height="20">
					<td align="center" bgcolor="#f0fcd7" width="50">班級</td>					
					<td align="center" bgcolor="#f0fcd7" width="30">老師</td>
					<td align="center" bgcolor="#f0fcd7" width="30">任教科目</td>
					<td align="center" bgcolor="#f0fcd7" width="30">已輔導學生人數</td>
					<td align="center" bgcolor="#f0fcd7" width="30">輔導次數</td>
				</tr>
					<%int i=0;%>
					<c:forEach items="${report}" var="dl">
					<%i=i+1;%>
						<tr height="20">
							<td align="left" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7" <%}%> >${dl.className}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.teacherName}</td>
							<td align="left" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.courseName}</td>
							<td align="right" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.countL_UT}</td>
							<td align="right" style="mso-number-format:\@" <%if(i%2==1){%>bgcolor="#ffffff"<%}else{%> bgcolor="#f0fcd7"<%}%>>${dl.countL}</td>
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