<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>

<table width="100" cellspacing="0" cellpadding="0">
<tr>
<td nowrap>


<table class="hairLineTable" align="left">
	<tr>
		<td class="hairLineTdF" nowrap>開頭文字</td>
		<td class="hairLineTd" nowrap><input type="text" name="titleText" value="中華聘字"/></td>
	</tr>
</table>

<table class="hairLineTable" align="left">
	<tr>
		<td class="hairLineTdF" nowrap>證號範圍</td>
		<td class="hairLineTd" nowrap>
		<input type="text" name="contract_startNo" value="${ContractManagerForm.map.contract_startNo}"/>
		<input type="text" name="contract_endNo" value="${ContractManagerForm.map.contract_endNo}"/>		
		</td>
		<td class="hairLineTdF" nowrap>		
		<INPUT type="submit"
				   name="method" id="editContract"
				   onMouseOver="showHelpMessage('立即建立聘書，勾選者將不建立聘書', 'inline', this.id)" 
			 	   onMouseOut="showHelpMessage('', 'none', this.id)"
				   value="<bean:message key='AddContract' bundle="PSN"/>"
				   class="CourseButton" />
		</td>
	</tr>
</table>






</td>
</tr>
</table>




<table class="hairLineTable" width="100%" align="center">
	<tr>
		<td class="hairLineTdF" align="center"><font size="1"><img src="images/action_stop.gif"/></font></td>
		<td class="hairLineTdF" align="center"><font size="1">教師姓名</font></td>
		<td class="hairLineTdF" align="center"><font size="1">主聘系所</font></td>
		<td class="hairLineTdF" align="center"><font size="1">分類</font></td>
		<td class="hairLineTdF" align="center"><font size="1">職級</font></td>
		<td class="hairLineTdF" align="center"><font size="1">審定</font></td>
		<td class="hairLineTdF" align="center"><font size="1">證書號</font></td>
		<td class="hairLineTdF" align="center"><font size="1">期間</font></td>
	</tr>
<c:forEach items="${newContract}" var="o">
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
	<td class="hairLineTdF"><input class="smallInput" type="text" size="10" name="license_nos" value="${o.license_no}" /></td>
	<td class="hairLineTdF">
	
	
	
	
	<input type="text" name="start_dates" id="start_dates${o.Oid}" size="12" value="${o.start_date}" class="smallInput"
	onclick="ds_sh(this);" autocomplete="off" style="ime-mode:disabled" autocomplete="off"><input 
	type="text" name="end_date" id="end_date${o.Oid}" size="12" value="${o.end_date}" class="smallInput"
	onclick="ds_sh(this);" autocomplete="off" style="ime-mode:disabled" autocomplete="off"></td>
		
		
	</tr>
</c:forEach>	
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