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

<form action="/CIS/StudAffair/StudBonusPenalty.do" method="post" name="tfdForm">

<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="0" cellspacing="0">
<script>generateTableBanner('<bean:message key="SAFTitle.StudBonusPenaltyDelete" bundle="SAF"/>');</script>	  
	<tr>
		<td width="100%" align="center" valign="top" class="decorate">
<!-- End Content Page Table Header -->

	<table width="100%" border="0" cellpadding="5" cellspacing="1">
      <!--DWLayoutTable-->
      <tr>
        <td height="33" colspan="6" align="left" valign="top" class="menuIndex"><c:out value="學生獎懲資料維護"/> -&gt; 
        <span class="style2">刪除確認
        </span></td>
  	  </tr>
	</table>
				
		</td>		
	</tr>
	<!-- Test if have Query Result  -->
	<c:if test="${StudBonusPenaltyDelete != null}" >
	    <tr><td height="10"></td></tr>
	    <c:if test="${BonusPenaltyStuMap != null}">
	    	<tr><td height="10"><font class="blue_13">&nbsp;
		    	<c:out value="${BonusPenaltyStuMap.studentNo}"/>&nbsp;&nbsp;	    	
	    		<c:out value="${BonusPenaltyStuMap.studentName}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<c:out value="${BonusPenaltyStuMap.departClass}" />&nbsp;&nbsp;
	    		<c:out value="${BonusPenaltyStuMap.depClassName}" /></font>
	    	</td></tr>
	    </c:if>
	    
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${StudBonusPenaltyDelete}" export="false" id="row" pagesize="100" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty StudBonusPenaltyDelete}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
 	        	<display:column title="日期"		property="sddate"		sortable="true" 	class="left" />
 	        	<display:column title="文號"		property="no"			sortable="true"  	class="center" />
	        	<display:column title="原因"		property="reason"		sortable="true"  	class="center" />
	        	<display:column title="種類1"	property="desdName1"	sortable="true"  	class="center" />
	        	<display:column title="次數1" 	property="cnt1" 		sortable="false" 	class="center" />
	        	<display:column title="種類2" 	property="desdName2" 	sortable="true"  	class="center" />
	        	<display:column title="次數2" 	property="cnt2"			sortable="false" 	class="center" />
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
