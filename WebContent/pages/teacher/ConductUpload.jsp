<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/CodeSelection.js" %>

<style type="text/css">
<!--
.input {
	width:20px;
}
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.style2 {color: #FF0000}
-->
</style>

<form action="/CIS/Teacher/StudConductUploadModify.do" method="post" name="cuForm">
<input type="hidden" name="opmode" value="" id="opmode">	

<c:set var="tcnt" value="1"/>
<c:set var="record" value="0"/>

<table width="100%" cellpadding="0" cellspacing="0">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.StudConductUpload" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td align="left"> 
<!-- End Content Page Table Header -->

	<table cellspacing="5" class="empty-border" width="100%">
	<c:choose>
	
	<c:when test="${StudConductUploadModify != null}">
	<tr>
		<td colspan="8">
		<c:if test="${StudConductUploadModifyInit.uploadMode=='Teacher'}">
		<!-- 
		<font color="red">請注意!!!如無法於30分鐘內完成操行加減分等設定，請導師先上傳後再重新設定，以避免資料遺失!!!<br/>
		程式會在第20及25分鐘時各提醒一次，不便之處請多包涵!!!</font><br/><br/>
		導師加減分最多為4分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 -->
		導師加減分最多為4分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</c:if>
		<c:if test="${StudConductUploadModifyInit.uploadMode=='Chairman'}">
		系主任加減分最多為2分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</c:if>
		<c:if test="${StudConductUploadModifyInit.uploadMode=='Drillmaster'}">
		教官加減分最多為4分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</c:if>
		</td>
	</tr>
	<tr>
		<td colspan="8">${StudConductUploadModifyInit.departClass}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		${StudConductUploadModifyInit.deptClassName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;基本分：82
		</td>
	</tr>
	<tr>
		<td align="left" valign="middle" width="30%" bgcolor="#add8e6">學號  姓名</td>
		<td width="10%" align="center" bgcolor="#add8e6">導師</td>
		<td width="10%" align="center" bgcolor="#add8e6">系主任</td>
		<td width="10%" align="center" bgcolor="#add8e6">教官</td>
		<td width="10%" align="center" bgcolor="#add8e6">勤惰</td>
		<td width="10%" align="center" bgcolor="#add8e6">獎懲</td>
		<td width="10%" align="center" bgcolor="#add8e6">評審會</td>
		<td width="10%" align="center" bgcolor="#add8e6">合計</td>
    </tr>
	<c:forEach items="${StudConductUploadModify}" var="Conduct">
		<input name="studentNo" type="hidden" value="${Conduct.studentNo}">
        <tr>
        	<td align="left" valign="middle" bgcolor="#99ee90">
        		${Conduct.studentNo}&nbsp;&nbsp;${Conduct.studentName}
        	</td>
        	<c:choose>
        		<c:when test="${StudConductUploadModifyInit.uploadMode=='Teacher'}">
        			<td align="center" valign="middle" bgcolor="#99ee90">
        				<input type="text" size="5" name="teacherScore" value="${Conduct.teacherScore}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});" onBlur="caclTotal(${tcnt+1});">
        			</td>
        			<c:set var="tcnt" value="${tcnt + 1}"/>
        			
        			<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.deptheaderScore}
		        	</td>
		        	<input type="hidden" name="deptheaderScore" value="${Conduct.deptheaderScore}">
		        		
		        	<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.militaryScore}
		        	</td>
		        	<input type="hidden" name="militaryScore" value="${Conduct.militaryScore}">
        		</c:when>
        		<c:when test="${StudConductUploadModifyInit.uploadMode=='Chairman'}">
        			<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.teacherScore}
        			</td>
        			<input type="hidden" name="teacherScore" value="${Conduct.teacherScore}">
        			
        			<td align="center" valign="middle" bgcolor="#99ee90">
		        		<input type="text" size="5" name="deptheaderScore" value="${Conduct.deptheaderScore}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});" onBlur="caclTotal(${tcnt+1});">
		        	</td>
        			<c:set var="tcnt" value="${tcnt + 1}"/>
		        		
		        	<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.militaryScore}
		        	</td>
		        	<input type="hidden" name="militaryScore" value="${Conduct.militaryScore}">
        		</c:when>
        		<c:when test="${StudConductUploadModifyInit.uploadMode=='Drillmaster'}">
        			<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.teacherScore}
        			</td>
        			<input type="hidden" name="teacherScore" value="${Conduct.teacherScore}">
        			
        			<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.deptheaderScore}
		        	</td>
		        	<input type="hidden" name="deptheaderScore" value="${Conduct.deptheaderScore}">
        			
        			<td align="center" valign="middle" bgcolor="#99ee90">
		        		<input type="text" size="5" name="militaryScore" value="${Conduct.militaryScore}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});" onBlur="caclTotal(${tcnt+1});">
		        	</td>
        			<c:set var="tcnt" value="${tcnt + 1}"/>
        		</c:when>
        	</c:choose>
		        		
		        	<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.dilgScore}
		        	</td>
		        	<input type="hidden" name="dilgScore" value="${Conduct.dilgScore}" id="hdilgScore">
		        	
		        	<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.desdScore}
		        	</td>
		        	<input type="hidden" name="desdScore" value="${Conduct.desdScore}" id="hdesdScore">
		        	
		        	<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.meetingScore}
		        	</td>
		        	<input type="hidden" name="meetingScore" value="${Conduct.meetingScore}">
		        	
		        	<td align="center" valign="middle" bgcolor="#99ee90" id="totalScoreInput${record}">${Conduct.totalScore}
		        	</td>
					<input type="hidden" name="totalScore" value="${Conduct.totalScore}" id="htotalScore${record}">
        			
        </tr>
        <c:if test="${StudConductUploadModifyInit.uploadMode=='Teacher'}">
        <tr>
        	<td colspan="8">
        	<table width="100%">
	        <tr>
	        	<td align="left" valign="middle" width="15%">評語代碼一 ：</td>
	        	<td align="left" valign="middle" colspan="6">
	  		 	   <c:set var="codeId" 		    value="comCode1"/>
	  		 	   <c:set var="codeIdInitValue" value="${Conduct.comCode1}"/>
	  		 	   <c:set var="codeSel"		    value="comCode1Sel"/>
	  		 	   <c:set var="codeList"		value="${ConductMark}"/>
	  		 	   <%@ include file="/pages/studaffair/include/CodeSelect4ClassConduct.jsp" %>
	  		 	   <c:set var="tcnt" value="${tcnt + 1}"/>
	        	</td>
	        </tr>
	        <tr>
	        	<td align="left" valign="middle" width="15%">評語代碼二 ：</td>
	        	<td align="left" valign="middle" colspan="6">
	  		 	   <c:set var="codeId" 		    value="comCode2"/>
	  		 	   <c:set var="codeIdInitValue" value="${Conduct.comCode2}"/>
	  		 	   <c:set var="codeSel"		    value="comCode2Sel"/>
	  		 	   <c:set var="codeList"		value="${ConductMark}"/>
	  		 	   <%@ include file="/pages/studaffair/include/CodeSelect4ClassConduct.jsp" %>
	  		 	   <c:set var="tcnt" value="${tcnt + 1}"/>
	        	</td>
	        </tr>
	        <tr>
	        	<td align="left" valign="middle" width="15%">評語代碼三 ：</td>
	        	<td align="left" valign="middle" colspan="6">
	  		 	   <c:set var="codeId" 		    value="comCode3"/>
	  		 	   <c:set var="codeIdInitValue" value="${Conduct.comCode3}"/>
	  		 	   <c:set var="codeSel"		    value="comCode3Sel"/>
	  		 	   <c:set var="codeList"		value="${ConductMark}"/>
	  		 	   <%@ include file="/pages/studaffair/include/CodeSelect4ClassConduct.jsp" %>
	  		 	   <c:set var="tcnt" value="${tcnt + 1}"/>
	        	</td>
	        </tr>
	        </table>
	     </td>
	     </tr>
	    </c:if>
		<c:set var="record" value="${record + 1}"/>
	   
    </c:forEach>
	</c:when>
	
	
	<c:when test="${not empty StudConductUploadInEdit}">
	
	<tr>
		<td colspan="8">
		<c:if test="${StudConductUploadModifyInit.uploadMode=='Teacher'}">
		<!-- 
		<font color="red">請注意!!!如無法於30分鐘內完成操行加減分等設定，請導師先上傳後再重新設定，以避免資料遺失!!!<br/>
		程式會在第20及25分鐘時各提醒一次，不便之處請多包涵!!!</font><br/><br/>
		導師加減分最多為4分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 -->
		導師加減分最多為4分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</c:if>
		<c:if test="${StudConductUploadModifyInit.uploadMode=='Chairman'}">
		系主任加減分最多為2分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</c:if>
		<c:if test="${StudConductUploadModifyInit.uploadMode=='Drillmaster'}">
		教官加減分最多為4分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</c:if>
		</td>
	</tr>
	<tr>
		<td colspan="8">${StudConductUploadModifyInit.departClass}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		${StudConductUploadModifyInit.deptClassName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;基本分：82
		</td>
	</tr>
	<tr>
		<td align="left" valign="middle" width="30%" bgcolor="#add8e6">學號  姓名</td>
		<td width="10%" align="center" bgcolor="#add8e6">導師</td>
		<td width="10%" align="center" bgcolor="#add8e6">系主任</td>
		<td width="10%" align="center" bgcolor="#add8e6">教官</td>
		<td width="10%" align="center" bgcolor="#add8e6">勤惰</td>
		<td width="10%" align="center" bgcolor="#add8e6">獎懲</td>
		<td width="10%" align="center" bgcolor="#add8e6">評審會</td>
		<td width="10%" align="center" bgcolor="#add8e6">合計</td>
    </tr>
	<c:forEach items="${StudConductUploadInEdit}" var="Conduct">
		<input name="studentNo" type="hidden" value="${Conduct.studentNo}">
        <tr>
        	<td align="left" valign="middle" bgcolor="#99ee90">
        		${Conduct.studentNo}&nbsp;&nbsp;${Conduct.studentName}
        	</td>
        	<c:choose>
        		<c:when test="${StudConductUploadModifyInit.uploadMode=='Teacher'}">
        			<td align="center" valign="middle" bgcolor="#99ee90">
        				<input type="text" size="5" name="teacherScore" value="${Conduct.teacherScore}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});" onBlur="caclTotal(${tcnt+1});">
        			</td>
        			<c:set var="tcnt" value="${tcnt + 1}"/>
        			
        			<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.deptheaderScore}
		        	</td>
		        	<input type="hidden" name="deptheaderScore" value="${Conduct.deptheaderScore}">
		        		
		        	<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.militaryScore}
		        	</td>
		        	<input type="hidden" name="militaryScore" value="${Conduct.militaryScore}">
        		</c:when>
        		<c:when test="${StudConductUploadModifyInit.uploadMode=='Chairman'}">
        			<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.teacherScore}
        			</td>
        			<input type="hidden" name="teacherScore" value="${Conduct.teacherScore}">
        			
        			<td align="center" valign="middle" bgcolor="#99ee90">
		        		<input type="text" size="5" name="deptheaderScore" value="${Conduct.deptheaderScore}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});" onBlur="caclTotal(${tcnt+1});">
		        	</td>
        			<c:set var="tcnt" value="${tcnt + 1}"/>
		        		
		        	<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.militaryScore}
		        	</td>
		        	<input type="hidden" name="militaryScore" value="${Conduct.militaryScore}">
        		</c:when>
        		<c:when test="${StudConductUploadModifyInit.uploadMode=='Drillmaster'}">
        			<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.teacherScore}
        			</td>
        			<input type="hidden" name="teacherScore" value="${Conduct.teacherScore}">
        			
        			<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.deptheaderScore}
		        	</td>
		        	<input type="hidden" name="deptheaderScore" value="${Conduct.deptheaderScore}">
        			
        			<td align="center" valign="middle" bgcolor="#99ee90">
		        		<input type="text" size="5" name="militaryScore" value="${Conduct.militaryScore}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});" onBlur="caclTotal(${tcnt+1});">
		        	</td>
        			<c:set var="tcnt" value="${tcnt + 1}"/>
        		</c:when>
        	</c:choose>
		        		
		        	<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.dilgScore}
		        	</td>
		        	<input type="hidden" name="dilgScore" value="${Conduct.dilgScore}" id="hdilgScore">
		        	
		        	<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.desdScore}
		        	</td>
		        	<input type="hidden" name="desdScore" value="${Conduct.desdScore}" id="hdesdScore">
		        	
		        	<td align="center" valign="middle" bgcolor="#99ee90">${Conduct.meetingScore}
		        	</td>
		        	<input type="hidden" name="meetingScore" value="${Conduct.meetingScore}">
		        	
		        	<td align="center" valign="middle" bgcolor="#99ee90" id="totalScoreInput${record}">${Conduct.totalScore}
		        	</td>
					<input type="hidden" name="totalScore" value="${Conduct.totalScore}" id="htotalScore${record}">
        			
        </tr>
        <c:if test="${StudConductUploadModifyInit.uploadMode=='Teacher'}">
        <tr>
        	<td colspan="8">
        	<table width="100%">
	        <tr>
	        	<td align="left" valign="middle" width="15%">評語代碼一 ：</td>
	        	<td align="left" valign="middle" colspan="6">
	  		 	   <c:set var="codeId" 		    value="comCode1"/>
	  		 	   <c:set var="codeIdInitValue" value="${Conduct.comCode1}"/>
	  		 	   <c:set var="codeSel"		    value="comCode1Sel"/>
	  		 	   <c:set var="codeList"		value="${ConductMark}"/>
	  		 	   <%@ include file="/pages/studaffair/include/CodeSelect4ClassConduct.jsp" %>
	  		 	   <c:set var="tcnt" value="${tcnt + 1}"/>
	        	</td>
	        </tr>
	        <tr>
	        	<td align="left" valign="middle" width="15%">評語代碼二 ：</td>
	        	<td align="left" valign="middle" colspan="6">
	  		 	   <c:set var="codeId" 		    value="comCode2"/>
	  		 	   <c:set var="codeIdInitValue" value="${Conduct.comCode2}"/>
	  		 	   <c:set var="codeSel"		    value="comCode2Sel"/>
	  		 	   <c:set var="codeList"		value="${ConductMark}"/>
	  		 	   <%@ include file="/pages/studaffair/include/CodeSelect4ClassConduct.jsp" %>
	  		 	   <c:set var="tcnt" value="${tcnt + 1}"/>
	        	</td>
	        </tr>
	        <tr>
	        	<td align="left" valign="middle" width="15%">評語代碼三 ：</td>
	        	<td align="left" valign="middle" colspan="6">
	  		 	   <c:set var="codeId" 		    value="comCode3"/>
	  		 	   <c:set var="codeIdInitValue" value="${Conduct.comCode3}"/>
	  		 	   <c:set var="codeSel"		    value="comCode3Sel"/>
	  		 	   <c:set var="codeList"		value="${ConductMark}"/>
	  		 	   <%@ include file="/pages/studaffair/include/CodeSelect4ClassConduct.jsp" %>
	  		 	   <c:set var="tcnt" value="${tcnt + 1}"/>
	        	</td>
	        </tr>
	        </table>
	     </td>
	     </tr>
	   	</c:if>
		<c:set var="record" value="${record + 1}"/>
	   
    </c:forEach>
	</c:when>
	<c:when test="${empty StudConductUploadInEdit}">
	<tr><td><font color="red">查無該班學生資料</font></td></tr>
	</c:when>
	</c:choose>
		</table>
	  </td>
	</tr>
			
	<c:if test="${(not empty StudConductUploadInEdit) || (not empty StudConductUploadModify)}">
	<script>
	generateTableBanner('<INPUT type="button" name="method" value="<bean:message key='StartUpload'/>" id="StartUpload" onClick="chgopmode(1);document.forms[0].submit();return true;">&nbsp;&nbsp;'+
	'<INPUT type="button" name="method" value="<bean:message key='Cancel'/>" id="cancel" onClick="chgopmode(0);document.forms[0].submit();">');
	//generateTableBanner('<INPUT type="button" name="method" value="<bean:message key='Cancel'/>" id="cancel" onClick="chgopmode(0);document.forms[0].submit();">');
	</script>
	</c:if>
	
	<c:if test="${(empty StudConductUploadInEdit) && (empty StudConductUploadModify)}">
	<script>
	generateTableBanner('<INPUT type="button" name="method" value="<bean:message key='Cancel'/>" id="cancel" onClick="chgopmode(0);document.forms[0].submit();">');
	//generateTableBanner('<INPUT type="button" name="method" value="<bean:message key='Cancel'/>" id="cancel" onClick="chgopmode(0);document.forms[0].submit();">');
	</script>
	</c:if>
