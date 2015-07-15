<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<script>
history.go(1);
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Salary/BatchSalyManager" method="post" onsubmit="init('執行中, 請稍後')">
<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/icon/money_add.gif" id="piftitle">
				</td>
				<td align="left">
				&nbsp;新給批次建檔&nbsp;<input type="hidden" name="exSearch" value="${HRmanagerForm.map.exSearch}" />
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td>
<%@ include file="BatchSalyManager/search.jsp"%>
		</td>
	</tr>
	
<!-- 列表 -->
<c:if test="${saly_mode=='list'}">
<c:if test="${!empty allSaly}">
	<tr>
		<td>		
<%@ include file="BatchSalyManager/list.jsp"%>
		</td>
	</tr>
</c:if>
</c:if>
<!-- 列表end -->


<!-- 編輯 -->
<c:if test="${saly_mode=='edit'}">
	<tr>
		<td>		
<%@ include file="BatchSalyManager/edit.jsp"%>
		</td>
	</tr>
</c:if>
<!-- 編輯end -->



	
	
</html:form>
</table>
<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>
<%@ include file="/pages/include/MyCalendar.jsp" %>