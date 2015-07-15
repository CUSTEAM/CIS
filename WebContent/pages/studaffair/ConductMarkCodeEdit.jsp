<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/CodeSelection.js" %>

<style type="text/css">
<!--
.input {
	width:20px;
}
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.style2 {color: #FF0000}
-->
</style>

<form action="/CIS/StudAffair/ConductMarkCode.do" method="post" name="cmcForm">

<c:set var="tcnt" value="1"/>
<c:set var="mode" value="${ConductMarkCodeEditInit.mode}"/>

<table width="100%" cellpadding="0" cellspacing="0">

<!-- Begin Content Page Table Header -->
<c:if test="${mode == 'Create'}">
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.ConductMarkCodeCreate" bundle="SAF"/></B></div>');</script>	  
</c:if>
<c:if test="${mode == 'Modify'}">
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.ConductMarkCodeModify" bundle="SAF"/></B></div>');</script>	  
</c:if>

	<tr>
		<td align="left"> 
<!-- End Content Page Table Header -->

	<table cellspacing="5" class="empty-border" width="100%">
	<c:choose>
	<c:when test="${ConductMarkCodeEdit != null}">
		<tr>
			<td align="left" valign="middle" width="50">代碼：</td>
			<td align="left" valign="middle">
				<c:if test="${mode == 'Create'}">
            	<input name="no" id="no" type="text" size="5" maxlength="20" value="${ConductMarkCodeEdit.no}">
        		</c:if>
				<c:if test="${mode == 'Modify'}">
            		${ConductMarkCodeEdit.no}
        		</c:if>
         	</td>
        </tr>
        <tr>
        	<td align="left" valign="middle">評語：</td>
        	<td align="left" valign="middle">
        		<input type="text" name="name" value="${ConductMarkCodeEdit.name}" id="name">
        	</td>
        </tr>
	</c:when>
	<c:when test="${mode=='Create'}">
		<tr>
			<td align="left" valign="middle" width="50">代碼：</td>
			<td align="left" valign="middle">
            	<input name="no" id="no" type="text" size="5" maxlength="20">
         	</td>
        </tr>
        <tr>
        	<td align="left" valign="middle">評語：</td>
        	<td align="left" valign="middle">
        		<input type="text" name="name" id="name">
        	</td>
        </tr>
	</c:when>
	<c:when test="${mode=='Modify'}">
        <input type="hidden" name="no" id="no" value="${ConductMarkCodeModify.no}">
		<tr>
			<td align="left" valign="middle" width="50">代碼：</td>
			<td align="left" valign="middle">
            	${ConductMarkCodeModify.no}
         	</td>
        </tr>
        <tr>
        	<td align="left" valign="middle">評語：</td>
        	<td align="left" valign="middle">
        		<input type="text" name="name" id="name" value="${ConductMarkCodeModify.name}">
        	</td>
        </tr>
	</c:when>
	</c:choose>
	</table>
	</td>
	</tr>
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='OK'/>" id="ok">&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" id="cancel">');
	</script>
</table>
</form>

<script>history.go(1);</script>