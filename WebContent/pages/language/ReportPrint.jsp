<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/Calendar.inc"%>

<script language="javaScript">
history.go(1);
globalTimeOut = globalTimeOut * 2;
function preview(isPreview) {
	var bFlag = true;
	var doc = document.forms[0];
	if ("Syllabus" == doc.printOpt.value && ("All" == doc.schoolInCharge2.value || "All" == doc.deptInCharge2.value)) {
		alert("開課班級選擇範圍過大");
		doc.deptInCharge2.focus();
		bFlag = false;
	} else if ("none" == doc.printOpt.value) {
		alert("請選擇報表項目");
		doc.printOpt.focus();
		bFlag = false;
	}
	
	var type = doc.printOpt.value;
	if (type == 'DeptStdSkillList-1' || type == 'DeptStdSkillList-2' || type == 'DeptStdSkillList-3'
		|| type == 'DeptStdSkillList-4' || type == 'DeptStdSkillList-5') { 
		var iCount = document.getElementById("startDate").value;
		if (iCount == '') {
			alert("起始時間不可為空白,謝謝!!");
			return false;
		}
	}

	var innerClass = "";
	var failType = "";
	var reportType = "";
	var rankType = "";
	
	if (document.getElementsByName("interClass")[0].checked) 
		innerClass = document.getElementsByName("interClass")[0].value;
	else
		innerClass = document.getElementsByName("interClass")[1].value;	
		
	if (document.getElementsByName("failType")[0].checked)
		failType = document.getElementsByName("failType")[0].value;
	else
		failType = document.getElementsByName("failType")[1].value;
		
	if (document.getElementsByName("reportType")[0].checked)
		reportType = document.getElementsByName("reportType")[0].value;
	else
		reportType = document.getElementsByName("reportType")[1].value;
		
	if (document.getElementsByName("rankType")[0].checked)
		rankType = document.getElementsByName("rankType")[0].value;
	else
		rankType = document.getElementsByName("rankType")[1].value;
	
	var str = "&c=" + doc.campusInCharge2.value + "&s=" + doc.schoolInCharge2.value + "&d=" + doc.deptInCharge2.value +
		"&cl=" + doc.classInCharge2.value + "&p=" + doc.printOpt.value + "&st=" + doc.sterm.value + "&year=" + doc.year.value + 
		"&printInterClass=" + innerClass + "&ft=" + failType + "&rt=" + reportType + "&rank=" + rankType +
		"&sd=" + doc.startDate.value + "&ed=" + doc.endDate.value;
	if (isPreview && bFlag) {	
		// alert(str);
		subwin = window.open("/CIS/Language/ReportPrint.do?method=preview" + str, "預覽列印",
			"width=600,height=600,scrollbars=yes,resizable=yes");
	}	
}

function deleteCourseCheck() {
	var iCount;
	iCount = getCookie("dtimeList1Count");
	if (iCount == 0) {
		alert("請勾選上方[納入期中成績之科目清單]內資料進行移除!!");
		return false;
	} else {
		if(confirm("確定不納入所選[" + iCount + "]門科目進行計算?"))
			return true;
		else 
			return false;	
	}
}

function addCourseCheck() {
	var iCount;
	iCount = getCookie("dtimeList2Count");
	if (iCount == 0) {
		alert("請勾選上方[不納入期中成績之科目清單]內資料進行加入!!");
		return false;
	} else {
		if(confirm("確定納入所選[" + iCount + "]門科目進行計算?"))
			return true;
		else 
			return false;
	}
}
</script>

<html:form action="/Language/ReportPrint" method="post" onsubmit="init('報表產生中, 請稍後')">
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<script>
	generateTableBanner('<table align="left"><tr><td align="left">&nbsp;&nbsp;<img src="images/16-cube-debug.png"></td><td>報表列印與預覽</td></tr></table>');
