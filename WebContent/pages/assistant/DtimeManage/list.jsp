<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 必修 -->
<table width="100%" cellpadding="0" cellspacing="0">
	
	<tr>
		<td style="font-size:20px;">
		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" style="font-size:20px;" nowrap>選擇已規劃的課程</td>
			</tr>
		</table>
		<table class="hairLineTable" cellpadding="5" cellspacing="5" width="99%">
			<tr>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>開設</td>
				<td class="hairLineTdF" style="font-size:18px;">人數</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>課程名稱</td>
				
			</tr>
<script>
function checkChose(Oid){
	if(document.getElementById("checked"+Oid).value==""){
		document.getElementById("checked"+Oid).value=Oid;	
	}else{
		document.getElementById("checked"+Oid).value="";	
	}
}
</script>
			<c:forEach items="${reserve}" var="r">
			<tr>	
				<td class="hairLineTdF" align="center"><input type="checkbox" onClick="checkChose('${r.Oid}')" /></td>
				<td class="hairLineTdF" style="font-size:18px;">
				<input type="text" name="Select_Limit" size="2" value="60" style="font-size:18px;"/>				
				</td>			
				<td class="hairLineTdF" width="100%" style="font-size:18px;">
				<input type="hidden" name="checked" id="checked${r.Oid}" value="" />
				${r.chi_name}
				<input type="hidden" name="cscode" id="cscode${r.Oid}"/>
				<input type="hidden" name="Oid"/>
				</td>
				
			</tr>
			</c:forEach>
			<c:if test="${empty reserve}">
				<tr>
					<td colspan="4" style="font-size:18px;" class="hairLineTdF">無課程規劃資料可供選擇</td>
				</tr>
			</c:if>
		</table>
		
		</td>
	</tr>	
	
	<tr>
		<td>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" style="font-size:20px;" nowrap>規劃在其他年度的課程</td>
			</tr>
		</table>
		<table class="hairLineTable" cellpadding="5" cellspacing="5" width="99%">
			<tr>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>開設</td>
				<td class="hairLineTdF" style="font-size:18px;">人數</td>
				<td class="hairLineTdF" style="font-size:18px;">學期</td>
				
				<td class="hairLineTdF" style="font-size:18px;" nowrap>課程名稱</td>											
			</tr>
			<tr>
				<td class="hairLineTdF" align="center">
				<input type="checkbox" checked disabled />				
				</td>
				
				<td class="hairLineTdF"><input type="text" name="Select_Limit" value="60" size="2" style="font-size:18px;"/></td>
				<td class="hairLineTdF">
				<input type="hidden" name="checked" value="" />
				<select name="term" style="font-size:18px;" id="term">
					<option value="1">上學期</option>
					<option value="2">下學期</option>
				</select>
				</td>				
				<td class="hairLineTdF" width="100%">
				<select name="cscode" style="font-size:18px;">
					<option value="">建立非本學期規劃課程</option>
					<c:forEach items="${reserved}" var="rd">
					<option value="${rd.Oid}">${rd.grade}年級第${rd.term}學期: ${rd.chi_name}</option>
					</c:forEach>
				</select>
				<input type="hidden" name="Oid"/>
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
				
				
				
				<a href="/CIS/Print/reserve/List4Class.do?term=${DtimeReserveManagerForm.map.term}&depart_class=${classSel}&year=${DtimeReserveManagerForm.map.year}">
				下載或開啟已開設課程</a>
				</td>
				
				
				
				
			</tr>
		</table>
		
		
		
		<table class="hairLineTable" cellpadding="5" cellspacing="5" width="99%">			
			<tr>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>編號</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>學期</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>選別</td>	
				<td class="hairLineTdF" style="font-size:18px;" nowrap>課程名稱</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>學分</td>							
				<td class="hairLineTdF" style="font-size:18px;" nowrap>時數</td>
				
				<td class="hairLineTdF" style="font-size:18px;" nowrap>人數</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>上機</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>類型</td>				
			</tr>	
			
			<c:forEach items="${opt1}" var="o">
			<tr>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>${o.Oid}</td>
				<td class="hairLineTdF" style="font-size:18px;" align="center">				
					<c:if test="${o.term=='1'}">上</c:if>
					<c:if test="${o.term=='2'}">下</c:if>				
				</td>
				<td class="hairLineTdF" style="font-size:18px;" align="center">			
				<c:if test="${o.opt=='1'}">校必</c:if>
				<c:if test="${o.opt=='2'}">院必</c:if>
				<c:if test="${o.opt=='3'}">系必</c:if>
				<c:if test="${o.opt=='4'}">校選</c:if>
				<c:if test="${o.opt=='5'}">院選</c:if>
				<c:if test="${o.opt=='6'}">選修</c:if>			
				</td>	
				<td class="hairLineTdF" width="100%" style="font-size:18px;" align="left">				
				<input type="hidden" name="Oid" value="${o.Oid }"/>
				<a href="/CIS/DepAssistant/DtimeManager.do?Oid=${o.Oid}">${o.cscode}&nbsp;${o.chi_name}</a>
				</td>
				
				<td class="hairLineTdF" style="font-size:18px;" align="right">${o.credit}</td>
				<td class="hairLineTdF" style="font-size:18px;" align="right">${o.thour}</td>
				
				
				<td class="hairLineTdF" style="font-size:18px;" align="right">${o.Select_Limit}</td>
				<td class="hairLineTdF" style="font-size:18px;" align="center">				
					<c:if test="${o.cyber=='0'}">否</c:if>
					<c:if test="${o.cyber=='1'}">是</c:if>				
				</td>
				<td class="hairLineTdF" style="font-size:18px;" align="center">
					<c:if test="${o.additionType=='0'}">一般</c:if>
					<c:if test="${o.additionType=='1'}">實習</c:if>
					<c:if test="${o.additionType=='2'}">專題</c:if>
				</td>				
			</tr>
			</c:forEach>
		</table>
				
		</td>
	</tr>	
	
<script>
function clearid(id){
	//alert();
	document.getElementById(id).value="";	
}	
</script>	
	
	
	
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<!-- input type="submit" name="method" onClick="return confirmSubmit('確定建立為第1學期？');"
		value="<bean:message key='WriteTerm1' bundle="COU"/>" 
		id="WriteTerm1" class="gCancel"
		onMouseOver="showHelpMessage('將以上資料建立第1學期基本開課資料', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">
					
		<input type="submit" name="method" onClick="return confirmSubmit('確定建立為第2學期？');"
		value="<bean:message key='WriteTerm2' bundle="COU"/>" 
		id="WriteTerm2" class="gGreen"
		onMouseOver="showHelpMessage('將以上資料建立第2學期基本開課資料', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)"-->
		
		
		
		</td>
	</tr>
</table>