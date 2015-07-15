<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/Calendar.inc" %>
<script>
history.go(1);
function searchCheck() {
	var iCount;
	iCount = getCookie("emplListCount");
	if (iCount == 0) {
		alert("請選擇一個教師作班級課表查詢!!");
		return false;
	} else if(iCount > 1) {
		alert("不可複選教師作班級課表查詢!!");
		return false;
	} else {
		return true;
	}
}
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Course/TeacherCourseSearch" method="post" focus="cname2" onsubmit="init('執行中, 請稍後')">
	<script>
		generateTableBanner('<div class="gray_15"><B><bean:message key="course.courseSearch.Banner" bundle="COU"/></B></div>');
	</script>
	<tr>
		<td align="center">
			<table cellspacing="2" class="empty-border">
				<tr>
					<td>
						教師姓名：&nbsp;<html:text property="cname2" size="15" maxlength="20" />
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<script>
  		generateTableBanner('<INPUT type="submit" name="method" id="s1" value="<bean:message key='teacher.classCadreSearch.btn.searchTeacher' bundle="TCH"/>" class="CourseButton">');			  
 	</script>
 	
 	<c:if test="${not empty emplList}">
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${emplList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
		        <%@ include file="../include/NoBanner.jsp" %>
		        <display:column title="<script>generateTriggerAll(${fn:length(emplList)}, 'emplList');</script>" class="center">
	          		<script>generateCheckbox("${row.oid}", "emplList");</script>
	          	</display:column>
		        <display:column titleKey="courseSearch.teacherName" property="cname" sortable="false" class="center" />
		        <display:column titleKey="deptInCharge" property="unit2" sortable="false" class="center" />
		        <display:column titleKey="courseSearch.teacherTitle" property="sname" sortable="false" class="center" />
		        <display:column title="電話" property="telephone" sortable="false" class="center" />
		        <display:column title="手機" property="cellPhone" sortable="false" class="center" />	        
		    </display:table>
		</td>
	</tr>
	<script>
  		generateTableBanner('<INPUT type="submit" name="method" id="s1" value="<bean:message key='teacher.classCadreSearch.btn.searchCourse' bundle="TCH"/>" onclick="return searchCheck();" class="CourseButton">');			  
 	</script>
	</c:if>
	
 	<c:if test="${not empty courseList}">
	<script>
		generateTableBanner('<div class="gray_15"><B>上 課 時 間 依 各 校 區 各 部 制 公 告 時 間 為 準</B></div>');
	</script>	
	<tr>
		<td>
			<table width="100%" cellpadding="2" cellspacing="2" border="1">
				<tr>
					<td height="30" bgcolor="CCCCCC">&nbsp;</td>
					<c:forEach begin="0" end="6" varStatus="status2">
					<td align="center" bgcolor="CCCCCC">
						<b><c:out value="${weekdayList[status2.index]}" /></b>
					</td>
					</c:forEach>
				</tr>
				<c:forEach begin="0" end="14" varStatus="status">
				<tr>	
					<td align="center" width="100" bgcolor="CCCCCC">
						<b><c:out value="${nodeList[status.index]}" escapeXml="false" /><b/>
					</td>	
					<c:forEach begin="0" end="6" varStatus="status1">	
					<td height="100" width="100"><strong>		
						<c:out value="${courseList[(status1.index * 15) + status.index]['ClassName']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index]['chi_name']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index]['cscode']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index]['name2']}" /><br/>
						<c:out value="${courseList[(status1.index * 15) + status.index]['place']}" /><br/></strong>
					</td>						
					</c:forEach>
				</tr>						
				</c:forEach>
			</table>
		</td>
	</tr>
	</c:if>
</html:form>
</table>