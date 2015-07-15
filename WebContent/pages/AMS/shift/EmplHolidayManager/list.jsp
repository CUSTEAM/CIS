<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="99%" class="hairLineTable">
	<tr>
		<td class="hairLineTdF" align="center">
		<div style="float:left;"><img src="images/icon/mimetypes/icon_xls.gif" /></div>
		<div style="float:left; padding-top:2px;"><a href="AMS/shift/EmplHolidayManager/report.jsp">列印核對報表</a></div>
		</td>
	</tr>
</table>
<table width="99%" class="hairLineTable">
	<tr>
		<td class="hairLineTdF" align="center"><font size="+1">姓名</font></td>
		<td class="hairLineTdF" align="center"><font size="+1">年資</font></td>		
		<td class="hairLineTdF" align="center"><font size="+1">適用年度</font></td>
		<td class="hairLineTdF" align="center"><font size="+1">適用期間</font></td>
		<td class="hairLineTdF" align="center"><font size="+1">休假型態</font></td>
		<td class="hairLineTdF" align="center"><font size="+1">日數</font></td>				
	</tr>
	<tr>		
		<td class="hairLineTd" colspan="2" align="center">
		<input type="button" class="gGreen" value="向下填滿" 
		onClick="setAllColumn()" 
		
		id="setAllvTypes" 
		onMouseOver="showHelpMessage('填滿所有班別<br><font size=-2>(先選左邊欄位)</font>', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"/>		
		<input type="hidden" name="idnos"/>
		</td>
		
		<td class="hairLineTd" align="center">
		<input type="text" name="vYears" id="vYears" size="3"/>		
		</td>
		</td>
		
		<td class="hairLineTd" align="center">
		<input id="validFroms" name="validFroms" type="text" size="4" 
		onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
		<input type="text" name="validTos" size="4" 
		onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
		</td>		
		
		<td class="hairLineTd" align="center">
		
		<select name="vTypes" class="courseButton" id="vTypes">			
			<option value="1">年資休假</option>
			<option value="2">特別休假</option>
		</select>
		</td>
		
		<td class="hairLineTd" nowrap align="center">		
		<input type="text" name="dayss" id="dayss" size="2"/>
		</td>
	</tr>	
	
	<c:forEach items="${hempl}" var="e">
	<tr>
		<td class="hairLineTdF" style="padding-left:30px;" nowrap align="left">
		<font size="+1">${e.cname}</font><font size="-1"> (${e.sname})</font><input type="hidden" name="idnos" value="${e.idno}"/>
		
		</td>
		<td class="hairLineTdF" align="right"><font size="+1">${e.Adate}</font></td>
		
		<td class="hairLineTdF" nowrap align="center"><font size="+1">
		<input type="text" name="vYears" size="3" />
		</td>
		
		<td class="hairLineTdF" align="center">
		<input type="text" name="validFroms" size="4" id="validFroms${e.idno}"
		onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
		<input type="text" name="validTos" size="4" id="validTos${e.idno}"
		onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
		</td>
		
		<td class="hairLineTdF" align="center">		
		<select name="vTypes" id="vTypes" class="courseButton">			
			<option value="1">年資休假</option>
			<option value="2">特別休假</option>
		</select>		
		</td>
		
		<td class="hairLineTdF" align="center">
		<input type="text" name="dayss" size="2" value="${e.year}"/>
		</td>		
		
	</tr>

</c:forEach>
</table>