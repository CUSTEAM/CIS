<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

		
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" align="center">				
		<table>
			<tr>
				<td width="1"><img src="images/icon/rewind_blue.gif"/></td>
				<td nowrap><a href="../Calendar/Directory.do?cycle=lastmonth" nowrap>上個月</a></td>
			</tr>
		</table>
		</td>
	
		<td class="hairLineTdF" align="center">				
		<table>
			<tr>
				<td width="1"><img src="images/icon/reverse_blue.gif"/></td>
				<td nowrap><a href="../Calendar/Directory.do?cycle=lastweek">上週</a></td>
			</tr>
		</table>		
		</td>
			
		<td class="hairLineTdF" align="center">				
		<table>
			<tr>
				<td width="1"><img src="images/icon/calendar_2.gif"/></td>
				<td nowrap><b>${myYear}年 ${myMonth}月 ${myWeek}週</b></td>
			</tr>
		</table>		
		</td>
	
		<td class="hairLineTdF" align="center">		
		<table>
			<tr>
				<td nowrap nowrap><a href="../Calendar/Directory.do?cycle=nextweek">下週</a></td>
				<td width="1"><img src="images/icon/play_green.gif"/></td>
			</tr>
		</table>		
		</td>
	
		<td class="hairLineTdF" align="center">				
		<table>
			<tr>
				<td nowrap><a href="../Calendar/Directory.do?cycle=nextmonth" nowrap>下個月</a></td>
				<td width="1"><img src="images/icon/forward_green.gif"/></td>
			</tr>
		</table>				
		</td>
		
		<td class="hairLineTdF" align="center" >				
		<table width="100">
			<tr>
				<td width="1"><img src="images/icon/calendar_1.gif"/></td>
				<td width="105" nowrap><a href="../Calendar/PriManager.do" nowrap><b>顯示完整 ${myMonth}月</b></a></td>				
			</tr>
		</table>				
		</td>
		
	</tr>
</table>