<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<script language="javascript" src="/CIS/pages/include/decorateJs.js"></script>
<!-- %@ include file="/pages/include/decorate.js" %-->
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Student/AbseManager" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/folder_lock.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">預備</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		
		<table><tr><td>
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF"><img src="images/icon/reverse_blue.gif"/></td>
				<td class="hairLineTdF"><a href="/CIS/Student/AbseManager.do?weekday=${weekday[0]}">上週</a></td>
			
				<td class="hairLineTdF" style="padding-left:20px; padding-right:20px;"><a href="/CIS/Student/AbseManager.do">最近一週</a></td>
						
				<td class="hairLineTdF"><a href="/CIS/Student/AbseManager.do?weekday=${weekday[8]}">下週</a></td>
				<td class="hairLineTdF"><img src="images/icon/play_blue.gif"/></td>
			</tr>
		</table>
		</td><td>		
		<table class="hairLineTable">
			<tr>
				
			
				
				<td class="hairLineTdF">勾選節次後點選建立假單(可跨週)</td>
				<td class="hairLineTdF">
				<input type="submit"
					   name="method" id="CreatAbs"
					   value="<bean:message
					   key='CreatAbs'/>"
					   class="gGreen" 
					   onMouseOver="showHelpMessage('可跨週多選', 'inline', this.id)" 
					   onMouseOut="showHelpMessage('', 'none', this.id)" />
				</td>
			</tr>
		</table>
		
		</td><td></td></tr></table>
		</td>
	</tr>
	<tr>
		<td>
		<%@ include file="AbseManager/table.jsp"%>
		</td>
	</tr>
	
	
	
	
</html:form>
</table>