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
	var schoolType = "";
	var payType = "";
	var newReg = "";
	
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
		
	if (document.getElementsByName("payTypeId")[0].checked)
		payType = document.getElementsByName("payTypeId")[0].value;
	else
		payType = document.getElementsByName("payTypeId")[1].value;	
		
	if (document.getElementsByName("schoolType")[0].checked)
		schoolType = document.getElementsByName("schoolType")[0].value;
	else if (document.getElementsByName("schoolType")[1].checked)
		schoolType = document.getElementsByName("schoolType")[1].value;
	else
		schoolType = document.getElementsByName("schoolType")[2].value;		
		
	if (document.getElementById("termScore").checked) {
		// alert(document.getElementById("termScore").value);
		thisTermScore = document.getElementById("termScore").value
	}
	if (document.getElementsByName("newReg")[0].checked)
		newReg = document.getElementsByName("newReg")[0].value;
	else if (document.getElementsByName("newReg")[1].checked)
		newReg = document.getElementsByName("newReg")[1].value;
				
	var str = "&c=" + doc.campusInCharge2.value + "&s=" + doc.schoolInCharge2.value + "&d=" + doc.deptInCharge2.value +
		"&cl=" + doc.classInCharge2.value + "&p=" + doc.printOpt.value + "&st=" + doc.sterm.value + "&year=" + doc.year.value + 
		"&printInterClass=" + innerClass + "&ft=" + failType + "&rt=" + reportType + "&nr=" + newReg + "&rank=" + rankType + "&tt=" + thisTermScore +
		"&dcp=" + doc.deptCodeOpt.value + "&sd=" + doc.startDate.value + "&ed=" + doc.endDate.value +
		"&dc=" + doc.dayCode.value + "&nc=" + doc.nodeCode.value + "&cc=" + doc.campusCode.value + "&stc=" + schoolType +
		"&rl=" + doc.reportListOpt.value + "&pt=" + payType;
	if (isPreview && bFlag) {	
		// alert(str);
		subwin = window.open("/CIS/Registration/ReportPrintRegister.do?method=preview" + str, "預覽列印",
			"width=600,height=600,scrollbars=yes,resizable=yes");
	}	
}

</script>

