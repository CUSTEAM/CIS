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
<input type="hidden" name="docType" value="${DocRevokeInit.docType}"/>
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="AMSTitle.DocRevokeMaintain" bundle="AMS"/></B></div>');</script>	  
<!-- End Content Page Table Header -->
	
	<!-- Test if have Query Result  -->
	<c:if test="${DocRevokeList != null}" >
	    <tr><td height="10"></td></tr>
	    
	    <%@include file="/pages/AMS/include/Displaytag4Checkbox.inc"%>
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${DocRevokeList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty DocRevokeList}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
	        	<display:column title="<script>generateTriggerAll(${fn:length(DocRevokeList)}, 'DocRevoke');</script>" class="center" >
	          	<script>generateCheckbox("${row.oid}", "DocRevoke");</script></display:column>
 	        	<display:column title="請假開始日期"		property="revokedDoc.startDate"	format="{0,date,yyyy-MM-dd HH:mm:ss}" sortable="true" 	class="left" />
 	        	<display:column title="請假結束日期"		property="revokedDoc.endDate"	format="{0,date,yyyy-MM-dd HH:mm:ss}" sortable="true"  	class="left" />
	        	<display:column title="銷假單填寫日期"	property="createDate"			format="{0,date,yyyy-MM-dd HH:mm:ss}" sortable="true"  	class="left" />
	        	<display:column title="處理狀態" 		property="statusName" 			sortable="true" 	class="left" />
	        	<display:column title="條碼編號" 	property="sn" 			sortable="true" 	class="left" />
				<c:if test="${not row.isExpired && row.status == null}">
	        	<display:column title="列印" 		sortable="true" >
	        	<script>generateReport("${row.oid}", "");</script></display:column>
	        	</c:if>
				<c:if test="${row.isExpired || row.status != null}">
	        	<display:column title="列印" 		sortable="false" > </display:column>
	        	</c:if>
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Add' bundle='AMS'/>">&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Modify' bundle='AMS'/>" onclick="return checkSelectForModify(\'DocRevoke\');">&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Delete' bundle='AMS'/>" onclick="return checkSelectForDelete(\'DocRevoke\');">&nbsp;&nbsp;' +
	'<Input type="submit" name="method" value="<bean:message key='Back' bundle='AMS'/>" >');
	</script>
	</c:if>
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
