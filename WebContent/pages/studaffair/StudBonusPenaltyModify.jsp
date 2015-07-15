<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<style type="text/css">
<!--
.input {
	width:20px;
}
.input30 {
	width:30px;
}
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.style2 {color: #FF0000}
.table1 {
	border: 1px solid #996699;
	display: table-cell;
}
-->
</style>
<script type="text/javascript">
<!--
function lockInput(tagId)
{
	var obj=document.getElementById(tagId);
	obj.readOnly=true;
}
// -->
</script>


<form action="/CIS/StudAffair/StudBonusPenaltyModify.do" method="post" name="bpForm">

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<input type="hidden" name="mode" value="${StudTimeOffEditInfo.mode}">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.StudBonusPenaltyModify" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td align="center">
<!-- End Content Page Table Header -->
	<br>
	<table cellspacing="0" border="1" class="table1">
      	<tr>
        	<td colspan="17" align="left" valign="middle">${BonusPenaltyStuMap.studentNo}&nbsp;
			<c:out value="${BonusPenaltyStuMap.studentName}" />&nbsp;&nbsp;&nbsp;&nbsp;
			<c:out value="${BonusPenaltyStuMap.departClass}"/>&nbsp;
			<c:out value="${BonusPenaltyStuMap.deptClassName}"/>
			</td>
		</tr>
      <tr>
      	<td width="25%">日期</td>
	 	<td width="13%" align="center" valign="middle">文號</td>
		<td width="13%" align="center" valign="middle">原因</td>
		<td width="13%" align="center" valign="middle">種類1</td>
		<td width="10%" align="center" valign="middle">次數1</td>
		<td width="10%" align="center" valign="middle">種類2</td>
		<td width="10%" align="center" valign="middle">次數2</td>
		<td>&nbsp;</td>
      </tr>
      <c:set var="tcnt" value="1"/>
      
      <c:choose>
      	<c:when test="${StudBonusPenaltyModify != null}">
      	<c:set var="bpYear" value="${StudBonusPenaltyModify.bpYear}"/>
      	<c:set var="bpMonth" value="${StudBonusPenaltyModify.bpMonth}"/>
      	<c:set var="bpDay" value="${StudBonusPenaltyModify.bpDay}"/>
      	<c:set var="docNo" value="${StudBonusPenaltyModify.docNo}"/>
      	<c:set var="reason" value="${StudBonusPenaltyModify.reason}"/>
      	<c:set var="kind1" value="${StudBonusPenaltyModify.kind1}"/>
       	<c:set var="cnt1" value="${StudBonusPenaltyModify.cnt1}"/>
      	<c:set var="kind2" value="${StudBonusPenaltyModify.kind2}"/>
      	<c:set var="cnt2" value="${StudBonusPenaltyModify.cnt2}"/>
     	<c:set var="rcnt" value="0"/>
     	
      	<c:forEach begin="0" end="${fn:length(docNo)-1}">
      	
      	<tr>
      	<td>
      	   	<input type="text" size="3" id="${tcnt}" class="input30" name="bpYear" value="${bpYear[rcnt]}" onKeyUp="nextfocus(${tcnt+1});">年
         	<c:set var="tcnt" value="${tcnt + 1}"/>
      	   	<input type="text" size="3" id="${tcnt}" class="input30" name="bpMonth" value="${bpMonth[rcnt]}" onKeyUp="nextfocus(${tcnt+1});">月
         	<c:set var="tcnt" value="${tcnt + 1}"/>
      	   	<input type="text" size="3" id="${tcnt}" class="input30" name="bpDay" value="${bpDay[rcnt]}" onKeyUp="nextfocus(${tcnt+1});">日
         	<c:set var="tcnt" value="${tcnt + 1}"/>
      	</td>
      	<td align="center" valign="middle">
      	 	<input type="text" size="8" name="docNo" value="${docNo[rcnt]}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="3" name="reason" value="${reason[rcnt]}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="kind1" value="${kind1[rcnt]}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="cnt1" value="${cnt1[rcnt]}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="kind2" value="${kind2[rcnt]}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="cnt2" value="${cnt2[rcnt]}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">&nbsp;</td>
      	
      	</tr>
         <c:set var="rcnt" value="${rcnt + 1}"/>
      	</c:forEach>
      	</c:when>
      	
      	<c:when test="${StudBonusPenaltyInEdit != null}">
      	<c:forEach items="${StudBonusPenaltyInEdit}" var="bpItems">
      	<c:set var="bpdate" value="${fn:split(bpItems.sddate, '-')}"/>
      	<tr>
      	<td>
      	   	<input type="text" size="3" id="${tcnt}" class="input30" name="bpYear" value="${bpdate[0]}" onKeyUp="nextfocus(${tcnt+1});">年
         	<c:set var="tcnt" value="${tcnt + 1}"/>
      	   	<input type="text" size="3" id="${tcnt}" class="input30" name="bpMonth" value="${bpdate[1]}" onKeyUp="nextfocus(${tcnt+1});">月
         	<c:set var="tcnt" value="${tcnt + 1}"/>
      	   	<input type="text" size="3" id="${tcnt}" class="input30" name="bpDay" value="${bpdate[2]}" onKeyUp="nextfocus(${tcnt+1});">日
         	<c:set var="tcnt" value="${tcnt + 1}"/>
      	</td>
      	<td align="center" valign="middle">
      	 	<input type="text" size="8" name="docNo" value="${bpItems.no}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="3" name="reason" value="${bpItems.reason}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="kind1" value="${bpItems.kind1}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="cnt1" value="${bpItems.cnt1}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="kind2" value="${bpItems.kind2}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="cnt2" value="${bpItems.cnt2}" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">&nbsp;</td>
      	 </tr>
      	 </c:forEach>
      	</c:when>
      </c:choose>
      
	</table>
	<br>
	</td>
	</tr>
	<script>
	generateTableBanner('<INPUT type="button" name="method" value="<bean:message key='OK'/>" id="ok" onClick="chgopmode(1);document.forms[0].submit();return true;">&nbsp;&nbsp;'+
	'<INPUT type="button" name="method" value="<bean:message key='Cancel'/>" id="cancel" onClick="chgopmode(0);document.forms[0].submit();">');
	</script>
</table>
<input type="hidden" name="opmode" value="" id="opmode">	
</form>
<script>history.go(1);</script>
<script language="javascript">
//<!--
	var iplimit = ${tcnt-1};
	var	editmode='modify';
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
				if(editmode=='modify') nextElem.select();
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
	
	function chgopmode(mode){
		var obj = document.getElementById("opmode");
		if(mode==1) {
			obj.value="ok";
		}else if(mode==0) {
			obj.value="cancel";
		}
	};
//-->
</script>
