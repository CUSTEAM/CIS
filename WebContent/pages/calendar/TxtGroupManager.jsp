<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Calendar/TxtGroupManager" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/group.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">群組名單管理</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		<%@ include file="TxtGroupManager/create.jsp"%>
		</td>	
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		<input type="submit" name="method"value="<bean:message key='Create'/>" class="gGreen">
		<input type="button" name="method"value="說明" class="gCancel">
		</td>	
	</tr>
	
	
<c:if test="${!empty myGroup}">
	<tr>
		<td>
		<%@ include file="TxtGroupManager/list.jsp"%>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">		
		</td>	
	</tr>
</c:if>
	
</html:form>
</table>