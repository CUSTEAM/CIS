<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
function checkEnglishCourseName() {
	var courseENames = document.getElementsByName("courseEName");
	// for (var i = 0; i < courseENames.length; i++) {
	//	if (courseENames[i].value == '') {
	//		alert("科目英文名稱不可為空白");
	//		courseENames[i].focus();
	//		return false;
	//	}
	//}
	
	if (document.getElementById("chiIntro").value == '') {
		alert("課程中文簡介不可為空白,謝謝");
		document.getElementById("chiIntro").focus();
		return false;
	}	
	return true;
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/TeachIntroduction" method="post">
	<script>
		generateTableBanner('<div class="gray_15"><B><bean:message key="teacher.introduction.banner" bundle="TCH"/></B></div>');
	</script>
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<th colspan="3" align="center">可 複 製 課 程 - <font color="red"> ${TechIntroductionForm.map.year}</font> 學 年 度 第 <font color="red">${TechIntroductionForm.map.term}</font> 學 期 課 程</th>
				</tr>
	 			<tr>
	 				<td align="center">
	 					<div id="term1" style="display:inline;">
      					<display:table name="${copyTeacherDtime}" export="false" id="row" pagesize="100" sort="list" excludedParams="*" class="list">
       						<%@ include file="../include/NoBanner.jsp" %>
        					<display:column title="" class="center">
          						<script>generateCheckbox("${row.oid}", "CopyTeachDtimeInfo");</script>
          					</display:column>
        					<display:column titleKey="Course.label.className" property="className" sortable="true" class="center" />
        					<display:column titleKey="Course.label.courseName" property="chiName" sortable="true" class="center" />
        					<display:column titleKey="Course.label.courseEname" property="engName" sortable="true" class="left" />
        					<display:column titleKey="Course.label.lastModify" property="dateFormat" sortable="true" class="center" />
      					</display:table>
      					</div>
      				</td>
      			</tr>
    		</table>
    	</td>
    </tr>
	<tr>
		<td align="center">
	       	<table cellspacing="2" class="empty-border">
	       		<tr>
	       			<td>
		       			<table width="100%" cellspacing="1" border="0">
		       				<tr>
				       			<th align="center"><bean:message key="Course.label.className" /></th>
				       			<th align="center"><bean:message key="Course.label.courseName" /></th>
				       			<th align="center"><bean:message key="Course.label.courseEname" /></th>
		       				</tr>	       				
		       				<c:forEach items="${sessionScope.courseInfo}" var="tdi">
		       				<tr>
				       			<td align="center"><input type="text" name="className" value="${tdi.className}" readonly disabled /></td>
				       			<td align="center"><input type="text" name="chiName" value="${tdi.chiName}" readonly disabled /></td>
				       			<td align="center"><input type="text" name="courseEName" size="60" maxlength="60" value="${tdi.engName}" /></td>
		       				</tr>
		       				</c:forEach>
		       			</table>
	       			</td>
	       		</tr>
	       		<tr>
	       			<th colspan="3" height="20">&nbsp;</th>
				</tr>
	       		<tr>
	       			<th colspan="3" height="20">&nbsp;</th>
				</tr>
	       		<tr>
	       			<th colspan="3" align="center"><bean:message key="teacher.introduction.chiIntro" bundle="TCH" /></th>
				</tr>
				<tr>
					<td colspan="3"><textarea name="chiIntro" cols="80" rows="12">${requestScope.courseIntroInfo.chiIntro}</textarea></td>
				</tr>
				<tr height="30">
					<td colspan="3">&nbsp;</td>
				</tr>
				<tr>
					<th colspan="3" align="center"><bean:message key="teacher.introduction.engIntro" bundle="TCH" /></th>
				</tr>
				<tr>
					<td colspan="3"><textarea name="engIntro" cols="80" rows="12">${requestScope.courseIntroInfo.engIntro}</textarea></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="10"></td>
	</tr>
   	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='OK' />" onclick="return checkEnglishCourseName();" class="CourseButton">&nbsp;' +
   							'<INPUT type="submit" name="method" value="<bean:message key='Cancel' />" class="CourseButton">&nbsp;');
   	</script>
</html:form>
</table>