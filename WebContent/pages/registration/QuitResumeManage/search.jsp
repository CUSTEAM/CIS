<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%">
	<tr>
		<td>
		
		<table class="hairlineTable" align="left">
			<tr>
				<td class="hairlineTdF" align="center" nowrap>辦理項目</td>
				<td class="hairlineTdF">
				
				<select name="occur_type">
					<option <c:if test="${QuitResumeManageForm.map.occur_type==''}">selected</c:if> value="">全部</option>
					<option <c:if test="${QuitResumeManageForm.map.occur_type=='occur'}">selected</c:if> value="occur">休學</option>
					<option <c:if test="${QuitResumeManageForm.map.occur_type=='recov'}">selected</c:if> value="recov">復學</option>
				</select>
				
				</td>
			</tr>
		</table>
		
		
		
		<table class="hairlineTable" align="left">
			<tr>
				<td class="hairlineTdF" align="center">辦理學年</td>
				<td class="hairlineTdF">
				<input type="text" name="year" size="2" value="${QuitResumeManageForm.map.year}"/>
				</td>
			</tr>
		</table>
		
		
		<table class="hairlineTable" align="left">
			<tr>
				<td class="hairlineTdF" align="center">辦理學期</td>
				<td class="hairlineTdF">
				<select name="term">
					<option <c:if test="${QuitResumeManageForm.map.term==''}">selected</c:if> value="">全部</option>
					<option <c:if test="${QuitResumeManageForm.map.term=='1'}">selected</c:if> value="1">上學期</option>
					<option <c:if test="${QuitResumeManageForm.map.term=='2'}">selected</c:if> value="2">下學期</option>
				</select>
				
				
				</td>
			</tr>
		</table>		
		
		</td>
	</tr>
	
	<tr>
		<td>
		
		<table class="hairLineTable" align="left">
			<tr>
			<td class="hairLineTdF">以班級查詢</td>
			<td class="hairLineTd" nowrap>
				<input type="text" id="classNo" name="classLess" size="6" 
				autocomplete="off" style="ime-mode:disabled" autocomplete="off"
	 			value="${QuitResumeManageForm.map.classLess}" 
	 			onkeyup="if(this.value.length>2)getAny(this.value, 'classNo', 'className', 'Class', 'no')"
	 			onclick="this.value='', document.getElementById('className').value=''"/><input 
	 			type="text" name="className" id="className"
	 			value="${QuitResumeManageForm.map.className}" 
	 			size="12" onkeyup="getAny(this.value, 'className', 'classNo', 'Class', 'name')"
	 			onclick="this.value='', document.getElementById('classNo').value=''"/>
				</td>
				<td class="hairLineTdF" width="30" align="center">
				<img src="images/icon/icon_info.gif" />
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	<tr>
		<td>
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">以學號查詢</td>
				<td class="hairLineTd">
				<input type="text" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
				name="student_no" id="studentNo" size="12" value="${QuitResumeManageForm.map.student_no}"
				onkeyup="if(this.value.length>=2)GgetAny(this.value, 'studentNo', 'studentName', 'gstmd', 'no')" 
				onMouseOver="showHelpMessage('學號輸入這邊, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 以便自動完成姓名', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)" 
				onClick="clearQuery()" /><input onMouseOver="showHelpMessage('姓名輸入這邊, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 以便自動完成學號', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)" 
				type="text" name="student_name" id="studentName" size="10" value="${QuitResumeManageForm.map.student_name}"
	 			onkeyup="if(this.value.length>1)GgetAny(this.value, 'studentName', 'studentNo', 'gstmd', 'name')" onClick="clearQuery()" />
				</td>
				<td class="hairLineTdF" width="30" align="center">
				<img src="images/icon/icon_info.gif" />
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	
</table>
