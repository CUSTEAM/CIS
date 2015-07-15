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
	if ("Syllabus" == doc.printOpt.value && ("All" == doc.schoolInCharge2.value || "" == doc.schoolInCharge2.value
		|| "All" == doc.deptInCharge2.value || "" == doc.deptInCharge2.value
		|| "All" == doc.deptInCharge2.value || "" == doc.deptInCharge2.value)) {
		alert("開課班級選擇範圍過大");
		doc.deptInCharge2.focus();
		bFlag = false;
	} else if ("none" == doc.printOpt.value) {
		alert("請選擇報表項目");
		doc.printOpt.focus();
		bFlag = false;
	} else if (doc.printOpt.value == 'StdSkillList') { 
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
	var thisTermScore = "";
	
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
	
	if (document.getElementById("termScore").checked) {
		// alert(document.getElementById("termScore").value);
		thisTermScore = document.getElementById("termScore").value
	}
				
	var str = "&c=" + doc.campusInCharge2.value + "&s=" + doc.schoolInCharge2.value + "&d=" + doc.deptInCharge2.value +
		"&cl=" + doc.classInCharge2.value + "&p=" + doc.printOpt.value + "&st=" + doc.sterm.value + "&year=" + doc.year.value + 
		"&printInterClass=" + innerClass + "&ft=" + failType + "&rt=" + reportType + "&rank=" + rankType + "&tt=" + thisTermScore +
		"&dcp=" + doc.deptCodeOpt.value + "&sd=" + doc.startDate.value + "&ed=" + doc.endDate.value +
		"&dc=" + doc.dayCode.value + "&nc=" + doc.nodeCode.value + "&cc=" + doc.campusCode.value + "&stc=" + doc.schoolType.value;
	if (isPreview && bFlag) {	
		// alert(str);
		subwin = window.open("/CIS/Registration/ReportPrint.do?method=preview" + str, "預覽列印",
			"width=600,height=600,scrollbars=yes,resizable=yes");
	}	
}

</script>

