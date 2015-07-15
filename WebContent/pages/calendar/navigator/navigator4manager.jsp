<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

		
<table width="99%" class="hairLineTable" alngn="center">
	<tr>
		<td class="hairLineTdF" align="center">				
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td width="1"><img src="images/icon/rewind_blue.gif"/></td>
				<td width="3"></td>
				<td><a href="../Calendar/PriManager.do?cycle=lastmonth">上個月</a></td>
			</tr>
		</table>
		</td>
	

		<td class="hairLineTdF" align="center" nowrap>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td width="1"><img src="images/icon/calendar_1.gif"/></td>
				<td width="3"></td>
				<td><b>${myYear}年 ${myMonth}月</b></td>
			</tr>
		</table>		
		</td>
	
		<td class="hairLineTdF" align="center">				
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td><a href="../Calendar/PriManager.do?cycle=nextmonth">下個月</a></td>
				<td width="3"></td>
				<td width="1"><img src="images/icon/forward_green.gif"/></td>
			</tr>
		</table>				
		</td>
		
		<td class="hairLineTdF" align="center">
		<table>
			<tr>
				<td width="1"><img src="images/icon/calendar_2.gif"/></td>
				<td><a href="../Calendar/Directory.do" nowrap><b>顯示 ${myMonth}月 ${myWeek}週</b></a></td>				
			</tr>
		</table>
		</td>
	</tr>
</table>