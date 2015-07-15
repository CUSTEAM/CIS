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

<form action="/CIS/StudAffair/StudInspectedAdd.do" method="post" name="bpForm">
<input type="hidden" name="opmode" value="" id="opmode">	

<c:set var="tcnt" value="1"/>

<table width="100%" cellpadding="0" cellspacing="0">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.StudInspectedAdd" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td align="left"> 
<!-- End Content Page Table Header -->

	<table cellspacing="5" class="empty-border" width="100%">
	<c:choose>
	<c:when test="${StudInspectedEdit != null}">
		<tr>
			<td width="80">學號 ：</td>
			<td width="80">
				<input name="studentNo" value="${StudInspectedEdit.studentNo}" id="${tcnt}" type="text" onKeyUp="nextfocus(${tcnt+1});">
			</td>
			<td align="left" valign="middle" id="student">
       			&nbsp;&nbsp;${StudInspectedEdit.studentName}
       			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${StudInspectedEdit.departClass}
       			&nbsp;&nbsp;${StudInspectedEdit.deptClassName}
        	</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">定察年度 ：</td>
			<td colspan="2">
				<input name="downYear" value="${StudInspectedEdit.downYear}" id="${tcnt}" type="text" size="3" onKeyUp="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">定察學期 ：</td>
			<td colspan="2">
				<input name="downTerm" value="${StudInspectedEdit.downTerm}" id="${tcnt}" type="text" size="1" onKeyUp="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">註銷年度 ：</td>
			<td colspan="2">
				<input name="upYear" value="${StudInspectedEdit.upYear}" id="${tcnt}" type="text" size="3" onKeyUp="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">註銷學期 ：</td>
			<td colspan="2">
				<input name="upTerm" value="${StudInspectedEdit.upTerm}" id="${tcnt}" type="text" size="1" onKeyUp="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
	</c:when>
		
	<c:when test="${StudInspectedEdit == null}">
		<tr>
			<td width="80">學號 ：</td>
			<td width="100">
				<input name="studentNo" id="${tcnt}" type="text" onKeyUp="nextfocus(${tcnt+1});">
			</td>
			<td align="left" valign="middle" id="student">&nbsp;
        	</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">定察年度 ：</td>
			<td colspan="2">
				<input name="downYear" id="${tcnt}" type="text" size="3" onKeyUp="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">定察學期 ：</td>
			<td colspan="2">
				<input name="downTerm" id="${tcnt}" type="text" size="1" onKeyUp="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">註銷年度 ：</td>
			<td colspan="2">
				<input name="upYear" id="${tcnt}" type="text" size="3" onKeyUp="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">註銷學期 ：</td>
			<td colspan="2">
				<input name="upTerm" id="${tcnt}" type="text" size="1" onKeyUp="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
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
</form>

<%@ include file="/pages/studaffair/include/GetStudentInfo.js" %>
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

<script>getfocus(1);</script>
<script>history.go(1);</script>
