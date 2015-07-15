<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>



<table width="100%" cellpadding="0" cellspacing="0" border="0">
<!-- 標題 start -->
<html:form action="/DepAssistant/DtimeReserveManager" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/book.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">課程規劃</font></div>		
		</td>
	</tr>
	
	
<c:if test="${aDtimeReserve!=null}">
	<tr>
		<td>
		<%@ include file="DtimeReserver/edit.jsp"%>
		</td>
	</tr>	
</c:if>
	
	
	
<c:if test="${aDtimeReserve==null}">
	<tr>
		<td>
		<%@ include file="DtimeReserver/search.jsp"%>			
		</td>
	</tr>	
	<c:if test="${opt1!=null}">
	<tr>
		<td>
		<%@ include file="DtimeReserver/list.jsp"%>		
		</td>
	</tr>
	</c:if>
</c:if>
	
	
	
</html:form>
</table>

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>