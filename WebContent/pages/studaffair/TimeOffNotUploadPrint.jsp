<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<style>
td {mso-number-format:General;}
</style>
<%
if(request.getParameter("type").equals("word")){
	response.setContentType("application/vnd.ms-word; charset=utf-8");
	response.setHeader("Content-Disposition","attachment;filename=TFNotUploadPrint.doc");
}
if(request.getParameter("type").equals("excel")){
	response.setContentType("application/vnd.ms-excel; charset=utf-8");
	response.setHeader("Content-disposition","attachment;filename=TFNotUploadPrint.xls");
}
%>
<html>
<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
</head>
<body>
<table>
<c:if test="${TFnotUploadList != null}">
	<tr>
		<td>
		<c:if test="${TFnotUploadQueryInit.pmode=='1'}">
			<table border="1" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
				<tr>
					<td colspan="7">
					中華科技大學 未點名教師清冊&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					${TFnotUploadQueryInit.DateStart} ~ ${TFnotUploadQueryInit.DateEnd}<br/>
					</td>
				</tr>
				<tr>
					<td>教師</td>
					<td>科目</td>
					<td>班級</td>
					<td>上課日期</td>
					<td>星期</td>
					<td>E-mail</td>
					<td>備註</td>
				</tr>
				<c:forEach items="${TFnotUploadList}" var="tfnup">
				<tr>
					<td>${tfnup.teacherName}</td>
					<td>${tfnup.subjectName}</td>
					<td>${tfnup.deptClassName}</td>
					<td>${tfnup.teachDate}</td>
					<td>${tfnup.teachWeek}</td>
					<td>${tfnup.email}</td>
					<td>${tfnup.memo}</td>
				</tr>
				</c:forEach>
			</table>
		</c:if>
		
		<c:if test="${TFnotUploadQueryInit.pmode=='2'}">
			<table border="1" align="left" cellpadding="0" cellspacing="1" bgcolor="#CFE69F" width="100%">
				<tr>
					<td colspan="2">
					中華科技大學 未點名教師統計表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					${TFnotUploadQueryInit.DateStart} ~ ${TFnotUploadQueryInit.DateEnd}<br/>
					</td>
				</tr>
				<tr>
					<td>教師</td>
					<td>未點名次數</td>
				</tr>
				<c:forEach items="${TFnotUploadList}" var="tfnup">
				<tr>
					<td>${tfnup.teacherName}</td>
					<td>${tfnup.count}</td>
				</tr>
				</c:forEach>
			</table>
		</c:if>
		</td>
	</tr>
</c:if>
</table>
</body>
</html>