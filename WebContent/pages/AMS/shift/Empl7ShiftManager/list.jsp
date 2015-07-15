<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="99%" class="hairLineTable">
	<tr>
		<td class="hairLineTdF" width="30"></td>
		<td class="hairLineTdF">排班日期</td>
		<td class="hairLineTdF">上班時間</td>
		<td class="hairLineTdF">下班時間</td>
	</tr>
	
<c:forEach items="${rightShift}" var="e">
	<tr>
		
		<td class="hairLineTdF" align="center"><img src="images/date.gif"/></td>
		<td class="hairLineTdF">${e.wdate} 星期${e.week}
		</td>
		
		<td class="hairLineTdF">
		<input type="text" name="set_in" value="${e.set_in}" size="6"/>
		</td>		
		<td class="hairLineTdF">
		<input type="text" name="set_out" value="${e.set_out}" size="6"/>		
		</td>				
		
	</tr>

</c:forEach>
	
	
	
</table>