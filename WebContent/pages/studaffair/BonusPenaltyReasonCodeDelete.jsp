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

<form action="/CIS/StudAffair/BonusPenaltyReasonCode.do" method="post" name="tfdForm">

<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="0" cellspacing="0">
<script>generateTableBanner('<bean:message key="SAFTitle.BonusPenaltyReasonCodeDelete" bundle="SAF"/>');</script>	  
	<tr>
		<td width="100%" align="center" valign="top" class="decorate">
<!-- End Content Page Table Header -->

	<table width="100%" border="0" cellpadding="5" cellspacing="1">
      <!--DWLayoutTable-->
      <tr>
        <td height="33" colspan="6" align="left" valign="top" class="menuIndex"><c:out value="操行評語維護"/> -&gt; 
        <span class="style2">刪除確認
        </span></td>
  	  </tr>
	</table>
				
		</td>		
	</tr>
	<!-- Test if have Query Result  -->
	<c:if test="${BonusPenaltyReasonCodeDelete != null}" >
	    <tr><td height="10"></td></tr>
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${BonusPenaltyReasonCodeDelete}" export="false" id="row" pagesize="100" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty BonusPenaltyReasonCodeDelete}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
 	        	<display:column title="代碼"		property="no"		sortable="true" class="left" />
	        	<display:column title="原因"		property="name"		sortable="true" class="left" />
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
