<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.div1 {
	font-size: 13px;
	font-weight: normal;
	height: auto;
	width: auto;
	border: 1px solid #FF6600;
	margin: 3px;
}

.scoreprn {
	background-color: #cfe69f;
	border: 1px solid #cfe69f;
	position: absolute;
	margin-top: -25%;
	margin-left: -25%;
	margin: auto;
	z-index: 32767;
}
.caution{
	font-size: 90%;
	color: red;
	background-color: #f3ffc7;
	margin: 12px;
	text-align: left;
}

-->
</style>
<style type="text/css">
<!--
.style2 {color: #FF0000}
-->
</style>

<script type="text/javascript">
<!--
noMenu = false;
openMenu();
function resetTimeOut(){
	if(myTimeOut != null) {
		globalTimeOut = myTimeOut;
		myTimeOut = null;
	}
}

resetTimeOut;
//-->
</script>

<!-- <html:form action="/Score/ScoreHist" method="post"> -->
<form action="/CIS/Teacher/ScoreRateChange.do" method="post" name="inForm" onSubmit="return chkRateSum();">
<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="0" cellspacing="0">
<script>generateTableBanner('教師資訊系統');</script>	  
	<tr>
		<td width="100%" align="center" valign="top" class="decorate">
<!-- End Content Page Table Header -->

	<table width="100%" border="0" cellpadding="5" cellspacing="1">
      <!--DWLayoutTable-->
      <tr>
        <td height="33" colspan="6" align="left" valign="top" class="menuIndex">
        <c:out value="教師輸入成績系統"/> -&gt;成績比例變更<span class="style2">
        </span></td>
  	    </tr>
      <tr>
        <td  height="35" colspan="6" align="left" valign="middle" bgcolor="#CCCCFF">
       	<font color="blue">${ScoreRateChangeInfo.teacherName}&nbsp;老師</font>
　　			<!-- 請點選要輸入的班級(開學後三週內可變更成績比例)： -->
        </td>
      	</tr>
      <tr>
      	<td>&nbsp;</td>
      </tr>
      <tr>
      	<td id="rateupdate">
		  <c:if test="${ScoreRateUpdate4Choose != null}" >
		  <c:set var="updateable" value="0"/>
		  
      	   	<c:forEach items="${ScoreRateUpdate4Choose}" var="classes">
       	   		<c:if test="${classes.updateable}">
       	   			<input type="hidden" name="rateN" id="rateN_input_${classes.oid}" value="${classes.rateN}">
        	   		<input type="hidden" name="rateM" id="rateM_input_${classes.oid}" value="${classes.rateM}">
        	   		<input type="hidden" name="rateF" id="rateF_input_${classes.oid}" value="${classes.rateF}">
        	   		<input type="hidden" name="changed" id="changed_${classes.oid}" value="0">
       	   		</c:if>
 		        <table style="border:1px solid #220022; background-color:#FFE4C4; margin:5px; padding:3px; spacing:10px;">
      	   		<tr>
      	   		<td colspan="4">
      	   			<c:out value="${classes.className2}(${classes.oid})"/>&nbsp;&nbsp;<c:out value="${classes.chiName2}"/>
      	   			<c:if test="${classes.updateable}">
      	   			<c:set var="updateable" value="1"/>
	      	   		<div class="caution">
					請檢視並勾選確認下列規定後,始可修改成績比例:<br>
					1. <input type="checkbox" name="chk1_${classes.oid}" id="chk1_${classes.oid}" onClick="setDisp(this);"> 本人對於本課程確認有調整成績比例之需要。<br>
					2. <input type="checkbox" name="chk2_${classes.oid}" id="chk2_${classes.oid}" onClick="setDisp2(this)" disabled> 本人已於開學上課三週前公告學生週知，本課程有調整成績比例。<br>
					</div>
					</c:if>
	      	   	</td>
	      	   	</tr>
	      	   	
	      	   	<tr>
       	   		<c:if test="${!classes.updateable}">
       	   			<td width="150" style="background-color:#F5F5F5;">平時比例:${classes.rateN}</td>
       	   			<td width="150" style="background-color:#F5F5F5;">期中比例:${classes.rateM}</td>
       	   			<td width="150" style="background-color:#F5F5F5;">期末比例:${classes.rateF}</td>
       	   			<td></td>
       	   		</c:if>
       	   		<c:if test="${classes.updateable}">
      	   		<td width="150" style="background-color:#FAFAD2;">平時比例:
      	   			<select name="rateNsel" id="rateN_${classes.oid}" onChange="setInputValue(this);" disabled>
					<c:forEach begin="2" end="6" var="myrate">
					<c:set var="trate" value="${myrate/10}"/>
					<c:if test="${trate == classes.rateN}">
						<option value="${trate}" selected>${trate}</option>
					</c:if>
					<c:if test="${trate != classes.rateN}">
						<option value="${trate}">${trate}</option>
					</c:if>
					</c:forEach>
					</select>
      	   		</td>
      	   		<td width="150" style="background-color:#FAFAD2;">期中比例:
      	   			<select name="rateMsel" id="rateM_${classes.oid}" onChange="setInputValue(this);" disabled>
					<c:forEach begin="2" end="6" var="myrate">
					<c:set var="trate" value="${myrate/10}"/>
					<c:if test="${trate == classes.rateM}">
						<option value="${trate}" selected>${trate}</option>
					</c:if>
					<c:if test="${trate != classes.rateM}">
						<option value="${trate}">${trate}</option>
					</c:if>
					</c:forEach>
					</select>
      	   		</td>
      	   		<td width="150" style="background-color:#FAFAD2;">期末比例:
      	   			<select name="rateFsel" id="rateF_${classes.oid}" onChange="setInputValue(this);" disabled>
					<c:forEach begin="2" end="6" var="myrate">
					<c:set var="trate" value="${myrate/10}"/>
					<c:if test="${trate == classes.rateF}">
						<option value="${trate}" selected>${trate}</option>
					</c:if>
					<c:if test="${trate != classes.rateF}">
						<option value="${trate}">${trate}</option>
					</c:if>
					</c:forEach>
					</select>
      	   		</td>
       	   		<td></td>
      	   		</c:if>
      	   		</tr>
                </table>
          	</c:forEach>
          </c:if>
		  <c:if test="${empty ScoreRateUpdate4Choose}" >
		  	<font color="gray">您這學期沒有授課班級</font>!!!
		  </c:if>
		  
     	</td>
      </tr>
	</table>
		</td>		
	</tr>
	<c:if test="${! empty ScoreRateUpdate4Choose && updateable=='1'}">
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='OK' bundle='SCR'/>" >&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel' bundle='SCR'/>" >&nbsp;&nbsp;');
	</script>
	</c:if>
<!-- Begin Content Page Table Footer -->
				
</table>
<!-- End Content Page Table Footer -->
</form>


<!-- </html:form> -->
<script>
function setDisp(chkbox1){
	chk2 = new String(chkbox1.id);
	chk2 = chk2.substring(5, chkbox1.length);
	var chkbox2 = document.getElementById("chk2_" + chk2);
	var rateN = document.getElementById("rateN_" + chk2);
	var rateM = document.getElementById("rateM_" + chk2);
	var rateF = document.getElementById("rateF_" + chk2);
	var changed = document.getElementById("changed_" + chk2);
	
	if(chkbox1.checked){
		chkbox2.disabled = false;
	}else{
		chkbox2.checked = false;
		chkbox2.disabled = true;
		changed.value = "0";
	}
		rateN.disabled = true;
		rateM.disabled = true;
		rateF.disabled = true;
};

function setDisp2(chkbox2){
	ratSel = new String(chkbox2.id);
	ratSel = ratSel.substring(5, chkbox2.length);
	var rateN = document.getElementById("rateN_" + ratSel);
	var rateM = document.getElementById("rateM_" + ratSel);
	var rateF = document.getElementById("rateF_" + ratSel);
	var changed = document.getElementById("changed_" + ratSel);
	
	if(chkbox2.checked){
		rateN.disabled = false;
		rateM.disabled = false;
		rateF.disabled = false;
		changed.value = "1";
	}else{
		rateN.disabled = true;
		rateM.disabled = true;
		rateF.disabled = true;
		changed.value = "0";
	}
};

function chkRateSum(){
	var rateN = document.getElementsByName("rateN");
	var rateM = document.getElementsByName("rateM");
	var rateF = document.getElementsByName("rateF");
	
	for(i=0; i<rateN.length; i++){
		if(eval(rateN[i].value + rateM[i].value + rateF[i].value)!=1){
			window.alert("成績比例總和必須等於1" + "," + (rateN[i].value + rateM[i].value + rateF[i].value));
			return false;
		} 
	}
	return ture;
}

function setInputValue(selObj){
	selId = new String(selObj.id);
	var rateName = selId.substring(0, 6);
	var oid = selId.substring(6, selId.length);
	
	var inputObj = document.getElementById(rateName + "input_" + oid);
	inputObj.value = selObj.value;
}

history.go(1);
</script>
