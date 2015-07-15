<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">

</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/StayTimeInput" method="post">
	<script>
		generateTableBanner('<div class="gray_15"><B><bean:message key="teacher.stayTime.banner" bundle="TCH"/></B></div>');
	</script>
	<tr>
		<td align="center">
	       	<table width="100%" cellspacing="5" class="empty-border">
				<tr>
 		 			<td>
 		 				系別：&nbsp;${requestScope.memberInfo.unit2}&nbsp;&nbsp;&nbsp;&nbsp;		       		
		       		</td>
		       		<td>
		       			教師姓名：&nbsp;${requestScope.memberInfo.name}
		       		</td>
		       		<td>
		       			Email：&nbsp;${requestScope.memberInfo.email}
		       		</td>
		       	</tr>
		       	<tr>
		       		<td colspan="3">
		       			辦公室位置：&nbsp;<input type="text" name="roomId" size="7" maxlength="6" value="${requestScope.locationInfo.roomId}" disabled="true" />&nbsp;
		       			備註：&nbsp;<input type="text" name="remark" size="25" maxlength="30" value="${requestScope.locationInfo.remark}" disabled="true" />&nbsp;&nbsp;&nbsp;&nbsp;
		       			分機：&nbsp;<input type="text" name="extPhone" size="8" maxlength="10" value="${requestScope.locationInfo.extension}" disabled="true" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		       			<img src="images/ok_st_obj.gif"/>：留校時間
		       		</td>
		       	</tr>
		       	<tr>
		 			<td colspan="3">
		 				<font color="red">若找不到老師，請洽${memberInfo.unit2}(分機：&nbsp;&nbsp;&nbsp;&nbsp;)或人事室(分機：112)</font>
		 			</td>
		 		</tr>
		 		<tr>
		 			<td colspan="3">
		 				<img src="images/ico_file_excel1.png" border='0' alt="課表與留校時間下載"><font color="red"><a href="/CIS/DeptAssistant/AssistantTeacherSchedOfficeHoursPrint.do?Oid=${requestScope.EmplOid}" target="_blank">下載課表與留校時間</a></font>
		 			</td>
		 		</tr>
		       	<tr>
		 			<td colspan="3">
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
			</table>
		</td>
	</tr>	
</html:form>
</table>