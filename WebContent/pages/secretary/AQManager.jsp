<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Secreatary/AQManager" method="post">	
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/folder_page.gif"></div>
		<div nowrap style="float:left;"><font class="gray_15">問卷管理</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">問卷期間自</td>
				<td class="hairLineTdF">		
				<input type="text" name="AQStart" value="${AQStart}" id="CoanswStart" style="font-size:18px;" readonly
				onclick="ds_sh(this), dateEdit();" autocomplete="off" style="ime-mode:disabled" autocomplete="off" size="6"/>		
				</td>
				<td class="hairLineTdF" width="30" align="center">至</td>
				<td class="hairLineTdF">		
				<input type="text" name="AQEnd" value="${AQEnd}" id="CoanswEnd" style="font-size:18px;" readonly
				onclick="ds_sh(this), dateEdit();" autocomplete="off" style="ime-mode:disabled" autocomplete="off" size="6"/>
				<input type="hidden" name="editDate" id="editDate" />
				</td>
				<td class="hairLineTdF"><INPUT type="submit" name="method" value="<bean:message key='SetQuestDate'/>" class="gSubmit"></td>			
			</tr>
		</table>
		<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
			<tr>
				<td id="ds_calclass"></td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td>
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">名稱</td>
				<td class="hairLineTdF"><select style="font-size:18px;"><option>行政滿意度問卷</option></select></td>
				<td class="hairLineTdF"><INPUT type="submit" name="method" value="<bean:message key='Print'/>" class="gGreen"/></td>
			</tr>
		</table>
		
		</td>
	</tr>
</html:form>
</table>
<%@ include file="/pages/include/MyCalendar.jsp" %>