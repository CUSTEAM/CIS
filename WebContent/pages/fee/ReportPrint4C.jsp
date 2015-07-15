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
	} else if (doc.printOpt.value == 'StdTransferAccountFile') {
		 if (doc.endDate.value == '') {
			alert("請輸入轉存日期,謝謝");
			bFlag = false;
		} else if (document.getElementById("emailToWho").value == '') {
			alert("請輸入檔案收件人Email,謝謝");
			document.getElementById("emailToWho").focus();
			bFlag = false;
		}
	}
	
	var str = "&p=" + doc.printOpt.value + "&st=" + doc.sterm.value + "&year=" + doc.year.value + 
		"&printInterClass=" + innerClass + "&ft=" + failType + "&rt=" + reportType + "&tt=" + thisTermScore + 
		"&ed=" + doc.endDate.value + "&cc=" + doc.campusCode.value + "&sch=" + schoolType + "&fk=" + feeKind +
		"&eto=" + doc.emailToWho.value;
	if (isPreview && bFlag) {	
		// alert(str);
		subwin = window.open("/CIS/FEE/ReportPrint4C.do?method=preview" + str, "預覽列印",
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

<html:form action="/FEE/ReportPrint4C" method="post" onsubmit="init('報表產生中, 請稍後')">
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
    					<span id="fail" style="display:none;">不及格名單類型: 
    						<input type="radio" id="failType" name="failType" checked value="2"/>1/2&nbsp;<input type="radio" id="failType" name="failType" value="3"/>2/3
    					</span>
    					<span id="thisTermScore" style="display:none;">
    						<input type="checkbox" id="termScore" name="termScore" value="true" checked />將本學期成績以60分(碩士班70分)計
    					</span>
    					<span id="dateRange" style="display:none;">轉存日期:
    						<input type="text" name="endDate" size="8" maxlength="8" readonly>&nbsp;
			 	   			<img src="images/cal.gif" name="calendar" width="20" height="20" 
		      					align="top" style="cursor:hand" alt="點選此處選擇日期"
		  	  					onclick="javascript:if(!endDate.disabled)popCalFrame.fPopCalendar('endDate','endDate',event);">	
    					&nbsp;&nbsp;</span>
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
							<option value="StdTransferAccountFile" <c:if test="${ReportPringForm.map.opt == 'StdTransferAccountFile'}">selected</c:if>>轉帳資料製作</option>
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
	if(type == 'StdTransferAccountFile') {
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
		document.getElementById('stermS').disabled = true;
		document.getElementById('dateRange').style.display = 'inline';
		document.getElementById('campus').style.display = 'inline';
		document.getElementById('emailTo').style.display = 'inline';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">轉帳資料製作會將檔案以Email方式寄出，謝謝！！</font>');
	}
	
	if(type == 'File4Yun') {
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
		document.getElementById('emailTo').style.display = 'inline';
		document.getElementById('helpMsg').innerHTML = ('&nbsp;<font color="red">系統執行完畢會寄送3個檔案，因此檔案收件人Email務必填寫，謝謝！！</font>');
	}
}
</script>