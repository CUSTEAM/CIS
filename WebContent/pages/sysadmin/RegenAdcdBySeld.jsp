<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/SysAdmin/RegenAdcdBySeld" method="post">
	<script>generateTableBanner('<div class="gray_15"><B>重&nbsp;&nbsp;&nbsp;&nbsp;建&nbsp;&nbsp;&nbsp;&nbsp;加&nbsp;&nbsp;&nbsp;&nbsp;退&nbsp;&nbsp;&nbsp;&nbsp;選&nbsp;&nbsp;&nbsp;&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;單</B></div>');</script>	  
		<tr><td align="center">
	       <table cellspacing="5" class="empty-border">
			 <tr><td>
			      學期&nbsp;
   			   <input type="text" name="term" size="1" value="${RegenAdcdBySeldForm.term}">
   			   &nbsp;&nbsp;&nbsp;&nbsp;
			   <bean:message key="Class"/>&nbsp;
  			   <c:set var="campusSel" value="${RegenAdcdBySeldForm.campusInCharge}"/>
  			   <c:set var="schoolSel" value="${RegenAdcdBySeldForm.schoolInCharge}"/>
  			   <c:set var="deptSel"   value="${RegenAdcdBySeldForm.deptInCharge}"/>
  			   <c:set var="classSel"  value="${RegenAdcdBySeldForm.classInCharge}"/>
   			   <%@ include file="/pages/include/AllClassSelect1.jsp" %></td></tr>
     	   </table></td></tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" class="CourseButton" value="<bean:message key='OK'/>" >');</script>
  </html:form>
</table>
