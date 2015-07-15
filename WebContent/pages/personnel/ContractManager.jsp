<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Personnel/ContractManager" method="post" onsubmit="init('執行中, 請稍後')">
<!-- 標題列 start-->	
	<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/folder_page.gif" id="piftitle">
				</td>
				<td align="left">
				&nbsp;聘書管理&nbsp;<input type="hidden" name="exSearch" value="${HRmanagerForm.map.exSearch}" />
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td>
<!-- 查詢 start -->
<c:import url="/pages/personnel/ContractManager/search.jsp"/>
<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
	<tr>
		<td id="ds_calclass"></td>
	</tr>
</table>
<!-- 查詢 end -->
		</td>
	</tr>
<c:if test="${!empty oldContract}">
	<tr>
		<td>
<!-- 列表 start -->
<c:import url="/pages/personnel/ContractManager/listOld.jsp"/>
<!-- 列表 end -->	
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<INPUT type="submit"
				   name="method" id="editContract"
				   onMouseOver="showHelpMessage('立即變更', 'inline', this.id)" 
			 	   onMouseOut="showHelpMessage('', 'none', this.id)"
				   value="<bean:message key='EditContract' bundle="PSN"/>"
				   class="CourseButton" />
		</td>
	</tr>
</c:if>

<!-- 確定建立 start -->	
<c:if test="${!empty newContract}">
	<tr>
		<td>
<!-- 列表 start -->
<c:import url="/pages/personnel/ContractManager/addNew.jsp"/>
<!-- 列表 end -->	
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		
		</td>
	</tr>
</c:if>	
<!-- 確定建立 end -->





</html:form>
</table>
<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/MyCalendarAD.jsp" %>