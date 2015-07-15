<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<script language="javaScript">
history.go(1);
</script>

<html:form action="/Teacher/Chairman/DtimeInfo" method="post" onsubmit="init('產生資料中, 請稍後')">
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<script>
	generateTableBanner('<table align="left"><tr><td align="left">&nbsp;&nbsp;<img src="images/16-cube-debug.png"></td><td>全校系所選課狀況</td></tr></table>');
</script>
	<tr>
		<td>
			<table class="empty-border">
				<tr>
					<td>
						開課學期: 第 
						<html:select property="sterm" styleId="stermS">
    						<html:option value="1">1</html:option>
    						<html:option value="2">2</html:option>
    					</html:select>學期&nbsp;&nbsp;
    					<span id="thisTermScore" style="display:none;">
    						<input type="checkbox" id="termScore" name="termScore" value="true" checked />將本學期成績以60分(碩士班70分)計
    					</span>
    				</td>    				
				</tr>
				<tr>
					<td><bean:message key="OpenCourse.label.classNumber" bundle="COU"/>:
				   		<c:set var="campusSel" value="${DtimeInfoForm.map.campusInCharge}"/>
	  			   		<c:set var="schoolSel" value="${DtimeInfoForm.map.schoolInCharge}"/>
	  			   		<c:set var="deptSel" value="${DtimeInfoForm.map.deptInCharge}"/>
	  			   		<c:set var="classSel" value="${DtimeInfoForm.map.classInCharge}"/>
	  			   		<%@include file="/pages/include/AllClassSelect1.jsp"%>
	  			   		<table width="100%" id="alert" style="display:none">
							<tr>
								<td><br>
									<div class="modulecontainer filled nomessages">
										<div class="first">
											<span class="first"></span>
											<span class="last"></span>
									</div>
									<div>
										<div>
											<table width="100%">
												<tr>
													<td id="helpMsg">
														&nbsp;&nbsp;<img src="images/24-book-green-message.png">
													</td>
													<td align="left"></td>
													<td width="150">
														<a href="javascript:void(0)" onclick="if(document.getElementById('alert').style.display='none'){document.getElementById('alert').style.display='none';}">
														<img src="images/16-tag-check.png" border="0"><font size="1">隱藏說明</font></a>
													</td>
												</tr>
											</table>
										</div>
									</div>
									<div class="last">
										<span class="first"></span>
										<span class="last"></span>
									</div><br>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="center" class="fullColorTable">
			<table align="center">
				<tr>
					<td>
					</td>
					<td>
						<html:submit styleId="previewB" property="method" styleClass="CourseButton"><bean:message key="Search" /></html:submit>&nbsp;
					</td>					
				</tr>
			</table>
		</td>
	</tr>
	
	<c:if test="${not empty dtimeInfos}">
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
	 			<tr>
	 				<td align="center">
	 					<div id="term1" style="display:inline;">
      					<display:table name="${dtimeInfos}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
       						<%@ include file="../include/NoBanner.jsp" %>
        					<display:column title="班級代碼" property="classNo" sortable="true" class="center" />
        					<display:column title="班級名稱" property="className" sortable="true" class="center" />
        					<display:column title="科目代碼" property="cscode" sortable="true" class="center" />
        					<display:column title="科目名稱" property="csname" sortable="true" class="center" />
        					<display:column title="老師姓名" property="teacherName" sortable="true" class="center" />
        					<display:column title="選別" property="opt" sortable="true" class="center" />
        					<display:column title="學分數" property="credit" sortable="true" class="center" />
        					<display:column title="時數" property="hour" sortable="true" class="center" />
        					<display:column title="選修人數" property="stdSelect" sortable="true" class="center" />
      					</display:table>
      					</div>
      				</td>
      			</tr>
    		</table>
    	</td>
    </tr>
    </c:if>
    
</html:form>
</table>

