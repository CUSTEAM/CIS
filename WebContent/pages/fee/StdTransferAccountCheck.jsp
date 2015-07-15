<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/Calendar.inc"%>

<script language="javaScript">
globalTimeOut = globalTimeOut * 2;
function preview(isPreview) {
	var bFlag = true;
	var doc = document.forms[0];
	
	var schoolType = "";
	
	if (document.getElementsByName("schoolType")[0].checked)
		schoolType = document.getElementsByName("schoolType")[0].value;
	else if (document.getElementsByName("schoolType")[1].checked)
		schoolType = document.getElementsByName("schoolType")[1].value;
	else
		schoolType = document.getElementsByName("schoolType")[2].value;	
		
	var str = "&st=" + doc.sterm.value + "&cc=" + doc.campusCode.value + "&sch=" + schoolType 
		+ "&kc=" + doc.kindCode.value;
	if (isPreview && bFlag) {	
		// alert(str);
		subwin = window.open("/CIS/FEE/StdTransferAccountCheck.do?method=preview" + str, "預覽列印",
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

function check4Query() {
	return true;
}


</script>

<html:form action="/FEE/StdTransferAccountCheck" method="post">
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<script>
	generateTableBanner('<table align="left"><tr><td align="left">&nbsp;&nbsp;<img src="images/16-cube-debug.png"></td><td>學生轉帳資料查核</td></tr></table>');
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
    					<span id="thisTermScore" style="display:none;">
    						<input type="checkbox" id="termScore" name="termScore" value="true" checked />將本學期成績以60分(碩士班70分)計
    					</span>
    					<span id="kindCode" style="display:inline;">種類:
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
	                     	</html:select>
    					&nbsp;&nbsp;</span>
    					<span id="extra" style="display:none;">
    						轉帳筆數:&nbsp;&nbsp;<html:text property="counts" styleId="counts" size="6" maxlength="4"/>&nbsp;&nbsp;
    						轉帳總額:&nbsp;&nbsp;<html:text property="totalMoney" styleId="totalMoney" size="8" maxlength="6"/>
    					&nbsp;&nbsp;</span>
    					<span id="dateRange" style="display:none;">轉存日期:
    						<input type="text" name="endDate" size="8" maxlength="8" value="${requestScope.transDate}">&nbsp;
			 	   			<img src="images/cal.gif" name="calendar" width="20" height="20" 
		      					align="top" style="cursor:hand" alt="點選此處選擇日期"
		  	  					onclick="javascript:if(!endDate.disabled)popCalFrame.fPopCalendar('endDate','endDate',event);">	
    					&nbsp;&nbsp;</span>
    					<span id="emailTo" style="display:none;">
    						&nbsp;
    					&nbsp;&nbsp;</span>
    				</td>    				
				</tr>
				<tr>
					<td>
						<span id="campus" style="display:inline;">校區:
    						<html:select property="campusCode" styleId="campusCode">  					
    							<html:option value="1">台北</html:option>
    							<html:option value="2">新竹</html:option>
    						</html:select>&nbsp;&nbsp;&nbsp;&nbsp;部別
    						<input type="radio" id="schoolType" name="schoolType" <c:if test="${ReportPrintFormFee.map.schoolType == 'D'}">checked</c:if> value="D"/>日間部&nbsp;
    						<input type="radio" id="schoolType" name="schoolType" <c:if test="${ReportPrintFormFee.map.schoolType == 'N'}">checked</c:if> value="N"/>進修推廣部&nbsp;
    						<input type="radio" id="schoolType" name="schoolType" <c:if test="${ReportPrintFormFee.map.schoolType == 'H'}">checked</c:if> value="H"/>進專學院
    						<input type="radio" id="schoolType" name="schoolType" <c:if test="${ReportPrintFormFee.map.schoolType == 'A'}">checked</c:if> value="A"/>全部&nbsp;
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
						<html:submit styleId="previewB" property="method" styleClass="CourseButton" onclick="return check4Query()"><bean:message key="fee.class.search" bundle="FEE"/></html:submit>
						<c:if test="${not empty trans}">
							<html:submit styleId="previewB" property="method" styleClass="CourseButton"><bean:message key="fee.trans.create" bundle="FEE"/></html:submit>
							<html:button styleId="previewB" property="method" styleClass="CourseButton" onclick="preview(true);"><bean:message key="Preview" /></html:button>&nbsp;
							<script>
								document.getElementById('emailTo').style.display = 'inline';
								document.getElementById('dateRange').style.display = 'inline';
							</script>
						</c:if>
					</td>					
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${trans}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
				<%@ include file="../include/NoBanner.jsp" %>
				<display:column title="學號" property="studentNo" sortable="false" class="center" />
				<display:column title="姓名" property="studentName" sortable="false" class="center" />
				<display:column title="身分證字號" property="idno" sortable="false" class="center" />
				<display:column title="班級名稱" property="className" sortable="true" class="center" />
				<display:column title="郵局局號" property="officeNo" sortable="true" class="center" />
				<display:column title="轉帳帳號" property="acctNo" sortable="true" class="center" />
				<display:column title="種類" property="kindName" sortable="true" class="center" />
				<display:column title="金額" sortable="false" class="center">
					<c:if test="${row.map.money != null}">
						<fmt:formatNumber value="${row.map.money}"/>
					</c:if>
				</display:column>
				<display:footer>
					<c:if test="${not empty trans}">
					<tr>
						<td>轉帳筆數: <fmt:formatNumber value="${sessionScope.transCount}" /> 筆</td>
						<td>轉帳總額: <fmt:formatNumber value="${sessionScope.transMoney}" /> 元</td>
					</tr>
					</c:if>	
				</display:footer>
			</display:table>
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