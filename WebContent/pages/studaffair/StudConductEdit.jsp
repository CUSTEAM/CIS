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

<form action="/CIS/StudAffair/StudConductEdit.do" method="post" name="bpForm" onSubmit="alert('submit!');">
<input type="hidden" name="opmode" value="" id="opmode">	

<c:set var="tcnt" value="1"/>

<table width="100%" cellpadding="0" cellspacing="0">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.StudConductEdit" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td align="left"> 
<!-- End Content Page Table Header -->

	<table cellspacing="5" class="empty-border" width="100%">
	<c:choose>
	<c:when test="${StudConductEdit != null}">
		<tr>
			<td align="left" valign="middle" colspan="7">
			<table width="100%">
				<tr>
				<td width="50">學號 ：</td>
				<td>
				<input name="studentNo" width="80" id="${tcnt}" type="text" size="8" maxlength="8" value="${StudConductEdit.studentNo}" onKeyUp="nextfocus(${tcnt+1});">
				</td>
				<td align="left" valign="middle" id="student">
       			&nbsp;&nbsp;${StudConductEdit.studentName}
       			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${StudConductEdit.departClass}
       			&nbsp;&nbsp;${StudConductEdit.deptClassName}
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;基本分：82 
				<c:set var="tcnt" value="${tcnt + 1}"/>
        		</td>
        		</tr>
        	</table>
        	</td>
        </tr>
        <tr>
			<td width="13%" align="center" bgcolor="#add8e6">導師</td>
			<td width="13%" align="center" bgcolor="#add8e6">系主任</td>
			<td width="13%" align="center" bgcolor="#add8e6">教官</td>
			<td width="13%" align="center" bgcolor="#add8e6">勤惰</td>
			<td width="16%" align="center" bgcolor="#add8e6">獎懲</td>
			<td width="16%" align="center" bgcolor="#add8e6">評審會</td>
			<td width="16%" align="center" bgcolor="#add8e6">合計</td>
        </tr>
        <tr>
        	<td align="center" valign="middle" bgcolor="#99ee90">
        		<input type="text" name="teacherScore" size="5" value="${StudConductEdit.teacherScore}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
        	</td>
        	<c:set var="tcnt" value="${tcnt + 1}"/>
        	<td align="center" valign="middle" bgcolor="#99ee90">
        		<input type="text" name="deptheaderScore" size="5" value="${StudConductEdit.deptheaderScore}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
        	</td>
        	<c:set var="tcnt" value="${tcnt + 1}"/>
        	<td align="center" valign="middle" bgcolor="#99ee90">
        		<input type="text" name="militaryScore" size="5" value="${StudConductEdit.militaryScore}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
        	</td>
        	<c:set var="tcnt" value="${tcnt + 1}"/>
        	<td align="center" valign="middle" bgcolor="#99ee90" id="dilgScoreDisp">${StudConductEdit.dilgScore}
        	</td>
        	<input type="hidden" name="dilgScore" value="${StudConductEdit.dilgScore}" id="hdilgScore">
        	<td align="center" valign="middle" bgcolor="#99ee90" id="desdScoreDisp">${StudConductEdit.dilgScore}
        	</td>
        	<input type="hidden" name="desdScore" value="${StudConductEdit.desdScore}" id="hdesdScore">
        	<td align="center" valign="middle" bgcolor="#99ee90">
        		<input type="text" name="meetingScore" size="5" value="${StudConductEdit.meetingScore}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
        	</td>
        	<c:set var="tcnt" value="${tcnt + 1}"/>
        	<td align="center" valign="middle" bgcolor="#99ee90" id="totalScoreInput">${StudConductEdit.totalScore}
        	</td>
        	<input type="hidden" name="totalScore" value="${StudConductEdit.totalScore}" id="htotalScore">
        </tr>
        <tr>
        	<td colspan="7">
        	<table width="100%">
	        <tr>
	        	<td align="left" valign="middle" width="15%">評語代碼一 ：</td>
	        	<td align="left" valign="middle" colspan="6">
	  		 	   <c:set var="codeId" 		    value="comCode1"/>
	  		 	   <c:set var="codeIdInitValue" value="${StudConductEdit.comCode1}"/>
	  		 	   <c:set var="codeSel"		    value="comCode1Sel"/>
	  		 	   <c:set var="codeList"		value="${ConductMark}"/>
	  		 	   <%@ include file="/pages/studaffair/include/CodeSelect.jsp" %>
	  		 	   <c:set var="tcnt" value="${tcnt + 1}"/>
	        	</td>
	        </tr>
	        <tr>
	        	<td align="left" valign="middle" width="15%">評語代碼二 ：</td>
	        	<td align="left" valign="middle" colspan="6">
	  		 	   <c:set var="codeId" 		    value="comCode2"/>
	  		 	   <c:set var="codeIdInitValue" value="${StudConductEdit.comCode2}"/>
	  		 	   <c:set var="codeSel"		    value="comCode2Sel"/>
	  		 	   <c:set var="codeList"		value="${ConductMark}"/>
	  		 	   <%@ include file="/pages/studaffair/include/CodeSelect.jsp" %>
	  		 	   <c:set var="tcnt" value="${tcnt + 1}"/>
	        	</td>
	        </tr>
	        <tr>
	        	<td align="left" valign="middle" width="15%">評語代碼三 ：</td>
	        	<td align="left" valign="middle" colspan="6">
	  		 	   <c:set var="codeId" 		    value="comCode3"/>
	  		 	   <c:set var="codeIdInitValue" value="${StudConductEdit.comCode3}"/>
	  		 	   <c:set var="codeSel"		    value="comCode3Sel"/>
	  		 	   <c:set var="codeList"		value="${ConductMark}"/>
	  		 	   <%@ include file="/pages/studaffair/include/CodeSelect.jsp" %>
	  		 	   <c:set var="tcnt" value="${tcnt + 1}"/>
	        	</td>
	        </tr>
	    </table>
	    </td>
	    </tr>
	</c:when>
		
	<c:when test="${StudConductEdit == null}">
		<tr>
			<td align="left" valign="middle" colspan="7">
			<table width="100%">
				<tr>
				<td width="50">學號 ：</td>
				<td width="80">
				<input name="studentNo" id="${tcnt}" type="text" size="8" maxlength="8" onKeyUp="nextfocus(${tcnt+1});">
				</td>
				<td align="left" valign="middle" id="student">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;基本分：82 
        		</td>
				<c:set var="tcnt" value="${tcnt + 1}"/>
				</tr>
			</table>
			</td>
        </tr>
        <tr>
			<td width="13%" align="center" bgcolor="#add8e6">導師</td>
			<td width="13%" align="center" bgcolor="#add8e6">系主任</td>
			<td width="13%" align="center" bgcolor="#add8e6">教官</td>
			<td width="13%" align="center" bgcolor="#add8e6">勤惰</td>
			<td width="16%" align="center" bgcolor="#add8e6">獎懲</td>
			<td width="16%" align="center" bgcolor="#add8e6">評審會</td>
			<td width="16%" align="center" bgcolor="#add8e6">合計</td>
        </tr>
        <tr>
        	<td align="center" valign="middle" bgcolor="#99ee90">
        		<input type="text" name="teacherScore" size="5" value="0.0" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
        	</td>
        	<c:set var="tcnt" value="${tcnt + 1}"/>
        	<td align="center" valign="middle" bgcolor="#99ee90">
        		<input type="text" name="deptheaderScore" size="5" value="0.0" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
        	</td>
        	<c:set var="tcnt" value="${tcnt + 1}"/>
        	<td align="center" valign="middle" bgcolor="#99ee90">
        		<input type="text" name="militaryScore" size="5" value="0.0" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
        	</td>
        	<c:set var="tcnt" value="${tcnt + 1}"/>
        	<td align="center" valign="middle" bgcolor="#99ee90" id="dilgScoreDisp">&nbsp;
        	</td>
        	<input type="hidden" name="dilgScore" value="" id="hdilgScore">
        	<td align="center" valign="middle" bgcolor="#99ee90" id="desdScoreDisp">&nbsp;
        	</td>
        	<input type="hidden" name="desdScore" value="" id="hdesdScore">
        	<td align="center" valign="middle" bgcolor="#99ee90">
        		<input type="text" name="meetingScore" size="5" value="0.0" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
        	</td>
        	<c:set var="tcnt" value="${tcnt + 1}"/>
        	<td align="center" valign="middle" bgcolor="#99ee90" id="totalScoreInput">&nbsp;</td>
        	<input type="hidden" name="totalScore" value="" id="htotalScore">
        </tr>
        <tr>
        	<td colspan="7">
        	<table width="100%">
	        <tr>
	        	<td align="left" valign="middle" width="13%">評語代碼一 ：</td>
	        	<td align="left" valign="middle" colspan="6">
	  		 	   <c:set var="codeId" 		    value="comCode1"/>
	  		 	   <c:set var="codeIdInitValue" value="${StudConductEdit.comCode1}"/>
	  		 	   <c:set var="codeSel"		    value="comCode1Sel"/>
	  		 	   <c:set var="codeList"		value="${ConductMark}"/>
	  		 	   <%@ include file="/pages/studaffair/include/CodeSelect.jsp" %>
	  		 	   <c:set var="tcnt" value="${tcnt + 1}"/>
	        	</td>
	        </tr>
	        <tr>
	        	<td align="left" valign="middle" width="13%">評語代碼二 ：</td>
	        	<td align="left" valign="middle" colspan="6">
	  		 	   <c:set var="codeId" 		    value="comCode2"/>
	  		 	   <c:set var="codeIdInitValue" value="${StudConductEdit.comCode2}"/>
	  		 	   <c:set var="codeSel"		    value="comCode2Sel"/>
	  		 	   <c:set var="codeList"		value="${ConductMark}"/>
	  		 	   <%@ include file="/pages/studaffair/include/CodeSelect.jsp" %>
	  		 	   <c:set var="tcnt" value="${tcnt + 1}"/>
	        	</td>
	        </tr>
	        <tr>
	        	<td align="left" valign="middle" width="13%">評語代碼三 ：</td>
	        	<td align="left" valign="middle" colspan="6">
	  		 	   <c:set var="codeId" 		    value="comCode3"/>
	  		 	   <c:set var="codeIdInitValue" value="${StudConductEdit.comCode3}"/>
	  		 	   <c:set var="codeSel"		    value="comCode3Sel"/>
	  		 	   <c:set var="codeList"		value="${ConductMark}"/>
	  		 	   <%@ include file="/pages/studaffair/include/CodeSelect.jsp" %>
	  		 	   <c:set var="tcnt" value="${tcnt + 1}"/>
	        	</td>
	        </tr>
	    </table>
	    </td>
	    </tr>
	</c:when>
	</c:choose>
		</table>
	  </td>
	</tr>		
	
	<script>
	generateTableBanner('<INPUT type="button" name="method" value="<bean:message key='OK'/>" id="ok" onClick="chgopmode(1);document.forms[0].submit();return true;">&nbsp;&nbsp;'+
	'<INPUT type="button" name="method" value="<bean:message key='Cancel'/>" id="cancel" onClick="chgopmode(0);document.forms[0].submit();">');
	//generateTableBanner('<INPUT type="button" name="method" value="<bean:message key='Cancel'/>" id="cancel" onClick="chgopmode(0);document.forms[0].submit();">');
	</script>

