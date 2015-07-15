<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<%response.setContentType("application/vnd.ms-excel");
response.setHeader("Content-disposition","attachment;filename=list4CheckOutSeld.xls");	%>
<html>
<head>
</head>
<body>
<table>
<c:if test="${not empty CheckGist}">
	<tr>
		<td>
			<table border="1" width="100%">
				
				<tr height="20">
					<td>開課班級</td>
					<td>科目名稱</td>
					<td>教師姓名</td>
					<td>綱要字數</td>
					<td>每週綱要</td>
					<td>中文簡介字數</td>
					<td>英文簡介字數</td>
					<td>未編輯</td>
				</tr>
				<c:forEach items="${CheckGist}" var="g">
				<tr>	
					<td>${g.ClassName}</td>
					<td>${g.chi_name}</td>
					<td>${g.cname}</td>
					<td>${g.syllabi}</td>
					<td>${g.syl_sub}</td>
					<td>${g.chi}</td>
					<td>${g.eng}</td>
					<td>${g.err}</td>
				</tr>						
				</c:forEach>
			</table>
		</td>
	</tr>
</c:if>
</table>
</body>
</html>