<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<script type="text/javascript">
history.go(1);
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="${requestScope.actionName}" method="post">
	<tr>
		<td>
			<table width="100%" cellspacing="0" border="0">
				<tr height="40" bordercolor="black">
       				<th align="center"><font size="+1"><strong>中  華  科 技 大 學  <font color="red">${requestScope.departmentInfo.chiName}</font> 中  英  文  課  程  簡  介</strong></font></th>
       			</tr>
       			<tr height="40" bordercolor="black">
       				<td align="center"><font size="+1">China University of Science and Technology</font></td>
       			</tr>
       			<tr height="20" bordercolor="black">
       				<td align="center"><font size="+1">Department：<font color="red">${requestScope.departmentInfo.engName}</font> Course Introduction</font></td>
       			</tr>
			</table>
       		<table width="100%" cellspacing="0" border="1">       			
		 		<tr height="40" bordercolor="black">
		 			<td width="22%" align="center"><bean:message key="student.viewCourseIntro.courseName" bundle="STD" /></td>
		 	 		<td width="13%" align="center"><bean:message key="student.viewCourseIntro.teacherName" bundle="STD" /></td>
		     		<td width="13%" align="center"><bean:message key="student.viewCourseIntro.division" bundle="STD" /></td>
		     		<td width="13%" align="center"><bean:message key="student.viewCourseIntro.program" bundle="STD" /></td>
		     		<td width="13%" align="center"><bean:message key="student.viewCourseIntro.grade" bundle="STD" /></td>
		     		<td width="13%" align="center"><bean:message key="student.viewCourseIntro.credits" bundle="STD" /></td>
		     		<td width="13%" align="center"><bean:message key="student.viewCourseIntro.totalHour" bundle="STD" /></td>
		   		</tr>
		   		<tr height="40" bordercolor="black">
		 			<td align="center"><b><font color="red"><c:out value="${chiCourse}" /></font></b></td>
		 	 		<td align="center"><c:out value="${teacherChiName}" escapeXml="false" /></td>
		     		<td align="center"><c:out value="${chiDivision}" /></td>
		     		<td align="center"><c:out value="${chiProgram}" /></td>
		     		<td align="center"><c:out value="${gradeChi}" /></td>
		     		<td align="center"><c:out value="${credit}" /></td>
		     		<td align="center"><c:out value="${thour * 18}" /></td>
		   		</tr>
		   		<tr><td colspan="7"><bean:message key="student.viewCourseIntro.chiIntro" bundle="STD" />：</td></tr>
		   		<tr><td colspan="7"><c:out value="${chiIntro}" /></td></tr>
		   		<tr><td height="120"></td></tr>
    		</table>
    		<table width="100%" cellspacing="0" border="1">
		 		<tr height="40" bordercolor="black">
		 			<td width="22%" align="center">Course Title</td>
		 	 		<td width="13%" align="center">Lecturer</td>
		     		<td width="13%" align="center">Division</td>
		     		<td width="13%" align="center">Program</td>
		     		<td width="13%" align="center">Year</td>
		     		<td width="13%" align="center">Credits</td>
		     		<td width="13%" align="center">Total Hours</td>
		   		</tr>
		   		<tr height="40" bordercolor="black">
		 			<td align="center" width="200"><b><font color="red"><c:out value="${engCourse}" escapeXml="false" /></font></b></td>
		 	 		<td align="center"><c:out value="${teacherEngName}" escapeXml="false" /></td>
		     		<td align="center"><c:out value="${engDivision}" /></td>
		     		<td align="center"><c:out value="${engProgram}" /></td>
		     		<td align="center"><c:out value="${gradeEng}" /></td>
		     		<td align="center"><c:out value="${credit}" /></td>
		     		<td align="center"><c:out value="${thour * 18}" /></td>
		   		</tr>
		   		<tr><td colspan="7">Course Description (should not exceed 500 words)：</td></tr>
		   		<tr><td colspan="7"><c:out value="${engIntro}" /></td></tr>
		   		<tr><td height="120"></td></tr>
    		</table>
    	</td>
    </tr>
    <tr>
		<td colspan="3"><font size="+1" color="red">遵守智慧財產權觀念，不得非法影印</font></td>
   	</tr>
   	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Back' />" class="CourseButton">')
   	</script>
 </html:form>
</table>