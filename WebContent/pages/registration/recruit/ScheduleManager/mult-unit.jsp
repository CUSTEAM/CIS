<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:forEach items="${multUnits}" var="u" varStatus="s">
<table class="hairLineTable" width="99%" >
	<tr>
		
		<c:if test="${s.index==0}"><td class="hairLineTdF" nowrap width="80">主辦單位</td></c:if>
		<c:if test="${s.index>0}"><td class="hairLineTdF" nowrap width="80">協辦單位</td></c:if>
		
		
		
		<td class="hairLineTdF">
			<select name="unit" id="unit${u.Oid}${u.id}">
				<option value="">刪除此系所或學院</option>
				<c:forEach items="${depts}" var="d">
					<option <c:if test="${u.idno==d.idno}">selected</c:if> value="${d.idno}">${d.name}</option>
				</c:forEach>
			</select>
			<input type="button" value="清除" onClick="delUnit('${u.Oid}${u.id}');" 
			class="gCancelSmall" id="table${u.Oid}" onMouseOver="showHelpMessage('清除後點擊最下面儲存 ','inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"/>
		</td>
	</tr>
</table>

</c:forEach>
<script>
function delUnit(id){
	document.getElementById('unit'+id).value="";	
}

function addUnit(){
	document.getElementById("addUnit").innerHTML=document.getElementById("addUnit").innerHTML+document.getElementById("aUnit").innerHTML;
}
</script>


<table class="hairLineTable" width="99%" >
	<tr>
		<td class="hairLineTdF" nowrap width="80">新增單位</td>
		<td class="hairLineTdF">
			<select name="unit" id="unit${u.Oid}${u.id}" onChange="addUnit();">
				<option value="">選擇新系所或學院</option>
				<c:forEach items="${depts}" var="d">
					<option value="${d.idno}">${d.name}</option>
				</c:forEach>
			</select>
			
		</td>
	</tr>
</table>

<div id="addUnit">

</div>

<div id="aUnit" style="display:none;">
<table class="hairLineTable" width="99%" >
	<tr>
		<td class="hairLineTdF" nowrap width="80">新增單位</td>
		<td class="hairLineTdF">
			<select name="unit" id="unit${u.Oid}${u.id}" onChange="addUnit();">
				<option value="">選擇新系所或學院</option>
				<c:forEach items="${depts}" var="d">
					<option value="${d.idno}">${d.name}</option>
				</c:forEach>
			</select>
			
		</td>
	</tr>
</table>
</div>