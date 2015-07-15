<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="Portfolio/MySkill" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
	
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/mark.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">專長資料管理</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		<table width="99%" class="hairLineTable">
			<tr>
				<td width="30" class="hairLineTdF" align="center"><img src="images/icon/icon_info.gif" /></td>
				<td class="hairLineTdF"><font class="gray_15">點擊分類展開子分類，勾選後點擊下方儲存即可。</font></td>
			</tr>
		</table>
		<c:forEach items="${list}" var="l">
		<table width="99%" class="hairLineTable" style="cursor:pointer;" onClick="showObj('s${l.Oid}')">
			<tr>
				<td class="hairLineTdF">
				<font class="gray_15">${l.name}</font>
				</td>
			</tr>
		</table>
		
		<table width="99%" class="hairLineTable" <c:if test="${l.someone<1}">style="display:none;"</c:if> id="s${l.Oid}">
			<c:forEach items="${l.skill1}" var="l1">
			<tr>
				<td width="30" class="hairLineTdF" align="center" nowrap>				
				<c:if test="${l1.directory=='0'}"><input type="checkBox" onClick="checkedBox('skillOid${l1.Oid}', '${l1.Oid}')" <c:if test="${l1.igot=='1'}">checked</c:if>/></c:if>
				<input type="hidden" name="skillOid" id="skillOid${l1.Oid}" value="<c:if test="${l1.igot=='1'}">${l1.Oid}</c:if>" />
				</td>
				
				
				
				<td class="hairLineTdF">
				<select <option <c:if test="${l1.level==null}">style="display:none;"</c:if> name="level" id="skillOid${l1.Oid}x">
					<option <c:if test="${l1.level=='3'}">selected</c:if> value="3">精通</option>
					<option <c:if test="${l1.level=='2'}">selected</c:if> value="2">熟練</option>
					<option <c:if test="${l1.level=='1'}">selected</c:if> value="1">略懂</option>
				</select>
				${l1.name}
				</td>
			</tr>
			
			</c:forEach>			
		</table>
		</c:forEach>		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		<input type="submit" name="method" 
					value="<bean:message key='Save'/>" 
					id="Query" class="gSubmit"
					onMouseOver="showHelpMessage('儲存以上資料', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
		</td>
	</tr>
</html:form>

<script>
function checkedBox(id, value){
	if(document.getElementById(id).value==""){
		document.getElementById(id).value=value;
	}else{
		document.getElementById(id).value="";
	}
	
	if(document.getElementById(id+"x").style.display=="none"){
		document.getElementById(id+"x").style.display="inline";
	}else{
		document.getElementById(id+"x").style.display="none";
	}
	
}

</script>

</table>