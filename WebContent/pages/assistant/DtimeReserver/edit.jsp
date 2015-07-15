<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>		
		<td>		
		
		
		<table class="hairLineTable" cellpadding="5" cellspacing="5" width="99%">			
			<tr>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>學期</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>選別</td>	
				<td class="hairLineTdF" style="font-size:18px;" nowrap>課程名稱</td>									
				<td class="hairLineTdF" style="font-size:18px;" nowrap>教師</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>學分</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>時數</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>人數</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>上機</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>創新</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>證照</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>類型</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>確認</td>
				
			</tr>	
			
			
			<tr>
				<td class="hairLineTdF">
				<select name="term" style="font-size:18px;">
					<option <c:if test="${aDtimeReserve.term=='1'}">selected</c:if> value="1">上學期</option>
					<option <c:if test="${aDtimeReserve.term=='2'}">selected</c:if> value="2">下學期</option>
				</select>
				
				</td>
				<td class="hairLineTdF">
				<select name="opt" style="font-size:18px;">
					<option <c:if test="${aDtimeReserve.opt=='1'}">selected</c:if> value="1">必修</option>
					<option <c:if test="${aDtimeReserve.opt=='2'}">selected</c:if> value="2">選修</option>
				</select>
				
				</td>	
				<td class="hairLineTdF" width="100%">				
				<input type="hidden" name="Oid" value="${aDtimeReserve.Oid }"/>
				<input type="text" autocomplete="off" style="width:100%; font-size:18px;" value="${aDtimeReserve.chi_name}"
				name="chi_name" id="chi_name"
				onkeyup="getAny(this.value, 'chi_name', 'cscode', 'CsnoLast2', '${DtimeReserveManagerForm.map.classLess}')"/>
				
				<input type="hidden" name="cscode" id="cscode${aDtimeReserve.Oid}" value="${aDtimeReserve.cscode}"/>				
				</td>
				
				<td class="hairLineTdF">				
				<input type="text" size="4" style="font-size:18px;" value="${aDtimeReserve.cname}"
				onkeyup="getAny(this.value, 'cname', 'techid', 'empl', 'name')" name="cname" id="cname"/>				
				<input type="hidden" name="techid" id="techid" value="${aDtimeReserve.techid}"/>
				</td>
				
				<td class="hairLineTdF"><input type="text" name="credit" size="1" style="font-size:18px;" value="${aDtimeReserve.credit}"/></td>
				<td class="hairLineTdF"><input type="text" name="thour" size="1" style="font-size:18px;" value="${aDtimeReserve.thour}"/></td>
				<td class="hairLineTdF"><input type="text" name="Select_Limit" size="2" style="font-size:18px;" value="${aDtimeReserve.Select_Limit}"/></td>
				<td class="hairLineTdF">
				<select name="cyber" style="font-size:18px;">
					<option <c:if test="${aDtimeReserve.cyber=='0'}">selected</c:if> value="0">否</option>
					<option <c:if test="${aDtimeReserve.cyber=='1'}">selected</c:if> value="1">是</option>
				</select>
				</td>
				<td class="hairLineTdF">
				<select name="innovate" style="font-size:18px;">
					<option <c:if test="${aDtimeReserve.innovate=='0'}">selected</c:if> value="0">否</option>
					<option <c:if test="${aDtimeReserve.innovate=='1'}">selected</c:if> value="1">是</option>
				</select>
				</td>
				<td class="hairLineTdF">
				<select name="license" style="font-size:18px;">
					<option <c:if test="${aDtimeReserve.license=='0'}">selected</c:if> value="0">否</option>
					<option <c:if test="${aDtimeReserve.license=='1'}">selected</c:if> value="1">是</option>
				</select>
				</td>
				<td class="hairLineTdF">
				<select name="additionType" style="font-size:18px;">
					<option <c:if test="${aDtimeReserve.additionType=='0'}">selected</c:if> value="0">一般</option>
					<option <c:if test="${aDtimeReserve.additionType=='1'}">selected</c:if> value="1">實習</option>
					<option <c:if test="${aDtimeReserve.additionType=='2'}">selected</c:if> value="2">專題</option>
				</select>
				</td>
				<td class="hairLineTdF">
				<select name="confirmType" style="font-size:18px;">
					<option <c:if test="${aDtimeReserve.confirmType=='1'}">selected</c:if> value="1">否</option>
					<option <c:if test="${aDtimeReserve.confirmType=='2'}">selected</c:if> value="2">是</option>
				</select>
				</td>
			</tr>
			
		</table>
		
		</td>
	</tr>
	<tr>
		<td>
		<input type="hidden" name="classLess" value="${aDtimeReserve.depart_class}" />
		<input type="hidden" name="year" value="${aDtimeReserve.year}" />
		<table class="hairLineTable" cellpadding="5" cellspacing="5" width="50%">
			<tr>
				<td class="hairLineTdF" style="font-size:18px;" colspan="4">一科目多教師</td>
			</tr>
			
			<tr>
				<td class="hairLineTdF" style="font-size:18px;" align="center" nowrap>排新教師</td>
				<td class="hairLineTdF">
				<input type="hidden" name="Dtime_reserve_teachers_oid" value=""/>
				<input type="text" size="4" style="font-size:18px;" 
				onkeyup="getAny(this.value, 'cname0', 'techid0', 'empl', 'name')" name="cname" id="cname0"/>				
				<input type="hidden" name="techids" id="techid0"/>
				</td>
				<td class="hairLineTdF" style="font-size:18px;" align="center" nowrap>排新時數</td>
				<td width="100%" class="hairLineTdF">
				<input type="text" name="thours" size="1" style="font-size:18px;" value=""/>
				</td>
			</tr>	
			
			<c:forEach items="${teachers}" var="t">
			<tr>
				<td class="hairLineTdF" style="font-size:18px;" align="center" nowrap>已排教師</td>
				<td class="hairLineTdF">
				<input type="hidden" name="Dtime_reserve_teachers_oid" value="${t.Oid}" />
				<input type="text" size="4" style="font-size:18px;" value="${t.cname}" 
				onkeyup="getAny(this.value, 'cname${t.Oid}', 'techid${t.Oid}', 'empl', 'name')" name="cname" id="cname${t.Oid}"/>				
				<input type="hidden" name="techids" id="techid${t.Oid}" value="${t.idno}"/>
				</td>
				<td class="hairLineTdF" style="font-size:18px;" align="center" nowrap>已排時數</td>
				<td width="100%" class="hairLineTdF">
				<input type="text" name="thours" size="1" style="font-size:18px;" value="${t.thours}"/>
				</td>
			</tr>
			</c:forEach>
				
		</table>
		
		</td>
	</tr>
	
	
	
	
	
	
	
	
	
	
	
	
	
	<tr height="10">
		<td>
		
		</td>
	</tr>
		
	
	<tr height="40">
		<td class="fullColorTable" align="center">
		<input type="submit" name="method" 
		value="<bean:message key='Save'/>" 
		id="Save" class="gSubmit"
		onMouseOver="showHelpMessage('依照以上條件儲存', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">
		
		<input type="submit" name="method" 
		value="<bean:message key='Delete'/>" 
		id="delete" class="gCancel"
		onMouseOver="showHelpMessage('刪除此筆規劃', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">
					
		<input type="submit" name="method" 
		value="<bean:message key='Back'/>" 
		id="Back" class="gGreen"
		onMouseOver="showHelpMessage('結束編輯', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">
		
		</td>
	</tr>
</table>