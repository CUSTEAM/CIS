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

<form action="/CIS/StudAffair/StudBonusPenaltyOneClass.do" method="post" name="bpForm">
<input type="hidden" name="mode" value="${classBPInfo.mode}">

<c:set var="tcnt" value="1"/>

<table width="100%" cellpadding="0" cellspacing="0">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="sbpTitle.StudBonusPenaltyOneClass" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td align="left"> 
<!-- End Content Page Table Header -->
	
	<c:if test="${classBPInfo.mode == 'edit'}">
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
        	<td align="left" valign="middle">班別：</td>
        	<td  align="left" valign="middle">
	  		<c:set var="campusSel" value="${classBPInfo.campusInChargeSAF}"/>
	  		<c:set var="schoolSel" value="${classBPInfo.schoolInChargeSAF}"/>
	  		<c:set var="deptSel"   value="${classBPInfo.deptInChargeSAF}"/>
	  		<c:set var="classSel"  value="${classBPInfo.classInChargeSAF}"/>
			<%@ include file="/pages/studaffair/include/ClassSelectSAF.jsp" %>
        	</td>
		</tr>
		
		</table>
	  </td>
	</tr>
	<input type="hidden" name="opmode" value="" id="opmode">	
	<script>
	generateTableBanner('<INPUT type="button" name="method" value="<bean:message key='OK'/>" id="ok" onClick="chgopmode(1);document.forms[0].submit();return true;">&nbsp;&nbsp;'+
	'<INPUT type="button" name="method" value="<bean:message key='Cancel'/>" id="cancel" onClick="chgopmode(0);document.forms[0].submit();">');
	</script>
	</c:if>

	<c:if test="${classBPInfo.mode == 'save'}">
	<table cellspacing="5" class="empty-border" width="100%">
		<tr>
			<td align="left" valign="middle">
				日期：民國${classBPInfo.bpYear}年&nbsp;&nbsp;${classBPInfo.bpMonth}月&nbsp;&nbsp;${classBPInfo.bpDay}日&nbsp;&nbsp;
        	</td>
        </tr>
        <tr>
        	<td align="left" valign="middle">
        		文號：${classBPInfo.docNo}
        	</td>
        </tr>

        <tr>
        	<td align="left" valign="middle">
        		原因：${classBPInfo.reason}&nbsp;&nbsp;${classBPInfo.reasonName}
        	</td>
        </tr>
        <tr>
        	<td align="left" valign="middle">
  		 	   	種類1：${classBPInfo.kind1Name}&nbsp;&nbsp;次數：${classBPInfo.cnt1}
        	</td>
        </tr>
        <tr>   
        	<td align="left" valign="middle">
        		種類2：${classBPInfo.kind2Name}&nbsp;&nbsp;次數：${classBPInfo.cnt2}
        	</td>
        </tr>
	    <%@include file="/pages/studaffair/include/Displaytag4Checkbox.inc"%>
		<tr>
		    <td><table width="50%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${classBPStudents}" export="false" id="row" pagesize="0" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty classBPStudents}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
	        	<display:column title="<script>generateTriggerAll(${fn:length(classBPStudents)}, 'classBPStudents');</script>" class="center" >
	          	<script>generateCheckbox("${row.oid}", "classBPStudents");</script></display:column>
 	        	<display:column title="學號"		property="studentNo"		sortable="true" 	class="left" />
 	        	<display:column title="姓名"		property="studentName"		sortable="true"  	class="center" />
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>
		
		</table>
	  </td>
	</tr>
	<input type="hidden" name="opmode" value="" id="opmode">	
	<script>
	generateTableBanner('<INPUT type="button" name="method" value="<bean:message key='OK'/>" id="ok" onClick="chgopmode(2);document.forms[0].submit();return true;">&nbsp;&nbsp;'+
	'<INPUT type="button" name="method" value="<bean:message key='Cancel'/>" id="cancel" onClick="chgopmode(0);document.forms[0].submit();">');
	</script>
	</c:if>

</form>
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
	
	
	function chgopmode(mode){
		var obj = document.getElementById("opmode");
		if(mode==1) {
			obj.value="ok";
		}else if(mode==0) {
			obj.value="cancel";
		}else if(mode==2) {
			obj.value="save";
		}
	}
//-->
</script>
<script>history.go(1);</script>

