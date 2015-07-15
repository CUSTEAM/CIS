<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<%
	response.setContentType("application/vnd.ms-word");
	response.setHeader("Content-Disposition","attachment;filename=coQuestion.doc");
%>



<%if(request.getParameter("type").equals("e")){%>

<html>
<head>
<style>
td {mso-number-format:General;}
</style>
</head>
<body>

課程名稱:<br>
課程代碼:<br>
學號:
<hr/>
<b>本課程包含遠距教學內容</b><br><br>
<c:forEach items="${eQuest}" var="n">
<li>
${n.options}
	<ul>
	<c:forEach items="${n.subOptions}" var="sn">
		□${sn.options}&nbsp;
	</c:forEach>
	</ul>
	
</li>


</c:forEach>
</body>
</html>



























<%}else{%>

<html>
<head>
<style>
td {mso-number-format:General;}
</style>
</head>
<body>

課程名稱:<br>
課程代碼:<br>
學號:
<hr/>
<b>本課程不包含遠距教學內容</b><br><br>
<c:forEach items="${nQuest}" var="n">
<li>
${n.options}
	<ul>
	<c:forEach items="${n.subOptions}" var="sn">
		□${sn.options}&nbsp;
	</c:forEach>
	</ul>
	
</li>


</c:forEach>
</body>
</html>
<%}%>

