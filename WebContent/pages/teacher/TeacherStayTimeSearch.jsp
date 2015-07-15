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
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/TeacherStayTimeSearch" method="post" focus="cname2" onsubmit="init('執行中, 請稍後')">
	<script>
		generateTableBanner('<div class="gray_15"><B><bean:message key="course.teacherStayTimeSearch.Banner" bundle="COU"/></B></div>');
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
		        <display:column titleKey="courseSearch.teacherName" property="cname" sortable="false" />
		        <display:column titleKey="deptInCharge" property="unit2" sortable="false" />
		        <display:column titleKey="courseSearch.teacherTitle" property="sname" sortable="false"  />	        
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