<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/CodeSelection.js" %>

<script language="javascript">
//<!--
	var myTimeOut = globalTimeOut;
	globalTimeOut = 36000000;
//-->

</script>

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

<form action="/CIS/StudAffair/DtimeDilgPeriod.do" method="post" name="ddpForm">
<input type="hidden" name="opmode" value="" id="opmode">	

<table width="100%" cellpadding="0" cellspacing="0">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.StudConductCreateAll" bundle="SAF"/></B></div>');</script>	  
	<tr>
	<td align="left"> 
<!-- End Content Page Table Header -->

	<table cellspacing="5" class="empty-border" width="100%">
		<tr>
			<td><br/>
			注意！
			本程將重新計算開課主檔之扣考節數並更新！<br/><br/>
			<br/></td>
		</tr>
	</table>
	</td>
	</tr>
	
	<script>
	generateTableBanner('<INPUT type="button" name="method" value="<bean:message key='forms[0]d="ok" onClick="if(reconfirm()) document.forms[0].submit();">&nbsp;&nbsp;'+
	'<INPUT type="button" name="method" value="<bean:message key='Cancforms[0]d="cancel" onClick="chgopmode(0);document.forms[0].submit();">');
	</script>
</table>

</form>
<script type="text/javascript">
<!--
isAutoLogout = false;
var pollurl = "";
var gpstimer = 10000;
var mypath = location.pathname;
var urlprefix=mypath.substring(0,mypath.indexOf("/", 1));
var key;
var bar_color='blue';
var span_id="block";
var clear="&nbsp;&nbsp;&nbsp;";


	function chgopmode(mode){
		var obj = document.getElementById("opmode");
		if(mode==1) {
			obj.value="ok";
		}else if(mode==0) {
			obj.value="cancel";
		}
	}

	function reconfirm() {
		var button_ok = document.getElementById("ok");
		var button_cancel = document.getElementById("cancel");
		if(confirm("確定要計算開課主檔之扣考節數並更新嗎？")) {
			chgopmode(1);
			button_ok.disabled = true;
			button_cancel.disabled = true;
			//setTimeout("pollServer()", gpstimer);
			return true;
		}else {
			chgopmode(0);
			return false;
		}
	}
	
	function chgUpdateType(mtype) {
		var updobj = document.getElementById("updateType");
		if(mtype=='all') {
			updobj.value='all';
		}else if(mtype=='total'){
			updobj.value='total';
		}
	}
	
	hidding();
//-->
</script>
<script>history.go(1);</script>