<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<script language="javascript" src="/CIS/pages/include/decorateJs.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Score/ScoreNotUpload" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/application_view_icons.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">學期成績查核管理</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		<%@ include file="ScoreNotUpload/search.jsp" %>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		<input type="submit" name="method" class="gSubmit" value="<bean:message key='Query'/>" id="Query"
		onMouseOver="showHelpMessage('查詢', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)"/>
		
		<c:if test="${!empty detail}">
		<input type="button" class="gCancel" value="返回" 
		onClick="history.back();">
		</c:if>
		
		</td>
	</tr>
	
	<c:if test="${!empty cslist}">
	<tr>
		<td>
		<%@ include file="ScoreNotUpload/list.jsp" %>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center"></td>
	</tr>
	</c:if>
	<c:if test="${!empty detail}">
	<tr>
		<td>
		<%@ include file="ScoreNotUpload/detail.jsp" %>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		<input type="submit" name="method" class="gSubmit" value="<bean:message key='Save'/>" id="Save"
		onMouseOver="showHelpMessage('儲存', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)"/>
		
		<input type="button" class="gCancel" value="列印" 
		onClick="preview();">		
		
		<input type="button" class="gGreen" value="返回" 
		onClick="history.back();">
		</td>
	</tr>
	</c:if>
</html:form>
</table>
<script language=javascript>
function preview() { 
	window.clipboardData.setData("Text",document.getElementById('table1').outerHTML);
	
	try{
		var ExApp = new ActiveXObject("Excel.Application")
		var ExWBk = ExApp.workbooks.add()
		var ExWSh = ExWBk.worksheets(1)
		ExApp.DisplayAlerts = false
		ExApp.visible = true
	}  
	catch(e){
		alert("請打開Excel貼上\nCtrl+v");
		return false
		}
	try{
		ExWBk.worksheets(1).Paste;	
	}catch(e){
		alert("請打開Excel貼上\nCtrl+v");
	}
 	
}
</script>
