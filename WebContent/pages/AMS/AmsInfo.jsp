<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/AllClassInCharge.js" %>
<%@ include file="/pages/include/ClassSelection.js" %>
<%@ include file="/pages/include/tooltips.js" %>

<DIV id="TipLayer" style="visibility:hidden;position:absolute;z-index:1000;top:-100"></DIV>

<script type="text/javascript">
history.go(1);
Style[0]=["white","black","#00B333","#E8E8FF","","","","","","","","","","",200,"",2,2,10,10,51,0.5,75,"simple","gray"];
Style[1]=["white","black","#00CCCC","#E8E8FF","","","","","","","","","","",200,"",2,2,10,10,51,0.5,75,"simple","gray"];
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
	<html:form action="/AMS/AmsInfo" method="post" onsubmit="init('執行中, 請稍後')">
		<script>generateTableBanner('<div class="gray_15"><B>員 工 出 勤 明 細</B></div>');</script>
		<script>generateTableBanner('<div class="gray_15"><B>查 詢 與 統 計 範 圍 : ${fromDate} ~ ${toDate}</B></div>');
		</script>
		
		<tr align="center">
			<td>
				<table class="empty-border">
					<tr>
						<td>
							<html:select property="fromYear" styleId="fromYear">
								<html:options property="fromYears" labelProperty="fromYears" />
							</html:select>&nbsp;&nbsp;年
							<html:select property="fromMonth" styleId="fromMonth">
								<html:option value="1">1</html:option>
    							<html:option value="2">2</html:option>
    							<html:option value="3">3</html:option>
    							<html:option value="4">4</html:option>
    							<html:option value="5">5</html:option>
    							<html:option value="6">6</html:option>
    							<html:option value="7">7</html:option>
    							<html:option value="8">8</html:option>
    							<html:option value="9">9</html:option>
    							<html:option value="10">10</html:option>
    							<html:option value="11">11</html:option>
    							<html:option value="12">12</html:option>
							</html:select>&nbsp;&nbsp;月
							<html:select property="fromDay" styleId="fromDay">
								<html:options property="fromDays" labelProperty="fromDays" />
							</html:select>&nbsp;&nbsp;日 ~ 
							<html:select property="toYear" styleId="toYear">
								<html:options property="toYears" labelProperty="toYears" />
							</html:select>&nbsp;&nbsp;年
							<html:select property="toMonth" styleId="toMonth">
								<html:option value="1">1</html:option>
    							<html:option value="2">2</html:option>
    							<html:option value="3">3</html:option>
    							<html:option value="4">4</html:option>
    							<html:option value="5">5</html:option>
    							<html:option value="6">6</html:option>
    							<html:option value="7">7</html:option>
    							<html:option value="8">8</html:option>
    							<html:option value="9">9</html:option>
    							<html:option value="10">10</html:option>
    							<html:option value="11">11</html:option>
    							<html:option value="12">12</html:option>
							</html:select>&nbsp;&nbsp;月
							<html:select property="toDay" styleId="toDay">
								<html:options property="toDays" labelProperty="toDays" />
							</html:select>&nbsp;&nbsp;日 
							
	    				</td>    
	    				<td>
							<html:submit property="method" styleClass="CourseButton"><bean:message key='course.onlineAddRemoveCourse.query' bundle="COU" /></html:submit>
						</td>				
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
			
				<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
				<display:table name="${amsInfo}" export="false" id="row" pagesize="31" sort="list" excludedParams="*" class="list">
					<%@ include file="../include/NoBanner.jsp" %>
					
					<display:footer>
						 <tr align="left">
							<td colspan="8">
								<p>&nbsp;</p>
								<font color="blue" size="+1">計算範圍：</font><b>
								<font color="red" size="+1">${fromDate} ~ ${toDate}</font><b>
							</td>
						</tr>
					    <tr align="left">
							<td colspan="8">
								<font color="blue" size="+1">在設定的上班時間後20分鐘內刷卡的次數：</font><b>
								<font color="red" size="+1">${betweenCounts}</font></b> 次&nbsp;&nbsp;
							</td>
						</tr>
						<tr align="left">
							<td colspan="8">
								遲到：<b><font color="red" size="+1">${delayCounts}</font></b> 次，
								早退：<b><font color="red" size="+1">${earlyCounts}</font></b> 次，
								共扣 <b><font color="red" size="+1"><!--(${delayCounts} + ${earlyCounts}) * 0.3 = -->${delayEarlysScore}</font></b> 分&nbsp;&nbsp;
							</td>
						</tr>
						
						<tr align="left">
							<td colspan="8">
								遲到但超過1小時算曠職：<b><font color="red" size="+1">${totalDelayButNoRecordHours}</font></b> 小時，
								扣 <b><font color="red" size="+1"><!--${totalDelayButNoRecordHours} * 0.5 = -->${totalDelayButNoRecordScore}</font></b> 分&nbsp;&nbsp;
							</td>
						</tr>
						<tr align="left">
							<td colspan="8">
								早退但超過1小時算曠職：<b><font color="red" size="+1">${totalEarlyButNoRecordHours}</font></b> 小時，
								扣  <b><font color="red" size="+1"><!--${totalEarlyButNoRecordHours} * 0.5 = -->${totalEarlyButNoRecordScore}</font></b> 分&nbsp;&nbsp;
							</td>
						</tr>
						<tr align="left">
							<td colspan="8">
								無出勤紀錄：<b><font color="red" size="+1">${noRecordCounts}</font></b> 次，
								扣 <b><font color="red" size="+1"><!--${noRecordCounts} * 8 * 0.5 = -->${noRecordCountsScore}</font></b> 分&nbsp;&nbsp;
							</td>
						</tr>
						<tr align="left">
							<td colspan="8">
								補登次數：<b><font color="red" size="+1">${amsDocApplyCounts}</font></b> 次
								<!--假日加班次數：<b>[ <font color="red" size="+1">${workOnHolidayCounts}</font> ]</b> 次&nbsp;&nbsp;-->
							</td>
						</tr>
						<tr align="left">
							<td colspan="8">
								<hr>
								假單未處理次數：<b><font color="red" size="+1">${vacationNoStatus}</font></b> 次，
								扣 <b><font color="red" size="+1"><!--${vacationNoStatus} * 8 * 0.5 = -->${vacationNoStatusScore}</font></b> 分&nbsp;&nbsp;
							</td>
						</tr>
						<tr align="left">
							<td colspan="8">
								事假：<b><font color="red" size="+1">${businessCounts}</font></b> 次，
								共計 <b><font color="red" size="+1">${businessTotalHours}</font></b> 小時，
								扣 <b><font color="red" size="+1"><!--${businessTotalHours} * 0.2 = -->${businessCountsScore}</font></b> 分&nbsp;&nbsp;
							</td>
						</tr>
						<tr align="left">
							<td colspan="8">
								病假：<b><font color="red" size="+1">${sickCounts}</font></b> 次，
								共計 <b><font color="red" size="+1">${sickTotalHours}</font></b> 小時，
								扣 <b><font color="red" size="+1"><!--${sickTotalHours} * 0.1 = -->${sickCountsScore}</font></b> 分&nbsp;&nbsp;
							</td>
						</tr>
						<tr align="left">
							<td colspan="8">
								生理假：<b><font color="red" size="+1">${womenSickCounts}</font></b> 次，
								共計 <b><font color="red" size="+1">${womenSickTotalHours}</font></b> 小時，
								扣 <b><font color="red" size="+1"><!--${womenSickTotalHours} * 0.1 = -->${womenSickCountsScore}</font></b> 分&nbsp;&nbsp;
							</td>
						</tr>
						<tr align="left">
							<td colspan="8">
								<hr>
								重要集會未出席：<b><font color="red" size="+1">${meetingAbsentCounts}</font></b> 次，
								扣：<b><font color="red" size="+1"><!--${meetingAbsentCounts} * 0.5 = -->${meetingAbsentScores}</font></b> 分&nbsp;&nbsp;
							</td>
						</tr>
						<tr align="left">
						<td colspan="8">
							重要集會請事假：<b><font color="red" size="+1">${meetingBusinessCounts}</font></b> 次，
							共計：<b><font color="red" size="+1">${meetingBusinessTotalHours}</font></b> 小時，
							扣：<b><font color="red" size="+1"><!--${meetingBusinessTotalHours} * 0.2 = -->${meetingBusinessScore}</font></b> 分&nbsp;&nbsp;
						</td>
					</tr>
					<tr align="left">
						<td colspan="8">
							重要集會請病假：<b><font color="red" size="+1">${meetingSickCounts}</font></b> 次，
							共計：<b><font color="red" size="+1">${meetingSickTotalHours}</font></b> 小時，
							扣：<b><font color="red" size="+1"><!--${meetingSickTotalHours} * 0.1 = -->${meetingSickScore}</font></b> 分&nbsp;&nbsp;
						</td>
					</tr>
					<tr align="left">
						<td colspan="8">
							重要集會請生理假：<b><font color="red" size="+1">${meetingWomenSickCounts}</font></b> 次，
							共計：<b><font color="red" size="+1">${meetingWomenSickTotalHours}</font></b> 小時，
							扣：<b><font color="red" size="+1"><!--  ${meetingWomenSickTotalHours} * 0.1 = -->${meetingWomenSickScore}</font></b> 分&nbsp;&nbsp;
						</td>
					</tr>
						<tr align="left">
							<td colspan="8">
								<hr>
								差勤扣分總計：<b><font color="red" size="+1">${totalScores}</font></b> 分&nbsp;&nbsp;
							</td>
						</tr>
						<!--<tr align="left">
							<td colspan="8">
								加班單核准次數：<b>[ <font color="red" size="+1">${amsDocOverTimeCounts}</font> ]</b> 次&nbsp;&nbsp;
								加班可休天數：<b>[ <font color="red" size="+1">${amsDocOverTimeTimes}</font> ]</b> 天&nbsp;&nbsp;
							</td>
						</tr>-->
						<tr align="left">
							<td colspan="8">
								年假：<b><font color="red" size="+1">${yearVacation}</font></b> 天，
								剩餘：<b><font color="red" size="+1">${yearVacationRemain}</font></b> 天&nbsp;&nbsp;
							</td>
						</tr>
						<tr align="left">
							<td colspan="8">
								特休假：<b><font color="red" size="+1">${specialVacation}</font></b> 天&nbsp;&nbsp;
								剩餘：<b><font color="red" size="+1">${specialVacationRemain}</font></b>天&nbsp;&nbsp;
							</td>
						</tr>
					</display:footer>
					
					<display:column title="日期" property="wdateInfo" sortable="false" class="center" />
					
					<c:choose>
						<c:when test="${row.onDelay == '3'}">
							<display:column title="正常上班時間" property="setInInfo" sortable="false" class="working" />
							<display:column title="正常下班時間" property="setOutInfo" sortable="false" class="working" />
						</c:when>
						<c:when test="${row.onDelay == '2'}">
							<display:column title="正常上班時間" property="setInInfo" sortable="false" class="info" />
							<display:column title="正常下班時間" property="setOutInfo" sortable="false" class="info" />
						</c:when>
						<c:otherwise>
							<display:column title="正常上班時間" property="setInInfo" sortable="false" class="center" />
							<display:column title="正常下班時間" property="setOutInfo" sortable="false" class="center" />
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${row.onDelay == '1'}">
							<display:column title="上班刷卡" property="realInInfo" sortable="false" class="error" />
							<display:column title="遲到" property="inDelay" sortable="false" class="error" />
						</c:when>
						<c:otherwise>
							<display:column title="上班刷卡" property="realInInfo" sortable="false" class="center" />
							<display:column title="遲到" property="inDelay" sortable="false" class="center" />
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${row.onEarly == '1'}">
							<display:column title="下班刷卡" property="realOutInfo" sortable="false" class="error" />
							<display:column title="加班/早退" property="outEarly" sortable="false" class="error" />
						</c:when>
						<c:otherwise>
							<display:column title="下班刷卡" property="realOutInfo" sortable="false" class="center" />
							<display:column title="加班/早退" property="outEarly" sortable="false" class="center" />
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${row.onDelay == '3'}">
							<!--display:column title="備註" property="commend" sortable="false" class="working" /-->
							<display:column title="備註" sortable="false" class="center">
								${row.commend}
					        	<img src="images/Get-Info-16x16.png" id="searchNorm" onmouseover="stm(['備註', '${row.commendDetail}'], Style[0])" onmouseout="htm()">
					        </display:column>
						</c:when>
						<c:when test="${row.workHard == '1'}">
							<!--display:column title="備註" property="commend" sortable="false" class="workHard" /-->
							<display:column title="備註" sortable="false" class="center">
								${row.commend}
					        	<img src="images/bestseller.png" id="searchNorm" onmouseover="stm(['備註', '${row.commendDetail}'], Style[0])" onmouseout="htm()">
					        </display:column>
						</c:when>
						<c:when test="${row.holiday == '1'}">
							<!--display:column title="備註" property="commend" sortable="false" class="workHard" /-->
							<display:column title="備註" sortable="false" class="center">
								<c:choose>
									<c:when test="${row.holidayType == 'H'}">
										${row.commend}
					        			<img src="images/free-for-job.png" id="searchNorm" onmouseover="stm(['備註', '${row.commendDetail}'], Style[0])" onmouseout="htm()">
									</c:when>
									<c:when test="${row.holidayType == 'W'}">
										${row.commend}
					        			<img src="images/business-contact.png" id="searchNorm" onmouseover="stm(['備註', '${row.commendDetail}'], Style[0])" onmouseout="htm()">
									</c:when>
								</c:choose>
					        </display:column>
						</c:when>
						<c:when test="${row.meeting == '1'}">
							<!-- 有可能在重要集會時請假(但不是請重要集會的假) -->
							<c:choose>
								<c:when test="${row.commend != null && row.commend != ''}">
									<!--display:column title="備註" property="commend" sortable="false" class="commend" /-->
									<display:column title="備註" sortable="false" class="center">
										<c:choose>
											<c:when test="${row.status == '' || row.status == null}">
												${row.commend}
												<img src="images/Get-Info-16x16.png" id="searchNorm" onmouseover="stm(['備註', '${row.commendDetail}'], Style[0])" onmouseout="htm()">
												<img src="images/lightbulb.png" id="searchNorm" onmouseover="stm(['備註', '${row.meetingInfo}'], Style[0])" onmouseout="htm()">
											</c:when>
											<c:when test="${row.status == '1' || ros.stauts == '3'}"> <!-- 已核准或強迫核准 -->
												${row.commend}
												<img src="images/accept-16x16.png" id="searchNorm" onmouseover="stm(['備註', '${row.commendDetail}'], Style[0])" onmouseout="htm()">
												<img src="images/lightbulb.png" id="searchNorm" onmouseover="stm(['備註', '${row.meetingInfo}'], Style[0])" onmouseout="htm()">
											</c:when>
											<c:when test="${row.status == '0' || row.status == '2'}"> <!-- 未核准,退回或已銷假 -->
												${row.commend}
												<img src="images/remove-16x16.png" id="searchNorm" onmouseover="stm(['備註', '${row.commendDetail}'], Style[0])" onmouseout="htm()">
												<img src="images/lightbulb.png" id="searchNorm" onmouseover="stm(['備註', '${row.meetingInfo}'], Style[0])" onmouseout="htm()">
											</c:when>
											<c:when test="${row.status == '8' || row.status == '9'}"> <!-- 已補休或已補休半天 -->
												${row.commend}
												<img src="images/accept-16x16.png" id="searchNorm" onmouseover="stm(['備註', '${row.commendDetail}'], Style[0])" onmouseout="htm()">
												<img src="images/lightbulb.png" id="searchNorm" onmouseover="stm(['備註', '${row.meetingInfo}'], Style[0])" onmouseout="htm()">
											</c:when>
										</c:choose>
							        </display:column>
								</c:when>
								<c:otherwise>
									<display:column title="備註" sortable="false" class="center">
							        	<img src="images/lightbulb.png" id="searchNorm" onmouseover="stm(['備註', '${row.meetingInfo}'], Style[0])" onmouseout="htm()">
							        </display:column>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${row.commend != null && row.commend != ''}">
							<!--display:column title="備註" property="commend" sortable="false" class="commend" /-->
							<display:column title="備註" sortable="false" class="center">
								<c:choose>
									<c:when test="${row.status == '' || row.status == null}">
										${row.commend}
										<img src="images/Get-Info-16x16.png" id="searchNorm" onmouseover="stm(['備註', '${row.commendDetail}'], Style[0])" onmouseout="htm()">
									</c:when>
									<c:when test="${row.status == '1' || ros.stauts == '3'}"> <!-- 已核准或強迫核准 -->
										${row.commend}
										<img src="images/accept-16x16.png" id="searchNorm" onmouseover="stm(['備註', '${row.commendDetail}'], Style[0])" onmouseout="htm()">
									</c:when>
									<c:when test="${row.status == '0' || row.status == '2'}"> <!-- 未核准,退回或已銷假 -->
										${row.commend}
										<img src="images/remove-16x16.png" id="searchNorm" onmouseover="stm(['備註', '${row.commendDetail}'], Style[0])" onmouseout="htm()">
									</c:when>
									<c:when test="${row.status == '8'} || row.status == '9'"> <!-- 已補休或已補休半天 -->
										${row.commend}
										<img src="images/accept-16x16.png" id="searchNorm" onmouseover="stm(['備註', '${row.commendDetail}'], Style[0])" onmouseout="htm()">
									</c:when>
									<c:otherwise>
										${row.commend}
										<img src="images/accept-16x16.png" id="searchNorm" onmouseover="stm(['備註', '${row.commendDetail}'], Style[0])" onmouseout="htm()">
									</c:otherwise>
								</c:choose>
					        </display:column>
						</c:when>
						<c:otherwise>
							<display:column title="備註" property="commend" sortable="false" class="center" />
						</c:otherwise>
					</c:choose>
					
				</display:table>
				
     		</td>
		</tr>
		
	</html:form>
</table>