</script>
	<tr>
		<td>
			<table class="empty-border">
				<tr>
					<td>
						<span id="year" style="display:none;">開課學年: 第 
							<html:select property="year" size="1">
	    						<html:options property="year" labelProperty="year" />	    						
	    					</html:select>學年&nbsp;&nbsp;&nbsp;&nbsp;
	    				</span>
	    				<span id="calendarYear" style="display:none;">第 
							<html:select property="calendarYear" size="1">
	    						<html:options property="calendarYears" labelProperty="calendarYears" />	    						
	    					</html:select>&nbsp;&nbsp;學年&nbsp;&nbsp;&nbsp;&nbsp;
	    				</span>
						開課學期: 第 
						<html:select property="sterm" styleId="stermS" disabled="true">
    						<html:option value="1">1</html:option>
    						<html:option value="2">2</html:option>
    					</html:select>學期&nbsp;&nbsp;
    					<span id="interClassId" style="display:none;">是否列印隨班附讀?    					
    						<input type="radio" id="interClass" name="interClass" value="yes"/>是&nbsp;<input type="radio" id="interClass" name="interClass" checked value="no"/>否
    					&nbsp;&nbsp;</span>
    					<span id="report" style="display:none;">請選擇報表類型: 
    						<input type="radio" id="reportType" name="reportType" value="mid"/>期中&nbsp;<input type="radio" id="reportType" name="reportType" checked value="final"/>學期
    					&nbsp;&nbsp;</span>
    					<span id="fail" style="display:none;">不及格名單類型: 
    						<input type="radio" id="failType" name="failType" checked value="2"/>1/2&nbsp;<input type="radio" id="failType" name="failType" value="3"/>2/3
    					&nbsp;&nbsp;</span>
    					<span id="rank" style="display:none;">排名類型: 
    						<input type="radio" id="rankType" name="rankType" checked value="c"/>班排名&nbsp;<input type="radio" id="rankType" name="rankType" value="d"/>系排名
    					&nbsp;&nbsp;</span>
    					<span id="unitDept" style="display:none;">&nbsp;&nbsp;單位
							<html:select property="unitCode" size="1" disabled="true">
	    						<html:options property="unitCodes" labelProperty="unitNames" />
	    					</html:select>
	    				</span>
	    				<span id="licenseDateRange" style="display:none;">&nbsp;&nbsp;證照生效日期
	    					<input type="text" name="startDate" size="10" maxlength="8" readonly>
    						<img src="images/cal.gif" name="calendar" width="20" height="20"
		      					align="top" style="cursor:hand" alt="點選此處選擇日期"
		  	  					onclick="javascript:if(!startDate.disabled)popCalFrame.fPopCalendar('startDate','startDate',event);">&nbsp;~&nbsp;
		  	  				<input type="text" name="endDate" size="10" maxlength="8" readonly>
    						<img src="images/cal.gif" name="calendar" width="20" height="20"
		      					align="top" style="cursor:hand" alt="點選此處選擇日期"
		  	  					onclick="javascript:if(!endDate.disabled)popCalFrame.fPopCalendar('endDate','endDate',event);">&nbsp;	
	    				</span>
    				</td>    				
				</tr>
				<tr>
					<td><span id="classInfo" style="display:none;"><bean:message key="OpenCourse.label.classNumber" bundle="COU"/>:
				   		<c:set var="campusSel" value="${ReportPringForm.map.campusInCharge2}"/>
	  			   		<c:set var="schoolSel" value="${ReportPringForm.map.schoolInCharge2}"/>
	  			   		<c:set var="deptSel" value="${ReportPringForm.map.deptInCharge2}"/>
	  			   		<c:set var="classSel" value="${ReportPringForm.map.classInCharge2}"/>
	  			   		<c:set var="classLess" value="${ReportPringForm.map.classLess}"/>
	  			   		<%@include file="/pages/include/ClassSelect5.jsp"%></span>
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
						<html:select property="printOpt" onchange="showHelp(this.value)">
							<option value="none">選擇報表項目</option>
							<option value="DeptStdSkillList" <c:if test="${ReportPringForm.map.opt == 'DeptStdSkillList'}">selected</c:if>>系所學生證照列印(匯入資料驗證用)</option>
							<option value="DeptStdSkillList-1" <c:if test="${ReportPringForm.map.opt == 'DeptStdSkillList-1'}">selected</c:if>>專業證照目次表</option>
							<option value="DeptStdSkillList-2" <c:if test="${ReportPringForm.map.opt == 'DeptStdSkillList-2'}">selected</c:if>>學生考取專業證照報名費補助清冊</option>
							<option value="DeptStdSkillList-3" <c:if test="${ReportPringForm.map.opt == 'DeptStdSkillList-3'}">selected</c:if>>系所證照類張數表</option>
							<option value="DeptStdSkillList-4" <c:if test="${ReportPringForm.map.opt == 'DeptStdSkillList-4'}">selected</c:if>>已取得證照列表</option>
							<option value="DeptStdSkillList-5" <c:if test="${ReportPringForm.map.opt == 'DeptStdSkillList-5'}">selected</c:if>>教師輔導學生取得證照一覽表</option>
							<option value="LicenseCodes" <c:if test="${ReportPringForm.map.opt == 'LicenseCodes'}">selected</c:if>>962至今報部證照代碼對照表列印</option>
							<option value="LicenseCodes961" <c:if test="${ReportPringForm.map.opt == 'LicenseCodes961'}">selected</c:if>>961以前(含)報部證照代碼對照表列印</option>
							<option value="CscodeList" <c:if test="${ReportPringForm.map.opt == 'CscodeList'}">selected</c:if>>科目代碼表列印</option>
							<option value="DeptCode4Yun" <c:if test="${ReportPringForm.map.opt == 'DeptCode4Yun'}">selected</c:if>>雲科大學制科系代碼表列印</option>
						</html:select>
						<input type="text" name="checkCredit" id="checkCredit" <c:if test="${ReportPringForm.map.checkOpt!='CheckCredit'}">style="display:none;"</c:if> size="1" value="${ReportPringForm.map.checkCredit}"/><img id="point" style="display:none;" src="images/16-exc-mark.gif" />
						<select name="schoolType" <c:if test="${ReportPringForm.map.checkOpt!='CheckGist'}">style="display:none;"</c:if>
				 			onchange="document.getElementById('classLess').value='', document.getElementById('classLess').value=this.value">
							<option value="">不分部制</option>
							<option value="164%' OR d.depart_class LIKE '142%' OR d.depart_class LIKE '112%' OR d.depart_class LIKE '11G">
							日間部</option>
							<option value="122%' OR d.depart_class LIKE '152%' OR d.depart_class LIKE '154%' OR d.depart_class LIKE '182%' OR d.depart_class LIKE '18G%' OR d.depart_class LIKE '192%' OR d.depart_class LIKE '11G">
							進修推廣部</option>
							<option value="172%' OR d.depart_class LIKE '132">
							進修學院</option>
						</select>
					</td>
					<td>
						<html:submit styleId="searchB" property="method" style="display='none'" styleClass="CourseButton"><bean:message key="score.reportPrint.excludeCourseSearch" bundle="SCR" /></html:submit>&nbsp;
						<html:button styleId="previewB" property="method" styleClass="CourseButton" onclick="preview(true);"><bean:message key="Preview" /></html:button>&nbsp;
					</td>					
				</tr>
			</table>
		</td>
	</tr>	
	
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
	if(type == 'Syllabus') {
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
		document.getElementById('stermS').disabled = false;
		document.getElementById('unitDept').style.display = 'none';
		document.getElementById('classInfo').style.display = 'inline';
		document.getElementById('calendarYear').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">列印範圍以系所為單位，謝謝！！</font>');
	}
	
	if(type == 'Introduction') {
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
		document.getElementById('stermS').disabled = false;
		document.getElementById('unitDept').style.display = 'none';
		document.getElementById('classInfo').style.display = 'inline';
		document.getElementById('calendarYear').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">列印範圍以系所為單位，謝謝！！</font>');
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
		document.getElementById('stermS').disabled = false;
		document.getElementById('unitDept').style.display = 'none';
		document.getElementById('classInfo').style.display = 'inline';
		document.getElementById('calendarYear').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">僅就有符合跨系選修之學生進行表列,不表列學生之全部跨系選課清單！！<br/>&nbsp;通識班級選課不列入跨系選課範圍！！<br/>&nbsp;非本學期跨選資料將以歷年成績處理之！！</font>');
	}

	if(type == 'StayTimePrint') {
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
		document.getElementById('unitDept').style.display = 'none';
		document.getElementById('classInfo').style.display = 'inline';
		document.getElementById('calendarYear').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;專任教師留校時間列印所需時間較長，請耐心等候。');
	}
	
	if (type == 'IdnoCheck') {
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
		document.getElementById('unitDept').style.display = 'none';
		document.getElementById('classInfo').style.display = 'inline';
		document.getElementById('calendarYear').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('');
	} 
	
	if (type == 'Rc1-7' || type == 'Rc1-8' || type == 'Rc1-9' || type == 'Rc1-10' || type == 'Rc1-11' || type == 'Rc1-12' || type == 'Rc1-13') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'inline';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('previewB').disabled = false;
		document.getElementById('stermS').disabled = true;
		document.getElementById('year').style.display = 'none';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('unitDept').style.display = 'inline';
		document.getElementById('classInfo').style.display = 'none';
		document.getElementById('calendarYear').style.display = 'inline';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;老師所屬系所單位，以人事室資料為主。');
	}
	
	if (type == 'AbilityList') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'inline';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('previewB').disabled = false;
		document.getElementById('stermS').disabled = true;
		document.getElementById('year').style.display = 'inline';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('unitDept').style.display = 'none';
		document.getElementById('classInfo').style.display = 'inline';
		document.getElementById('calendarYear').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;請選擇班級進行處理。');
	}
	
	if (type == 'StdSkillList') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'inline';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('previewB').disabled = false;
		document.getElementById('stermS').disabled = false;
		document.getElementById('year').style.display = 'inline';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('unitDept').style.display = 'none';
		document.getElementById('classInfo').style.display = 'inline';
		document.getElementById('calendarYear').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;請選擇班級進行處理。');
	}
	
	if (type == 'LicenseCodes' || type == 'LicenseCodes961' || type == 'CscodeList') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'inline';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('previewB').disabled = false;
		document.getElementById('stermS').disabled = true;
		document.getElementById('year').style.display = 'none';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('unitDept').style.display = 'none';
		document.getElementById('classInfo').style.display = 'none';
		document.getElementById('calendarYear').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;');
	}
	
	if (type == 'DeptStdSkillList') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'inline';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('previewB').disabled = false;
		document.getElementById('stermS').disabled = true;
		document.getElementById('year').style.display = 'none';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('unitDept').style.display = 'none';
		document.getElementById('classInfo').style.display = 'none';
		document.getElementById('calendarYear').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;');	
	}
	
	if (type == 'DeptStdSkillList-1' || type == 'DeptStdSkillList-2' || type == 'DeptStdSkillList-3'
		|| type == 'DeptStdSkillList-4' || type == 'DeptStdSkillList-5') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'inline';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('previewB').disabled = false;
		document.getElementById('stermS').disabled = true;
		document.getElementById('year').style.display = 'none';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('unitDept').style.display = 'none';
		document.getElementById('classInfo').style.display = 'none';
		document.getElementById('calendarYear').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'inline';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;');	
	}
	
}
</script>