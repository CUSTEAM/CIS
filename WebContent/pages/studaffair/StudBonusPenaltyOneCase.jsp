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

<form action="/CIS/StudAffair/StudBonusPenaltyOneCase.do" method="post" name="bpForm" onSubmit="alert('submit!');">
<input type="hidden" name="mode" value="${StudBonusPenaltyInfo.mode}">

<c:set var="tcnt" value="1"/>

<table width="100%" cellpadding="0" cellspacing="0">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="sbpTitle.StudBonusPenaltyOneCase" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td align="left"> 
<!-- End Content Page Table Header -->

	<table cellspacing="5" class="empty-border" width="100%">
		<c:if test="${StudBonusPenaltyEdit != null}">
		
		</c:if>
		
		<tr>
			<td align="left" valign="middle">日期：</td>
			<td align="left" valign="middle">民國
       			<input name="bpYear" id="${tcnt}" type="text" size="3" maxlength="3" value="" onKeyUp="nextfocus(${tcnt+1});">年&nbsp;&nbsp;
				<c:set var="tcnt" value="${tcnt + 1}"/>
            	<input name="bpMonth" id="${tcnt}" type="text" size="2" maxlength="3" value="" onKeyUp="nextfocus(${tcnt+1});">月&nbsp;&nbsp;
				<c:set var="tcnt" value="${tcnt + 1}"/>
            	<input name="bpDay" id="${tcnt}" type="text" size="2" maxlength="3" value="" onKeyUp="nextfocus(${tcnt+1});">日&nbsp;&nbsp;
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</td>
        </tr>
        <tr>
        	<td align="left" valign="middle">文號：</td>
        	<td align="left" valign="middle" colspan="4">
        		<input type="text" name="docNo" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
        	</td>
        	<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
        <tr>
        	<td align="left" valign="middle">原因：</td>
        	<td align="left" valign="middle">
  		 	   <c:set var="codeId" 		    value="reason"/>
  		 	   <c:set var="codeIdInitValue" value="${StudBonusPenaltyInfo.reason}"/>
  		 	   <c:set var="codeSel"		    value="reasonSel"/>
  		 	   <c:set var="codeList"		value="${BonusPenaltyReason}"/>
  		 	   <%@ include file="/pages/studaffair/include/CodeSelect.jsp" %>
  		 	   <c:set var="tcnt" value="${tcnt + 1}"/>
        	</td>
        </tr>
        <tr>
        	<td width="50" align="left" valign="middle">種類1：</td>
  		 	   <c:set var="codeId" 		    value="kind1"/>
  		 	   <c:set var="codeIdInitValue" value="${StudBonusPenaltyInfo.kind1}"/>
  		 	   <c:set var="codeSel"		    value="kind1Sel"/>
  		 	   <c:set var="codeList"		value="${BonusPenaltyCode}"/>
  		 	<td colspan="4" align="left" valign="middle">
  		 	   <%@ include file="/pages/studaffair/include/Code5Select.jsp" %>
  		 	   <c:set var="tcnt" value="${tcnt + 1}"/>
  		 	次數：<input type="text" id="${tcnt}" name="cnt1" size="1" onKeyUp="nextfocus(${tcnt+1});">
        	</td>
        	   <c:set var="tcnt" value="${tcnt + 1}"/>  
        </tr>
        <tr>   
        	<td width="50" align="left" valign="middle">種類2：</td>
  		 	   <c:set var="codeId" 		    value="kind2"/>
  		 	   <c:set var="codeIdInitValue" value="${StudBonusPenaltyInfo.kind2}"/>
  		 	   <c:set var="codeSel"		    value="kind2Sel"/>
  		 	   <c:set var="codeList"		value="${BonusPenaltyCode}"/>
  		 	<td  align="left" valign="middle">
  		 	   <%@ include file="/pages/studaffair/include/Code5Select.jsp" %>
  		 	   <c:set var="tcnt" value="${tcnt + 1}"/>
        	次數：<input type="text" id="${tcnt}" name="cnt2" size="1" onKeyUp="nextfocus(${tcnt+1});">
        	</td>
        	   <c:set var="tcnt" value="${tcnt + 1}"/>     
        </tr>
        <tr>
        	<td align="left" valign="middle" id="numtd1">人數：</td>
        	<td  align="left" valign="middle" id="numtd2">
        		<input type="text" id="${tcnt}" size=2 maxlength=3 name="number" onKeyUp="nextfocus(${tcnt+1});">
        		<c:set var="tcnt" value="${tcnt + 1}"/>
        	</td>
		</tr>
		</table>
	  </td>
	</tr>
	<tr>
	  <td>
		<table>
			<tr>
			<td colspan="2" id="students">&nbsp;</td>
			</tr>
		</table>
	  </td>
	</tr>
	<input type="hidden" name="opmode" value="" id="opmode">	
	<script>
	generateTableBanner('<INPUT type="button" name="method" value="<bean:message key='OK'/>" id="ok" onClick="chgopmode(1);document.forms[0].submit();return true;">&nbsp;&nbsp;'+
	'<INPUT type="button" name="method" value="<bean:message key='Cancel'/>" id="cancel" onClick="chgopmode(0);document.forms[0].submit();">');
	</script>

