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
 			
 			var dilgs = xmldoc.getElementsByTagName("stmdPrompt");
 			//alert("getElementsByTagName(studentNo)" + xmldoc.getElementsByTagName("studentNo").length);
 			var studentNo = xmldoc.getElementsByTagName("studentNo")[0].firstChild.data;
 			var studentName = xmldoc.getElementsByTagName("studentName")[0].firstChild.data;
			var departClass = xmldoc.getElementsByTagName("departClass")[0].firstChild.data;
 			if(studentName != "notFound") {
			
				var students = document.getElementsByName("studentNo");
				//alert("studentNo ==>" + studentNo);
				for(i=0; i<students.length; i++){
					if(students[i].value == studentNo){
 						//alert("studentNo matched!");
				
						var inputName = document.getElementById("name" + students[i].id);
						var inputClass = document.getElementById("class" + students[i].id);
						if(inputName != null){
							inputName.value = studentName;
							inputName.readOnly = true;
						}
						if(inputClass != null){
							inputClass.value = departClass;
							inputClass.readOnly = true;
						}
					
					}
				}
			} else {
				alert("查無此學生,學號:" + studentNo);
			}

 		}			
 	}
 }
 	function getTimeOff(mode) { 
  	 	var studno = document.getElementById("studno");
 		var studentno = studno.value;
 		var mypath = location.pathname;
 		var urlprefix=mypath.substring(0,mypath.indexOf("/", 1));
 		//alert("urlprefix:" + urlprefix);
		httpRequest("GET",urlprefix + '/AjaxGetStudentTimeOff?mode='+encodeURIComponent(mode) + '&studentno=' + studentno,true,parseResult);
			 	
	}
	
	function getStudentInfo(inputId){
		var studentNo = document.getElementById(inputId).value;
		var mypath = location.pathname;
 		var urlprefix=mypath.substring(0,mypath.indexOf("/", 1));
		httpRequest("GET",urlprefix + '/AjaxGetStudentInfo?studentno=' + studentNo,true,parseResult);
	}

//-->
</script>
