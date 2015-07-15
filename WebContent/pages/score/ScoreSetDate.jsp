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
<!-- Begin Content Page Table Header -->
<form action="/CIS/Score/ScoreSetDate.do" method="post" name="histForm">
<table width="100%" cellpadding="0" cellspacing="0">
<input type="hidden" name="level" value="${ScoreSetDateLevel}">
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="scrTitle.ScoreSetDate" bundle="SCR"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<!-- Test if have Query Result  -->
	<c:if test="${ScoreSetDate != null}" >
	    <%@include file="/pages/studaffair/include/Displaytag4Checkbox.inc"%>
		<tr>
		<td><font color="red">類別 (1:期中, 2:期末, 3:畢業, 4:暑修, 5:操行, 6:教學評量)<br>
		日期輸入格式:xx/xx/xx     時間輸入格式xx:xx:xx<br>
		學制輸入格式:11:台北日間 12:台北進修 13:台北學院 21:新竹日間 22:新竹進修</font>
		</td>
		</tr>
		<tr><td><table width="100%" cellpadding="0" cellspacing="0">
		 <tr><td align="center">  
	      <display:table name="${ScoreSetDate}" export="false" id="row" pagesize="0" sort="list" excludedParams="*" class="list">
	  		<c:if test="${empty ScoreSetDate}">
	     		<%@ include file="../include/NoBanner.jsp" %>
	  		</c:if>
	        <display:column title="<script>generateTriggerAll(${fn:length(ScoreSetDate)}, 'ScoreSetDate');</script>" class="center" >
	          <script>generateCheckbox("${row.oid}", "ScoreSetDate");</script></display:column>
 	        <display:column title="類別"		property="levelName"		sortable="true" class="center" />
	        <display:column title="學制"		property="departName"		sortable="true" class="center" />
	        <display:column title="開始日期"	property="beginDate"		sortable="true" class="center" />
	        <display:column title="開始時間" 	property="beginTime" 			sortable="true" class="center" />
	        <display:column title="終止日期" 	property="endDate" 				sortable="true" class="center" />
	        <display:column title="終止時間" 	property="endTime"			sortable="true" class="center" />
 	      </display:table></td></tr>	      
	      			</table>
	      		</td>
	      	</tr>
	</c:if>
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Create'/>" >&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Delete'/>" onclick="return checkSelectForDelete(\'ScoreSetDate\');">&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Modify'/>" onclick="return checkSelectForModify(\'ScoreSetDate\');">');
	</script>
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
