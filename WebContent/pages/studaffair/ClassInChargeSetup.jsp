<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/${ClassInChargeSetup.baseDir}/ClassInChargeSetup4${ClassInChargeSetup.authority}" method="post">
	<script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.ClassInChargeSetup" bundle="ADM"/>&nbsp;(&nbsp;<bean:message key="${ClassInChargeSetup.authority}" bundle="ADM"/>&nbsp;)</B></div>');</script>	  
		<input type="hidden" name="empOid"  value="${ClassInChargeSetup.empOid}" >
		<tr><td align="center">
	       <table cellspacing="5" class="empty-border">
			 <tr><td>帳號&nbsp;
			 		<input type="text" name="idno" size="12" value="${ClassInChargeSetup.idno}" ></td></tr>
     	   </table></td></tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" class="CourseButton" value="<bean:message key='Query'/>" >');</script>
   <c:if test="${ClassInChargeSetup.found == true}">
		<tr><td height="25" valign="bottom" class="blue_13">&nbsp;
		  <c:out value="${ClassInChargeSetup.name}"/>&nbsp;
		  <c:out value="${ClassInChargeSetup.idno}"/></td></tr>
		<tr><td width="100%">
		  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	      <display:table name="${ClassList}" export="false" id="row" pagesize="10000" sort="list" excludedParams="*" class="list">
	        <%@ include file="../include/NoBanner.jsp" %>
	        <display:column title="<script>generateTriggerAll(${fn:length(ClassList)}, 'ClassesInCharge');</script>" class="center" >
	          <script>generateCheckbox("${row.oid}", "ClassesInCharge");</script></display:column>
	        <display:column title="班級代碼"	property="classNo"		sortable="true" class="center" />
	        <display:column title="班     級     全     名" 	property="classFullName" sortable="true" />
	        <display:column title="校區"		property="campusName"	sortable="true" class="center" />
	        <display:column title="部制" 	property="schoolName" 	sortable="true" class="center" />
	        <display:column title="系所" 	property="deptName" 	sortable="true" />
	        <display:column title="年級" 	property="grade"		sortable="true" class="center" />
	      </display:table></td></tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" class="CourseButton" value="<bean:message key='OK'/>" >&nbsp;&nbsp;'
							  + '<INPUT type="submit" name="method" class="CourseButton" value="<bean:message key='Cancel'/>" >');</script>
   </c:if>  
  </html:form>
</table>
