<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">

function checkStayTimeCounts() {
	var stayTimeCounts = ${sessionScope.emplSnameCounts};
	var counts = 0;
	// alert(stayTimeCounts);
	// document.getElementsByName()
	for (var i = 0; i <= 13; i++) {
		if (document.getElementById("mon" + i) != null && document.getElementById("mon" + i).checked)
			counts++;
		if (document.getElementById("tue" + i) != null && document.getElementById("tue" + i).checked)
			counts++;
		if (document.getElementById("wed" + i) != null && document.getElementById("wed" + i).checked)
			counts++;
		if (document.getElementById("thu" + i) != null && document.getElementById("thu" + i).checked)
			counts++;
		if (document.getElementById("fri" + i) != null && document.getElementById("fri" + i).checked)
			counts++;
		if (document.getElementById("sat" + i) != null && document.getElementById("sat" + i).checked)
			counts++;
		if (document.getElementById("sun" + i) != null && document.getElementById("sun" + i).checked)
			counts++;				
	}
	//alert(counts);
	
	if (counts < stayTimeCounts) {
		alert("目前輸入留校時間節數為" + counts + "節,留校時間輸入總節數應為" + stayTimeCounts + "節,請重新輸入,謝謝!");
		return false;
	}
	return true;

}

</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/StayTimeInput" method="post" onsubmit="init('查詢執行中, 請稍後')">
	<script>
		generateTableBanner('<div class="gray_15"><B><font color="red">${year}</font>  學 年 第 <font color="red">${term}</font>  學 期 教 師 每 週 留 校 時 間 表 維 護&nbsp;&nbsp;<font color="red">(開放時間:${STAYBEGIN} ~ ${STAYEND})</font></B></div>');
	</script>
	<tr>
		<td align="center">
	       	<table width="100%" cellspacing="5" class="empty-border">
				<tr>
 		 			<td>
 		 				系別：&nbsp;${memberInfo.unit2}&nbsp;&nbsp;&nbsp;&nbsp;		       		
		       		</td>
		       		<td>
		       			教師姓名：&nbsp;${memberInfo.cname}
		       		</td>
		       		<td>
		       			Email：&nbsp;${memberInfo.email}
		       		</td>
		       	</tr>
		       	<tr>
 		 			<td>
 		 				職稱：&nbsp;${memberInfo.sname}	       		
		       		</td>
		       		<td>
		       			<font color="red">留校時間總節數：&nbsp;<font size="+1">${emplSnameCounts}</font>節</font>
		       		</td>
		       		<td>
		       			&nbsp;
		       		</td>
		       	</tr>
		       	<tr>
		       		<td colspan="3">
		       			辦公室位置：&nbsp;<input type="text" name="roomId" size="5" maxlength="10" value="${locationInfo.roomId}" />&nbsp;
		       			備註：&nbsp;<input type="text" name="remark" size="25" maxlength="30" value="${locationInfo.remark}" />&nbsp;&nbsp;&nbsp;&nbsp;
		       			分機：&nbsp;<input type="text" name="extPhone" size="8" maxlength="10" value="${locationInfo.extension}" />
		       		</td>
		       	</tr>
		       	<tr>
		       		<td colspan="3">
		       			<font color="red" size="+1">${STAYDEADLINE}</font>以前之留校時間任何更新動作,不計入統計計算中
		       		</td>
		       	</tr>
		       	<tr>
		       		<td colspan="3">
		       			目前更新總次數：&nbsp;<font color="red" size="+1">${modifyCounts} 次</font>&nbsp;
		       			最後一次更新時間：&nbsp;<font color="red" size="+1">${lastModified}</font>&nbsp;&nbsp;
		       		</td>
		       	</tr>
		       	<tr>
		 			<td colspan="3">
		 				<font color="red">若找不到老師，請洽${memberInfo.unit2}(分機：${memberInfo.unit2})或人事室(分機：112)</font>
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
				   				<td align="center" height="40">進修學院節次</td>
				   			</tr>
				   			
				   			<!-- 應該都不會執行到下面區塊,因為stayTimeInfo都會有值,只是都是一堆0(表示查無留校時間) -->
				   			<c:if test="${empty sessionScope.stayTimeInfo}">
				   			<c:forEach begin="0" end="13" var="index">
				   			<tr>
				   				<td align="center" height="30">${sessionScope.orderInfo[index]}</td>
				   				<td align="center"><input type="checkbox" name="mon${index}" id="mon${index}" /></td>
				   				<td align="center"><input type="checkbox" name="tue${index}" id="tue${index}"/></td>
				   				<td align="center"><input type="checkbox" name="wed${index}" id="wed${index}"/></td>
				   				<td align="center"><input type="checkbox" name="thu${index}" id="thu${index}"/></td>
				   				<td align="center"><input type="checkbox" name="fri${index}" id="fri${index}"/></td>
				   				<td align="center"><input type="checkbox" name="sat${index}" id="sat${index}"/></td>
				   				<td align="center"><input type="checkbox" name="sun${index}" id="sun${index}"/></td>
				   				<td align="center" height="30">${sessionScope.orderInfoHoliday[index]}</td>
				   			</tr>
				   			</c:forEach>
				   			</c:if>
				   			
				   			<!-- 判斷大於4小於10是因為留校時間有日午須排除  -->
				   			<!-- 不跳過4會發生日午也會有科目出現 -->
				   			<!-- status.index是節次 --> 
				   			<c:if test="${not empty sessionScope.stayTimeInfo}">
				   			<c:forEach items="${stayTimeInfo}" var="index" varStatus="status">
				   			
				   			<tr>
				   				<td align="center" height="30">${sessionScope.orderInfo[status.index]}</td>
				   				<td align="center">
				   					<c:choose>
				   						<c:when test="${sessionScope.courseList[(0 * 15) + status.index]['chi_name'] == null}">
				   							<input type="checkbox" name="mon${status.index}" id="mon${status.index}" <c:if test="${sessionScope.stayTimeInfo[status.index][0] == '1'}">checked</c:if> />
				   						</c:when>
				   						<c:otherwise>
				   							<c:out value="${sessionScope.courseList[(0 * 15) + status.index]['ClassName']}"/><br/>
											<c:out value="${sessionScope.courseList[(0 * 15) + status.index]['chi_name']}"/>				   						
				   						</c:otherwise>
				   					</c:choose>
				   				</td>
				   				<td align="center">
				   					<c:choose>
				   						<c:when test="${sessionScope.courseList[(1 * 15) + status.index]['chi_name'] == null}">
				   							<input type="checkbox" name="tue${status.index}" id="tue${status.index}" <c:if test="${sessionScope.stayTimeInfo[status.index][1] == '1'}">checked</c:if> />
				   						</c:when>
				   						<c:otherwise>
				   							<c:out value="${sessionScope.courseList[(1 * 15) + status.index]['ClassName']}"/><br/>
											<c:out value="${sessionScope.courseList[(1 * 15) + status.index]['chi_name']}"/>				   						
				   						</c:otherwise>
				   					</c:choose>
				   				</td>
				   				<td align="center">
				   					<c:choose>
				   						<c:when test="${sessionScope.courseList[(2 * 15) + status.index]['chi_name'] == null}">
				   							<input type="checkbox" name="wed${status.index}" id="wed${status.index}" <c:if test="${sessionScope.stayTimeInfo[status.index][2] == '1'}">checked</c:if> />
				   						</c:when>
				   						<c:otherwise>
				   							<c:out value="${sessionScope.courseList[(2 * 15) + status.index]['ClassName']}"/><br/>
											<c:out value="${sessionScope.courseList[(2 * 15) + status.index]['chi_name']}"/>				   						
				   						</c:otherwise>
				   					</c:choose>
				   				</td>
				   				<td align="center">
				   					<c:choose>
				   						<c:when test="${sessionScope.courseList[(3 * 15) + status.index]['chi_name'] == null}">
				   							<input type="checkbox" name="thu${status.index}" id="thu${status.index}" <c:if test="${sessionScope.stayTimeInfo[status.index][3] == '1'}">checked</c:if> />
				   						</c:when>
				   						<c:otherwise>
				   							<c:out value="${sessionScope.courseList[(3 * 15) + status.index]['ClassName']}"/><br/>
											<c:out value="${sessionScope.courseList[(3 * 15) + status.index]['chi_name']}"/>				   						
				   						</c:otherwise>
				   					</c:choose>
				   				</td>
				   				<td align="center">
				   					<c:choose>
				   						<c:when test="${sessionScope.courseList[(4 * 15) + status.index]['chi_name'] == null}">
				   							<input type="checkbox" name="fri${status.index}" id="fri${status.index}" <c:if test="${sessionScope.stayTimeInfo[status.index][4] == '1'}">checked</c:if> />
				   						</c:when>
				   						<c:otherwise>
				   							<c:out value="${sessionScope.courseList[(4 * 15) + status.index]['ClassName']}"/><br/>
											<c:out value="${sessionScope.courseList[(4 * 15) + status.index]['chi_name']}"/>				   						
				   						</c:otherwise>
				   					</c:choose>
				   				</td>
				   				<td align="center">
				   					<c:choose>
				   						<c:when test="${sessionScope.courseList[(5 * 15) + status.index]['chi_name'] == null}">
				   							<input type="checkbox" name="sat${status.index}" id="sat${status.index}" <c:if test="${sessionScope.stayTimeInfo[status.index][5] == '1'}">checked</c:if> />
				   						</c:when>
				   						<c:otherwise>
				   							<c:out value="${sessionScope.courseList[(5 * 15) + status.index]['ClassName']}"/><br/>
											<c:out value="${sessionScope.courseList[(5 * 15) + status.index]['chi_name']}"/>				   						
				   						</c:otherwise>
				   					</c:choose>
				   				</td>
				   				<td align="center">
				   					<c:choose>
				   						<c:when test="${sessionScope.courseList[(6 * 15) + status.index]['chi_name'] == null}">
				   							<input type="checkbox" name="sun${status.index}" id="sun${status.index}" <c:if test="${sessionScope.stayTimeInfo[status.index][6] == '1'}">checked</c:if> />
				   						</c:when>
				   						<c:otherwise>
				   							<c:out value="${sessionScope.courseList[(6 * 15) + status.index]['ClassName']}"/><br/>
											<c:out value="${sessionScope.courseList[(6 * 15) + status.index]['chi_name']}"/>				   						
				   						</c:otherwise>
				   					</c:choose>
				   				</td>
				   				<td align="center" height="30">${sessionScope.orderInfoHoliday[status.index]}</td>
				   			</tr>
				   			</c:forEach>
				   			</c:if>
		 				</table>
		 			</td>
		       	</tr>		       		
			</table>
		</td>
	</tr>
	<c:if test="${sessionScope.modifyMode == '0'}">
	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='teacher.stayTime.btn.update' bundle="TCH" />" class="CourseButton" onclick="return checkStayTimeCounts()">&nbsp;');
   	</script>
   	</c:if>
</html:form>
</table>