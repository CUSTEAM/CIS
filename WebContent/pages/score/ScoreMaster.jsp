<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/Calendar.inc" %>
<%@ include file="/pages/include/ajax.js" %>

<script type="text/javascript">
			
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
				document.getElementById("school_year").value = obj.year;
				document.getElementById("school_term").value = obj.term;
				document.getElementById("chineseThesis").value = obj.chThesis;
				document.getElementById("englishThesis").value = obj.enThesis;
				document.getElementById("thesisScore").value = obj.thesisScore;
				document.getElementById("evgrScore").value = obj.evgrScore;
				document.getElementById("gradScore").value = obj.gradScore;
				document.getElementById("onlineFileDate").value = obj.online;
				document.getElementById("remark").value = obj.remark;
				document.getElementById("oid").value = obj.oid;
				document.getElementById("s1").setAttribute("disabled", obj.add);
				document.getElementById("s2").setAttribute("disabled", obj.upd);
				document.getElementById("s3").setAttribute("disabled", obj.del);
				document.getElementById("school_year").focus();
			} else {
				span.innerHTML = "";
				//span.style.backgroundColor = "yellow";
				span.style.color = "red";
				span.style.fontSize = "1em";
				// span.innerHTML = '<bean:message key="Message.ScoreHist.CantFindStudent" />';
				document.getElementById("student_no").focus();
				document.getElementById("school_year").value = "";
				document.getElementById("school_term").value = "";
				document.getElementById("chineseThesis").value = "";
				document.getElementById("englishThesis").value = "";
				document.getElementById("thesisScore").value = "";
				document.getElementById("evgrScore").value = "";
				document.getElementById("gradScore").value = "";
				document.getElementById("onlineFileDate").value = "";
				document.getElementById("remark").value = "";
				document.getElementById("oid").value = "";
				document.getElementById("s1").setAttribute("disabled", "true");
				document.getElementById("s2").setAttribute("disabled", "true");
			}
		}
	}
}

function modifyCheck() {
	var stdNo = document.getElementById("student_no").value;
	if(stdNo == "") {
		alert("請先選擇再行更新!!!");
		document.getElementById("student_no").focus();
		return false;
	}
	if(!confirm("確定更新?")) {
		return false;
	}
	return true;
}

function deleteCheck() {
	var stdNo = document.getElementById("student_no").value;
	if(stdNo == "") {
		alert("請先選擇再行刪除!!!");
		document.getElementById("student_no").focus();
		return false;
	}
	if(!confirm("確定刪除?")) {
		return false;
	}
	return true;
}

function calcGradScore() {
	var thesisScore = document.getElementById("thesisScore");
	if (thesisScore.value.length > 0 && isNaN(thesisScore.value)) {
		alert("論文成績輸入錯誤!!");
		thesisScore.focus();
	} else {	
		var evgrScore = parseFloat(document.getElementById("evgrScore").value) * 0.5;
		var tScore = parseFloat(thesisScore.value) * 0.5;
		if (isNaN(evgrScore + tScore))
			document.getElementById("gradScore").value = "0.0";
		else
			document.getElementById("gradScore").value = evgrScore + tScore;	
	}	
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Score/ScoreMaster" method="post" focus="student_no">
<input type="hidden" name="oid" />
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="score.scoreMaster.banner" bundle="SCR"/></B></div>');</script>	  
	<tr>
		<td align="center">
       	<table cellspacing="5" class="empty-border">
			<tr>
				<td>學號：&nbsp;
		 			<html:text property="student_no" size="10"
		 				onkeyup="if(this.value != '') autoCheck(this); return false;" 
		 			/>
		 			&nbsp;&nbsp;<span id="stdInfo"></span>		 			
				 </td>
			</tr>
		 	<tr>
		 		<td>學年：&nbsp;
		 			<html:text property="school_year" size="2" maxlength="3" />&nbsp;&nbsp;&nbsp;&nbsp;
		 			學期：&nbsp;
		 			<html:text property="school_term" size="2" maxlength="1" />
		 		</td>
		 	</tr>	
		 	<tr>
	      		<td>論文中文名稱：&nbsp;
	      			<html:textarea property="chineseThesis" rows="3" cols="50" />
	      		</td>
	      	</tr>
		 	<tr>
	      		<td>論文英文名稱：&nbsp;
	      			<html:textarea property="englishThesis" rows="3" cols="50" />
	      		</td>
	      	</tr>
	      	<tr>
	        	<td>論文成績：&nbsp;
	        		<html:text property="thesisScore" size="7" maxlength="5"
	        			onkeyup="calcGradScore();"
	        		/> 
	        		學業成績：&nbsp;
	        		<html:text property="evgrScore" size="7" maxlength="5" readonly="true" />
          			畢業成績：&nbsp;
          			<html:text property="gradScore" size="7" maxlength="5" readonly="true" />
	        	</td>
	      	</tr>
	      	<tr>
	      		<td>已查核全國博碩士論文線上建檔資料日期：
	      			<html:text property="onlineFileDate" size="8" maxlength="8" readonly="true" />
	      			<img src="images/cal.gif" name="calendar" width="20" height="20" 
	      				align="top" style="cursor:hand" 
	  	  				onclick="javascript:if(!onlineFileDate.disabled)popCalFrame.fPopCalendar('onlineFileDate','onlineFileDate',event);">
	      		</td>	
	      	</tr>
	      	<tr>
	      		<td>備 註：&nbsp;
	      			<html:textarea property="remark" rows="3" cols="50" />
	      		</td>
	      	</tr>
		</table>
	</td>
</tr>
<script>
	generateTableBanner(
		'<INPUT type="submit" name="scrMasterAction" id="s1" class="CourseButton" disabled="true" value="<bean:message key='score.scoreMaster.add' bundle='SCR'/>">&nbsp;&nbsp;' +
		'<INPUT type="submit" name="scrMasterAction" id="s2" class="CourseButton" disabled="true" value="<bean:message key='score.scoreMaster.update' bundle='SCR'/>" onclick="return modifyCheck();">&nbsp;&nbsp;' +
		'<INPUT type="submit" name="scrMasterAction" id="s3" class="CourseButton" disabled="true" value="<bean:message key='score.scoreMaster.delete' bundle='SCR'/>" onclick="return deleteCheck();">&nbsp;&nbsp;'
	);
</script>
</html:form>
</table>