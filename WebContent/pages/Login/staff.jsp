<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<table class="hairLineTable" width="98%">
	<tr>
		<td class="fullColorTablePad" onClick="showObj('staffLink')" style="cursor:pointer;">		
		<div style="float:left; padding:2 5 0 0;"><img src="images/icon/application_view_icons.gif"/></div>
		<div nowrap style="float:left;" style="float:left; padding:2 5 0 0;"><font class="gray_15">教職員系統功能導覽</font></div>	
		</td>
	</tr>	
	<tr>
		<td class="hairLineTd">
		<div id="staffLink" style="display:inline;">	
			<table width="100%" >
			<c:forEach items="${PageLink.stf}" var="t" begin="0" end="4">
				<tr>
					<td>						
					<table cellpadding="0" cellspacing="0">
						<tr>
						<td width="1">
						<c:if test="${t.Icon==null||t.Icon==''}"><img src="images/icon_info.gif"/></c:if>
						<c:if test="${t.Icon!=null&&t.Icon!=''}"><img src="images/${t.Icon}"/></c:if>
						</td>
						<td width="100%" align="left" id="stf${t.Oid}" onClick="showObj('tm${t.Oid}');" style="cursor:pointer;">${t.Label}</td>							
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
								<td width="100%" id="stff${tm.Oid}" aligh="left" style="cursor:pointer;" nowrap>${tm.Label}
								</td>
							</tr>
						</table>
						</c:forEach>
					</td>
				</tr>				
			</c:forEach>
			</table>
			
			
			<table align="right" onClick="showObj('staffLink_more')" style="cursor:pointer;">
				<tr>
					<td><img src="images/icon/find.gif" /></td>
					<td><b>瀏覽更多功能</b></td>
				</tr>
			</table>
			
			
			
			
			
			
			
			
			<table id="staffLink_more" style="display:none;">
			<c:forEach items="${PageLink.stf}" var="t" begin="5">
				<tr>
					<td>						
					<table cellpadding="0" cellspacing="0">
						<tr>
						<td width="1">
						<c:if test="${t.Icon==null||t.Icon==''}"><img src="images/icon_info.gif"/></c:if>
						<c:if test="${t.Icon!=null&&t.Icon!=''}"><img src="images/${t.Icon}"/></c:if>
						</td>
						<td width="100%" align="left" id="stf${t.Oid}" onClick="showObj('tm${t.Oid}');" style="cursor:pointer;">${t.Label}</td>							
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
								<td width="100%" id="stff${tm.Oid}" aligh="left" style="cursor:pointer;" nowrap>${tm.Label}
								</td>
							</tr>
						</table>
						</c:forEach>
					</td>
				</tr>				
			</c:forEach>
			</table>
			
			</div>
			
			
		
		</td>
	</tr>
</table>