</table>
</form>
	
<%@ include file="/pages/studaffair/include/studGetJustInfo.js" %>

<c:choose>
<c:when test="${StudConductUploadModifyInit.uploadMode=='Teacher'}">
	<script language="javascript">
		var tabperrow = 4;
		var alertnum = 1;
		
		//setTimeout("upAlert()", 1200000);
		
		function upAlert(){
			if(alertnum == 1){
			 	alert("20分鐘時間已經到了喔!");
			 	alertnum = alertnum + 1;
			 	setTimeout("upAlert()", 300000);
			} else if(alertnum > 1){
				alert("25分鐘時間已經到了喔!\n請記得趕快上傳,以免資料遺失!");
				
			}
		}
	</script>
</c:when>
<c:otherwise>
	<script language="javascript">
		var tabperrow = 1;
	</script>
</c:otherwise>
</c:choose>

<script language="javascript">
//<!--
	var myTimeOut = globalTimeOut;
	globalTimeOut = 7200000;
	var iplimit = ${tcnt-1};
	String.prototype.trim=trim;  //傳回去除前後空白的值
	String.prototype.ltrim=ltrim;  //傳回去除左邊空白的值
	String.prototype.rtrim=rtrim;  //傳回去除右邊空白的值

	function trim() {
  		return this.replace(/^\s+|\s+$/g, "");
	} 
	function ltrim() {
 	 	return this.replace(/(^\s*)/g, "");
	}
	function rtrim() {
  		return this.replace(/(\s*$)/g, "");
	}
	
	function calcTotalScore(recnum) {
		var teacherScore = (document.getElementsByName("teacherScore"))[recnum];
		var deptheaderScore = (document.getElementsByName("deptheaderScore"))[recnum];
		var militaryScore = (document.getElementsByName("militaryScore"))[recnum];
		var meetingScore = (document.getElementsByName("meetingScore"))[recnum];
		var dilgScore = (document.getElementsByName("dilgScore"))[recnum];
		var desdScore = (document.getElementsByName("desdScore"))[recnum];
		
		var ScoreTeacher = 0;
		var ScoreDeptheader = 0;
		var ScoreMilitary = 0;
		var ScoreMeeting = 0;
		var ScoreDilg = 0;
		var ScoreDesd = 0;		
		var str1 = "";
		
		var totalScore = 82;
		
		str1 = teacherScore.value.trim();
		if(str1.indexOf("-") == 0){
			str1 = str1.substr(1);
		}
		if(isNaN(str1)){
			alert("導師加減分欄位僅能輸入數字!");
			return;
		} else {
			ScoreTeacher = eval(teacherScore.value);
			if(ScoreTeacher > 4 || ScoreTeacher < -4){
				alert("導師加減分最多為4分!");
				return;
			}
			totalScore = totalScore +　ScoreTeacher;
		}
		
		str1 = deptheaderScore.value.trim();
		if(str1.indexOf("-") == 0){
			str1 = str1.substr(1);
		}
		if(isNaN(str1)){
			alert("系主任加減分欄位僅能輸入數字!");
			return;
		} else {
			ScoreDeptheader = eval(deptheaderScore.value);
			if(ScoreDeptheader > 2 || ScoreDeptheader < -2){
				alert("系主任加減分最多為2分!");
				return;
			}
			totalScore = totalScore +　ScoreDeptheader;
		}
		
		str1 = militaryScore.value.trim();
		if(str1.indexOf("-") == 0){
			str1 = str1.substr(1);
		}
		if(isNaN(str1)){
			alert("教官加減分欄位僅能輸入數字!");
			return;
		} else {
			ScoreMilitary = eval(militaryScore.value);
			if(ScoreMilitary > 4 || ScoreMilitary < -4){
				alert("教官加減分最多為4分!");
				return;
			}
			totalScore = totalScore +　ScoreMilitary;
		}
		
		if(isNaN(meetingScore.value)){
			alert("評審會加減分欄位僅能輸入數字!");
			return;
		} else {
			totalScore = totalScore +　eval(meetingScore.value);
		}
		
		totalScore = totalScore +　eval(dilgScore.value);
		totalScore = totalScore +　eval(desdScore.value);
		if(totalScore > 0)
			totalScore = Math.round(totalScore * 100) / 100;
		else if(totalScore < 0)
			totalScore = Math.round(totalScore * 100 * -1) / 100 * -1;
			
		if(totalScore > 95) totalScore=95;
		
		var totalScoreInput = document.getElementById("totalScoreInput" + recnum);
		totalScoreInput.innerHTML = totalScore;
		var htotalScore = document.getElementById("htotalScore" + recnum);
		//alert("totalScore:" + htotalScore.value);
		htotalScore.value = totalScore;
		return;
	}
	
	function caclTotal(ntab){
		var recnum = Math.floor((ntab-1)/tabperrow);
		if(tabperrow==1) recnum = recnum - 1;
		calcTotalScore(recnum);
	}
	
	function nextfocus(ntab) {
		var code = event.keyCode;
		var iptx1s = document.getElementById("1");
		var thisElem, nextElem;
		//code:left(37),right(39),up(38),down(40),Enter(13),del(46),backspace(8)
		//alert("code:" + code + ",ntab:" + ntab);
		thisElem = document.getElementById(ntab-1);
		//if(thisElem.value.length >= thisElem.maxlength || code==39 || code==40 || code==13) {
		if(thisElem.value.length >= thisElem.maxlength || code==40 || code==13) {
			if(thisElem.name=="studentNo") {
				getStudentJust(ntab-1);
			} else if(thisElem.name=="teacherScore" || thisElem.name=="deptheaderScore" || 
				thisElem.name=="militaryScore" || thisElem.name=="meetingScore") {
				var recnum = Math.floor((ntab-1)/tabperrow);
				if(tabperrow==1) recnum = recnum - 1;
				calcTotalScore(recnum);
			}
			if(ntab <= iplimit) {
				nextElem = document.getElementById(ntab);
				nextElem.focus();
			} else if(ntab > iplimit){
				chgopmode(1);
				document.forms[0].submit();
			}
		//}else if(code==37 || code==38) {
		}else if(code==38) {
			if(ntab==2) return;
			ntab = ntab -2;
			nextElem = document.getElementById(ntab);
			nextElem.focus();
			nextElem.select();
		}else if(code==46 || code==8) {
			return;
		}else if(ntab > iplimit) {
			return;
		}
		return;
	};
	
	function getfocus(tbidx) {
		var obj=document.getElementById(tbidx);
		obj.focus();
	}
	
	function fsubmit(){
		alert("fsubmit");
		document.bpForm.submit();
	}
	
	function buttonctl(mode){
		if(mode=="enable") {
			document.getElementById("ok").disabled = false;
		}
		if(mode=="disable") {
			document.getElementById("ok").disabled = true;
		}
	}
	
	function chgopmode(mode){
		var obj = document.getElementById("opmode");
		if(mode==1) {
			obj.value="StartUpload";
		}else if(mode==0) {
			obj.value="cancel";
		}
	}

	function locktags(tagname) {
		var objs = document.getElementsByName(tagname);
		for(k=0; k<objs.length; k++){
			objs[k].readOnly = true;
		}
	}
		
//-->

</script>

<c:if test="${StudConductEdit != null}">
<script>
//buttonctl('enable');
locktags('studentName');locktags('departClass');getfocus(6);</script>
</c:if>	

<c:if test="${StudConductEdit == null}">
<script>getfocus(1);</script>
</c:if>
<script>history.go(1);</script>