<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/ajax.js" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript" src="<%=basePath%>pages/include/json2.js"></script>

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

.grid1 {
	font-size: 13px;
	font-weight: normal;
	height: auto;
	border: 1px solid #FF6600;
	margin: 3px;
	background-color: #FFFFCC;
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

.timeoffButton {
	background-color: #F0E0FE;
	font: normal 8pt;
	border: 1px outset gray;
	margin: auto;
	vertical-align: middle;
	line-height: normal;
}
-->
</style>
<style type="text/css">
<!--
.style2 {color: #FF0000;}
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
<form action="/CIS/StudAffair/TeacherTimeOffInputPatch.do" method="post" name="inForm" id="inForm" onSubmit="init('資料傳送中.....');">

<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="0" cellspacing="0">
<script>generateTableBanner(':: 教師上課未點名補點名作業  ::');</script>
<input type="hidden" name="mode" id="mode" value="${SAFTimeOffInputInfo.mode}">
<c:if test="${SAFTimeOffInputInfo.mode=='patch'}">
	<input type="hidden" name="dtOid" id="dtOid" value="${SAFTimeOffInputInfo.dtOid}">
	<input type="hidden" name="tdate" id="tdate" value="${SAFTimeOffInputInfo.tdate}">
	<input type="hidden" name="teacherId" id="tdate" value="${SAFTimeOffInputInfo.teacherId}">
</c:if>
      
    <tr><td id="classBookBody" style="display:none;">
	</td></tr>
<!-- Begin Content Page Table Footer -->
				
</table>
<!-- End Content Page Table Footer -->
</form>

<html:messages id="msg" message="true">
<script>window.alert("${msg}");</script>
</html:messages>

<script>
var opmode = "${SAFTimeOffInputInfo.mode}";


var orig_dtOid = '';
var orig_tdate = '';
function getClassBook(){
	var oidObj = document.getElementById("dtOid");
	var tdateObj = document.getElementById("tdate");
	var oid = oidObj.value;
	var selDate = tdateObj.value;
	var cbBody = document.getElementById("classBookBody");
	orig_dtOid = oid;
	orig_tdate = selDate;
	if(oid != "" && selDate != ""){
 		var mypath = location.pathname;
 		var urlprefix=mypath.substring(0,mypath.indexOf("/", 1));
		var url = urlprefix + "/AjaxGlobal";
		var jsonQuery = '{"method":"getTchTimeOffInputData", "dtOid":"' + oid + '","tdate":"' + selDate + '"}';
		//window.alert(jsonQuery);
		doJSONRequest(url, jsonQuery, fillClassBook);
		
		
		var inHTML = "<table cellpadding=10><tr><td>";
		inHTML += "<img src=\"/CIS/pages/images/ajax-loader2.gif\" align=\"middle\">";
		inHTML += "&nbsp;&nbsp;<font color=\"red\">資料擷取中.....</font></td></tr></table>";
		cbBody.innerHTML = inHTML;
				
		cbBody.style.display = "inline";
		oidObj.disabled = true;
		tdateObj.disabled = true;
	}else{
		//cbBody.innerHTML = "";
		cbBody.style.display = "none";
	}
}

function clears(){
	var nameTableBody = document.getElementById("classBookBody");
	var ind = nameTableBody.childNodes.length;
	if(ind >0){
		for(var i=ind-1; i>=0; i--){
			nameTableBody.removeChild(nameTableBody.childNodes[i]);
		}
	}
	
	var iform = document.getElementById("inForm");
	var childName = "";
	window.alert("form elements:" + iform.childNodes.length);
	ind = iform.childNodes.length
	for(var k=ind-1; k>=0; k--){
		childName = iform.childNodes[k].name;
		window.alert("child elements:" + childName);
		if(childName == 'studentNo' || childName == 'st0' || childName == 'st1'
		 || childName == 'st2' || childName == 'st3' || childName == 'st4' || childName == 'st5'
		  || childName == 'st6' || childName == 'st7' || childName == 'st8' || childName == 'st9'
		   || childName == 'st10' || childName == 'st11' || childName == 'st12' || childName == 'st13'
		    || childName == 'st14' || childName == 'st15'){
			iform.removeChild(iform.childNodes[k]);
		}
	}
	window.alert("form elements:" + iform.childNodes.length);
}

function setTimeOffStatus(button){
	var bval = button.value;
	//window.alert(button.id);
	var inputObj = document.getElementById("in_" + button.id);
	if(bval == '  '){
		button.value = "曠課";
		inputObj.value = "2";
	}else if(bval=='曠課'){
		button.value = "遲到/早退";
		inputObj.value = "5";
	}else{
		button.value = "  ";
		inputObj.value = "";
	}
}

function hiddenClassBook(){
	var cbBody = document.getElementById("classBookBody");
	//var button = document.getElementById("button1");
	//var inHTML = "<table cellpadding=10><tr><td>";
	//inHTML += "<img src=\"/CIS/pages/images/ajax-loader2.gif\" align=\"middle\">";
	//inHTML += "&nbsp;&nbsp;<font color=\"red\">資料傳送中.....</font></td></tr></table>";
	//cbBody.innerHTML = inHTML;
	cbBody.style.display = "none";
	document.getElementById("dtOid").disabled = true;
	document.getElementById("tdate").disabled = true;
	//window.alert(document.iForm.name);
	//button.style.display = "none";
	return true;
}

function fillClassBook(){
	if(handleState()){
		if(request.responseText){
			//window.alert(request.responseText);
			var cbBody = document.getElementById("classBookBody");
			var button = document.getElementById("button1");
			var oidObj = document.getElementById("dtOid");
			var tdateObj = document.getElementById("tdate");
			var oid = oidObj.value;
			var selDate = tdateObj.value;
			if(oid == orig_dtOid && selDate == orig_tdate){
				var jsonObj = JSON.parse(request.responseText);
				//window.alert("resetCategory:" + jsonObj);
				//var size = jsonObj.length;
				
				var init = jsonObj[0];
				var clazz = init.clazz;
				var depClassName = init.depClassName;
				var cscode = init.cscode;
				var cscodeName = init.cscodeName;
				var tfYear = init.tfYear;
				var tfMonth = init.tfMonth;
				var tfDay = init.tfDay;
				var daynite = init.daynite;
				//window.alert(JSON.stringify(jsonObj[1]));
				var TimeOffSubjInUse = JSON.parse(JSON.stringify(jsonObj[1]));
				var classBook = JSON.parse(JSON.stringify(jsonObj[2]));
				status = new Array(16);
				//var inHTML = "<form action=\"/CIS/Teacher/TimeOffInput.do\" method=\"post\" name=\"iForm\" onSubmit=\"hiddenClassBook();\">";
				var inHTML = "";
				//inHTML += "<input type=\"hidden\" name=\"openClass\" value=\"${TimeOffInputInfo.openClass}\">";
				inHTML += "<table>";
				var stno = "";
				var typ = 0;
				var cols = 1;
				for(var k=0; k<TimeOffSubjInUse.length; k++){
					cols = cols + (TimeOffSubjInUse[k].end - TimeOffSubjInUse[k].begin + 1);
				}
				tfType = new Array("曠課", "病假", "事假", "遲到/早退", "公假", "喪假", "婚假", "產假");
				
				//inHTML += "<tr><td>1:升旗 , 2:曠課 , 3:病假 , 4:事假 , 5:遲到早退 , 6:公假 , 7:喪假 , 8:婚假 , 9:產假 <br>";
				//inHTML += "狀態： w:退選, n:扣考, e:異常, x:禁止輸入</td></tr>";
				//inHTML += "<tr><td><font color=red>老師請注意:點名輸入時間為８天(包含例假日)</font></td></tr>";
				inHTML += "<tr><td colspan=2>";
				inHTML += "<font class=\"blue_13\">班級：" + clazz + "&nbsp;&nbsp;" + depClassName + "&nbsp;&nbsp;";
				inHTML += "科目：" + cscode + "&nbsp;&nbsp;" + cscodeName + "</font>";
				inHTML += "</td></tr>";
				inHTML += "<tr><td colspan=2>";
				inHTML += "日期：" + tfYear + " 年  " + tfMonth + " 月 " + tfDay + " 日";
				inHTML += "</td></tr>";
				inHTML += "<tr><td>";
				inHTML += "<table width=\"" + (130+(cols-1)*60) + "\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\" bgcolor=\"#FFFFCC\" id=\"tb1\">";
				inHTML += "<tr><td width=\"130\" align=\"center\">節次</td>";
				for(var k=0; k<TimeOffSubjInUse.length; k++){
					for(var p=TimeOffSubjInUse[k].begin; p<=TimeOffSubjInUse[k].end; p++){
						inHTML += "<td width=\"60\" align=\"center\" valign=\"top\">";
						if(daynite == '2' && p > 10){
							if(p==11) inHTML += "N1";
							else if(p==12) inHTML += "N2";
							else if(p==13) inHTML += "N3";
							else if(p==14) inHTML += "N4";
							else if(p==15) inHTML += "N5";
							else  inHTML += p;
						}else{
							inHTML += p;
						}
						inHTML += "</td>";
					}
				}
				//inHTML += "<td></td>";
				inHTML += "</tr>";
				/*
				inHTML += "<tr><td width=\"120\">科目名稱</td>";
				for(var k=0; k<TimeOffSubjInUse.length; k++){
					for(var p=TimeOffSubjInUse[k].begin; p<=TimeOffSubjInUse[k].end; p++){
						inHTML += "<td width=\"30\" align=\"left\" valign=\"top\">";
						inHTML += cscodeName;
						inHTML += "</td>";
					}
				}
				inHTML += "</tr>";
				*/
				
				//window.alert(classBook[0].status1);
				for(var i = 0; i<classBook.length; i++){
					inHTML += "<input type=\"hidden\" name=\"studentNo\" value=\"" + classBook[i].studentNo + "\">";
					inHTML += "<tr><td width=\"130\">" + classBook[i].studentNo + " " + classBook[i].studentName + "</td>";
					stno = classBook[i].studentNo;
					var evs = "";
					//var status = classBook[i].status;
					for(var j = 0; j <= 15; j++){
						switch(j){
						case 0: 
							evs = classBook[i].status0;
							break;
						case 1: 
							evs = classBook[i].status1;
							break;
						case 2: 
							evs = classBook[i].status2;
							break;
						case 3: 
							evs = classBook[i].status3;
							break;
						case 4: 
							evs = classBook[i].status4;
							break;
						case 5: 
							evs = classBook[i].status5;
							break;
						case 6: 
							evs = classBook[i].status6;
							break;
						case 7: 
							evs = classBook[i].status7;
							break;
						case 8: 
							evs = classBook[i].status8;
							break;
						case 9: 
							evs = classBook[i].status9;
							break;
						case 10: 
							evs = classBook[i].status10;
							break;
						case 11: 
							evs = classBook[i].status11;
							break;
						case 12: 
							evs = classBook[i].status12;
							break;
						case 13: 
							evs = classBook[i].status13;
							break;
						case 14: 
							evs = classBook[i].status14;
							break;
						case 15: 
							evs = classBook[i].status15;
							break;
						default:
							evs = "x";
						}
					
					
						switch(evs){
						case 'x':
							inHTML += "<input type=\"hidden\" name=\"st" + j + "\" value=\"" + evs +"\">";
							break;
						case '2':
						case '5':
							typ = eval(evs) - 2;
			          		inHTML += "<td width=\"60\" align=\"center\">";
			          		inHTML += "<input type=\"button\" id=\"" + stno + "_" + j + "\" value=\"" + tfType[typ] + "\" onClick=\"setTimeOffStatus(this);\" class=\"timeoffButton\">";
			          		inHTML += "</td>";
			          		inHTML += "<input type=\"hidden\" name=\"st" + j + "\" id=\"in_" + stno + "_" + j + "\" value=\"" + evs + "\">";
			          		break;
						case '':
			          		inHTML += "<td width=\"60\" align=\"center\">";
			          		inHTML += "<input type=\"button\" id=\"" + stno + "_" + j + "\" value=\"  \" onClick=\"setTimeOffStatus(this);\" class=\"timeoffButton\">";
			          		inHTML += "</td>";
			          		inHTML += "<input type=\"hidden\" name=\"st" + j + "\" id=\"in_" + stno + "_" + j + "\" value=\"\">";
			          		break;
						
						
						case '3':
						case '4':
						case '6':
						case '7':
						case '8':
						case '9':
							typ = eval(evs) - 2;
			          		inHTML += "<td width=\"60\" align=\"center\">" + tfType[typ];
			          		inHTML += "</td>";
			          		inHTML += "<input type=\"hidden\" name=\"st" + j + "\" id=\"in_" + stno + "_" + j + "\" value=\"" + evs + "\">";
			          		break;
						case 'w':
			          		inHTML += "<td width=\"60\" align=\"center\">退選</td>";
			          		inHTML += "<input type=\"hidden\" name=\"st" + j + "\" id=\"in_" + stno + "_" + j + "\" value=\"" + evs + "\">";
			          		break;
						case 'n':
			          		inHTML += "<td width=\"60\" align=\"center\">扣考</td>";
			          		inHTML += "<input type=\"hidden\" name=\"st" + j + "\" id=\"in_" + stno + "_" + j + "\" value=\"" + evs + "\">";
			          		break;
						case 'e':
			          		inHTML += "<td width=\"60\" align=\"center\">異常</td>";
			          		inHTML += "<input type=\"hidden\" name=\"st" + j + "\" id=\"in_" + stno + "_" + j + "\" value=\"" + evs + "\">";
			          		break;
						default:
			          		inHTML += "<td width=\"60\" align=\"center\">" + evs;
			          		inHTML += "<input type=\"hidden\" name=\"st" + j + "\" id=\"in_" + stno + "_" + j + "\" value=\"" + evs + "\">";
			          		inHTML += "</td>";
						}
					}
					//inHTML += "<td></td>";
					inHTML += "</tr>";
				
				}
				inHTML += "</table></td><td>&nbsp;</td></tr>";
				
				inHTML += "<tr><td colspan=2>";
				inHTML += "<INPUT type=\"submit\" name=\"method\" value=\"<bean:message key='StartUpload'/>\" onClick=\"chmode(1);\">&nbsp;&nbsp; ";
				inHTML += "<INPUT type=\"submit\" name=\"method\" value=\"<bean:message key='Cancel'/>\"  onClick=\"chmode(0);\">";
				inHTML += "</td></tr>";
				inHTML += "</table>";
				cbBody.innerHTML = inHTML;
				cbBody.style.display = "inline";
				//button.style.display = "inline";
				//window.alert(inHTML);
			}
			oidObj.disabled = false;
			tdateObj.disabled = false;
		}
	}
}

function chmode(mode){
	modObj = document.getElementById("mode");
	if(mode==1){
		modObj.value = "Save";
	}else{
		modObj.value = "Cancel";
	}
	//window.alert("mode:" + modObj.value);
}

function getTodayClassBook(){
	var dtObj =  document.getElementById("dtOid");
	var dateObj = document.getElementById("tdate");
	
	document.getElementById("tdate").value = onLine_date;
	for(var i=0; i<dateObj.length; i++){
		//window.alert(dateObj.options[i].value + ":" + onLine_date);
		if(dateObj.options[i].value == onLine_date){
		//window.alert(dateObj.options[i].value + ":ok:" + onLine_date);
			dateObj.options[i].selected = true;
			break;
		}
	}
	
	//window.alert('call:' + document.getElementById("dtOid").value + ':' + onLine_date + ':' + document.getElementById("tdate").value );
	document.getElementById("tdate").style.display = 'inline';
	getClassBook();
}
</script>


<c:if test="${SAFTimeOffInputInfo.mode=='patch' && SAFTimeOffInputInfo.dtOid != null}">
<script>
var redirect_oid = ${SAFTimeOffInputInfo.dtOid};
//window.alert("'" + "${TimeOffInputInfo.tdate}" + "'");
redirect_date = new String("${SAFTimeOffInputInfo.tdate}");
function setRedirect(){
	var dtObj =  document.getElementById("dtOid");
	var dateObj = document.getElementById("tdate");
	
	dtObj.value = redirect_oid;
	dateObj.value = redirect_date;
	dtObj.style.display = 'none';
	dateObj.style.display = 'none';
	getClassBook();
}

setRedirect();
</script>
</c:if>


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

<script>
history.go(1);
</script>
