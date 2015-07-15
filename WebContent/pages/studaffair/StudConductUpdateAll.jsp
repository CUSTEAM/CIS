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

<form action="/CIS/StudAffair/StudConductUpdateAll.do" method="post" name="caForm">
<input type="hidden" name="opmode" value="" id="opmode">	
<input type="hidden" name="updateType" value="" id="updateType">	

<table width="100%" cellpadding="0" cellspacing="0">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.StudConductUpdateAll" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td align="left"> 
<!-- End Content Page Table Header -->

	<table cellspacing="5" class="empty-border" width="100%">
		<tr>
			<td colspan="2"><br/>
			本程式功能只會更新所選擇部制學生操行成績(曠缺,獎懲加減分及總分)<br/>
			如需重新建立操行成績，請通知電算中心!<br/>
			<font color="red" style="size:20px;">執行時間較久，請耐心等候...</font>
			<br/><br/></td>
		</tr>
	    <tr>
	        <td align="center" valign="middle">請選擇班級 ：
        	<c:set var="campusSel" value="${ConductUpdateInit.campus}"/>
	  		<c:set var="schoolSel" value="${ConductUpdateInit.school}"/>
	  		<c:set var="deptSel"   value="${ConductUpdateInit.dept}"/>
	  		<c:set var="classSel"  value="${ConductUpdateInit.departClass}"/>
			<%@ include file="/pages/studaffair/include/ClassSelectSAF.jsp" %>
	  		<br/>
	  		<!-- 
	  		請選擇更新種類 ：
	  		<input type="radio" name="update" onclick="chgUpdateType('all');">曠缺,獎懲加減分及總分
	  		<br/>
	  		<input type="radio" name="update" onclick="chgUpdateType('total');">總分
	        </td>
	         -->
	        <td id="statusBlock">
        
        		<table align="center">
				<tbody>
				<tr>
				<td id="title" align="left" colspan="3"></td>
				</tr>
				<tr>
				<td width="50">
				<div id="loading"><img src="images/ajax-loader2.gif"></div>
				</td>
				<td>&nbsp;</td>
				<td>
				<div id="progressBar" style="padding:2px;border:solid gray 1px;visibility:hidden;">
				<span id="block1">&nbsp;&nbsp;&nbsp;</span>
				<span id="block2">&nbsp;&nbsp;&nbsp;</span>
				<span id="block3">&nbsp;&nbsp;&nbsp;</span>
				<span id="block4">&nbsp;&nbsp;&nbsp;</span>
				<span id="block5">&nbsp;&nbsp;&nbsp;</span>
				<span id="block6">&nbsp;&nbsp;&nbsp;</span>
				<span id="block7">&nbsp;&nbsp;&nbsp;</span>
				<span id="block8">&nbsp;&nbsp;&nbsp;</span>
				<span id="block9">&nbsp;&nbsp;&nbsp;</span>
				</div>
				<br>
				<textarea name="logs" id="logs" Cols="30" Rows="5" ></textarea>
				</td>
				</tr>
				<tr><td id="complete" align="center" colspan="3"></td></tr>
				</tbody>
				</table>
        	</td>
	        
	    </tr>
	</table>
	</td>
	</tr>
	
	<script>
	generateTableBanner('<INPUT type="button" name="method" value="<bean:message key='OK'/>" id="ok" onClick="if(reconfirm()) document.forms[0].submit();">&nbsp;&nbsp;'+
	'<INPUT type="button" name="method" value="<bean:message key='Cancel'/>" id="cancel" onClick="chgopmode(0);document.forms[0].submit();">');
	</script>
</table>

</form>
<%@ include file="/pages/include/ajax.js" %>
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

function pollServer() {
	httpRequest("GET", pollurl, true, pollCallback);
}

function pollCallback() {
	if(request.readyState== 4) {
		if(request.status == 200)  {
			var methodName = request.responseXML.getElementsByTagName("methodName")[0].firstChild.data;
			var step = request.responseXML.getElementsByTagName("step")[0].firstChild.data;
			var rcount = request.responseXML.getElementsByTagName("rcount")[0].firstChild.data;
			var total = request.responseXML.getElementsByTagName("total")[0].firstChild.data;
			var percentage = request.responseXML.getElementsByTagName("percentage")[0].firstChild.data;
			var complete = request.responseXML.getElementsByTagName("complete")[0].firstChild.data;
			
			//alert("isRunning:" + percentage);
			if(percentage=='none') {
				document.getElementById("title").innerHTML = "步驟->執行中,請稍候! ";
				setTimeout("pollServer()", gpstimer);
			} else {
				document.getElementById("title").innerHTML = "步驟->" + step + "->已處理 " + rcount + "筆 / 共 " + total + " 筆";
				var index = processResult("" + Math.round((rcount/total)*100));
				//alert("index=" + index + ":" + Math.round(rcount/total));
				clearBar();
				for(var i=1; i<=index; i++) {
					var elem = document.getElementById("block" + i);
					elem.innerHTML = clear;
					elem.style.backgroundColor = bar_color;
					var next_cell = i+1;
					if(next_cell >index && next_cell <= 9) {
						document.getElementById("block" + next_cell).innerHTML = Math.round((rcount/total)*100) + "%";
				
					}
				}
				if (index < 9 || complete!='yes') {
					setTimeout("pollServer()", gpstimer);
				}else if(index >= 9 && complete=='no'){
					//faild for retry
					clearBar();
				}
				if(complete=='yes') {
					document.getElementById("complete").innerHTML = "執行完畢!";
					document.getElementById("loading").style.visibility = "hidden";
					document.getElementById("ok").disabled = false;
				}
				if(complete!='yes' || complete!='no'){
					document.getElementById("logs").value = complete;
				}
			}
		}
	}
	
}

function processResult(percentage) {
//取出百分比的值以10個block來表示
	var ind;
	//alert("percentage length:" + percentage.length + ", percentage=" + percentage);
	if(percentage.length == 1){
		ind = 1;
	} else if(percentage.length == 2) {
		ind = percentage.substring(0,1);
	} else {
		ind = 9;
	}
	return ind;
}

function checkDiv() {
	var progress_bar =document.getElementById("progressBar");
	if(progress_bar.style.visibility == "visible") {
	clearBar();
	document.getElementById("complete").innerHTML = "";
	} else {
		document.getElementById("statusBlock").style.backgroundColor="#ffd700";
		document.getElementById("title").innerHTML = "開始執行 ..... ";
		document.getElementById("loading").style.visibility = "visible";
		document.getElementById("loading").innerHTML = "<img src='images/ajax-loader2.gif'>";
		progress_bar.style.visibility = "visible";
		document.getElementById("logs").style.visibility = "visible";
	}
}

function clearBar() {
	for(var j = 1; j < 10; j++) {
		var elem = document.getElementById("block" + j);
		elem.innerHTML = clear;
		elem.style.backgroundColor = "gray";
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

	function hidding() {
		document.getElementById("progressBar").style.visibility = "hidden";
		document.getElementById("loading").style.visibility = "hidden";
		document.getElementById("logs").style.visibility = "hidden";
	}

	function reconfirm() {
		var button_ok = document.getElementById("ok");
		var button_cancel = document.getElementById("cancel");
		if(confirm("確定要重新計算操行成績嗎？")) {
			chgopmode(1);
			button_ok.disabled = true;
			button_cancel.disabled = true;
			// alert("urlprefix:" + urlprefix);
			pollurl = urlprefix + "/AjaxGetRunningStatus?process=updateJustAll";
			//alert("pollurl:" + pollurl);
			checkDiv();
			setTimeout("pollServer()", gpstimer);
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