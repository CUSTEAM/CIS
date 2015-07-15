<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/SysAdmin/SaveDtime" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/application_view_icons.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">儲存歷年課程</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		
		<table class="hairLIneTable">
			<tr>
				<td class="hairLIneTdF"><select disabled><option>${year}</option></select></td>
				<td class="hairLIneTdF"><select disabled><option>${term}</option></select></td>
				<td class="hairLIneTdF"><input type="submit" name="method" value="<bean:message key='OK'/>" class="green" /></td>
			</tr>
		</table>
		
		</td>
	</tr>
</html:form>
</table>