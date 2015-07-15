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

<form action="/CIS/StudAffair/StudBonusPenaltyEdit.do" method="post" name="bpForm" onSubmit="alert('submit!');">

<c:set var="tcnt" value="1"/>

<table width="100%" cellpadding="0" cellspacing="0">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.StudTimeBonusPenaltyEdit" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td align="left"> 
<!-- End Content Page Table Header -->

	<table cellspacing="5" class="empty-border" width="100%">
	
	<c:if test="${StudBonusPenaltyEdit != null}">
		<tr>
			<td align="left" valign="middle" width="50">日期：</td>
			<td align="left" valign="middle">民國
       			<input name="bpYear" id="${tcnt}" type="text" size="3" maxlength="3" value="${StudBonusPenaltyEdit.bpYear}" onKeyUp="nextfocus(${tcnt+1});">年&nbsp;&nbsp;
				<c:set var="tcnt" value="${tcnt + 1}"/>
            	<input name="bpMonth" id="${tcnt}" type="text" size="2" maxlength="3" value="${StudBonusPenaltyEdit.bpMonth}" onKeyUp="nextfocus(${tcnt+1});">月&nbsp;&nbsp;
				<c:set var="tcnt" value="${tcnt + 1}"/>
            	<input name="bpDay" id="${tcnt}" type="text" size="2" maxlength="3" value="${StudBonusPenaltyEdit.bpDay}" onKeyUp="nextfocus(${tcnt+1});">日&nbsp;&nbsp;
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</td>
        </tr>
        <tr>
        	<td align="left" valign="middle">文號：</td>
        	<td align="left" valign="middle">
        		<input type="text" name="docNo" value="${StudBonusPenaltyEdit.docNo}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
        	</td>
        	<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
        <tr>
        	<td align="left" valign="middle">原因：</td>
        	<td align="left" valign="middle">
  		 	   <c:set var="codeId" 		    value="reason"/>
  		 	   <c:set var="codeIdInitValue" value="${StudBonusPenaltyEdit.reason}"/>
  		 	   <c:set var="codeSel"		    value="reasonSel"/>
  		 	   <c:set var="codeList"		value="${BonusPenaltyReason}"/>
  		 	   <%@ include file="/pages/studaffair/include/CodeSelect.jsp" %>
  		 	   <c:set var="tcnt" value="${tcnt + 1}"/>
        	</td>
        </tr>
		</table>
	  </td>
	</tr>
	<tr>
	  <td>
		<table width="100%">
			<tr>
			<td id="students">
			<table width="100%" cellpadding="2" cellspacing="3">
				<tr>
				<td width="13%" align="center" bgcolor="#add8e6">學號</td>
				<td width="13%" align="center" bgcolor="#add8e6">姓名</td>
				<td width="13%" align="center" bgcolor="#add8e6">班級</td>
				<td width="10%" align="center" bgcolor="#99ee90">種類1：</td>
				<td width="10%" align="center" bgcolor="#99ee90">次數：</td>
				<td width="10%" align="center" bgcolor="#99ee90">種類2：</td>
				<td width="10%" align="center" bgcolor="#99ee90">次數：</td>
				<td>&nbsp;</td>
				</tr>
				<c:set var="studentNo" value="${StudBonusPenaltyEdit.studentNo}"/>
				<c:set var="studentName" value="${StudBonusPenaltyEdit.studentName}"/>
				<c:set var="departClass" value="${StudBonusPenaltyEdit.departClass}"/>
				<c:set var="kind1" value="${StudBonusPenaltyEdit.kind1}"/>
       			<c:set var="cnt1" value="${StudBonusPenaltyEdit.cnt1}"/>
      			<c:set var="kind2" value="${StudBonusPenaltyEdit.kind2}"/>
      			<c:set var="cnt2" value="${StudBonusPenaltyEdit.cnt2}"/>
      			
     			<c:set var="rcnt" value="0"/>
     			
      			<c:forEach begin="1" end="${fn:length(studentNo)}">
				<tr>
				<td width="13%" bgcolor="#add8e6">
				<input type="text" name="studentNo" size="9" maxlength="9" id="${tcnt}" value="${studentNo[rcnt]}" onKeyUp="nextfocus(${tcnt+1});"></td>
				<td width="13%" bgcolor="#add8e6">
				<input type="text" name="studentName" size="8" maxlength="8" id="name${tcnt}" value="${studentName[rcnt]}"></td>
				<td width="13%" bgcolor="#add8e6">
				<input type="text" name="departClass" size="8" maxlength="8" id="class${tcnt}" value="${departClass[rcnt]}"></td>
        		<td width="10%" align="center" valign="middle" bgcolor="#99ee90">
        		<c:set var="tcnt" value="${tcnt + 1}"/>
      
   		 	    <INPUT type="text" id="${tcnt}" name="kind1" size="1"  value="${kind1[rcnt]}"onKeyUp="nextfocus(${tcnt+1});"></td>
  		 	   	<c:set var="tcnt" value="${tcnt + 1}"/>
        		<td width="10%" align="center" valign="middle" bgcolor="#99ee90">
  		 		<input type="text" id="${tcnt}" name="cnt1" size="1"  value="${cnt1[rcnt]}"onKeyUp="nextfocus(${tcnt+1});">
        		</td>
        		<c:set var="tcnt" value="${tcnt + 1}"/>
        		<td width="10%"  align="center" valign="middle" bgcolor="#99ee90">
   		 	   	<INPUT type="text" id="${tcnt}" name="kind2" size="1" value="${kind2[rcnt]}" onKeyUp="nextfocus(${tcnt+1});"></td>
  		 	   	<c:set var="tcnt" value="${tcnt + 1}"/>
        		<td width="10%" align="center" valign="middle" bgcolor="#99ee90">
  		 		<input type="text" id="${tcnt}" name="cnt2" size="1" value="${cnt2[rcnt]}" onKeyUp="nextfocus(${tcnt+1});">
        		</td>
        		<c:set var="tcnt" value="${tcnt + 1}"/>
				<td>&nbsp;</td>	
				</tr>
				<c:set var="rcnt" value="${rcnt+1}"/>
				</c:forEach>
				</table>
				<br><center>
				
			</td>
			</tr>
		</table>
	  </td>
	</tr>
		
	</c:if>
		
	<c:if test="${StudBonusPenaltyEdit == null}">
		<tr>
			<td align="left" valign="middle" width="50">日期：</td>
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
        	<td align="left" valign="middle">
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
        <!-- 
        <tr>
        	<td align="left" valign="middle" id="numtd1">人數：</td>
        	<td  align="left" valign="middle" id="numtd2">
        		<input type="text" id="${tcnt}" size=2 maxlength=3 name="number" onKeyUp="nextfocus(${tcnt+1});">
        	</td>
		</tr>
		 -->
		 <input type="hidden" name="number" value="1">
		</table>
	  </td>
	</tr>
	<tr>
	  <td>
		<table width="100%">
			<tr>
			<td id="students">&nbsp;</td>
			</tr>
		</table>
	  </td>
	</tr>
	</c:if>
	
	<input type="hidden" name="opmode" value="" id="opmode">	
	<script>
	generateTableBanner('<INPUT type="button" name="method" value="<bean:message key='OK'/>" id="ok" onClick="chgopmode(1);document.forms[0].submit();return true;">&nbsp;&nbsp;'+
	'<INPUT type="button" name="method" value="<bean:message key='Cancel'/>" id="cancel" onClick="chgopmode(0);document.forms[0].submit();">');
	//generateTableBanner('<INPUT type="button" name="method" value="<bean:message key='Cancel'/>" id="cancel" onClick="chgopmode(0);document.forms[0].submit();">');
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
	
	function generateInput() {
		var numberObj = document.getElementsByName("number");
		var confirmtd = document.getElementById("confirmtd");
		var numtd1 = document.getElementById("numtd1");
		var numtd2 = document.getElementById("numtd2");
		var count = numberObj[0].value;
		var inputObj = document.getElementById("students");
		var tcnt = ${tcnt};
		var htmls = "";
		//alert("tcnt:" + tcnt);
		if(!isNaN(count)) {
			if(count > 0) {
				htmls = '<table width="100%" cellpadding="2" cellspacing="3">';
				htmls = htmls + '<tr>';
				htmls = htmls + '<td width="13%" align="center" bgcolor="#add8e6">學號</td>';
				htmls = htmls + '<td width="13%" align="center" bgcolor="#add8e6">姓名</td>';
				htmls = htmls + '<td width="13%" align="center" bgcolor="#add8e6">班級</td>';
				htmls = htmls + '<td width="10%" align="center" bgcolor="#99ee90">種類1：</td>';
				htmls = htmls + '<td width="10%" align="center" bgcolor="#99ee90">次數：</td>';
				htmls = htmls + '<td width="10%" align="center" bgcolor="#99ee90">種類2：</td>';
				htmls = htmls + '<td width="10%" align="center" bgcolor="#99ee90">次數：</td>';
				htmls = htmls + '<td>&nbsp;</td>';
				htmls = htmls + '</tr>';
				for(i=0; i<count; i++) {
					htmls = htmls + '<tr>';
					htmls = htmls + '<td width="13%" bgcolor="#add8e6">';
					htmls = htmls + '<input type="text" name="studentNo" size="9" maxlength="9" id="' + tcnt + '" onKeyUp="nextfocus(' + (tcnt+1) + ');"></td>';
					htmls = htmls + '<td width="13%" bgcolor="#add8e6">';
					htmls = htmls + '<input type="text" name="studentName" size="8" maxlength="8" id="name' + tcnt + '"></td>';
					htmls = htmls + '<td width="13%" bgcolor="#add8e6">';
					htmls = htmls + '<input type="text" name="departClass" size="8" maxlength="8" id="class' + tcnt + '"></td>';
        			htmls = htmls + '<td width="10%" align="center" valign="middle" bgcolor="#99ee90">';
        			tcnt++;iplimit++;
   		 	   		htmls = htmls + '<INPUT type="text" id="' + tcnt + '" name="kind1" size="1" onKeyUp="nextfocus(' + (tcnt+1) + ');"></td>';
  		 	   		tcnt++;iplimit++;
        			htmls = htmls + '<td width="10%" align="center" valign="middle" bgcolor="#99ee90">';
  		 			htmls = htmls + '<input type="text" id="' + tcnt + '" name="cnt1" size="1" onKeyUp="nextfocus(' + (tcnt+1) + ');">';
        			htmls = htmls + '</td>';
        			tcnt++;iplimit++;
        			htmls = htmls + '<td width="10%"  align="center" valign="middle" bgcolor="#99ee90">';
   		 	   		htmls = htmls + '<INPUT type="text" id="' + tcnt + '" name="kind2" size="1" onKeyUp="nextfocus(' + (tcnt+1) + ');"></td>';
  		 	   		tcnt++;iplimit++;
        			htmls = htmls + '<td width="10%" align="center" valign="middle" bgcolor="#99ee90">';
  		 			htmls = htmls + '<input type="text" id="' + tcnt + '" name="cnt2" size="1" onKeyUp="nextfocus(' + (tcnt+1) + ');">';
        			htmls = htmls + '</td>';
        			tcnt++;iplimit++;
					htmls = htmls + '<td>&nbsp;</td>';	
					htmls = htmls + '</tr>';
				}
				htmls = htmls + '</table>';
				htmls = htmls + '<br><center>';
				inputObj.innerHTML = htmls;
				//buttonctl('enable');
			}
		}
		
	}
	
	
//-->
</script>
</form>

<c:if test="${StudBonusPenaltyEdit != null}">
<script>
//buttonctl('enable');
locktags('studentName');locktags('departClass');getfocus(6);</script>
</c:if>	

<c:if test="${StudBonusPenaltyEdit == null}">
<script>generateInput();getfocus(1);</script>
</c:if>
<script>history.go(1);</script>