<%@ include file="/pages/studaffair/include/studBPGetStudInfo.js" %>
<script language="javascript">
//<!--
	var iplimit = ${tcnt-1};
		
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
				getStudentInfo(ntab-1);
			} else if(thisElem.name=="number") {
				generateInput();
			}
			if(ntab <= iplimit) {
				nextElem = document.getElementById(ntab);
				nextElem.focus();
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
	
	function fsubmit(){
		alert("fsubmit");
		document.bpForm.submit();
	}
	
	function buttonctl(mode){
		if(mode=="enable") {
			document.getElementById("ok").disabled = false;
			document.getElementById("cancel").disabled = false;
		}
		if(mode=="disable") {
			document.getElementById("ok").disabled = true;
			document.getElementById("cancel").disabled = true;
		}
	}
	
	function generateInput() {
		var numberObj = document.getElementsByName("number");
		var confirmtd = document.getElementById("confirmtd");
		var numtd1 = document.getElementById("numtd1");
		var numtd2 = document.getElementById("numtd2");
		var count = numberObj[0].value;
		var inputObj = document.getElementById("students");
		var tcnt = ${tcnt};
		var htmlString = "";
		
		if(!isNaN(count)) {
			if(count==1) {
				htmlString = '<table width="100%" cellpadding="2" cellspacing="3">';
				htmlString = htmlString + '<tr>';
				htmlString = htmlString + '<td width="15%" align="center" bgcolor="#add8e6">學號</td>';
				htmlString = htmlString + '<td width="15%" align="center" bgcolor="#add8e6">姓名</td>';
				htmlString = htmlString + '<td width="15%" align="center" bgcolor="#add8e6">班級</td>';
				htmlString = htmlString + '<td>&nbsp;</td>';
				htmlString = htmlString + '</tr>';
				htmlString = htmlString + '<tr>';
				htmlString = htmlString + '<td width="15%" bgcolor="#add8e6">';
				htmlString = htmlString + '<input type="text" name="studentNo" size="9" maxlength="9" id="' + tcnt + '" onKeyUp="nextfocus(' + (tcnt+1) + ');"></td>';
				htmlString = htmlString + '<td width="15%" bgcolor="#add8e6">';
				htmlString = htmlString + '<input type="text" name="studentName" size="8" maxlength="8" id="name' + tcnt + '"></td>';
				htmlString = htmlString + '<td width="15%" bgcolor="#add8e6">';
				htmlString = htmlString + '<input type="text" name="departClass" size="8" maxlength="8" id="class' + tcnt + '"></td>';
				htmlString = htmlString + '<td>&nbsp;</td>';
				htmlString = htmlString + '</tr>';
				htmlString = htmlString + '</table>';
				htmlString = htmlString + '<br><center>';
				//htmlString = htmlString + '<INPUT type="button" name="method" value="確定" onClick="alert(this.form.name);this.form.submit();">&nbsp;&nbsp;';
				//htmlString = htmlString + '<INPUT type="button" name="method" value="取消" onClick="alert(this.form.name);this.form.submit();"></center>';
				inputObj.innerHTML = htmlString;
				//numtd1.innerHTML='&nbsp;';
				//numtd2.innerHTML='&nbsp;';
				tcnt++;
				iplimit++;
				buttonctl('enable');
			} else if(count > 0) {
				htmlString = '<table width="100%" cellpadding="2" cellspacing="3">';
				htmlString = htmlString + '<tr>';
				htmlString = htmlString + '<td width="15%" align="center" bgcolor="#add8e6">學號</td>';
				htmlString = htmlString + '<td width="15%" align="center" bgcolor="#add8e6">姓名</td>';
				htmlString = htmlString + '<td width="15%" align="center" bgcolor="#add8e6">班級</td>';
				htmlString = htmlString + '<td width="15%" align="center" bgcolor="#99ee90">學號</td>';
				htmlString = htmlString + '<td width="15%" align="center" bgcolor="#99ee90">姓名</td>';
				htmlString = htmlString + '<td width="15%" align="center" bgcolor="#99ee90">班級</td>';
				htmlString = htmlString + '<td>&nbsp;</td>';
				htmlString = htmlString + '</tr>';
				for(i=0; i<count; i++) {
					if(i%2 == 0){
						htmlString = htmlString + '<tr>';
						htmlString = htmlString + '<td width="15%" bgcolor="#add8e6">';
						htmlString = htmlString + '<input type="text" name="studentNo" size="9" maxlength="9" id="' + tcnt + '" onKeyUp="nextfocus(' + (tcnt+1) + ');"></td>';
						htmlString = htmlString + '<td width="15%" bgcolor="#add8e6">';
						htmlString = htmlString + '<input type="text" name="studentName" size="8" maxlength="8" id="name' + tcnt + '"></td>';
						htmlString = htmlString + '<td width="15%" bgcolor="#add8e6">';
						htmlString = htmlString + '<input type="text" name="departClass" size="8" maxlength="8" id="class' + tcnt + '"></td>';
						
					} else {
						htmlString = htmlString + '<td width="15%" bgcolor="#99ee90">';
						htmlString = htmlString + '<input type="text" name="studentNo" size="9" maxlength="9" id="' + tcnt + '" onKeyUp="nextfocus(' + (tcnt+1) + ');"></td>';
						htmlString = htmlString + '<td width="15%" bgcolor="#99ee90">';
						htmlString = htmlString + '<input type="text" name="studentName" size="8" maxlength="8" id="name' + tcnt + '"></td>';
						htmlString = htmlString + '<td width="15%" bgcolor="#99ee90">';
						htmlString = htmlString + '<input type="text" name="departClass" size="8" maxlength="8" id="class' + tcnt + '"></td>';
						htmlString = htmlString + '<td>&nbsp;</td>';
						htmlString = htmlString + '</tr>';
					}
					tcnt++;
					iplimit++;
				}
				if(i%2 == 0) {
					htmlString = htmlString + '<td>&nbsp;</td>';
					htmlString = htmlString + '</tr>';
				}
				htmlString = htmlString + '</table>';
				htmlString = htmlString + '<br><center>';
				//htmlString = htmlString + '<INPUT type="button" name="method" value="確定" onClick="alert(this.form.name);this.form.submit();">&nbsp;&nbsp;';
				//htmlString = htmlString + '<INPUT type="button" name="method" value="取消" onClick="alert(this.form.name);this.form.submit();"></center>';
				inputObj.innerHTML = htmlString;
				//numtd1.innerHTML='&nbsp;';
				//numtd2.innerHTML='&nbsp;';
				buttonctl('enable');
			}
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
//-->
</script>
</form>
<script>history.go(1);buttonctl('disable');</script>

