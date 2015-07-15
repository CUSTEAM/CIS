<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/PublicAccess/DemandManager" method="post" onsubmit="init('系統處理中...')">
<!-- 標題列 start-->		
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/computer_go.gif" id="piftitle"></div>
		<div nowrap style="float:left;"><font class="gray_15">資訊系統需求申請</font></div>		
		</td>
	</tr>
</html:form>
</table>