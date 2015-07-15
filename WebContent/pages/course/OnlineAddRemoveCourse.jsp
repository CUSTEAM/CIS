<%@ page language="java" contentType="text/html;charset=UTF-8"
	%>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/ajax.js"%>

<script type="text/javascript">
history.go(1);
function autoCheck(obj) {
	var arg = obj.value;
	var path = "<%= request.getContextPath() %>";
	var url = path + "/Score/ScoreMaster.do?scrMasterAction=" +
		"findStdByNo&stdNo=" + encodeURIComponent(arg);
	httpRequest("GET", url, true, responseHandler);
}

function responseHandler() {
	if(request.readyState == READ_STATE_COMPLETE) {
		if(request.status == RESPONSE_STATUS_OK) {
			var responseValue = request.responseText;
			var span = document.getElementById("stdInfo");
			if(responseValue.indexOf("查無此人") == -1) {
				var func = new Function("return " + responseValue);
				var obj = func();
				span.innerHTML = "";
				span.style.color = "blue";
				span.style.fontSize = "0.9em";
				span.innerHTML = obj.info;
				document.getElementById("s1").setAttribute("disabled", "");
				document.getElementById("s1").focus();
			} else {
				span.innerHTML = "";
				span.style.color = "red";
				span.style.fontSize = "1em";
				//span.innerHTML = '<bean:message key="Message.ScoreHist.CantFindStudent" />';
				document.getElementById("stdNo").focus();
				document.getElementById("s1").setAttribute("disabled", "true");
			}
		}
	}
}

function modifyCheck() {
	var stdNo = document.getElementById("stdNo").value;
	if(stdNo == "") {
		alert("請先選擇再行更新!!!");
		document.getElementById("stdNo").focus();
		return false;
	}
	if(!confirm("確定更新?")) {
		return false;
	}
	return true;
}

function makeSure() {
	if(!checkForDel("seldList")) {
		alert("請選取至少一筆資料!!");
		return false;
	}
	var count = getCookie("seldListCount");
	if(confirm("確定退選[" + count + "]筆課程資料嗎?")) {
		return true;
	}
	return false;
}

function modifySelLimit() {
	if(!checkForDel("seldList")) {
		alert("請選取至少一筆資料!!");
		return false;
	}
	return true;
}

function checkForDel(cookieName) {
	var iCount;
	iCount = getCookie(cookieName + "Count");
	if (iCount < 1) {
		return false;
	} else {
		return true;
	}
}

