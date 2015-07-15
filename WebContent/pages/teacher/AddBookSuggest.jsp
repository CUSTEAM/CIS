<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">

</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/TeachBookSuggest" method="post" focus="bookSuggest">
	<script>
		generateTableBanner('<div class="gray_15"><B><bean:message key="teacher.bookSuggest.banner" bundle="TCH"/></B></div>');
	</script>
	<tr>
		<td align="center">
	       	<table cellspacing="2" class="empty-border">
	       		<tr>
	       			<td><bean:message key="Course.label.className" />：&nbsp;${sessionScope.courseInfo.className}</td>
	       			<td colspan="2"><bean:message key="Course.label.courseName" />：&nbsp;${sessionScope.courseInfo.chiName}</td>	       			
	       		</tr>
	       		<tr>
	       			<td align="center" colspan="3"><bean:message key="teacher.bookSuggest.bookList" bundle="TCH" /></td>					
				</tr>
				<tr>
					<td colspan="3"><textarea name="bookSuggest" cols="50" rows="15">${requestScope.courseIntroInfo.bookSuggest}</textarea></td>			
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="10"></td>
	</tr>
   	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='OK' />" class="CourseButton">&nbsp;' +
   							'<INPUT type="submit" name="method" value="<bean:message key='Cancel' />" class="CourseButton">');
   	</script>
</html:form>
</table>