<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="99%" class="hairLineTable">
	<tr>		
		<td class="hairLineTd" colspan="2" align="center">
		<input type="button" class="gGreen" value="向下填滿" 
		onClick="setAllColumn()" 
		
		id="setAllvTypes" 
		onMouseOver="showHelpMessage('填滿所有班別<br><font size=-2>(先選左邊欄位)</font>', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"/>		
		<input type="hidden" name="idnos"/>
		<input type="hidden" name="Oid"/>
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
			<option value=""></option>
			<option value="">取消休假</option>		
			<option value="1">年資休假</option>
			<option value="2">特別休假</option>
		</select>
		</td>
		
		<td class="hairLineTd" nowrap align="center">		
		<input type="text" name="dayss" id="dayss" size="2"/>
		</td>
	</tr>
<c:forEach items="${mempl}" var="e">
	<tr>
		<td class="hairLineTdF" nowrap align="center">
		<font size="+1">${e.cname}</font>
		<input type="hidden" name="idnos" value="${e.idno}"/>
		<input type="hidden" name="Oid" value="${e.Oid}"/>
		</td>
		<td class="hairLineTdF" align="right"><font size="+1">${e.Adate}</font></td>
		
		<td class="hairLineTdF" nowrap align="center"><font size="+1">
		<input type="text" name="vYears" size="3" value="${e.vYear}"/>
		</td>
		
		<td class="hairLineTdF" align="center">
		<input type="text" name="validFroms" size="4" id="validFroms${e.idno}" value="${e.validFrom}"
		onclick="ds_sh(this);" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
		<input type="text" name="validTos" size="4" id="validTos${e.idno}" value="${e.validTo}"
		onclick="ds_sh(this);" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
		</td>
		
		<td class="hairLineTdF" align="center">		
		<select name="vTypes" class="courseButton">
			<option value="">取消休假</option>		
			<option <c:if test="${e.vType=='1'}">selected</c:if> value="1">年資休假</option>
			<option <c:if test="${e.vType=='2'}">selected</c:if> value="2">特別休假</option>
		</select>		
		</td>
		
		<td class="hairLineTdF" align="center">
		<input type="text" name="dayss" size="2" value="${e.days}"/>
		</td>		
		
	</tr>

</c:forEach>
</table>