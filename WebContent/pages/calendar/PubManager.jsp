<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Calendar/PubManager" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/calendar_1.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">共用行事曆管理</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		<%@ include file="PubManager/view4month.jsp"%>		
		</td>	
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		
		</td>	
	</tr>
</html:form>
</table>
