<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html:form action="Portfolio/FreePortfolioManager" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">

<c:if test="${tag!='newTag'}">
<c:forEach items="${free}" var="f">
<table width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" nowrap>
				選擇分類
				</td>
				<td class="hairLineTdF" width="100%">
				<input type="hidden" name="Oid" value="${f.Oid}" />				
				<select name="tag" onChange="showNewTag('tag${f.Oid}', 'newTag${f.Oid}')" id="tag${f.Oid}">
				<option value="">請選擇分類</option>
				<c:forEach items="${freeTag}" var="t">				
				<option <c:if test="${f.tag==t.tag}">selected</c:if> value="${t.tag}">${t.tag}</option>
				</c:forEach>
				<option value="">建立新分類</option>
				</select>
				<input type="text" name="newTag" id="newTag${f.Oid}" style="display:none;" />
				</td>
			</tr>
			<tr>
				<td class="hairLineTdF" nowrap>
				歷程名稱
				</td>
				<td class="hairLineTdF" width="100%">
				<input type="text" name="title" value="${f.title}" style="width:100%;"/>
				</td>
			</tr>
		
			<tr>
				<td class="hairLineTdF" colspan="2">
				<textarea name="content" rows="1" style="width:100%;" onClick="this.rows=20">${f.content}</textarea>				
				</td>
			</tr>
			
			
			
		</table>		
		
		</td>
	</tr>
</table>
</c:forEach>

<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" align="right">
		<INPUT type="submit" name="method" value="<bean:message key='Update'/>" class="gSubmit">
		</td>
	</tr>
</table>
</c:if>

<c:if test="${tag=='newTag'}">
<table width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" nowrap>
				選擇分類
				</td>
				<td class="hairLineTdF" width="100%">

				<select name="tag" id="tag" onChange="showNewTag('tag', 'newTag')">
				<option value="">請選擇分類</option>			
				<c:forEach items="${freeTag}" var="t">				
				<option <c:if test="${f.tag==t.tag}">selected</c:if> value="${t.tag}">${t.tag}</option>
				</c:forEach>
				<option value="">建立新分類</option>
				</select>
				<input type="text" name="newTag" id="newTag" style="display:none;" />
				</td>
			</tr>
			<tr>
				<td class="hairLineTdF" nowrap>
				歷程名稱
				</td>
				<td class="hairLineTdF" width="100%">
				<input type="text" name="title" value="${f.title}" style="width:100%;" />
				</td>
			</tr>
			<tr>
				<td class="hairLineTdF" colspan="2">
				<textarea name="content" rows="1" style="width:100%;" onClick="this.rows=20">${f.content}</textarea>				
				</td>
			</tr>
			
		</table>		
		
		</td>
	</tr>
</table>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" align="right">
		<INPUT type="submit" name="method" value="<bean:message key='Create'/>" class="gSubmit">
		</td>
	</tr>
</table>
</c:if>

</html:form>
<script>
function showNewTag(id, id1){

	if(document.getElementById(id).value==""){
		document.getElementById(id1).style.display="inline";	
	}else{
		document.getElementById(id1).style.display="none";	
	}
}
</script>