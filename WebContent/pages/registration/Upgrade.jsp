<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>



<table width="100%" cellpadding="0" cellspacing="0" border="0">
<!-- 標題 start -->
<html:form action="/Registration/Upgrade" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/package_go.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">學生批次升級管理</font></div>		
		</td>
	</tr>
	
	<tr>
		<td>
		<%@ include file="Upgrade/search.jsp"%>
		</td>
	</tr>
	
	<c:if test="${!empty students}">
	<tr>
		<td>
		
		<table width="99%" class="hairLineTable">
			<tr>
				<td class="hairLineTdF">班級代碼</td>
				<td class="hairLineTdF">班級名稱</td>
				<td class="hairLineTdF">學號</td>
				<td class="hairLineTdF">姓名</td>
				<td class="hairLineTdF">入學年月</td>
			</tr>
			<c:forEach items="${students}" var="s">			
			<tr>
				<td class="hairLineTdF">${s.ClassNo}</td>
				<td class="hairLineTdF">${s.ClassName}</td>
				<td class="hairLineTdF">${s.student_no}</td>
				<td class="hairLineTdF">${s.student_name}</td>
				<td class="hairLineTdF">${s.entrance}</td>
			</tr>
			</c:forEach>
			
		</table>
		
		
		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center">
		<input type="submit" name="method" 
			value="<bean:message key='LevelUp'/>" 
			id="LevelUp" class="gGreen"
			onMouseOver="showHelpMessage('依照以上條件查詢', 'inline', this.id)" 
			onMouseOut="showHelpMessage('', 'none', this.id)">
			
		<input type="submit" name="method" 
			value="<bean:message key='LevelDown'/>" 
			id="LevelDown" class="gCancel"
			onMouseOver="showHelpMessage('依照以上條件查詢', 'inline', this.id)" 
			onMouseOut="showHelpMessage('', 'none', this.id)">
		</td>
	</tr>
	</c:if>
	
</html:form>
</table>

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>