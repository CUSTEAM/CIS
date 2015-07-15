<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Registration/GraduateInfo" method="post">
	<script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.DeleteGraduateInfo" bundle="REG"/></B></div>');</script>	  
	  <tr height="50"><td align="center" class="blue_15">
	  	<bean:message key="DeleteStudentInfoVerify" bundle="REG"/>&nbsp;
	  	<input type="submit" name="method" value="<bean:message key='DeleteConfirm'/>" class="CourseButton">&nbsp;&nbsp;
	  	<input type="submit" name="method" value="<bean:message key='Cancel'/>" class="CourseButton"></td></tr>
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr><td><table width="100%" cellpadding="0" cellspacing="0">
		 <tr><td align="center">
	      <display:table name="${GraduateInfoDelete}" export="false" id="row" pagesize="10000" sort="list" excludedParams="*" class="list">
	        <%@ include file="../include/NoBanner.jsp" %>
	        <display:column title="<script>generateTriggerAll(${fn:length(StudentInfoDelete)}, 'GraduateDelete');</script>" class="center" >
	          <script>generateCheckbox("${row.oid}", "GraduateDelete");</script></display:column>
	        <display:column titleKey="Student.Name"		property="studentName"	sortable="true" class="center" />
	        <display:column titleKey="Student.No" 		property="studentNo" 	sortable="true" class="center" />
	        <display:column titleKey="Student.Sex" 		property="sex2" 		sortable="true" class="center" />
	        <display:column titleKey="Student.Class" 	property="departClass2"	sortable="true" />
	        <display:column titleKey="Student.Idno" 	property="idno"			sortable="true" class="center" />
	        <display:column titleKey="Student.Status" 	property="occurStatus2"	sortable="true" class="center" />
	      </display:table></td></tr>
	    </table></td></tr>
       	<script>generateTableBanner('');</script>
  </html:form>
</table>	      	
<script>history.go(1);</script>	  