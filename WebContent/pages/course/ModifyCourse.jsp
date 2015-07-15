<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/ajax.js" %>

<script type="text/javascript"> 
history.go(1);
function autoCheck(obj) {
	var arg = obj.value;
	var url = "/CIS/Score/ScoreMaster.do?scrMasterAction=" + 
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
				var info = obj.info;
				document.getElementById("stdName").value = (info.split(" "))[0];
				document.getElementById("stdClassName").value = (info.split(" "))[1];
				span.innerHTML = obj.info;
				document.getElementById("s1").setAttribute("disabled", "");				
			} else {
				span.innerHTML = "";
				span.style.color = "red";
				span.style.fontSize = "1em";
				span.innerHTML = '<bean:message key="Message.ScoreHist.CantFindStudent" />';
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

</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Course/OnlineAddRemoveCourse" method="post">
<input type="hidden" name="dtimeOid" value="${seldData.dtimeOid}" />
	<script>
		generateTableBanner('<div class="gray_15"><B><bean:message key="course.onlineAddRemoveCourse.modifyBanner" bundle="COU"/></B></div>');	
	</script>	  
	<tr>
		<td align="center">
	       	<table cellspacing="2" class="empty-border">
				<!-- tr>
					<td>學號：&nbsp;
			 			<html:text property="stdNo" size="10" maxlength="8" disabled="true" />		 					 								  	
					</td>
					<td>姓名：&nbsp;
			 			<html:text property="stdName" size="10" maxlength="8" disabled="true" />		 					 			
					</td>
					<td>班級：&nbsp;
			 			<html:text property="stdClassName" size="20" disabled="true" />		 					 			
					 </td>
				</tr-->	
				<tr>
					<td>科目代碼：&nbsp;
			 			<html:text property="csCode" size="5" maxlength="5" value="${seldData.csCode}" disabled="true" />		 					 								  	
					 </td>
					 <td>科目名稱：&nbsp;
			 			<html:text property="csName" size="20" maxlength="20" value="${seldData.csName}" disabled="true" />		 					 			
					 </td>					 
				</tr>	
				<tr>
					<td>目前選課人數：&nbsp;
			 			<html:text property="stuSelect" size="3" maxlength="3" value="${seldData.stuSelect}" disabled="true" />
					 </td>					 
					 <td>選課人數上限：&nbsp;
			 			<html:text property="selectLimit" size="1" maxlength="3" value="${seldData.selectLimit}" />		 					 			
					 </td>	
					 <td>&nbsp;</td>				 
				</tr>
				<tr>
					<td>時數：&nbsp;
			 			<html:text property="hour" size="3" maxlength="3" value="${seldData.hour}" disabled="true" />		 					 								  	
					 </td>
					 <td>學分：&nbsp;
			 			<html:text property="credit" size="4" maxlength="3" value="${seldData.credit}" disabled="true" />	 					 			
					 </td>
					 <td>
					 	<html:select property="opt" value="${seldData.opt}" disabled="true">
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
			'<INPUT type="submit" name="method" id="s1" value="<bean:message key='course.onlineAddRemoveCourse.update' bundle="COU" />" class="CourseButton">&nbsp;&nbsp;' + 
			'<INPUT type="submit" name="method" id="s1" value="<bean:message key='Cancel' />" class="CourseButton">'
		);
	</script>		
</html:form>
</table>