<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<!-- 標題 start -->
<html:form action="/AMS/EmplStaticShiftManager" method="post" onsubmit="init('人員過多處理時間較久, 請耐心等候')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/bell.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">人員常態排班管理</font></div>		
		</td>
	</tr>
	<tr>
		<td>		
		<%@ include file="EmplStaticShiftManager/search.jsp"%>		
		</td>
	</tr>
	<tr>
		<td id="help" style="display:none;">
		<%@ include file="EmplStaticShiftManager/help.jsp"%>		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" 
					value="<bean:message key='Query'/>" 
					id="Query" class="gSubmit"
					onMouseOver="showHelpMessage('查詢現有班別或人員', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
		
		<input type="button" class="gCancle" value="返回" id="back"
					onclick="location='/CIS/AMS/Directory.do';"
					onMouseOver="showHelpMessage('返回排班表與人員管理', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
				
		<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
					onMouseOver="showHelpMessage('按下可開啟或關閉說明', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
		
		</td>
	</tr>
	
	
	<c:if test="${!empty empls}">
	<tr>
		<td>	
		<%@ include file="EmplStaticShiftManager/list.jsp"%>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" 
					value="<bean:message key='Save'/>" 
					id="Save" class="gSubmit"
					onMouseOver="showHelpMessage('將以上資料儲存', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
		
		</td>
	</tr>
</c:if>
	
	
	
	
	
	
	
	
</html:form>
</table>

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>
<%@ include file="/pages/include/MyCalendar.jsp" %>