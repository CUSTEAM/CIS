<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<table class="hairlineTableIndex" cellspacing="0" cellpadding="0">
	<tr>
		<td class="fullColorTablePad">		
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td align="center"><img src="images/user_comment.gif"/></td>
				<td onClick="showObj('language')" style="cursor:pointer;" width="99%" align="left">&nbsp;
				<bean:message key="Login.LanguageSel" bundle="IND"/>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="center" >
		
		<table align="left" class="hairlineTd" id="language" width="100%" style="display:none;">
			<tr>
				<td width="1"><bean:message key="Login.LanguageFlag" bundle="IND"/></td>
				<td width="1"><img src="images/12-em-right.png"></td>
				
				<td width="100%">
				<c:import url="/pages/include/LanguageSelection.jsp"/>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
</table>