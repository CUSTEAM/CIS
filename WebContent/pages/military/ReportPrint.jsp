<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<script language="javaScript">
history.go(1);
globalTimeOut = globalTimeOut * 2;
function preview(isPreview) {
	var bFlag = true;
	var doc = document.forms[0];
	if ("ClassStudentsRegistrationCard" == doc.printOpt.value 
		&& ("All" == doc.campusInCharge2.value || "" == doc.campusInCharge2.value
		|| "All" == doc.schoolInCharge2.value || "" == doc.schoolInCharge2.value
		|| "All" == doc.deptInCharge2.value || "" == doc.deptInCharge2.value
		|| "All" == doc.classInCharge2.value || "" == doc.classInCharge2.value)) {
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
		"&printInterClass=" + innerClass + "&ft=" + failType + "&rt=" + reportType + "&rank=" + rankType + "&tt=" + thisTermScore;
	if (isPreview && bFlag) {	
		// alert(str);
		subwin = window.open("/CIS/Teacher/Military/ReportPrint.do?method=preview" + str, "預覽列印",
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

<html:form action="/Teacher/Military/ReportPrint" method="post" onsubmit="init('報表產生中, 請稍後')">
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
    				</td>    				
				</tr>
				<tr>
					<td><bean:message key="OpenCourse.label.classNumber" bundle="COU"/>:
				   		<c:set var="campusSel" value="${ReportPringForm.map.campusInCharge2}"/>
	  			   		<c:set var="schoolSel" value="${ReportPringForm.map.schoolInCharge2}"/>
	  			   		<c:set var="deptSel" value="${ReportPringForm.map.deptInCharge2}"/>
	  			   		<c:set var="classSel" value="${ReportPringForm.map.classInCharge2}"/>
	  			   		<c:set var="classLess" value="${ReportPringForm.map.classLess}"/>
	  			   		<%@include file="/pages/include/AllClassSelect2.jsp"%>
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
							<option value="ClassStudentsRegistrationCard" <c:if test="${ReportPringForm.map.opt == 'ClassStudentsRegistrationCard'}">selected</c:if>>新生全班學籍卡</option>
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
	document.getElementById('stermS').disabled = false;

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
	
}
</script>