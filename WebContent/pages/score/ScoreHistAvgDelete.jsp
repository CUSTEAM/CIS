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

<form action="/CIS/Score/ScoreHistAvg.do" method="post" name="histForm">

<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="0" cellspacing="0">
<script>generateTableBanner('<bean:message key="scrTitle.ScoreMenu" bundle="SCR"/>');</script>	  
	<tr>
		<td width="100%" align="center" valign="top" class="decorate">
<!-- End Content Page Table Header -->

	<table width="100%" border="0" cellpadding="5" cellspacing="1">
      <!--DWLayoutTable-->
      <tr>
        <td height="33" colspan="6" align="left" valign="top" class="menuIndex"><c:out value="成績資料維護"/> -&gt;歷年平均成績資料維護 -&gt; 
        <span class="style2">刪除確認
        </span></td>
  	    </tr>
      <tr>
        <td  height="35" colspan="6" align="left" valign="middle" bgcolor="#CCCCFF">學號：
        	<c:out value="${ScoreAvgStuMap.studentNo}"/>&nbsp;&nbsp;
				 	 <font class="blue_13"><c:out value="${ScoreAvgStuMap.studentName}" />&nbsp;
				 	 					   <c:out value="${ScoreAvgStuMap.depClassName}"/></font>
		</td>
      </tr>
	</table>
				
		</td>		
	</tr>
	<!-- Test if have Query Result  -->
	<c:if test="${ScoreHistAvgDelete != null}" >
	    <tr><td height="10"></td></tr>
		<tr><td><table width="100%" cellpadding="0" cellspacing="0">
		 <tr><td align="center">  
	      <display:table name="${ScoreHistAvgDelete}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	  		<c:if test="${empty ScoreHistAvgDelete}">
	     		<%@ include file="../include/NoBanner.jsp" %>
	  		</c:if>
 	        <display:column title="學年"		property="schoolYear"		sortable="true" class="center" />
	        <display:column title="學期"		property="schoolTerm"		sortable="true" class="center" />
	        <display:column title="學分" 	property="totalCredit"			sortable="true" class="center" />
	        <display:column title="分數" 	property="score"			sortable="true" class="right" />
 	      </display:table></td></tr>	      
	      			</table>
	      		</td>
	      	</tr>
<!-- Begin Content Page Table Footer -->
	<script>
	generateTableBanner('<INPUT type="submit" name="scrHistAction" value="<bean:message key='score.histdelconfirm' bundle='SCR'/>" >&nbsp;&nbsp;'+
	'<INPUT type="submit" name="scrHistAction" value="<bean:message key='score.histdelcancel' bundle='SCR'/>" >');
	</script>
</c:if>
	
</table>
<script>history.go(1);</script>
<!-- End Content Page Table Footer -->
</form>
