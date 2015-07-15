<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable">
	<tr>
		<td class="hairLineTdF">適用系所</td>
		<td class="hairLineTd">
		<select name="deptNo">
			<c:forEach items="${DeptList}" var="d">
			<option <c:if test="${CsCoreForm.map.deptNo==d.idno}">selected</c:if> value="${d.idno}">${d.name}</option>		
			</c:forEach>		
		</select>
		</td>
	</tr>
</table>

<table class="hairlineTable">
	<tr>
		<td class="hairlineTdF">課程名稱</td>
		<td class="hairlineTd">
		
		<input type="text" autocomplete="off"
		name="courseName" id="csnameS" size="16"
		value="${CsCoreForm.map.courseName}" onFocus="chInput(this.id)"
		onMouseOver="showHelpMessage('請輸入課程名稱, 如: 國文, 若用貼上請動一下方向鍵令代碼自動完成', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)"
		onkeyup="getAny(this.value, 'csnameS', 'cscodeS', 'Csno', 'name')"
		onclick="this.value='', courseNumber.value=''"/>
		
		<input type="text" name="courseNumber" id="cscodeS" size="8"
		autocomplete="off" style="ime-mode:disabled" autocomplete="off"
		value="${CsCoreForm.map.courseNumber}"
		onkeyup="if(this.value.length>2)getAny(this.value, 'cscodeS', 'csnameS', 'Csno', 'no')"
		onclick="this.value='', courseName.value=''" onFocus="chInput(this.id)"
		onMouseOver="showHelpMessage('請輸入科目代碼, 如: 50000', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)"/>
		
		
		
		
		</td>
		<td width="30" align="center" class="hairlineTdF">				
		<img src="images/16-exc-mark.gif" />
		</td>
	</tr>
</table>