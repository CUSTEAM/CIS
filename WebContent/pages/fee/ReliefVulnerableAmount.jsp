<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>

<script language="javaScript">
history.go(1);
function check() {
	var iCount = document.getElementById("txtUserInput").value;
	if (iCount == '') {
		alert("身分證字號不可為空白,謝謝!!");
		document.getElementById("txtUserInput").focus();
		return false;
	}
	
	document.AJAXForm.submit();
}

function TypeAhead(xStrText) {
	if (xStrText.length >= 1) {
		var url = "/CIS/AjaxRegisterSearch?c=false&l=30&idno=" + encodeURIComponent(xStrText);
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

<form name="AJAXForm" id="AJAXForm" method="post" action="/CIS/FEE/ReliefVulnerableAmount.do?method=preview" onsubmit="init('執行中, 請稍後')">
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<script>
	generateTableBanner('<table align="left"><tr><td align="left">&nbsp;&nbsp;<img src="images/16-cube-debug.png"></td><td>更 新 學 生 註 冊 檔( 減 免 與 弱 勢) 維 護</td></tr></table>');
</script>
	<tr>
		<td>
			<table class="empty-border">
				<tr>
					<td>
						<select name="year" size="1">
							<option value="99">99</option>
							<option value="100">100</option>
							<option value="101">101</option>
							<option value="102">102</option>
							<option value="103">103</option>
							<option value="104">104</option>
							<option value="105">105</option>
							<option value="106">106</option>
						</select>&nbsp;&nbsp;學年度&nbsp;&nbsp;
						<select name="sterm" size="1">
							<option value="1">第一學期</option>
							<option value="2">第二學期</option>
						</select>&nbsp;&nbsp;
						身分證字號&nbsp;&nbsp;
              			<input type="text" name="txtUserInput" size="16" maxlength="10" AUTOCOMPLETE="off"/>
              			<input type="hidden" name="txtUserValue" ID="hidden4ajax" value="oscar"/>
                     	<input type="text" name="txtIgnore" style="display:none" />&nbsp;&nbsp;&nbsp;&nbsp;
						<!--
						<bean:message key="OpenCourse.label.classNumber" bundle="COU"/>:
				   		<c:set var="campusSel" value="${StdTransferAccountForm.map.campusInCharge2}"/>
	  			   		<c:set var="schoolSel" value="${StdTransferAccountForm.map.schoolInCharge2}"/>
	  			   		<c:set var="deptSel" value="${StdTransferAccountForm.map.deptInCharge2}"/>
	  			   		<c:set var="classSel" value="${StdTransferAccountForm.map.classInCharge2}"/>
	  			   		<c:set var="classLess" value="${StdTransferAccountForm.map.classLess}"/>
	  			   		-->
	  			   		<!--
	  			   		-->
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
						<html:button styleId="previewB" property="method" styleClass="CourseButton" onclick="return check()"><bean:message key="fee.class.search" bundle="FEE"/></html:button>
					</td>					
				</tr>
			</table>
		</td>
	</tr>	
	
	<c:if test="${not empty registerList}">
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${registerList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
				<%@ include file="../include/NoBanner.jsp" %>
				<display:column title="學年" property="schoolYear" sortable="true" class="center" />
				<display:column title="學期" property="schoolTerm" sortable="true" class="center" />
				<display:column title="姓名" property="studentName" sortable="true" class="center" />
				<display:column title="身分証字號" property="idno" sortable="true" class="center" />
				<display:column title="減免學雜費" property="reliefTuitionAmount" sortable="true" class="center" />
				<display:column title="弱勢助學金額" property="vulnerableAmount" sortable="true" class="center" />
			</display:table>
    	</td>
	</tr>
	
	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='makeSure' />" onclick="return makeSure()" class="CourseButton">');
   	</script>
   	</c:if>
	
</form>	
</table>
