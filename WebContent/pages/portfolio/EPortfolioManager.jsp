<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<!-- 多用途層 end -->
<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
<tr>
<td id="ds_calclass"></td>
</tr>
</table>
<!-- 多用途層 end -->
<table width="100%" cellpadding="0" cellspacing="0" border="0">

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
				
				<table width="100%">
					<tr>
						<td width="10" align="left" nowrap>
				  			<hr noshade class="myHr"/>
							</td>
							<td width="24" align="center" nowrap>
							<img src="images/icon/wrench.gif" />
							</td>
							<td nowrap style="cursor:pointer;">
							系統學習歷程
							</td>
							<td width="100%" align="left">
				  			<hr noshade class="myHr"/>
						</td>
					</tr>
				</table>
				
				<c:forEach items="${allTable}" var="at">
				<table class="hairLineTable" width="97%">
					<tr>
						<td class="hairLineTdF">
						
						<a href="../Portfolio/EPortfolioManager.do?sys=1&table_oid=${at.Oid}">${at.name}</a>
						</td>
					</tr>
				</table>				
				</c:forEach>				
				
				<table width="100%">
					<tr>
						<td width="10" align="left" nowrap>
				  			<hr noshade class="myHr"/>
							</td>
							<td width="24" align="center" nowrap>
							<img src="images/icon/wrench_orange.gif" />
							</td>
							<td nowrap style="cursor:pointer;">
							建立學習歷程
							</td>
							<td width="100%" align="left">
				  			<hr noshade class="myHr"/>
						</td>
					</tr>
				</table>
				
				<c:forEach items="${bllTable}" var="at">				
				<table class="hairLineTable" width="97%">
					<tr>
						<td class="hairLineTdF">
						<a href="../Portfolio/EPortfolioManager.do?sys=0&table_oid=${at.Oid}">${at.name}</a>
						</td>
					</tr>
				</table>				
				</c:forEach>
				
				
				
				
				
				<table width="100%">
					<tr>
						<td width="10" align="left" nowrap>
				  			<hr noshade class="myHr"/>
							</td>
							<td width="24" align="center" nowrap>
							<img src="images/icon/wrench_orange.gif" />
							</td>
							<td nowrap style="cursor:pointer;">
							自訂學習歷程
							</td>
							<td width="100%" align="left">
				  			<hr noshade class="myHr"/>
						</td>
					</tr>
				</table>
				
				<table class="hairLineTable" width="97%">
					<tr>
						<td class="hairLineTdF">
						
						<table>
							<tr>
								<td id="showSwitch">
								<a href="../Portfolio/FreePortfolioManager.do?tag=newTag">建立自訂歷程</a>
								</td>
							</tr>
						</table>
						
						
						
						</td>
					</tr>
				</table>
				
				
				<c:forEach items="${cllTable}" var="at">				
				<table class="hairLineTable" width="97%">
					<tr>
						<td class="hairLineTdF">						
						<script>
						document.write("<a href='../Portfolio/FreePortfolioManager.do?tag="+encodeURIComponent("${at.tag}")+"'>${at.tag}</a>");						
						</script>
						</td>
					</tr>
				</table>				
				</c:forEach>
				
				
				
				
				
				
				</td>
				
				<td width="80%" valign="top" align="center">
				<html:form action="Portfolio/EPortfolioManager">
				<c:if test="${!empty format}">
				<input type="hidden" name="table_oid"
				 <%if(request.getParameter("table_oid")!=null){%>
				value="<%=request.getParameter("table_oid")%>" 
				<%}else{%>
				value="${PortfolioManagerForm.map.table_oid}"
				<%}%>/>
				</c:if>
				<c:if test="${free==null}">				
				<!-- 當自定義為空 -->
					<c:if test="${empty myDate}">
						<c:if test="${sys=='1'}">
						<%@ include file="AboutMe/add.jsp"%>
						</c:if>
						<c:if test="${sys=='0'}">
						<%@ include file="PortfolioManager/add.jsp"%>
						</c:if>				
					</c:if>
					
					<c:if test="${!empty myDate}">
						<c:if test="${sys=='1'}">
							<%@ include file="AboutMe/edit.jsp"%>
							
						</c:if>
						
						<c:if test="${sys=='0'}">
							<%@ include file="PortfolioManager/add.jsp"%>
							<%@ include file="PortfolioManager/edit.jsp"%>					
						</c:if>
					</c:if>
					<%@ include file="AboutMe/preview.jsp" %>
				</c:if>
				</html:form>
				
				
				<c:if test="${free!=null}">						
				<!-- 當自定義不為空 -->
					<%@ include file="FreeManager/edit.jsp"%>		
				</c:if>
				
				
				
				</td>
				
			</tr>
			
			
			
			
			
			
			
			
			
		</table>
		
		</td>
	</tr>
	
	


	
	
	<tr height="30">
		<td class="fullColorTable" align="center" valign="bottom">		
		
		</td>
	</tr>
</table>

<%@ include file="/pages/include/MyCalendar.jsp" %>