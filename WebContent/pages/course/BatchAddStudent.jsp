<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/ajax.js" %>

<script type="text/javascript"> 

var oIndex = 0;
function autoStudentCheck(obj, index) {
	var arg = obj.value;
	oIndex = index;
	var path = "<%= request.getContextPath() %>";
	var url = path + "/AjaxLookupTool.do?method=lookupStudentByNo&key="
		+ encodeURIComponent(arg);
	httpRequest("GET", url, true, responseStdHandler);
}

function responseStdHandler() {	
	if(request.readyState == READ_STATE_COMPLETE) {
		if(request.status == RESPONSE_STATUS_OK) {
			var responseValue = request.responseText;
			if(responseValue.indexOf("查無學生資料") == -1) {
				var func = new Function("return " + responseValue);
				var obj = func();
				document.getElementById("stdName" + oIndex).value = obj.name;
				document.getElementById("stdClass" + oIndex).value = obj.className;
				//document.getElementById("email" + oIndex).value = obj.email;
				//document.getElementById("cellPhone" + oIndex).value = obj.cellPhone;
				//document.getElementById("homePhone" + oIndex).value = obj.homePhone;
				//document.getElementById("oid" + oIndex).value = obj.oid;
			} else {
				//document.getElementById("email" + oIndex).value = '<bean:message key="Message.ScoreHist.CantFindStudent" />';
				document.getElementById("stdName" + oIndex).value = "";
				document.getElementById("stdClass" + oIndex).value = "";
				//document.getElementById("stdNo" + oIndex).value = "";
				//document.getElementById("stdNo" + oIndex).focus();
			}
		}
	}
}

function autoCourseCheck(obj) {
	var arg = obj.value;
	var path = "<%= request.getContextPath() %>";
	var url = path + "/AjaxLookupTool.do?method=lookupCourseInfo&csCode="
		+ encodeURIComponent(arg);
	httpRequest("GET", url, true, responseCourseHandler);
}

function responseCourseHandler() {
	if(request.readyState == READ_STATE_COMPLETE) {
		if(request.status == RESPONSE_STATUS_OK) {
			var responseValue = request.responseText;
			var span = document.getElementById("courseInfo");
			if(responseValue.indexOf("查無此科目資料") == -1) {
				var func = new Function("return " + responseValue);
				var obj = func();				
				document.getElementById("courseName").value = obj.chName;
				span.innerHTML = "";
				var classNo = document.getElementById("classNo").value;
				var csNo = document.getElementById("courseNumber").value;
				var sterm = document.getElementById("sterm").value;
				var path = "<%= request.getContextPath() %>";
				var url = path + "/AjaxLookupTool.do?method=lookupDtimeInfo&csCode="
					+ encodeURIComponent(csNo)
					+ "&classNo=" + encodeURIComponent(classNo)
					+ "&sterm=" + encodeURIComponent(sterm);
				httpRequest("GET", url, true, responseDtimeHandler);
			} else {
				span.style.color = "red";
				span.style.fontSize = "1em";
				document.getElementById("courseName").value = "";
				//span.innerHTML = request.responseText;
				document.getElementById("credit").value = "";
				document.getElementById("hour").value = "";
				document.getElementById("stuSelect").value = "";
				document.getElementById("selectLimit").value = "";
				document.getElementById("opt").value = "";
				document.getElementById("countInfo").innerHTML = "";
				document.getElementById("courseNumber").focus();
			}
		}
	}
}

function checkTerm(term) {
	if(document.getElementById("courseNumber").value != "") {
		autoCourseCheck(document.getElementById("courseNumber"));
	} 
}

function autoClassCheck(obj) {
	var arg = obj.value;
	var path = "<%= request.getContextPath() %>";
	var url = path + "/AjaxLookupTool.do?method=lookupClassInfo&classNo="
		+ encodeURIComponent(arg);
	httpRequest("GET", url, true, responseClassHandler);
}

