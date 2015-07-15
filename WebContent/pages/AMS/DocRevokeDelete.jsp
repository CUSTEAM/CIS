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
.docopacity {
	font-size:12px; 
	background-color:#d3d3d3; 
	border-style:solid; 
	border-width: 1px 1px 1px 1px;
	border-color:#ffa500;}
-->
</style>
<style type="text/css">
<!--
.style2 {color: #FF0000}
-->
</style>
<form action="/CIS/AMS/DocManager.do" method="post" name="inputForm">
<input name="opmode" type="hidden" value="delete"/>
<input name="docType" type="hidden" value="${DocRevokeDelInit.docType}"/>
<!-- for auto input select item use -->
	<div id="popup" style="position:absolute;">
		 <table id="name_table" bgcolor="#FFFAFA" border="0" cellspacing="0" cellpadding="0">
		 	  <tbody id="name_body"></tbody>
		 </table>
	</div> 


<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="2" cellspacing="2">
<script>generateTableBanner('銷假單管理->請確認刪除!');</script>	  
<!-- End Content Page Table Header -->


	<c:forEach items="${DocRevokeDelete}" var="formback">
	<tr><td style="font-size:12px; 
	background-color:#d3d3d3; 
	border-style:solid; 
	border-width: 2px 1px 2px 1px;
	border-color:#ffa500;">
	<table width="100%" id="docopacity">
	<c:set var="askleave" value="${formback.revokedDoc}"></c:set>
	<tr>
		<td  class="title" colspan="2" style="background-color:#9999ee;">銷假假單內容</td>
	</tr>
	<tr>
		<td width="75" class="title">請假事由</td>
		<td class="input">${askleave.reason}</td>
	</tr>
	<tr>
		<td width="75" class="title">假別</td>
		<td class="input">${askleave.askLeaveName}</td>
	</tr>
	<tr>
		<td width="75" class="title">開始日期</td>
		<td class="input">西元 ${askleave.startYear} 年&nbsp;&nbsp;
		${askleave.startMonth} 月&nbsp;&nbsp;
		${askleave.startDay} 日&nbsp;&nbsp;
		${askleave.startHour} 時&nbsp;&nbsp;
		${askleave.startMinute} 分
		</td>
	</tr>
	<tr>
		<td width="75" class="title">結束日期</td>
		<td class="input">西元 ${askleave.endYear} 年&nbsp;&nbsp;
		${askleave.endMonth} 月&nbsp;&nbsp;
		${askleave.endDay} 日&nbsp;&nbsp;
		${askleave.endHour} 時&nbsp;&nbsp;
		${askleave.endMinute} 分
		</td>
	</tr>
	<tr>
		<td width="75" class="title">請假日數</td>
		<td class="input">${askleave.totalDay} 日&nbsp;&nbsp;
		${askleave.totalHour} 時&nbsp;&nbsp;
		${askleave.totalMinute} 分
		</td>
	</tr>
	<tr>
		<td width="75" class="title">授課節數</td>
		<td class="input">共：${askleave.teachPeriod} 節 
		</td>
	</tr>
	<tr>
		<td width="75" class="title">代理人</td>
		<td class="input">姓名 ${askleave.fscname} </td>
	</tr>
	<tr>
		<td width="75" class="title" valign="top">備註</td>
		<td class="input">${askleave.memo}</td>
	</tr>
	</table></td></tr>
	
	<tr><td><table width="100%">
	<tr>
		<td width="75" class="title">銷假原因</td>
		<td class="input">${formback.reason}</td>
	</tr>
	<tr>
		<td width="75" class="title" valign="top">備註</td>
		<td class="input"><textarea rows="3" cols="60" name="memo">${formback.memo}</textarea>
		</td>
	</tr>
	</table></td></tr>
	</c:forEach>
	
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='DeleteConfirm'/>" >&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" >');
	</script>
</table>
<script type="text/javascript" src="<%=basePath%>pages/AMS/include/docQuery.js"></script>
