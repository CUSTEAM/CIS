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
	
	if (doc.printOpt.value == 'AmsWorkdataList' || doc.printOpt.value == 'DocApplyList'
		|| doc.printOpt.value == 'EmplWorkdateList' || doc.printOpt.value == 'EmplAmsWorkdataList'
		|| doc.printOpt.value == 'QueryByList' || doc.printOpt.value == 'EmplWorkdateListE') {
		if (doc.startDate.value == '' || doc.endDate.value == '') {
			alert("請輸入查詢範圍,謝謝");
			bFlag = false;
		} else if (doc.printOpt.value == 'EmplAmsWorkdataList') {
			if (doc.cname2.value == '') {
				alert("請輸入員工姓名,謝謝");
				bFlag = false;
			}
		} else if (doc.printOpt.value == 'QueryByList') {
			if (doc.conditionTimes.value == '') {
				alert("請輸入次數範圍,謝謝");
				document.getElementById("conditionTimes").focus();
				bFlag = false;
			}
		}
	}
				
	var str = "&p=" + doc.printOpt.value + "&st=" + doc.sterm.value + "&year=" + doc.year.value + "&sd=" + doc.startDate.value + "&ed=" + doc.endDate.value
		+ "&printInterClass=" + innerClass + "&ft=" + failType + "&rt=" + reportType + "&rank=" + rankType + "&uc=" + doc.unitCode.value
		+ "&cnm=" + doc.cname2.value + "&cc=" + doc.conditionCode.value + "&ct=" + doc.conditionTimes.value;;
	if (isPreview && bFlag) {	
		// alert(str);
		subwin = window.open("/CIS/AMS/ReportPrint.do?cg="+document.getElementById('emplCategory').value+"&method=preview" + str, "預覽列印",
			"width=600,height=600,scrollbars=yes,resizable=yes");
	}	
}
</script>

