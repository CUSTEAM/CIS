<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/AQ/Qform" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/application_view_icons.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">滿意度調查</font></div>		
		</td>
	</tr>
	
	<c:if test="${empty qform}">
	<tr>
		<td>
		<%@ include file="Qform/direct.jsp" %>
		</td>
	</tr>
	</c:if>
	
	
	<c:if test="${!empty qform}">
	<tr>
		<td>
		<%@ include file="Qform/Qform.jsp" %>
		</td>
	</tr>
	</c:if>
	
	
	<tr height="30">
		<td class="fullColorTable">
		
		
		
		</td>
	</tr>
</html:form>
</table>