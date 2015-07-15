<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<!-- 標題 start -->
<html:form action="/Course/BatchStaticTableing" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/calendar_view_month.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">固定節次排課</font></div>		
		</td>
	</tr>	
	<tr>
		<td>
		
		<%@ include file="BatchStaticTableing/search.jsp"%>
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		<input type="submit" name="method" value="<bean:message key='Save'/>" 
		id="Save" class="gGreen">
		
		</td>
	</tr>
	
	
	
	
	<c:if test="${!empty allRules}">
	<tr>
		<td>
		
		<%@ include file="BatchStaticTableing/list.jsp"%>
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		</td>
	</tr>
	</c:if>
	
	
	
	
	
	
	
	
	
	
	
</html:form>
</table>


	

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>