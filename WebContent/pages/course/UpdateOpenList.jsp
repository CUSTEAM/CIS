<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/Calendar.inc" %>
<script>
history.go(1);
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Course/BatchProcess" method="post">
	<script>generateTableBanner('<table align="left">' + '<tr><td align="left">' + '&nbsp;&nbsp;<img src="images/24-imageset-open.png"></td>'
		+ '<td>批次作業</td>' + '</tr></table>');
	</script>
	<c:if test="${selDtimes != null}">
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${selDtimes}" export="false" id="row" pagesize="100" sort="list" excludedParams="*" class="list">
		        <display:column titleKey="Course.label.className" property="ClassName" sortable="true" class="center" />
	        	<display:column titleKey="Course.label.classNo" property="ClassNo" sortable="true" class="center" />
	        	<display:column titleKey="Course.label.courseName"  property="chi_name" sortable="true" class="left" />
	        	<display:column titleKey="Course.label.courseNumber" property="cscode" sortable="true" class="center" />
	        	<display:column titleKey="Course.label.techName" property="cname" sortable="false" class="center" />
	        	<display:column titleKey="Course.label.opt" property="opt2" sortable="true" class="center" />
	        	<display:column titleKey="Course.label.credit" property="credit" sortable="true" class="center" />
	        	<display:column titleKey="Course.label.hours" property="thour" sortable="true" class="center" />
	        	<display:column titleKey="Course.label.open" property="openName" sortable="true" class="center" />
	      	</display:table>
		</td>
	</tr>
  	<script>
 		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='updateOpen'/>" class="CourseButton">&nbsp;' +
 							'<INPUT type="submit" name="method" value="<bean:message key='updateNotOpen'/>" class="CourseButton">&nbsp;' +
 						    '<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" class="CourseButton">&nbsp;');
 	</script>
	</c:if>
</html:form>
</table>