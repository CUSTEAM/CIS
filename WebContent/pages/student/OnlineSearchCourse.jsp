<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">

</script>
<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Student/OnlineAddDelCourse" method="post">
	<script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.MaintainStudentInfo" bundle="REG"/></B></div>');</script>
	<tr>
		<td>
	    	<table width="100%" cellspacing="5" class="empty-border">
				<tr>
					<td colspan="2"><bean:message key="Class"/>&nbsp;
	  			   		<c:set var="campusSel" value="${StudentListForm.map.campusInCharge2}"/>
	  			   		<c:set var="schoolSel" value="${StudentListForm.map.schoolInCharge2}"/>
	  			   		<c:set var="deptSel" value="${StudentListForm.map.deptInCharge2}"/>
	  			   		<c:set var="classSel" value="${StudentListForm.map.classInCharge2}"/>
	  			   		<%@ include file="/pages/include/ClassSelect2.jsp" %>
	  			   	</td>
	  			 	<td><bean:message key="Status" />&nbsp;
	  			   		<c:set var="statusSel" value="${StudentListForm.map.status2}"/>
	  			   		<%@ include file="/pages/include/StatusSelect2.jsp" %>
	  			   	</td>
	  			</tr>
			</table>
		</td>
	</tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Create'/>" >&nbsp;&nbsp;'
     						  + '<INPUT type="submit" name="method" value="<bean:message key='Query' />" >');
    </script>
</html:form>
</table>