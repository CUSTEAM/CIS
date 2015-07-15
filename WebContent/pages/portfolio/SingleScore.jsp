<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<script>
history.go(1);
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Portfolio/DeptScore" enctype="multipart/form-data" method="post" onsubmit="init('執行中, 請稍後')">
<!-- 標題列 start-->	
	<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/folder_lock.gif" id="piftitle">
				</td>
				<td align="left">
				&nbsp;個人成績&nbsp;<input type="hidden" name="delOid"/>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		
		</td>
	</tr>
</html:form>
</table>