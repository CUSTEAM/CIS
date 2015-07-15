<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
	<script>generateTableBanner('<div class="gray_15"><B>${DirectoryBanner}</B></div>');</script>
	<tr height="25"><td></td></tr>
	<tr height="300" align="center" valign="top"><td>
		<table border="0">
			<c:if test="${not empty ModuleList}">
				<c:forEach items="${ModuleList}" var="module">
					<TR height="30">
					<td><c:if test="${module.icon!=null && module.icon!=''}"><img src="images/${module.icon}"/></c:if></td>
					<TD>
						<html:link page="${module.action}"><font class="gray_15">${module.label}</font></html:link>
					</TD></TR>
				</c:forEach>
			</c:if>
		</table></td></tr>
	<tr height="25"><td></td></tr> 
	<script>generateTableBanner('');</script>
</table>