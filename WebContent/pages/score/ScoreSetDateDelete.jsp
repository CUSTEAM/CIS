<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
}
-->
</style>
<style type="text/css">
<!--
.style2 {color: #FF0000}
-->
</style>

<form action="/CIS/Score/ScoreSetDate.do" method="post" name="sddForm">

<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="0" cellspacing="0">
<script>generateTableBanner('<bean:message key="Title.ScoreSetDateDelete" bundle="SCR"/>');</script>	  
<!-- End Content Page Table Header -->

	<!-- Test if have Query Result  -->
	<c:if test="${ScoreSetDateDelete != null}" >
	    
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${ScoreSetDateDelete}" export="false" id="row" pagesize="100" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty ScoreSetDateDelete}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
	 	        <display:column title="類別"		property="level"		sortable="false" class="center" />
		        <display:column title="學制"		property="depart"		sortable="false" class="center" />
		        <display:column title="開始日期"	property="beginDate"	sortable="false" class="center" />
		        <display:column title="開始時間" 	property="beginTime" 	sortable="false" class="center" />
		        <display:column title="終止日期" 	property="endDate" 		sortable="false" class="center" />
		        <display:column title="終止時間" 	property="endTime"		sortable="false" class="center" />
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>
<!-- Begin Content Page Table Footer -->
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='DeleteConfirm'/>" >&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" >');
	</script>
</c:if>
	
</table>
<script>history.go(1);</script>
<!-- End Content Page Table Footer -->
</form>
