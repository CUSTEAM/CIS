<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.style2 {color: #FF0000}
// -->
</style>
<!-- Begin Content Page Table Header -->
<form action="/CIS/AMS/DocManager.do" method="post" name="askForm">
<input type="hidden" name="docType" value="${DocRevokeEditInit.docType}"/>
<input name="opmode" type="hidden" value="add"/>
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="AMSTitle.DocRevokeMaintain" bundle="AMS"/></B></div>');</script>	  
<!-- End Content Page Table Header -->
	
	<!-- Test if have Query Result  -->
	<c:if test="${askLeaves != null}" >
	    <tr><td height="10"></td></tr>
	    <tr><td><font style="font-weight:500; color=#FF0000;">請選擇欲銷假之假單：</font> </td></tr>
	    <%@include file="/pages/AMS/include/Displaytag4Checkbox.inc"%>
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${askLeaves}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty askLeaves}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
	        	<display:column title="<script>generateTriggerAll(${fn:length(askLeaves)}, 'DocAskLeaveC');</script>" class="center" >
	          	<script>generateCheckbox("${row.oid}", "DocAskLeaveC");</script></display:column>
	        	<display:column title="假別"			property="askLeaveName"	sortable="true"  	class="left" />
 	        	<display:column title="開始日期"		property="startDate" sortable="true" 	class="left" />
 	        	<display:column title="結束日期"		property="endDate" sortable="true"  	class="left" />
	        	<display:column title="填寫日期"		property="createDate" sortable="true"  	class="left" />
	        	<display:column title="處理狀態" 	property="statusName" 	sortable="true" 	class="left" />
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>
	<script>
	generateTableBanner('<Input type="submit" name="method" value="<bean:message key='Revoke' bundle='AMS'/>"  onclick="return checkSelectForRevoke(\'DocAskLeaveC\');">&nbsp;&nbsp;' + 
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel' bundle='AMS'/>">');
	</script>
	</c:if>
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
