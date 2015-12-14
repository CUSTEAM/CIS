<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
function check() {
	var iCount = document.getElementById("txtUserInput").value;
	if (iCount == '') {
		alert("學號不可為空白,謝謝!!");
		document.getElementById("studentNo").focus();
		return false;
	}	
	
	iCount = document.getElementById("officeNo").value;
	if (iCount == '') {
		alert("郵局局號不可為空白,謝謝!!");
		document.getElementById("officeNo").focus();
		return false;
	}
	
	iCount = document.getElementById("accountNo").value;
	if (iCount == '') {
		alert("轉帳帳號不可為空白,謝謝!!");
		document.getElementById("accountNo").focus();
		return false;
	}
	
	iCount = document.getElementById("money").value;
	if (iCount == '') {
		alert("轉帳金額不可為空白,謝謝!!");
		document.getElementById("money").focus();
		return false;
	}
	
	iCount = document.getElementById("type").value;
	if (iCount == '') {
		alert("種類不可為空白,謝謝!!");
		document.getElementById("type").focus();
		return false;
	}
	
	// return true;
	document.AJAXForm.submit();
}

function goBack() {
	document.AJAXForm.action = '/CIS/FEE/StdTransferAccount.do';
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
		var url = "/CIS/AjaxStmdsSearch?c=false&l=30&idno=" + encodeURIComponent(xStrText);
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

window.onload = function() {
	var elemSpan = document.createElement("span");
	elemSpan.id = "spanOutput";
	elemSpan.className = "spanTextDropdown";
	
	document.body.appendChild(elemSpan);
	document.AJAXForm.txtUserInput.onkeyup = GiveOptions;
	document.AJAXForm.txtUserInput.onfocus = GiveOptions;
	document.AJAXForm.officeNo.onkeyup = checkOfficeNo;
	document.AJAXForm.accountNo.onkeyup = checkAccountNo;
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
<form name="AJAXForm" id="AJAXForm" method="post" action="/CIS/FEE/StdTransferAccountAdd.do" onsubmit="init('執行中, 請稍後')">
	<script>generateTableBanner('<div class="gray_15"><B>學 生 轉 帳 帳 號 資 料 維 護</B></div>');</script>
	<tr>
		<td>
			<table bgcolor="cfe69f" cellpadding="5" cellspacing="1" bgcolor="#CFE69F">
				<tr bgcolor="#f0fcd7">
					<td class="hairlineTdF">&nbsp;&nbsp;學號&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
              			<input type="text" name="txtUserInput" size="16" maxlength="10" AUTOCOMPLETE="off"/>
              			<input type="hidden" name="txtUserValue" ID="hidden4ajax" value="oscar"/>
                     	<input type="text" name="txtIgnore" style="display:none" />&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF">&nbsp;&nbsp;郵局局號&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<input type="text" name="officeNo" id="officeNo" size="12" maxlength="7"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF">&nbsp;&nbsp;轉帳帳號&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<input type="text" name="accountNo" id="accountNo" size="12" maxlength="7"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
				</tr>
				<tr bgcolor="#f0fcd7">
					<td class="hairlineTdF">&nbsp;&nbsp;轉帳金額&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF">
                     	<input type="text" name="money" id="money" size="8" maxlength="7"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                    <td class="hairlineTdF">&nbsp;&nbsp;種類&nbsp;&nbsp;&nbsp;&nbsp;</td>
              		<td class="hairlineTdF" colspan="3">
                     	<select name="type" size="1">
                     		<option value=""></option>
                     		<option value="1">助學貸款</option>
                     		<option value="2">學雜費</option>
                     		<option value="3">工讀費</option>
                     		<option value="4">退費</option>
                     		<option value="5">其他</option>
                     		<option value="6">獎學金</option>
                     		<option value="7">網路選課退費</option>
                     		<option value="8">住宿生保證金退費</option>
                     		<option value="9">新生獎學金發放</option>
                     		<option value="10">原住民獎學金</option>
                     		<option value="11">學產助學金</option>
                     		<option value="12">生活助學金</option>
                     		<option value="13">土地銀行獎學金</option>
                     	</select>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
				</tr>
			</table>
    	</td>
	</tr>
	<script>
   		generateTableBanner('<INPUT type="button" name="method" value="<bean:message key='fee.add.sure' bundle="FEE" />" onclick="return check()" class="CourseButton">' + 
   		'<INPUT type="button" name="method" value="<bean:message key='fee.back' bundle="FEE" />" onclick="goBack()" class="CourseButton">');
   	</script>
	
</form>
</table>

<script type="text/javascript">
	document.getElementById('txtUserInput').focus();
</script>