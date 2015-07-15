<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<%response.setHeader("Content-disposition","attachment; filename=techlimit.xls");%>
<html>
	<head></head>
	<body>	
	<table class="hairLineTable" width="99%" border="1">
		<tr>
			<td class="hairLineTdF" style="font-size:20px;" nowrap>主聘科系</td>
			<td class="hairLineTdF" style="font-size:20px;" nowrap>教師職級</td>
			<td class="hairLineTdF" style="font-size:20px;" nowrap>本校職稱</td>
			<td class="hairLineTdF" style="font-size:20px;" nowrap>教師姓名</td>
			<td class="hairLineTdF" style="font-size:20px;">任課時數</td>
		</tr>
		<c:forEach items="${empls}" var="e">
		<tr>
			<td class="hairLineTdF" style="font-size:20px;" nowrap>${e.unitname}</td>
			<td class="hairLineTdF" style="font-size:20px;" nowrap>${e.name}</td>
			<td class="hairLineTdF" style="font-size:20px;" width="100%">${e.sname}</td>
			<td class="hairLineTdF" style="font-size:20px;" nowrap>${e.cname}</td>
			<td class="hairLineTdF" style="font-size:20px;" nowrap>${e.time}</td>
		</tr>		
		</c:forEach>
	</table>
	</body>
</html>