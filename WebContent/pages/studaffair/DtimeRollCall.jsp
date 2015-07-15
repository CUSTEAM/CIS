<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/score/include/scoreinput.js" %>

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
<form action="/CIS/StudAffair/DtimeRollCall.do" method="post" name="inputForm">
<table width="100%" cellpadding="0" cellspacing="0">

<!-- Begin Content Page Table Header -->
    <script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.DtimeRollCall" bundle="SAF"/></B></div>');</script>
	
<!-- End Content Page Table Header -->
	<tr><td>

	<table width="100%" cellspacing="5" class="empty-border">
      <tr>
        <td height="25" colspan="6" align="left" valign="bottom">
      	<font class="blue_13">請依序選擇校區、學制、科系、班級和科目</font>
      	</td>
      </tr>
      <tr><td>
      	開課班別：
	  		<c:set var="campusSel" value="${DtimeRollCallInit.campus}"/>
	  		<c:set var="schoolSel" value="${DtimeRollCallInit.school}"/>
	  		<c:set var="deptSel"   value="${DtimeRollCallInit.dept}"/>
	  		<c:set var="classSel"  value="${DtimeRollCallInit.clazz}"/>
			<%@ include file="/pages/studaffair/include/ClassSelectSAF2.jsp" %>
			<img src="spacer.gif" alt="spacer" width="20" height="6"/>科目：
          	<select name="cscode" onchange="document.forms[0].submit();">
          		<c:if test="${RollCallCourses != null }">
          		<option value="">選擇科目</option>
          		<c:forEach items="${RollCallCourses}" var="cscodes">
          			<c:if test="${DtimeRollCallInit.cscode==cscodes.cscode}">
          				<option name="cscode" value="${cscodes.cscode}" selected="selected">${cscodes.chiName}</option>
          			</c:if>
          			<c:if test="${DtimeRollCallInit.cscode!=cscodes.cscode}">
          				<option name="cscode" value="${cscodes.cscode}">${cscodes.chiName}</option>
          			</c:if>
          		</c:forEach>
          		</c:if>
          		<c:if test="${RollCallCourses == null }">
          			<option value=""></option>
          		</c:if>
          	</select>
        </td>
      </tr>
	</table>
		</td>		
	</tr>
	
	<!-- Test if have Query Result  -->
	<c:if test="${TermRollCall != null}" >
	    <tr><td height="10"></td></tr>
	    <tr><td>請勾選要上課的日期，沒勾選的表示不要點名 （${TermRollCallInfo.className}&nbsp;&nbsp;${TermRollCallInfo.courseName}）</td></tr>
	    
	    <%@include file="/pages/studaffair/include/Displaytag4Checkbox.inc"%>
		<tr>
		    <td><table width="380" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${TermRollCall}" export="false" id="row" pagesize="0" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty TermRollCall}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
	        	<display:column title="<script>generateTriggerAll(${fn:length(TermRollCall)}, 'TermRollCall');</script>" class="center" >
	          	<script>generateCheckbox("${row.oid}", "TermRollCall");</script></display:column>
 	        	<display:column title="上課日期"		property="tdate"	format="{0,date,yyyy-MM-dd}" sortable="true" 	class="center" />
 	        	<!-- display:column title="Oid"			property="oid"		sortable="true" 	class="center" / -->
 	        	<!-- display:column title="點名"			property="isRollCall"	sortable="true" 	class="center" / -->
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>
	
	<script>generateTableBanner('<input type="submit" name="method" value="<bean:message key='Save'/>">&nbsp;&nbsp;'
							  + '<input type="submit" name="method" value="<bean:message key='Cancel'/>">');</script>
	</c:if>

</table>
</form>
