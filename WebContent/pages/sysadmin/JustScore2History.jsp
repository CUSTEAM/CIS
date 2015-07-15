<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.style2 {color: #FF0000}
-->
</style>
<script language="javascript">
//<!--
	var myTimeOut = globalTimeOut;
	globalTimeOut = 36000000;
//-->

</script>
<!-- Begin Content Page Table Header -->
<form action="/CIS/SysAdmin/JustScore2History.do" method="post" name="myForm">
<input type="hidden" name="opmode" value="" id="opmode">	
<table width="100%" cellpadding="0" cellspacing="0">
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="Messages.Just2HistTitle" bundle="ADM"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <!-- 
      <tr>
        <td height="33" colspan="6" align="left" valign="top" class="menuIndex"><c:out value="成績資料維護"/> -&gt; <span class="style2">歷年成績資料維護</span></td>
  	    </tr>
  	  -->
      <tr>
        <td  height="30" colspan="6" align="center" valign="middle">
		 ..... 本 程 式 將 根 據 本 學 期 的 操 行 成 績 及 全 勤 資 .....<br/>
		..... 料,轉 入 歷 年 操 行 成 績 檔(cond),歷 年 操 行 評 語 .....<br/>
		..... 檔 (comb1), 歷 年 獎 懲 檔 (comb2), 因 此 在 執 行 此 .....<br/>
		..... 程 式 前 務 必 確 定 各 項 成 績 無 誤  !!!            ..... 
        </td>
      </tr>
      <tr>
        <td  height="34" colspan="6" align="center" valign="middle" bgcolor="#CCCCFF">
        	<c:set var="campusSel" value="${Just2HistInit.campus}"/>
	  		<c:set var="schoolSel" value="${Just2HistInit.school}"/>
	  		<c:set var="deptSel"   value="${Just2HistInit.dept}"/>
	  		<c:set var="classSel"  value="${Just2HistInit.clazz}"/>
			<%@ include file="/pages/include/ClassSelect.jsp" %>
    		<!--     
			<Input type="text" name="depart" value="11" size="2">&nbsp;&nbsp;
			<font color=red>11：台北日間，12：台北夜間，13：台北進院，21：新竹日間，22：新竹夜間，23：新竹進院</font>
			 -->
        </tr>
      <tr>
        <td  height="20" colspan="6" align="left" valign="middle">&nbsp;</td>
        </tr>
	</table>
		</td>		
	</tr>
	<tr>
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
	<script>generateTableBanner('<Input type="button" name="method" id="ok" value="<bean:message key='StartTransfer' bundle='ADM'/>" onClick="if(reconfirm()) document.forms[0].submit();">&nbsp;&nbsp;'
                			  + '<Input type="button" name="method" id="cancel" value="<bean:message key='Cancel' bundle='ADM'/>" >');</script>
	
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
<%@ include file="/pages/include/ajax.js" %>
<script type="text/javascript">
<!--
isAutoLogout = false;
var pollurl = "";
var gpstimer = 5000;
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
					document.getElementById("complete").innerHTML = "轉檔完畢!";
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
			obj.value="StartTransfer";
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
		if(confirm("已確定該學制完成操行成績!\n準備開始轉入歷史成績!？")) {
			chgopmode(1);
			button_ok.disabled = true;
			button_cancel.disabled = true;
			// alert("urlprefix:" + urlprefix);
			pollurl = urlprefix + "/AjaxGetRunningStatus?process=JustScore2History";
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
