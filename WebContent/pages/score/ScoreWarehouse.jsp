<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<script language="javascript" src="/CIS/pages/include/decorateJs.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Score/ScoreWarehouse" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/folder_page.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">儲存歷年成績</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">執行範圍</td>
				<td class="hairLineTdF">
				<select name="CampuseNo" style="font-size:18px;">
					<c:forEach items="${camps}" var="c">
					<option  <c:if test="${ScoreWarehouseForm.map.CampuseNo==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
					</c:forEach>
				</select>
				</td>
				
				<td class="hairLineTdF">
				<select name="SchoolType" style="font-size:18px;">
					<c:forEach items="${type}" var="c">
					<option <c:if test="${ScoreWarehouseForm.map.SchoolType==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
					</c:forEach>
				</select>
				</td>
				
				<td class="hairLineTdF">
				<input type="submit" name="method" class="gSubmit" value="<bean:message key='OK'/>" id="OK"
		onMouseOver="showHelpMessage('查詢', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)"/>
				</td>
				
			</tr>
		</table>
		
		</td>
	</tr>
	<c:if test="${!empty checklist}">
	<tr>
		<td>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF">學年</td>
				<td class="hairLineTdF">學期</td>
				<td class="hairLineTdF">校區</td>
				<td class="hairLineTdF">部制</td>
				<td class="hairLineTdF">執行人員</td>
				<td class="hairLineTdF">日期</td>
			</tr>
			<c:forEach items="${checklist}" var="c">
			<tr>
				<td class="hairLineTdF">${c.school_year}</td>
				<td class="hairLineTdF">${c.school_term}</td>
				<td class="hairLineTdF">				
				<select name="CampuseNo" style="font-size:18px;" disabled>
					<c:forEach items="${camps}" var="a">
					<option  <c:if test="${c.CampuseNo==a.idno}">selected</c:if> value="${a.idno}">${a.name}</option>
					</c:forEach>
				</select>
				</td>
				<td class="hairLineTdF">
				<select name="SchoolType" style="font-size:18px;" disabled>
					<c:forEach items="${type}" var="t">
					<option <c:if test="${c.SchoolType==t.idno}">selected</c:if> value="${t.idno}">${t.name}</option>
					</c:forEach>
				</select>
				
				</td>
				
				<td class="hairLineTdF">${c.cname}</td>
				<td class="hairLineTdF">${c.checkDate}</td>
			</tr>
			</c:forEach>
		</table>
		
		</td>
	</tr>
	</c:if>
	<tr height="30">
		<td class="fullColorTable"></td>
	</tr>
</html:form>
</table>