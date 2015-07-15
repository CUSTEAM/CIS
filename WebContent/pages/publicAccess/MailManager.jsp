<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script language="javascript" src="/CIS/pages/include/decorateJs.js"></script>
<html:form action="/Personnel/MailManager" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<table width="100%" cellpadding="0" cellspacing="0" border="0">

<!-- 標題列 start-->	
	<tr height="30">
		<td class="fullColorTable" width="100%">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/email_start.gif" id="piftitle"></div>
		<div nowrap style="float:left;"><font class="gray_15">群組郵件</font></div>
		</td>
	</tr>

	<c:if test="${empty sendList}">	
		<tr>
			<td>		
			<%@ include file="MailManager/search.jsp"%>
			</td>
		</tr>
	</c:if>
	<tr height="30">
		<td class="fullColorTable">
		
		</td>
	</tr>

</table>
</html:form>