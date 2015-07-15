<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Teacher/Tutor/StudentInfo" method="post">
	<script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.QueryStudentInfo" bundle="REG"/></B></div>');</script>	  
		<tr><td>
	       <table width="100%" cellspacing="5" class="empty-border">
			 <tr><td><bean:message key="Name"     />&nbsp;
			 	   <input type="text" name="nameT" 	 size="12" value="${StudentListFormT.map.nameT}"></td>
			 	 <td><bean:message key="StudentNo"/>&nbsp;
			 	   <input type="text" name="studentNoT" size="12" value="${StudentListFormT.map.studentNoT}"></td>
			     <td><bean:message key="Idno"     />&nbsp;
			       <input type="text" name="idNoT"		 size="12" value="${StudentListFormT.map.idNoT}"></td></tr>
  			 <tr><td colspan="2"><bean:message key="Class"/>&nbsp;
	  			   <c:set var="campusSel" value="${StudentListFormT.map.campusInChargeT}"/>
	  			   <c:set var="schoolSel" value="${StudentListFormT.map.schoolInChargeT}"/>
	  			   <c:set var="deptSel"   value="${StudentListFormT.map.deptInChargeT}"/>
	  			   <c:set var="classSel"  value="${StudentListFormT.map.classInChargeT}"/>
	  			   <%@ include file="/pages/include/ClassSelectT.jsp" %></td>
	  			 <td><bean:message key="Status"    />&nbsp;
	  			   <c:set var="statusSel"  value="${StudentListFormT.map.status2}"/>
	  			   <%@ include file="/pages/include/StatusSelect2.jsp" %></td></tr>
     		</table></td></tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" class="CourseButton" value="<bean:message key='Query' />" >');</script>
	<c:if test="${StudentInfoListT != null}" >
	    <tr><td height="10"></td></tr>
	    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
		<tr><td><table width="100%" cellpadding="0" cellspacing="0">
		 <tr><td align="center">
	      <display:table name="${StudentInfoListT}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	        <c:if test="${empty StudentInfoListT}" >
	        	<%@ include file="../include/NoBanner.jsp" %>
	        </c:if>
	        <display:column title="<script>generateTriggerAll(${fn:length(StudentInfoListT)}, 'StudentInfo');</script>" class="center" >
	          <script>generateCheckbox("${row.oid}", "StudentInfo");</script></display:column>
	        <display:column titleKey="Student.Name"		property="studentName"	sortable="true" class="center" />
	        <display:column titleKey="Student.No" 		property="studentNo" 	sortable="true" class="center" />
	        <display:column titleKey="Student.Sex" 		property="sex2" 		sortable="true" class="center" />
	        <display:column title="電話" 	property="telephone"	sortable="true" class="center" />
	        <display:column title="手機" 	property="cellPhone"	sortable="true" class="center" />
	        <display:column title="Email" 	property="email"	sortable="true" class="center" />
	        <display:column title="備註" sortable="false" class="center">
	        	<html:link page="/Teacher/Tutor/StudentInfo.do?method=scoreHist" paramName="row" paramId="no" paramProperty="studentNo">
					[<font color="blue">歷年成績</font>]
				</html:link>
	        </display:column>
	      </display:table></td></tr>
	    </table></td></tr>
	    <script>
	   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Details'/>" class="CourseButton" onclick="return checkSelectForView(\'StudentInfo\')">&nbsp;' + 
	   							'<INPUT type="submit" name="method" value="<bean:message key='ClassCourse'/>" class="CourseButton">');
	   	</script>
	</c:if>
  </html:form>
</table>
