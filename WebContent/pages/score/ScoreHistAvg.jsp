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
<form action="/CIS/Score/ScoreHistAvg.do" method="post" name="histForm">
<table width="100%" cellpadding="0" cellspacing="0">
<input type="hidden" name="student_name" value="${ScoreAvgStuMap.studentName}">
<input type="hidden" name="student_class" value="${ScoreAvgStuMap.departClass}">
<input type="hidden" name="student_classname" value="${ScoreAvgStuMap.depClassName}">
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="scrTitle.ScoreHistAvgMaintain" bundle="SCR"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <!-- 
      <tr>
        <td height="33" colspan="6" align="left" valign="top" class="menuIndex"><c:out value="成績資料維護"/> -&gt; <span class="style2">歷年成績資料維護</span></td>
  	    </tr>
  	  -->
      <tr>
        <td  height="30" colspan="6" align="center" valign="middle">學號：
        <c:if test="${ScoreStuMap.studentNo != ''}">
          <input name="student_no" type="text" size="7" maxlength="10" value="${ScoreAvgStuMap.studentNo}" />
        </c:if>
        <c:if test="${ScoreStuMap.studentNo == ''}">
          <input name="student_no" type="text" size="7" maxlength="10" />
        </c:if>
          <!-- <img src="spacer.gif" alt="spacer" width="20" height="6"/>姓名：
          <input name="student_name" type="text" size="10" maxlength="20" /> -->
          <img src="spacer.gif" alt="spacer" width="20" height="6"/>學年：
          <input name="school_year" type="text" size="3" maxlength="5" value="${ScoreAvgStuMap.schoolYear}" />
          <img src="spacer.gif" alt="spacer" width="20" height="6"/>學期：
          <input name="school_term" type="text" size="2" maxlength="1" value="${ScoreAvgStuMap.schoolTerm}"/>
        </td>
      </tr>
      <!-- 
      <tr>
        <td  height="34" colspan="6" align="center" valign="middle" bgcolor="#CCCCFF">
			<Input type="submit" name="scrHistAction" value="<bean:message key='score.histadd' bundle='SCR'/>" >
          <img src="spacer.gif" alt="spacer" width="20" height="6"/>
		<Input type="submit" name="scrHistAction" value="<bean:message key='score.histqry' bundle='SCR'/>" >
        </tr>
      <tr>
        <td  height="33" colspan="6" align="left" valign="middle">&nbsp;</td>
        </tr>
      -->
	</table>
		</td>		
	</tr>
	
	<script>generateTableBanner('<Input type="submit" name="scrHistAction" value="<bean:message key='score.histadd' bundle='SCR'/>" >&nbsp;&nbsp;'
							  + '<Input type="submit" name="scrHistAction" value="<bean:message key='score.histqry' bundle='SCR'/>" >');</script>
	
	<!-- Test if have Query Result  -->
	<c:if test="${ScoreAvgInfoList != null}" >
	    <tr><td height="10"></td></tr>
	    <tr><td height="10"><font class="blue_13">&nbsp;
		    <c:out value="${ScoreAvgStuMap.studentNo}"/>&nbsp;&nbsp;	    	
	    	<c:out value="${ScoreAvgStuMap.studentName}" />&nbsp;&nbsp;
	    	<c:out value="${ScoreAvgStuMap.depClassName}" /></font>
	    </td></tr>
	    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
		<tr><td><table width="100%" cellpadding="0" cellspacing="0">
		 <tr><td align="center">  
	      <display:table name="${ScoreAvgInfoList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	  		<c:if test="${empty ScoreAvgInfoList}">
	     		<%@ include file="../include/NoBanner.jsp" %>
	  		</c:if>
	        <display:column title="<script>generateTriggerAll(${fn:length(ScoreAvgInfoList)}, 'ScoreHistAvg');</script>" class="center" >
	          <script>generateCheckbox("${row.oid}", "ScoreHistAvg");</script></display:column>
 	        <display:column title="學年"		property="schoolYear"		sortable="true" class="center" />
	        <display:column title="學期"		property="schoolTerm"		sortable="true" class="center" />
	        <display:column title="當學期總修習學分數" 	property="totalCredit"			sortable="true" class="center" />
	        <display:column title="分數" 	property="score"			sortable="true" class="right" />
	      </display:table></td></tr>	      
	      			</table>
	      		</td>
	      	</tr>
	<script>
	generateTableBanner('<INPUT type="submit" name="scrHistAction" value="<bean:message key='score.histdel' bundle='SCR'/>" onclick="return checkSelectForDelete(\'ScoreHistAvg\');">&nbsp;&nbsp;'+
	'<INPUT type="submit" name="scrHistAction" value="<bean:message key='score.histmodify' bundle='SCR'/>" onclick="return checkSelectForModify(\'ScoreHistAvg\');">');
	</script>
	</c:if>
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
