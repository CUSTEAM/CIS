<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="${sessionScope.actionName}" method="post">
	<script>
		generateTableBanner('<div class="gray_15"><B>加 選 衝 堂 之 課 程</B></div>');
	</script>
	<tr>
		<td align="center">
	       	<table cellspacing="2" class="empty-border">
	       		<tr>
					<td>班級：&nbsp;${classInfo}</td>	
					<td width="20">&nbsp;</td>
					<td>科目：&nbsp;${csnoInfo.chiName}</td>					
				</tr>
			</table>	
		</td>
	</tr>	
	<script>
		generateTableBanner(
			<%--'<INPUT type="submit" name="method" id="s1" value="<bean:message key='course.onlineAddRemoveCourse.conflictAdd' bundle="COU" />" class="CourseButton">&nbsp;&nbsp;' +--%>
			'<INPUT type="submit" name="method" value="<bean:message key='Cancel' />" class="CourseButton">'
		);
	</script>
</html:form>
</table>