<html:form action="/Registration/ReportPrint" method="post" onsubmit="init('報表產生中, 請稍後')">
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
    					<span id="thisTermScore" style="display:none;">
    						<input type="checkbox" id="termScore" name="termScore" value="true" checked />將本學期成績以60分(碩士班70分)計
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
	    				<span id="stayData" style="display:none;">星期
							<select name="dayCode" size="1">
								<option value="1">一</option>
	    						<option value="2">二</option>
	    						<option value="3">三</option>
	    						<option value="4">四</option>
	    						<option value="5">五</option>
	    						<option value="6">六</option>
	    						<option value="7">日</option>
	    					</select>&nbsp;&nbsp;&nbsp;&nbsp;節次
	    					<select name="nodeCode" size="1">
	    						<option value=""></option>
	    						<option value="1">第一節</option>
	    						<option value="2">第二節</option>
	    						<option value="3">第三節</option>
	    						<option value="4">第四節</option>
	    						<option value="5">第五節</option>
	    						<option value="6">第六節</option>
	    						<option value="7">第七節</option>
	    						<option value="8">第八節</option>
	    						<option value="9">第九節</option>
	    						<option value="10">第十節</option>
	    						<option value="N1">夜一節</option>
	    						<option value="N2">夜二節</option>
	    						<option value="N3">夜三節</option>
	    						<option value="N4">夜四節</option>
	    					</select>
	    				</span>
	    				<span id="campus" style="display:none;">校區:
    						<html:select property="campusCode" size="1">  					
    							<html:option value="1">台北</html:option>
    							<html:option value="2">新竹</html:option>
    						</html:select>&nbsp;&nbsp;&nbsp;&nbsp;部別
    						<html:select property="schoolType" size="1">			
    							<html:option value="D">日間部</html:option>
    							<html:option value="N">進修部</html:option>
    							<html:option value="H">進專學院</html:option>
    						</html:select>
    					</span>
    				</td>    				
				</tr>
				<tr>
					<td><bean:message key="OpenCourse.label.classNumber" bundle="COU"/>:
				   		<c:set var="campusSel" value="${ReportPringForm.map.campusInCharge2}"/>
	  			   		<c:set var="schoolSel" value="${ReportPringForm.map.schoolInCharge2}"/>
	  			   		<c:set var="deptSel" value="${ReportPringForm.map.deptInCharge2}"/>
	  			   		<c:set var="classSel" value="${ReportPringForm.map.classInCharge2}"/>
	  			   		<c:set var="classLess" value="${ReportPringForm.map.classLess}"/>
	  			   		<%@include file="/pages/include/ClassSelect5.jsp"%>
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
							<option value="RegisterList" <c:if test="${ReportPringForm.map.opt == 'RegisterList'}">selected</c:if>>註冊會辦單</option>
							<option value="ClassStudentsScoreHistory" <c:if test="${ReportPringForm.map.opt == 'ClassStudentsScoreHistory'}">selected</c:if>>全班歷年成績表</option>
							<option value="ClassStudentsScoreHistory4CreditClass" <c:if test="${ReportPringForm.map.opt == 'ClassStudentsScoreHistory4CreditClass'}">selected</c:if>>全班歷年成績表(學院專用)</option>
							<option value="ClassStudentsRegistrationCard" <c:if test="${ReportPringForm.map.opt == 'ClassStudentsRegistrationCard'}">selected</c:if>>新生全班學籍卡</option>
							<option value="IdnoCheck" <c:if test="${ReportPringForm.map.opt == 'IdnoCheck'}">selected</c:if>>學生身分證字號檢核</option>
							<option value="StmdUnSeld" <c:if test="${ReportPringForm.map.opt == 'StmdUnSeld'}">selected</c:if>>本學期學生未選課清單</option>
							<option value="StayTimePrint" <c:if test="${ReportPringForm.map.opt == 'StayTimePrint'}">selected</c:if>>專任教師留校時間列印</option>
							<option value="Calculate" <c:if test="${ReportPringForm.map.opt == 'Calculate'}">selected</c:if>>符合跨系選課學生資料</option>
							<option value="GstmdList4Ntnu" <c:if test="${ReportPringForm.map.opt == 'GstmdList4Ntnu'}">selected</c:if>>應屆畢業生基本資料(師大格式)</option>
							<option value="GstmdList4Ntnu1" <c:if test="${ReportPringForm.map.opt == 'GstmdList4Ntnu1'}">selected</c:if>>大一大三學生基本資料(師大格式)</option>
							<option value="Listing4-1" <c:if test="${ReportPringForm.map.opt == 'Listing4-1'}">selected</c:if>>報部表4-1</option>
							<option value="Listing4-2" <c:if test="${ReportPringForm.map.opt == 'Listing4-2'}">selected</c:if>>報部表4-2</option>
							<option value="Listing4-4-1" <c:if test="${ReportPringForm.map.opt == 'Listing4-4-1'}">selected</c:if>>報部表4-4-1</option>
							<option value="StdSkillList" <c:if test="${ReportPringForm.map.opt == 'StdSkillList'}">selected</c:if>>系所證照資料列印</option>
							<option value="GstmdCreditAvg" <c:if test="${ReportPringForm.map.opt == 'GstmdCreditAvg'}">selected</c:if>>畢業班畢業學分數資料</option>
							<option value="RegisterManagerList" <c:if test="${ReportPringForm.map.opt == 'RegisterManagerList'}">selected</c:if>>註冊檔資料清單</option>
							<option value="UploadNewStmdTemplate" <c:if test="${ReportPringForm.map.opt == 'UploadNewStmdTemplate'}">selected</c:if>>下載新生基本資料樣本檔(未編班編學號)</option>
							<option value="UploadNewStmdTemplate1" <c:if test="${ReportPringForm.map.opt == 'UploadNewStmdTemplate1'}">selected</c:if>>下載新生基本資料樣本檔(已編班編學號)</option>
						</html:select>
						<input type="text" name="checkCredit" id="checkCredit" <c:if test="${ReportPringForm.map.checkOpt!='CheckCredit'}">style="display:none;"</c:if> size="1" value="${ReportPringForm.map.checkCredit}"/><img id="point" style="display:none;" src="images/16-exc-mark.gif" />
					</td>
					<td>
						<html:submit styleId="searchB" property="method" style="display='none'" styleClass="CourseButton"><bean:message key="score.reportPrint.excludeCourseSearch" bundle="SCR" /></html:submit>&nbsp;
						<html:button styleId="previewB" property="method" styleClass="CourseButton" onclick="preview(true);"><bean:message key="Preview" /></html:button>&nbsp;
						<!--<html:checkbox property="setupPrinter">設定印表機</html:checkbox>&nbsp;&nbsp;-->
						<html:image property="pdf" src="images/acroread.png" title="下載Adobe Reader" />						
						<html:link href="http://www.chinese-t.adobe.com/products/acrobat/readstep2.html?c=tw" title="下載Adobe Reader" target="_blank">
							<font color="red">下載Adobe Reader</font>
						</html:link>
					</td>					
				</tr>
			</table>
		</td>
	</tr>	
	
	<c:if test="${not empty dtimeList1}">
	<tr><td>&nbsp;</td></tr>
	<script>generateTableBanner('<div class="gray_15"><B>納 入 期 中 成 績 之 科 目 清 單</B></div>');</script>
    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>    
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
	 			<tr>
	 				<td align="center">
      					<display:table name="${dtimeList1}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	        				<%@ include file="../include/NoBanner.jsp" %>
        					<display:column title="<script>generateTriggerAll(${fn:length(dtimeList1)}, 'dtimeList1'); </script>" class="center">
								<script>generateCheckbox("${row.oid}", "dtimeList1")</script>
							</display:column>
							<display:column titleKey="Course.label.className" property="departClass2" sortable="false" class="center" />
					        <display:column titleKey="Course.label.courseNumber" property="cscode" sortable="false" class="center" />
					        <display:column titleKey="Course.label.courseName" property="chiName2" sortable="true" class="center" />
					        <display:column titleKey="Course.label.opt" property="opt2" sortable="false" class="center" />
					        <display:column titleKey="Course.label.credit" property="credit" sortable="false" class="center" />
					        <display:column titleKey="Course.label.hours" property="thour" sortable="false" class="center" />					        
      					</display:table>
      				</td>
      			</tr>
      			<script>
   					generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='score.reportPrint.deleteExcludeCourse' bundle="SCR" />" onclick="return deleteCourseCheck();" class="CourseButton">');
   				</script>
    		</table>
    	</td>
    </tr>
	</c:if>	
	
	<c:if test="${not empty dtimeList2}">
	<script>generateTableBanner('<div class="gray_15"><B>不 納 入 期 中 成 績 之 科 目 清 單</B></div>');</script>
    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
    <tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
	 			<tr>
	 				<td align="center">
      					<display:table name="${dtimeList2}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	        				<%@ include file="../include/NoBanner.jsp" %>
        					<display:column title="<script>generateTriggerAll(${fn:length(dtimeList2)}, 'dtimeList2'); </script>" class="center">
								<script>generateCheckbox("${row.oid}", "dtimeList2")</script>
							</display:column>
							<display:column titleKey="Course.label.className" property="departClass2" sortable="false" class="center" />
					        <display:column titleKey="Course.label.courseNumber" property="cscode" sortable="false" class="center" />
					        <display:column titleKey="Course.label.courseName" property="chiName2" sortable="true" class="center" />
					        <display:column titleKey="Course.label.opt" property="opt2" sortable="false" class="center" />
					        <display:column titleKey="Course.label.credit" property="credit" sortable="false" class="center" />
					        <display:column titleKey="Course.label.hours" property="thour" sortable="false" class="center" />					        
      					</display:table>
      				</td>
      			</tr>
      			<script>
   					generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='score.reportPrint.addExcludeCourse' bundle="SCR" />" onclick="return addCourseCheck();" class="CourseButton">&nbsp;');
   				</script>
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
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('stayData').style.display = 'none';
		document.getElementById('campus').style.display = 'none';
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
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('stayData').style.display = 'none';
		document.getElementById('campus').style.display = 'none';
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
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('stayData').style.display = 'none';
		document.getElementById('campus').style.display = 'none';
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
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('stayData').style.display = 'none';
		document.getElementById('campus').style.display = 'none';
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
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('stayData').style.display = 'none';
		document.getElementById('campus').style.display = 'none';
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
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('stayData').style.display = 'none';
		document.getElementById('campus').style.display = 'none';
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
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('stayData').style.display = 'none';
		document.getElementById('campus').style.display = 'none';
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
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('stayData').style.display = 'none';
		document.getElementById('campus').style.display = 'none';
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
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('stayData').style.display = 'none';
		document.getElementById('campus').style.display = 'none';
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
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('stayData').style.display = 'none';
		document.getElementById('campus').style.display = 'none';
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
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('stayData').style.display = 'none';
		document.getElementById('campus').style.display = 'none';
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
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('stayData').style.display = 'none';
		document.getElementById('campus').style.display = 'none';
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
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('stayData').style.display = 'none';
		document.getElementById('campus').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;列印學期成績雙1/2不及格學生名單前，<font color="red">須先執行期中期末全班成績單列印程式</font>，謝謝。');
	}
	
	if(type == 'StdSkillList') {
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
		document.getElementById('deptCodes').style.display = 'inline';
		document.getElementById('licenseDateRange').style.display = 'inline';
		document.getElementById('stayData').style.display = 'none';
		document.getElementById('campus').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">僅就符合所選之學年學期、系所與區間進行證照表列！！</font>');
	}
	
	if (type == 'StayTimePrint') {
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
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('stayData').style.display = 'inline';
		document.getElementById('campus').style.display = 'none';
	}
	
	if (type == 'GstmdCreditAvg') {
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
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('stayData').style.display = 'none';
		document.getElementById('campus').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">只需下拉選擇要統計之畢業學年學期即可！！</font>');
	}
	
	if (type == 'RegisterManagerList') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'inline';
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
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('stayData').style.display = 'none';
		document.getElementById('campus').style.display = 'inline';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">只需下拉選擇要統計之畢業學年學期即可！！</font>');
	}
	
	if (type == 'UploadNewStmdTemplate' || type == 'UploadNewStmdTemplate1') {
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
		document.getElementById('deptCodes').style.display = 'none';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('stayData').style.display = 'none';
		document.getElementById('campus').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">只需按下預覽鈕即可</font>');
	}
		
}
</script>