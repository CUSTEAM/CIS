<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table cellspacing="0" cellpadding="0" width="100%">
<html:form action="/Regstration/Recruit/Config/MailListManager" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題start -->
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/email_tool.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">電子郵件名單管理</font></div>		
		</td>
	</tr>
	<tr>
		<td>		
		<%@ include file="MailListManager/search.jsp"%>		
		</td>
	</tr>
	
	<tr>
		<td id="help" style="display:none;">		
		<%@ include file="MailListManager/help.jsp"%>
		</td>
	</tr>
	<tr class="fullColorTr">
		<td align="center">
		<input type="submit" name="method" 
					value="<bean:message key='Query'/>" 
					id="Save" class="gSubmit"
					onMouseOver="showHelpMessage('查詢資料', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
					
		<input type="submit" name="method" 
					value="<bean:message key='Create'/>" 
					id="Create" class="gGreen"
					onMouseOver="showHelpMessage('建立資料', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">		
					
		<input type="button" class="gCancle" value="返回" id="back" onClick="history.go(-1)" 
					onMouseOver="showHelpMessage('返回功能列表', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
					
		<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
					onMouseOver="showHelpMessage('顯示說明', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
		
		</td>
	</tr>
	
	
<c:if test="${!empty mails}">
	<tr>
		<td>
		<%@ include file="MailListManager/list.jsp"%>		
		</td>
	</tr>
	<tr height="30">
		<td height="30" class="fullColorTable">
		</td>
	</tr>
</c:if>
	
	
	
	
</html:form>
</table>
<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>