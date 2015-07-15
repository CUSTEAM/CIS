<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%">
	<tr>
		<td>
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">學年度</td>
				<td class="hairLineTdF">
				
				<input type="text" name="school_year" value="" size="3"/>
				</select>
				
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
					<option <c:if test="${RecruitQueryForm.map.SchoolNo==s.idno}">selected</c:if> value="${s.idno}">${s.name}</option>
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
					<option <c:if test="${RecruitQueryForm.map.DeptNo==d.idno}">selected</c:if> value="${d.idno}">${d.name}</option>
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
					<input type="text" name="schoolName" id="school_name" value="${RecruitQueryForm.map.schoolName}" onClick="clearSchool();"
					onkeyup="if(this.value.length>1)getAny(this.value, 'school_name', 'school_code', 'Recruitschool', 'name')" size="20"/>
					<input type="text" name="school_code" id="school_code" value="${RecruitQueryForm.map.school_code}"
					onkeyup="if(this.value.length>1)getAny(this.value, 'school_code', 'school_name', 'Recruitschool', 'no')" size="6"
					style="ime-mode:disabled" autocomplete="off" autocomplete="off" readonly/>
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