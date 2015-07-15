<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<!-- 標題 start -->
<html:form action="/AMS/ArtificialInput" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/user_tick.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">人工補登作業</font></div>		
		</td>
	</tr>
	<tr>
		<td>		
		<%@ include file="include/ArtificialInput/search.jsp"%>		
		</td>
	</tr>
	<tr>
		<td id="help" style="display:none;">
		<%@ include file="include/ArtificialInput/help.jsp"%>		
		</td>
	</tr>	
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" 
					value="<bean:message key='Query'/>" 
					id="Query" class="gSubmit"
					onMouseOver="showHelpMessage('查詢未刷卡資料', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
		
		<input type="button" class="gCancle" value="返回" id="gradHelp"
					onclick="location='/CIS/AMS/Directory.do';"
					onMouseOver="showHelpMessage('返回差勤系統頁面', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
				
		<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
					onMouseOver="showHelpMessage('按下可開啟或關閉說明', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
		</td>
	</tr>
<c:if test="${!empty myShift}">
	<tr>
		<td>		
		<%@ include file="include/ArtificialInput/list.jsp"%>		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" 
					value="<bean:message key='Save'/>" 
					id="Save" class="gSubmit"
					onMouseOver="showHelpMessage('儲存以上設定', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
		</td>
	</tr>
</c:if>
	
	
</html:form>
</table>

<script>
function setAllColumn(){	
	setAll('set_in', document.getElementById('set_in').value);
	setAll('set_out', document.getElementById('set_out').value);	
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