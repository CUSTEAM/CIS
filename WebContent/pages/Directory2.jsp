<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
	<script>generateTableBanner('<div class="gray_15"><B>${DirectoryBanner}</B></div>');</script>
	<tr height="25">
		<td></td>
	</tr>
	<tr height="300" align="center" valign="top">
		<td>
			<table border="0">
			<c:set var="total" scope="page" value="${sessionScope.ModuleList}"></c:set>
			<c:if test="${not empty ModuleList}">
				<c:forEach items="${ModuleList}" var="module" varStatus="status">
				<c:if test="${status.index % 2 == 0}">
				<tr height="30">
				<td><html:link page="${module.action}" target="_blank"><font class="gray_15">${module.label}</font></html:link></td>
				<td width="20"></td>
				</c:if>
				<c:if test="${status.index % 2 == 1}">
				<td>${module.icon}<html:link page="${module.action}" target="_blank"><font class="gray_15">${module.label}</font></html:link></td>				
				</c:if>									
				</c:forEach>				
			</c:if>
			</table>
		</td>
	</tr>
	<tr height="25">
		<td></td>
	</tr> 
	<script>generateTableBanner('');</script>
</table>