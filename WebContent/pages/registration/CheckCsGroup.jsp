<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table cellspacing="0" cellpadding="0" width="100%">
<!-- 標題start -->
	<tr>
		<td class="fullColorTable">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/icon/folder_brick.gif" />
				</td>
				<td align="left">
				&nbsp;學程資格審核&nbsp;
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
<html:form action="/Registration/CheckCsGroup" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
	<tr>
		<td>
		
		<%@ include file="CheckCsGroup/search.jsp" %>
		
		
		</td>
	</tr>
<c:if test="${!empty relult}">
	<tr>
		<td>		
		<%@ include file="CheckCsGroup/list.jsp" %>		
		</td>
	</tr>
</c:if>	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
</html:form>
</table>