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

function handleState() {
	if(request.readyState == READ_STATE_COMPLETE){
		if(request.status == RESPONSE_STATUS_OK){
			return true;
		}else{
			return false;
		}
	}else{
		return false;
	}
	return false;
}

function doJSONRequest(url, jsonQuery, responfunc) {
	//alert("doJsonRequest");
	if(window.XMLHttpRequest) {
		request = new XMLHttpRequest();
	} else if(window.ActiveXObject) {
		request = new ActiveXObject("Msxml2.XMLHTTP");
		if(!request) {
			request = new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	if(request) {
	
		initJSONReq(url, jsonQuery, responfunc);
	} else {
		window.alert("Error to retrived Request Object!!!");
	}
}

function initJSONReq(url, jsonQuery, func) {
	try {
		request.onreadystatechange = func;
		url = url + "?sessionNumber=" + new Date().getTime() + Math.floor(Math.random()*999);
		//window.alert(url);
		request.open("post", url, true);
		request.setRequestHeader("Cache-Control", "no-cache");
		request.setRequestHeader("Pragma", "no-cache");
		// IE will CACHED request, so should be appended following code
		// (Firefox not)
		request.setRequestHeader("If-Modified-Since", "0");
		request.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
		request.send(jsonQuery);
		//window.alert("jsonQuery have sent!");
	} catch(err) {
		window.alert(err.message);
	}
}

function getJson(filterFun){
	if(filterFun){
		jsonObj = JSON.parse(request.responseText,filterFun);
	}else{
		jsonObj = JSON.parse(request.responseText);
	}
}

//-->
</script>