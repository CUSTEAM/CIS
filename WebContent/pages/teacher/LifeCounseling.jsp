<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">

</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/Tutor/LifeCounseling" method="post" onsubmit="init('查詢執行中, 請稍後')">
	<script>
		generateTableBanner('<div class="gray_15"><B><font color="red">${year}</font>  學 年 第 <font color="red">${term}</font> 學 期 每 週 生 活 輔 導 時 間 表 維 護</B></div>');
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
		       		<td colspan="3">
		       			辦公室位置：&nbsp;<input type="text" name="roomId" size="5" maxlength="10" value="${locationInfo.roomId}" />&nbsp;&nbsp;
		       			備註：&nbsp;<input type="text" name="remark" size="25" maxlength="30" value="${locationInfo.remark}" />&nbsp;&nbsp;&nbsp;&nbsp;
		       			分機：&nbsp;<input type="text" name="extPhone" size="8" maxlength="10" value="${locationInfo.extension}" />&nbsp;&nbsp;&nbsp;&nbsp;
		       			<img src="images/ok_st_obj.gif"/>：留校時間
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
				   			<c:if test="${empty sessionScope.lifeCounselingInfo}">
				   			<c:forEach begin="0" end="13" var="index">
				   			<tr>
				   				<td align="center" height="30">${sessionScope.orderInfo[index]}</td>
				   				<td align="center"><input type="checkbox" name="mon${index}" /></td>
				   				<td align="center"><input type="checkbox" name="tue${index}" /></td>
				   				<td align="center"><input type="checkbox" name="wed${index}" /></td>
				   				<td align="center"><input type="checkbox" name="thu${index}" /></td>
				   				<td align="center"><input type="checkbox" name="fri${index}" /></td>
				   				<td align="center"><input type="checkbox" name="sat${index}" /></td>
				   				<td align="center"><input type="checkbox" name="sun${index}" /></td>
				   				<td align="center" height="30">${sessionScope.orderInfoHoliday[index]}</td>
				   			</tr>
				   			</c:forEach>
				   			</c:if>
				   			
				   			<!-- 判斷大於4小於10是因為生活輔導時間有日午須排除  -->
				   			<!-- 不跳過4會發生日午也會有科目出現 -->
				   			<c:if test="${not empty sessionScope.lifeCounselingInfo}">
				   			<c:forEach items="${lifeCounselingInfo}" var="index" varStatus="status">
				   			
				   			<tr>
				   				<td align="center" height="30">${sessionScope.orderInfo[status.index]}</td>
				   				<td align="center">
				   					<c:choose>
				   						<c:when test="${sessionScope.courseList[(0 * 15) + status.index]['chi_name'] == null}">
				   							<c:choose>
				   								<c:when test="${sessionScope.stayTimeInfo[status.index][0] == '1'}">
				   									<img src="images/ok_st_obj.gif"/>
				   								</c:when>
				   								<c:otherwise>
				   									<input type="checkbox" name="mon${status.index}" id="mon${status.index}" <c:if test="${sessionScope.lifeCounselingInfo[status.index][0] == '1'}">checked</c:if> />
				   								</c:otherwise>
				   							</c:choose>
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
				   							<c:choose>
				   								<c:when test="${sessionScope.stayTimeInfo[status.index][1] == '1'}">
				   									<img src="images/ok_st_obj.gif"/>
				   								</c:when>
				   								<c:otherwise>
				   									<input type="checkbox" name="tue${status.index}" id="tue${status.index}" <c:if test="${sessionScope.lifeCounselingInfo[status.index][1] == '1'}">checked</c:if> />
				   								</c:otherwise>
				   							</c:choose>
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
				   							<c:choose>
				   								<c:when test="${sessionScope.stayTimeInfo[status.index][2] == '1'}">
				   									<img src="images/ok_st_obj.gif"/>
				   								</c:when>
				   								<c:otherwise>
				   									<input type="checkbox" name="wed${status.index}" id="wed${status.index}" <c:if test="${sessionScope.lifeCounselingInfo[status.index][2] == '1'}">checked</c:if> />
				   								</c:otherwise>
				   							</c:choose>
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
				   							<c:choose>
				   								<c:when test="${sessionScope.stayTimeInfo[status.index][3] == '1'}">
				   									<img src="images/ok_st_obj.gif"/>
				   								</c:when>
				   								<c:otherwise>
				   									<input type="checkbox" name="thu${status.index}" id="thu${status.index}" <c:if test="${sessionScope.lifeCounselingInfo[status.index][3] == '1'}">checked</c:if> />
				   								</c:otherwise>
				   							</c:choose>
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
				   							<c:choose>
				   								<c:when test="${sessionScope.stayTimeInfo[status.index][4] == '1'}">
				   									<img src="images/ok_st_obj.gif"/>
				   								</c:when>
				   								<c:otherwise>
				   									<input type="checkbox" name="fri${status.index}" id="fri${status.index}" <c:if test="${sessionScope.lifeCounselingInfo[status.index][4] == '1'}">checked</c:if> />
				   								</c:otherwise>
				   							</c:choose>
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
				   							<c:choose>
				   								<c:when test="${sessionScope.stayTimeInfo[status.index][5] == '1'}">
				   									<img src="images/ok_st_obj.gif"/>
				   								</c:when>
				   								<c:otherwise>
				   									<input type="checkbox" name="sat${status.index}" id="sat${status.index}" <c:if test="${sessionScope.lifeCounselingInfo[status.index][5] == '1'}">checked</c:if> />
				   								</c:otherwise>
				   							</c:choose>
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
				   							<c:choose>
				   								<c:when test="${sessionScope.stayTimeInfo[status.index][6] == '1'}">
				   									<img src="images/ok_st_obj.gif"/>
				   								</c:when>
				   								<c:otherwise>
				   									<input type="checkbox" name="sun${status.index}" id="sun${status.index}" <c:if test="${sessionScope.lifeCounselingInfo[status.index][6] == '1'}">checked</c:if> />
				   								</c:otherwise>
				   							</c:choose>
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
	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='teacher.stayTime.btn.update' bundle="TCH" />" class="CourseButton">&nbsp;');
   	</script>
</html:form>
</table>