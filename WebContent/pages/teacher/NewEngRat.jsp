<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/NewEngRat" method="post" onsubmit="init('儲存中, 請稍後')">	
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/font.gif"></div>
		<div nowrap style="float:left;"><font class="gray_15"><bean:message key="EngRat.title"/></font></div>		
		</td>
	</tr>
	
	
	
	<tr>
		<td>
			
		<%@ include file="NewEngRat/help.jsp" %>
		
		<%@ include file="NewEngRat/ListClass.jsp" %>
		
		
		</td>
	</tr>
	<tr height="30">
	
		<td class="fullColorTable" align="center">
		
		
		
		</td>
	</tr>
	
</html:form>
</table>
