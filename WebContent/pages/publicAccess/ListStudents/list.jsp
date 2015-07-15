<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="99%" class="hairLineTable">
		
	<tr>
		<td class="hairLineTdF" nowrap>型態</td>
		<td class="hairLineTdF">校區</td>
		<td class="hairLineTdF">部制</td>
		<td class="hairLineTdF">科系</td>
		<td class="hairLineTdF">班級名稱</td>	
		<td class="hairLineTdF">男</td>
		<td class="hairLineTdF">女</td>	
		<td class="hairLineTdF">人數</td>
		<td class="hairLineTdF">資訊</td>
	</tr>	
	
	
	<c:forEach items="${classes}" var="c">
	<tr>
		<td class="hairLineTdF" width="1" nowrap>		
		<select name="Types" disabled style="font-size:18px;">
			<option <c:if test="${c.Type=='P'}">selected</c:if> value="P">實體班級</option>
			<option <c:if test="${c.Type=='V'}">selected</c:if> value="V">虛擬班級</option>
			<option <c:if test="${c.Type=='E'}">selected</c:if> value="E">延修班級</option>
			<option <c:if test="${c.Type=='O'}">selected</c:if> value="O">廢止班級</option>
		</select>		
		</td>
		<td class="hairLineTdF">${c.CampusName}</td>
		<td class="hairLineTdF">${c.SchoolTypeName}</td>
		<td class="hairLineTdF">${c.DeptName}</td>
		<td class="hairLineTdF">${c.depart_class}, ${c.ClassName}</td>
		
		<td class="hairLineTdF">${c.m}</td>
		<td class="hairLineTdF">${c.cnt-c.m}</td>
		<td class="hairLineTdF">${c.cnt}</td>
		<td class="hairLineTdF">課表|教師|學生</td>
	</tr>
	
	</c:forEach>
	</table>
<script>
function checkSel(id){
	document.getElementById(id).value="*";
}
</script>