<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Registration/SetupClassGroup" method="post">
	<script>generateTableBanner('<div class="gray_15"><B>設&nbsp;&nbsp;&nbsp;&nbsp;定&nbsp;&nbsp;&nbsp;&nbsp;組&nbsp;&nbsp;&nbsp;&nbsp;別</B></div>');</script>	  
		<tr><td align="center">
	       <table cellspacing="5" class="empty-border">
			 <tr><td><bean:message key="Class"/>&nbsp;
  			   <c:set var="campusSel" value="${SetupClassGroup.campusInCharge}"/>
  			   <c:set var="schoolSel" value="${SetupClassGroup.schoolInCharge}"/>
  			   <c:set var="deptSel"   value="${SetupClassGroup.deptInCharge}"/>
  			   <c:set var="classSel"  value="${SetupClassGroup.classInCharge}"/>
   			   <%@ include file="/pages/include/ClassSelect.jsp" %></td></tr>
		 	 <tr><td><bean:message key="Group"  bundle="REG"/>&nbsp;
  		 	   <c:set var="codeId" 		    value="group"/>
  		 	   <c:set var="codeSel"		    value="groupSel"/>
  		 	   <c:set var="codeList"		value="${StudentGroup}"/>
   		 	   <c:set var="codeIdInitValue" value="${SetupClassGroup.group}"/>
  		 	   <%@ include file="/pages/include/CodeSelection.js" %>
  		 	   <%@ include file="/pages/include/CodeSelect.jsp" %></td></tr>
     	   </table></td></tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" class="CourseButton" value="<bean:message key='OK'/>" >&nbsp;&nbsp;'
     						  + '<INPUT type="submit" name="method" class="CourseButton" value="<bean:message key='Cancel' />" >');</script>
  </html:form>
</table>
