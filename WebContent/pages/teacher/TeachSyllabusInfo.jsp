<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/TeachSyllabusSearch" method="post">
	<script>
		generateTableBanner('<div class="gray_15"><B><bean:message key="teacher.syllabusSearch.banner" bundle="TCH"/></B></div>');
	</script>
	<tr>
		<td>
       		<table width="100%" cellspacing="5" class="empty-border">
 		 		<tr>
 		 			<td colspan="2">科系名稱(Department)：&nbsp;
		       			<input type="text" name="ename" size="20" value="${sessionScope.courseInfo.className}" readonly>
		       		</td>
		       	</tr>
		 		<tr>
		 			<td colspan="2">教師(Instructor)：&nbsp;
		 	   			<input type="text" name="email" size="40" value="${requestScope.emplInfo.cname}" readonly>&nbsp;&nbsp;&nbsp;&nbsp;
		 	   			E-mail：&nbsp;<input type="text" name="email" size="30" value="${requestScope.emplInfo.email}" readonly>
		 	   		</td>
		 	   	</tr>		 				 	   		 	 
		 		<tr>
		 			<td>科目名稱(Course Title)：&nbsp;
		 	   			<input type="text" name="cellPhone" size="20" value="${requestScope.csnoInfo.chiName}" readonly>
		 	   		</td>
		 	   	</tr>
		 		<tr>
		 			<td colspan="3">英文科目名稱(Course in English)：&nbsp;
		 	   			<input type="text" name="courseEName" size="60" value="${requestScope.csnoInfo.engName}" readonly>
		 	   		</td>
		 	   	</tr>
		 	   	<tr>
		 			<td>輔導時間(Office Hours)：&nbsp;
		 	   			<input type="text" name="officeHour" size="30" maxlength="30" value="${courseSyllabusInfo.officeHours}" readonly>
		 	   		</td>
		 	   	</tr>
		 	   	<tr>
		 			<td colspan="3">先修科目或先備能力(Course Prerequisites)：&nbsp;
		 	   			<input type="text" name="requisites" size="80" value="${courseSyllabusInfo.prerequisites}" readonly>
		 	   		</td>
		 	   	</tr>
		 	   	<tr>
		 			<td colspan="3">課程目標(Course Objectives)：&nbsp;
		 	   			<div><textarea name="objectives" rows="5" cols="80" readonly>${courseSyllabusInfo.objectives}</textarea></div>
		 	   		</td>
		 	   	</tr>
		 	   	<tr>
		 			<td colspan="3">教學大綱(Syllabus)：&nbsp;
		 				<div><textarea name="syllabus" rows="5" cols="80" readonly>${courseSyllabusInfo.syllabus}</textarea></div>
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
				   				<td><textarea name="topic" rows="3" cols="10" readonly></textarea></td>
				   				<td><textarea name="content" rows="3" cols="50" readonly></textarea></td>
				   				<td align="center"><input type="text" name="hours" size="2" maxlength="5" readonly></td>
				   				<td align="center"><input type="text" name="weekNo" size="3" maxlength="10" readonly></td>
				   				<td align="center"><textarea name="remark" rows="3" cols="10" readonly></textarea></td>
				   			</tr>
				   			</c:forEach>
				   			</c:if>
				   			<c:if test="${not empty courseSyllabusList}">
				   			<c:forEach items="${courseSyllabusList}" var="s" begin="0" end="18">
				   			<tr>
				   				<td><textarea name="topic" rows="3" cols="10" readonly>${s.topic}</textarea></td>
				   				<td><textarea name="content" rows="3" cols="50" readonly>${s.content}</textarea></td>
				   				<td align="center"><input type="text" name="hours" size="2" maxlength="5" value="${s.hours}" readonly></td>
				   				<td align="center"><input type="text" name="weekNo" size="3" maxlength="10" value="${s.week}" readonly></td>
				   				<td align="center"><textarea name="remark" rows="3" cols="10" readonly>${s.remarks}</textarea></td>
				   			</tr>
				   			</c:forEach>
				   			</c:if>
				   			<%-- 以下Code主要是將輸入條件湊滿18個  --%>
				   			<c:forEach begin="1" end="${counts}">
				   			<tr>
				   				<td><textarea name="topic" rows="3" cols="10" readonly></textarea></td>
				   				<td><textarea name="content" rows="3" cols="50" readonly></textarea></td>
				   				<td align="center"><input type="text" name="hours" size="2" maxlength="5" readonly></td>
				   				<td align="center"><input type="text" name="weekNo" size="3" maxlength="10" readonly></td>
				   				<td align="center"><textarea name="remark" rows="3" cols="10" readonly></textarea></td>
				   			</tr>
				   			</c:forEach>				   			
				   		</table>
		 			</td>
		 	   	</tr>
	   		</table>	   		
	   	</td>
	</tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Back'/>" class="CourseButton">&nbsp;&nbsp;');</script>
</html:form>
</table>
<script>history.go(1);</script>