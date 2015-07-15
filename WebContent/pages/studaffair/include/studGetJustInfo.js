<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<script language="javascript">
//<!--

var READ_STATE_UNINITIALIZED = 0;
var READ_STATE_LOADING = 1;
var READ_STATE_LOADED = 2;
var READ_STATE_INTERACTIVE = 3;
var READ_STATE_COMPLETE = 4;

var RESPONSE_STATUS_OK = 200;

var request = null;
var daynite = true;

function chkDayNite(studentNo) {

	var flag;
	if(studentNo.length == 8) flag=studentNo.charAt(3);
	else if(studentNo.length == 7) flag=studentNo.charAt(2);
	else flag='2';
	if(flag=='2' || flag=='3' || flag=='4' || flag=='5') daynite=true;
	else if(flag=='0' || flag=='1' || flag=='8' || flag=='9' || flag=='A') daynite=false;
	else if(flag=='6' || flag=='7') daynite=true;
	else if(flag=='G') {
		var Gflag = studentNo.charAt(5);
		if(Gflag=='0' || Gflag=='1' || Gflag=='2' || Gflag=='3' || Gflag=='4') {
			daynite=true;
		} else if(Gflag=='5' || Gflag=='6' || Gflag=='7' || Gflag=='8' || Gflag=='9') {
			daynite=true;
		}
	} 
	else daynite=true;
	return daynite;
}


function httpRequest(method, url, asynch, resHandle) {
	if(window.XMLHttpRequest) {
		request = new XMLHttpRequest();
	} else if(window.ActiveXObject) {
		request = new ActiveXObject("Msxml2.XMLHTTP");
		if(!request) {
			request = new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	if(request) {
		if(method.toLowerCase() != "post") {
			initReq(method, url, asynch, resHandle);
		} else {
			var args = arguments[4];
			if(args !== null && args.length > 0) {
				initReq(method, url, asynch, resHandle, args);
			}
		}
	} else {
		window.alert("Error to retrived Request Object!!!");
	}
}

function initReq(method, url, asynch, resHandle) {
	try {
		request.onreadystatechange = resHandle;
		request.open(method, url, asynch);
		request.setRequestHeader("Cache-Control", "no-cache");
		request.setRequestHeader("Pragma", "no-cache");
		// IE will CACHED request, so should be appended following code
		// (Firefox not)
		request.setRequestHeader("If-Modified-Since", "0");
		if(method.toLowerCase() == "post") {
			request.setRequestHeader("Content-type", "application/x-www-form-urlencoded;");
			request.send(arguments[4]);
		} else {
			request.send(null);
		}
	} catch(err) {
		window.alert(err.message);
	}
}

function parseResult() {
 	if(request.readyState==READ_STATE_COMPLETE){ 
 		if(request.status==RESPONSE_STATUS_OK){
 			var xmldoc = request.responseXML;
 			
 			var justs = xmldoc.getElementsByTagName("stmdPrompt");
 			//alert("justs:" + justs.length);
 			//alert("getElementsByTagName(studentNo)" + xmldoc.getElementsByTagName("studentNo").length);
 			var studentNo = xmldoc.getElementsByTagName("studentNo")[0].firstChild.data;
 			var studentName = xmldoc.getElementsByTagName("studentName")[0].firstChild.data;
			var departClass = xmldoc.getElementsByTagName("departClass")[0].firstChild.data;
			var deptClassName = xmldoc.getElementsByTagName("deptClassName")[0].firstChild.data;
			var dilgScore = xmldoc.getElementsByTagName("dilgScore")[0].firstChild.data;
			var desdScore = xmldoc.getElementsByTagName("desdScore")[0].firstChild.data;
			var totalScore = xmldoc.getElementsByTagName("totalScore")[0].firstChild.data;
			var total = 82;
 			if(studentName != "notFound") {
 				var studDisplay = document.getElementById("student");
 				var hdilgInput = document.getElementById("hdilgScore");
 				var hdesdInput = document.getElementById("hdesdScore");
				var dilgDisplay = document.getElementById("dilgScoreDisp");
				var desdDisplay = document.getElementById("desdScoreDisp");
				var totalDisplay = document.getElementById("totalScoreInput");
				if(hdilgInput != null){
					hdilgInput.value = dilgScore;
				}
				if(hdesdInput != null){
					hdesdInput.value = desdScore;
				}
				if(dilgDisplay != null){
					dilgDisplay.innerHTML = dilgScore;
				}
				if(desdDisplay != null){
					desdDisplay.innerHTML = desdScore;
				}
				if(studDisplay != null){
					var dispString = "&nbsp;&nbsp;" + studentName;
					dispString = dispString + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + departClass;
					dispString = dispString + "&nbsp;&nbsp;" + deptClassName;
					dispString = dispString + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;基本分：82";
					studDisplay.innerHTML = dispString; 
       			}
       			if(totalScore != "0.0"){
       			 	alert("該學生操行資料已存在!\n請勿重複建立!");
       			}else{
       				total = total + eval(dilgScore) + eval(desdScore);
       				if(totalDisplay != null) totalDisplay.innerHTML= total;
       			}
					
			} else {
				alert("查無此學生資料,學號:" + studentNo);
			}

 		}			
 	}
 }
	
function getStudentJust(inputId){
	var studentNo = document.getElementById(inputId).value;
	var mypath = location.pathname;
 	var urlprefix=mypath.substring(0,mypath.indexOf("/", 1));
	httpRequest("GET",urlprefix + '/AjaxGetStudentJust?studentno=' + studentNo,true,parseResult);
}

//-->
</script>
