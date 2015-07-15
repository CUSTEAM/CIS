<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/Calendar.inc"%>

<script type="text/javascript">
function check() {
	var iCount = document.getElementById("txtUserInput").value;
	if (iCount == '') {
		alert("學號欄位不可為空白,謝謝!!");
		document.getElementById("txtUserInput").focus();
		return false;
	}	
	
	iCount = document.getElementById("txtUserInput4LC").value;
	if (iCount == '') {
		alert("證照代碼欄位不可為空白,謝謝!!");
		document.getElementById("txtUserInput4LC").focus();
		return false;
	}
	
	iCount = document.getElementById("schoolYear").value;
	if (iCount == '') {
		alert("學年欄位不可為空白,謝謝!!");
		document.getElementById("schoolYear").focus();
		return false;
	}
	
	iCount = document.getElementById("amount").value;
	if (iCount == '') {
		alert("補助金額欄位不可為空白,謝謝!!");
		document.getElementById("amount").focus();
		return false;
	}
	
	iCount = document.getElementById("licenseValidDate").value;
	if (iCount == '') {
		alert("證照生效日期欄位不可為空白,謝謝!!");
		document.getElementById("licenseValidDate").focus();
		return false;
	}
	
	// return true;
	document.AJAXForm.action = '/CIS/DeptAssistant/StdSkillUpdate.do';
	document.AJAXForm.submit();
}

function goBack() {
	document.AJAXForm.action = '/CIS/DeptAssistant/StdSkill.do';
	document.AJAXForm.submit();
}

function checkOfficeNo() {
	if (document.getElementById('officeNo').value.length == 7) {
		document.getElementById('accountNo').focus();
	}
}

function checkAccountNo() {
	if (document.getElementById('accountNo').value.length == 7) {
		document.getElementById('money').focus();
	}
}

function TypeAhead(xStrText) {
	if (xStrText.length >= 1) {
		var url = "/CIS/AjaxStmdsSearch?c=true&l=30&idno=" + encodeURIComponent(xStrText);
		if (window.XMLHttpRequest) {
			req = new XMLHttpRequest();
	    } else if (window.ActiveXObject) {
		    req = new ActiveXObject("Microsoft.XMLHTTP");
	    }
	
	    if (req) {
			req.open("GET", url, true);
	        req.onreadystatechange = callback;
	        req.send(null);        
	    }
    }
}

function TypeAhead4LC(xStrText) {
	if (xStrText.length >= 1) {
		var url = "/CIS/AjaxLicenseCodeSearch?c=true&l=50&code=" + encodeURIComponent(xStrText);
		if (window.XMLHttpRequest) {
			req = new XMLHttpRequest();
	    } else if (window.ActiveXObject) {
		    req = new ActiveXObject("Microsoft.XMLHTTP");
	    }
	
	    if (req) {
			req.open("GET", url, true);
	        req.onreadystatechange = callback4LC;
	        req.send(null);        
	    }
    }
}

window.onload = function() {
	var elemSpan = document.createElement("span");
	elemSpan.id = "spanOutput";
	elemSpan.className = "spanTextDropdown";
	
	document.body.appendChild(elemSpan);
	
	document.AJAXForm.txtUserInput.onkeyup = GiveOptions;
	document.AJAXForm.txtUserInput.onfocus = GiveOptions;
	document.AJAXForm.txtUserInput4LC.onkeyup = GiveOptions4LC;
	document.AJAXForm.txtUserInput4LC.onfocus = GiveOptions4LC;
}

window.onresize = function() {
	BuildList(document.AJAXForm.txtUserInput.value);
}
</script>

<style>
<!--
span.spanTextDropdown { 
	position: absolute;
	top: 0px;
	left: 0px;
	width: 150px;
	z-index: 101;
	background-color: #FFFFFF;
	border: 1px solid #ddd;
	padding: 2px 2px 2px 4px;
	overflow: visible;
	display: none;
	font-size: 13px;
	line-height: 1.5em;
	border-bottom: 2px solod #ccc;
}

span.spanMatchText { 
	text-decoration: underline;
	font-weight: bold;
	color: black;
}

span.spanNormalElement { 
	background: #FFFFFF;
	color: #3399ff;
}

