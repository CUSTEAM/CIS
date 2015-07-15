<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/AMS/EmplHolidayManager" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/date.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">休假日數管理</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		<%@ include file="EmplHolidayManager/search.jsp"%>		
		</td>
	</tr>
	<tr>
		<td id="help" style="display:none;">
		<%@ include file="EmplHolidayManager/help.jsp"%>		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" 
					value="<bean:message key='AddHoliday'/>" 
					id="AddHoliday" class="gSubmit"
					onMouseOver="showHelpMessage('依照上方所設定的條件查詢人員, <br>準備開始建立休假日數', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
					
		<input type="submit" name="method" 
					value="<bean:message key='ModifyHoilday'/>" 
					id="ModifyHoilday" class="gGreen"
					onMouseOver="showHelpMessage('依照上方所設定的條件查詢人員, <br>準備修改或刪除已建立的休假日數', 'inline', this.id)" 
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
<c:if test="${!empty hempl}">
	<tr>
		<td>		
		<%@ include file="EmplHolidayManager/list.jsp"%>		
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

<c:if test="${!empty mempl}">
	<tr>
		<td>		
		<%@ include file="EmplHolidayManager/mlist.jsp"%>		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method"
					value="<bean:message key='Modify'/>" 
					id="Query" class="gGreen"
					onMouseOver="showHelpMessage('查詢人員並準備建立假期', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
		</td>
	</tr>
</c:if>
</html:form>
</table>

<script>
function setAllColumn(){
	setAll('vTypes', document.getElementById('vTypes').value);
	setAll('vYears', document.getElementById('vYears').value);
	setAll('validFroms', document.getElementById('validFroms').value);
	setAll('validTos', document.getElementById('validTos').value);
	
	if(document.getElementById("vTypes").value=="2"){
		setAll('dayss', document.getElementById('dayss').value);
	}
	
}

function setAll(name, value){
	//alert(value);
	var a=document.getElementsByName(name);
	for(i=0; i<a.length; i++){
		a[i].value=value;
	}
}
</script>


<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>
<%@ include file="/pages/include/MyCalendar.jsp" %>