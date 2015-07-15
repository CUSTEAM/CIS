<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/AMS/CheckOvertime" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/16-clock.png"/></div>
		<div nowrap style="float:left;"><font class="gray_15">超時工作查核</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		<%@ include file="CheckOvertime/search.jsp"%>		
		</td>
	</tr>
	<tr>
		<td id="help" style="display:none;">
		<%@ include file="CheckOvertime/help.jsp"%>		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" 
					value="<bean:message key='Search'/>" 
					id="Search" class="gSubmit"
					onMouseOver="showHelpMessage('依照上方所設定的條件查詢人員, <br>準備開始建立休假日數', 'inline', this.id)" 
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
<c:if test="${!empty result}">
	<tr>
		<td>		
		<%@ include file="CheckOvertime/list.jsp"%>		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" 
					value="<bean:message key='Create'/>" 
					id="Query" class="gSubmit"
					onMouseOver="showHelpMessage('查詢人員並準備建立假期', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
		</td>
	</tr>
</c:if>
</html:form>
</table>



<%@ include file="/pages/include/MyCalendar.jsp" %>