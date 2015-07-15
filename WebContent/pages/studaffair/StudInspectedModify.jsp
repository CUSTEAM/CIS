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

<form action="/CIS/StudAffair/StudInspectedModify.do" method="post" name="inForm">
<input type="hidden" name="opmode" value="" id="opmode">	

<c:set var="tcnt" value="1"/>

<table width="100%" cellpadding="0" cellspacing="0">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.StudInspectedModify" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td align="left"> 
<!-- End Content Page Table Header -->

	<table cellspacing="5" class="empty-border" width="100%">
	<c:choose>
	<c:when test="${StudInspectedMdify != null}">
	<input type="hidden" name="studentNo" value="${StudInspectedMdify.studentNo}" id="studentNo">
		<tr>
			<td width="10%">學號 ：</td>
			<td width="50%">
				${StudInspectedMdify.studentNo}
       			&nbsp;&nbsp;${StudInspectedMdify.studentName}
       			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${StudInspectedMdify.departClass}
       			&nbsp;&nbsp;${StudInspectedMdify.deptClassName}
        	</td>
        	<td>&nbsp;</td>
        </tr>
		<tr>
			<td width="10%" align="left">定察年度 ：</td>
			<td width="50%" align="left">
				<input name="downYear" value="${StudInspectedMdify.downYear}" id="${tcnt}" type="text" size="3" onKeyUp="nextfocus(${tcnt+1});">
			</td>
       		<td>&nbsp;</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="10%" align="left">定察學期 ：</td>
			<td width="50%" align="left">
				<input name="downTerm" value="${StudInspectedMdify.downTerm}" id="${tcnt}" type="text" size="1" onKeyUp="nextfocus(${tcnt+1});">
			</td>
       		<td>&nbsp;</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="10%" align="left">註銷年度 ：</td>
			<td width="50%" align="left">
				<input name="upYear" value="${StudInspectedMdify.upYear}" id="${tcnt}" type="text" size="3" onKeyUp="nextfocus(${tcnt+1});">
			</td>
       		<td>&nbsp;</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="10%" align="left">註銷學期 ：</td>
			<td width="50%" align="left">
				<input name="upTerm" value="${StudInspectedMdify.upTerm}" id="${tcnt}" type="text" size="1" onKeyUp="nextfocus(${tcnt+1});">
			</td>
       		<td>&nbsp;</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
	</c:when>
		
	<c:when test="${StudInspectedInEdit != null}">
	<input type="hidden" name="studentNo" value="${StudInspectedInEdit.studentNo}" id="studentNo">
		<tr>
			<td width="80">學號 ：</td>
			<td>
				${StudInspectedInEdit.studentNo}
       			&nbsp;&nbsp;${StudInspectedInEdit.studentName}
       			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${StudInspectedInEdit.departClass}
       			&nbsp;&nbsp;${StudInspectedInEdit.deptClassName}
       	</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">定察年度 ：</td>
			<td>
				<input name="downYear" value="${StudInspectedInEdit.downYear}" id="${tcnt}" type="text" size="3" onKeyUp="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">定察學期 ：</td>
			<td>
				<input name="downTerm" value="${StudInspectedInEdit.downTerm}" id="${tcnt}" type="text" size="1" onKeyUp="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">註銷年度 ：</td>
			<td>
				<input name="upYear" value="${StudInspectedInEdit.upYear}" id="${tcnt}" type="text" size="3" onKeyUp="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">註銷學期 ：</td>
			<td>
				<input name="upTerm" value="${StudInspectedInEdit.upTerm}" id="${tcnt}" type="text" size="1" onKeyUp="nextfocus(${tcnt+1});">
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
	</script>

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
<script>history.go(1);</script>
</form>