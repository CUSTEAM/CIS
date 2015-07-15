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

<form action="/CIS/Teacher/Tutor/Counseling.do" method="post" name="bpForm">

<table width="100%" cellpadding="0" cellspacing="0">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="CounselingRecordDelete" bundle="TCH"/></B></div>');</script>	  
	<tr>
		<td align="left"> 
<!-- End Content Page Table Header -->

	<table cellspacing="5" class="empty-border" width="100%">
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${StudCounselDeleteT}" export="false" id="row" pagesize="100" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty StudCounselDeleteT}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
 	        	<display:column title="學生"			property="studentName"		sortable="true" 	class="left" />
 	        	<display:column title="輔導日期"		property="simpleCdate"		sortable="true"  	class="left" />
	        	<display:column title="輔導項目"		property="itemName"			sortable="true"  	class="left" />
	        	<display:column title="內容"			property="content"			sortable="true"  	class="left" />
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>
	    </table>
	 </td>
	</tr>
		
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='DeleteConfirm'/>" id="ok">&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" id="cancel" >');
	//generateTableBanner('<INPUT type="button" name="method" value="<bean:message key='Cancel'/>" id="cancel" onClick="chgopmode(0);document.forms[0].submit();">');
	</script>
</table>
</form>

<script>history.go(1);</script>