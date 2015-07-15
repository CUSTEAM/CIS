<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>



<table width="100%" cellpadding="0" cellspacing="0" border="0">
<!-- 標題 start -->
<html:form action="/Course/FastCCS" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon_Member_add.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">快速加退選</font></div>		
		</td>
	</tr>
	
	
	<tr>
		<td>
		<%@ include file="FastCCS/search.jsp"%>
		<td>
	</tr>
	
	

	<c:if test="${!empty myCs}">
	<tr>
		<td>
		<%@ include file="FastCCS/list.jsp"%>
		<td>
	</tr>
	</c:if>
	
</html:form>
</table>

<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxUniMod.jsp" %>