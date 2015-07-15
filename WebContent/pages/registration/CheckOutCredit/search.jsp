<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table>
	<tr>		
		<td>
		
		<table class="hairlineTable" align="left">
			<tr>
				<td align="center" class="hairlineTdF">適用學制</td>
				<td class="hairlineTd">
				<input type="hidden" name="Oid" size="4" value="" id="Oid${r.Oid}" />
				<div name="selectBox" type="border" id="select_SchoolNo">
				<select name="SchoolNo" id="SchoolNo" onFocus="chInput(this.id)">
					<option value="">全部學制</option>
					<c:forEach items="${AllSchools}" var="as">
					<option <c:if test="${CheckeOutCreditForm.map.SchoolNo[0]==as.idno}">selected</c:if> value="${as.idno}">${as.name}</option>				
					</c:forEach>
				</select>
				</div>		
				</td>
				<td class="hairlineTdF" width="30" align="center" id="help1" style="cursor:pointer;"
				onMouseOver="showHelpMessage('適用哪個學制<br>請調整下拉選單', 'inline', this.id)" 
			   	onMouseOut="showHelpMessage('', 'none', this.id)">
				<img src="images/icon/icon_info.gif"/>				
				</td>
			</tr>
		</table>		
		
		</td>
		<td>
		
		<table class="hairlineTable" align="left">
			<tr>
				<td align="center" class="hairlineTdF">適用科系</td>
				<td class="hairlineTd">
				<div name="selectBox" type="border" id="select_DeptNo">
				<select name="DeptNo" id="DeptNo" onFocus="chInput(this.id)">
					<option value="">全部科系</option>
					<c:forEach items="${AllDepts}" var="ad">
					<option <c:if test="${CheckeOutCreditForm.map.DeptNo[0]==ad.idno}">selected</c:if> value="${ad.idno}">${ad.name}</option>				
					</c:forEach>
				</select>
				</div>		
				</td>
				<td class="hairlineTdF" width="30" align="center" id="help2" style="cursor:pointer;"
				onMouseOver="showHelpMessage('適用哪個科系<br>請調整下拉選單', 'inline', this.id)" 
			   	onMouseOut="showHelpMessage('', 'none', this.id)">
				<img src="images/icon/icon_info.gif"/>				
				</td>
			</tr>
		</table>		
		
		</td>
	</tr>
<table>
	
<table>	
	<tr>
		<td>
		
		<table class="hairlineTable" align="left">
			<tr>
				<td align="center" class="hairlineTdF">範圍從</td>
				<td class="hairlineTd">
				<input type="text" size="3" name="start_year" id="start_year" value="${CheckeOutCreditForm.map.start_year[0]}" onClick="this.value=''" onFocus="chInput(this.id)">						
				
				</td>
			
				<td align="center" class="hairlineTdF" width="30">至</td>
				<td class="hairlineTd">
				<input type="text" size="3" name="end_year" id="end_year" value="${CheckeOutCreditForm.map.end_year[0]}" onClick="this.value=''" onFocus="chInput(this.id)">
				
				</td>
				<td class="hairlineTdF">
				學年入學
				
				</td>
				<td class="hairlineTdF" width="30" align="center" id="help3" style="cursor:pointer;"
				onMouseOver="showHelpMessage('前欄空白表示追溯至最早的入學學生, <br>後欄空白表示未來入學的學生仍然適用<br>學年比對標準為「學生入學年」'+
				'<br><br>要產生報表時建議將範圍縮小為1個入學年, <br>例如要比對94年入學的學生, 請輸入94至94, <br>要找多個年度入學的學生, 也可以擴大範圍'
				, 'inline', this.id)" 
			   	onMouseOut="showHelpMessage('', 'none', this.id)">
				<img src="images/icon/icon_info_exclamation.gif"/>				
				</td>
				
				
			</tr>
		</table>
		
		</td>
	</tr>
</table>

<table>
	<tr>
		
		<td>
		
		<table class="hairlineTable" align="left">
			<tr>
				<td align="center" class="hairlineTdF">必修學分</td>
				<td class="hairlineTd">
				<input type="text" id="opt1" size="3" name="opt1" value="${CheckeOutCreditForm.map.opt1[0]}" onClick="this.value=''" onFocus="chInput(this.id)">						
				</td>
			
				<td align="center" class="hairlineTdF">選修學分</td>
				<td class="hairlineTd">
				<input type="text" id="opt2" size="3" name="opt2" value="${CheckeOutCreditForm.map.opt2[0]}" onClick="this.value=''" onFocus="chInput(this.id)">						
				</td>
			
				<td align="center" class="hairlineTdF">通識學分</td>
				<td class="hairlineTd">
				<input type="text" size="3" id="opt3" name="opt3" value="${CheckeOutCreditForm.map.opt3[0]}" onClick="this.value=''" onFocus="chInput(this.id)">						
				</td>
				</td>
				<td class="hairlineTdF" width="30" align="center" id="help4" style="cursor:pointer;"
				onMouseOver="showHelpMessage('輸入的學分數將會成為系統比對標準', 'inline', this.id)" 
			   	onMouseOut="showHelpMessage('', 'none', this.id)">
				<img src="images/icon/icon_info.gif"/>				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
</table>