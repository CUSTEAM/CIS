<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
<!--
history.go(1);
function delConfirm() {
	if (confirm("確定刪除?")) 
		return true;
	else
		return false;	
}
//-->
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<html:form action="/Course/SetCourseNameForm" method="post" focus="courseNumber" onsubmit="init()">
		<script>
			generateTableBanner('<table align="left"><tr><td align="left">&nbsp;&nbsp;<img src="images/24-book-green-mark.png"></td><td><bean:message key="setCourse.title" bundle="COU"/></td></tr></table>');
		</script>
		<!-- check editMode -->
		<c:if test="${editMode==false}">
			<tr>
				<td>
					<table>
						<tr>
							<td>
								<!-- put search bar -->
								<table class="hairlineTable" align="left">
									<tr>
										<td class="hairlineTdF"><bean:message key="setCourse.label.courseNumber" bundle="COU" /></td>
										<td class="hairlineTd">
											<input type="text" name="courseNumber" size="12"
												value="${SetCourseNameForm.map.courseNumber}" 
												style="ime-mode:disabled">
										</td>
										<td class="hairlineTdF"><bean:message key="setCourse.label.courseName" bundle="COU" /></td>
										<td class="hairlineTd">
											<input type="text" name="courseName" size="12"
												value="${SetCourseNameForm.map.courseName}">
										</td>
										<td width="30" align="center" class="hairlineTdF">	
										<img src="images/16-IChat-bubble.jpg" />
										</td>
									</tr>
								</table>
								
								<table class="hairlineTable">
									<tr>
										<td class="hairlineTdF">英文名稱</td>
										<td class="hairlineTd">
											<input type="text" name="courseEname"
												value="${SetCourseNameForm.map.courseEname}" 
												style="ime-mode:disabled">
										</td>
										<td width="30" align="center" class="hairlineTdF">	
										<img src="images/font_go.gif" />
										</td>
									</tr>
								</table>

							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<script>
     						generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Query'/>" class="CourseButton">'+'<INPUT type="submit" name="method" value="<bean:message key='Create'/>" class="CourseButton">');
     					</script>
					</table>
				</td>
			</tr>
			<c:if test="${courses!=null}">
				<tr>
					<td>
						<!-- put a result -->

						<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
						<display:table name="${courses}" export="false" id="row"
							pagesize="10" sort="list" excludedParams="*" class="list">
							<c:if test="${empty courses}">
								<%@ include file="../include/NoBanner.jsp"%>
							</c:if>
							<display:column
								title="<script>generateTriggerAll(${fn:length(courses)}, 'courseList'); </script>"
								class="center">
								<script>generateCheckbox("${row.oid}", "courseList")</script>
							</display:column>
							<display:column titleKey="Course.label.courseNumber"
								property="cscode" sortable="true" class="left" />
							<display:column titleKey="Course.label.courseName"
								property="chiName" sortable="true" class="left" />
							<display:column titleKey="Course.label.courseEname"
								property="engName" sortable="true" class="left" />
						</display:table>
					</td>
				</tr>
				<script>
	       		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Delete'/>" class="CourseButton" onclick="return delConfirm();">'+
	       		'<INPUT type="submit" name="method" value="<bean:message key='Modify'/>" class="CourseButton">');
	       	</script>

				<!-- put a result ^ -->
			</c:if>			
			<!-- check editMode -->
		</c:if>
		<c:if test="${editMode==true}">
			<tr>
				<td align="left" valign="top">
					<table class="empty-border">
						<tr>
							<td align="left">
								&nbsp;&nbsp;
								<bean:message key="setCourse.label.courseNumber" bundle="COU" />
								:
							</td>
							<td>
								<input type="text" name="modifyCourseNumber" size="12"
									value="${selCourseNumber}" readonly="true">
								&nbsp;&nbsp;
							</td>
						<tr>
							<td align="left">
								&nbsp;&nbsp;
								<bean:message key="setCourse.label.courseName" bundle="COU" />
								:
							</td>
							<td>
								<input type="text" name="modifyCourseName" size="12"
									value="${selCourseName}">
							</td>
						<tr>
							<td align="left">
								&nbsp;&nbsp;
								<bean:message key="setCourse.label.courseEname" bundle="COU" />
								:
							</td>
							<td>
								<input type="text" name="modifyCourseEname" size="36"
									value="${selCourseEname}">

							</td>
						</tr>
					</table>
				</td>
			</tr>
			<script>
	       		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" class="CourseButton">'+
	       		'<INPUT type="submit" name="method" value="<bean:message key='ModifyRecord'/>" class="CourseButton">');
	       	</script>
		</c:if>
	</html:form>
</table>
<script></script>
