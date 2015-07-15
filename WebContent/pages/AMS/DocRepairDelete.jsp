<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>
<script type="text/javascript" src="<%=basePath%>pages/include/json2.js"></script>

<style type="text/css">
<!--
.style1 {color: #0000FF}
.style3 {
	font-size: 24px;
	font-weight: bold;
}
.title {
	font-size:13px;
	background-color:#ddffdd;
	border-style:solid;
	border-width: 1px 1px 1px 1px;
	border-color:lightblue;
}
.input {
	font-size:13px;
	background-color:#ffffee;
	border-style:solid;
	border-width: 1px 1px 1px 1px;
	border-color:lightblue;
}
.mustInput {
	font-size:13px;
	background-color:#ffcccc;
	border-style:solid;
	border-width: 1px 1px 1px 1px;
	border-color:#cc6666;
}
.descript {
	font-size:12px;
	background-color:#eeffff;
	border-style:solid;
	border-width: 1px 1px 1px 1px;
	border-color:lightblue;
}
-->
</style>
<style type="text/css">
<!--
.style2 {color: #FF0000}
-->
</style>
<form action="/CIS/AMS/DocManager.do" method="post" name="inputForm">
<input name="opmode" type="hidden" value="delete"/>
<input name="docType" type="hidden" value="${DocRepairDelInit.docType}"/>
<!-- for auto input select item use -->
	<div id="popup" style="position:absolute;">
		 <table id="name_table" bgcolor="#FFFAFA" border="0" cellspacing="0" cellpadding="0">
		 	  <tbody id="name_body"></tbody>
		 </table>
	</div> 


<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="2" cellspacing="2">
<script>generateTableBanner('未刷卡補登管理->請確認刪除!');</script>	  
<!-- End Content Page Table Header -->
	<tr><td><table>
	<c:forEach items="${DocRepairDelete}" var="formback">
	<tr>
		<td width="75" class="title">未刷卡原因</td>
		<td class="input">${formback.reason}</td>
	</tr>
	<tr>
		<td width="75" class="title">上班時間</td>
		<td class="input">西元 ${formback.startYear} 年&nbsp;&nbsp;
		${formback.startMonth} 月&nbsp;&nbsp;
		${formback.startDay} 日&nbsp;&nbsp;
		${formback.startHour} 時&nbsp;&nbsp;
		${formback.startMinute} 分
		</td>
	</tr>
	<tr>
		<td width="75" class="title">下班時間</td>
		<td class="input">西元 ${formback.endYear} 年&nbsp;&nbsp;
		${formback.endMonth} 月&nbsp;&nbsp;
		${formback.endDay} 日&nbsp;&nbsp;
		${formback.endHour} 時&nbsp;&nbsp;
		${formback.endMinute} 分
		</td>
	</tr>
	</c:forEach>
	</table></td></tr>
	
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='DeleteConfirm'/>" >&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" >');
	</script>
</table>
<script type="text/javascript" src="<%=basePath%>pages/AMS/include/docQuery.js"></script>
