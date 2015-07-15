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
	
	//if ("All" == doc.schoolInCharge2.value || "" == doc.schoolInCharge2.value
	//	|| "All" == doc.deptInCharge2.value || "" == doc.deptInCharge2.value
	//	|| "All" == doc.deptInCharge2.value || "" == doc.deptInCharge2.value) {
	//	if (doc.printOpt.value != 'StayTimeList' && doc.printOpt.value != 'AmsWorkdateList') {
	//		alert("開課班級選擇範圍過大");
	//		doc.deptInCharge2.focus();
	//		bFlag = false;
	//	}
	//} else if ("none" == doc.printOpt.value) {
	//	alert("請選擇報表項目");
	//	doc.printOpt.focus();
	//	bFlag = false;
	//}
	var innerClass = "";
	var failType = "";
	var reportType = "";
	var rankType = "";
	var thisTermScore = "";
	var stayTimeChangeCounts = "";
	
	var type = doc.printOpt.value;
	if (type == 'StdSkillList') { 
		var iCount = document.getElementById("startDate").value;
		if (iCount == '') {
			alert("起始時間不可為空白,謝謝!!");
			return false;
		}
	}
	
	//if (document.getElementsByName("interClass")[0].checked) 
	//	innerClass = document.getElementsByName("interClass")[0].value;
	//else
	//	innerClass = document.getElementsByName("interClass")[1].value;	
		
	//if (document.getElementsByName("failType")[0].checked)
	//	failType = document.getElementsByName("failType")[0].value;
	//else
	//	failType = document.getElementsByName("failType")[1].value;
		
	//if (document.getElementsByName("reportType")[0].checked)
	//	reportType = document.getElementsByName("reportType")[0].value;
	//else
	//	reportType = document.getElementsByName("reportType")[1].value;
		
	//if (document.getElementsByName("rankType")[0].checked)
	//	rankType = document.getElementsByName("rankType")[0].value;
	//else
	//	rankType = document.getElementsByName("rankType")[1].value;
	
	//if (document.getElementById("termScore").checked) {
		// alert(document.getElementById("termScore").value);
	//	thisTermScore = document.getElementById("termScore").value
	//} 
	
	if (document.getElementById("stcc") != null) {
		stayTimeChangeCounts = document.getElementById("stcc").value;
	}
				
	var str = "&p=" + doc.printOpt.value + "&st=" + doc.sterm.value + "&stcc=" + stayTimeChangeCounts
		+ "&year=" + doc.year.value + "&dcp=" + doc.deptCodeOpt.value
		+ "&sd=" + doc.startDate.value + "&ed=" + doc.endDate.value
		+ "&sd1=" + doc.startDate1.value + "&ed1=" + doc.endDate1.value + "&odc=" + doc.opDeptCodeOpt.value;
	if (isPreview && bFlag) {	
		// alert(str);
		subwin = window.open("/CIS/Secretary/ReportPrint.do?method=preview" + str, "預覽列印",
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

<html:form action="/Secretary/ReportPrint" method="post" onsubmit="init('報表產生中, 請稍後')">
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<script>
	generateTableBanner('<table align="left"><tr><td align="left">&nbsp;&nbsp;<img src="images/16-cube-debug.png"></td><td>報表列印與預覽</td></tr></table>');
</script>
	<tr>
		<td>
			<table class="empty-border">
				<tr>
					<td>
						<span id="year" style="display:none;">學年: 第 
							<html:select property="year" size="1">
	    						<html:options property="year" labelProperty="year" />	    						
	    					</html:select>學年&nbsp;&nbsp;&nbsp;&nbsp;
	    				</span>	
						學期: 第 
						<html:select property="sterm" styleId="stermS">
							<html:option value=""></html:option>
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
    					<span id="stayTimeChangeCounts" style="display:none;">變更次數: 
    						<input type="text" id="stcc" name="stcc" size="1" maxlength="1" value="2"/>(含)以上
    					</span>
    					<span id="deptCodes" style="display:none;">系所:
    						<select name="deptCodeOpt" size="1">
    							<option value="">全部</option>
    							<option value="1">機械工程系</option>
	    						<option value="2">電機工程系</option>
	    						<option value="3">電子工程系</option>
	    						<option value="4">工業工程與管理系</option>
	    						<option value="5">建築工程系</option>
	    						<option value="6">土木工程系</option>
	    						<option value="7">企業管理系</option>
	    						<option value="8">國際貿易系</option>
	    						<option value="9">財務金融系</option>
	    						<option value="A">航空機械系</option>
	    						<option value="B">航空電子系</option>
	    						<option value="C">航空服務管理系</option>
	    						<option value="D">資訊管理系</option>
	    						<option value="E">資訊工程系</option>
	    						<option value="F">食品科學系</option>
	    						<option value="H">生物科技系</option>
	    						<option value="I">餐飲管理系</option>
	    						<option value="J">觀光事業管理系</option>
	    						<option value="K">國際企業系</option>
	    						<option value="U">觀光餐旅系</option>
	    						<option value="0">語言中心</option>
    						</select>
    					</span>
    					<span id="opDeptCodes" style="display:none;">單位:
    						<select name="opDeptCodeOpt" size="1">
    							<option value="1">日間部教務處</option>
	    						<option value="2">日間部學務處</option>
	    						<option value="3">總務處</option>
	    						<option value="4">進修部教務組</option>
	    						<option value="5">進修部學務組</option>
	    						<option value="6">進修學院教務組</option>
	    						<option value="7">進修學院學務組</option>
	    						<option value="8">會計室</option>
	    						<option value="9">秘書室</option>
	    						<option value="10">人事室</option>
	    						<option value="11">電算中心</option>
	    						<option value="12">新竹分部主任</option>
	    						<option value="13">新竹分部教務組</option>
	    						<option value="14">新竹分部學務組</option>
	    						<option value="15">新竹分部總務組</option>
	    						<option value="16">新竹分部會計</option>
	    						<option value="17">校本部秘書室</option>
	    						<option value="18">校本部人事室</option>
	    						<option value="19">新竹分部電算中心</option>
	    						<option value="20">圖書館</option>
	    						<option value="21">新竹分部圖書館</option>
	    						<option value="">全部</option>
    						</select>
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
	    				<span id="calendarDateRange" style="display:none;">&nbsp;&nbsp;日期
	    					<input type="text" name="startDate1" size="10" maxlength="8" readonly>
    						<img src="images/cal.gif" name="calendar" width="20" height="20"
		      					align="top" style="cursor:hand" alt="點選此處選擇日期"
		  	  					onclick="javascript:if(!startDate1.disabled)popCalFrame.fPopCalendar('startDate1','startDate1',event);">&nbsp;~&nbsp;
		  	  				<input type="text" name="endDate1" size="10" maxlength="8" readonly>
    						<img src="images/cal.gif" name="calendar" width="20" height="20"
		      					align="top" style="cursor:hand" alt="點選此處選擇日期"
		  	  					onclick="javascript:if(!endDate1.disabled)popCalFrame.fPopCalendar('endDate1','endDate1',event);">&nbsp;	
	    				</span>
    				</td>    				
				</tr>
				<tr>
					<td><!-- bean:message key="OpenCourse.label.classNumber" bundle="COU"/>: -->
				   		<c:set var="campusSel" value="${ReportPringForm.map.campusInCharge2}"/>
	  			   		<c:set var="schoolSel" value="${ReportPringForm.map.schoolInCharge2}"/>
	  			   		<c:set var="deptSel" value="${ReportPringForm.map.deptInCharge2}"/>
	  			   		<c:set var="classSel" value="${ReportPringForm.map.classInCharge2}"/>
	  			   		<c:set var="classLess" value="${ReportPringForm.map.classLess}"/>
	  			   		<!--%@include file="/pages/include/ClassSelect5.jsp"%-->
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
							<option value="StayTimeList" <c:if test="${ReportPringForm.map.opt == 'StayTimeOverCounts'}">selected</c:if>>專任教師留校時間清單</option>
							<option value="LicenseCodes" <c:if test="${ReportPringForm.map.opt == 'LicenseCodes'}">selected</c:if>>報部證照代碼對照表列印</option>
							<option value="StdSkillList" <c:if test="${ReportPringForm.map.opt == 'StdSkillList'}">selected</c:if>>證照資料列印</option>
							<option value="StdSkillList-1" <c:if test="${ReportPringForm.map.opt == 'StdSkillList-1'}">selected</c:if>>系所證照統計列印</option>
							<option value="CalendarList" <c:if test="${ReportPringForm.map.opt == 'CalendarList'}">selected</c:if>>行事曆列印</option>
							<option value="OpinionList" <c:if test="${ReportPringForm.map.opt == 'OpinionList'}">selected</c:if>>學生意見反映統計列印</option>
							<option value="OpinionDetailList" <c:if test="${ReportPringForm.map.opt == 'OpinionDetailList'}">selected</c:if>>學生意見反映明細列印</option>
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
						<!--<html:checkbox property="setupPrinter">設定印表機</html:checkbox>&nbsp;&nbsp;-->
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
	document.getElementById('stermS').disabled = false;
	if(type == 'StayTimeOverCounts') {
		document.getElementById('stermS').disabled = false;
		document.getElementById('stayTimeChangeCounts').style.display = 'inline';
	}
	
	if (type == 'StayTimeList') {
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
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('calendarDateRange').style.display = 'none';
		document.getElementById('opDeptCodes').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;');
	}
	
	if (type == 'LicenseCodes') {
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
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('calendarDateRange').style.display = 'none';
		document.getElementById('opDeptCodes').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;');
	}
	
	if(type == 'StdSkillList') {
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
		document.getElementById('stermS').disabled = false;
		document.getElementById('deptCodes').style.display = 'inline';
		document.getElementById('licenseDateRange').style.display = 'inline';
		document.getElementById('calendarDateRange').style.display = 'none';
		document.getElementById('opDeptCodes').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">僅就符合所選之學年學期進行證照表列！！</font>');
	}
	
	if(type == 'StdSkillList-1') {
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
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('calendarDateRange').style.display = 'none';
		document.getElementById('opDeptCodes').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;');
	}
	
	if(type == 'CalendarList') {
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
		document.getElementById('stermS').disabled = true;
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('calendarDateRange').style.display = 'inline';
		document.getElementById('opDeptCodes').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">僅就符合所選之學年學期進行證照表列！！</font>');
	}
	
	if(type == 'OpinionList') {
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
		document.getElementById('stermS').disabled = false;
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('calendarDateRange').style.display = 'none';
		document.getElementById('opDeptCodes').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red"></font>');
	}
	
	if(type == 'OpinionDetailList') {
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
		document.getElementById('stermS').disabled = false;
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('calendarDateRange').style.display = 'none';
		document.getElementById('opDeptCodes').style.display = 'inline';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red"></font>');
	}
		
}
</script>