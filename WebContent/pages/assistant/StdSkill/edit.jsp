<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" cellpadding="0" cellspacing="0">
	
	<tr>		
		<td>
		<table class="hairLineTable">		
			<tr>
				<td class="hairLineTdF" nowrap>學年</td>
				<td class="hairLineTdF">
				
				<input type="hidden" name="Oid" value="${skill.Oid}" />
				<input style="font-size:18px;" type="text" name="SchoolYear" id="SchoolYear"
				size="1" value="${skill.SchoolYear}" /></td>
				
				<td class="hairLineTdF">
				
				<select name="SchoolTerm" style="font-size:18px;" id="SchoolTerm">
					<option <c:if test="${skill.SchoolTerm==''}">selected</c:if> value="">全學期</option>
					<option <c:if test="${skill.SchoolTerm=='1'}">selected</c:if> value="1">上學期</option>
					<option <c:if test="${skill.SchoolTerm=='2'}">selected</c:if> value="2">下學期</option>
				</select>
				</td>			
			
				<td class="hairLineTdF" nowrap>學號/身分證</td>
				<td class="hairLineTdF">
				<input type="text" autocomplete="off" style="font-size:18px; ime-mode:disabled" autocomplete="off"
				name="StudentNo" id="studentNo" size="8" value="${skill.StudentNo}"
				onkeyup="if(this.value.length>=2)getAny(this.value, 'studentNo', 'studentName', 'stmid', 'no')"/>
				</td>
				<td class="hairLineTdF" nowrap>姓名</td>
				<td class="hairLineTdF">
				<input type="text" name="StudentName" style="font-size:18px;" id="studentName" size="4" value="${skill.StudentName}"
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
				name="LicenseCode" id="LicenseCode" size="12" value="${skill.LicenseCode}"
				onkeyup="if(this.value.length>=1)getAny(this.value, 'LicenseCode', 'LicenseName', 'license', 'no')"/>
				
				
				<input type="text" name="LicenseName" style="font-size:18px;" id="LicenseName" value="${skill.Name}"
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
				<input type="text" name="Amount" value="${skill.Amount}" style="font-size:18px;" size="5"/>				
				</td>
				
				<td class="hairLineTdF" nowrap>
				<select name="AmountType" style="font-size:18px;">
					<option <c:if test="${skill.AmountType=='3'}">selected</c:if> value="3">無補助</option>
					<option <c:if test="${skill.AmountType=='1'}">selected</c:if> value="1">專業證照報名費</option>
                     <option <c:if test="${skill.AmountType=='2'}">selected</c:if> value="2">特種獎學金</option>                     		
				</select>
				</td>
				
				<td class="hairLineTdF" nowrap>補助日期</td>
				<td class="hairLineTdF" nowrap>
				
				<input type="text" name="AmountDate" id="AmountDate" size="6" value="${skill.AmountDate}"
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
				<input type="text" name="LicenseNo" value="${skill.LicenseNo}" style="font-size:18px;" size="5"/>				
				</td>
				<td class="hairLineTdF" nowrap>生效日期</td>
				<td class="hairLineTdF" nowrap>
				<input type="text" id="LicenseValidDate" name="LicenseValidDate" value="${skill.LicenseValidDate}" 
				onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled; font-size:18px;" autocomplete="off" 
				style="font-size:18px;" size="5"/>				
				</td>
				<td class="hairLineTdF" nowrap>
				<select name="Reason" style="font-size:18px;">
					<option <c:if test="${skill.Reason==''}">selected</c:if> value="">未領補助原因</option>
               		<option <c:if test="${skill.Reason=='1'}">selected</c:if> value="1">非學籍生</option>
               		<option <c:if test="${skill.Reason=='2'}">selected</c:if> value="2">未聯絡到</option>
               		<option <c:if test="${skill.Reason=='3'}">selected</c:if> value="3">已聯絡但未來領取</option>
               		<option <c:if test="${skill.Reason=='9'}">selected</c:if> value="9">其他</option>
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
					id="CsName" size="8" value="${skill.CsName}"
					onFocus="chInput(this.id)"
					onMouseOver="showHelpMessage('請輸入課程名稱, 如: 國文, 若用貼上請動一下方向鍵令代碼自動完成', 'inline', this.id)"
					onMouseOut="showHelpMessage('', 'none', this.id)"
					onkeyup="getAny(this.value, 'CsName', 'Cscode', 'Csno', 'name')"/>
				<input type="text" name="Cscode" id="Cscode" size="4"
					autocomplete="off" style="ime-mode:disabled; font-size:18px;" autocomplete="off"
					value="${skill.Cscode}"
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
					value="${skill.TechName}" />
				
				<input type="text" name="TechIdno" id="TechIdno" size="8"
					style="font-size:18px; ime-mode:disabled;" autocomplete="off"
					value="${skill.TechIdno}"
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
				<input type="text" name="SerialNo" style="font-size:18px;" size="6" value="${skill.SerialNo}"/>				
				</td>
				
				<td class="hairLineTdF" nowrap>
				<select name="CustomNo" style="font-size:18px;">
					<option <c:if test="${skill.CustomNo==''}">selected</c:if> value="">自訂分類</option>
					<option <c:if test="${skill.CustomNo=='1'}">selected</c:if> value="1">語文類</option>
               		<option <c:if test="${skill.CustomNo=='2'}">selected</c:if> value="2">資訊類</option>
               		<option <c:if test="${skill.CustomNo=='3'}">selected</c:if> value="3">專業類</option>
               		<option <c:if test="${skill.CustomNo=='4'}">selected</c:if> value="4">其他</option>
				</select>			
				</td>
				
				<td class="hairLineTdF" nowrap>
				<select name="ApplyType" style="font-size:18px;">
					<option <c:if test="${skill.ApplyType==''}">selected</c:if> value="">報名類型</option>
					<option <c:if test="${skill.ApplyType=='0'}">selected</c:if> value="0">單獨</option>
                    <option <c:if test="${skill.ApplyType=='1'}">selected</c:if> value="1">團體</option>
				</select>			
				</td>
				
				<td class="hairLineTdF" nowrap>
				<select name="Pass" style="font-size:18px;">
					<option <c:if test="${skill.Pass=='1'}">selected</c:if> value="1">畢業技能檢定合格</option>
					<option <c:if test="${skill.Pass=='0'}">selected</c:if> value="0">畢業技能檢定不合格</option>
				</select>			
				</td>
			</tr>
		</table>
		</td>
	</tr>
	
	
	<tr>
		<td class="fullColorTable" align="center">		
		<input type="submit" name="method" value="<bean:message key='Modify'/>" 
		id="Query" class="gSubmit" onMouseOver="showHelpMessage('依照以上條件修改', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">
		
		<input type="button" name="method" value="返回" id="Clear" class="gCancel" onMouseOver="showHelpMessage('返回', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)" onClick="location.href='/CIS/DeptAssistant/StdSkill.do';">
		</td>
	</tr>
</table>
		