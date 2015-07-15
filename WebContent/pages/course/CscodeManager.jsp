<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Course/CscodeManager" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/book_addresses.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">科目代碼管理</font></div>		
		</td>
	</tr>	
	<tr>
		<td>		
		<%@ include file="CscodeManager/search.jsp"%>		
		</td>
	</tr>	
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" 
		value="<bean:message key='Query'/>" 
		id="AddHoliday" class="gSubmit">					
		
		<input type="submit" name="method" 
		value="<bean:message key='Create'/>" 
		id="AddHoliday" class="gGreen">		
		</td>
	</tr>	
	<c:if test="${!empty css}">
	<tr>
		<td>
		<%@ include file="CscodeManager/list.jsp"%>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center"></td>
	</tr>
	</c:if>
</html:form>
</table>