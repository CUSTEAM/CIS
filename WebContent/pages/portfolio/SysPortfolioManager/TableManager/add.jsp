<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<br>
<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td width="10" align="left" nowrap>
  			<hr noshade class="myHr"/>
			</td>
			<td width="24" align="center" nowrap>
			<img src="images/icon/table.gif" />
			</td>
			<td nowrap style="cursor:pointer;">
			新增表格
			</td>
			<td width="100%" align="left">
  			<hr noshade class="myHr"/>
		</td>
	</tr>
</table>

<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td>
		
		
		<table class="hairLineTable" width="99%">
			<tr>			
				<td class="hairLineTdF" onClick="showObj('createTable');">
				<input type="hidden" name="Oid"/>
				<input type="text" name="tableName" style="width:100%;"/>				
				</td>
				
				<td class="hairLineTdF" width="60">
				表格順序
				</td>				
				<td class="hairLineTd" width="1">
				<input type="text" name="tableSeq" size="2"/>				
				</td>
			</tr>
		</table>	
		
		
		<table class="hairLineTable" cellpadding="0" cellspacing="0" id="createTable" style="display:none;" width="99%">
			<tr>
				<td class="hairLineTdF" colspan="8" align="right" width="100%">
				
				<INPUT type="submit" name="method" value="<bean:message key='Create'/>" class="CourseButton">
				
				</td>
			</tr>		
		</table>
		
		</td>
	</tr>
</table>