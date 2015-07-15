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
 			
 			var student = xmldoc.getElementsByTagName("stmdPrompt");
 			//alert("justs:" + justs.length);
 			//alert("getElementsByTagName(studentNo)" + xmldoc.getElementsByTagName("studentNo").length);
 			var studentNo = xmldoc.getElementsByTagName("studentNo")[0].firstChild.data;
 			var studentName = xmldoc.getElementsByTagName("studentName")[0].firstChild.data;
			var departClass = xmldoc.getElementsByTagName("departClass")[0].firstChild.data;
			var deptClassName = xmldoc.getElementsByTagName("deptClassName")[0].firstChild.data;
			if(studentName != "notFound") {
 				var studDisplay = document.getElementById("student");
				if(studDisplay != null){
					var dispString = "&nbsp;&nbsp;" + studentName;
					dispString = dispString + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + departClass;
					dispString = dispString + "&nbsp;&nbsp;" + deptClassName;
					dispString = dispString + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;基本分：82";
					studDisplay.innerHTML = dispString; 
       			}
			} else {
				alert("查無此學生資料,學號:" + studentNo);
			}

 		}			
 	}
 }
	
function getStudentInfo(inputId){
	var studentNo = document.getElementById(inputId).value;
	var mypath = location.pathname;
 	var urlprefix=mypath.substring(0,mypath.indexOf("/", 1));
	httpRequest("GET",urlprefix + '/AjaxGetStudentInfo?studentno=' + studentNo,true,parseResult);
}

//-->
</script>
