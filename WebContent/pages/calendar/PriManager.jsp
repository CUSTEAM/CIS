<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Calendar/PriManager" enctype="multipart/form-data" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/calendar_3.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">行事曆管理</font></div>		
		</td>
	</tr>
	<tr>
		<td>
		<%@ include file="PriManager/view4month.jsp"%>		
		</td>	
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">		
		<c:forEach items="${myGroup}" var="g">
		<div id="g${g.Oid}" style="display:none;">${g.members}</div>
		</c:forEach>
				
		</td>	
	</tr>
</html:form>
</table>
<div style="display:none;" id="addFile"><input type="file" name="addFile" style="width:98%;" size="4"/></div>