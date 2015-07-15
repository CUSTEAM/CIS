<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable"  width="99%">
	<tr>
		<td class="hairLineTdF" width="30" align="center">
				<img src="images/icon_clock.gif"  id="helpIdName"
				onMouseOver="showHelpMessage('?', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)"/>
 		</td>
		<td class="hairLineTdF">		
		${Empl7ShiftManagerForm.map.name}同仁
		${Empl7ShiftManagerForm.map.startDate} 至 ${Empl7ShiftManagerForm.map.endDate}的排班時間將依下表建立		
		
		
		<input name="id" type="hidden" size="4" value="${Empl7ShiftManagerForm.map.id}"/>
		<input name="name" type="hidden" size="4" value="${Empl7ShiftManagerForm.map.name}"/>
		<input name="startDate" type="hidden" size="4" value="${Empl7ShiftManagerForm.map.startDate}"/>
		<input name="endDate" type="hidden" size="4" value="${Empl7ShiftManagerForm.map.endDate}"/>
		
		
		
		
		</td>
	</tr>
</table>




<table class="hairLineTable"  width="99%">
	<tr>
		<td class="hairLineTdF" colspan="2">星期一</td>
		<td class="hairLineTdF" colspan="2">星期二</td>
		<td class="hairLineTdF" colspan="2">星期三</td>
		<td class="hairLineTdF" colspan="2">星期四</td>
		<td class="hairLineTdF" colspan="2">星期五</td>
		<td class="hairLineTdF" colspan="2">星期六</td>
		<td class="hairLineTdF" colspan="2">星期日</td>
	</tr>
	<tr>
		<%for(int i=0; i<7; i++){ %>
		<td class="hairLineTdF">應上</td>
		<td class="hairLineTdF">應下</td>
		<%} %>
	</tr>
	<tr>
		
		<td class="hairLineTdF"><input type="text" name="in1" size="5" class="smallInput"/></td>
		<td class="hairLineTdF"><input type="text" name="out1" size="5" class="smallInput"/></td>
		
		<td class="hairLineTdF"><input type="text" name="in2" size="5" class="smallInput"/></td>
		<td class="hairLineTdF"><input type="text" name="out2" size="5" class="smallInput"/></td>
		
		<td class="hairLineTdF"><input type="text" name="in3" size="5" class="smallInput"/></td>
		<td class="hairLineTdF"><input type="text" name="out3" size="5" class="smallInput"/></td>
		
		<td class="hairLineTdF"><input type="text" name="in4" size="5" class="smallInput"/></td>
		<td class="hairLineTdF"><input type="text" name="out4" size="5" class="smallInput"/></td>
		
		<td class="hairLineTdF"><input type="text" name="in5" size="5" class="smallInput"/></td>
		<td class="hairLineTdF"><input type="text" name="out5" size="5" class="smallInput"/></td>
		
		<td class="hairLineTdF"><input type="text" name="in6" size="5" class="smallInput"/></td>
		<td class="hairLineTdF"><input type="text" name="out6" size="5" class="smallInput"/></td>
		
		<td class="hairLineTdF"><input type="text" name="in7" size="5" class="smallInput"/></td>
		<td class="hairLineTdF"><input type="text" name="out7" size="5" class="smallInput"/></td>
		
	</tr>		
	
</table>