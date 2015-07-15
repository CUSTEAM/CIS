<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>		
		<td>		
		<input type="hidden" name="classLess" value="${aDtimeReserve.depart_class}" />
		<input type="hidden" name="year" value="${aDtimeReserve.year}" />
		<input type="hidden" name="term"  value="${aDtimeReserve.term}"/>
		<input type="hidden" name="Oid" value="${aDtimeReserve.Oid }"/>		
		
		<table class="hairLineTable" cellpadding="5" cellspacing="5" width="99%">			
			<tr>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>學期</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>選別</td>	
				<td class="hairLineTdF" style="font-size:18px;" nowrap>課程名稱</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>時數</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>學分</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>人數</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>上機</td>
				<td class="hairLineTdF" style="font-size:18px;" nowrap>類型</td>
			</tr>
			
			<tr>
				<td class="hairLineTdF" style="font-size:18px;">
				
				
				<c:if test="${aDtimeReserve.term=='1'}">上</c:if>
				<c:if test="${aDtimeReserve.term=='2'}">下</c:if>
					
				
				</td>
				<td class="hairLineTdF" style="font-size:18px;">
				<c:if test="${aDtimeReserve.opt=='1'}">校必</c:if>
				<c:if test="${aDtimeReserve.opt=='2'}">院必</c:if>
				<c:if test="${aDtimeReserve.opt=='3'}">系必</c:if>
				<c:if test="${aDtimeReserve.opt=='4'}">校選</c:if>
				<c:if test="${aDtimeReserve.opt=='5'}">院選</c:if>
				<c:if test="${aDtimeReserve.opt=='6'}">選修</c:if>
				
				</td>	
				<td class="hairLineTdF" width="100%" style="font-size:18px;">
				${aDtimeReserve.chi_name}
				</td>
				
				
				<td class="hairLineTdF" style="font-size:18px;">${aDtimeReserve.thour}</td>				
				<td class="hairLineTdF" style="font-size:18px;">${aDtimeReserve.credit}</td>				
				<td class="hairLineTdF" style="font-size:18px;">
				
				<input type="text" name="Select_Limit" size="2" style="font-size:18px;" value="${aDtimeReserve.Select_Limit}"/>
				</td>
				<td class="hairLineTdF" style="font-size:18px;">
				<c:if test="${aDtimeReserve.cyber=='0'}">否</c:if>
				<c:if test="${aDtimeReserve.cyber=='1'}">是</c:if>
				</td>
								
				</td>
				<td class="hairLineTdF" style="font-size:18px;">
					<c:if test="${aDtimeReserve.additionType=='0'}">一般</c:if>
					<c:if test="${aDtimeReserve.additionType=='1'}">實習</c:if>
					<c:if test="${aDtimeReserve.additionType=='2'}">專題</c:if>
				</td>
				
			</tr>
			
		</table>
		
		</td>
	</tr>
	
	<tr height="40">
		<td class="fullColorTable" align="center">		
		
		<input type="submit" name="method" 
		value="<bean:message key='Delete'/>" 
		id="delete" class="gCancel"
		onMouseOver="showHelpMessage('刪除此課程', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">	
		
		</td>
	</tr>
	
	
	<tr>
		<td valign="top">	
		<table class="hairLineTable" cellpadding="5" cellspacing="5" width="99%">
			<tr>
				<td class="hairLineTdF" style="font-size:18px;" colspan="4">一科目多教師</td>
			</tr>
		</table>
		<table class="hairLineTable" cellpadding="5" cellspacing="5" width="99%" id="addTech">
			<tr>
				<td class="hairLineTdF" style="font-size:18px;" align="center" nowrap>排新教師</td>
				<td class="hairLineTdF">
				<input type="hidden" name="Dtime_reserve_teachers_Oid" value=""/>
				<input type="text" size="4" style="font-size:18px;"
				onkeyup="getAny(this.value, 'cname0', 'techid0', 'empl', 'name')" name="cname" id="cname0"/>				
				<input type="hidden" name="techid" id="techid0"/>
				</td>
				<td class="hairLineTdF" style="font-size:18px;" align="center" nowrap>排新時數</td>
				<td class="hairLineTdF" nowrap width="100%">
				<input type="text" name="thour" size="1" style="font-size:18px;" value=""/>
				<input type="button" value="增加教師" class="gGreenSmall" onClick="document.getElementById('addTech1').style.display='inline';"/>
				</td>
			</tr>
		</table>
		<c:forEach begin="1" end="7" var="t">
		<table class="hairLineTable" cellpadding="5" cellspacing="5" width="99%" id="addTech${t}" style="display:none;">
			<tr>
				<td class="hairLineTdF" style="font-size:18px;" align="center" nowrap>排新教師</td>
				<td class="hairLineTdF">
				<input type="hidden" name="Dtime_reserve_teachers_Oid" value=""/>
				<input type="text" size="4" style="font-size:18px;"				
				onkeyup="getAny(this.value, 'cname${t}', 'techid${t}', 'empl', 'name')" name="cname" id="cname${t}"/>				
				<input type="hidden" name="techid" id="techid${t}"/>
				</td>
				<td class="hairLineTdF" style="font-size:18px;" align="center" nowrap>排新時數</td>
				<td class="hairLineTdF" width="100%">
				<input type="text" name="thour" size="1" style="font-size:18px;" value=""/>
				<input type="button" value="增加教師" class="gGreenSmall" onClick="document.getElementById('addTech${t+1}').style.display='inline';"/>
				</td>
			</tr>
		</table>
		</c:forEach>
		<table class="hairLineTable" cellpadding="5" cellspacing="5" width="99%" id="addTech8" style="display:none;">
			<tr>
				<td class="hairLineTdF" style="font-size:18px;" align="center" nowrap>排新教師</td>
				<td class="hairLineTdF">
				<input type="hidden" name="Dtime_reserve_teachers_Oid" value=""/>
				<input type="text" size="4" style="font-size:18px;"
				onkeyup="getAny(this.value, 'cname8', 'techid8', 'empl', 'name')" name="cname" id="cname8"/>				
				<input type="hidden" name="techid" id="techid8"/>
				</td>
				<td class="hairLineTdF" style="font-size:18px;" align="center" nowrap>排新時數</td>
				<td class="hairLineTdF" width="100%">
				<input type="text" name="thour" size="1" style="font-size:18px;" value=""/>
				<input type="button" value="請確認" class="gGreenSmall"/>
				</td>
			</tr>
		</table>		
		
		<table class="hairLineTable" cellpadding="5" cellspacing="5" width="99%">
			<c:forEach items="${teachers}" var="t">
			<tr>
				<td class="hairLineTdF" style="font-size:18px;" align="center" nowrap>已排教師</td>
				<td class="hairLineTdF">
				<input type="hidden" name="Dtime_reserve_teachers_Oid" value="${t.Oid}" />
				<input type="text" size="4" style="font-size:18px;" value="${t.cname}"
				onkeyup="getAny(this.value, 'cname${t.Oid}', 'techidx${t.Oid}', 'empl', 'name')" name="cname" id="cnamex${t.Oid}"/>				
				<input type="hidden" name="techid" id="techidx${t.Oid}" value="${t.teach_id}"/>
				</td>
				<td class="hairLineTdF" style="font-size:18px;" align="center" nowrap>已排時數</td>
				<td width="100%" class="hairLineTdF">
				<input type="text" name="thour" size="1" style="font-size:18px;" value="${t.hours}"/>				
				</td>
			</tr>
			</c:forEach>
		</table>		
		</td>
	</tr>
	
	<tr>
		
		<td width="100%" valign="top">
		<table class="hairLineTable" cellpadding="5" cellspacing="5" width="99%">	
			<tr>		
				<td class="hairLineTdF">跨選規則</td>
			</tr>
		</table>
		
		<div id="optemp" style="display:none;">
		<table class="hairLineTable" cellpadding="5" cellspacing="5" width="99%">	
			<tr>		
				<td class="hairLineTdF">
				<input type="hidden" name="Dtime_reserve_opencs_Oid" />
				<select name="Cidno" style="font-size:18px;">
					<option value="">選擇校區</option>
					<option value="*">所有校區</option>
					<c:forEach items="${AllCampuses}" var="code5">
						<option value="${code5.idno}">${code5.name}</option>
					</c:forEach>
				</select>
				<select name="Sidno" style="font-size:18px;">
					<option value="">選擇校區</option>
					<option value="*">所有學制</option>
					<c:forEach items="${AllSchools}" var="code5">
						<option value="${code5.idno}">${code5.name}</option>
					</c:forEach>
				</select>
				<select name="Didno" style="font-size:18px;">
					<option value="">選擇科系</option>
					<option value="*">所有科系</option>
					<c:forEach items="${AllDepts}" var="code5">
						<option value="${code5.idno}">${code5.name}</option>
					</c:forEach>
				</select>
				<select name="grades" style="font-size:18px;">
					<option value="">選擇年級</option>
					<option value="*">所有年級</option>
					<option value="1">1年級</option>
					<option value="2">2年級</option>
					<option value="3">3年級</option>
					<option value="4">4年級</option>
					<option value="5">5年級</option>
				</select>
				<select name="ClassNo" style="font-size:18px;">
					<option value="">選擇校區</option>
					<option value="*">所有班級</option>
					<option value="1">甲</option>
					<option value="2">乙</option>
					<option value="3">丙</option>
					<option value="4">丁</option>
				</select>
				<input type="button" value="增加規則" class="gGreenSmall"
				onClick="document.getElementById('opencs').innerHTML=document.getElementById('opencs').innerHTML+document.getElementById('optemp').innerHTML" />
				</td>
			</tr>
		</table>
		</div>
		
		<div id="opencs">
		<table class="hairLineTable" cellpadding="5" cellspacing="5" width="99%">	
			<tr>		
				<td class="hairLineTdF">
				<input type="hidden" name="Dtime_reserve_opencs_Oid" />
				<select name="Cidno" style="font-size:18px;">
					<option value="">選擇校區</option>
					<option value="*">所有校區</option>
					<c:forEach items="${AllCampuses}" var="code5">
						<option value="${code5.idno}">${code5.name}</option>
					</c:forEach>
				</select>
				<select name="Sidno" style="font-size:18px;">
					<option value="">選擇校區</option>
					<option value="*">所有學制</option>
					<c:forEach items="${AllSchools}" var="code5">
						<option value="${code5.idno}">${code5.name}</option>
					</c:forEach>
				</select>
				<select name="Didno" style="font-size:18px;">
					<option value="">選擇科系</option>
					<option value="*">所有科系</option>
					<c:forEach items="${AllDepts}" var="code5">
						<option value="${code5.idno}">${code5.name}</option>
					</c:forEach>
				</select>
				<select name="grades" style="font-size:18px;">
					<option value="">選擇年級</option>
					<option value="*">所有年級</option>
					<option value="1">1年級</option>
					<option value="2">2年級</option>
					<option value="3">3年級</option>
					<option value="4">4年級</option>
					<option value="5">5年級</option>
				</select>
				<select name="ClassNo" style="font-size:18px;">
					<option value="">選擇班級</option>
					<option value="*">所有班級</option>
					<option value="1">甲</option>
					<option value="2">乙</option>
					<option value="3">丙</option>
					<option value="4">丁</option>
				</select>
				<input type="button" value="增加規則" class="gGreenSmall"
				onClick="document.getElementById('opencs').innerHTML=document.getElementById('opencs').innerHTML+document.getElementById('optemp').innerHTML" />
				</td>
			</tr>
		</table>
		</div>
		
		
		
		
		<c:forEach items="${opencs}" var="o">
		<table class="hairLineTable" cellpadding="5" cellspacing="5" width="99%">	
			<tr>		
				<td class="hairLineTdF">
				<input type="hidden" name="Dtime_reserve_opencs_Oid" value="${o.Oid}"/>
				<select name="Cidno" style="font-size:18px;">
					<option value="">刪除</option>
					<option <c:if test="${o.Cidno=='*'}">selected</c:if> value="*">所有校區</option>
					<c:forEach items="${AllCampuses}" var="code5">
						<option <c:if test="${o.Cidno==code5.idno}">selected</c:if> value="${code5.idno}">${code5.name}</option>
					</c:forEach>
				</select>
				<select name="Sidno" style="font-size:18px;">
					<option <c:if test="${o.Sidno=='*'}">selected</c:if> value="*">所有學制</option>
					<c:forEach items="${AllSchools}" var="code5">
						<option <c:if test="${o.Sidno==code5.idno}">selected</c:if> value="${code5.idno}">${code5.name}</option>
					</c:forEach>
				</select>
				<select name="Didno" style="font-size:18px;">
					<option <c:if test="${o.Didno=='*'}">selected</c:if> value="*">所有科系</option>
					<c:forEach items="${AllDepts}" var="code5">
						<option <c:if test="${o.Didno==code5.idno}">selected</c:if> value="${code5.idno}">${code5.name}</option>
					</c:forEach>
				</select>
				<select name="grades" style="font-size:18px;">
					<option <c:if test="${o.grade=='*'}">selected</c:if> value="*">所有年級</option>
					<option <c:if test="${o.grade=='1'}">selected</c:if> value="1">1年級</option>
					<option <c:if test="${o.grade=='2'}">selected</c:if> value="2">2年級</option>
					<option <c:if test="${o.grade=='3'}">selected</c:if> value="3">3年級</option>
					<option <c:if test="${o.grade=='4'}">selected</c:if> value="4">4年級</option>
					<option <c:if test="${o.grade=='5'}">selected</c:if> value="5">5年級</option>
				</select>
				<select name="ClassNo" style="font-size:18px;">
					<option <c:if test="${o.ClassNo=='*'}">selected</c:if> value="*">所有班級</option>
					<option <c:if test="${o.ClassNo=='1'}">selected</c:if> value="1">甲</option>
					<option <c:if test="${o.ClassNo=='2'}">selected</c:if> value="2">乙</option>
					<option <c:if test="${o.ClassNo=='3'}">selected</c:if> value="3">丙</option>
					<option <c:if test="${o.ClassNo=='4'}">selected</c:if> value="4">丁</option>
				</select>
				<input type="button" value="增加規則" class="gGreenSmall"
				onClick="document.getElementById('opencs').innerHTML=document.getElementById('opencs').innerHTML+document.getElementById('optemp').innerHTML" />
				</td>
			</tr>
		</table>
		
		
		</c:forEach>
		
		
		
		</td>
	</tr>
	
	<tr height="40">
		<td class="fullColorTable" align="center">
		<input type="submit" name="method" 
		value="<bean:message key='Save'/>" 
		id="Save2" class="gSubmit"
		onMouseOver="showHelpMessage('依照以上條件儲存', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">
		
		<input type="submit" name="method" 
		value="<bean:message key='Leave'/>" 
		id="leave2" class="gCancel"
		onMouseOver="showHelpMessage('返回課程列表', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">
		
		
		
		</td>
	</tr>
</table>