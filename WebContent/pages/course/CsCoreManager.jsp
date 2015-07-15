<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Course/CsCoreManager" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start-->	
	<tr>
		<td class="fullColorTable" width="100%">
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr height="30">
				<td width="28" align="right">
				<img src="images/icon/folder_star.gif">
				</td>
				<td align="left">
				&nbsp;核心課程管理&nbsp;
				</td>
			</tr>
		</table>
		</td>
	</tr>
	
	<c:if test="${core==null}">
	<tr>
		<td>
		<%@ include file="CsCoreManager/search.jsp"%>
		<%@ include file="CsCoreManager/list.jsp"%>
		
		</td>
	</tr>
	
	
	</c:if>
	
	
	<c:if test="${core!=null}">
	<tr>
		<td>
		<%@ include file="CsCoreManager/edit.jsp"%>
		</td>
	</tr>
	
	
	</c:if>
</html:form>
</table>