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

function courseCheck() {
	if (document.getElementById("hist").value == "") {
		alert("請選擇歷年資料進行複製");
		document.getElementById("hist").focus();
		return false;
	}
	if (confirm("確定複製歷年資料?")) {
		return true;
	}
	return false;
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/TeachIntroduction" method="post" onsubmit="init('系統處理中...')">
	<script>
		generateTableBanner('<div class="gray_15"><B><bean:message key="teacher.introduction.banner" bundle="TCH"/></B></div>');
	</script>
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
	       			<td height="20">選擇歷年資料:
	       				<html:select property="hist" size="1">
	       					<html:option value=""></html:option>
    						<html:options property="histOid" labelProperty="histData" />	    						
    					</html:select>&nbsp;&nbsp;(<font color="red">選擇左列下拉資料並按"複製"鈕,可以將歷年資料複製至目前所選科目</font>)
	       			</td>
				</tr>
	       		<tr>
	       			<th colspan="3" align="center"><bean:message key="teacher.introduction.chiIntro" bundle="TCH" /></th>
				</tr>
				<tr>
					<td colspan="3"><textarea name="chiIntro" cols="80" rows="6">${requestScope.courseIntroInfo.chiIntro}</textarea></td>
				</tr>
				<tr height="30">
					<td colspan="3">&nbsp;</td>
				</tr>
				<tr>
					<th colspan="3" align="center"><bean:message key="teacher.introduction.engIntro" bundle="TCH" /></th>
				</tr>
				<tr>
					<td colspan="3"><textarea name="engIntro" cols="80" rows="6">${requestScope.courseIntroInfo.engIntro}</textarea></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="10"></td>
	</tr>
   	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='OK' />" onclick="return checkEnglishCourseName();" class="CourseButton">&nbsp;' +
   							'<INPUT type="submit" name="method" value="<bean:message key='teacher.introduction.btn.copy' bundle="TCH" />" onclick="return courseCheck();" class="CourseButton">&nbsp;' + 
   							'<INPUT type="submit" name="method" value="<bean:message key='Cancel' />" class="CourseButton">&nbsp;');
   	</script>
</html:form>
</table>