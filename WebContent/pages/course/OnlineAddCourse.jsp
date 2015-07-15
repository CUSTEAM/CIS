<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/ajax.js" %>

<script type="text/javascript">
history.go(1);
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
				document.getElementById("csName").value = obj.chName;
				span.innerHTML = "";
				var classNo = document.getElementById("classNo").value;
				var csNo = document.getElementById("csCode").value;
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
				document.getElementById("csName").value = "";
				//span.innerHTML = request.responseText;
				document.getElementById("csCode").focus();
			}
		}
	}
}

function checkTerm(term) {
	if(document.getElementById("csCode").value != "") {
		autoCourseCheck(document.getElementById("csCode"));
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
				document.getElementById("csCode").focus();
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
				document.getElementById("oid").value = obj.oid;
				document.getElementById("credit").value = obj.credit;
				document.getElementById("hour").value = obj.hour;
				var selCount = obj.select;
				var limitCount = obj.limit;
				document.getElementById("stuSelect").value = selCount;
				document.getElementById("selectLimit").value = limitCount;
				document.getElementById("opt").value = obj.opt;
				document.getElementById("optId").value = obj.opt;
				document.getElementById("s1").setAttribute("disabled", "");
				if(parseInt(selCount) >= parseInt(limitCount)) {
					// 不限制行政人員進行加選
					// document.getElementById("s1").setAttribute("disabled", "true");
					var span = document.getElementById("countInfo");
					span.style.color = "red";
					span.style.fontSize = "1em";
					span.innerHTML = "選課人數不可超過上限人數!!";
				} 
				document.getElementById("s1").focus();
			} else {
				document.getElementById("credit").value = "";
				document.getElementById("hour").value = "";
				document.getElementById("stuSelect").value = "";
				document.getElementById("selectLimit").value = "";
				document.getElementById("opt").value = "";
				document.getElementById("optId").value = "";
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
	var aForm = document.forms[0];
	if(document.getElementById("classNo").value == "") {
		alert("班級代碼不可為空白!!");
		document.getElementById("classNo").focus();
		return false;
	}
	if(document.getElementById("csCode").value == "") {
		alert("科目代碼不可為空白!!");
		document.getElementById("csCode").focus();
		return false;
	}
	//alert(parseInt(document.getElementById("stuSelect").value));
	//alert(parseInt(document.getElementById("selectLimit").value));
	if(parseInt(document.getElementById("stuSelect").value) >= 
		parseInt(document.getElementById("selectLimit").value)) {
		alert("選課人數超過上限,請修改選課人數後再行加選!!");
		return false;
	}
	return true;
}

</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="${sessionScope.actionName}" method="post" focus="classNo" onsubmit="init('執行中, 請稍後')">
	<input type="hidden" name="oid" value="" />
	<input type="hidden" name="optId" value="" />
	<script>
		generateTableBanner('<div class="gray_15"><B><bean:message key="course.onlineAddRemoveCourse.addCourseBanner" bundle="COU"/></B></div>');
	</script>
	<tr>
		<td align="center">
	       	<table cellspacing="2" class="empty-border">
				<tr>
					<td>學號：&nbsp;
			 			<html:text property="stdNo" size="10" maxlength="8" disabled="true" />
					</td>
					<td>姓名：&nbsp;
			 			<html:text property="stdName" size="10" maxlength="8" disabled="true" />
					</td>
					<td>班級：&nbsp;
			 			<html:text property="stdClassName" size="20" disabled="true" />
					 </td>
				</tr>
				<tr>
					<td>班級代碼：&nbsp;
			 			<html:text property="classNo" size="6" maxlength="6" onkeyup="if(this.value != '') autoClassCheck(this); return false;" />&nbsp;&nbsp;
			 		</td>
					<td>班級名稱：&nbsp;
			 			<html:text property="className" size="20" maxlength="20" disabled="true" />
			 		</td>
					 <td><span id="classInfo"></span></td>
				</tr>
				<tr>
					<td>科目代碼：&nbsp;
			 			<html:text property="csCode" size="5" maxlength="5" onkeyup="if(this.value != '') autoCourseCheck(this); return false;" />
					 </td>
					 <td>科目名稱：&nbsp;
			 			<html:text property="csName" size="20" maxlength="20" disabled="true" />&nbsp;&nbsp;
			 			<html:select property="sterm" disabled="true" onchange="checkTerm(this);">
			 				<html:option value="1">1</html:option>
			 				<html:option value="2">2</html:option>
			 			</html:select>&nbsp;學期
					 
					 </td>
					 <td><span id="courseInfo"></span></td>
				</tr>
				<tr>
					<td>目前選課人數：&nbsp;
			 			<html:text property="stuSelect" size="3" maxlength="3" disabled="true" />
					 </td>
					 <td>選課人數上限：&nbsp;
			 			<html:text property="selectLimit" size="3" maxlength="3" disabled="true" />
						<span id="countInfo"></span>
					</td>
				</tr>
				<tr>
					<td>時數：&nbsp;
			 			<html:text property="hour" size="3" maxlength="3" disabled="true" />
					 </td>
					 <td>學分：&nbsp;
			 			<html:text property="credit" size="4" maxlength="3" readonly="true" />
					 </td>
					 <td>
					 	<html:select property="opt" disabled="true">
					 		<html:option value=""></html:option>
					 		<html:option value="1">必修</html:option>
					 		<html:option value="2">選修</html:option>
					 	</html:select>
					 </td>
				</tr>
			</table>
		</td>
	</tr>
	<script>
		generateTableBanner(
			'<INPUT type="submit" name="method" id="s1" value="<bean:message key='course.onlineAddRemoveCourse.makeSure' bundle="COU" />" class="CourseButton" onclick="return checkStuSelect();">&nbsp;&nbsp;' +
			'<INPUT type="submit" name="method" value="<bean:message key='Cancel' />" class="CourseButton">'
		);
	</script>
</html:form>
</table>