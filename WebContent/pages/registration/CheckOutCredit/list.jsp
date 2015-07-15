<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<table class="hairLineTable" width="99%">
	<tr>
		
		<td class="hairLineTdF" align="left">
		<img src="images/printer.gif" border="0">

		<select name="reportType" id="reportType"
		onchange="jumpMenu('parent',this,0)" onMouseOver="showHelpMessage('列出本次查詢的全部學生', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">
			<option value="javascript:void(0)">查詢結果列表</option>			
			<option value="/CIS/Registration/List4CheckCredit?type=pass">全部符合標準的學生</option>
			<option value="/CIS/Registration/List4CheckCredit?type=not">全部不符標準的學生</option>
			<option value="/CIS/Registration/List4CheckCredit?type=book">全部不符標準的學生不及格明細</option>
		</select>		
		</td>
	</tr>
</table>

<table class="hairLineTable" width="99%">
	<tr>
		
		<td class="hairLineTdF">適用學制</td>
		<td class="hairLineTdF">適用科系</td>
		
		
		<td class="hairLineTdF">開始</td>
		<td class="hairLineTdF">結束</td>
		<td class="hairLineTdF">必修</td>
		<td class="hairLineTdF">選修</td>
		<td class="hairLineTdF">通識</td>
		
		
		
		<td class="hairLineTdF">建立者</td>
		<td class="hairLineTdF">建立日期</td>
		
	</tr>
<c:forEach items="${result}" var="r">
	<tr>
		
		<td class="hairLineTdF">
		<select name="SchoolNo" onChange="checkOid('Oid${r.Oid}', '${r.Oid}')">
			<c:forEach items="${AllSchools}" var="as">
			<option <c:if test="${r.SchoolNo==as.idno}">selected</c:if> value="${as.idno}">${as.name}</option>				
			</c:forEach>
		</select>
		</td>
		
		<td class="hairLineTdF">
		<select name="DeptNo" onChange="checkOid('Oid${r.Oid}', '${r.Oid}')">
			<c:forEach items="${AllDepts}" var="as">
			<option <c:if test="${r.DeptNo==as.idno}">selected</c:if> value="${as.idno}">${as.name}</option>				
			</c:forEach>
		</select>
		</td>
		
		
		
		
		<td class="hairLineTdF"><input type="text" name="start_year" size="2" value="${r.start_year}" onKeyDown="checkOid('Oid${r.Oid}', '${r.Oid}')" /></td>
		<td class="hairLineTdF"><input type="text" name="end_year" size="2" value="${r.end_year}" onKeyDown="checkOid('Oid${r.Oid}', '${r.Oid}')"/></td>
		
		<td class="hairLineTdF"><input type="text" name="opt1" size="2" value="${r.opt1}" onKeyDown="checkOid('Oid${r.Oid}', '${r.Oid}')"/></td>
		<td class="hairLineTdF"><input type="text" name="opt2" size="2" value="${r.opt2}" onKeyDown="checkOid('Oid${r.Oid}', '${r.Oid}')"/></td>
		<td class="hairLineTdF"><input type="text" name="opt3" size="2" value="${r.opt3}" onKeyDown="checkOid('Oid${r.Oid}', '${r.Oid}')"/></td>
		
		<td class="hairLineTdF">${r.editorName}</td>
		<td class="hairLineTdF">
		<fmt:formatDate pattern="yyyy-MM-dd" type="date" value="${r.editime}"/></td>
		
	</tr>
</c:forEach>
</table>
<table class="hairLineTable" width="99%">
	<tr>
		
		<td class="hairLineTdF" align="left">
		<img src="images/printer.gif" border="0">

		<select name="reportType" id="reportType"
		onchange="jumpMenu('parent',this,0)" onMouseOver="showHelpMessage('列出本次查詢的全部學生', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">
			<option value="javascript:void(0)">查詢結果列表</option>			
			<option value="/CIS/Registration/List4CheckCredit?type=pass">全部符合標準的學生</option>
			<option value="/CIS/Registration/List4CheckCredit?type=not">全部不符標準的學生</option>
			<option value="/CIS/Registration/List4CheckCredit?type=book">全部不符標準的學生被當明細</option>		
		</select>		
		</td>
	</tr>
</table>
<script>
function checkOid(id, value){

	document.getElementById(id).value=value;
}

function jumpMenu(targ,selObj,restore){
	eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'")
	eval(targ+".location.target='_blank'");
	if (restore) selObj.selectedIndex=0;
}
</script>