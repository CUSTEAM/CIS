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
<form action="/CIS/StudAffair/StudPublicAbsenceExam.do" method="post" name="askForm">
<input name="examMode" type="hidden" value="${StudPublicDocExamInit.examMode}"/>
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="Title.StudentAbsenceExamine" bundle="SAF"/></B></div>');</script>	  
<!-- End Content Page Table Header -->
	
	<!-- Test if have Query Result  -->
	<c:if test="${StudPublicDocs4Exam != null}" >
	    <tr><td height="10"></td></tr>
	    <tr><td>要審核的公假單</td></tr>
	    <tr><td><font color=red>如果您在下列的審核清單中，看不到電子郵件所通知應該要審核的假單，可能是該假單已取消送核或已刪除！</font></td></tr>
	    
	    <%@include file="/pages/student/include/Displaytag4Checkbox.inc"%>
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${StudPublicDocs4Exam}" export="false" id="row" pagesize="0" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty StudPublicDocs4Exam}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
	        	<display:column title="<script>generateTriggerAll(${fn:length(StudPublicDocs4Exam)}, 'StudPublicDocExam');</script>" class="center" >
	          	<script>generateCheckbox("${row.oid}", "StudPublicDocExam");</script></display:column>
	        	<display:column title="單位"			property="applierUnit"		sortable="true"  	class="center" />
	        	<display:column title="申請人"		property="applierName"		sortable="true"  	class="center" />
	        	<display:column title="班級"			property="deptClassName"	sortable="true"  	class="center" />
	        	<display:column title="事由"			property="reason"			sortable="true"  	class="center" />
 	        	<display:column title="開始日期"		property="startDate"		format="{0,date,yyyy-MM-dd}" sortable="true" 	class="center" />
 	        	<display:column title="開始節次"		property="startPeriod"		sortable="true" 	class="center" />
 	        	<display:column title="結束日期"		property="endDate"			format="{0,date,yyyy-MM-dd}" sortable="true"  	class="center" />
 	        	<display:column title="結束節次"		property="endPeriod"		sortable="true"  	class="center" />
	        	<!-- display:column title="日數" 		property="totalDay" 		sortable="false" / -->
	        	<display:column title="填寫日期"		property="createDate"		format="{0,date,yyyy-MM-dd}" sortable="true"  	class="center" />
	        	<display:column title="處理狀態" 		property="statusName" 		sortable="true" 	class="center" />
	        	<display:column title="處理時間" 		property="processDate" 		format="{0,date,yyyy-MM-dd HH:mm:ss}"	sortable="true" 	class="center" />
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>
	<script>
	generateTableBanner('<Input type="submit" name="method" value="<bean:message key='Examine' bundle='SAF' />" ' +
	' onclick="return checkSelectFor(\'StudPublicDocExam\', \'<bean:message key='Examine' bundle='SAF'/>\');">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +
	'<INPUT type="submit" name="method" value="<bean:message key='Back' bundle='AMS'/>">');
	</script>
	</c:if>
	
	<!-- Examined Docs  -->
	<c:if test="${not empty StudPublicDocsExamed}" >
	    <tr><td height="10"></td></tr>
	    <tr><td>審核過的公假單</td></tr>
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${StudPublicDocsExamed}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty StudPublicDocsExamed}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
	        	<display:column title="單位"			property="applierUnit"		sortable="true"  	class="center" />
	        	<display:column title="申請人"		property="applierName"		sortable="true"  	class="center" />
	        	<display:column title="班級"			property="deptClassName"	sortable="true"  	class="center" />
	        	<display:column title="事由"			property="reason"			sortable="true"  	class="center" />
 	        	<display:column title="開始日期"		property="startDate"		format="{0,date,yyyy-MM-dd}" sortable="true" 	class="center" />
 	        	<display:column title="開始節次"		property="startPeriod"		sortable="true" 	class="center" />
 	        	<display:column title="結束日期"		property="endDate"			format="{0,date,yyyy-MM-dd}" sortable="true"  	class="center" />
 	        	<display:column title="結束節次"		property="endPeriod"		sortable="true"  	class="center" />
	        	<!-- display:column title="日數" 		property="totalDay" 		sortable="false" /-->
	        	<display:column title="審核日期"		property="exam.examDate"	format="{0,date,yyyy-MM-dd}" sortable="true"  	class="center" />
	        	<display:column title="審核狀態" 		property="exam.statusName" 	sortable="true" 	class="left" />
	        	<display:column title="說明" 		property="exam.description" sortable="false" 	class="left" />
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>
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