<html:form action="/AMS/ReportPrint" method="post" onsubmit="init('報表產生中, 請稍後')">
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
						開課學期: 第 
						<html:select property="sterm" styleId="stermS">
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
    					</span>
    					<span id="rank" style="display:none;">排名類型: 
    						<input type="radio" id="rankType" name="rankType" checked value="c"/>班排名&nbsp;<input type="radio" id="rankType" name="rankType" value="d"/>系排名
    					</span>
    					<span id="dateRange" style="display:none;">時間範圍:
    						<input type="text" name="startDate" size="8" maxlength="8" readonly>
    						<img src="images/cal.gif" name="calendar" width="20" height="20" 
		      					align="top" style="cursor:hand" alt="點選此處選擇日期"
		  	  					onclick="javascript:if(!startDate.disabled)popCalFrame.fPopCalendar('startDate','startDate',event);">&nbsp;~&nbsp;
		  	  				<input type="text" name="endDate" size="8" maxlength="8" readonly>&nbsp;
			 	   			<img src="images/cal.gif" name="calendar" width="20" height="20" 
		      					align="top" style="cursor:hand" alt="點選此處選擇日期"
		  	  					onclick="javascript:if(!endDate.disabled)popCalFrame.fPopCalendar('endDate','endDate',event);">	
    					&nbsp;&nbsp;</span>
    					<span id="emplName" style="display:none;">員工身分證字號:
    						<html:text property="cname2" size="10" maxlength="10" />
    					&nbsp;&nbsp;</span>
    					<span id="unitDept" style="display:none;">&nbsp;&nbsp;單位
							<html:select property="unitCode" size="1">
	    						<html:option value="A">所有單位</html:option>  
	    						<html:options property="unitCodes" labelProperty="unitNames" />
	    					</html:select>
	    				&nbsp;&nbsp;</span>
	    				<span id="condition" style="display:none;">&nbsp;&nbsp;條件
							<html:select property="conditionCode" size="1">
	    						<html:option value="1">事假</html:option>
	    						<html:option value="2">病假</html:option>
	    						<html:option value="3">公假</html:option>
	    						<html:option value="ai">補登</html:option>
	    					</html:select>&nbsp;&nbsp;次數&nbsp;&nbsp;<html:text property="conditionTimes" size="3" maxlength="3" />
	    				&nbsp;&nbsp;</span>
	    				
	    				<span id="category" style="display:none;">
							<select name="emplCategory">
	    						<option value="1">專任教師</option>
	    						<option value="2">兼任教師</option>	    
	    						<option value="3">職員工</option>
	    						<option value="4">軍護教師</option>
	    					</select>
	    				&nbsp;&nbsp;</span>
    				</td>    				
				</tr>
				<tr>
					<td>
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
							<option value="AmsWorkdataList" <c:if test="${ReportPringForm.map.opt == 'AmsWorkdataList'}">selected</c:if>>刷卡紀錄清單</option>
							<option value="DocApplyList" <c:if test="${ReportPringForm.map.opt == 'DocApplyList'}">selected</c:if>>請假紀錄清單</option>
							<option value="MeetingDocApplyList" <c:if test="${ReportPringForm.map.opt == 'MeetingDocApplyList'}">selected</c:if>>重要集會請假紀錄清單</option>
							<option value="EmplWorkdateList" <c:if test="${ReportPringForm.map.opt == 'EmplWorkdateList'}">selected</c:if>>員工請假紀錄</option>
							<option value="EmplWorkdateListE" <c:if test="${ReportPringForm.map.opt == 'EmplWorkdateListE'}">selected</c:if>>員工差勤統計</option>
							<option value="AmsWorkdateList" <c:if test="${ReportPringForm.map.opt == 'AmsWorkdateList'}">selected</c:if>>應上班人員班表</option>
							<option value="VacationList" <c:if test="${ReportPringForm.map.opt == 'VacationList'}">selected</c:if>>休假統計表</option>
							<option value="QueryByList" <c:if test="${ReportPringForm.map.opt == 'QueryByList'}">selected</c:if>>查核報表</option>
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

	if(type == 'AmsWorkdateList') {
		document.getElementById('stermS').disabled = false;
	}
	
	document.getElementById('helpMsg').innerHTML = '';
	document.getElementById('year').style.display = 'none';
	document.getElementById('interClassId').style.display = 'none';
	document.getElementById('report').style.display = 'none';
	document.getElementById('fail').style.display = 'none';
	document.getElementById('rank').style.display = 'none';
	document.getElementById('stermS').disabled = false;
	if(type == 'AmsWorkdataList' || type == 'VacationList') {
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
		document.getElementById('dateRange').style.display = 'inline';
		document.getElementById('stermS').disabled = false;
		document.getElementById('unitDept').style.display = 'inline';
		document.getElementById('emplName').style.display = 'none';
		document.getElementById('condition').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">時間範圍為必填欄位，謝謝！！</font>');
	}
	
	if(type == 'DocApplyList') {
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
		document.getElementById('dateRange').style.display = 'inline';
		document.getElementById('stermS').disabled = false;
		document.getElementById('unitDept').style.display = 'none';
		document.getElementById('emplName').style.display = 'none';
		document.getElementById('condition').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">時間範圍為必填欄位，謝謝！！</font>');
	}
	
	if(type == 'AmsWorkdateList') {
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
		document.getElementById('dateRange').style.display = 'none';
		document.getElementById('stermS').disabled = false;
		document.getElementById('unitDept').style.display = 'none';
		document.getElementById('emplName').style.display = 'none';
		document.getElementById('condition').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">直接點選預覽鈕即可</font>');
	}
	
	if(type == 'MeetingDocApplyList') {
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
		document.getElementById('dateRange').style.display = 'inline';
		document.getElementById('stermS').disabled = false;
		document.getElementById('unitDept').style.display = 'none';
		document.getElementById('emplName').style.display = 'none';
		document.getElementById('condition').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">時間範圍為必填欄位，謝謝！！</font>');
	}
	
	if(type == 'EmplWorkdateListE'){
		document.getElementById('category').style.display = 'inline';
		document.getElementById('emplCategory').style.display = 'inline';
	}else{
		document.getElementById('emplCategory').style.display = 'none';
	}
	
	if(type == 'EmplWorkdateList' || type == 'EmplWorkdateListE') {		
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
		document.getElementById('dateRange').style.display = 'inline';
		document.getElementById('stermS').disabled = false;
		document.getElementById('unitDept').style.display = 'inline';
		document.getElementById('emplName').style.display = 'none';
		document.getElementById('condition').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">列印範圍以班為單位，謝謝！！</font>');
	} 
	
	if(type == 'EmplAmsWorkdataList') {
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
		document.getElementById('dateRange').style.display = 'inline';
		document.getElementById('stermS').disabled = false;
		document.getElementById('unitDept').style.display = 'none';
		document.getElementById('emplName').style.display = 'inline';
		document.getElementById('condition').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red"></font>');
	}

	if (type == 'QueryByList') {
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
		document.getElementById('dateRange').style.display = 'inline';
		document.getElementById('stermS').disabled = false;
		document.getElementById('unitDept').style.display = 'none';
		document.getElementById('emplName').style.display = 'none';
		document.getElementById('condition').style.display = 'inline';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">時間範圍為必填欄位，謝謝！！</font>');
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