<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<script language="javaScript">
globalTimeOut = globalTimeOut * 2;
function preview(isPreview) {
	var bFlag = true;
	var doc = document.forms[0];
	if ("All" == doc.schoolInCharge2.value) {
		alert("開課班級選擇範圍過大");
		doc.deptInCharge2.focus();
		bFlag = false;
	} else if ("none" == doc.printOpt.value) {
		alert("請選擇報表項目");
		doc.printOpt.focus();
		bFlag = false;
	}
	var innerClass = "";
	var failType = "";
	var reportType = "";
	var reportFormat = "";
	var rankType = "";
	var scholarshipType = "";
	var isLiteracy = "";
	if (document.getElementsByName("interClass")[0].checked) 
		innerClass = document.getElementsByName("interClass")[0].value;
	else
		innerClass = document.getElementsByName("interClass")[1].value;
		
	if (document.getElementsByName("isLiteracy")[0].checked) 
		isLiteracy = document.getElementsByName("isLiteracy")[0].value;
	else
		isLiteracy = document.getElementsByName("interClass")[1].value;	
		
	if (document.getElementsByName("failType")[0].checked)
		failType = document.getElementsByName("failType")[0].value;
	else
		failType = document.getElementsByName("failType")[1].value;
		
	if (document.getElementsByName("reportType")[0].checked)
		reportType = document.getElementsByName("reportType")[0].value;
	else
		reportType = document.getElementsByName("reportType")[1].value;
		
	if (document.getElementsByName("reportFormat")[0].checked)
		reportFormat = document.getElementsByName("reportFormat")[0].value;
	else
		reportFormat = document.getElementsByName("reportFormat")[1].value;	
		
	if (document.getElementsByName("rankType")[0].checked)
		rankType = document.getElementsByName("rankType")[0].value;
	else
		rankType = document.getElementsByName("rankType")[1].value;
		
	if (document.getElementsByName("scholarshipType")[0].checked)
		scholarshipType = document.getElementsByName("scholarshipType")[0].value;
	else
		scholarshipType = document.getElementsByName("scholarshipType")[1].value; 	
				
	var str = "&c=" + doc.campusInCharge2.value + "&s=" + doc.schoolInCharge2.value + "&d=" + doc.deptInCharge2.value +
		"&cl=" + doc.classInCharge2.value + "&p=" + doc.printOpt.value + "&st=" + doc.sterm.value + "&year=" + doc.year.value + 
		"&printInterClass=" + innerClass + "&ft=" + failType + "&rt=" + reportType + "&rf=" + reportFormat +  
		"&rank=" + rankType + "&tn=" + doc.termNum.value + "&sst=" + scholarshipType + "&il=" + isLiteracy;
	if (isPreview && bFlag) {	
		// alert(str);
		subwin = window.open("/CIS/Score/ReportPrint.do?method=preview" + str, "預覽列印",
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

<html:form action="/Score/ReportPrint" method="post" onsubmit="init('報表產生中, 請稍後')">
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
    					<span id="isLiteracyId" style="display:none;">是否建立通識班成績總表?					
    						<input type="radio" id="isLiteracy" name="isLiteracy" value="yes"/>是&nbsp;<input type="radio" id="isLiteracy" name="isLiteracy" checked value="no"/>否
    					&nbsp;&nbsp;</span>
    					<span id="report" style="display:none;">請選擇報表類型: 
    						<input type="radio" id="reportType" name="reportType" checked value="mid"/>期中&nbsp;<input type="radio" id="reportType" name="reportType" value="final"/>學期
    					&nbsp;&nbsp;</span>
    					<span id="reportForm" style="display:none;">請選擇報表格式: 
    						<input type="radio" id="reportFormat" name="reportFormat" checked value="pdf"/>PDF&nbsp;<input type="radio" id="reportFormat" name="reportFormat" value="xls"/>Excel
    					&nbsp;&nbsp;</span>
    					<span id="fail" style="display:none;">不及格名單類型: 
    						<input type="radio" id="failType" name="failType" checked value="2"/>1/2&nbsp;<input type="radio" id="failType" name="failType" value="3"/>2/3
    					</span>
    					<span id="termCount" style="display:none;">學期數:&nbsp;
    						<input type="text" id="termNum" name="termNum" size="1" maxlength="1" value=""/>
    					&nbsp;&nbsp;</span>
    					<span id="rank" style="display:none;">排名類型: 
    						<input type="radio" id="rankType" name="rankType" checked value="c"/>班排名&nbsp;<input type="radio" id="rankType" name="rankType" value="d"/>系排名
    					&nbsp;&nbsp;</span>
    					<span id="scholarship" style="display:none;">是否包括畢業班: 
    						<input type="radio" id="scholarshipType" name="scholarshipType" checked value="n"/>否&nbsp;<input type="radio" id="scholarshipType" name="scholarshipType" value="y"/>是
    					&nbsp;&nbsp;</span>
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
							<option value="MidtermFinalResult" <c:if test="${ReportPringForm.map.opt == 'MidtermFinalResult'}">selected</c:if>>期中/學期全班成績總表</option>
							<option value="FinalScoreNotice" <c:if test="${ReportPringForm.map.opt == 'FinalScoreNotice'}">selected</c:if>>期末個人成績通知單</option>
							<option value="MidtermFinalAvgResult" <c:if test="${ReportPringForm.map.opt == 'MidtermFinalAvgResult'}">selected</c:if>>期中/學期全班平均成績</option>
							<option value="MidtermFinalStatistic" <c:if test="${ReportPringForm.map.opt == 'MidtermFinalStatistic'}">selected</c:if>>期中/學期成績統計表</option>
							<option value="MidtermFinalFailStudentsResult" <c:if test="${ReportPringForm.map.opt == 'MidtermFinalFailStudentsResult'}">selected</c:if>>1/2或2/3不及格學生名單(全校)</option>
							<option value="MidtermFinalFailStudentsByClassResult" <c:if test="${ReportPringForm.map.opt == 'MidtermFinalFailStudentsByClassResult'}">selected</c:if>>1/2或2/3不及格學生名單(個人)</option>
							<option value="MidtermFinalTopStudentsResult" <c:if test="${ReportPringForm.map.opt == 'MidtermFinalTopStudentsResult'}">selected</c:if>>期中/學期各班前三名學生名單</option>
							<option value="MidtermFinalFailTwiceStudentsResult" <c:if test="${ReportPringForm.map.opt == 'MidtermFinalFailTwiceStudentsResult'}">selected</c:if>>學期成績雙1/2不及格名單</option>
							<option value="MidtermFinalFailStudentsTooMuch" <c:if test="${ReportPringForm.map.opt == 'MidtermFinalFailStudentsTooMuch'}">selected</c:if>>期中/學期教師任教科目不及格人數統計表</option>
							<option value="GraduatingScoreAvg" <c:if test="${ReportPringForm.map.opt == 'GraduatingScoreAvg'}">selected</c:if>>應屆畢業生平均成績表</option>
							<option value="ScholarshipStudentsList" <c:if test="${ReportPringForm.map.opt == 'ScholarshipStudentsList'}">selected</c:if>>學優獎學金名冊</option>
							<option value="StdAdcdList" <c:if test="${ReportPringForm.map.opt == 'StdAdcdList'}">selected</c:if>>個人加選科目成績清冊</option>
							<option value="NoneCounseling" <c:if test="${ReportPringForm.map.opt == 'NoneCounseling'}">selected</c:if>>未進行輔導紀錄學生清冊</option>
							<option value="MidtermCourseExcluded" <c:if test="${ReportPringForm.map.opt == 'MidtermCourseExcluded'}">selected</c:if>>期中成績不納入計算科目維護</option>
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
	document.getElementById('termCount').style.display = 'none';
	document.getElementById('stermS').disabled = false;
	if(type == 'FinalScoreNotice') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'none';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('year').style.display = 'inline';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('isLiteracyId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('reportForm').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('scholarship').style.display = 'none';
		document.getElementById('termCount').style.display = 'none';
		document.getElementById('stermS').disabled = false;
		document.getElementById('helpMsg').innerHTML = ('&nbsp;期末個人成績通知單列印所需時間較長，請耐心等候。<br/>&nbsp;列印期中/期末全班平均成績前，<font color="red">須先執行期中期末全班成績單列印程式</font>，謝謝。<br/>&nbsp;<font color="red">開課班級選擇範圍請勿過大，謝謝！！</font>');
	}
	
	if(type == 'MidtermFinalAvgResult') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('schoolType').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'none';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('year').style.display = 'none';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('isLiteracyId').style.display = 'none';
		document.getElementById('report').style.display = 'inline';
		document.getElementById('reportForm').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('scholarship').style.display = 'none';
		document.getElementById('termCount').style.display = 'none';
		document.getElementById('stermS').disabled = false;
		document.getElementById('helpMsg').innerHTML = ('&nbsp;列印期中/期末全班平均成績前，<font color="red">須先執行期中期末全班成績單列印程式</font>，謝謝。');
	}

	if(type == 'MidtermFinalResult') {
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
		document.getElementById('isLiteracyId').style.display = 'inline';
		document.getElementById('report').style.display = 'inline';
		document.getElementById('reportForm').style.display = 'inline';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('scholarship').style.display = 'none';
		document.getElementById('termCount').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;期中/期末全班成績單列印所需時間較長，請耐心等候。<br/>&nbsp;列印報表的紙張大小為<font color="red">B4</font>，謝謝。');
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
		document.getElementById('isLiteracyId').style.display = 'none';
		document.getElementById('report').style.display = 'inline';
		document.getElementById('reportForm').style.display = 'none';
		document.getElementById('fail').style.display = 'inline';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('scholarship').style.display = 'none';
		document.getElementById('termCount').style.display = 'none';
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
		document.getElementById('isLiteracyId').style.display = 'none';
		document.getElementById('report').style.display = 'inline';
		document.getElementById('reportForm').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('scholarship').style.display = 'none';
		document.getElementById('termCount').style.display = 'none';
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
		document.getElementById('isLiteracyId').style.display = 'none';
		document.getElementById('report').style.display = 'inline';
		document.getElementById('reportForm').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('scholarship').style.display = 'none';
		document.getElementById('termCount').style.display = 'none';
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
		document.getElementById('isLiteracyId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('reportForm').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('stermS').disabled = false;
		document.getElementById('scholarship').style.display = 'none';
		document.getElementById('termCount').style.display = 'none';
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
		document.getElementById('isLiteracyId').style.display = 'none';
		document.getElementById('report').style.display = 'inline';
		document.getElementById('reportForm').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('scholarship').style.display = 'none';
		document.getElementById('termCount').style.display = 'none';
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
		document.getElementById('isLiteracyId').style.display = 'none';
		document.getElementById('report').style.display = 'inline';
		document.getElementById('reportForm').style.display = 'none';
		document.getElementById('fail').style.display = 'inline';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('scholarship').style.display = 'none';
		document.getElementById('termCount').style.display = 'none';
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
		document.getElementById('isLiteracyId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('reportForm').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('scholarship').style.display = 'none';
		document.getElementById('termCount').style.display = 'none';
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
		document.getElementById('isLiteracyId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('reportForm').style.display = 'inline';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'inline';
		document.getElementById('scholarship').style.display = 'none';
		document.getElementById('termCount').style.display = 'inline';
	}
		
	if (type == 'ScholarshipStudentsList') {
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
		document.getElementById('isLiteracyId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('reportForm').style.display = 'inline';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('scholarship').style.display = 'inline';
		document.getElementById('termCount').style.display = 'none';
	}	
}
</script>