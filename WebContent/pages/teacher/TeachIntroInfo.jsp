<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/TeachIntroductionSearch" method="post">
	<script>
		generateTableBanner('<div class="gray_15"><B><bean:message key="teacher.introductionSearch.banner" bundle="TCH"/></B></div>');
	</script>
	<tr>
		<td align="center">
	       	<table cellspacing="2" class="empty-border">
	       		<tr>
	       			<td colspan="3"><bean:message key="Course.label.className" />：&nbsp;${sessionScope.courseInfo.className}</td>
	       		</tr>
	       		<tr>	
	       			<td colspan="3"><bean:message key="Course.label.courseName" />：&nbsp;${sessionScope.courseInfo.chiName}</td>
	       		</tr>
	       		<tr>
	       			<td colspan="3">
	       				<bean:message key="Course.label.courseEname" />：&nbsp;<input type="text" name="courseEName" readonly="true" size="60" maxlength="60" value="${sessionScope.courseInfo.engName}" />
	       			</td>
	       		</tr>
	       		<tr>
	       			<th colspan="3" align="center"><bean:message key="teacher.introduction.chiIntro" bundle="TCH" /></th>
				</tr>
				<tr>
					<td colspan="3"><textarea name="chiIntro" cols="80" rows="15" readonly="true">${requestScope.courseIntroInfo.chiIntro}</textarea></td>
				</tr>
				<tr height="30">
					<td colspan="3">&nbsp;</td>
				</tr>
				<tr>
					<th colspan="3" align="center"><bean:message key="teacher.introduction.engIntro" bundle="TCH" /></th>
				</tr>
				<tr>
					<td colspan="3"><textarea name="engIntro" cols="80" rows="15" readonly="true">${requestScope.courseIntroInfo.engIntro}</textarea></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="10"></td>
	</tr>
   	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Back' />" class="CourseButton">&nbsp;');
   	</script>
</html:form>
</table>