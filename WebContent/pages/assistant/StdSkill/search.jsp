<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td>
		<table class="hairLineTable">	
			<tr>		
				<td class="hairLineTdF">
				<select name="Cidno" style="font-size:18px;">
					<option <c:if test="${StdSkillForm.map.Cidno=='_'}">selected</c:if> value="_">所有校區</option>
					<c:forEach items="${AllCampuses}" var="code5">
						<option <c:if test="${StdSkillForm.map.Cidno==code5.idno}">selected</c:if> value="${code5.idno}">${code5.name}</option>
					</c:forEach>
				</select>
				<select name="Sidno" style="font-size:18px;">
					<option <c:if test="${StdSkillForm.map.Sidno=='_'}">selected</c:if> value="__">所有學制</option>
					<c:forEach items="${AllSchools}" var="code5">
						<option <c:if test="${StdSkillForm.map.Sidno==code5.idno}">selected</c:if> value="${code5.idno}">${code5.name}</option>
					</c:forEach>
				</select>
				<select name="Didno" style="font-size:18px;">
					<option <c:if test="${StdSkillForm.map.Didno=='_'}">selected</c:if> value="_">所有科系</option>
					<c:forEach items="${AllDepts}" var="code5">
						<option <c:if test="${StdSkillForm.map.Didno==code5.idno}">selected</c:if> value="${code5.idno}">${code5.name}</option>
					</c:forEach>
				</select>
				<select name="Grade" style="font-size:18px;">
					<option <c:if test="${StdSkillForm.map.Grade=='_'}">selected</c:if> value="_">所有年級</option>
					<option <c:if test="${StdSkillForm.map.Grade=='1'}">selected</c:if> value="1">1年級</option>
					<option <c:if test="${StdSkillForm.map.Grade=='2'}">selected</c:if> value="2">2年級</option>
					<option <c:if test="${StdSkillForm.map.Grade=='3'}">selected</c:if> value="3">3年級</option>
					<option <c:if test="${StdSkillForm.map.Grade=='4'}">selected</c:if> value="4">4年級</option>
					<option <c:if test="${StdSkillForm.map.Grade=='5'}">selected</c:if> value="5">5年級</option>
				</select>
				<select name="ClassNo" style="font-size:18px;">
					<option <c:if test="${StdSkillForm.map.ClassNo=='_'}">selected</c:if> value="_">所有班級</option>
					<option <c:if test="${StdSkillForm.map.ClassNo=='1'}">selected</c:if> value="1">甲</option>
					<option <c:if test="${StdSkillForm.map.ClassNo=='2'}">selected</c:if> value="2">乙</option>
					<option <c:if test="${StdSkillForm.map.ClassNo=='3'}">selected</c:if> value="3">丙</option>
					<option <c:if test="${StdSkillForm.map.ClassNo=='4'}">selected</c:if> value="4">丁</option>
				</select>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>		
		<td>
		<table class="hairLineTable">		
			<tr>
				<td class="hairLineTdF" nowrap>學年</td>
				<td class="hairLineTdF">
				
				
				<input style="font-size:18px;" type="text" name="SchoolYear" id="SchoolYear"
				size="1" value="${StdSkillForm.map.SchoolYear}" /></td>
				
				<td class="hairLineTdF">
				
				<select name="SchoolTerm" style="font-size:18px;" id="SchoolTerm">
					<option <c:if test="${StdSkillForm.map.SchoolTerm==''}">selected</c:if> value="">全學期</option>
					<option <c:if test="${StdSkillForm.map.SchoolTerm=='1'}">selected</c:if> value="1">上學期</option>
					<option <c:if test="${StdSkillForm.map.SchoolTerm=='2'}">selected</c:if> value="2">下學期</option>
				</select>
				</td>			
			
				<td class="hairLineTdF" nowrap>學號/身分證</td>
				<td class="hairLineTdF">
				<input type="text" autocomplete="off" style="font-size:18px; ime-mode:disabled" autocomplete="off"
				name="StudentNo" id="studentNo" size="8" value="${StdSkillForm.map.StudentNo}"
				onkeyup="if(this.value.length>=2)getAny(this.value, 'studentNo', 'studentName', 'stmid', 'no')"/>
				</td>
				<td class="hairLineTdF" nowrap>姓名</td>
				<td class="hairLineTdF">
				<input type="text" name="StudentName" style="font-size:18px;" id="studentName" size="4" value="${StdSkillForm.map.StudentName}"
	 			onkeyup="if(this.value.length>0)getAny(this.value, 'studentName', 'studentNo', 'stmid', 'name')"/>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	<tr>
		<td>
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF" nowrap>證照代碼</td>
				<td class="hairLineTdF" nowrap>
				<input type="text" autocomplete="off" style="font-size:18px; ime-mode:disabled" autocomplete="off"
				name="LicenseCode" id="LicenseCode" size="12" value="${StdSkillForm.map.LicenseCode}"
				onkeyup="if(this.value.length>=1)getAny(this.value, 'LicenseCode', 'LicenseName', 'license', 'no')"/>
				
				
				<input type="text" name="LicenseName" style="font-size:18px;" id="LicenseName" value="${StdSkillForm.map.LicenseName}"
	 			onkeyup="if(this.value.length>1)getAny(this.value, 'LicenseName', 'LicenseCode', 'license', 'name')"/>
				
				</td>
			</tr>
		</table>
		</td>
	</tr>
	
	<tr>
		<td>
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF" nowrap>補助金額</td>
				<td class="hairLineTdF" nowrap>
				<input type="text" name="Amount" value="${StdSkillForm.map.Amount}" style="font-size:18px;" size="5"/>				
				</td>
				
				<td class="hairLineTdF" nowrap>
				<select name="AmountType" style="font-size:18px;">
					<option <c:if test="${StdSkillForm.map.AmountType=='3'}">selected</c:if> value="3">無補助</option>
					<option <c:if test="${StdSkillForm.map.AmountType=='1'}">selected</c:if> value="1">專業證照報名費</option>
                     <option <c:if test="${StdSkillForm.map.AmountType=='2'}">selected</c:if> value="2">特種獎學金</option>                     		
				</select>
				</td>
				
				<td class="hairLineTdF" nowrap>補助日期</td>
				<td class="hairLineTdF" nowrap>
				
				<input type="text" name="AmountDate" id="AmountDate" size="6" value="${StdSkillForm.map.AmountDate}"
				onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled; font-size:18px;" autocomplete="off"/>	
				<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display:none;">
					<tr>
					<td id="ds_calclass"></td>
					</tr>
				</table>		
				</td>
			</tr>
		</table>
		</td>
	</tr>
	
	<tr>
		<td>
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF" nowrap>證書編號</td>
				<td class="hairLineTdF" nowrap>
				<input type="text" name="LicenseNo" value="${StdSkillForm.map.LicenseNo}" style="font-size:18px;" size="5"/>				
				</td>
				<td class="hairLineTdF" nowrap>生效日期</td>
				<td class="hairLineTdF" nowrap>
				<input type="text" id="LicenseValidDate" name="LicenseValidDate" value="${StdSkillForm.map.LicenseValidDate}" 
				onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled; font-size:18px;" autocomplete="off" 
				style="font-size:18px;" size="5"/>				
				</td>
				<td class="hairLineTdF" nowrap>
				<select name="Reason" style="font-size:18px;">
					<option <c:if test="${StdSkillForm.map.Reason==''}">selected</c:if> value="">未領補助原因</option>
               		<option <c:if test="${StdSkillForm.map.Reason=='1'}">selected</c:if> value="1">非學籍生</option>
               		<option <c:if test="${StdSkillForm.map.Reason=='2'}">selected</c:if> value="2">未聯絡到</option>
               		<option <c:if test="${StdSkillForm.map.Reason=='3'}">selected</c:if> value="3">已聯絡但未來領取</option>
               		<option <c:if test="${StdSkillForm.map.Reason=='9'}">selected</c:if> value="9">其他</option>
				</select>			
				</td>
			</tr>
		</table>
		</td>
	</tr>
	
	
	<tr>
		<td>
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF" nowrap>課程名稱</td>
				<td class="hairLineTdF" nowrap>
				<input type="text" style="font-size:18px;" autocomplete="off" name="CsName"
					id="CsName" size="8" value="${StdSkillForm.map.CsName}"
					onFocus="chInput(this.id)"
					onMouseOver="showHelpMessage('請輸入課程名稱, 如: 國文, 若用貼上請動一下方向鍵令代碼自動完成', 'inline', this.id)"
					onMouseOut="showHelpMessage('', 'none', this.id)"
					onkeyup="getAny(this.value, 'CsName', 'Cscode', 'Csno', 'name')"/>
				<input type="text" name="Cscode" id="Cscode" size="4"
					autocomplete="off" style="ime-mode:disabled; font-size:18px;" autocomplete="off"
					value="${StdSkillForm.map.Cscode}"
					onkeyup="if(this.value.length>2)getAny(this.value, 'Cscode', 'CsName', 'Csno', 'no')"									
					onFocus="chInput(this.id)"
					onMouseOver="showHelpMessage('請輸入科目代碼, 如: 50000', 'inline', this.id)"
					onMouseOut="showHelpMessage('', 'none', this.id)" />
				
				</td>
				
				<td class="hairLineTdF" nowrap>教師姓名</td>
				<td class="hairLineTdF" nowrap>
				<input type="text" style="font-size:18px;"
					onkeyup="getAny(this.value, 'TechName', 'TechIdno', 'empl', 'name')"
					autocomplete="off"
					name="TechName"
					onMouseOver="showHelpMessage('請輸入教師姓名, 若用貼上請動一下方向鍵令代碼自動完成', 'inline', this.id)"
					onMouseOut="showHelpMessage('', 'none', this.id)"
					onFocus="chInput(this.id)" id="TechName" size="4"
					value="${StdSkillForm.map.TechName}" />
				
				<input type="text" name="TechIdno" id="TechIdno" size="8"
					style="font-size:18px; ime-mode:disabled;" autocomplete="off"
					value="${StdSkillForm.map.TechIdno}"
					onMouseOver="showHelpMessage('請輸入教師代碼', 'inline', this.id)"
					onMouseOut="showHelpMessage('', 'none', this.id)"
					onFocus="chInput(this.id)"
					onkeyup="if(this.value.length>2)getAny(this.value, 'TechIdno', 'TechName', 'empl', 'no')"/>				
				</td>
			</tr>
		</table>
		</td>
	</tr>
	
	<tr>
		<td>
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF" nowrap>序號</td>
				<td class="hairLineTdF" nowrap>
				<input type="text" name="SerialNo" style="font-size:18px;" size="6" value="${StdSkillForm.map.SerialNo}"/>				
				</td>
				
				<td class="hairLineTdF" nowrap>
				<select name="CustomNo" style="font-size:18px;">
					<option <c:if test="${StdSkillForm.map.CustomNo==''}">selected</c:if> value="">自訂分類</option>
					<option <c:if test="${StdSkillForm.map.CustomNo=='1'}">selected</c:if> value="1">語文類</option>
               		<option <c:if test="${StdSkillForm.map.CustomNo=='2'}">selected</c:if> value="2">資訊類</option>
               		<option <c:if test="${StdSkillForm.map.CustomNo=='3'}">selected</c:if> value="3">專業類</option>
               		<option <c:if test="${StdSkillForm.map.CustomNo=='4'}">selected</c:if> value="4">其他</option>
				</select>			
				</td>
				
				<td class="hairLineTdF" nowrap>
				<select name="ApplyType" style="font-size:18px;">
					<option <c:if test="${StdSkillForm.map.ApplyType==''}">selected</c:if> value="">報名類型</option>
					<option <c:if test="${StdSkillForm.map.ApplyType=='0'}">selected</c:if> value="0">單獨</option>
                    <option <c:if test="${StdSkillForm.map.ApplyType=='1'}">selected</c:if> value="1">團體</option>
				</select>			
				</td>
				
				<td class="hairLineTdF" nowrap>
				<select name="Pass" style="font-size:18px;">
					<option <c:if test="${StdSkillForm.map.Pass=='1'}">selected</c:if> value="1">畢業技能檢定合格</option>
					<option <c:if test="${StdSkillForm.map.Pass=='0'}">selected</c:if> value="0">畢業技能檢定不合格</option>
				</select>			
				</td>
			</tr>
		</table>
		</td>
	</tr>
	
	
	<tr>
		<td class="fullColorTable" align="center">		
		<input type="submit" name="method" value="<bean:message key='Query'/>" 
		id="Query" class="gSubmit" onMouseOver="showHelpMessage('依照以上條件查詢', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">
		
		<input type="submit" name="method" value="<bean:message key='Print'/>" 
		id="Print" class="gSubmit" onMouseOver="showHelpMessage('依照以上條件列印', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">
					
		<input type="submit" name="method" value="<bean:message key='Create'/>" 
		id="add" class="gGreen" onMouseOver="showHelpMessage('依照以上條件新增', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">
		
		<input type="submit" name="method" value="<bean:message key='Clear'/>" 
		id="Clear" class="gCancel" onMouseOver="showHelpMessage('清空查詢條件', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">
		</td>
	</tr>
</table>
		