<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="99%" class="hairLineTable">
	<tr>
		<td class="hairLineTdF" width="30"></td>
		<td class="hairLineTdF">排班日期</td>
		<td class="hairLineTdF">上班時間</td>
		<td class="hairLineTdF">下班時間</td>
	</tr>
	<tr>
		
		<td class="hairLineTd" align="center" colspan="2">
		<input type="button" value="向下填滿" 
		onClick="setAllColumn();";
		class="gGreen" />
		<input type="hidden" name="wdate" />
		<input type="hidden" name="idno" value="${EmplShiftManagerForm.map.sidno}" />
		</td>
		
		<td class="hairLineTd"><input type="text" name="set_in" id="set_in" size="6"/></td>
		<td class="hairLineTd"><input type="text" name="set_out" id="set_out" size="6"/></td>
	</tr>
	<c:forEach items="${myShift}" var="e">
	<tr>
		
		<td class="hairLineTdF" align="center"><img src="images/date.gif"/></td>
		<td class="hairLineTdF">${e.cdate} 星期${e.week}
		<input type="hidden" name="wdate" value="${e.wdate}" />
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