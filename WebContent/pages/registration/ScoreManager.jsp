<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script language="javascript" src="include/util.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Registration/ScoreManager" method="post" onsubmit="init('系統處理中...')">
<!-- 標題列 start-->	
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/folder_page_zero.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">成績管理</font></div>		
		</td>
	</tr>
	
	<tr>
		<td>
		<%@ include file="ScoreManager/search.jsp"%>
		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center">
		<input type="submit" name="method" value="<bean:message key='Add'/>" 
		id="Add" onMouseOver="showHelpMessage('建立成績', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)"
		class="gGreen">
		</td>
	</tr>
	
	<c:if test="${!empty selds}">
	<tr>
		<td>
		<%@ include file="ScoreManager/seld.jsp"%>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
		
		</td>
	</tr>
	</c:if>
	
	<c:if test="${!empty scorehist}">
	<tr>
		<td>
		<%@ include file="ScoreManager/scorehist.jsp"%>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
		
		</td>
	</tr>
	</c:if>
	
	
</html:form>
</table>

<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetStmdOrGstmd.jsp" %>