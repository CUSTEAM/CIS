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

<form action="/CIS/StudAffair/StudInspected.do" method="post" name="inForm">
<input type="hidden" name="opmode" value="" id="opmode">	

<c:set var="tcnt" value="1"/>

<table width="100%" cellpadding="0" cellspacing="0">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.StudInspectedDelete" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td align="left"> 
<!-- End Content Page Table Header -->

	<table cellspacing="5" class="empty-border" width="100%">
		<tr>
			<td colspan="3">確認刪除以下資料 ：</td>
        </tr>
        
	<c:if test="${StudInspectedDelete != null}">
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${StudInspectedDelete}" export="false" id="row" pagesize="0" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty StudInspectedList}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
 	        	<display:column title="班級"		property="departClass"		sortable="true" 	class="left" />
 	        	<display:column title="班級名稱"	property="deptClassName"	sortable="true" 	class="left" />
 	        	<display:column title="學號"		property="studentNo"		sortable="true" 	class="left" />
 	        	<display:column title="姓名"		property="studentName"		sortable="true"  	class="left" />
 	        	<display:column title="定察年度"	property="downYear"			sortable="true"  	class="left" />
	        	<display:column title="定察學期"	property="downTerm"			sortable="false"  	class="left" />
	        	<display:column title="註銷年度"	property="upYear"			sortable="true"  	class="left" />
	        	<display:column title="註銷學期" 	property="upTerm" 			sortable="false" 	class="left" />
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>

	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='DeleteConfirm'/>">&nbsp;&nbsp;' + 
						'<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>">');
	</script>
	</c:if>
		
		</table>
	  </td>
	</tr>		
	
<script>history.go(1);</script>
</form>