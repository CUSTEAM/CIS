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


<form action="/CIS/StudAffair/StudTimeOffEdit.do" method="post" name="tfForm">

<c:set var="tcnt" value="1"/>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<input type="hidden" name="mode" value="${StudTimeOffEditInfo.mode}">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="stfTitle.StudTimeOffCreate" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td align="left">
<!-- End Content Page Table Header -->

	<table cellspacing="5" class="empty-border">
		<tr>
			<td style="background-color: lightblue;">
				2:曠課 , 3:病假 , 4:事假 , 5:遲到早退 , 6:公假 , 7:喪假 , 8:婚假 , 9:產假, 1:重大傷病住院 
			</td>
		</tr>
        <c:choose>
        <c:when test="${StudTimeOffEdit != null}">
		<input type="hidden" name="daynite" value="${StudTimeOffEdit.daynite}">
      	<tr>
        	<td colspan="16" align="left" valign="middle">學號：
           	<input name="studentNo" id="${tcnt}" value="${StudTimeOffEdit.studentNo}" type="text" size="7" maxlength="10" 
          		onblur="if(this.value!='') document.tfForm.submit();" onKeyUp="nextfocus(${tcnt+1});">
          	<c:set var="tcnt" value="${tcnt + 1}"/>
 				 	 &nbsp;&nbsp;
			<font class="blue_13">
			<c:out value="${StudTimeOffEdit.studentName}" />&nbsp;
			<c:out value="${StudTimeOffEdit.deptClassName}"/>
			</font>
			</td>
		</tr>
	  	<tr>
			<td colspan="16" align="left" valign="middle">日期：(民國)
       		<input name="tfYear" id="${tcnt}" type="text" size="3" maxlength="3" value="${StudTimeOffEdit.tfYear}" onKeyUp="nextfocus(${tcnt+1});">年&nbsp;&nbsp;
			<c:set var="tcnt" value="${tcnt + 1}"/>
            <input name="tfMonth" id="${tcnt}" type="text" size="2" maxlength="3" value="${StudTimeOffEdit.tfMonth}" onKeyUp="nextfocus(${tcnt+1});">月&nbsp;&nbsp;
			<c:set var="tcnt" value="${tcnt + 1}"/>
            <input name="tfDay" id="${tcnt}" type="text" size="2" maxlength="3" value="${StudTimeOffEdit.tfDay}" onKeyUp="nextfocus(${tcnt+1});">日&nbsp;&nbsp;
			<c:set var="tcnt" value="${tcnt + 1}"/>
        	</td>
      	</tr>
        </c:when>
        <c:when test="${StudTimeOffInEdit != null}">
		<input type="hidden" name="daynite" value="${StudTimeOffInEdit.daynite}">
		<c:set var="tfdate" value="${fn:split(StudTimeOffInEdit.sddate, '-')}"/>
      	<tr>
        	<td colspan="16" align="left" valign="middle">學號：
          	<input name="studentNo" id="${tcnt}" value="${StudTimeOffInEdit.studentNo}" type="text" size="7" maxlength="10" 
          		onblur="if(this.value!='') document.tfForm.submit();"  onKeyUp="nextfocus(${tcnt+1});">
			<c:set var="tcnt" value="${tcnt + 1}"/>
 				 	 &nbsp;&nbsp;
			<font class="blue_13">
			<c:out value="${StudTimeOffInEdit.studentName}" />&nbsp;
			<c:out value="${StudTimeOffInEdit.deptClassName}"/>
			</font>
		  </td>
		</tr>
	  	<tr>
			<td colspan="16" align="left" valign="middle">日期：(民國)
       		<input name="tfYear" id="${tcnt}" type="text" size="3" maxlength="3" value="${tfdate[0]}" onKeyUp="nextfocus(${tcnt+1});">年&nbsp;&nbsp;
			<c:set var="tcnt" value="${tcnt + 1}"/>
            <input name="tfMonth" id="${tcnt}" type="text" size="2" maxlength="3" value="${tfdate[1]}" onKeyUp="nextfocus(${tcnt+1});">月&nbsp;&nbsp;
			<c:set var="tcnt" value="${tcnt + 1}"/>
            <input name="tfDay" id="${tcnt}" type="text" size="2" maxlength="3" value="${tfdate[2]}" onKeyUp="nextfocus(${tcnt+1});">日&nbsp;&nbsp;
			<c:set var="tcnt" value="${tcnt + 1}"/>
        	</td>
      	</tr>
        </c:when>
        <c:when test="${StudTimeOffEditInfo != null}">
		<input type="hidden" name="daynite" value="${StudTimeOffEditInfo.daynite}">
      	<tr>
        	<td colspan="16" align="left" valign="middle">學號：
          	<input name="studentNo" id="${tcnt}" value="${StudTimeOffEditInfo.studentNo}" type="text" size="7" maxlength="10" 
          		onblur="if(this.value!='') document.tfForm.submit();"  onKeyUp="nextfocus(${tcnt+1});">
			<c:set var="tcnt" value="${tcnt + 1}"/>
 				 	 &nbsp;&nbsp;
			<font class="blue_13">
			</font>
			<c:out value="${StudTimeOffEditInfo.studentName}" />&nbsp;
			<c:out value="${StudTimeOffEditInfo.deptClassName}"/>
		  </td>
		</tr>
	  	<tr>
			<td colspan="16" align="left" valign="middle">日期：(民國)
       		<input name="tfYear" id="${tcnt}" type="text" size="3" maxlength="3" value="${StudTimeOffEditInfo.tfYear}" onKeyUp="nextfocus(${tcnt+1});">年&nbsp;&nbsp;
			<c:set var="tcnt" value="${tcnt + 1}"/>
            <input name="tfMonth" id="${tcnt}" type="text" size="2" maxlength="3" value="${StudTimeOffEditInfo.tfMonth}" onKeyUp="nextfocus(${tcnt+1});">月&nbsp;&nbsp;
			<c:set var="tcnt" value="${tcnt + 1}"/>
            <input name="tfDay" id="${tcnt}" type="text" size="2" maxlength="3" value="${StudTimeOffEditInfo.tfDay}" onKeyUp="nextfocus(${tcnt+1});">日&nbsp;&nbsp;
			<c:set var="tcnt" value="${tcnt + 1}"/>
        	</td>
      	</tr>
        </c:when>
        </c:choose>
        
        <tr>
        	<td>
        	<table border="1" cellpadding="1" cellspacing="0">
        	<tr>
        	<td width="35" align="left" valign="middle">升旗</td>
        	<td width="35" align="left" valign="middle">1</td>
        	<td width="35" align="left" valign="middle">2</td>
        	<td width="35" align="left" valign="middle">3</td>
        	<td width="35" align="left" valign="middle">4</td>
        	<td width="35" align="left" valign="middle">5</td>
        	<td width="35" align="left" valign="middle">6</td>
        	<td width="35" align="left" valign="middle">7</td>
        	<td width="35" align="left" valign="middle">8</td>
        	<td width="35" align="left" valign="middle">9</td>
        	<td width="35" align="left" valign="middle">10</td>
        	<c:choose>
        	<c:when test="${StudTimeOffEdit.daynite == '1'}">
        		<td width="35" align="left" valign="middle">11</td>
        		<td width="35" align="left" valign="middle">12</td>
        		<td width="35" align="left" valign="middle">13</td>
        		<td width="35" align="left" valign="middle">14</td>
        		<td width="35" align="left" valign="middle">15</td>
        	</c:when>
        	<c:when test="${StudTimeOffEdit.daynite == '2'}">
        		<td width="35" align="left" valign="middle">N1</td>
        		<td width="35" align="left" valign="middle">N2</td>
        		<td width="35" align="left" valign="middle">N3</td>
        		<td width="35" align="left" valign="middle">N4</td>
        		<td width="35" align="left" valign="middle">N5</td>
        	</c:when>
        	<c:otherwise>
        		<td width="35" align="left" valign="middle">11</td>
        		<td width="35" align="left" valign="middle">12</td>
        		<td width="35" align="left" valign="middle">13</td>
        		<td width="35" align="left" valign="middle">14</td>
        		<td width="35" align="left" valign="middle">15</td>
        	</c:otherwise>
        	</c:choose>
       	
        </tr>
        <tr>
        	
      <tr>
        <td align="left" valign="middle">
        	<c:choose>
        	<c:when test="${StudTimeOffEdit != null}">
        		<input type="text" size="1" maxlength="1" name="abs0" id="${tcnt}" class="input" value="${StudTimeOffEdit.abs0}"  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:when>
        	<c:otherwise>
        		<input type="text" size="1" maxlength="1" name="abs0" id="${tcnt}" class="input" value=""  onKeyUp="nextfocus(${tcnt+1});">
 				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:otherwise>
        	</c:choose>
        </td>
        
        <td align="left" valign="middle">
        	<c:choose>
        	<c:when test="${StudTimeOffEdit != null}">
        		<input type="text" size="1" maxlength="1" name="abs1" id="${tcnt}" class="input" value="${StudTimeOffEdit.abs1}"  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:when>
        	<c:otherwise>
        		<input type="text" size="1" maxlength="1" name="abs1" id="${tcnt}" class="input" value=""  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:otherwise>
        	</c:choose>
        </td>
        <td align="left" valign="middle">
        	<c:choose>
        	<c:when test="${StudTimeOffEdit != null}">
        		<input type="text" size="1" maxlength="1" name="abs2" id="${tcnt}" class="input" value="${StudTimeOffEdit.abs2}"  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:when>
        	<c:otherwise>
        		<input type="text" size="1" maxlength="1" name="abs2" id="${tcnt}" class="input" value=""  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:otherwise>
        	</c:choose>
        </td>
        <td align="left" valign="middle">
        	<c:choose>
        	<c:when test="${StudTimeOffEdit != null}">
        		<input type="text" size="1" maxlength="1" name="abs3" id="${tcnt}" class="input" value="${StudTimeOffEdit.abs3}"  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:when>
        	<c:otherwise>
        		<input type="text" size="1" maxlength="1" name="abs3" id="${tcnt}" class="input" value=""  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:otherwise>
        	</c:choose>
        </td>
        <td align="left" valign="middle">
        	<c:choose>
        	<c:when test="${StudTimeOffEdit != null}">
        		<input type="text" size="1" maxlength="1" name="abs4" id="${tcnt}" class="input" value="${StudTimeOffEdit.abs4}"  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:when>
        	<c:otherwise>
        		<input type="text" size="1" maxlength="1" name="abs4" id="${tcnt}" class="input" value=""  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:otherwise>
        	</c:choose>
        </td>
        <td align="left" valign="middle">
        	<c:choose>
        	<c:when test="${StudTimeOffEdit != null}">
        		<input type="text" size="1" maxlength="1" name="abs5" id="${tcnt}" class="input" value="${StudTimeOffEdit.abs5}"  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:when>
        	<c:otherwise>
        		<input type="text" size="1" maxlength="1" name="abs5" id="${tcnt}" class="input" value=""  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:otherwise>
        	</c:choose>
        </td>
        <td align="left" valign="middle">
        	<c:choose>
        	<c:when test="${StudTimeOffEdit != null}">
        		<input type="text" size="1" maxlength="1" name="abs6" id="${tcnt}" class="input" value="${StudTimeOffEdit.abs6}"  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:when>
        	<c:otherwise>
        		<input type="text" size="1" maxlength="1" name="abs6" id="${tcnt}" class="input" value=""  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:otherwise>
        	</c:choose>
        </td>
        <td align="left" valign="middle">
        	<c:choose>
        	<c:when test="${StudTimeOffEdit != null}">
        		<input type="text" size="1" maxlength="1" name="abs7" id="${tcnt}" class="input" value="${StudTimeOffEdit.abs7}"  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:when>
        	<c:otherwise>
        		<input type="text" size="1" maxlength="1" name="abs7" id="${tcnt}" class="input" value=""  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:otherwise>
        	</c:choose>
        </td>
        <td align="left" valign="middle">
        	<c:choose>
        	<c:when test="${StudTimeOffEdit != null}">
        		<input type="text" size="1" maxlength="1" name="abs8" id="${tcnt}" class="input" value="${StudTimeOffEdit.abs8}"  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:when>
	       	<c:otherwise>
        		<input type="text" size="1" maxlength="1" name="abs8" id="${tcnt}" class="input" value=""  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:otherwise>
        	</c:choose>
        </td>
        <td align="left" valign="middle">
        	<c:choose>
        	<c:when test="${StudTimeOffEdit != null}">
        		<input type="text" size="1" maxlength="1" name="abs9" id="${tcnt}" class="input" value="${StudTimeOffEdit.abs9}"  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:when>
        	<c:otherwise>
        		<input type="text" size="1" maxlength="1" name="abs9" id="${tcnt}" class="input" value=""  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:otherwise>
        	</c:choose>
        </td>
        <td align="left" valign="middle">
        	<c:choose>
        	<c:when test="${StudTimeOffEdit != null}">
        		<input type="text" size="1" maxlength="1" name="abs10" id="${tcnt}" class="input" value="${StudTimeOffEdit.abs10}"  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:when>
        	<c:otherwise>
        		<input type="text" size="1" maxlength="1" name="abs10" id="${tcnt}" class="input" value=""  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:otherwise>
        	</c:choose>
        </td>
        <td align="left" valign="middle">
        	<c:choose>
        	<c:when test="${StudTimeOffEdit != null}">
        		<input type="text" size="1" maxlength="1" name="abs11" id="${tcnt}" class="input" value="${StudTimeOffEdit.abs11}"  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:when>
        	<c:otherwise>
        		<input type="text" size="1" maxlength="1" name="abs11" id="${tcnt}" class="input" value=""  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:otherwise>
        	</c:choose>
        </td>
        <td align="left" valign="middle">
        	<c:choose>
        	<c:when test="${StudTimeOffEdit != null}">
        		<input type="text" size="1" maxlength="1" name="abs12" id="${tcnt}" class="input" value="${StudTimeOffEdit.abs12}"  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:when>
        	<c:otherwise>
        		<input type="text" size="1" maxlength="1" name="abs12" id="${tcnt}" class="input" value=""  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:otherwise>
        	</c:choose>
        </td>
        <td align="left" valign="middle">
        	<c:choose>
        	<c:when test="${StudTimeOffEdit != null}">
        		<input type="text" size="1" maxlength="1" name="abs13" id="${tcnt}" class="input" value="${StudTimeOffEdit.abs13}"  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:when>
        	<c:otherwise>
        		<input type="text" size="1" maxlength="1" name="abs13" id="${tcnt}" class="input" value=""  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:otherwise>
        	</c:choose>
        </td>
        <td align="left" valign="middle">
        	<c:choose>
        	<c:when test="${StudTimeOffEdit != null}">
        		<input type="text" size="1" maxlength="1" name="abs14" id="${tcnt}" class="input" value="${StudTimeOffEdit.abs14}"  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:when>
        	<c:otherwise>
        		<input type="text" size="1" maxlength="1" name="abs14" id="${tcnt}" class="input" value=""  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:otherwise>
        	</c:choose>
         </td>
        <td align="left" valign="middle">
        	<c:choose>
        	<c:when test="${StudTimeOffEdit != null}">
        		<input type="text" size="1" maxlength="1" name="abs15" id="${tcnt}" class="input" value="${StudTimeOffEdit.abs15}"  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:when>
        	<c:otherwise>
        		<input type="text" size="1" maxlength="1" name="abs15" id="${tcnt}" class="input" value=""  onKeyUp="nextfocus(${tcnt+1});">
				<c:set var="tcnt" value="${tcnt + 1}"/>
        	</c:otherwise>
        	</c:choose>
        </td>
	  </tr>
	</table>
	</td>
	</tr>
	</table>
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
		var thisElem, nextElem;
		if(ntab-1 <=4 && code!=46 && code!=8 && code!=37 && code!=38) {
			thisElem = document.getElementById(ntab-1);
			if(thisElem.value.length >= thisElem.maxlength || code==39 || code==40) {
				nextElem = document.getElementById(ntab);
				nextElem.focus();
			}
			
		} else {
			if(code==46 || code==8) {
				return;
			}
			if(code==37 || code==38) {
				if(ntab==2) return;
				ntab = ntab -2;
			} else if(code==40) {
		
			}
			if(ntab > iplimit) return;
			nextElem = document.getElementById(ntab);
			if(code==37 || code==38) {
				nextElem.value="";
			}
			nextElem.focus();
		}
	};
//-->
</script>
<c:if test="${StudTimeOffEditInfo.mode == 'Modify'}">
<script type="text/javascript">
<!--
lockInput('1');
//lockInput('idyear');
//lockInput('idmonth');
//lockInput('idday');
// -->
</script>
</c:if>

