<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table cellspacing="0" cellpadding="0" width="100%">
<html:form action="/Course/CsCoreSystem/StudentsCore" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題start -->
	<tr>
		<td class="fullColorTable">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="/CIS/pages/images/chart_line.gif">
				</td>
				<td align="left">
				&nbsp;各系核心能力指標查詢&nbsp;
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td width="100%">
		<%@ include file="DeptCore/search.jsp"%>		
		</td>
	</tr>
	
	<tr>
		<td id="help" style="display:none;">		
		<%@ include file="DeptCore/help.jsp"%>	
		</td>
	</tr>
	
	<tr>
		<td class="fullColorTable" align="center">		
		<input type="submit" name="method" class="gSubmit" value="<bean:message key='Query'/>" id="Query"
					onMouseOver="showHelpMessage('查詢相關核心課程', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>					
		<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
					onMouseOver="showHelpMessage('顯示說明', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>		
		</td>
	</tr>
	
	<c:if test="${!empty dcores}">
	<tr>
		<td width="100%">		
		<%@ include file="DeptCore/list.jsp"%>		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		</td>
	</tr>
	</c:if>
	
	<c:if test="${empty dcores && DeptName!=null}">
	<tr>
		<td>
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" width="100%">${DeptName}尚未建立任何核心課程</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		</td>
	</tr>	
	</c:if>
	
	
</html:form>
</table>