<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/Calendar.inc" %>
<script>
history.go(1);
function checkRadio(mode) {
	if(mode == 'A') {
		document.getElementById("bA").style.display = 'inline';
		document.getElementById("bD").style.display = 'none';
	} else if(mode == 'D') {
		document.getElementById("bA").style.display = 'none';
		document.getElementById("bD").style.display = 'inline';
	}
}
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Course/BatchProcess" method="post">
<!--welcomeMode Start-->
<script>
	generateTableBanner('<table align="left">'
	+ '<tr><td align="left">'
	+ '&nbsp;&nbsp;<img src="images/24-imageset-open.png"></td>'
	+ '<td>批次作業</td>'
	+ '</tr></table>');
</script>
	<tr>
		<td>
			<table class="empty-border">
				<tr>
					<td>開課學期: 第
						<html:select property="sterm">
    						<html:option value="1">1</html:option>
    						<html:option value="2">2</html:option>
    					</html:select>學期&nbsp;&nbsp;
    					<c:set var="choseTypeSel" value="${BatchProcessForm.map.choseType}"/>
    					<select name="choseType">
    						<option value="ALL" <c:if test="${choseTypeSel=='1'}"> selected</c:if>>選別</option>
							<option value="1" <c:if test="${choseTypeSel=='1'}"> selected</c:if>>必修</option>
   							<option value="2" <c:if test="${choseTypeSel=='2'}"> selected</c:if>>選修</option>
   							<option value="3" <c:if test="${choseTypeSel=='3'}"> selected</c:if>>通識</option>
						</select>
					</td>
				</tr>
				<tr>
					<td><bean:message key="OpenCourse.label.classNumber" bundle="COU"/>:
				   		<c:set var="campusSel" value="${BatchProcessForm.map.campusInCharge2}"/>
	  			   		<c:set var="schoolSel" value="${BatchProcessForm.map.schoolInCharge2}"/>
	  			   		<c:set var="deptSel"   value="${BatchProcessForm.map.deptInCharge2}"/>
	  			   		<c:set var="classSel"  value="${BatchProcessForm.map.classInCharge2}"/>
	  			   		<%@include file="/pages/include/ClassSelect2.jsp"%>
					</td>
				</tr>
				<!--tr>
					<td><bean:message key="setCourse.label.courseNumber" bundle="COU"/>:
						<input type="text" name="courseNumber" id="cscodeS" size="12"
							value="${OpenCourseForm.map.courseNumber}" onChange="getCourseName('cscodeS', 'csnameS')"
							onmouseup="this.value='', courseName.value=''"><input type="text"
							name="courseName" id="csnameS" size="16" value="${OpenCourseForm.map.courseName}"
							readonly="true">
					</td>
				</tr>
				<tr>
					<td><bean:message key="OpenCourse.label.teacherNumber" bundle="COU"/>:
						<input type="text" name="teacherId" id="techidS" size="12" value="${OpenCourseForm.map.teacherId}"
							onChange="getTeacherName('techidS', 'technameS')"
							onmouseup="this.value='', teacherName.value=''"><input type="text"
							name="teacherName" id="technameS" size="12" value="${OpenCourseForm.map.teacherName}" readonly="true">
					</td>
				</tr-->
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<script>
				generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='OpenChoose'/>" class="CourseButton">&nbsp;'
								  +	'<INPUT type="submit" name="method" value="<bean:message key='CreateBaselect'/>" class="CourseButton">');
								  <%--
								  + '<INPUT type="submit" name="method" value="<bean:message key='ClearTimes'/>" class="CourseButton">&nbsp;'
								  + '<INPUT type="submit" name="method" value="<bean:message key='ClearTeachers'/>" class="CourseButton">&nbsp;'
								  + '<INPUT type="submit" name="method" value="<bean:message key='ClearExam'/>" class="CourseButton">');
								  --%>
			</script>
	  		</table>
		</td>
	</tr>
	<c:if test="${dtimeList != null}">
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${dtimeList}" export="false" id="row" pagesize="10000" sort="list" excludedParams="*" class="list">
		        <%@ include file="../include/NoBanner.jsp" %>
		        <c:if test="${row.canChoose != '1'}">
		        <display:column title="" class="center">
					<script>generateCheckbox("${row.oid}", "dtimes")</script>
		        </display:column>
		        </c:if>
		        <c:if test="${row.canChoose == '1'}">
		        <display:column title="" class="center">	
		        	&nbsp;&nbsp;&nbsp;&nbsp;				
		        </display:column>
		        </c:if>
		        <display:column titleKey="Course.label.className" property="ClassName" sortable="false" class="center" />
		        <display:column titleKey="Course.label.classNo" property="ClassNo" sortable="false" class="center" />
		        <display:column titleKey="Course.label.courseName"  property="chi_name" sortable="false" class="left" />
		        <display:column titleKey="Course.label.courseNumber" property="cscode" sortable="false" class="center" />
		        <display:column titleKey="Course.label.techName" property="cname" sortable="false" class="center" />
		        <display:column titleKey="Course.label.opt" property="opt2" sortable="false" class="center" />
		        <display:column titleKey="Course.label.credit" property="credit" sortable="false" class="center" />
		        <display:column titleKey="Course.label.hours" property="thour" sortable="false" class="center" />
		        <display:column titleKey="Course.onlineAddRemoveCourse.stuSel" sortable="false" class="center">
		        	<html:link page="/Course/BatchProcessStdList.do" title="查詢選課清單" paramName="row" paramId="oid" paramProperty="oid">
						<c:out value="${row.stu_select}" />
					</html:link>
				</display:column>
				<display:column titleKey="Course.onlineAddRemoveCourse.adcdList" sortable="false" class="center">
		        	<html:link page="/Course/BatchProcessAdcdList.do" title="加退選課清單" paramName="row" paramId="oid" paramProperty="oid">
						<c:out value="${fn:length(row.adcdList)}" />
					</html:link>
				</display:column>
		        <display:column titleKey="Course.onlineAddRemoveCourse.selLimit" property="Select_Limit" sortable="false" class="center" />
		        <display:column titleKey="Course.label.open" property="openName" sortable="true" class="center" />
		    </display:table>
		</td>
	</tr>
  	<script>
  		<c:if test="${sessionScope.mode eq 'open'}">
 		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='listUpdateOpen'/>" class="CourseButton">'); 						  
 		</c:if>
 		
 		<c:if test="${sessionScope.mode eq 'baseSel'}"> 		
 		generateTableBanner('<INPUT type="radio" name="mode" value="add" checked onclick="checkRadio(\'A\');"><b>建立</b>&nbsp;&nbsp;'
 			+ '<INPUT type="radio" name="mode" value="del" onclick="checkRadio(\'D\');"><b>刪除</b>');
 		generateTableBanner('<INPUT type="submit" id="bA" name="method" value="<bean:message key='updateBaselect'/>" class="CourseButton" onclick="return confirm(\'確定建立該班所屬學生基本選課資料?\');">'
 			+ '<INPUT type="submit" name="method" id="bD" value="<bean:message key='deleteBaselect'/>" style="display:none;" class="CourseButton" onclick="return confirm(\'確定刪除該班所屬學生基本選課資料?\');">'); 						  
 		</c:if>	
 					  
 	</script>
	</c:if>
</html:form>
</table>