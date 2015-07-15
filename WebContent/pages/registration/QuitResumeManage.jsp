<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Registration/QuitResumeManage" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/door_out.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">休學復學記錄管理</font></div>		
		</td>
	</tr>
	<tr>
		<td>		
		<%@ include file="QuitResumeManage/search.jsp"%>		
		</td>
	</tr>
	<tr>
		<td id="help" style="display:none;">
		<%@ include file="QuitResumeManage/help.jsp"%>		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" 
					value="<bean:message key='Query'/>" 
					id="Query" class="gSubmit"
					onMouseOver="showHelpMessage('以上條件查詢', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
					
		<input type="submit" name="method" 
					value="<bean:message key='Create'/>" 
					id="Create" class="gGreen"
					onMouseOver="showHelpMessage('以上條件新增', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">			
					
		
		<input type="button" class="gCancle" value="返回" id="back"
					onclick="location='/CIS/Registration/StudentDirectory.do';"
					onMouseOver="showHelpMessage('返回上層功能列表', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
				
		<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
					onMouseOver="showHelpMessage('按下可開啟或關閉說明', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
		
		</td>
	</tr>
	
<c:if test="${!empty students}">
	<tr>
		<td>		
		<%@ include file="QuitResumeManage/list.jsp"%>		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" 
					value="<bean:message key='Modify'/>" 
					id="Query" class="gSubmit"
					onMouseOver="showHelpMessage('將以上資料儲存', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
		</td>
	</tr>
</c:if>
	
	
	
	


</html:form>
</html>
</table>


<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetStmdOrGstmd.jsp" %>
<%@ include file="/pages/include/MyCalendar.jsp" %>
<script>
	function clearQuery(){
		document.getElementById("studentNo").value="";
		document.getElementById("studentName").value="";
	}							
</script>