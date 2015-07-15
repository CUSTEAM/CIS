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
<html:form action="/Course/CourseSearch" method="post">
	<script>
		generateTableBanner('<div class="gray_15"><B><bean:message key="course.courseSearch.Banner" bundle="COU"/></B></div>');
	</script>
	<tr>
		<td>
			<table class="empty-border">
				<tr>
					<td>開課學期: 第
						<html:select property="sterm">
    						<html:option value="1">1</html:option>
    						<html:option value="2">2</html:option>
    					</html:select>&nbsp;學期    					
					</td>
				</tr>
				<tr>
					<td><bean:message key="OpenCourse.label.classNumber" bundle="COU"/>:
				   		<c:set var="campusSel" value="${OpenCourseForm.map.campusInCharge2}"/>
	  			   		<c:set var="schoolSel" value="${OpenCourseForm.map.schoolInCharge2}"/>
	  			   		<c:set var="deptSel"   value="${OpenCourseForm.map.deptInCharge2}"/>
	  			   		<c:set var="classSel"  value="${OpenCourseForm.map.classInCharge2}"/>
	  			   		<%@include file="/pages/include/ClassSelect2.jsp"%>
					</td>
				</tr>				
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<script>
				generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='course.courseSearch.btn.search' bundle="COU" />" class="CourseButton">');
			</script>
	  		</table>
		</td>
	</tr>
	<c:if test="${not empty courseList}">
	<script>
		generateTableBanner('<div class="gray_15"><B>${classFullName}</B></div>');
	</script>	
	<tr>
		<td>
			<table width="100%" cellpadding="2" cellspacing="2" border="1">
				<tr>
					<td height="30" bgcolor="CCCCCC">&nbsp;</td>
					<c:forEach begin="0" end="${rowsCols['cols']}" varStatus="status2">
					<td align="center" bgcolor="CCCCCC">
						<b><c:out value="${weekdayList[status2.index]}" /></b>
					</td>
					</c:forEach>
				</tr>
				<c:if test="${rowsCols['mode'] == 'D'}">
				<c:forEach begin="0" end="${rowsCols['rows']}" varStatus="status">
				<tr>	
					<td align="center" width="100" bgcolor="CCCCCC">
						<b><c:out value="${nodeList[status.index]}" escapeXml="false" /><b/>
					</td>	
					<c:forEach begin="0" end="${rowsCols['cols']}" varStatus="status1">	
					<td height="100" width="80"><strong>		
						<c:out value="${courseList[(status1.index * 15) + status.index]['chi_name']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index]['cname']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index]['cscode']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index]['name2']}" />&nbsp;
						<c:out value="${courseList[(status1.index * 15) + status.index]['place']}" /><br/></strong>
					</td>						
					</c:forEach>
				</tr>						
				</c:forEach>
				</c:if>
				
				<c:if test="${rowsCols['mode'] == 'N'}">
				<c:forEach begin="0" end="${rowsCols['rows']}" varStatus="status">
				<tr>	
					<td align="center" width="100" bgcolor="CCCCCC">
						<b><c:out value="${nodeList[status.index]}" escapeXml="false" /><b/>
					</td>	
					<c:forEach begin="0" end="${rowsCols['cols']}" varStatus="status1">	
					<td height="100" width="80"><strong>		
						<c:out value="${courseList[(status1.index * 15) + status.index + 5]['chi_name']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index + 5]['cname']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index + 5]['cscode']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index + 5]['name2']}" />&nbsp;
						<c:out value="${courseList[(status1.index * 15) + status.index + 5]['place']}" /><br/></strong>
					</td>						
					</c:forEach>
				</tr>						
				</c:forEach>
				</c:if>
				
				<c:if test="${rowsCols['mode'] == 'H'}">
				<c:forEach begin="0" end="${rowsCols['rows']}" varStatus="status">
				<tr>	
					<td align="center" width="100" bgcolor="CCCCCC">
						<b><c:out value="${nodeList[status.index]}" escapeXml="false" /><b/>
					</td>	
					<c:forEach begin="0" end="${rowsCols['cols']}" varStatus="status1">	
					<td height="100" width="80"><strong>		
						<c:out value="${courseList[(status1.index * 15) + status.index]['chi_name']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index]['cname']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index]['cscode']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index]['name2']}" />&nbsp;
						<c:out value="${courseList[(status1.index * 15) + status.index]['place']}" /><br/></strong>
					</td>						
					</c:forEach>
				</tr>						
				</c:forEach>
				</c:if>
				
				<c:if test="${rowsCols['mode'] == 'S'}">
				<c:forEach begin="0" end="${rowsCols['rows']}" varStatus="status">
				<tr>	
					<td align="center" width="100" bgcolor="CCCCCC">
						<b><c:out value="${nodeList[status.index]}" escapeXml="false" /><b/>
					</td>	
					<c:forEach begin="0" end="${rowsCols['cols']}" varStatus="status1">	
					<td height="100" width="80"><strong>		
						<c:out value="${courseList[(status1.index * 15) + status.index]['chi_name']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index]['cname']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index]['cscode']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index]['name2']}" />&nbsp;
						<c:out value="${courseList[(status1.index * 15) + status.index]['place']}" /><br/></strong>
					</td>						
					</c:forEach>
				</tr>						
				</c:forEach>
				</c:if>
			</table>
		</td>
	</tr>
	</c:if>
</html:form>
</table>