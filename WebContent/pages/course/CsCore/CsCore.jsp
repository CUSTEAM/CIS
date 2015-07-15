<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Course/CsCoreSystem/CsCore" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start-->	
	<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/chart_line.gif">
				</td>
				<td align="left">
				&nbsp;課程核心能力指標查詢&nbsp;
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<%@ include file="CsCore/search.jsp"%>
		</td>
	</tr>
	
	<tr>
		<td id="help" style="display:none;">
		
		<%@ include file="CsCore/help.jsp"%>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" class="gSubmit" value="<bean:message key='Query'/>" id="Query"
					onMouseOver="showHelpMessage('查詢相關核心課程', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>		
					
		<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
					onMouseOver="showHelpMessage('顯示說明', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
		
		</td>
	</tr>
	
	<%if(request.getParameter("Oid")==null){%>
	<c:if test="${!empty cores}">
	<tr>
		<td>
		<%@ include file="CsCore/list.jsp"%>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		</td>
	</tr>
	</c:if>
	<%}%>
	
	<%if(request.getParameter("Oid")!=null){%>
	<tr>
		<td>
		<%@ include file="CsCore/detail.jsp"%>
		</td>
	</tr>
	<tr>
		<td>
		<%@ include file="chart/chart4cs.jsp"%>
		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center">
		<input type="button" class="gCancle" value="返回" id="back" onClick="history.go(-1)" 
					onMouseOver="showHelpMessage('返回查詢結果', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
		</td>
	</tr>
	<%}%>
	
	
	
	
	
	
	
	
</html:form>
</talbe>
<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>