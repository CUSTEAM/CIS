<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Registration/StudentInfo" method="post">
	<script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.DeleteStudentInfo" bundle="REG"/></B></div>');</script>	  
	  <tr height="50"><td align="center" class="blue_15">
	  	下列學生因有案未結而保留未刪&nbsp;
	  	<input type="submit" name="method" value="<bean:message key='Continue'/>" class="CourseButton"></td></tr>
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr><td><table width="100%" cellpadding="0" cellspacing="0">
		 <tr><td align="center">
	      <display:table name="${UndeletedStudentInfo}" export="false" id="row" pagesize="10000" sort="list" excludedParams="*" class="list">
	        <%@ include file="../include/NoBanner.jsp" %>
	        <display:column titleKey="Student.Name"		property="studentName"	sortable="true" class="center" />
	        <display:column titleKey="Student.No" 		property="studentNo" 	sortable="true" class="center" />
	        <display:column titleKey="Student.Class" 	property="departClass2"	sortable="true" />
	        <display:column title="未 結 事 項" 	property="undeleteReason"	sortable="true" />
	      </display:table></td></tr>
	    </table></td></tr>
       	<script>generateTableBanner('');</script>
  </html:form>
</table>	      	
	  