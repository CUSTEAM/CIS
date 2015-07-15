<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<script>
history.go(1);
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="Salary/TemplateManager" method="post" onsubmit="init('執行中, 請稍後')">
<!-- 標題列 start-->	
	<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/icon/money.gif" id="piftitle">
				</td>
				<td align="left">
				&nbsp;薪資基本設定&nbsp;<input type="hidden" name="exSearch" value="${HRmanagerForm.map.exSearch}" />
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
<!-- 標題列 end-->

<c:if test="${empty mode}">
<!-- 搜尋列 start-->
	<tr>
		<td>		
<%@ include file="TemplateManager/search.jsp"%>
<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>
		</td>
	</tr>
<!-- 搜尋列 end-->
</c:if>

<!-- 列表 start -->
<c:if test="${mode=='list'}">
	<tr>
		<td>
		<%@ include file="TemplateManager/list.jsp"%>	
		</td>
	</tr>
</c:if>
<!-- 列表 end -->

<!-- 個人編輯 start -->
<c:if test="${mode=='edit'}">
	<tr>
		<td>
		<%@ include file="TemplateManager/edit.jsp"%>
		</td>
	</tr>
</c:if>
<!-- 個人編輯 end -->	

</html:form>
</table>