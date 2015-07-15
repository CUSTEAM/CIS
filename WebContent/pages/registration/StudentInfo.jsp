<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Registration/StudentInfo" method="post">
	<script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.MaintainStudentInfo" bundle="REG"/></B></div>');</script>	  
		<tr><td>
	       <table width="100%" cellspacing="5" class="empty-border">
			 <tr><td><bean:message key="Name"     />&nbsp;
			 		<input type="text" name="name2" 	 size="12" value="${StudentListForm.map.name2}"></td>
			 	 <td><bean:message key="StudentNo"/>&nbsp;
			 	 	<input type="text" name="studentNo2" size="12" value="${StudentListForm.map.studentNo2}"></td>
			     <td><bean:message key="Idno"     />&nbsp;
			     	<input type="text" name="idNo2"		 size="12" value="${StudentListForm.map.idNo2}"></td></tr>
  			 <tr><td colspan="2"><bean:message key="Class"/>&nbsp;
	  			   <c:set var="campusSel" value="${StudentListForm.map.campusInCharge2}"/>
	  			   <c:set var="schoolSel" value="${StudentListForm.map.schoolInCharge2}"/>
	  			   <c:set var="deptSel"   value="${StudentListForm.map.deptInCharge2}"/>
	  			   <c:set var="classSel"  value="${StudentListForm.map.classInCharge2}"/>
	  			   <%@ include file="/pages/include/ClassSelect2.jsp" %></td>
	  			 <td><bean:message key="Status"    />&nbsp;
	  			   <c:set var="statusSel"  value="${StudentListForm.map.status2}"/>
	  			   <%@ include file="/pages/include/StatusSelect2.jsp" %></td></tr>
     		</table></td></tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Query'/>" class="CourseButton" >&nbsp;&nbsp;'
     						  + '<INPUT type="submit" name="method" value="<bean:message key='Create' />" class="CourseButton" >');</script>
	<c:if test="${StudentInfoList != null}" >
	    <tr><td height="10"></td></tr>
	    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
		<tr><td><table width="100%" cellpadding="0" cellspacing="0">
		 <tr><td align="center">
	      <display:table name="${StudentInfoList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	        <c:if test="${empty StudentInfoList}" >
	        	<%@ include file="../include/NoBanner.jsp" %>
	        </c:if>
	        <display:column title="<script>generateTriggerAll(${fn:length(StudentInfoList)}, 'StudentInfo');</script>" class="center" >
	          <script>generateCheckbox("${row.oid}", "StudentInfo");</script></display:column>
	        <display:column titleKey="Student.Name"		property="studentName"	sortable="true" class="center" />
	        <display:column titleKey="Student.No" 		property="studentNo" 	sortable="true" class="center" />
	        <display:column titleKey="Student.Sex" 		property="sex2" 		sortable="true" class="center" />
	        <display:column titleKey="Student.Class" 	property="departClass2"	sortable="true" />
	        <display:column titleKey="Student.Idno" 	property="idno"			sortable="true" class="center" />
	        <display:column titleKey="Student.Status" 	property="occurStatus2"	sortable="true" class="center" />
	      </display:table></td></tr>
	    </table></td></tr>
       	<script>
       		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Delete'/>" class="CourseButton" onclick="return checkSelectForDelete(\'StudentInfo\');">&nbsp;&nbsp;'+
       							'<INPUT type="submit" name="method" value="<bean:message key='Modify'/>" class="CourseButton" onclick="return checkSelectForModify(\'StudentInfo\');">&nbsp;&nbsp;'+
       							'<INPUT type="submit" name="method" value="<bean:message key='View'/>"   class="CourseButton" onclick="return checkSelectForView(\'StudentInfo\');"');
       	</script>	      	
	</c:if>
  </html:form>
</table>
