<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<table class="hairlineTableIndex" cellspacing="0" cellpadding="0">
	<tr>
		<td class="fullColorTablePad" onClick="showObj('allUser')" style="cursor:pointer;">
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td width="30" align="center"><img src="images/building_link.gif"/></td>
				<td><bean:message key="Login.AllUser.title" bundle="IND"/></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="center">
		
		<table align="left" class="hairLineTd" id="allUser" cellspacing="0" cellpadding="0"
		style="display:none;" width="100%">
			<tr>								
				<td>
				
				<table>
					<tr>
						<td>
						
						<table>
						<c:forEach items="${PageLink.a}" var="a">
							<tr>
								<td><img src="images/${a.icon}"/></td>
								<td><a href="${a.Url}" target="${p.target}"><bean:message key="${a.Label}" bundle="IND"/></a></td>
							</tr>
						</c:forEach>
						</table>
						
						</td>
					</tr>
				</table>
				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
</table>