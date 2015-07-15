<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<script>
history.go(1);
function searchCheck() {
	var iCount;
	iCount = getCookie("emplListCount");
	if (iCount == 0) {
		alert("請選擇一個教師作查詢!!");
		return false;
	} else if(iCount > 1) {
		alert("不可複選教師作查詢!!");
		return false;
	} else {
		return true;
	}
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Student/TeacherStayTimeSearch" method="post" focus="cname2">
	<script>generateTableBanner('<div class="gray_15"><B>教 師 留 校 時 間</B></div>');</script>
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${courseInfo}" export="false" id="row" pagesize="10000" sort="list" excludedParams="*" class="list">
		        <%@ include file="../include/NoBanner.jsp" %>		        
		        <display:column titleKey="Course.label.courseName" sortable="false" class="left">
		        	<!-- html:link page="/Student/OnlineAddDelCourse.do?method=readCourseIntro" title="瀏覽中英文簡介" paramName="row" paramId="oid" paramProperty="oid" -->
						<c:out value="${row['chi_Name']}" />
					<!-- /html:link -->
				</display:column>	
				<display:column titleKey="Course.label.techName" sortable="false" class="center">
					<c:out value="${row['cname']}" />&nbsp;
					[<html:link page="/Student/TeacherStayTimeSearch.do?method=readStayTime" title="瀏覽教師留校時間" paramName="row" paramId="eoid" paramProperty="eoid">
						<font color="blue">留校時間</font>
					</html:link>]
				</display:column>
		        <display:column titleKey="Course.label.opt" property="opt2" sortable="false" class="center" />	        
		        <display:column titleKey="Course.label.credit" property="credit" sortable="false" class="center" />
		        <display:column titleKey="Course.label.hours" property="thour" sortable="false" class="center" />
		        <display:column titleKey="Course.onlineAddRemoveCourse.stuSel" property="stu_Select" sortable="false" class="center" />
		        <display:column titleKey="Course.label.classTime" property="time" sortable="false" class="center"/>
		        <display:column titleKey="Course.label.place" sortable="false" class="center">
		        	<c:out value="${row['name2']}" />&nbsp;&nbsp;<c:out value="${row['room_Id']}" />
		        </display:column>
		    </display:table>
		</td>
	</tr>	
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
  		generateTableBanner('<INPUT type="submit" name="method" id="s1" value="<bean:message key='teacher.teacherStayTimeSearch.btn.search' bundle="TCH"/>" class="CourseButton">');			  
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
		    </display:table>
		</td>
	</tr>
	<script>
  		generateTableBanner('<INPUT type="submit" name="method" id="s1" value="<bean:message key='teacher.teacherStayTimeSearch.btn.searchStayTime' bundle="TCH"/>" onclick="return searchCheck();" class="CourseButton">');			  
 	</script>
	</c:if>
	
	<c:if test="${not empty stayTimeInfo}">
 	<tr>
   		<td colspan="4">
   			辦公室位置：&nbsp;${requestScope.locationInfo.roomId}&nbsp;
   			備註：&nbsp;${requestScope.locationInfo.remark}&nbsp;&nbsp;&nbsp;&nbsp;
   			分機：&nbsp;${requestScope.locationInfo.extension}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   			<img src="images/ok_st_obj.gif"/>：留校時間
   		</td>
   	</tr>
	<tr>
		<td colspan="4">
			<table width="100%" cellspacing="0" border="1" bordercolor="black">
				<tr>
	   				<td align="center" height="40">節&nbsp;&nbsp;次</td>
	   				<td align="center">星期一</td>
	   				<td align="center">星期二</td>
	   				<td align="center">星期三</td>
	   				<td align="center">星期四</td>
	   				<td align="center">星期五</td>
	   				<td align="center">星期六</td>
	   				<td align="center">星期日</td>
	   				<td width="15%" align="center">進修學院節次</td>
   				</tr>
   				<c:if test="${empty requestScope.stayTimeInfo}">
	   			<c:forEach begin="0" end="13" var="index">
	   			<tr>
	   				<td align="center" height="30">${requestScope.orderInfo[index]}</td>
	   				<td align="center"><input type="checkbox" name="mon${index}" /></td>
	   				<td align="center"><input type="checkbox" name="tue${index}" /></td>
	   				<td align="center"><input type="checkbox" name="wed${index}" /></td>
	   				<td align="center"><input type="checkbox" name="thu${index}" /></td>
	   				<td align="center"><input type="checkbox" name="fri${index}" /></td>
	   				<td align="center"><input type="checkbox" name="sat${index}" /></td>
	   				<td align="center"><input type="checkbox" name="sun${index}" /></td>
	   				<td align="center">${requestScope.orderInfoHoliday[index]}</td>
	   			</tr>
	   			</c:forEach>
	   			</c:if>
	   			<c:if test="${not empty requestScope.stayTimeInfo}">
	   			<c:forEach items="${stayTimeInfo}" var="index" varStatus="status">
	   			<tr>
	   				<td align="center" height="30">${requestScope.orderInfo[status.index]}</td>
	   				<td align="center">
	   					<c:if test="${requestScope.stayTimeInfo[status.index][0] == '1'}"><img src="images/ok_st_obj.gif"/></c:if>
	   					<c:if test="${requestScope.stayTimeInfo[status.index][0] != '1'}">&nbsp;</c:if>
	   				</td>
	   				<td align="center">
	   					<c:if test="${requestScope.stayTimeInfo[status.index][1] == '1'}"><img src="images/ok_st_obj.gif"/></c:if>
	   					<c:if test="${requestScope.stayTimeInfo[status.index][1] != '1'}">&nbsp;</c:if>
	   				</td>
	   				<td align="center">
	   					<c:if test="${requestScope.stayTimeInfo[status.index][2] == '1'}"><img src="images/ok_st_obj.gif"/></c:if>
	   					<c:if test="${requestScope.stayTimeInfo[status.index][2] != '1'}">&nbsp;</c:if>
	   				</td>
	   				<td align="center">
	   					<c:if test="${requestScope.stayTimeInfo[status.index][3] == '1'}"><img src="images/ok_st_obj.gif"/></c:if>
	   					<c:if test="${requestScope.stayTimeInfo[status.index][3] != '1'}">&nbsp;</c:if>
	   				</td>
	   				<td align="center">
	   					<c:if test="${requestScope.stayTimeInfo[status.index][4] == '1'}"><img src="images/ok_st_obj.gif"/></c:if>
	   					<c:if test="${requestScope.stayTimeInfo[status.index][4] != '1'}">&nbsp;</c:if>
	   				</td>
	   				<td align="center">
	   					<c:if test="${requestScope.stayTimeInfo[status.index][5] == '1'}"><img src="images/ok_st_obj.gif"/></c:if>
	   					<c:if test="${requestScope.stayTimeInfo[status.index][5] != '1'}">&nbsp;</c:if>
	   				</td>
	   				<td align="center">
	   					<c:if test="${requestScope.stayTimeInfo[status.index][6] == '1'}"><img src="images/ok_st_obj.gif"/></c:if>
	   					<c:if test="${requestScope.stayTimeInfo[status.index][6] != '1'}">&nbsp;</c:if>
	   				</td>
	   				<td align="center">${requestScope.orderInfoHoliday[status.index]}</td>
	   			</tr>
   				</c:forEach>
   				</c:if>
			</table>
		</td>
	</tr>		
	</c:if>
</html:form>
</table>