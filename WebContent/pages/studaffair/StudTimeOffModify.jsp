<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

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


<form action="/CIS/StudAffair/StudTimeOffModify.do" method="post" name="tfForm">

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<input type="hidden" name="mode" value="${StudTimeOffEditInfo.mode}">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="stfTitle.StudTimeOffCreate" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td align="center">
<!-- End Content Page Table Header -->
	<br>
	<table cellspacing="0" border="1" class="table1">
		<input type="hidden" name="daynite" value="${StudTimeOffEditInfo.daynite}">
	<tr>
		<td colspan="17" style="background-color: lightblue;">
			2:曠課 , 3:病假 , 4:事假 , 5:遲到早退 , 6:公假 , 7:喪假 , 8:婚假 , 9:產假, 1:重大傷病住院 
		</td>
	</tr>
      <tr>
        	<td colspan="17" align="left" valign="middle">${StudTimeOffEditInfo.studentNo}&nbsp;
			<c:out value="${StudTimeOffEditInfo.studentName}" />&nbsp;&nbsp;&nbsp;&nbsp;
			<c:out value="${StudTimeOffEditInfo.departClass}"/>&nbsp;
			<c:out value="${StudTimeOffEditInfo.deptClassName}"/>
			</td>
		</tr>
      <tr>
      	<td width="120">日期</td>
	 	<td width="30" align="center" valign="middle">升旗</td>
		<td width="30" align="center" valign="middle">１</td>
		<td width="30" align="center" valign="middle">２</td>
		<td width="30" align="center" valign="middle">３</td>
		<td width="30" align="center" valign="middle">４</td>
		<td width="30" align="center" valign="middle">５</td>
		<td width="30" align="center" valign="middle">６</td>
		<td width="30" align="center" valign="middle">７</td>
		<td width="30" align="center" valign="middle">８</td>
		<td width="30" align="center" valign="middle">９</td>
		<td width="30" align="center" valign="middle">10</td>
		<c:if test="${StudTimeOffEditInfo.daynite == '2'}">
        	<td width="30" align="center" valign="middle">N1</td>
        	<td width="30" align="center" valign="middle">N2</td>
        	<td width="30" align="center" valign="middle">N3</td>
        	<td width="30" align="center" valign="middle">N4</td>
        	<td width="30" align="center" valign="middle">N5</td>
        </c:if>
        <c:if test="${StudTimeOffEditInfo.daynite == '1'}">
        	<td width="30" align="center" valign="middle">11</td>
        	<td width="30" align="center" valign="middle">12</td>
        	<td width="30" align="center" valign="middle">13</td>
        	<td width="30" align="center" valign="middle">14</td>
        	<td width="30" align="center" valign="middle">15</td>
        </c:if>
      </tr>
      <c:set var="tcnt" value="1"/>
      
      <c:choose>
      	<c:when test="${StudTimeOffEdit != null}">
      	<c:set var="sddate" value="${StudTimeOffEdit.sddate}"/>
      	<c:set var="abs0" value="${StudTimeOffEdit.abs0}"/>
      	<c:set var="abs1" value="${StudTimeOffEdit.abs1}"/>
      	<c:set var="abs2" value="${StudTimeOffEdit.abs2}"/>
       	<c:set var="abs3" value="${StudTimeOffEdit.abs3}"/>
      	<c:set var="abs4" value="${StudTimeOffEdit.abs4}"/>
      	<c:set var="abs5" value="${StudTimeOffEdit.abs5}"/>
      	<c:set var="abs6" value="${StudTimeOffEdit.abs6}"/>
      	<c:set var="abs7" value="${StudTimeOffEdit.abs7}"/>
      	<c:set var="abs8" value="${StudTimeOffEdit.abs8}"/>
      	<c:set var="abs9" value="${StudTimeOffEdit.abs9}"/>
      	<c:set var="abs10" value="${StudTimeOffEdit.abs10}"/>
      	<c:set var="abs11" value="${StudTimeOffEdit.abs11}"/>
      	<c:set var="abs12" value="${StudTimeOffEdit.abs12}"/>
      	<c:set var="abs13" value="${StudTimeOffEdit.abs13}"/>
      	<c:set var="abs14" value="${StudTimeOffEdit.abs14}"/>
      	<c:set var="abs15" value="${StudTimeOffEdit.abs15}"/>
     	<c:set var="rcnt" value="0"/>
      	<c:forEach begin="0" end="${fn:length(sddate)}">
      	<input type="hidden" name="sddate" value="${sddate[rcnt]}">
      	<tr>
      	<td>${sddate[rcnt]}</td>
      	<td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs0" value="${abs0[rcnt]}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs1" value="${abs1[rcnt]}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs2" value="${abs2[rcnt]}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs3" value="${abs3[rcnt]}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs4" value="${abs4[rcnt]}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs5" value="${abs5[rcnt]}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs6" value="${abs6[rcnt]}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs7" value="${abs7[rcnt]}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs8" value="${abs8[rcnt]}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs9" value="${abs9[rcnt]}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs10" value="${abs10[rcnt]}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs11" value="${abs11[rcnt]}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs12" value="${abs12[rcnt]}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs13" value="${abs13[rcnt]}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs14" value="${abs14[rcnt]}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs15" value="${abs15[rcnt]}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	</tr>
         <c:set var="rcnt" value="${rcnt + 1}"/>
      	</c:forEach>
      	</c:when>
      	<c:when test="${StudTimeOffInEdit != null}">
      	<c:forEach items="${StudTimeOffInEdit}" var="tfItems">
      	<input type="hidden" name="sddate" value="${tfItems.sddate}">
      	<tr>
      	<td>${tfItems.sddate}</td>
      	<td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs0" value="${tfItems.abs0}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs1" value="${tfItems.abs1}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs2" value="${tfItems.abs2}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs3" value="${tfItems.abs3}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs4" value="${tfItems.abs4}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs5" value="${tfItems.abs5}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs6" value="${tfItems.abs6}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs7" value="${tfItems.abs7}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs8" value="${tfItems.abs8}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs9" value="${tfItems.abs9}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs10" value="${tfItems.abs10}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs11" value="${tfItems.abs11}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs12" value="${tfItems.abs12}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs13" value="${tfItems.abs13}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs14" value="${tfItems.abs14}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 <td align="center" valign="middle">
      	 	<input type="text" size="1" maxlength="1" name="abs15" value="${tfItems.abs15}" class="input" id="${tcnt}" onKeyUp="nextfocus(${tcnt+1});">
      	 </td>
         <c:set var="tcnt" value="${tcnt + 1}"/>
      	 </tr>
      	 </c:forEach>
      	</c:when>
      </c:choose>
      
	</table>
	<br>
	</td>
	</tr>
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='OK'/>" >&nbsp;&nbsp;'+
						'<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" >');
	</script>
</table>
</form>
<script>history.go(1);</script>
<script language="javascript">
//<!--
		var iplimit = ${tcnt-1};
		
		function nextfocus(ntab) {
		var code = event.keyCode;
		var iptx1s = document.getElementById("1");
		if(code==46 || code==8) {
			return;
		}
		if(code==37 || code==38) {
			if(ntab==2) return;
			ntab = ntab -2;
		} else if(code==40) {
		
		}
		if(ntab > iplimit) return;
		var nextElem = document.getElementById(ntab);
				if(code==37 || code==38) {
			nextElem.value="";
		}
		nextElem.focus();
	};
//-->
</script>
<c:if test="${StudTimeOffEditInfo.mode == 'Modify'}">
<script type="text/javascript">
<!--
lockInput('studno');
//lockInput('idyear');
//lockInput('idmonth');
//lockInput('idday');
// -->
</script>
</c:if>

