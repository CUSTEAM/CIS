<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 必修 -->
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" style="font-size:20px;" nowrap>建立 ${DtimeReserveManagerForm.map.year}學年, ${DtimeReserveManagerForm.map.grade}年級學生的課程規劃</td>
				
			</tr>
		</table>
		<table class="hairLineTable" cellpadding="5" cellspacing="5" width="99%">
			<tr>
				
				<td class="hairLineTdF" style="font-size:18px;">學期</td>
				<td class="hairLineTdF" style="font-size:18px;">選別</td>	
				<td class="hairLineTdF" style="font-size:18px;" nowrap>課程名稱</td>
				<td class="hairLineTdF" style="font-size:18px;">學分</td>			
				<td class="hairLineTdF" style="font-size:18px;">時數</td>				
				<td class="hairLineTdF" style="font-size:18px;">上機</td>
				<td class="hairLineTdF" style="font-size:18px;">類型</td>
							
			</tr>
			<tr>
						
				<td class="hairLineTdF">
				<select name="terms" style="font-size:18px;">
					<option value="1">上學期</option>
					<option value="2">下學期</option>
				</select>
				</td>
				<td class="hairLineTdF" style="font-size:18px;">
				<select name="opt" style="font-size:18px;">
					<option value="1">學校必修</option>
					<option value="2">學院必修</option>
					<option value="3">學系必修</option>
					<option value="4">學校選修</option>
					<option value="5">學院選修</option>
					<option value="6">選修科目</option>
				</select>				
				</td>
				<td class="hairLineTdF" nowrap width="100%">				
				<input type="text" name="chi_name" id="chi_name" style="width:75%;font-size:18px;"
				onkeyup="getAny(this.value, 'chi_name', 'cscode', 'Csno', 'name')" />
				<input type="text" name="cscode" id="cscode" readonly style="width:20%;font-size:18px;"/>				
				<input type="hidden" name="Oid"/>
				</td>
				
				<td class="hairLineTdF"><input type="text" name="credit" size="1" style="font-size:18px;"/></td>
				<td class="hairLineTdF"><input type="text" name="thour" size="1" style="font-size:18px;"/></td>
			
				<td class="hairLineTdF">
				<select name="cyber" style="font-size:18px;">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
				</td>
				<td class="hairLineTdF">
				<select name="additionType" style="font-size:18px;">
					<option value="0">一般</option>
					<option value="1">實習</option>
					<option value="2">專題</option>
				</select>
				</td>
							
			</tr>
			
		</table>
			
		</td>
	</tr>
	
	<tr height="40">
		<td class="fullColorTable" align="center">
		<input type="submit" name="method" 
		value="<bean:message key='Create'/>" 
		id="Create" class="gGreen"
		onMouseOver="showHelpMessage('依照以上條件新增', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">
		
		<input type="submit" name="method" onClick="return confirmSubmit('確定建立嗎？');"
		value="<bean:message key='CopyLastYear' bundle="COU"/>" 
		id="copy" class="gCancel"
		onMouseOver="showHelpMessage('延用上一學年的規劃資料', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">
		
		</td>
	</tr>	
	
	<tr>
		<td>		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" style="font-size:20px;" nowrap>
				<a href="/CIS/Print/reserve/List4Grade.do?year=${DtimeReserveManagerForm.map.year}&dept=${campusSel}${schoolSel}${deptSel}">
				下載或開啟 ${DtimeReserveManagerForm.map.year}學年入學的學生歷年課程規劃</a>
				</td>
				
			</tr>
		</table>		
		<table class="hairLineTable" cellpadding="5" cellspacing="5" width="99%">			
			<tr>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>學期</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>選別</td>	
				<td class="hairLineTdF" style="font-size:18px;" nowrap>課程名稱</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>學分</td>								
				<td class="hairLineTdF" style="font-size:18px;" nowrap>時數</td>
								
				<td class="hairLineTdF" style="font-size:18px;" nowrap>上機</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>類型</td>
				
				
			</tr>	
			
			<c:forEach items="${opt1}" var="o">
			<tr>
						
				<td class="hairLineTdF">
				<input type="hidden" name="Oid" value="${o.Oid}"/>
				<select name="terms" style="font-size:18px;">
					<option value="">刪除</option>
					<option <c:if test="${o.term=='1'}">selected</c:if> value="1">上學期</option>
					<option <c:if test="${o.term=='2'}">selected</c:if> value="2">下學期</option>
				</select>
				</td>
				<td class="hairLineTdF" style="font-size:18px;">
				<select name="opt" style="font-size:18px;">
					<option value="">刪除</option>
					<option <c:if test="${o.opt=='1'}">selected</c:if> value="1">學校必修</option>
					<option <c:if test="${o.opt=='2'}">selected</c:if> value="2">學院必修</option>
					<option <c:if test="${o.opt=='3'}">selected</c:if> value="3">學系必修</option>
					<option <c:if test="${o.opt=='4'}">selected</c:if> value="4">學校選修</option>
					<option <c:if test="${o.opt=='5'}">selected</c:if> value="5">學院選修</option>
					<option <c:if test="${o.opt=='6'}">selected</c:if> value="6">選修科目</option>
				</select>				
				</td>
				<td class="hairLineTdF" width="100%">				
								
				<input type="text" name="chi_name" id="chi_name${o.Oid}" style="width:75%; font-size:18px;" 
				onkeyup="getAny(this.value, 'chi_name${o.Oid}', 'cscode${o.Oid}', 'Csno', 'name')" onclick="this.value='', courseNumber.value=''" value="${o.chi_name}"/>
				<input type="text" name="cscode" id="cscode${o.Oid}" readonly style="width:20%;font-size:18px;" value="${o.cscode}"/>
				
				</td>				
				<td class="hairLineTdF"><input type="text" name="credit" size="1" style="font-size:18px;" value="${o.credit}"/></td>
				<td class="hairLineTdF"><input type="text" name="thour" size="1" style="font-size:18px;" value="${o.thour}"/></td>			
				<td class="hairLineTdF">
				<select name="cyber" style="font-size:18px;">
					<option <c:if test="${o.cyber=='0'}">selected</c:if> value="0">否</option>
					<option <c:if test="${o.cyber=='1'}">selected</c:if> value="1">是</option>
				</select>
				</td>
				<td class="hairLineTdF">
				<select name="additionType" style="font-size:18px;">
					<option <c:if test="${o.additionType=='0'}">selected</c:if> value="0">一般</option>
					<option <c:if test="${o.additionType=='1'}">selected</c:if> value="1">實習</option>
					<option <c:if test="${o.additionType=='2'}">selected</c:if> value="2">專題</option>
				</select>
				</td>
							
			</tr>
			</c:forEach>
		</table>
				
		</td>
	</tr>	
	
<script>
function clearid(id){
	document.getElementById(id).value="";	
}	
</script>	
	
	
	
	<tr height="40">
		<td class="fullColorTable" align="center">
		<input type="submit" name="method" 
		value="<bean:message key='Save'/>" 
		id="save" class="gSubmit"
		onMouseOver="showHelpMessage('依照以上條件修改', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">
		
		</td>
	</tr>
</table>