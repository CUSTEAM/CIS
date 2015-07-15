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
.td-lightcyan	{
	background-color: #E0FFFF;
	font-weight: bold;
}
.td-lightgrey	{
	background-color: #D3D3D3;
	align:center;
}
-->
</style>

<form action="/CIS/Score/ScoreSetDateModify.do" method="post" name="inForm">
<input type="hidden" name="opmode" value="" id="opmode">	

<c:set var="tcnt" value="1"/>

<table width="100%" cellpadding="0" cellspacing="0">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="Title.ScoreSetDateModify" bundle="SCR"/></B></div>');</script>	  
	<tr>
		<td align="left"> 
<!-- End Content Page Table Header -->

	<table cellspacing="5" class="empty-border" width="100%">
		<tr>
		<td colspan="6"><font color="red">類別 (1:期中, 2:期末, 3:畢業, 4:暑修, 5:操行, 6:教學評量)<br>
		日期輸入格式:xx/xx/xx     時間輸入格式xx:xx:xx<br>
		學制輸入格式:11:台北日間 12:台北進修 13:台北學院 21:新竹日間 22:新竹進修</font>
		</td>
		</tr>
		<tr>
			<td class="td-lightgrey">類別</td>
			<td class="td-lightgrey">學制</td>
			<td class="td-lightgrey">開始日期</td>
			<td class="td-lightgrey">開始時間</td>
			<td class="td-lightgrey">終止日期</td>
			<td class="td-lightgrey">終止時間</td>
		</tr>
	<c:choose>
	<c:when test="${ScoreSetDateModify != null}">
		<c:forEach items="${ScoreSetDateModify}" var="dates">
		<tr class="td-lightcyan">
			<td align="left" valign="middle">
	  			${dates.levelName}
			</td>
			
			<td align="left" valign="middle">
	  			${dates.departName}
			</td>
			
			<td align="left" valign="middle">
				<input name="beginDate" value="${dates.beginDate}" id="${tcnt}" type="text" size="9" maxlength="10" onKeyUp="nextfocus(${tcnt+1});" onBlur="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
			
			<td align="left" valign="middle">
				<input name="beginTime" value="${dates.beginTime}" id="${tcnt}" type="text" size="8" maxlength="8" onKeyUp="nextfocus(${tcnt+1});" onBlur="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
			
			<td align="left" valign="middle">
				<input name="endDate" value="${dates.endDate}" id="${tcnt}" type="text" size="9" maxlength="10" onKeyUp="nextfocus(${tcnt+1});" onBlur="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>

			<td align="left" valign="middle">
				<input name="endTime" value="${dates.endTime}" id="${tcnt}" type="text" size="8" maxlength="8" onKeyUp="nextfocus(${tcnt+1});" onBlur="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
        </c:forEach>
	</c:when>
		
	<c:when test="${ScoreSetDateInEdit != null}">
		<c:forEach items="${ScoreSetDateInEdit}" var="dates">
		<tr class="td-lightcyan">
			<td align="left" valign="middle">
	  			${dates.levelName}
			</td>
			
			<td align="left" valign="middle">
	  			${dates.departName}
			</td>
			
			<td align="left" valign="middle">
				<input name="beginDate" value="${dates.beginDate}" id="${tcnt}" type="text" size="9" maxlength="10" onKeyUp="nextfocus(${tcnt+1});" onBlur="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
			
			<td align="left" valign="middle">
				<input name="beginTime" value="${dates.beginTime}" id="${tcnt}" type="text" size="8" maxlength="8" onKeyUp="nextfocus(${tcnt+1});" onBlur="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
			
			<td align="left" valign="middle">
				<input name="endDate" value="${dates.endDate}" id="${tcnt}" type="text" size="9" maxlength="10" onKeyUp="nextfocus(${tcnt+1});" onBlur="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>

			<td align="left" valign="middle">
				<input name="endTime" value="${dates.endTime}" id="${tcnt}" type="text" size="8" maxlength="8" onKeyUp="nextfocus(${tcnt+1});" onBlur="nextfocus(${tcnt+1});">
			</td>
			<c:set var="tcnt" value="${tcnt + 1}"/>
        </tr>
        </c:forEach>
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