function responseClassHandler() {
	if(request.readyState == READ_STATE_COMPLETE) {
		if(request.status == RESPONSE_STATUS_OK) {
			var responseValue = request.responseText;
			//alert(responseValue);
			if(responseValue.indexOf("查無此班級資料") == -1) {
				document.getElementById("className").value = responseValue;
				document.getElementById("classInfo").innerHTML = "";
				document.getElementById("courseNumber").focus();
			} else {
				document.getElementById("className").value = "";
				var span = document.getElementById("classInfo");
				span.style.color = "red";
				span.style.fontSize = "1em";
				//span.innerHTML = request.responseText;
				document.getElementById("className").value = "";
				document.getElementById("classNo").focus();
				document.getElementById("s1").setAttribute("disabled", "true");
			}
		}
	}
}

function responseDtimeHandler() {
	if(request.readyState == READ_STATE_COMPLETE) {
		if(request.status == RESPONSE_STATUS_OK) {
			var responseValue = request.responseText;
			//alert(responseValue);
			if(responseValue.indexOf("查無此開課資料") == -1
				&& responseValue.indexOf("無此開課紀錄") == -1) {
				var func = new Function("return " + responseValue);
				var obj = func();
				document.getElementById("countInfo").innerHTML = "";
				document.getElementById("oid").value = obj.oid;
				document.getElementById("credit").value = obj.credit;
				document.getElementById("hour").value = obj.hour;
				var selCount = obj.select;
				var limitCount = obj.limit;
				document.getElementById("stuSelect").value = selCount;
				document.getElementById("selectLimit").value = limitCount;
				document.getElementById("opt").value = obj.opt;
				document.getElementById("optId").value = obj.opt;
				document.getElementById("creditId").value = obj.credit;
				document.forms[0].stdNo0.focus();
				document.getElementById("s1").setAttribute("disabled", "");
				if(parseInt(selCount) >= parseInt(limitCount)) {
					document.getElementById("s1").setAttribute("disabled", "true");
					var span = document.getElementById("countInfo");
					span.style.color = "red";
					span.style.fontSize = "1em";
					span.innerHTML = "選課人數不可超過上限人數!!";
				}
			} else {
				document.getElementById("countInfo").innerHTML = "";
				document.getElementById("credit").value = "";
				document.getElementById("hour").value = "";
				document.getElementById("stuSelect").value = "";
				document.getElementById("selectLimit").value = "";
				document.getElementById("opt").value = "";
				//document.getElementById("optId").value = "";
				document.getElementById("s1").setAttribute("disabled", "true");
				var span = document.getElementById("courseInfo");
				span.style.color = "red";
				span.style.fontSize = "1em";
				span.innerHTML = responseValue;
			}
		}
	}
}

function checkStuSelect() {		
	if(document.getElementById("classNo").value == "") {
		alert("班級代碼不可為空白!!");
		document.getElementById("classNo").focus();
		return false;
	}
	if(document.getElementById("courseNumber").value == "") {
		alert("科目代碼不可為空白!!");
		document.getElementById("courseNumber").focus();
		return false;
	}
	
	if(parseInt(document.getElementById("stuSelect").value) >= 
		parseInt(document.getElementById("selectLimit").value)) {
		alert("選課人數超過上限,請修改選課人數後再行加選!!");
		return false;
	}
	return true;
}