</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
	<html:form action="${sessionScope.actionName}" method="post" focus="stdNo" onsubmit="init('執行中, 請稍後')">
		<input type="hidden" name="oid" />
		<script>generateTableBanner('<div class="gray_15"><B><bean:message key="course.onlineAddRemoveCourse.banner" bundle="COU"/></B></div>');</script>
		<tr>
			<td align="center">
				<table cellspacing="2" class="empty-border">
					<tr>
						<td>
							學號：&nbsp;
							<html:text property="stdNo" size="8" maxlength="10"
								onkeyup="if(this.value != '') autoCheck(this); return false;" />
						</td>
						<td width="230" align="center">
							<span id="stdInfo">${sessionScope.StudentInfoAjax}</span>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<div id="seldInfo"></div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<script>
		generateTableBanner(
			'<INPUT type="submit" name="method" id="s1" value="<bean:message key='Query' />" class="CourseButton" disabled="true">'
		);
		</script>
		<script>generateTableBanner('<div class="gray_15"><B><bean:message key="course.onlineAddRemoveCourse.search.banner" bundle="COU"/></B></div>');</script>
		<tr>
			<td>
				<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
				<display:table name="${seldList}" export="false" id="row" pagesize="10000" sort="list" excludedParams="*" class="list">
					<%@ include file="../include/NoBanner.jsp" %>
					<display:column title="<script>generateTriggerAll(${fn:length(seldList)}, 'seldList'); </script>" class="center">
						<script>generateCheckbox("${row.position}", "seldList")</script>
					</display:column>
					<display:column titleKey="Course.label.classNo" property="classNo" sortable="true" class="center" />
					<display:column titleKey="Course.label.className" property="className" sortable="true" class="center" />
					<display:column titleKey="Course.label.courseNumber" property="csCode" sortable="true" class="center" />
					<display:column titleKey="Course.label.courseName" property="csName" sortable="true" class="left" />
					<display:column titleKey="Course.label.term" property="term" sortable="true" class="center" />	
					<display:column titleKey="Course.label.opt" property="optName" sortable="true" class="center" />	
					<display:column titleKey="Course.onlineAddRemoveCourse.stuSel" property="stuSelect" sortable="true" class="center" />
					<display:column titleKey="Course.onlineAddRemoveCourse.selLimit" property="selectLimit" sortable="true" class="center" />						
					<display:column titleKey="Course.label.credit" property="credit" sortable="true" class="center" />
					<display:column titleKey="Course.label.hours" property="hour" sortable="true" class="center" />
				</display:table>
				
				<c:if test="${not empty seldList}">
					<c:if test="${sessionScope.mode eq 'ALL'}">
						<script>
     						generateTableBanner(
     							'<INPUT type="submit" name="method" value="<bean:message key='course.onlineAddRemoveCourse.addCourse' bundle="COU" />" class="CourseButton" >&nbsp;&nbsp;' +
     							'<INPUT type="submit" name="method" value="<bean:message key='course.onlineAddRemoveCourse.deleteCourse' bundle="COU" />" class="CourseButton" onclick="return makeSure();">&nbsp;&nbsp;' +
     							'<INPUT type="submit" name="method" value="<bean:message key='course.onlineAddRemoveCourse.modifySelectLimit' bundle="COU" />" class="CourseButton" onclick="return modifySelLimit();">&nbsp;&nbsp;' +
     							'<INPUT type="submit" name="method" value="<bean:message key='course.onlineAddRemoveCourse.createdBaseCourse' bundle="COU" />" class="CourseButton">');
     					</script>
     				</c:if>  
				</c:if>		  
				
				<c:if test="${empty seldList}">
					<c:if test="${sessionScope.mode eq 'NONE'}"> 	
						<script>
	   						generateTableBanner(
	   							'<INPUT type="submit" name="method" value="<bean:message key='course.onlineAddRemoveCourse.addCourse' bundle="COU" />" class="CourseButton" >&nbsp;&nbsp;' +
	   							'<INPUT type="submit" name="method" value="<bean:message key='course.onlineAddRemoveCourse.createdBaseCourse' bundle="COU" />" class="CourseButton">');
	   					</script>
					</c:if>  
				</c:if>				
     		</td>
		</tr>
		
		<c:if test="${not empty seldList}">
		<tr height="40">
			<td align="center">
				<table width="99%" class="hairLineTable">
	  				<tr>
	    				<td class="hairLineTdF">
	    					<table>
	    						<tr>
	    							<td> 
		    							<html:link page="/Course/StudentScoreHistory.do" paramId="no" paramName="NO">
		    							<img src="images/vcard.png" border="0"> 歷年成績表
		    							</html:link>
	    							</td>
	    						</tr>
	    					</table>
	    				</td>
	  				</tr>
				</table>
			</td>
		</tr>
		</c:if>	
		
	</html:form>
</table>
<script type="text/javascript">
<c:if test="${not empty seldList}">
	var msg = "總科目數：" + <%=session.getAttribute("seldCount")%> + "&nbsp;&nbsp;&nbsp;";
	msg += "總學分數：" + <%=(Float) session.getAttribute("seldCredit")%> + "&nbsp;&nbsp;&nbsp;";
	msg += "總時數：" + <%=session.getAttribute("seldHours")%>;
	document.getElementById("seldInfo").innerHTML = msg;
	document.getElementById("s1").setAttribute("disabled", "");
</c:if>
</script>
