<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>		
<table class="hairlineTable" width="99%">
	<tr>
		<td class="hairlineTdF" nowrap>學號</td>
		<td class="hairlineTdF" nowrap>		
		
		<input type="text" name="student_no" id="student_no" 
		autocomplete="off" size="10"
		value="${ScoreManagerForm.map.student_no}" onClick="this.value=''" style="ime-mode:disabled; font-size:18px;"
		onkeyup="if(this.value.length>=2)GgetAny(this.value, 'student_no', 'student_name', 'stmd', 'no')" />		
		
		<input type="text" autocomplete="off" name="student_name" id="student_name"
		style="font-size:18px;" size="6" readonly
		value="${ScoreManagerForm.map.student_name}" />
		</td>
		<td class="hairLineTdF" width="100%">				
		<input type="submit" name="method" value="<bean:message key='Query'/>" 
		id="Query" class="gSubmitSmall" onMouseOver="showHelpMessage('建立成績不需查詢', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)"/> <font size="-1">查詢學生歷年成績</font>
		</td>		
	</tr>
</table>
		
<table class="hairlineTable" width="99%">
	<tr>
		<td class="hairLineTdF" nowrap>學年</td>
		<td class="hairLineTdF"><input type="hidden" name="Oid" />
		<!--<input autocomplete="off" autocomplete="off" style="ime-mode:disabled; font-size:18px;" 
		size="3" type="text" name="school_year" value="${s.school_year}" maxlength="3"/>-->
		<input autocomplete="off" autocomplete="off" style="ime-mode:disabled; font-size:18px;" 
		size="3" type="text" name="school_year" value="${ScoreManagerForm.map.school_year[0]}" maxlength="3"/>
		</td>
		<td class="hairLineTdF" nowrap>學期</td>
		<td class="hairLineTdF">
		<select name="school_term" style="font-size:18px;">
		<!--<option <c:if test="${s.school_term=='1'}">selected</c:if> value="1">1</option>
			<option <c:if test="${s.school_term=='2'}">selected</c:if> value="2">2</option>-->
			<option <c:if test="${ScoreManagerForm.map.school_term[0]=='1'}">selected</c:if> value="1">1</option>
			<option <c:if test="${ScoreManagerForm.map.school_term[0]=='2'}">selected</c:if> value="2">2</option>
		</select>
		</td>
		<td class="hairLineTdF" width="100%"></td>
	</tr>
</table>
		
<table class="hairlineTable" width="99%">
	<tr>		
		<td class="hairLineTdF" nowrap>課程代碼</td>
		<td class="hairLineTdF" nowrap>
		<input type="text" name="cscode" id="cscodeS" size="8"
		autocomplete="off" style="ime-mode:disabled" autocomplete="off"
		value="${ScoreManagerForm.map.cscode[0]}"
		onkeyup="if(this.value.length>2)getAny(this.value, 'cscodeS', 'csnameS', 'Csno', 'no')"
		onclick="this.value='', courseName.value=''"
		onFocus="chInput(this.id)"
		onMouseOver="showHelpMessage('請輸入科目代碼, 如: 50000', 'inline', this.id)"
		onMouseOut="showHelpMessage('', 'none', this.id)" />
		
		<input type="text" autocomplete="off" name="chi_name"
		id="csnameS" size="16" value="${ScoreManagerForm.map.chi_name[0]}"
		onFocus="chInput(this.id)"
		onMouseOver="showHelpMessage('請輸入課程名稱, 如: 國文, 若用貼上請動一下方向鍵令代碼自動完成', 'inline', this.id)"
		onMouseOut="showHelpMessage('', 'none', this.id)"
		onkeyup="getAny(this.value, 'csnameS', 'cscodeS', 'Csno', 'name')"
		onclick="this.value='', courseNumber.value=''" />
		</td>
		<td class="hairLineTdF" width="100%" align="center">
		
		</td>
	</tr>
</table>

<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" nowrap>班級代碼</td>
		<td class="hairLineTdF" nowrap>
		<input type="text" id="classNo" name="stdepart_class" size="6" 
		autocomplete="off" style="ime-mode:disabled" autocomplete="off"
		value="${ScoreManagerForm.map.stdepart_class[0]}" onFocus="chInput(this.id)"
		onkeyup="if(this.value.length>2)getAny(this.value, 'classNo', 'className', 'Class', 'no')"
		onclick="this.value='', document.getElementById('className').value=''"/>
		
		<input 
		type="text" name="className" id="className"
		value="${ScoreManagerForm.map.className[0]}" onFocus="chInput(this.id)"
		size="12" onkeyup="getAny(this.value, 'className', 'classNo', 'Class', 'name')"
		onclick="this.value='', document.getElementById('classNo').value=''"/>
		</td>
		<td class="hairLineTdF" width="100%" align="center">
		
		</td>
	</tr>
</table>
		
<table class="hairlineTable" width="99%">
	<tr>		
		
		<td class="hairLineTdF">
		<select style="font-size:18px" name="opt">			
			<!--<option <c:if test="${s.opt=='1'}">selected</c:if> value="1">必修</option>
			<option <c:if test="${s.opt=='2'}">selected</c:if> value="2">選修</option>
			<option <c:if test="${s.opt=='3'}">selected</c:if> value="3">通識</option>-->
			<option <c:if test="${ScoreManagerForm.map.opt[0]=='1'}">selected</c:if> value="1">必修</option>
			<option <c:if test="${ScoreManagerForm.map.opt[0]=='2'}">selected</c:if> value="2">選修</option>
			<option <c:if test="${ScoreManagerForm.map.opt[0]=='3'}">selected</c:if> value="3">通識</option>
		</select>
		</td>	
		<td class="hairLineTdF" nowrap>學分</td>
		<td class="hairLineTdF">
		<!--<input autocomplete="off" autocomplete="off" style="ime-mode:disabled; font-size:18px;" 
		size="1" type="text" name="credit" value="${s.credit}"/>-->
		<input autocomplete="off" autocomplete="off" style="ime-mode:disabled; font-size:18px;" 
		size="1" type="text" name="credit" value="${ScoreManagerForm.map.credit[0]}"/>
		</td>
		<td class="hairLineTdF">
		<select style="font-size:18px" name="evgr_type">
			<c:forEach items="${CourseType}" var="c">
			<!--<option <c:if test="${s.evgr_type==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>-->
			<option <c:if test="${ScoreManagerForm.map.evgr_type[0]==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
			</c:forEach>
		</select>
		</td>
		<td class="hairLineTdF" nowrap>學期成績</td>
		<td class="hairLineTdF" nowrap>
		<input autocomplete="off" autocomplete="off" style="ime-mode:disabled; font-size:18px;" 
		size="3" type="text" name="score" value="${ScoreManagerForm.map.score[0]}" maxlength="3"/>		
		
		
		</td>
		<td class="hairLineTdF" width="100%" align="center">
		
		</td>
	</tr>
</table>	