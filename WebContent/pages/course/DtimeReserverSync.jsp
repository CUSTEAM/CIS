<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<!-- 標題 start -->
<html:form action="/Course/DtimeReserverSync" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		

		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/plugin_link.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">課程規劃開課作業</font></div>
			

		</td>
	</tr>

	<tr>
		<td>
		
		
		<%@ include file="DtimeReserveSync/search.jsp"%>
		
		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" 
					value="<bean:message key='OK'/>" 
					id="Continue" class="gSubmit"
					onMouseOver="showHelpMessage('依照以上條件查詢', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
								
		
		
		</td>
	</tr>
	<c:if test="${!empty reserve}">
	<tr>
		<td>
		<%@ include file="DtimeReserveSync/list.jsp"%>
		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center" height="30">
		
		
								
		
		
		</td>
	</tr>
	</c:if>	
</html:form>
</table>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxUniMod.jsp" %>