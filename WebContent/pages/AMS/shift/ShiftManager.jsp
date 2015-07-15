<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<!-- 標題 start -->
<html:form action="/AMS/ShiftManager" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/table.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">班別應上應下時間管理</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		<%@ include file="ShiftManager/search.jsp"%>		
		</td>
	</tr>
	<tr>
		<td>
		<%@ include file="ShiftManager/list.jsp"%>		
		</td>
	</tr>
</html:form>
</table>