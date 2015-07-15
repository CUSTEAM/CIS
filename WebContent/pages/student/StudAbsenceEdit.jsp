<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>

<script type="text/javascript" src="<%=basePath%>pages/include/json2.js"></script>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/Calendar.inc"%>
<%@ include file="/pages/include/tooltips.js" %>

<DIV id="TipLayer" style="visibility:hidden;position:absolute;z-index:1000;top:-100"></DIV>

<%@ include file="/pages/include/ajax.js" %>

<script type="text/javascript">
	var basePath='<%=basePath%>';
</script>


<!-- *** AjaxUpload *** -->
<link type="text/css" href="<%=basePath%>pages/include/css/jquery-ui-1.8.1.lightness.css" rel="stylesheet" />	
<script type="text/javascript" src="<%=basePath%>pages/include/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/include/jquery-ui-1.8.1.min.js"></script>
<!-- We will use version hosted by Google-->
<!-- script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.1/jquery.min.js" type="text/javascript"></script -->
<!-- Required for jQuery dialog demo-->
<!-- script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.min.js" type="text/javascript"></script -->
<!-- link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/ui-darkness/jquery-ui.css" type="text/css" media="all" / -->
<!-- AJAX Upload script doesn't have any dependencies-->
<script type="text/javascript" src="<%=basePath%>pages/include/ajaxupload.3.9.js"></script>

<style type="text/css">
<!--
.wrapper {
	width: 133px;
	margin: 0 auto;
}

div.button {
	height: 29px;	
	width: 133px;
	background: url(<%=basePath%>pages/images/button.png) 0 0;
	
	font-size: 14px;
	color: #C7D92C;
	text-align: center;
	padding-top: 8px;
}
/* 
We can't use ":hover" preudo-class because we have
invisible file input above, so we have to simulate
hover effect with javascript. 
 */
div.button.hover {
	background: url(<%=basePath%>pages/images/button.png) 0 56px;
	color: #95A226;	
}
#button2.hover, #button4.hover {
	text-decoration:underline;
}
-->
</style>

