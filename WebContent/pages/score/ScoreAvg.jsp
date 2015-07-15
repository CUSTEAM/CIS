<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<script language="javascript" src="/CIS/pages/include/decorateJs.js"></script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Score/ScoreAvg" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/folder_page.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">儲存歷年平均成績</font></div>		
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
	
	<tr height="30">
		<td class="fullColorTable"></td>
	</tr>
</html:form>
</table>