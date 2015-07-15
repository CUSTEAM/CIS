<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:forEach items="${myGroup}" var="m">
<table width="100%">
	<tr>
		<td>		
		<table class="hairLineTable" width="99%">
			<tr>
				
				<td class="hairLineTdF" align="center" nowrap>群組名稱</td>
				<td class="hairLineTdF" nowrap>
				<table cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td width="100%"><input type="text" name="name" value="${m.name}" style="width:100%" /></td>
						<td>&nbsp;</td>
						<td nowrap>
						<input type="submit" name="method"value="<bean:message key='Modify'/>" onClick="checkSelect('${m.Oid}')" class="gSubmitlSmall">
						<input type="submit" name="method"value="<bean:message key='Delete'/>" onClick="checkSelect('${m.Oid}')" class="gCancelSmall">						
						</td>
					</tr>
				</table>
				
				</td>
			</tr>
			<tr>
				
				<td class="hairLineTdF" align="center" nowrap>群組人員</td>
				<td class="hairLineTdF" width="100%">
				<textarea name="members" style="width:100%" onClick="this.rows=10;" rows="1">${m.members}</textarea>				
				<input type="hidden" id="delOid${m.Oid}" name="delOid" value=""/>
				</td>
				
			</tr>
		</table>
		</td>
	</tr>
</table>
</c:forEach>
<script>
function checkSelect(id){
	document.getElementById("delOid"+id).value=id;
}
</script>