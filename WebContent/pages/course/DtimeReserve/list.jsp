<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 必修 -->
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" style="font-size:20px;" nowrap>建立 ${DtimeReserveManagerForm.map.year}學年課程</td>
				
			</tr>
		</table>
		<table class="hairLineTable" cellpadding="5" cellspacing="5" width="99%">
			<tr>
				
				<td class="hairLineTdF" style="font-size:18px;">學期</td>
				<td class="hairLineTdF" style="font-size:18px;">選別</td>	
				<td class="hairLineTdF" style="font-size:18px;" nowrap>課程名稱</td>				
				<td class="hairLineTdF" style="font-size:18px;">教師</td>
				<td class="hairLineTdF" style="font-size:18px;">學分</td>
				<td class="hairLineTdF" style="font-size:18px;">時數</td>
				<td class="hairLineTdF" style="font-size:18px;">人數</td>
				<td class="hairLineTdF" style="font-size:18px;">上機</td>
				<td class="hairLineTdF" style="font-size:18px;">創新</td>
				<td class="hairLineTdF" style="font-size:18px;">證照</td>
				<td class="hairLineTdF" style="font-size:18px;">類型</td>
				<td class="hairLineTdF" style="font-size:18px;">確認</td>				
			</tr>
			<tr>
						
				<td class="hairLineTdF">
				<select name="term" style="font-size:18px;">
					<option value="1">上學期</option>
					<option value="2">下學期</option>
				</select>
				</td>
				<td class="hairLineTdF" style="font-size:18px;">
				<select name="opt" style="font-size:18px;">
					<option value="1">必修</option>
					<option value="2">選修</option>
				</select>				
				</td>
				<td class="hairLineTdF" width="100%">				
				<input type="text" autocomplete="off" style="width:100%; font-size:18px;"
				name="chi_name" id="chi_name" onkeyup="getAny(this.value, 'chi_name', 'cscode', 'CsnoLast2', '${DtimeReserveManagerForm.map.classLess}')"/>
				<input type="hidden" name="cscode" id="cscode"/>				
				<input type="hidden" name="Oid"/>
				</td>
				
				<td class="hairLineTdF">				
				<input type="text" size="4" style="font-size:18px;" onkeyup="getAny(this.value, 'cname', 'techid', 'empl', 'name')" name="cname" id="cname"/>				
				<input type="hidden" name="techid"/>				
				</td>
				
				<td class="hairLineTdF"><input type="text" name="credit" size="1" style="font-size:18px;"/></td>
				<td class="hairLineTdF"><input type="text" name="thour" size="1" style="font-size:18px;"/></td>
				<td class="hairLineTdF"><input type="text" name="Select_Limit" size="2" style="font-size:18px;"/></td>
				<td class="hairLineTdF">
				<select name="cyber" style="font-size:18px;">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
				</td>
				<td class="hairLineTdF">
				<select name="innovate" style="font-size:18px;">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
				</td>
				<td class="hairLineTdF">
				<select name="license" style="font-size:18px;">
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
				<td class="hairLineTdF">
				<select name="confirmType" style="font-size:18px;">
					<option value="0">否</option>
					<option value="1">是</option>
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
		
		<input type="button" class="gCancle" value="說明" id="adddHelp" onClick="showObj('help')" 
					onMouseOver="showHelpMessage('按下可開啟或關閉說明', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
		
		</td>
	</tr>	
	
	<tr>
		<td>		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" style="font-size:20px;" nowrap>
				${DtimeReserveManagerForm.map.year}學年課程列表，點選課程名稱即可進行編輯。
				</td>
				
			</tr>
		</table>
		
		
		
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
			
			<c:forEach items="${opt1}" var="o">
			<tr>
				<td class="hairLineTdF" style="font-size:18px;" align="center">				
					<c:if test="${o.term=='1'}">上</c:if>
					<c:if test="${o.term=='2'}">下</c:if>				
				</td>
				<td class="hairLineTdF" style="font-size:18px;" align="center">				
					<c:if test="${o.opt=='1'}">必</c:if>
					<c:if test="${o.opt=='2'}">選</c:if>				
				</td>	
				<td class="hairLineTdF" width="100%" style="font-size:18px;" align="left">				
				<input type="hidden" name="Oid" value="${o.Oid }"/>
				<a href="/CIS/Course/DtimeReserveManager.do?Dtime_reserve_oid=${o.Oid}">${o.cscode}&nbsp;${o.chi_name}</a>
				</td>
				
				<td class="hairLineTdF" nowrap width="80" style="font-size:18px;" align="left">${o.cname}</td>
				
				<td class="hairLineTdF" style="font-size:18px;" align="right">${o.credit}</td>
				<td class="hairLineTdF" style="font-size:18px;" align="right">${o.thour}</td>
				<td class="hairLineTdF" style="font-size:18px;" align="right">${o.Select_Limit}</td>
				<td class="hairLineTdF" style="font-size:18px;" align="center">				
					<c:if test="${o.cyber=='0'}">否</c:if>
					<c:if test="${o.cyber=='1'}">是</c:if>				
				</td>
				<td class="hairLineTdF" style="font-size:18px;" align="center">
					<c:if test="${o.innovate=='0'}">否</c:if>
					<c:if test="${o.innovate=='1'}">是</c:if>
				</td>
				<td class="hairLineTdF" style="font-size:18px;" align="center">
					<c:if test="${o.license=='0'}">否</c:if>
					<c:if test="${o.license=='1'}">是</c:if>
				</td>
				<td class="hairLineTdF" style="font-size:18px;" align="center">
					<c:if test="${o.additionType=='0'}">否</c:if>
					<c:if test="${o.additionType=='1'}">是</c:if>
				</td>
				<td class="hairLineTdF" style="font-size:18px;" align="center">				
					<c:if test="${o.confirmType=='0'}">否</c:if>
					<c:if test="${o.confirmType=='1'}">是</c:if>
					<c:if test="${o.confirmType=='2'}">定</c:if>
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
	
	
	
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" onClick="return confirmSubmit('確定建立為第1學期？');"
		value="<bean:message key='WriteTerm1' bundle="COU"/>" 
		id="WriteTerm1" class="gCancel"
		onMouseOver="showHelpMessage('將以上資料建立第1學期基本開課資料', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">
					
		<input type="submit" name="method" onClick="return confirmSubmit('確定建立為第2學期？');"
		value="<bean:message key='WriteTerm2' bundle="COU"/>" 
		id="WriteTerm2" class="gGreen"
		onMouseOver="showHelpMessage('將以上資料建立第2學期基本開課資料', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">
		
		
		
		</td>
	</tr>
</table>