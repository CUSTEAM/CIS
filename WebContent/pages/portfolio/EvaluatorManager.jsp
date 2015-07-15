<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<script>
history.go(1);
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Portfolio/EvaluatorManager" enctype="multipart/form-data" method="post" onsubmit="init('執行中, 請稍後')">
<!-- 標題列 start-->	
	<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/folder_lock.gif" id="piftitle">
				</td>
				<td align="left">
				&nbsp;評審管理&nbsp;
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td>
		<%@ include file="EvaluatorManager/search.jsp"%>	
		</td>
	</tr>
	
	<tr>
		<td id="help" style="display:none;">
		<%@ include file="ActivitiesManager/help.jsp"%>		
		</td>
	</tr>
	
	<tr>
		<td class="fullColorTable" align="center">
		<INPUT type="submit" name="method" value="<bean:message key='Query'/>" class="gSubmit">
		<INPUT type="submit" name="method" value="<bean:message key='Create'/>" class="gGreen"
				onClick="return confirm('確定建立嗎？');">
		<input type="button" class="gCancle" value="返回" id="back" onclick="location='/CIS/Portfolio/ActivitiesDir.do';"/>
		<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
					onMouseOver="showHelpMessage('按下可開啟或關閉說明', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
		</td>
	</tr>
	
	

<c:if test="${!empty evaluators && evaluators!=null}">	
	<tr>
		<td>
		<%@ include file="EvaluatorManager/list.jsp"%>
		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center">
		<INPUT type="submit" name="method" value="<bean:message key='Delete'/>" class="gSubmit"
				onClick="return confirm('確定刪除嗎？');">
		</td>
	</tr>
</c:if>


	
	
</html:form>
</table>
<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>