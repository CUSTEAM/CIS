<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<script src="/CIS/pages/include/decorate.js"></script>
<!-- 多用途層 end -->
<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
<tr>
<td id="ds_calclass"></td>
</tr>
</table>
<!-- 多用途層 end -->
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="Portfolio/PortfolioManager">
	<tr>
		<td class="fullColorTable" width="100%">
			<table width="100%" cellpadding="0" cellspacing="0" border="0">
				<tr height="30">
					<td width="28" align="right">
						<img src="images/folder_lock.gif">
					</td>
					<td align="left">
						&nbsp;學習歷程管理&nbsp;
					</td>
					<td>
						<table align="right">
							<tr>							
								<td width="1"><img src="images/icon/zoomout.gif"></td>
								<td><a href="${myUrl}" target="_blank">立即檢視</a></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td>
		
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td width="20%" valign="top" align="center">			
				
				<c:forEach items="${allTable}" var="at">
				<table class="hairLineTable" width="97%">
					<tr>
						<td class="hairLineTdF">
						<a href="../Portfolio/PortfolioManager.do?table_oid=${at.Oid}">${at.name}</a>
						</td>
					</tr>
				</table>				
				</c:forEach>			
				
				</td>
				
				<td width="80%" valign="top" align="center">
				
				<%@ include file="PortfolioManager/add.jsp"%>
				
				<%@ include file="PortfolioManager/edit.jsp"%>
				
				</td>
				
			</tr>
		</table>
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center" valign="bottom">		
		</td>
	</tr>
</html:form>
</table>

<%@ include file="/pages/include/MyCalendar.jsp" %>