<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/Calendar.inc" %>
<script>
history.go(1);
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Course/BatchProcessStdList" method="post">
	<script>
		generateTableBanner('<div class="gray_15"><B> 選 修 學 生 清 單 </B></div>');
	</script>
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${seldStdList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
		        <%@ include file="../include/NoBanner.jsp" %>
		        <display:column titleKey="Student.No" property="studentNo" sortable="false" class="center" />
		        <display:column titleKey="Student.Name" property="studentName" sortable="false" class="center" />
		        <display:column titleKey="Student.Sex"  property="sex2" sortable="false" class="center" />
		        <display:column titleKey="Course.label.classNo" property="departClass" sortable="false" class="center" />
		        <display:column titleKey="Course.label.className" property="departClass2" sortable="false" class="center" />		        
		    </display:table>
		</td>
	</tr>
  	<script>
  		generateTableBanner('<html:submit property="method" styleClass="CourseButton"><bean:message key="Back"/></html:submit>');			  
 	</script>
</html:form>
</table>