<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<table class="hairLineTable" width="98%">
	<tr>
		<td class="fullColorTablePad" onClick="showObj('stuLink')" style="cursor:pointer;">		
		<div style="float:left; padding:2 5 0 0;"><img src="images/icon/application_view_tile.gif"/></div>
		<div nowrap style="float:left;" style="float:left; padding:2 5 0 0;"><font class="gray_15">學生系統功能導覽</font></div>	
		</td>
	</tr>
	<tr>
		<td align="left" class="hairLineTd" width="100%">
		
		<div id="stuLink" style="display:inline;">
				
				<table>
				<c:forEach items="${PageLink.s}" var="s" begin="0" end="4">
					<tr>
						<td>
						<c:if test="${s.Icon==null||s.Icon==''}"><img src="images/icon_info.gif"/></c:if>
						<c:if test="${s.Icon!=null&&s.Icon!=''}"><img src="images/${s.Icon}"/></c:if>
						</td>
						<td id="m${s.Oid}" style="cursor:pointer;">
						${s.Label}</td>
					</tr>
				</c:forEach>
				</table>
				
				
				<table align="right" onClick="showObj('stuLink_more');" style="cursor:pointer;">
				<tr>
					<td><img src="images/icon/find.gif" /></td>
					<td><b>瀏覽更多功能</b></td>
				</tr>
			</table>
				
				
				
				<table id="stuLink_more" style="display:none;">
				<c:forEach items="${PageLink.s}" var="s" begin="5">
					<tr>
						<td>
						<c:if test="${s.Icon==null||s.Icon==''}"><img src="images/icon_info.gif"/></c:if>
						<c:if test="${s.Icon!=null&&s.Icon!=''}"><img src="images/${s.Icon}"/></c:if>
						</td>
						<td id="m${s.Oid}" style="cursor:pointer;">
						${s.Label}</td>
					</tr>
				</c:forEach>
				</table>
			</div>
				
		</td>
	</tr>
</table>