<%@ include file="/pages/studaffair/include/studGetJustInfo.js" %>
<script language="javascript">
//<!--
	var iplimit = ${tcnt-1};
	
	function calcTotalScore() {
		var teacherScore = document.getElementById("teacherScore");
		var deptheaderScore = document.getElementById("deptheaderScore");
		var militaryScore = document.getElementById("militaryScore");
		var meetingScore = document.getElementById("meetingScore");
		var dilgScore = document.getElementById("hdilgScore");
		var desdScore = document.getElementById("hdesdScore");
		var totalScore = 82;
		if(isNaN(teacherScore.value)){
			alert("導師加減分欄位僅能輸入數字!");
			return;
		} else {
			totalScore = totalScore +　eval(teacherScore.value);
		}
		
		if(isNaN(deptheaderScore.value)){
			alert("系主任加減分欄位僅能輸入數字!");
			return;
		} else {
			totalScore = totalScore +　eval(deptheaderScore.value);
		}
		
		if(isNaN(militaryScore.value)){
			alert("教官加減分欄位僅能輸入數字!");
			return;
		} else {
			totalScore = totalScore +　eval(militaryScore.value);
		}
		
		if(isNaN(meetingScore.value)){
			alert("評審會加減分欄位僅能輸入數字!");
			return;
		} else {
			totalScore = totalScore +　eval(meetingScore.value);
		}
		//alert("dilgScore" + dilgScore.value);
		totalScore = totalScore +　eval(dilgScore.value);
		totalScore = totalScore +　eval(desdScore.value);
		if(totalScore > 95) totalScore=95;
		
		var totalScoreInput = document.getElementById("totalScoreInput");
		totalScoreInput.innerHTML = totalScore;
		var htotalScore = document.getElementById("htotalScore");
		htotalScore.value = totalScore;
		return;
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
				calcTotalScore();
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
			obj.value="ok";
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
</form>

<c:if test="${StudConductEdit != null}">
<script>
//buttonctl('enable');
locktags('studentName');locktags('departClass');getfocus(6);</script>
</c:if>	

<c:if test="${StudConductEdit == null}">
<script>getfocus(1);</script>
</c:if>
<script>history.go(1);</script>