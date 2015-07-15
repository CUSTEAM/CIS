<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%">
	<tr>
		<td>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">學年度</td>
				<td class="hairLineTdF">
				<input type="hidden" name="Oid" value=""/>
				<input type="text" name="school_year" size="3" value="${DraftManagerForm.map.school_year[0]}" 
				autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">招生學制</td>
				<td class="hairLineTdF">				
				<select name="SchoolNo">
					<option value="">所有學制</option>
					<c:forEach items="${schools}" var="s">
					<option <c:if test="${DraftManagerForm.map.SchoolNo[0]==s.idno}">selected</c:if> value="${s.idno}">${s.name}</option>
					</c:forEach>
				</select>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">招生科系</td>
				<td class="hairLineTdF">
				
				<select name="DeptNo">
					<option value="">所有科系</option>
					<c:forEach items="${depts}" var="d">
					<option <c:if test="${DraftManagerForm.map.DeptNo[0]==d.idno}">selected</c:if> value="${d.idno}">${d.name}</option>
					</c:forEach>
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
				<td class="hairLineTdF" nowrap>學校名稱</td>
				<td class="hairLineTdF" nowrap>
					<input type="text" name="schoolName" id="school_name" value="${DraftManagerForm.map.schoolName[0]}" onClick="clearSchool();"
					onkeyup="if(this.value.length>1)getAny(this.value, 'school_name', 'school_code', 'Recruitschool', 'name')" size="20"/>
					<input type="text" name="school_code" id="school_code" value="${DraftManagerForm.map.school_code[0]}"
					onkeyup="if(this.value.length>1)getAny(this.value, 'school_code', 'school_name', 'Recruitschool', 'no')" size="6"
					style="ime-mode:disabled" autocomplete="off" autocomplete="off" readonly/>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	<tr>
		<td>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">核定人數</td>
				<td class="hairLineTdF">
				<input type="text" name="standard" size="3" value="${DraftManagerForm.map.standard[0]}" 
				autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">錄取人數</td>
				<td class="hairLineTdF">
				<input type="text" name="enroll" size="3" value="${DraftManagerForm.map.enroll[0]}" 
				autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">報到人數</td>
				<td class="hairLineTdF">
				<input type="text" name="real" size="3" value="${DraftManagerForm.map.real[0]}" 
				autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>	
</table>
<script>
function clearSchool(){

	document.getElementById("school_code").value="";
	document.getElementById("school_name").value="";
}
</script>
<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>