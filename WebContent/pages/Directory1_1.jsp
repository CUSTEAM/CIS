<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">	
	<tr height="30">
		<td class="fullColorTable">
		
		<div style="float:left; padding:0 5 0 5;"><img src="images/${icon}"/></div>
		<div nowrap style="float:left;"><font class="gray_15">${title}</font></div>
		
		</td>
	</tr>
	<tr height="300" align="center" valign="top">
		<td style="padding:5 5 5 5;">
		
		
			<table>			
				<c:forEach items="${ModuleList}" var="m">
				<tr height="30">
					<td valign="baseline">
				
					<div style="float:left; padding-right:5px;"><html:link page="${m.Action}"><img src="images/${m.Icon}"border="0"/></html:link></div>
					<div nowrap style="float:left;"><html:link page="${m.Action}"><font class="gray_15">${m.Label}</font></html:link></div>
					
					</td>
				</tr>								
				</c:forEach>				
			</table>
			
			
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable"></td>
	</tr> 
</table>