<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/AMS/Empl7ShiftManager" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon_calendar.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">每週個人排班管理</font></div>		
		</td>
	</tr>
	
	
	
<c:if test="${aEmpl==null&&rightShift==null}">
	<tr>
		<td>
		<%@ include file="Empl7ShiftManager/search.jsp"%>
		</td>
	</tr>
	<tr>
		<td id="help" style="display:none;">
		<%@ include file="Empl7ShiftManager/help.jsp"%>		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" 
					value="<bean:message key='Continue'/>" 
					id="Continue" class="gSubmit"
					onMouseOver="showHelpMessage('依照上方所設定的條件查詢人員, <br>準備開始建立班表', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">					
		
		<input type="button" class="gCancle" value="返回" id="back"
					onclick="location='/CIS/AMS/Directory.do';"
					onMouseOver="showHelpMessage('返回', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
				
		<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
					onMouseOver="showHelpMessage('按下可開啟或關閉說明', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
		
		</td>
	</tr>
</c:if>
	
	
	
<c:if test="${aEmpl!=null&&rightShift==null}">
	<tr>
		<td>
		<%@ include file="Empl7ShiftManager/edit.jsp"%>

		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" 
					value="<bean:message key='OK'/>" 
					id="Confirm" class="gSubmit"
					onMouseOver="showHelpMessage('依照上方所設定的條件建立班表', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
		
		</td>
	</tr>
</c:if>
	
	
<c:if test="${rightShift!=null}">
	<tr>
		<td>
		<%@ include file="Empl7ShiftManager/list.jsp"%>

		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" 
					value="<bean:message key='Complete'/>" 
					id="Complete" class="gSubmit"
					onMouseOver="showHelpMessage('完成並進行下一位', 'inline', this.id)" 
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