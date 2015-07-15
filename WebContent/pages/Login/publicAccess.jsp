<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<table class="hairlineTableIndex" cellspacing="0" cellpadding="0">
	<tr>
		<td class="fullColorTablePad" onClick="showObj('publicAccess')" style="cursor:pointer;">
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td width="30" align="center"><img src="images/bell.gif"/></td>
				<td><bean:message key="Login.PublicAccess.title" bundle="IND"/></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="center">
		
		<table align="left" class="hairLineTd" id="publicAccess" cellspacing="0" cellpadding="0"
		width="100%">
			<tr>								
				<td>
				
				<table>
					<tr>
						<td>
						
						<table>
						<c:forEach items="${PageLink.p}" var="p">
							<tr>
								<td><img src="images/${p.icon}"/></td>
								<td><a href="${p.Url}" target="${p.target}"><bean:message key="${p.Label}" bundle="IND"/></a></td>
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