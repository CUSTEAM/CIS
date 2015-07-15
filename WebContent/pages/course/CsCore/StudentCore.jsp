<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table cellspacing="0" cellpadding="0" width="100%">
<html:form action="/Course/CsCoreSystem/StudentCore" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題start -->
	<tr>
		<td class="fullColorTable">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/chart_line.gif">
				</td>
				<td align="left">
				&nbsp;個人核心能力指標查詢&nbsp;
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td width="100%">
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">以班級尋找學生</td><td class="hairLineTdF"><%@ include file="StudentCore/search.jsp"%>	</td>
			</tr>
		</table>
			
		</td>
	</tr>
	
	<tr>
		<td id="help" style="display:none;">		
		<%@ include file="StudentCore/help.jsp"%>	
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
	
	<%if(request.getParameter("student_no")==null){%>
	<c:if test="${!empty students}">	
	<tr>
		<td width="100%">		
		<%@ include file="StudentCore/list.jsp"%>		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		</td>
	</tr>
	</c:if>
	<%}%>
	
	<%if(request.getParameter("student_no")!=null){%>
	<tr>
		<td width="100%">		
		<%@ include file="StudentCore/chart.jsp"%>		
		</td>
	</tr>
	
	<tr height="30">
		<td class="fullColorTable" align="center">
		<input type="button" class="gCancle" value="返回" id="back1" onClick="history.go(-1)" 
					onMouseOver="showHelpMessage('返回查詢結果', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
		</td>
	</tr>
	
	<tr>
		<td width="100%">		
		<%@ include file="StudentCore/detail.jsp"%>		
		</td>
	</tr>
	
	<tr height="30">
		<td class="fullColorTable" align="center">
		<input type="button" class="gCancle" value="返回" id="back" onClick="history.go(-1)" 
					onMouseOver="showHelpMessage('返回查詢結果', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
		</td>
	</tr>
	<%}%>
</html:form>
</table>

<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetStmdOrGstmd.jsp" %>
<%@ include file="/pages/include/MyCalendar.jsp" %>