</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Course/BatchInputStdCourse" method="post" focus="classNo">
	<input type="hidden" name="oid" />
	<input type="hidden" name="creditId" />
	<input type="hidden" name="optId" />
	<script>
		generateTableBanner('<div class="gray_15"><B><bean:message key="batch.courseAdd.Banner" bundle="COU"/></B></div>');
	</script>
	<tr>
		<td align="center">
	       	<table cellspacing="2" class="empty-border">
	       		<tr><td colspan="3" align="center"><font color="red"><strong>注意：學生衝堂僅顯示訊息，不影響加選作業</strong></font></td></tr>
				<tr>
					<td>班級代碼：&nbsp;
			 			<html:text property="classNo" size="6" maxlength="6" onkeyup="if(this.value != '') autoClassCheck(this); return false;" />
					 </td>
					 <td colspan="2">班級名稱：&nbsp;
			 			<html:text property="className" size="20" maxlength="20" readonly="true" />&nbsp;&nbsp;
					 	<span id="classInfo"></span>
					 </td>
				</tr>
				<tr>
					<td>科目代碼：&nbsp;
			 			<html:text property="courseNumber" size="5" maxlength="5" onkeyup="if(this.value != '') autoCourseCheck(this); return false;" />&nbsp;&nbsp;
			 			<html:select property="sterm" onchange="checkTerm(this);">
			 				<html:option value="1">1</html:option>
			 				<html:option value="2">2</html:option>
			 			</html:select>&nbsp;學期
					 </td>
					 <td>科目名稱：&nbsp;
			 			<html:text property="courseName" size="20" maxlength="20" readonly="true" />
					 	&nbsp;&nbsp;<span id="courseInfo"></span>
					 </td>
				</tr>
				<tr>
					<td>目前選課人數：&nbsp;
			 			<html:text property="stuSelect" size="3" maxlength="3" readonly="true" />
					 </td>
					 <td colspan="2">選課人數上限：&nbsp;
			 			<html:text property="selectLimit" size="3" maxlength="3" readonly="true" />&nbsp;&nbsp;
			 			<span id="countInfo"></span>
			 		</td>
				</tr>
				<tr>
					<td colspan="3">
			 			時數：&nbsp;<html:text property="hour" size="3" maxlength="3" readonly="true" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 	學分：&nbsp;<html:text property="credit" size="4" maxlength="3" readonly="true" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  	選別：&nbsp;<html:select property="opt" disabled="true">
					 		<html:option value=""></html:option>
					 		<html:option value="1">必修</html:option>
					 		<html:option value="2">選修</html:option>
					 	</html:select>
					 </td>
				</tr>
				<tr>
					<td colspan="3">
						<table width="100%" cellspacing="0" border="0" bordercolor="black">
							<tr>
								<td align="center">
									學號&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									姓名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									班級
								</td>
								<td width="40"></td>
								<td align="center">
									學號&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									姓名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									班級
								</td>
							</tr>								
							<c:forEach begin="0" end="<%= tw.edu.chit.struts.action.course.BatchInputStdCourseAction.ROW_COUNT - 1%>" step="2" var="index">
							<tr>								
								<td align="center">
									<input type="text" name="stdNo${index}" size="10" maxlength="10" onkeyup="autoStudentCheck(this, '${index}');" onblur="autoStudentCheck(this, '${index}');"/>									
									<input type="text" name="stdName${index}" size="8" disabled="true" />
									<input type="text" name="stdClass${index}" size="15" disabled="true" />
								</td>	
								<td>&nbsp;</td>													
								<td align="center">
									<input type="text" name="stdNo${index + 1}" size="10" maxlength="10" onkeyup="autoStudentCheck(this, '${index + 1}');" onblur="autoStudentCheck(this, '${index + 1}');"/>
									<input type="text" name="stdName${index + 1}" size="8" disabled="true" />	
									<input type="text" name="stdClass${index + 1}" size="15" disabled="true" />						
								</td>									
							</tr>	
							</c:forEach>
						</table>						
					</td>
				</tr>				
			</table>
		</td>
	</tr>
	<script>
		generateTableBanner(
			'<INPUT type="submit" name="method" id="s1" value="<bean:message key='makeSure' />" disabled="true" class="CourseButton" onclick="return checkStuSelect();">&nbsp;&nbsp;' +
			'<INPUT type="submit" name="method" value="<bean:message key='Cancel' />" class="CourseButton">'
		);
	</script>
</html:form>
</table>