<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="SysAdmin/PortfolioManager/TableManager" method="post" onsubmit="init('系統處理中...')">

	<tr>
		<td class="fullColorTable" width="100%">
			<table width="100%" cellpadding="0" cellspacing="0" border="0">
				<tr height="30">
					<td width="28" align="right">
						<img src="images/icon/table_relationship.gif">
					</td>
					<td align="left">
						&nbsp;學生對應項目管理&nbsp;
					</td>
				</tr>
			</table>
		</td>
	</tr>	
	<tr>
		<td>		
		<%@ include file="TableManager/add.jsp"%>
		<%@ include file="TableManager/edit.jsp"%>		
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">
		
		
		</td>
	</tr>
</html:form>
</table>

<script>
function addField(tableOid){
	//alert(tableOid);
	//alert(fieldOid);
	//document.getElementById("addFieldOid").value=fieldOid;
	document.getElementById("addTableOid").value=tableOid;
	document.getElementById("addField"+tableOid).innerHTML=document.getElementById("addField"+tableOid).innerHTML+document.getElementById("fieldContent").innerHTML;
}
</script>

<div id="fieldContent" style="display:none;">
<table class="hairLineTable" width="99%" id="field${a.Oid}">
	<tr>
		<td class="hairLineTd" width="100%">		
		<input type="text" name="fieldNames" style="width:100%;"/>		
		</td>
		
		<td class="hairLineTd" width="1">		
		<select name="type">
			<c:forEach items="${format}" var="ff">
			<option value="${ff.Oid}">${ff.name}</option>
			</c:forEach>
		</select>		
		</td>		
		
		<td class="hairLineTdF" width="50" align="right" nowrap>長度</td>
		<td class="hairLineTd" width="1">		
		<input type="text" name="size" size="2"/>
		<input type="hidden" name="tableOid" id="addTableOid" value="" size="2" />
		<input type="hidden" name="fieldOid" id="addFieldOid" size="2" value=""/><!-- 欄位 -->		
		</td>
		
		<td class="hairLineTdF" width="70" align="right" nowrap>欄位順序</td>
		<td class="hairLineTd" width="1">		
		<input type="text" name="fieldSeq" size="2" />		
		</td>
	</tr>		
</table>
</div>