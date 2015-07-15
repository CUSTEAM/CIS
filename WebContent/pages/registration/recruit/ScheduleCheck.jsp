<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table cellspacing="0" cellpadding="0" width="100%">
<html:form action="Regstration/Recruit/ScheduleCheck" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題start -->
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/accept.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">招生工作管考</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		<%@ include file="ScheduleCheck/search.jsp"%>		
		</td>
	</tr>
	<tr>
		<td id="help" style="display:none;">		
		<%@ include file="ScheduleCheck/help.jsp"%>
		</td>
	</tr>
	<tr class="fullColorTr">
		<td align="center">
		<input type="submit" name="method" 
					value="<bean:message key='Query'/>" 
					id="query" class="gSubmit"
					onMouseOver="showHelpMessage('查詢資料', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
					
		<input type="button" class="gCancle" value="返回" id="back" onClick="history.go(-1)" 
					onMouseOver="showHelpMessage('返回功能列表', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
					
		<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
					onMouseOver="showHelpMessage('顯示說明', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
		
		</td>
	</tr>
	
<c:if test="${!empty scheduleChecks}">
	<tr>
		<td>
		
		<%@ include file="ScheduleCheck/list.jsp"%>
		</td>
	</tr>
	<tr class="fullColorTr" height="30">
		<td align="center"></td>
	</tr>



</c:if>
	
	
	
	
</html:form>
</table>
<%@ include file="/pages/include/MyCalendarAD.jsp" %>