<script>
document.getElementById('stermS').disabled = false;
function showHelp(type) {
	document.getElementById('helpMsg').innerHTML = '';
	document.getElementById('year').style.display = 'none';
	document.getElementById('interClassId').style.display = 'none';
	document.getElementById('report').style.display = 'none';
	document.getElementById('fail').style.display = 'none';
	document.getElementById('rank').style.display = 'none';
	document.getElementById('stermS').disabled = false;
	if(type == 'ClassStudentsScoreHistory') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'none';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('year').style.display = 'none';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('thisTermScore').style.display = 'inline';
		document.getElementById('stermS').disabled = false;
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">列印範圍以班為單位，謝謝！！</font><br>&nbsp;<font color="red">列印Excel檔案時，請將列印頁數設為第1頁到第1頁(除延修班或單一學生有2頁歷年資料)，謝謝！！</font>');
	}
	
	if(type == 'ClassStudentsScoreHistory4CreditClass') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'none';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('year').style.display = 'none';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('thisTermScore').style.display = 'inline';
		document.getElementById('stermS').disabled = false;
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">列印範圍以班為單位，謝謝！！</font><br>&nbsp;<font color="red">列印Excel檔案時，請將列印頁數設為第1頁到第1頁(除延修班或單一學生有2頁歷年資料)，謝謝！！</font>');
	}
	
	if(type == 'ClassStudentsRegistrationCard') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'none';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('year').style.display = 'none';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('thisTermScore').style.display = 'none';
		document.getElementById('stermS').disabled = false;
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">列印範圍以班為單位，謝謝！！</font>');
	}
	
	if(type == 'RegisterList') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'none';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('year').style.display = 'none';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('thisTermScore').style.display = 'none';
		document.getElementById('stermS').disabled = false;
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">列印範圍以班為單位，謝謝！！</font>');
	}
	
	if(type == 'IdnoCheck') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'none';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('year').style.display = 'none';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('thisTermScore').style.display = 'none';
		document.getElementById('stermS').disabled = false;
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">列印範圍以班為單位，謝謝！！</font>');
	} 
	
	if(type == 'Calculate') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'none';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('year').style.display = 'inline';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('thisTermScore').style.display = 'none';
		document.getElementById('stermS').disabled = false;
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">通識班級選課不列入跨系選課範圍！！<br/>&nbsp;非本學期跨選資料將以歷年成績處理之！！</font>');
	}

	if (type == 'MidtermFinalFailStudentsResult') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'inline';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('previewB').disabled = false;
		document.getElementById('stermS').disabled = false;
		document.getElementById('year').style.display = 'none';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'inline';
		document.getElementById('fail').style.display = 'inline';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;列印二分之ㄧ或三分之二不及格全校學生名單前，<font color="red">須先執行期中期末全班成績單列印程式</font>，謝謝。');
	} 
	
	if (type == 'MidtermFinalFailStudentsByClassResult') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'inline';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('previewB').disabled = false;
		document.getElementById('stermS').disabled = false;
		document.getElementById('year').style.display = 'none';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'inline';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;列印二分之ㄧ或三分之二不及格學生個人名單前，<font color="red">須先執行期中期末全班成績單列印程式</font>，謝謝。');
	}
	
	if (type == 'MidtermFinalStatistic') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'inline';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('previewB').disabled = false;
		document.getElementById('stermS').disabled = false;
		document.getElementById('year').style.display = 'none';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'inline';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;列印期中期末成績統計表前，<font color="red">須先執行期中期末全班成績單列印程式</font>，謝謝。');
	}
	
	if (type == 'MidtermCourseExcluded') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'inline';
		document.getElementById('previewB').style.display = 'none';
		document.getElementById('searchB').style.display = 'inline';
		document.getElementById('year').style.display = 'none';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('stermS').disabled = false;
		document.getElementById('helpMsg').innerHTML = ('&nbsp;將部份科目從期中成績計算中納入/排除!');
	}
	
	if (type == 'MidtermFinalTopStudentsResult') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'inline';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('previewB').disabled = false;
		document.getElementById('stermS').disabled = false;
		document.getElementById('year').style.display = 'none';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'inline';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;列印期中期末各班前三名學生名單前，<font color="red">須先執行期中期末全班成績單列印程式</font>，謝謝。');
	}
	
	if (type == 'MidtermFinalFailStudentsTooMuch') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'inline';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('previewB').disabled = false;
		document.getElementById('stermS').disabled = false;
		document.getElementById('year').style.display = 'none';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'inline';
		document.getElementById('fail').style.display = 'inline';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;列印教師任教科目不及格學生統計表前，<font color="red">須先執行期中期末全班成績單列印程式</font>，謝謝。<br/>&nbsp;注意：只有不及格學生人數，<font color="red">超過選課人數1/2之科目</font>，才會顯示。');
	}
	
	if (type == 'MidtermFinalFailTwiceStudentsResult') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'inline';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('previewB').disabled = false;
		document.getElementById('stermS').disabled = false;
		document.getElementById('year').style.display = 'none';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;列印學期成績雙1/2不及格學生名單前，<font color="red">須先執行期中期末全班成績單列印程式</font>，謝謝。');
	}
	
	if (type == 'GraduatingScoreAvg') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'inline';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('previewB').disabled = false;
		document.getElementById('stermS').disabled = false;
		document.getElementById('year').style.display = 'none';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'inline';
	}
		
}
</script>