span.spanHighElement { 
	background-image: url(images/header_bg-1.jpg);
	color: white;
	cursor: pointer; 
}
-->
</style>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form styleId="AJAXForm" method="post" action="/DeptAssistant/StdSkillUpdate" onsubmit="init('執行中, 請稍後')">
	<script>generateTableBanner('<div class="gray_15"><B>學 生 證 照 管 理 </B></div>');</script>
	<html:hidden property="skillOid" value="${skill4U.oid}"/>
	<tr>
		<td>
			<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
				<tr bgcolor="#f0fcd7">
					<td class="hairlineTdF" align="right">&nbsp;&nbsp;建檔學年&nbsp;&nbsp;</td>
					<td class="hairlineTdF">
						<html:select property="schoolYear" size="1">
							<html:options property="years" labelProperty="years"/>
						</html:select>&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF" align="right">&nbsp;&nbsp;建檔學期&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
						<html:select property="schoolTerm" size="1">
							<html:option value="1">第一學期</html:option>
							<html:option value="2">第二學期</html:option>
						</html:select>&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF" align="right">&nbsp;&nbsp;學號&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
              			<input type="text" name="txtUserInput" size="30" maxlength="10" AUTOCOMPLETE="off" value="${skill4U.studentNo}&nbsp;${skill4U.studentName}" disabled readonly/>
              			<input type="hidden" name="txtUserValue" ID="hidden4ajax" value="oscar"/>
                     	<input type="text" name="txtIgnore" style="display:none" />&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
				</tr>
				<tr bgcolor="#f0fcd7">
					<td class="hairlineTdF" align="right">&nbsp;&nbsp;證照代碼&nbsp;&nbsp;</td>
					<td class="hairlineTdF" colspan="5">
              			<input type="text" name="txtUserInput4LC" size="100" maxlength="30" AUTOCOMPLETE="off" value="${skill4U.license.code}|${skill4U.license.name}|${skill4U.license.level}"/>
              			<input type="hidden" name="txtUserValue4LC" ID="hidden4ajax" value="oscar"/>
                     	<input type="text" name="txtIgnore4LC" style="display:none" />&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
				</tr>	
				<tr bgcolor="#f0fcd7">
					<td class="hairlineTdF" align="right">&nbsp;&nbsp;補助金額&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<html:text property="amount" styleId="amount" size="4" maxlength="4"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF" align="right">&nbsp;&nbsp;補助類別&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<html:select property="amountType" size="1">
                     		<html:option value="1">專業證照報名費</html:option>
                     		<html:option value="2">特種獎學金</html:option>
                     		<html:option value="3">無補助</html:option>
                     	</html:select>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF" align="right">&nbsp;&nbsp;補助核發年月&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
              			<html:text property="amountDate" styleId="amountDate" size="4" maxlength="5"/>
                    </td>
				</tr>
				<tr bgcolor="#f0fcd7">
					<td class="hairlineTdF" align="right">&nbsp;&nbsp;證書編號&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<html:text property="licenseNo" styleId="licenseNo" size="20" maxlength="30"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF" align="right">&nbsp;&nbsp;證照生效日期&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<html:text property="licenseValidDate" size="8" maxlength="8" readonly="true"/>&nbsp;
		 	   			<img src="images/cal.gif" name="calendar" width="20" height="20" 
	      					align="top" style="cursor:hand" alt="點選此處選擇日期"
	  	  					onclick="javascript:if(!licenseValidDate.disabled)popCalFrame.fPopCalendar('licenseValidDate','licenseValidDate',event);">
                    </td>
                    <td class="hairlineTdF" align="right">&nbsp;&nbsp;未領取補助原因&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<select name="reason" size="1">
                     		<option value=""></option>
                     		<option value="1">非學籍生</option>
                     		<option value="2">未聯絡到</option>
                     		<option value="3">已聯絡但未來領取</option>
                     		<option value="9">其他</option>
                     	</select>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
				</tr>
				<tr bgcolor="#f0fcd7">
					<td class="hairlineTdF" align="right">&nbsp;&nbsp;課程科目代碼&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<html:text property="cscode" styleId="cscode" size="5" maxlength="5"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF" align="right">&nbsp;&nbsp;教師身分証字號&nbsp;&nbsp;</td>
              		<td class="hairlineTdF" colspan="3">
                     	<html:text property="techIdno" styleId="techIdno" size="10" maxlength="10"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
				</tr>
				<tr bgcolor="#f0fcd7">
					<td class="hairlineTdF" align="right">&nbsp;&nbsp;序號&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<html:text property="serialNo" styleId="serialNo" size="4" maxlength="6"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF" align="right">&nbsp;&nbsp;自訂分類&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<html:select property="customNo" size="1">
                     		<option value="1">公職考試</option>
                     		<option value="2">語文證照-英文</option>
                     		<option value="3">語文證照-非英文</option>
                     		<option value="4">國際認證</option>
                     		<option value="5">政府機關</option>
                     		<option value="6">其他</option>
                     	</html:select>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF" align="right">&nbsp;&nbsp;報名類型&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<html:select property="applyType" size="1">
                     		<html:option value=""></html:option>
                     		<html:option value="0">單獨</html:option>
                     		<html:option value="1">團體</html:option>
                     	</html:select>
                    </td>
				</tr>
				<tr bgcolor="#f0fcd7">
					<td class="hairlineTdF" align="right">&nbsp;&nbsp;畢業技能檢定審查&nbsp;&nbsp;</td>
              		<td class="hairlineTdF" colspan="5">
                     	<html:radio property="pass" value="1"/>合格&nbsp;<html:radio property="pass" value="0"/>不合格
                    </td>
				</tr>
			</table>
    	</td>
	</tr>
	<script>
   		generateTableBanner('<INPUT type="button" name="method" value="<bean:message key='fee.update.sure' bundle="FEE" />" onclick="return check()" class="CourseButton">' + 
   		'<INPUT type="button" name="method" value="<bean:message key='fee.back' bundle="FEE" />" onclick="goBack()" class="CourseButton">');
   	</script>
	
</html:form>
</table>

<script type="text/javascript">
	document.getElementById('amount').focus();
</script>