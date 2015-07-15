<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<%response.setHeader("Content-disposition","attachment; filename=CheckThour.xls");%>
<html>
<head>
</head>
<body>
<display:table name="${techs}" export="true" id="row" sort="list" excludedParams="*" class="list">
		<display:column title="系所名稱" property="name" class="left" />
		<display:column title="教師姓名" property="cname" class="left" />
		<display:column title="職稱" property="sname" class="left" />
		<display:column title="基本時數" property="time" class="left" />
		<display:column title="可超時數" property="time_over" class="left" />
		<display:column title="排課時數" property="total" class="left" />
		<display:column title="排課天數" property="day" class="left" />
		<display:column title="備註"/>
	</display:table>
</body>
</html>