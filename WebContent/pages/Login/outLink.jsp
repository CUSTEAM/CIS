<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<table class="hairlineTableIndex" cellspacing="0" cellpadding="0">
	<tr>
		<td class="fullColorTablePad" onClick="showObj('out')" style="cursor:pointer;">
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td width="30" align="center"><img src="images/link_go.gif"/></td>
				<td>校外連結</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="center">
		
		<table align="left" class="hairLineTd" id="out" cellspacing="0" cellpadding="0"
		style="display:none;" width="100%">
			<tr>								
				<td>
				
				<table>
					<tr>
						<td>
						
						<table>
						<c:forEach items="${PageLink.out}" var="out">
							<tr>
								<td><img src="images/${out.icon}"/></td>
								<td><a href="${a.Url}" target="${out.target}"><bean:message key="${out.Label}" bundle="IND"/></a></td>
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