<style type="text/css">
<!--
.style1 {color: #0000FF}
.style3 {
	font-size: 24px;
	font-weight: bold;
}
.title {
	font-size:13px;
	background-color:#ddffdd;
	border-style:solid;
	border-width: 1px 1px 1px 1px;
	border-color:lightblue;
}
.input {
	font-size:13px;
	background-color:#ffffee;
	border-style:solid;
	border-width: 1px 1px 1px 1px;
	border-color:lightblue;
}
.mustInput {
	font-size:13px;
	background-color:#ffcccc;
	border-style:solid;
	border-width: 1px 1px 1px 1px;
	border-color:#cc6666;
}
.descript {
	font-size:12px;
	background-color:#eeffff;
	border-style:solid;
	border-width: 1px 1px 1px 1px;
	border-color:lightblue;
}
.textsmallred {
	font-size:8px;
	color: #FF0000;
}
-->
</style>
<style type="text/css">
<!--
.style2 {
	color: #FF0000;
	border-style:solid;
	border-width: 1px 1px 1px 1px;
	border-color:#cc6666;
}
-->
</style>
<!-- Add from EmplAmsInfo.jsp -->
<script type="text/javascript">
history.go(1);
Style[0]=["white","black","#00B333","#E8E8FF","","","","","","","","","","",150,"",2,2,10,10,51,0.5,75,"simple","gray"];
Style[1]=["white","black","#00CCCC","#E8E8FF","","","","","","","","","","",150,"",2,2,10,10,51,0.5,75,"simple","gray"];
function check() {
	var iCount = document.getElementById("txtUserInput").value;
	if (iCount == '') {
		alert("身分證字號不可為空白,謝謝!!");
		document.getElementById("txtUserInput").focus();
		return false;
	}

	iCount = document.getElementById("startDate").value;
	if (iCount == '') {
		alert("起始時間不可為空白,謝謝!!");
		return false;
	}

	iCount = document.getElementById("endDate").value;
	if (iCount == '') {
		alert("結束時間不可為空白,謝謝!!");
		return false;
	}

	return true;
}

</script>

<style>
<!--
span.spanTextDropdown { 
	position: absolute;
	top: 0px;
	left: 0px;
	width: 150px;
	z-index: 101;
	background-color: #ECEBAE;
	border: 2px solid #8C9024;
	padding-left: 2px;
	overflow: visible;
	display: none;
	font-size: 13px;
}

span.spanMatchText { 
	text-decoration: underline;
	color: blue;
}

span.spanNormalElement { 
	background: #FFFFFF; 
}

span.spanHighElement { 
	background: red;
	color: white;
	cursor: pointer; 
}
-->
</style>



<form action="/CIS/Student/StudAbsenceApply.do" method="post" name="inputForm" onSubmit="checkFilled();">
<input name="opmode" type="hidden" value="add"/>
<input name="studentNo" type="hidden" value="${studentNo}"/>
<!-- for auto input select item use -->
	<div id="popup" class="ds_box" style="position:absolute;">
		 <table id="name_table" bgcolor="#FFFAFA" border="1" cellspacing="2" cellpadding="2">
		 	  <tbody id="name_body"></tbody>
		 </table>
	</div> 


<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="2" cellspacing="2">
<script>generateTableBanner('請假管理->新增');</script>	

<tr><td><table width="100%">
<tr><td align="left">
<font color="red">同學請注意:線上請假時間以８天為限(包含例假日)</font>
</td>
<td align="right">
<span style="font-size:90%;*font-size:85%;float:right;display:inline;margin:3px 5px 0;" onClick="swHelp();">
<img src="/CIS/pages/images/info.png" width="20" height="20" align="middle" class="none">
<font color="blue" style="font-family:標楷體 新細明體;font-size:12px;">使用說明</font>
</span>
</td></tr>

<tr><td colspan="2" id="helptd" style="border-top: 1px #ff7340 solid;
overflow:scroll;border-bottom: 3px #ff7340 outset;
border-left: 1px #0746ff solid;
border-right: 3px #0746ff outset;">
<div id="ophelp" style="background: #fffff0;
font-size: 12px;
text-decoration: none;
line-height: 180%;
display: none;">
1.請假事由，可輸入中英文，字數請勿過多，最好不要超過30個字。<br>
2.開始及結束日期，請直接點選輸入框右側之月曆圖像，並點選要輸入的日期即可。
<br>
3.點選完請假之開始即結束日期後，系統才會擷取並顯示請假期間您上課的節次資訊，每一節次上有一個按鈕，均有兩種狀態循環顯示<br>
，初始狀態為<font color="blue">「空白」</font>：<font color="red">沒有請假</font>，按一下轉換為<font color="blue">「V」表示要請假</font>，再按一下還原為「空白」沒有請假的狀態
 <br>
4.<font color="red">如有附件要上傳，請按『上傳附件圖檔』按鈕，瀏覽並選取要上傳的檔案，上傳完成後即可在下方看到以上傳檔案的名稱。</font>
<br>
5.如要刪除已上傳的檔案，請直接點選檔案名稱前的
<img src="/CIS/pages/images/delete.jpg" style="width:68px;height:20px" class="none"/>
圖示，即可直接刪除該檔案。
<br>
6.目前可上傳檔案限制如下，格式：<font color="red">『jpeg』『gif』『png』『bmp』，</font>大小：每一檔案不可超過500Kbytes。
<br>
</div>
		  	</td></tr>
</table></td></tr>  

<!-- End Content Page Table Header -->
	<tr><td><table>
	<c:choose>
	<c:when test="${StudAskLeaveEditForm != null}">
	<c:set var="formback" value="${StudAskLeaveEditForm}"></c:set>
	<tr>
		<td width="75" class="title">請假事由</td>
		<td class="input"><input type="text" maxlength="13" name="reason" class="mustInput" value="${formback.reason}"></td>
	</tr>
	<tr>
		<td width="75" class="title">假別</td>
		<td class="input">
		<select name="askLeaveType" id="askLeaveType">
			<c:forEach items="${askLeaveTypes}" var="askType">
				<c:if test="${askType.idno == formback.askLeaveType}">
				<option value="${askType.idno}" Selected="selected">${askType.name}</option>
				</c:if>
				<c:if test="${askType.idno != formback.askLeaveType}">
				<option value="${askType.idno}">${askType.name}</option>
				</c:if>
			</c:forEach> 
		</select>
		</td>
	</tr>



	<tr>
		<td class="hairlineTdF">&nbsp;&nbsp;開始日期&nbsp;&nbsp;&nbsp;&nbsp;</td>
        <td class="hairlineTdF">
            <input type="text" name="startDate" id="startDate" size="10" maxlength="8" readonly="readonly" value="${formback.startDate}" onBlur=" changePeriods();">
    			<img src="<%=basePath%>pages/images/cal.gif" name="calendar" id="sCalImg" width="20" height="20"
		      		align="top" style="cursor:hand" alt="點選此處選擇日期"
		  	  		onclick="javascript:if(!(document.getElementById('startDate').disabled))popCalFrame.fPopCalendar('startDate','startDate',event);">&nbsp;
        </td>
	</tr>
	<tr>
        <td class="hairlineTdF">&nbsp;&nbsp;結束時間&nbsp;&nbsp;&nbsp;&nbsp;</td>
        <td class="hairlineTdF">
            <input type="text" name="endDate" id="endDate" size="10" maxlength="8" readonly="readonly" value="${formback.endDate}" onBlur=" changePeriods();">
    			<img src="<%=basePath%>pages/images/cal.gif" name="calendar" id="eCalImg" width="20" height="20"
    				align="top" style="cursor:hand" alt="點選此處選擇日期"
 		  	  		onclick="javascript:if(!(document.getElementById('endDate').disabled))popCalFrame.fPopCalendar('endDate','endDate',event);">
        </td>
	</tr>
	<tr>
		<td width="75" class="title">選擇節次</td>
		<td class="input">
		<DIV id="periodSelect"></DIV>
		</td>
	</tr>
	 
	<tr>
		<td width="75" class="title" valign="top">上傳附件</td>
		<td class="input">
		<input type="hidden" name="fileOids" id="fileOids" value="${formback.fileOids}"/>
		<div class="wrapper">
			<div id="button1" class="button">上傳附件圖檔</div>(格式：<font color="red">『jpeg』『gif』『png』『bmp』，</font>大小：500KB以內。
		</div>
		<p>已上傳檔案:</p>
		<div id="fileList">
		<c:forEach items="${formback.attachs}" var="attach">
			<div id="${attach.fileOid}" style="height:25px">
			<img src="/CIS/pages/images/delete.jpg" style="width:68px;height:20px;vertical-align:middle;" class="delete" onClick="deleteFile(this);"/>
			<a href="<%=basePath%>AjaxGlobal?method=getDocAttachUpload&oid=${attach.fileOid}"  target="_blank" style="color:blue; text-decoration:none;">${attach.fileName}</a>
			</div>
  		</c:forEach>
		</div>
		
		</td>
	</tr>
	
	<tr>
		<td width="75" class="title" valign="top">備註</td>
		<td class="input"><textarea rows="3" cols="60" name="memo">${formback.memo}</textarea>
		</td>
	</tr>
	
	</c:when>
	<c:otherwise>
	<tr>
		<td width="75" class="title">請假事由</td>
		<td class="input"><input type="text" maxlength="13" name="reason" class="mustInput" value=""></td>
	</tr>
	<tr>
		<td width="75" class="title">假別</td>
		<td class="input">
		<select name="askLeaveType" id="askLeaveType">
			<option value="0" Selected="selected">請選擇</option>
			<c:forEach items="${askLeaveTypes}" var="askType">
				<c:if test="${askType.idno == formback.askLeaveType}">
				<option value="${askType.idno}" Selected="selected">${askType.name}</option>
				</c:if>
				<c:if test="${askType.idno != formback.askLeaveType}">
				<option value="${askType.idno}">${askType.name}</option>
				</c:if>
			</c:forEach> 
		</select>
		</td>
	</tr>



	<tr>
		<td class="hairlineTdF">&nbsp;&nbsp;開始日期&nbsp;&nbsp;&nbsp;&nbsp;</td>
        <td class="hairlineTdF">
            <input type="text" name="startDate" id="startDate" size="10" readonly="readonly" maxlength="8" value="" onBlur=" changePeriods();">
    			<img src="<%=basePath%>pages/images/cal.gif" name="calendar" id="sCalImg" width="20" height="20"
		      		align="top" style="cursor:hand" alt="點選此處選擇日期"
		  	  		onclick="javascript:if(!(document.getElementById('startDate').disabled)){popCalFrame.fPopCalendar('startDate','startDate',event);}">&nbsp;
        </td>
	</tr>
	<tr>
        <td class="hairlineTdF">&nbsp;&nbsp;結束日期&nbsp;&nbsp;&nbsp;&nbsp;</td>
        <td class="hairlineTdF">
            <input type="text" name="endDate" id="endDate" size="10" readonly="readonly" maxlength="8" value="" onBlur=" changePeriods();">
    			<img src="<%=basePath%>pages/images/cal.gif" name="calendar" id="eCalImg" width="20" height="20"
    				align="top" style="cursor:hand" alt="點選此處選擇日期"
 		  	  		onclick="javascript:if(!(document.getElementById('endDate').disabled)){popCalFrame.fPopCalendar('endDate','endDate',event);}"
 		  	  		>
        </td>
	</tr>
	<tr>
		<td width="75" class="title">選擇節次</td>
		<td class="input" id="periodSelect" style="display:none;">
		</td>
	</tr>
	 
	<tr>
		<td width="75" class="title" valign="top">上傳附件</td>
		<td class="input">
		<input type="hidden" name="fileOids" id="fileOids" value=""/>
		<div class="wrapper">
			<div id="button1" class="button">上傳附件圖檔</div>
		</div>
		<p>已上傳檔案:</p>
		<div id="fileList"></div>
		
		</td>
	</tr>
	
	<tr>
		<td width="75" class="title" valign="top">備註</td>
		<td class="input"><textarea rows="3" cols="60" name="memo"></textarea>
		</td>
	</tr>
	
	</c:otherwise>
	</c:choose>
	</table></td></tr>
	
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Save'/>" >&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" >');
	</script>
</table>
</form>
<script language="javascript">
var orig_sdate = '';
var orig_edate = '';

function swHelp(){
	var helpObj = document.getElementById("ophelp");
	var tdObj = document.getElementById("helptd");
	if(helpObj.style.display=='none'){
		tdObj.style.background="#fffff0";
		helpObj.style.display='inline';
	}else if(helpObj.style.display=='inline'){
		tdObj.style.background="";
		helpObj.style.display='none';
	}
}


function changePeriods(){
	var sdateObj = document.getElementById("startDate");
	var edateObj = document.getElementById("endDate");
	var sCalImg =  document.getElementById("sCalImg");
	var eCalImg =  document.getElementById("eCalImg");
	sdate = new String(sdateObj.value);
	edate = new String(edateObj.value);
	sdatea = sdate.split("-");
	edatea = edate.split("-");
	//window.alert("sdate:" + sdatea[0] + sdatea[1] + sdatea[2]  + ", edate:" + edatea[0] + edatea[1] + edatea[2]);
	var cbBody = document.getElementById("periodSelect");
	if(sdate != "" && edate != "" && (sdatea.length==3 && edatea.length==3)){
		if(sdatea[0]<=edatea[0] || (sdatea[0]==edatea[0] && sdatea[1]<=edatea[1]) ||
			(sdatea[0]==edatea[0] && sdatea[1]==edatea[1] && sdatea[2]==edatea[2])){
			orig_sdate = sdate;
			orig_edate = edate;
	 		var mypath = location.pathname;
	 		var urlprefix=mypath.substring(0,mypath.indexOf("/", 1));
			var url = urlprefix + "/AjaxGlobal";
			var jsonQuery = '{"method":"getStudentPeriodData", "startDate":"' + sdate + '","endDate":"' + edate + '","docOid":""}';
			//window.alert(jsonQuery);
			doJSONRequest(url, jsonQuery, fillPeriodData);
			
			
			var inHTML = "<table cellpadding=10><tr><td>";
			inHTML += "<img src=\"/CIS/pages/images/ajax-loader2.gif\" align=\"middle\">";
			inHTML += "&nbsp;&nbsp;<font color=\"red\">資料擷取中.....</font></td></tr></table>";
			cbBody.innerHTML = inHTML;
					
			cbBody.style.display = "inline";
			sdateObj.disabled = true;
			edateObj.disabled = true;
			//sCalImg..disabled = true;
			//eCalImg..disabled = true;
			
		}else{
			cbBody.style.display = "none";
		}
	}else{
		//cbBody.innerHTML = "";
		cbBody.style.display = "none";
	}
}

function setTimeOffStatus(button){
	var bval = button.value;
	//window.alert(button.id);
	var inputObj = document.getElementById("in_" + button.id);
	if(bval == ' '){
		button.value = "V";
		inputObj.value = "1";
	}else if(bval=='V'){
		button.value = " ";
		inputObj.value = "";
	}
}


function fillPeriodData(){
	if(handleState()){
		if(request.responseText){
			//window.alert(request.responseText);
			var cbBody = document.getElementById("periodSelect");
			var button = document.getElementById("button1");
			var sdateObj = document.getElementById("startDate");
			var edateObj = document.getElementById("endDate");
			var sCalImg =  document.getElementById("sCalImg");
			var eCalImg =  document.getElementById("eCalImg");
			var sdate = sdateObj.value;
			var edate = edateObj.value;
			if(sdate == orig_sdate && edate == orig_edate){
				var jsonObj = JSON.parse(request.responseText);
				//window.alert("resetCategory:" + jsonObj);
				//var size = jsonObj.length;
				
				var clazz = "";
				var depClassName = "";
				var cscode = "";
				var cscodeName = "";
				var tfYear = "";
				var tfMonth = "";
				var tfDay = "";
				var daynite = "";
				//window.alert(JSON.stringify(jsonObj[1]));
				status = new Array(16);
				var inHTML = "";
				inHTML += "<table class=\"style2\">";
				var stno = "";
				var typ = 0;
				var cols = 15;
				tfType = new Array("曠課", "病假", "事假", "遲到", "公假", "喪假", "婚假", "產假");
				
				//inHTML += "<tr><td>1:升旗 , 2:曠課 , 3:病假 , 4:事假 , 5:遲到早退 , 6:公假 , 7:喪假 , 8:婚假 , 9:產假 <br>";
				//inHTML += "狀態： w:退選, n:扣考, e:異常, x:禁止輸入</td></tr>";
				//inHTML += "<tr><td><font color=red>老師請注意:點名輸入時間為８天(包含例假日)</font></td></tr>";
				inHTML += "<tr><td rowspan=\"2\" align=\"center\" class=\"style2\">";
				inHTML += "<font class=\"blue_13\">日期</font>";
				inHTML += "</td>";
				inHTML += "<td colspan=\"15\" align=\"center\" class=\"style2\">";
				inHTML += "<font class=\"blue_13\">節次</font>";
				inHTML += "</td></tr>";
				inHTML += "<tr>";
				for(var j=1; j<=15; j++){
					inHTML += "<td align=\"center\" class=\"style2\">";
					if(j > 10){
						if(j==11) inHTML += "N1";
						else if(j==12) inHTML += "N2";
						else if(j==13) inHTML += "N3";
						else if(j==14) inHTML += "N4";
						else if(j==15) inHTML += "N5";
						else  inHTML += j;
					}else{
						inHTML += j;
					}
					inHTML += "</td>";
				}
				inHTML += "</tr>";
				
				for(var i=0; i<jsonObj.length; i++){
					inHTML += "<input type=\"hidden\" name=\"tdate\" value=\"" + jsonObj[i].tdate + "\" >";
					inHTML += "<tr><td class=\"style2\">" + jsonObj[i].tdate + "</td>";
					tfdate = jsonObj[i].tdate;
					periods = jsonObj[i].periods;
					absName = jsonObj[i].absName;
					for(var k=1; k<=15; k++){
						if(periods[k]=='n'){
							inHTML += "<input type=\"hidden\" name=\"st" + k + "\" value=\"n\">";
			          		inHTML += "<td class=\"style2\" width=\"30\" align=\"center\">&nbsp;</td>";
						}else if(periods[k]=='y'){
			          		inHTML += "<td class=\"style2\" width=\"30\" align=\"center\">";
			          		inHTML += "<input type=\"button\" id=\"" + tfdate + "_" + k + "\" value=\" \" onClick=\"setTimeOffStatus(this);\" class=\"timeoffButton\">";
			          		inHTML += "</td>";
			          		inHTML += "<input type=\"hidden\" name=\"st" + k + "\" id=\"in_" + tfdate + "_" + k + "\" value=\"\">";
						}else if(periods[k]=='1'){
			          		inHTML += "<td class=\"style2\" width=\"30\" align=\"center\">";
			          		inHTML += "<input type=\"button\" id=\"" + tfdate + "_" + k + "\" value=\"V\" onClick=\"setTimeOffStatus(this);\" class=\"timeoffButton\">";
			          		inHTML += "</td>";
			          		inHTML += "<input type=\"hidden\" name=\"st" + k + "\" id=\"in_" + tfdate + "_" + k + "\" value=\"1\">";
						}
					}
					inHTML += "</tr>";
				}
				
				
				inHTML += "</table>";
				
				//inHTML += "<tr><td colspan=2>";
				//inHTML += "<INPUT type=\"submit\" name=\"method\" value=\"<bean:message key='StartUpload'/>\">&nbsp;&nbsp; ";
				//inHTML += "<INPUT type=\"submit\" name=\"method\" value=\"<bean:message key='Cancel'/>\">";
				//inHTML += "</td></tr>";
				cbBody.innerHTML = inHTML;
				cbBody.style.display = "inline";
				//button.style.display = "inline";
				//window.alert(inHTML);
			}
			sdateObj.disabled = false;
			edateObj.disabled = false;
			//sCalImg.disabled = false;
			//sCalImg.disabled = false;
		}
	}
}


</script>

<script type= "text/javascript">/*<![CDATA[*/
var mypath = location.pathname;
var urlprefix=mypath.substring(0,mypath.indexOf("/", 1));
var url = urlprefix + "/AjaxGlobal";
var jsonQuery = url + "?method=uploadFile&imgType=studDoc";

jQuery(document).ready(function(){
	changePeriods();
	var button = $('#button1'), interval;
	new AjaxUpload(button,{
		//action: 'upload-test.php', // I disabled uploads in this example for security reasons
		action: jsonQuery, 
        name: 'myupload',
        data: {
        imgType: "studDoc"
    	},
        
		//responseType: 'json',
		onSubmit : function(file, ext){
            if (!(ext && /^(jpg|jpeg|gif|png|bmp)$/i.test(ext))) {
                alert('檔案格式不正確,請上傳jpg/gif/png/bmp等格式的圖檔!');
                return false;
            }else{
            	this.setData({
                imgType: "studDoc"
            	});
            }
			// change button text, when user selects file			
			button.text('上傳中');
			
			// If you want to allow uploading only 1 file at time,
			// you can disable upload button
			this.disable();
			
			// Uploding -> Uploading. -> Uploading...
			interval = window.setInterval(function(){
				var text = button.text();
				if (text.length < 13){
					button.text(text + '.');					
				} else {
					button.text('上傳中');				
				}
			}, 200);
		},
		onComplete: function(file, response){
			//window.alert("response:" + response);
			button.text('上傳附件圖檔');
						
			window.clearInterval(interval);
						
			// enable upload button
			this.enable();
			
			// add file to the list
			var jsonObj = JSON.parse(response);
			if(jsonObj != null){
				if(jsonObj.oid != null){
					alert("上傳成功!");
		            $('<div id="' + jsonObj.oid + '"  style="height:25px"><img id="img_' + jsonObj.oid + '" src="/CIS/pages/images/delete.jpg" style="width:68px;height:20px;vertical-align:middle;" class="delete" />' + file + '</div>').appendTo('#fileList');
					addFileOid(jsonObj.oid);
					//$('<li></li>').appendTo('#example1 .files').text(file);						
				}else if(jsonObj.fail != null){
					alert(jsonObj.fail);
				}
			}else{
				alert("上傳失敗!");
			}
		}
	});

	
	//Function to delete uploaded file in server.
    jQuery('img').live("click", function() {deleteFile($(this)); });
	
});

function deleteFile(objfile) {
	var myclass = objfile.attr("class");
	if(myclass!='delete') return false;
	var parentId = objfile.parent().attr("id");
	//window.alert("delete:" + parentId);
	
	var jsonQuery = url + '?method=deleteStudDocAttach&fileName=' + parentId;
   $.ajax({ url: jsonQuery, type:"GET", dataType:"json", success: function(jsonRep) { delSuccess(jsonRep, objfile.parent()); } });
   //$('#uploadStatus').html("Deleted");
}

function delSuccess(jsonRep, divParent){
	if(jsonRep.result == 'success'){
		clears(divParent); 
		divParent.hide();
	}else{
		window.alert("刪除失敗:" + jsonRep.result);
	}
}

function clears(myObj){
	//update fileOids input value
	var fOidObj = document.getElementById("fileOids");
	fileOids = new String(fOidObj.value);
	oids = fOidObj.value.split("|");
	newStr = new String();
	for(i=0; i<oids.length; i++){
		if(myObj.attr("id")!=oids[i]){
			newStr = newStr + oids[i] + "|";
		}
	}
	fOidObj.value = newStr.substring(0, newStr.length-1);
	//remove fileList child
	var ind = myObj.children().length;
	var kids = myObj.children();
	kids.remove();
}

function addFileOid(oid){
	var fOidObj = document.getElementById("fileOids");
	if(fOidObj.value == ''){
		fOidObj.value = oid;
	}else{
		fOidObj.value = fOidObj.value + "|" + oid;
	}
	//window.alert(fOidObj.value);
}

/*]]>*/</script>
