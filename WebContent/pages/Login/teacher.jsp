<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<table class="hairlineTableIndex" cellspacing="0" cellpadding="0">
	<tr>
		<td class="fullColorTablePad">
		
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td align="center"><img src="images/user_suit.gif"/></td>
				<td onClick="showObj('teLink')" style="cursor:pointer;" width="99%" align="left">&nbsp;
				<bean:message key="Login.Teacher.title" bundle="IND"/>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>	
	
	<tr>
		<td id="teLink" width="100%" style="display:none;">				
			<table width="100%" class="realFullColorTable" cellspacing="0" cellpadding="0">
			<c:forEach items="${PageLink.t}" var="t">
				<tr>
					<td>						
					<table>
						<tr>
						<td width="1">
						<c:if test="${t.Icon==null||t.Icon==''}"><img src="images/icon_info.gif"/></c:if>
						<c:if test="${t.Icon!=null&&t.Icon!=''}"><img src="images/${t.Icon}"/></c:if>
						</td>
						<td width="100%" align="left" id="m${t.Oid}" onClick="showObj('tm${t.Oid}'), getNews('${t.Oid}', 'newsInfo'), 
						showNews(this.id)" style="cursor:pointer;">${t.Label}</td>							
						</tr>
					</table>
					</td>
				</tr>
				
				
				<tr>
					<td align="right" style="display:none;" id="tm${t.Oid}">
						<c:forEach items="${t.subMenu}" var="tm">
						<table width="95%">
							<tr>
								<td width="1">
								<c:if test="${tm.Icon==null||tm.Icon==''}"><img src="images/icon_info.gif"/></c:if>
								<c:if test="${tm.Icon!=null&&tm.Icon!=''}"><img src="images/${tm.Icon}"/></c:if>
								</td>
								<td width="100%" id="te${tm.Oid}" aligh="left" OnClick="getNews('${tm.Oid}', 'newsInfo'), 
								showNews(this.id)" style="cursor:pointer;" nowrap>${tm.Label}
								</td>
							</tr>
						</table>
						</c:forEach>
					</td>
				</tr>				
			</c:forEach>
			</table>
		
		</td>
	</tr>
</table>