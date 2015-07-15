<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Registration/DeptNameManager" method="post" onsubmit="init('系統處理中...')">
<!-- 標題列 start-->	
	<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/folder_star.gif">
				</td>
				<td align="left">
				&nbsp;科系(所)名稱設定&nbsp;
				</td>
			</tr>
		</table>
		</td>
	</tr>	
<!-- 標題列 end -->
	<tr>
		<td>
		<table width="99%" class="hairLineTable">
			<tr>
				<td class="hairLineTdF"><font size="1">代碼</font></td>
				<td class="hairLineTdF"><font size="1">部制名稱</font></td>
				<td class="hairLineTdF"><font size="1">系所簡稱</font></td>
				<td class="hairLineTdF"><font size="1">系所全名</font></td>.
				
				<td class="hairLineTdF"><font size="1">系所英文名稱</td>
				
				<td class="hairLineTdF"><font size="1">組別名稱</font></td>
				
			</tr>
			<tr>
				<td class="hairLineTdF"><input class="smallinput" type="hidden" name="editCheck" size="1" /><input type="hidden" name="Oid"/>
				<input class="smallinput" type="text" name="no" size="6" maxlength="5"/></td>
				<td class="hairLineTdF"><input class="smallinput" type="text" name="school_name" size="10"/></td>
				<td class="hairLineTdF"><input class="smallinput" type="text" name="sname" size="8" /></td>
				<td class="hairLineTdF"><input class="smallinput" type="text" name="fname" size="20" /></td>				
				<td class="hairLineTdF"><input class="smallinput" type="text" name="engname" size="25" /></td>				
				<td class="hairLineTdF"><input class="smallinput" type="text" name="dname" size="8"/></td>
				
			</tr>
		</table>
		
		</td>
	</tr>
	<tr height="35">
		<td class="fullColorTable" align="center">
		<INPUT type="submit" name="method" value="<bean:message key='Create'/>" class="gGreen">
		</td>
	</tr>


	<tr>
		<td>
		<table width="99%" class="hairLineTable">
			<tr>
				<td class="hairLineTdF"><font size="1">代碼</font></td>
				<td class="hairLineTdF"><font size="1">部制名稱</font></td>
				<td class="hairLineTdF"><font size="1">系所簡稱</font></td>
				<td class="hairLineTdF"><font size="1">系所全名</font></td>.
				
				<td class="hairLineTdF"><font size="1">系所英文名稱</td>
				
				<td class="hairLineTdF"><font size="1">組別名稱</font></td>
				<td class="hairLineTdF"><font size="1">建立/修改日期</font></td>
			</tr>
<c:forEach items="${depts}" var="d">
			<tr onKeyUp="document.getElementById('editCheck${d.Oid}').value='y'">
				<td class="hairLineTdF"><input type="hidden" name="Oid" value="${d.Oid}" /><input type="hidden" name="editCheck" id="editCheck${d.Oid}" /><input class="smallinput" type="text" name="no" value="${d.no}" size="6" maxlength="4"/></td>
				<td class="hairLineTdF"><input class="smallinput" type="text" name="school_name" value="${d.school_name}" size="10"/></td>
				<td class="hairLineTdF"><input class="smallinput" type="text" name="sname" value="${d.sname}" size="8" /></td>
				<td class="hairLineTdF"><input class="smallinput" type="text" name="fname" value="${d.fname}" size="20" /></td>
				
				<td class="hairLineTdF"><input class="smallinput" type="text" name="engname" value="${d.engname}" size="25" /></td>
				
				<td class="hairLineTdF"><input class="smallinput" type="text" name="dname" value="${d.dname}" size="8"/></td>
				<td class="hairLineTdF"><font size="1">${d.last_edit_time} ${d.last_edit_user}</font></td>
			</tr>
</c:forEach>
		</table>
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		<INPUT type="submit" name="method" value="<bean:message key='OK'/>" class="gSubmit">
		</td>
	</tr>
</html:form>
</table>