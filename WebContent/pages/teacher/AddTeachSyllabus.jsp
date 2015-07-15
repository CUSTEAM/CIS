<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
function checkEnglishCourseName() {
	var courseENames = document.getElementsByName("courseEName");
	for (var i = 0; i < courseENames.length; i++) {
		if (courseENames[i].value == '') {
			alert("科目英文名稱不可為空白");
			courseENames[i].focus();
			return false;
		}
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
<html:form action="${requestScope.actionName}" method="post" onsubmit="init('系統處理中...')">
	<script>
		generateTableBanner('<div class="gray_15"><B><bean:message key="teacher.syllabus.banner" bundle="TCH"/></B></div>');
	</script>
	<tr>
		<td>
       		<table width="100%" cellspacing="5" class="empty-border">
       			<tr>
	       			<td>
		       			<table width="100%" cellspacing="1" border="0">
		       				<tr>
				       			<th align="center"><bean:message key="Course.label.className" /></th>
				       			<th align="center"><bean:message key="Course.label.courseName" /></th>
				       			<th align="center"><bean:message key="Course.label.courseEname" /></th>
		       				</tr>	       				
		       				<c:forEach items="${sessionScope.courseInfos}" var="tdi">
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
		 			<td colspan="2">教師(Instructor)：&nbsp;
		 	   			<input type="text" name="email" size="40" value="${emplInfo.cname}" readonly disabled>&nbsp;&nbsp;&nbsp;&nbsp;
		 	   			E-mail：&nbsp;<input type="text" name="email" size="30" value="${emplInfo.email}" readonly disabled>
		 	   		</td>
		 	   	</tr>
		 	   	<tr>
		 			<td>輔導時間(Office Hours)：&nbsp;
		 	   			<input type="text" name="officeHour" size="30" maxlength="30" value="${courseSyllabusInfo.officeHours}">
		 	   		</td>
		 	   	</tr>
		 	   	<tr>
		 			<td colspan="3">先修科目或先備能力(Course Prerequisites)：&nbsp;
		 	   			<input type="text" name="requisites" size="80" value="${courseSyllabusInfo.prerequisites}">
		 	   		</td>
		 	   	</tr>
		 	   	<tr>
		 			<td colspan="3">課程目標(Course Objectives)：&nbsp;
		 	   			<div><textarea name="objectives" rows="5" cols="80">${courseSyllabusInfo.objectives}</textarea></div>
		 	   		</td>
		 	   	</tr>
		 	   	<tr>
		 			<td colspan="3">教學大綱(Syllabus)：&nbsp;
		 				<div><textarea name="syllabus" rows="5" cols="80">${courseSyllabusInfo.syllabus}</textarea></div>
		 			</td>
		 	   	</tr>
		 	   	<tr>
		 			<td colspan="3">
		 				<table width="100%" cellspacing="0" border="0" bordercolor="black">
				   			<tr>
				   				<td width="10%" align="center">章節主題<br/>(Unit/Session Topics)</td>
				   				<td width="50%" align="center">內容綱要<br/>(Content)</td>
				   				<td width="10%" align="center">教學時數<br/>(Teaching Hours)</td>
				   				<td width="10%" align="center">週次<br/>(Week Number)</td>
				   				<td width="20%" align="center">備註(Remarks)</td>
				   			</tr>
				   			<c:if test="${empty courseSyllabusInfo}">
				   			<c:forEach begin="1" end="18">
				   			<tr>
				   				<td><textarea name="topic" rows="3" cols="10"></textarea></td>
				   				<td><textarea name="content" rows="3" cols="50"></textarea></td>
				   				<td align="center"><input type="text" name="hours" size="2" maxlength="5"></td>
				   				<td align="center"><input type="text" name="weekNo" size="3" maxlength="10"></td>
				   				<td align="center"><textarea name="remark" rows="3" cols="10"></textarea></td>
				   			</tr>
				   			</c:forEach>
				   			</c:if>
				   			<c:if test="${not empty courseSyllabusList}">
				   			<c:forEach items="${courseSyllabusList}" var="s" begin="0" end="18">
				   			<tr>
				   				<td><textarea name="topic" rows="3" cols="10">${s.topic}</textarea></td>
				   				<td><textarea name="content" rows="3" cols="50">${s.content}</textarea></td>
				   				<td align="center"><input type="text" name="hours" size="2" maxlength="5" value="${s.hours}"></td>
				   				<td align="center"><input type="text" name="weekNo" size="3" maxlength="10" value="${s.week}"></td>
				   				<td align="center"><textarea name="remark" rows="3" cols="10">${s.remarks}</textarea></td>
				   			</tr>
				   			</c:forEach>
				   			</c:if>
				   			<%-- 以下Code主要是將輸入條件湊滿18個  --%>
				   			<c:forEach begin="1" end="${counts}">
				   			<tr>
				   				<td><textarea name="topic" rows="3" cols="10"></textarea></td>
				   				<td><textarea name="content" rows="3" cols="50"></textarea></td>
				   				<td align="center"><input type="text" name="hours" size="2" maxlength="5"></td>
				   				<td align="center"><input type="text" name="weekNo" size="3" maxlength="10"></td>
				   				<td align="center"><textarea name="remark" rows="3" cols="10"></textarea></td>
				   			</tr>
				   			</c:forEach>				   			
				   		</table>
		 			</td>
		 	   	</tr>
	   		</table>	   		
	   	</td>
	</tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Save'/>" onclick="return checkEnglishCourseName();" class="CourseButton">&nbsp;' + 
								'<INPUT type="submit" name="method" value="<bean:message key='teacher.introduction.btn.copy' bundle="TCH" />" onclick="return courseCheck();" class="CourseButton">&nbsp;' + 
								'<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" class="CourseButton">');</script>
</html:form>
</table>