<html:form action="/Registration/ReportPrintRegister" method="post" onsubmit="init('報表產生中, 請稍後')">
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
    						<input type="radio" id="interClass" name="interClass" value="yes"/>是&nbsp;<input type="radio" id="interClass" name="interClass" checked="checked" value="no"/>否
    					&nbsp;&nbsp;</span>
    					<span id="report" style="display:none;">請選擇報表類型: 
    						<input type="radio" id="reportType" name="reportType" value="yes"/>已繳費&nbsp;<input type="radio" id="reportType" name="reportType" checked="checked" value="no"/>未繳費
    					&nbsp;&nbsp;</span>
    					<span id="fail" style="display:none;">不及格名單類型: 
    						<input type="radio" id="failType" name="failType" checked="checked" value="2"/>1/2&nbsp;<input type="radio" id="failType" name="failType" value="3"/>2/3
    					</span>
    					<span id="rank" style="display:none;">排名類型: 
    						<input type="radio" id="rankType" name="rankType" checked="checked" value="c"/>班排名&nbsp;<input type="radio" id="rankType" name="rankType" value="d"/>系排名
    					</span>
    					<span id="thisTermScore" style="display:none;">
    						<input type="checkbox" id="termScore" name="termScore" value="true" checked="checked" />將本學期成績以60分(碩士班70分)計
    					</span>
    					<span id="campus" style="display:none;">校區:
    						<html:select property="campusCode" size="1">  					
    							<html:option value="1">台北</html:option>
    							<html:option value="2">新竹</html:option>
    							<html:option value="A">全部</html:option>
    						</html:select>
    					&nbsp;&nbsp;</span>
    					<span id="schoolTypeId" style="display:none;">部別:					
    						<input type="radio" id="schoolType" name="scoreType" checked="checked" value="D"/>日間部&nbsp;<input type="radio" id="schoolType" name="scoreType" value="N"/>進修部&nbsp;<input type="radio" id="schoolType" name="scoreType" value="H"/>進專學院
    					&nbsp;&nbsp;</span>
    					<!-- 
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
    					&nbsp;&nbsp;</span>
    					 -->
    					 
    					<span id="deptCodes" style="display:none;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;或全校系所：
        				<Select name="deptCodeOpt">
        				<option value=""></option>
        				<c:forEach items="${depts}" var="dept">
        				<option value="${dept.idno}">${dept.name}</option>
        				</c:forEach>
        				</Select> 
    					&nbsp;&nbsp;</span>
    					
    					<span id="reportList" style="display:none;">繳費類型:
    						<select name="reportListOpt" size="1">
    							<option value="1">學雜費</option>
	    						<option value="2">代辦費</option>
	    						<option value="3">減免學雜費</option>
	    						<option value="4">就學貸款費</option>
	    						<option value="5">弱勢助學費</option>
    						</select>
    					&nbsp;&nbsp;</span>
    					<span id="payType" style="display:none;">繳費類型:
    						<input type="radio" id="payTypeId" name="payTypeId" checked="checked" value="yes"/>已繳費&nbsp;<input type="radio" id="payTypeId" name="payTypeId" value="no"/>未繳費
    					&nbsp;&nbsp;</span>
    					<span id="licenseDateRange" style="display:none;">&nbsp;&nbsp;證照生效日期
	    					<input type="text" name="startDate" size="10" maxlength="8" readonly="readonly">
    						<img src="images/cal.gif" name="calendar" width="20" height="20"
		      					align="top" style="cursor:hand" alt="點選此處選擇日期"
		  	  					onclick="javascript:if(!startDate.disabled)popCalFrame.fPopCalendar('startDate','startDate',event);">&nbsp;~&nbsp;
		  	  				<input type="text" name="endDate" size="10" maxlength="8" readonly="readonly">
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
	    				<span id="schoolTypeCode" style="display:none;">部別:
    						<html:select property="schoolType" size="1">			
    							<html:option value="D">日間部</html:option>
    							<html:option value="N">進修部</html:option>
    							<html:option value="H">進專學院</html:option>
    						</html:select>
    					</span>
    					
	    				<span id="newStmdReg" style="display:none;">新生註冊:
    						<input type="radio" name="newReg" value="0" />不含			
    						<input type="radio" name="newReg" value="1" checked="checked" />包含&nbsp;&nbsp;<font color="red">只填學籍卡新生</font>
    					</span>

    					<span id="classSelect" style="display:none;">
    						<br/>
							<bean:message key="OpenCourse.label.classNumber" bundle="COU"/>:
				   			<c:set var="campusSel" value="${ReportPringForm.map.campusInCharge2}"/>
	  			   			<c:set var="schoolSel" value="${ReportPringForm.map.schoolInCharge2}"/>
	  			   			<c:set var="deptSel" value="${ReportPringForm.map.deptInCharge2}"/>
	  			   			<c:set var="classSel" value="${ReportPringForm.map.classInCharge2}"/>
	  			   			<c:set var="classLess" value="${ReportPringForm.map.classLess}"/>
	  			   			<%@include file="/pages/include/ClassSelect5.jsp"%>
	  			   		</span>

    				</td>    				
				</tr>
	  			<tr><td>
	  			   		<table width="100%" id="alert" style="display:none">
							<tr>
								<td><br>
									<div class="modulecontainer filled nomessages">
										<div class="first">
											<span class="first"></span>
											<span class="last"></span>
										</div>
									</div>
									<div class="modulecontainer filled nomessages">
										<div>
											<table width="100%">
												<tr>
													<td>&nbsp;&nbsp;<img src="images/24-book-green-message.png"></td>
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
									<div class="modulecontainer filled nomessages">
										<div class="last">
											<span class="first"></span>
											<span class="last"></span>
										</div>
									</div>
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
							<option value="UploadNewStmdTemplate" <c:if test="${ReportPrintRegisterForm.map.opt == 'UploadNewStmdTemplate'}">selected</c:if>>下載新生基本資料樣本檔(未編班編學號)</option>
							<option value="UploadNewStmdTemplate1" <c:if test="${ReportPrintRegisterForm.map.opt == 'UploadNewStmdTemplate1'}">selected</c:if>>下載新生基本資料樣本檔(已編班編學號)</option>
							<option value="RegisterManagerList" <c:if test="${ReportPringForm.map.opt == 'RegisterManagerList'}">selected</c:if>>註冊檔資料清單</option>
							<option value="RegisterReportList1" <c:if test="${ReportPrintRegisterForm.map.opt == 'RegisterReportList1'}">selected</c:if>>未啟動註冊資料明細表</option>
							<option value="RegisterReportList2" <c:if test="${ReportPrintRegisterForm.map.opt == 'RegisterReportList2'}">selected</c:if>>未繳/已繳費名單明細表</option>
							<option value="RegisterReportListSum" <c:if test="${ReportPrintRegisterForm.map.opt == 'RegisterReportListSum'}">selected</c:if>>未啟動註冊資料統計表</option>
							<option value="RegisterReportNew" <c:if test="${ReportPrintRegisterForm.map.opt == 'RegisterReportNew'}">selected</c:if>>新生可編班編學號明細表</option>
						</html:select>
						<input type="text" name="checkCredit" id="checkCredit" <c:if test="${ReportPringForm.map.checkOpt!='CheckCredit'}">style="display:none;"</c:if> size="1" value="${ReportPringForm.map.checkCredit}"/><img id="point" style="display:none;" src="images/16-exc-mark.gif" />
					</td>
					<td>
						<html:button styleId="previewB" property="method" styleClass="CourseButton" onclick="preview(true);"><bean:message key="Preview" /></html:button>&nbsp;
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
	document.getElementById('classSelect').style.display = 'none';
	document.getElementById('newStmdReg').style.display = 'none';
	
	if(type == 'RegisterReportList1' || type == 'RegisterReportNew') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'none';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('year').style.display = 'none';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('rank').style.display = 'none';
		document.getElementById('thisTermScore').style.display = 'none';
		document.getElementById('stermS').disabled = false;
		document.getElementById('deptCodes').style.display = 'inline';
		document.getElementById('licenseDateRange').style.display = 'none';
		document.getElementById('stayData').style.display = 'none';
		document.getElementById('campus').style.display = 'inline';
		document.getElementById('schoolTypeId').style.display = 'inline';
		document.getElementById('reportList').style.display = 'none';
		document.getElementById('payType').style.display = 'none';
		// document.getElementById('schoolTypeCode').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">列印範圍以校區與學制為單位，若校區選全部則不考慮學制，或請選擇學系但忽略其他選項。謝謝！！</font>');
	}
	
	if(type == 'RegisterReportList2') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'none';
		document.getElementById('previewB').style.display = 'inline';
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
		document.getElementById('campus').style.display = 'inline';
		document.getElementById('schoolTypeId').style.display = 'inline';
		document.getElementById('reportList').style.display = 'inline';
		document.getElementById('payType').style.display = 'inline';
		// document.getElementById('schoolTypeCode').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">列印範圍以校區與學制為單位，若校區選全部則不考慮學制，謝謝！！</font>');
	}
	
	if(type == 'RegisterReportListSum') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'none';
		document.getElementById('previewB').style.display = 'inline';
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
		document.getElementById('schoolTypeId').style.display = 'none';
		document.getElementById('reportList').style.display = 'none';
		document.getElementById('payType').style.display = 'none';
		document.getElementById('newStmdReg').style.display = 'inline';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">新生註冊資料若選擇包含上網填寫學籍資料者，即使該生未繳費、未辦理助學貸款、減免及弱勢補助等，亦計入為已啟動註冊！！</font>');
	}
	
	if (type == 'RegisterManagerList') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'none';
		document.getElementById('previewB').style.display = 'inline';
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
		document.getElementById('campus').style.display = 'inline';
		document.getElementById('schoolTypeId').style.display = 'inline';
		document.getElementById('reportList').style.display = 'none';
		document.getElementById('payType').style.display = 'none';
		document.getElementById('campus').style.display = 'inline';
		// document.getElementById('schoolTypeCode').style.display = 'inline';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">只需下拉選擇要統計之畢業學年學期即可，若校區選全部則不考慮學制，謝謝！！</font>');
	}
}
</script>