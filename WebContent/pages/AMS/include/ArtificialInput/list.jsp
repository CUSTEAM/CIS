<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="99%" class="hairLineTable">
	<tr>
		
		<td class="hairLineTdF"></td>
		<td class="hairLineTdF"></td>
		<td class="hairLineTdF">上班時間</td>
		<td class="hairLineTdF">下班時間</td>
	</tr>

	<tr>
		
		<td class="hairLineTd" align="center" >
		<input type="button" value="向下填滿" 
		onClick="setAllColumn();";
		class="gGreen" />
		<input type="hidden" name="wdate" />
		<input type="hidden" name="idno" value="${EmplShiftManagerForm.map.sidno}" />
		</td>
		<td class="hairLineTdF" align="center">
		  <select name="saveType" id="saveType">			          
		    <option value="p">不列紀錄</option>	
		    <option value="ai">列入紀錄</option>					  
		  </select>
		</td>
		<td class="hairLineTd"><input type="text" name="set_in" id="set_in" size="6"/></td>
		<td class="hairLineTd"><input type="text" name="set_out" id="set_out" size="6"/></td>
	</tr>

	<c:forEach items="${myShift}" var="e">
	<tr>
		
		
		<td class="hairLineTdF">
		  ${e.cdate} 星期${e.week} 應上${e.set_in} 應下${e.set_out}		
		  <input type="hidden" name="idno" value="${e.idno}" />
		  <input type="hidden" name="wdate" value="${e.wdate}" />
		</td>
		<td class="hairLineTdF" align="center">
		  <select name="saveType" id="saveType">			          
		    <option value="p">不列紀錄</option>	
		    <option value="ai">列入紀錄</option>					  
		  </select>
		</td>		
		<td class="hairLineTdF">
		<input type="text" name="set_in" value="${e.real_in }" size="6"/>
		</td>		
		<td class="hairLineTdF">
		<input type="text" name="set_out" value="${e.real_out }" size="6"/>		
		</td>				
		
	</tr>

</c:forEach>
</table>