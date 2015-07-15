<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Registration/EntrnoManager" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/table_relationship.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">入學文號管理</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		<table class="hairLineTable" width="99%">
			<tr>
				
				<td class="hairLineTdF" nowrap width="100">文號</td>
				<td class="hairLineTdF" nowrap><input type="text" style="width:100%" name="permission_no" value="${EntrnoManagerForm.map.permission_no}"/></td>
				<td width="30" align="center" class="hairlineTdF">
				 <input type="submit" name="method" 
					value="<bean:message key='Search'/>" 
					id="Search" class="gSubmit"
					onMouseOver="showHelpMessage('以文號查詢', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
				</td>
		</table>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF" nowrap>依文號+班級範圍建立</td>
				<td class="hairLineTdF" nowrap>
				<input type="text" id="classNo" name="classLess" style="font-size:18;"
						 size="8" autocomplete="off" style="ime-mode:disabled" autocomplete="off" value="${EntrnoManagerForm.map.classLess}" 
				 		 onkeyup="if(this.value.length>2)getAny(this.value, 'classNo', 'className', 'Class', 'no')"
				 		 onclick="this.value='', document.getElementById('className').value=''"/>
				 		 <input type="text" name="className" value="${EntrnoManagerForm.map.className}" style="font-size:18;"
						 id="className" onclick="this.value='', document.getElementById('classNo').value=''"/>
				 		 </td>
				 		 <td width="30" align="center" class="hairlineTdF">
				 		<img src="images/icon/icon_info.gif" id="ch" onMouseOver="showHelpMessage('輸入班級, 系統會根據項目找尋', 'inline', this.id)" 
						 onMouseOut="showHelpMessage('', 'none', this.id)"/>
				</td>
				<td class="hairLineTdF" nowrap>
				<input type="submit" name="method" 
					value="<bean:message key='Create'/>" 
					id="CreateByDept" class="green" onMouseDown="document.getElementById('first_stno').value='';"
					onMouseOver="showHelpMessage('以文號+班級建立', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF" nowrap>依文號+學號範圍建立</td>
				<td class="hairLineTdF" nowrap>開始學號</td>
				
				<td class="hairLineTdF" nowrap><input type="text" size="10" name="first_stno" id="first_stno" value="${EntrnoManagerForm.map.first_stno}" /></td>
				
				<td class="hairLineTdF" nowrap>結束學號</td>
				<td class="hairLineTdF" nowrap><input type="text" size="10" name="second_stno" id="second_stno" value="${EntrnoManagerForm.map.second_stno}" /></td>
				
				
				</td>
				<td width="30" align="center" class="hairlineTdF">
				<img src="images/icon/icon_info.gif" id="chj" onMouseOver="showHelpMessage('輸入班級, 系統會根據項目找尋', 'inline', this.id)" 
				onMouseOut="showHelpMessage('', 'none', this.id)"/>
				</td>
				<td class="hairLineTdF" nowrap>
				<input type="submit" name="method" 
					value="<bean:message key='Create'/>" 
					id="Create" class="green" onMouseDown="document.getElementById('classNo').value='';"
					onMouseOver="showHelpMessage('以文號+學號建立', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
				</td>
			</tr>
		</table>
		
		
		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center">
		
		
		
		</td>
	</tr>
	
	<c:if test="${!empty entrnos}">
	<tr>
		<td>
		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF">開始學號</td>
				<td class="hairLineTdF">結束學號</td>
				<td class="hairLineTdF" width="100%">文號</td>
				<td class="hairLineTdF"></td>
			</tr>
			<c:forEach items="${entrnos}" var="e">
		
			<tr>
				<td class="hairLineTdF"><input disabled type="text" size="10" name="first_stno" value="${e.first_stno}" /></td>
				<td class="hairLineTdF"><input disabled type="text" size="10" name="second_stno" value="${e.second_stno}" /></td>
				<td class="hairLineTdF"><input disabled type="text" name="permission_no" style="width:100%;" value="${e.permission_no}" /></td>
				<td class="hairLineTdF"><input type="hidden" name="Oid" id="Oid${e.Oid}" value="" />
				<input type="submit" name="method" 
					value="<bean:message key='Delete'/>" 
					id="Delete${e.Oid}" class="gCancel" onMouseDown="document.getElementById('Oid${e.Oid}').value='${e.Oid}'";
					onMouseOver="showHelpMessage('刪除此文號', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
				
				</td>
			</tr>
		
		</c:forEach>
		</table>
	
		
		</td>
	</tr>
	</c:if>
	<tr height="30">
		<td class="fullColorTable">
		
		</td>
	</tr> 
</html:form>
</table>
<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>