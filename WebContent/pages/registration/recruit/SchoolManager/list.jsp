<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">學校名稱</td>
		<td class="hairLineTdF">學校地址</td>
		<td class="hairLineTdF">連絡人(可複數)</td>
		<td class="hairLineTdF">連絡電話</td>
		<td class="hairLineTdF">學校網址</td>
	</tr>
	
	<c:forEach items="${school}" var="school">
	<tr>
		<td class="hairLineTdF" nowrap valign="top"><font size="-2">${school.no}</font> - ${school.name}</td>
		<td class="hairLineTdF" nowrap valign="top">
			<input type="hidden" name="Oid" id="Oid${school.Oid}" value="" onClick="checkOid('${school.Oid}')"/>
			<input type="text" name="zip" value="${school.zip}" size="5" class="smallInput" onClick="checkOid('${school.Oid}')"/>
			<input type="text" name="address" value="${school.address}" class="smallInput" onClick="checkOid('${school.Oid}')"/>
		</td>
		<td nowrap class="hairLineTdF" valign="top">
		<textarea name="connector" id="connector${school.Oid}" class="smallTextarea" onClick="changeText(this.id);" onClick="changeText(this.id)">${school.connector}</textarea>
		
		</td>
		<td nowrap class="hairLineTdF" valign="top"><input type="text" name="tel" size="14" value="${school.tel}" class="smallInput" onClick="checkOid('${school.Oid}')"/></td>
		<td nowrap class="hairLineTdF" valign="top"><input type="text" name="url" size="14" value="${school.url}" class="smallInput" onClick="checkOid('${school.Oid}')"/></td>
	</tr>
	</c:forEach>
</table>
<script>
function checkOid(id){
	document.getElementById("Oid"+id).value=id;
}

function changeText(id){
	if(document.getElementById(id).className=="longTextarea"){
		document.getElementById(id).className="smallTextarea";
	}else{
		document.getElementById(id).className="longTextarea";
	}
	
}
</script>