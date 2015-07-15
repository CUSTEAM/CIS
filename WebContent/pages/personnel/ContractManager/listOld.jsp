<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<table class="hairLineTable" width="100%" align="center">
	<tr>
		<td class="hairLineTdF" align="center"><font size="1"><img src="images/action_stop.gif"/></font></td>
		<td class="hairLineTdF" align="center"><font size="1">教師姓名</font></td>
		<td class="hairLineTdF" align="center"><font size="1">主聘系所</font></td>
		<td class="hairLineTdF" align="center"><font size="1">分類</font></td>
		<td class="hairLineTdF" align="center"><font size="1">職級</font></td>
		<td class="hairLineTdF" align="center"><font size="1">審定</font></td>
		<td class="hairLineTdF" align="center"><font size="1">證書/聘書字號</font></td>
		<td class="hairLineTdF" align="center"><font size="1">期間</font></td>
	</tr>
<c:forEach items="${oldContract}" var="o">
<tr>
	<td class="hairLineTdF" align="center"><input type="checkBox" onClick="checkCheckBox('delete${o.Oid}')" />
	<input type="hidden" name="delete" id="delete${o.Oid}"/>
	<input type="hidden" name="Oid" value="${o.Oid}"/></td>
	<!-- td class="hairLineTdF"><font size="1">${o.cname} - ${o.idno}</font></td-->
	<td class="hairLineTdF"><font size="1">${o.cname}</font></td>
	<td class="hairLineTdF" nowrap><font size="1">${o.name}</font></td>
	<td class="hairLineTdF">
	
	<select name="types" class="smallInput">				
	<c:forEach items="${type}" var="t">
		<option <c:if test="${o.type==t.id}">selected</c:if> value="${t.id}">${t.name}</option>
	</c:forEach>				
	</select>				
	
	</td>
	<td class="hairLineTdF">
	
	<select name="levels" class="smallInput">
	<c:forEach items="${level}" var="t">
		<option <c:if test="${o.level==t.id}">selected</c:if> value="${t.id}">${t.name}</option>
	</c:forEach>
	</select>
	
	</td>
	<td class="hairLineTdF">
	
	<select name="markups" class="smallInput">
	<c:forEach items="${markup}" var="t">
		<option <c:if test="${o.markup==t.id}">selected</c:if> value="${t.id}">${t.name}</option>
	</c:forEach>
	</select>
	
	</td>
	<td class="hairLineTdF"><input class="smallInput" type="text" size="10" name="license_nos" value="${o.license_no}" /><input 
	class="smallInput" type="text" size="16" name="contract_nos" value="${o.contract_no}" /></td>
	<td class="hairLineTdF">
	
	
	
	
	<input type="text" name="start_dates" id="start_dates${o.Oid}" size="12" value="${o.start_date}" class="smallInput"
	onclick="ds_sh(this);" autocomplete="off" style="ime-mode:disabled" autocomplete="off"><input 
	type="text" name="end_dates" id="end_date${o.Oid}" size="12" value="${o.end_date}" class="smallInput"
	onclick="ds_sh(this);" autocomplete="off" style="ime-mode:disabled" autocomplete="off"></td>
		
		
	</tr>
</c:forEach>	
</table>

<table width="100%" class="hairLineTable">
	<tr>
		<td class="hairLineTdF">
		
		<table>
			<tr>
				<td width="1"><img src="images/ico_file_word.png" border="0"/></td><td><a target="_blank" href="/CIS/pages/course/export/contract4teacher.jsp">聘書列印</a></td>
				<td width="1"><img src="images/ico_file_excel.png" border="0"/></td><td><a target="_blank" href="/CIS/pages/course/export/contract4teacher.jsp">續聘名冊列印</a></td>
				<td width="1"><img src="images/ico_file_word.png" border="0"/></td><td><a target="_blank" href="/CIS/Contract4TeacherPOI">聘書列印POI</a></td>
			</tr>
		</table>
		
		</td>
	</tr>
</table>
<script>
function checkCheckBox(id){
	if(document.getElementById(id).value==""){
		document.getElementById(id).value="*";
	}else{
		document.getElementById(id).value="";
	}
}
</script>