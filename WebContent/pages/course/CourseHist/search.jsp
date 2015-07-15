<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairlineTable">
	<tr>
		<td class="hairlineTdF">學年</td>
		<td class="hairlineTdF">
		
		<select name="school_year" style="font-size:18px;">
			<c:forEach items="${allyear}" var="y" >
			<option <c:if test="${CourseHistForm.map.school_year==y.school_year}">selected</c:if> value="${y.school_year}">${y.school_year}學年</option>
			</c:forEach>
		</select>
		
		</td>
		<td class="hairlineTdF">
		<select name="school_term" style="font-size:18px;">
			<option <c:if test="${CourseHistForm.map.school_term=='1'}">selected</c:if> value="1">第1學期</option>
			<option <c:if test="${CourseHistForm.map.school_term=='2'}">selected</c:if> value="2">第2學期</option>
		</select>
		</td>
		
	</tr>
</table>

<table class="hairlineTable">
	<tr>
		<td class="hairlineTdF">班級代碼</td>
		<td class="hairlineTdF">
		<input type="text" id="depart_class" name="depart_class" style="font-size:18;"
	 	size="8" autocomplete="off" class="upInput" style="ime-mode:disabled" autocomplete="off" value="${CourseHistForm.map.depart_class}" 
		onkeyup="if(this.value.length>2)getAny(this.value, 'depart_class', 'className', 'Class', 'no')" onClick="this.value='', document.getElementById('className').value='';"/>
		<input type="text" name="ClassName" value="${CourseHistForm.map.ClassName}" style="font-size:18;" class="upInput" id="className"/>
		</td>
	</tr>
</table>

<table class="hairlineTable">
	<tr>
		<td class="hairlineTdF">課程代碼</td>
		<td class="hairlineTdF">
		<input type="text" name="cscode" id=""cscode"" size="8" class="upInput"
		autocomplete="off" style="ime-mode:disabled" autocomplete="off"
		value="${CourseHistForm.map.cscode}" onClick="this.value='',document.getElementById('chi_name').value='';"
		onkeyup="if(this.value.length>2)getAny(this.value, 'cscode', 'chi_name', 'Csno', 'no')"/>
		
		<input type="text" autocomplete="off" name="chi_name" id="chi_name" size="16" value="${CourseHistForm.map.chi_name}"
		class="upInput" onkeyup="getAny(this.value, 'chi_name', 'cscode', 'Csno', 'name')" onclick="this.value='', document.getElementById('cscode').value='';" />
		</td>
	</tr>
</table>

<table class="hairlineTable">
	<tr>
		<td class="hairlineTdF">教師代碼</td>
		<td class="hairlineTd">
			<input type="text" name="techid" id="techid" size="8" class="upInput" onClick="this.value='', document.getElementById('cname').value='';"
			style="ime-mode:disabled" autocomplete="off" value="${CourseHistForm.map.techid}"
			onkeyup="if(this.value.length>2)getAny(this.value, 'techid', 'cname', 'empl', 'no')" />
			
			<input type="text" onkeyup="getAny(this.value, 'cname', 'techid', 'empl', 'name')" size="4"
			onClick="this.value='', document.getElementById('techid').value='';"
			autocomplete="off" name="cname" id="cname" value="${CourseHistForm.map.cname}" class="upInput"/>
		</td>
	</tr>
</table>

<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %> 