<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="99%" class="hairLineTable">
	<tr>
		<td class="hairLineTdF" width="30" align="center"><input type="checkbox" disabled /></td>
		<td class="hairLineTdF">活動名稱</td>
		<td class="hairLineTdF">教師編號</td>
		<td class="hairLineTdF">教師姓名</td>
	</tr>
	<c:forEach items="${evaluators}" var="v">
	<tr>
		<td class="hairLineTdF" align="center">
		<input type="checkbox" onClick="del('Oid${v.Oid}','${v.Oid}');" />
		<input type="hidden" name="Oid" id="Oid${v.Oid}"/>
		</td>
		<td class="hairLineTdF">${v.name}</td>
		<td class="hairLineTdF">${v.idno}</td>
		<td class="hairLineTdF">${v.cname}</td>
	</tr>		
	</c:forEach>			
</table>
<script>
function del(id, value){	
	if(document.getElementById(id).value==""){
		document.getElementById(id).value=value;
	}else{
		document.getElementById(id).value="";
	}
}
</script>