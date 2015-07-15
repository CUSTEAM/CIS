<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/SysAdmin/MailAccountManager" method="post" onsubmit="init('執行中, 請稍後')">
<!-- 標題列 start-->	
	<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/16-manager-st.gif" id="piftitle">
				</td>
				<td align="left">
				&nbsp;郵件帳號管理&nbsp;<input type="hidden" name="exSearch" value="${HRmanagerForm.map.exSearch}" />
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td>
		
		<select name="servers">
		<c:forEach items="${servers}" var="s">
			<option value="${s.Oid}">${s.Value} - ${s.notes}</option>
		</c:forEach>
		</select>
		
		
		
		
		
		</td>
	</tr>
	
	
	
	
	
	
</html:form>
</table>
