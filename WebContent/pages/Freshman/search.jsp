<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable" width="300">
	<tr>
		<td class="hairLineTdF" align="center"><b>第1步: 驗證&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></td>		
	</tr>
</table>
<table class="hairLineTable">
	<tr>
		<td class="hairLineTdF" nowrap width="130" align="center">身分證字號</td>
		<td class="hairLineTdF" width="1"><input name="field" type="text" value="${std.idno}"></td>
		<td class="hairLineTdF" nowrap width="130" align="center">出生日期</td>
		<td class="hairLineTdF" width="1"><input name="field" type="password"></td>
		<td class="hairLineTdF">
		<input type="submit" name="method" 
		value="<bean:message key='Verification'/>" 
		id="Verification" class="gCancel">
		</td>
	</tr>
</table>
<table class="hairLineTable" width="698">
	<tr>
		<td class="hairLineTdF" align="left">&nbsp;&nbsp;&nbsp;&nbsp;身分證英文字母不分大小寫，生日假設為民國78年4月1日則輸入780401</td>		
	</tr>
</table>
<p></p>