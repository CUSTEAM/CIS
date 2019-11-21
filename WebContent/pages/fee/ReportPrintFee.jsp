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
	if ("ClassFeePay2List" == doc.printOpt.value && 
		("All" == doc.campusInCharge2.value || "All" == doc.schoolInCharge2.value)) {
		alert("開課班級選擇範圍過大");
		doc.schoolInCharge2.focus();
		bFlag = false;
	} else if ("none" == doc.printOpt.value) {
		alert("請選擇報表項目");
		doc.printOpt.focus();
		bFlag = false;
	}
	
	var innerClass = "";
	var failType = "";
	var reportType = "";
	var schoolType = "";
	var thisTermScore = "";
	var feeKind = "";
	
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
		
	if (document.getElementsByName("schoolType")[0].checked)
		schoolType = document.getElementsByName("schoolType")[0].value;
	else if (document.getElementsByName("schoolType")[1].checked)
		schoolType = document.getElementsByName("schoolType")[1].value;
	else
		schoolType = document.getElementsByName("schoolType")[2].value;	
		
	if (document.getElementsByName("feeKind")[0].checked)
		feeKind = document.getElementsByName("feeKind")[0].value;
	else
		feeKind = document.getElementsByName("feeKind")[1].value;
	
	if (document.getElementById("termScore").checked) {
		// alert(document.getElementById("termScore").value);
		thisTermScore = document.getElementById("termScore").value
	}
	
	if (doc.printOpt.value == 'StmdFeePayList') {
		if (doc.endDate.value == '') {
			alert("請輸入繳費期限,謝謝");
			bFlag = false;
		}
	} else if (doc.printOpt.value == 'File4Yun') {
		if (document.getElementById("emailToWho").value == '') {
			alert("請輸入檔案收件人Email,謝謝");
			document.getElementById("emailToWho").focus();
			bFlag = false;
		}
	}
	
	var str = "&c=" + doc.campusInCharge2.value + "&s=" + doc.schoolInCharge2.value + "&d=" + doc.deptInCharge2.value +
		"&cl=" + doc.classInCharge2.value + "&p=" + doc.printOpt.value + "&st=" + doc.sterm.value + "&year=" + doc.year.value + 
		"&printInterClass=" + innerClass + "&ft=" + failType + "&rt=" + reportType + "&tt=" + thisTermScore + 
		"&ed=" + doc.endDate.value + "&cc=" + doc.campusCode.value + "&sch=" + schoolType + "&fk=" + feeKind +
		"&eto=" + doc.emailToWho.value + "&kc=" + doc.kindCode.value;
	if (isPreview && bFlag) {	
		// alert(str);
		subwin = window.open("/CIS/FEE/ReportPrintFee.do?method=preview" + str, "預覽列印",
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

<html:form action="/FEE/ReportPrintFee" method="post" onsubmit="init('報表產生中, 請稍後')">
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
    					<span id="kindCode" style="display:none;">種類:
    						<html:select property="kindCode" size="1">
	                     		<html:option value="1">助學貸款</html:option>
	                     		<html:option value="2">學雜費</html:option>
	                     		<html:option value="3">工讀費</html:option>
	                     		<html:option value="4">退費</html:option>
	                     		<html:option value="5">其他</html:option>
	                     		<html:option value="6">獎學金</html:option>
                     			<html:option value="7">網路選課退費</html:option>
                     			<html:option value="8">住宿生保證金退費</html:option>
                     			<html:option value="9">新生獎學金發放</html:option>
                     			<html:option value="10">原住民獎學金</html:option>
                     			<html:option value="11">學產助學金</html:option>
                     			<html:option value="12">生活助學金</html:option>
                     			<html:option value="13">土地銀行獎學金</html:option>
                     			<html:option value="14">教學助理費</html:option>
                     			<html:option value="15">圓夢計畫</html:option>
	                     	</html:select>
    					&nbsp;&nbsp;</span>
    					<span id="fail" style="display:none;">不及格名單類型: 
    						<input type="radio" id="failType" name="failType" checked value="2"/>1/2&nbsp;<input type="radio" id="failType" name="failType" value="3"/>2/3
    					</span>
    					<span id="thisTermScore" style="display:none;">
    						<input type="checkbox" id="termScore" name="termScore" value="true" checked />將本學期成績以60分(碩士班70分)計
    					</span>
    					<span id="dateRange" style="display:none;">繳費期限:
    						<input type="text" name="endDate" size="8" maxlength="8" readonly>&nbsp;
			 	   			<img src="images/cal.gif" name="calendar" width="20" height="20" 
		      					align="top" style="cursor:hand" alt="點選此處選擇日期"
		  	  					onclick="javascript:if(!endDate.disabled)popCalFrame.fPopCalendar('endDate','endDate',event);">	
    					</span>
    					<span id="emailTo" style="display:none;">檔案收件人Email:
    						<input type="text" name="emailToWho" size="20" maxlength="50">&nbsp;
    					</span>
    				</td>    				
				</tr>
				<tr>
					<td>
						<span id="campus" style="display:none;">校區:
    						<html:select property="campusCode" styleId="campusCode">  					
    							<html:option value="1">台北</html:option>
    							<html:option value="2">新竹</html:option>
    						</html:select>&nbsp;&nbsp;&nbsp;&nbsp;部別
    						<input type="radio" id="schoolType" name="schoolType" checked value="D"/>日間部&nbsp;
    						<input type="radio" id="schoolType" name="schoolType" value="N"/>進修推廣部&nbsp;
    						<input type="radio" id="schoolType" name="schoolType" value="H"/>進專學院&nbsp;&nbsp;&nbsp;&nbsp;種類
    						<input type="radio" id="feeKind" name="feeKind" checked value="1"/>學雜費&nbsp;
    						<input type="radio" id="feeKind" name="feeKind" value="2"/>代辦費&nbsp;
    					&nbsp;&nbsp;</span>
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
					</span></td>
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
							<option value="StdTransferAccountList" <c:if test="${ReportPringForm.map.opt == 'StdTransferAccountList'}">selected</c:if>>學生轉帳帳號資料列印</option>
							<option value="ClassFeePay2List" <c:if test="${ReportPringForm.map.opt == 'ClassFeePay2List'}">selected</c:if>>班級代辦費資料列印</option>
							<option value="RegisterUpdate" <c:if test="${ReportPringForm.map.opt == 'RegisterUpdate'}">selected</c:if>>下載匯入更新註冊檔表格</option>
						</html:select>
						<input type="text" name="checkCredit" id="checkCredit" <c:if test="${ReportPringForm.map.checkOpt!='CheckCredit'}">style="display:none;"</c:if> size="1" value="${ReportPringForm.map.checkCredit}"/><img id="point" style="display:none;" src="images/16-exc-mark.gif" />
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
document.getElementById('stermS').disabled = true;
function showHelp(type) {
	document.getElementById('helpMsg').innerHTML = '';
	document.getElementById('year').style.display = 'none';
	document.getElementById('interClassId').style.display = 'none';
	document.getElementById('report').style.display = 'none';
	document.getElementById('fail').style.display = 'none';
	document.getElementById('stermS').disabled = false; 
	if(type == 'StdTransferAccountList') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'none';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('year').style.display = 'none';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('thisTermScore').style.display = 'none';
		document.getElementById('stermS').disabled = false;
		document.getElementById('dateRange').style.display = 'none';
		document.getElementById('campus').style.display = 'none';
		document.getElementById('emailTo').style.display = 'none';
		document.getElementById('kindCode').style.display = 'inline';
		document.getElementById('classInfo').style.display = 'none';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">列印資料以個人所鍵入系統之當學期資料為主，謝謝！！</font>');
	}
	
	if(type == 'ClassFeePay2List') {
		document.getElementById('checkCredit').style.display = 'none';
		document.getElementById('alert').style.display = 'inline';
		document.getElementById('point').style.display = 'none';
		document.getElementById('previewB').style.display = 'inline';
		document.getElementById('searchB').style.display = 'none';
		document.getElementById('year').style.display = 'none';
		document.getElementById('interClassId').style.display = 'none';
		document.getElementById('report').style.display = 'none';
		document.getElementById('fail').style.display = 'none';
		document.getElementById('thisTermScore').style.display = 'none';
		document.getElementById('stermS').disabled = false;
		document.getElementById('dateRange').style.display = 'none';
		document.getElementById('campus').style.display = 'none';
		document.getElementById('emailTo').style.display = 'none';
		document.getElementById('kindCode').style.display = 'none';
		document.getElementById('classInfo').style.display = 'inline';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red"></font>');
	}
}
</script>