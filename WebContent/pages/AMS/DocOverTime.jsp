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
<input type="hidden" name="docType" value="${DocOverTimeInit.docType}"/>
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="AMSTitle.DocOverTimeMaintain" bundle="AMS"/></B></div>');</script>	  
<!-- End Content Page Table Header -->
	
	<!-- Test if have Query Result  -->
	<c:if test="${DocOverTimeList != null}" >
	    <tr><td height="10"></td></tr>
	    
	    <%@include file="/pages/AMS/include/Displaytag4Checkbox.inc"%>
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${DocOverTimeList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty DocOverTimeList}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
	        	<display:column title="<script>generateTriggerAll(${fn:length(DocOverTimeList)}, 'DocOverTime');</script>" class="center" >
	          	<script>generateCheckbox("${row.oid}", "DocOverTime");</script></display:column>
 	        	<display:column title="開始日期"		property="startDate"	format="{0,date,yyyy-MM-dd HH:mm:ss}" sortable="true" 	class="left" />
 	        	<display:column title="結束日期"		property="endDate"		format="{0,date,yyyy-MM-dd HH:mm:ss}" sortable="true"  	class="left" />
	        	<display:column title="日時數" 		sortable="true" >
	        	${row.totalDay}日${row.totalHour}時</display:column>
	        	<display:column title="填寫日期"		property="createDate"	format="{0,date,yyyy-MM-dd HH:mm:ss}" sortable="true"  	class="left" />
	        	<display:column title="處理狀態" 	property="statusName" 	sortable="true" 	class="left" />
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
	generateTableBanner('<Input type="submit" name="method" value="<bean:message key='Add' bundle='AMS'/>" >&nbsp;&nbsp;' + 
	'<INPUT type="submit" name="method" value="<bean:message key='Modify' bundle='AMS'/>" onclick="return checkSelectForModify(\'DocOverTime\');">&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Delete' bundle='AMS'/>" onclick="return checkSelectForDelete(\'DocOverTime\');">&nbsp;&nbsp;' +
	'<Input type="submit" name="method" value="<bean:message key='Back' bundle='AMS'/>" >');
	</script>
	</c:if>
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
