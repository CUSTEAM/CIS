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

<form action="/CIS/Score/ScoreSetDateAdd.do" method="post" name="sdForm">
<input type="hidden" name="opmode" value="" id="opmode">	

<c:set var="tcnt" value="1"/>

<table width="100%" cellpadding="0" cellspacing="0">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="Title.ScoreSetDateAdd" bundle="SCR"/></B></div>');</script>	  
	<tr>
		<td align="left"> 
<!-- End Content Page Table Header -->

	<table cellspacing="5" class="empty-border" width="100%">
	<c:choose>
	<c:when test="${ScoreSetDateEdit != null}">
		<tr>
			<td width="80">類別 ：</td>
			<td align="left" valign="middle">
	  			<c:set var="codeId" 		 	value="level"/>
	  			<c:set var="codeIdInitValue" 	value="${ScoreSetDateEdit.level}"/>
	  			<c:set var="codeSel"		 	value="levelSel"/>
	  			<c:set var="codeList"			value="${setDateLevel}"/>
	  			<%@ include file="/pages/studaffair/include/Code5Select.jsp" %>
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">學制 ：</td>
			<td align="left" valign="middle">
	  		<c:set var="codeId" 		 	value="depart"/>
	  		<c:set var="codeIdInitValue" 	value="${ScoreSetDateEdit.depart}"/>
	  		<c:set var="codeSel"		 	value="departSel"/>
	  		<c:set var="codeList"			value="${Department}"/>
	  		<%@ include file="/pages/studaffair/include/CodeSelect.jsp" %>
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">開始日期 ：</td>
			<td align="left" valign="middle">
				<input name="beginDate" value="${ScoreSetDateEdit.beginDate}" id="${tcnt}" type="text" size="9" maxlength="10" onKeyUp="nextfocus(${tcnt+1});" onBlur="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">開始時間 ：</td>
			<td align="left" valign="middle">
				<input name="beginTime" value="${ScoreSetDateEdit.beginTime}" id="${tcnt}" type="text" size="8" maxlength="8" onKeyUp="nextfocus(${tcnt+1});" onBlur="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">終止日期 ：</td>
			<td align="left" valign="middle">
				<input name="endDate" value="${ScoreSetDateEdit.endDate}" id="${tcnt}" type="text" size="9" maxlength="10" onKeyUp="nextfocus(${tcnt+1});" onBlur="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">終止時間 ：</td>
			<td align="left" valign="middle">
				<input name="endTime" value="${ScoreSetDateEdit.endTime}" id="${tcnt}" type="text" size="8" maxlength="8" onKeyUp="nextfocus(${tcnt+1});" onBlur="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
	</c:when>
		
	<c:when test="${ScoreSetDateEdit == null}">
		<tr>
			<td width="80">類別 ：</td>
			<td align="left" valign="middle">
	  			<c:set var="codeId" 		 	value="level"/>
	  			<c:set var="codeIdInitValue" 	value=""/>
	  			<c:set var="codeSel"		 	value="levelSel"/>
	  			<c:set var="codeList"			value="${setDateLevel}"/>
	  			<%@ include file="/pages/studaffair/include/Code5Select.jsp" %>
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">學制 ：</td>
			<td align="left" valign="middle">
	  		<c:set var="codeId" 		 	value="depart"/>
	  		<c:set var="codeIdInitValue" 	value=""/>
	  		<c:set var="codeSel"		 	value="departSel"/>
	  		<c:set var="codeList"			value="${Department}"/>
	  		<%@ include file="/pages/studaffair/include/CodeSelect.jsp" %>
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">開始日期 ：</td>
			<td align="left" valign="middle">
				<input name="beginDate" value="" id="${tcnt}" type="text" size="9" maxlength="10" onKeyUp="nextfocus(${tcnt+1});" onBlur="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">開始時間 ：</td>
			<td align="left" valign="middle">
				<input name="beginTime" value="" id="${tcnt}" type="text" size="8" maxlength="8" onKeyUp="nextfocus(${tcnt+1});" onBlur="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">終止日期 ：</td>
			<td align="left" valign="middle">
				<input name="endDate" value="" id="${tcnt}" type="text" size="9" maxlength="10" onKeyUp="nextfocus(${tcnt+1});" onBlur="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
		<tr>
			<td width="80">終止時間 ：</td>
			<td align="left" valign="middle">
				<input name="endTime" value="" id="${tcnt}" type="text" size="8" maxlength="8" onKeyUp="nextfocus(${tcnt+1});" onBlur="nextfocus(${tcnt+1});">
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
