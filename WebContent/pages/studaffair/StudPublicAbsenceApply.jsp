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
<form action="/CIS/StudAffair/StudPublicAbsenceApply.do" method="post" name="askForm">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="Title.StudentPublicAbsenceMaintain" bundle="SAF"/></B></div>');</script>	  
<!-- End Content Page Table Header -->
	
	<!-- Test if have Query Result  -->
	<c:if test="${StudPublicDocs != null}" >
	    <tr><td height="10"></td></tr>
	    
	    <%@include file="/pages/studaffair/include/Displaytag4Checkbox.inc"%>
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${StudPublicDocs}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty StudPublicDocs}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
	        	<display:column title="<script>generateTriggerAll(${fn:length(StudPublicDocs)}, 'StudPubDoc');</script>" class="center" >
	          	<script>generateCheckbox("${row.oid}", "StudPubDoc");</script></display:column>
  	        	<display:column title="事由"			property="reason"		sortable="false" 	class="left" />
  	        	<display:column title="班級"			property="deptClassName"		sortable="false" 	class="left" />
 	        	<display:column title="開始日期"		property="startDate"	format="{0,date,yyyy-MM-dd}" sortable="true" 	class="center" />
 	        	<display:column title="開始節次"		property="startPeriod"	sortable="false" 	class="center" />
 	        	<display:column title="結束日期"		property="endDate"		format="{0,date,yyyy-MM-dd}" sortable="true"  	class="center" />
 	        	<display:column title="結束節次"		property="endPeriod"	sortable="false" 	class="center" />
	        	<display:column title="填寫日期"		property="createDate"	format="{0,date,yyyy-MM-dd}" sortable="true"  	class="center" />
	        	<display:column title="處理狀態" 		property="statusName" 	sortable="true" 	class="center" />
	        	<display:column title="處理時間" 		property="processDate" 		format="{0,date,yyyy-MM-dd HH:mm:ss}"	sortable="true" 	class="center" />
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>
	<script>
	generateTableBanner('<Input type="submit" name="method" value="<bean:message key='Create' />" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + 
	'<INPUT type="submit" name="method" value="<bean:message key='Modify' />" onclick="return checkSelectForModify(\'StudPubDoc\');">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Delete' />" onclick="return checkSelectForDelete(\'StudPubDoc\');">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +
	'<INPUT type="submit" name="method" value="<bean:message key='View' />" onclick="return checkSelectForView(\'StudPubDoc\');">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +
	'<INPUT type="submit" name="method" value="<bean:message key='Send4Examine' bundle='STD'/>" onclick="return checkSelectFor(\'StudPubDoc\', \'<bean:message key='Send4Examine' bundle='STD'/>\');">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +
	'<INPUT type="submit" name="method" value="<bean:message key='CancelExamine' bundle='STD'/>" onclick="return checkSelectFor(\'StudPubDoc\', \'<bean:message key='CancelExamine' bundle='STD'/>\' );">'
	);
	</script>
	<!-- + '<INPUT type="submit" name="method" value="<bean:message key='Revoke' bundle='AMS'/>" onclick="return checkSelectForRevoke(\'DocAskLeave\');">&nbsp;&nbsp;' -->
	</c:if>
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
<c:if test="${DocError != null}">
<script language="javascript">
	err = new String('${DocError}');
	window.alert(err);
</script>
</c:if>
