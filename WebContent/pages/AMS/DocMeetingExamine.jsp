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

<script>
function submitConfirm(inputObj){
	if(event.keyCode<48 || event.keyCode>57){
		return false;
	}
	input = new String(inputObj.value);
	if(input.length >= 13){
		document.getElementById("askForm").submit();
	}
}
</script>
<!-- Begin Content Page Table Header -->
<form action="/CIS/AMS/DocMeetingExamine.do" method="post" name="askForm" id="askForm">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="AMSTitle.DocMeetingExamine" bundle="AMS"/></B></div>');</script>	  
<!-- End Content Page Table Header onKeyup="submitConfirm(this);"-->
	<tr><td><table width="100%">
	<tr>
		<td width="105" class="title">請輸入條碼：</td>
		<td class="input"><input type="text" name="sn" id="sn" class="mustInput" value="" ></td>
	</tr>
	</table></td></tr>
	<script>
	// generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='OK' bundle='AMS'/>" >');
	</script>
	<!-- Test if have Query Result  -->

	<c:if test="${not empty meetingExamined}" >
	    <tr><td><table width="100%">
	    <tr><td height="10"></td></tr>
	    <tr><td height="10" style="background-color: #99ccff;"><font color="blue">正常申請單</font></td></tr>
	    
	    <%@include file="/pages/AMS/include/Displaytag4Checkbox.inc"%>
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${meetingExamined}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty meetingExamined}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
 	        	<display:column title="申請人"			property="emplName"				sortable="true" 	class="left" />
 	        	<display:column title="假別"				property="askleaveName"			sortable="true" 	class="left" />
 	        	<display:column title="條碼"				property="askCode"				sortable="true" 	class="left" />
 	        	<display:column title="會議(集會)日期"		property="meetingDate"			format="{0,date,yyyy-MM-dd hh:mm:ss}" sortable="true" 	class="left" />
 	        	<display:column title="會議名稱"			property="meetingName"			sortable="true"  	class="left" />
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>
	    <tr><td height="10"></td></tr>
	    </table></td></tr>
	</c:if>
			
<!-- Begin Content Page Table Footer -->
</table>		
</form>
<script>
window.onLoad = document.getElementById("sn").focus();
</script>
