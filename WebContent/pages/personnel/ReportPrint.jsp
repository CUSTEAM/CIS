<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
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
	if ("none" == doc.printOpt.value) {
		alert("請選擇報表項目");
		doc.printOpt.focus();
		bFlag = false;
	}
	
	var innerClass = "";
	var failType = "";
	var reportType = "";
	var rankType = "";
	var thisTermScore = "";
	var stayTimeChangeCounts = "";
	
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
		+ "&uc=" + doc.unitCode.value + "&mn=" + doc.birthMonthCode.value
		+ "&dc=" + doc.dayCode.value + "&nc=" + doc.nodeCode.value;
	if (isPreview && bFlag) {	
		// alert(str);
		subwin = window.open("/CIS/Personnel/ReportPrint.do?method=preview" + str, "預覽列印",
			"width=600,height=600,scrollbars=yes,resizable=yes");
	}	
}
</script>

<html:form action="/Personnel/ReportPrint" method="post" onsubmit="init('報表產生中, 請稍後')">
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
    					<span id="stayTimeChangeCounts" style="display:none;">變更次數: 
    						<input type="text" id="stcc" name="stcc" size="1" maxlength="1" value="2"/>(含)以上
    					</span>
    					<span id="thisTermScore" style="display:none;">
    						<input type="checkbox" id="termScore" name="termScore" value="true" checked />將本學期成績以60分(碩士班70分)計
    					</span>
    					<span id="unitDept" style="display:none;">單位
							<html:select property="unitCode" size="1">
								<html:option value="%">所有單位</html:option>
	    						<html:options property="unitCodes" labelProperty="unitNames" />	    						
	    					</html:select>
	    				</span>
	    				<span id="birthMonth" style="display:none;">月份
							<select name="birthMonthCode" size="1">
								<option value="0">一月</option>
	    						<option value="1">二月</option>
	    						<option value="2">三月</option>
	    						<option value="3">四月</option>
	    						<option value="4">五月</option>
	    						<option value="5">六月</option>
	    						<option value="6">七月</option>
	    						<option value="7">八月</option>
	    						<option value="8">九月</option>
	    						<option value="9">十月</option>
	    						<option value="10">十一月</option>
	    						<option value="11">十二月</option>		
	    					</select>
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
							<option value="NoneStayTimeList" <c:if test="${ReportPringForm.map.opt == 'NoneStayTimeList'}">selected</c:if>>專任教師未填寫留校時間清單</option>
							<option value="StayTimeOverCounts" <c:if test="${ReportPringForm.map.opt == 'StayTimeOverCounts'}">selected</c:if>>專任教師變更留校時間次數清單</option>
							<option value="StayTimeList" <c:if test="${ReportPringForm.map.opt == 'StayTimeList'}">selected</c:if>>專任教師留校時間清單</option>
							<option value="StayTimeListReport" <c:if test="${ReportPringForm.map.opt == 'StayTimeListReport'}">selected</c:if>>專任教師留校時間表</option>
							<option value="EmpRetire4BankList" <c:if test="${ReportPringForm.map.opt == 'EmpRetire4BankList'}">selected</c:if>>教職員退休撫恤檔案</option>
							<option value="EmpBirthList" <c:if test="${ReportPringForm.map.opt == 'EmpBirthList'}">selected</c:if>>教職員工慶生名單</option>
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
		document.getElementById('birthMonth').style.display = 'none';
		document.getElementById('stayData').style.display = 'none';
	}
	
	if(type == 'NoneStayTimeList') {
		document.getElementById('stermS').disabled = false;
		document.getElementById('stayTimeChangeCounts').style.display = 'none';
		document.getElementById('birthMonth').style.display = 'none';
		document.getElementById('stayData').style.display = 'none';
	}
	
	if(type == 'AmsWorkdateList') {
		document.getElementById('stermS').disabled = false;
		document.getElementById('stayTimeChangeCounts').style.display = 'none';
		document.getElementById('birthMonth').style.display = 'none';
		document.getElementById('stayData').style.display = 'none';
	}
	
	if(type == 'StayTimeList') {
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
		document.getElementById('thisTermScore').style.display = 'none';
		document.getElementById('unitDept').style.display = 'inline';
		document.getElementById('stermS').disabled = false;
		document.getElementById('stayTimeChangeCounts').style.display = 'none';
		document.getElementById('birthMonth').style.display = 'none';
		document.getElementById('stayData').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">請選擇單位，謝謝！！</font>');
	}
	
	if(type == 'EmpBirthList') {
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
		document.getElementById('thisTermScore').style.display = 'none';
		document.getElementById('unitDept').style.display = 'none';
		document.getElementById('stermS').disabled = true;
		document.getElementById('birthMonth').style.display = 'inline';
		document.getElementById('stayData').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">請選擇月份，謝謝！！</font>');
	}
	
	if(type == 'StayTimeListReport') {
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
		document.getElementById('thisTermScore').style.display = 'none';
		document.getElementById('unitDept').style.display = 'none';
		document.getElementById('stermS').disabled = true;
		document.getElementById('birthMonth').style.display = 'none';
		document.getElementById('stayData').style.display = 'inline';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">請選擇節次與星期，謝謝！！</font>');
	}
	
}
</script>