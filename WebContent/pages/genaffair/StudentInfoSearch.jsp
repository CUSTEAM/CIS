<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/GeneralAffair/StudentInfoSearch" method="post">
	<script>generateTableBanner('<div class="gray_15"><B><bean:message key="general.studentInfoSearch.banner" bundle="GEA"/></B></div>');</script>	  
	<tr>
		<td>
       		<table width="100%" cellspacing="5" class="empty-border">
       			<tr>
       				<td><bean:message key="Name" />&nbsp;
			 			<input type="text" name="name2"　size="12" value="${StudentListForm.map.name2}">
			 		</td>
			 	 	<td><bean:message key="StudentNo" />&nbsp;
			 	 		<input type="text" name="studentNo2" size="12" value="${StudentListForm.map.studentNo2}">
			 	 	</td>
			     	<td><bean:message key="Idno" />&nbsp;
			     		<input type="text" name="idNo2"　size="12" value="${StudentListForm.map.idNo2}">
			     	</td>
			    </tr>
		 		<tr>
		 			<td colspan="2"><bean:message key="Class"/>&nbsp;
  			   			<c:set var="campusSel" value="${StudentListFormT.map.campusInCharge2}" />
  			   			<c:set var="schoolSel" value="${StudentListFormT.map.schoolInCharge2}" />
  			   			<c:set var="deptSel" value="${StudentListFormT.map.deptInCharge2}" />
  			   			<c:set var="classSel" value="${StudentListFormT.map.classInCharge2}" />
  			   			<%@ include file="/pages/include/AllClassSelect2.jsp" %>
  			   		</td>  			 		
  			   	</tr>
    		</table>
    	</td>
    </tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Query'/>" class="CourseButton" >');
    	</script>
    	
	<c:if test="${StudentInfoList != null}" >
    <tr>
    	<td height="10">&nbsp;</td>
    </tr>
    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
	 			<tr>
	 				<td align="center">
      					<display:table name="${StudentInfoList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
        				<c:if test="${empty StudentInfoList}" >
        					<%@ include file="../include/NoBanner.jsp" %>
        				</c:if>
	        				<display:column titleKey="Student.Class" property="departClass2" sortable="true" class="center" />
	          				<display:column titleKey="Student.No" property="studentNo" sortable="true" class="center" />
	        				<display:column titleKey="Student.Name"	property="studentName" sortable="true" class="center" />	
	        				<display:column title="性別"	property="sex2" sortable="true" class="center" />        					        				
	        				<display:column titleKey="Student.telephone" property="telephone" sortable="true" class="center" />
	        				<display:column titleKey="Student.cellPhone" property="cellPhone" sortable="true" class="center" />
	        				<display:column titleKey="Student.email" property="email" sortable="true" class="left" />	        				
      					</display:table>
      				</td>
      			</tr>
      		</table>
    	</td>
	</tr>
	</c:if>
</html:form>
</table>
