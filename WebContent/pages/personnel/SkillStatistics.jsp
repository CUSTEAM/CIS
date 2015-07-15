<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="Personnel/SkillStatistics" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
	
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/chart_line_add.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">專長統計</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		<table width="99%" class="hairLineTable">
			<tr>
				<td width="30" class="hairLineTdF" align="center"><img src="images/icon/icon_info.gif" /></td>
				<td class="hairLineTdF"><font class="gray_15">點擊分類展開子分類</font></td>
			</tr>
		</table>
		<c:forEach items="${list}" var="l">
		<table width="99%" class="hairLineTable" style="cursor:pointer;" onClick="showObj('s${l.Oid}')">
			<tr>
				<td class="hairLineTdF">
				<font class="gray_15">${l.name} - ${l.count}</font>
				</td>
			</tr>
		</table>
		
		<table width="99%" class="hairLineTable" style="display:none;" id="s${l.Oid}">
			
			
			<c:forEach items="${l.skill1}" var="l1">
			<tr>
				<td width="30" class="hairLineTdF" align="center"><font class="gray_15">${l1.count}</font></td>
				<td class="hairLineTdF"
				<c:if test="${!empty l1.guys}">
				onClick="showObj('guys${l1.Oid}')" style="cursor:pointer;"				
				id="guyss${l1.Oid}" class="gSubmit"
				onMouseOver="showHelpMessage('點擊展開名單', 'inline', this.id)" 
				onMouseOut="showHelpMessage('', 'none', this.id)"
				</c:if> >
				<font class="gray_15">${l1.name}</font>
				</td>
			</tr>
			
			
			<c:if test="${!empty l1.guys}">
			<tr id="guys${l1.Oid}" style="display:none;">
				<td colspan="2" class="hairLineTdF">
				<c:forEach items="${l1.guys}" var="g">
				${g.cname}, 
				</c:forEach>
				</td>
			</tr>
			</c:if>
			
			</c:forEach>
			
			
			
						
		</table>
		</c:forEach>		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		</td>
	</tr>
</html:form>
</table>