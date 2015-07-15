<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>



<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/DeptAssistant/StdSkill" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/application_view_tile.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">證照管理</font></div>		
		</td>
	</tr>	
	
	<c:if test="${empty skill}">
	<tr>
		<td>
		<%@ include file="StdSkill/search.jsp"%>		
		</td>
	</tr>
	</c:if>
	
	
	<c:if test="${!empty skilist && empty skill}">
	<tr>
		<td>
		<%@ include file="StdSkill/list.jsp"%>
		
		</td>
	</tr>
	</c:if>
	
	<c:if test="${!empty skill}">
	<tr>
		<td>
		<%@ include file="StdSkill/edit.jsp"%>
		
		</td>
	</tr>
	</c:if>
	
	
</html:form>
</table>
<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/MyCalendar.jsp" %>