<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script language="javascript" src="/CIS/pages/include/decorateJs.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Example/FormExample" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/application_view_icons.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">表單輸入範例</font></div>		
		</td>
	</tr>
	
	<tr>
		<td>
		<table class="hairLineTable" width="99%" id="table1">
			<tr>
				<td class="hairLineTdF" width="100">編號</td>
				<td class="hairLineTdF"><input type="text" name="cscode" id="cscode" value=""/></td>
			</tr>
			<tr>
				<td class="hairLineTdF" width="100">名稱</td>
				<td class="hairLineTdF"><input type="text" name="chi_name" id="chi_name" value=""/></td>
			</tr>
		</table>
		</td
	</tr>
	<tr>
		<td class="fullColorTable" align="center">
		<input type="submit" name="method" class="green" 
		value="<bean:message key='Query'/>" />
		</td>
	</tr>
	
	
	
	<c:if test="${!empty result}">
	<tr>
		<td>
		<table class="hairLineTable" width="99%" id="table1">
			<tr>
				<td class="hairLineTdF" width="100">編號</td>
				<td class="hairLineTdF" width="100">名稱</td>
			</tr>
			<c:forEach items="${result}" var="r">
			<tr>
				<td class="hairLineTdF" width="100">${r.cscode}</td>
				<td class="hairLineTdF" width="100">${r.chi_name}</td>
			</tr>
			</c:forEach>
		</table>
		</td
	</tr>
	
	</c:if>
	
</html:form>
</table>