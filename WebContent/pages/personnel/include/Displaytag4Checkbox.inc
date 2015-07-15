<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<script language="javascript">
<!--

//////////////////////////////////
//
// COPIED FROM cookie_functions.js
//
//////////////////////////////////

	function getCookie(name){
		var cname = name + "=";
		var dc = document.cookie;
		var begin, end;
		if (dc.length > 0) {
			begin = dc.indexOf(cname);
			if (begin != -1) {
				begin += cname.length;
				end = dc.indexOf(";", begin);
				if (end == -1) {
					end = dc.length;
				}
				return dc.substring(begin, end);
			}
		}
		return (null);
	};

	function setCookie(name, value, expires) {
		var expTime = "";
		if (expires != null) {
			var now = new Date();
			now.setTime(now.getTime() + expires);
			expTime = now.toGMTString();
		}
		document.cookie = name + "=" + value + "; path=/" + ((expires == null) ? "" : "; expires=" + expTime);
		return value;
	};

	function deleteCookie(name) {
		document.cookie = name + "=; expires=Thu, 01-Jan-1970 00:00:01 GMT" +  "; path=/";
	};

	// Upgrade to support cookie name, see below
	//function resetCheckboxCookie() {
	//	setCookie("checkCount", "0");
	//	setCookie("checkedIndex", "|");
	//}

	function resetCheckboxCookie(cookieName) {
		setCookie(cookieName + "Count", "0");
		setCookie(cookieName, "|");
	};

	function setAllCheckboxCookie(ttlCount, cookieName) {
		setCookie(cookieName+"Count", ttlCount);
		//var checkedIndex ;
		//if (ttlCount > 500) {
		//	checkedIndex = "all-";
		//} else {
		//	checkedIndex = "|trigger|";
		//	for (var i=1; i <= ttlCount; i=i+1) {
		//		checkedIndex += i + "|"; <===== THIS IS TOTALLY WRONG!
		//	}
		//}
		setCookie(cookieName, "all-");
	};

///////////////////////////////
//
// END OF cookie_functions.js
//
///////////////////////////////

	function submitCheckStatus(obj, cookieName) {

		var checkCount = getCookie(cookieName+"Count");
		if (checkCount == null) {
			if (obj.checked == true) {
				checkCount = 1;
			}
		} else {
			if (obj.checked == true) {
				checkCount++;
			} else {
				checkCount--;
			}
		}
		setCookie(cookieName+"Count", checkCount);

		var checkedIndices = getCookie(cookieName);
		if (checkedIndices == null) {
			checkedIndices = setCookie(cookieName, "|");
		}
		if (checkedIndices.indexOf("all-") != 0) {
			/// COOKIE CONTAINS NO "all-" FROM THE BEGINNING - POSITIVE LISTING
			if (obj.checked == true) {
				checkedIndices += obj.value + "|";
			} else {
				checkedIndices = deleteSubString(checkedIndices, obj.value + "|", "|");
			}
		} else {
			/// COOKIE CONTAINS "all-" FROM THE BEGINNING - NEGATIVE LISTING
			if (obj.checked == true) {
				   checkedIndices = deleteSubString(checkedIndices, obj.value + "-", "-");
			} else {
				   checkedIndices += obj.value + "-";
			}
		}
		//alert(cookieName + "=" + checkedIndices);
		setCookie(cookieName, checkedIndices);
	};

	function deleteSubString(source, pattern, startDelimiter) {

		/// THIS FUNCTION IS USED TO DELETE INDEX NUMBER, TOGETHER WITH
		/// ITS END DELIMITER FROM THE COOKIE VALUE STRING.
		///
		/// FOR POSITIVE LISTING, THIS FUNCTION SHOULD BE CALLED AS:
		///
		///		deleteSubString(cookieValue, index + "|", "|");
		///
		/// FOR NEGATIVE LISTING, THIS FUNCTION SHOULD BE CALLED AS:
		///
		///		deleteSubString(cookieValue, index + "-", "-");
		///
		var startPos = source.indexOf(startDelimiter + pattern) + 1;
		if (startPos == 0) {
			return source;
		}
		var endPos = startPos + pattern.length;
		return source.substring(0, startPos) + source.substring(endPos);
	};

	function getCheckStatus(index, cookieName) {
		/// THIS FUNCTION IS CALLED BEFORE onLoad="checkStatusNotify()"
		/// THE COOKIE IS RETRIEVED BEFORE RESET BY checkStatusNotify()
		/// SO, SAME CRITERION IS CHECKED TO DETERMIN IF COOKIE WILL BE RESET
		var msg = "<%=request.getAttribute("StatusNotify")%>";
		if (msg != "null") {
			var keepCheckboxCookie = "<%=request.getAttribute("KeepCheckboxCookie")%>";
			if (keepCheckboxCookie == "null") {
				return "";
			}
		}
		///
		var checkedIndices = getCookie(cookieName);
		if (checkedIndices == null) {
			checkedIndices = "|";
		}
		if (checkedIndices.indexOf("all-") == 0) {	// started with 'all-', negative listing
			if (checkedIndices.indexOf("-" + index + "-") != -1) {
   	  	   	   	return "";
   	  	   	} else {
   	  	   	   	return "checked";
   	  	   	}
   	  	} else {	// positive listing
   	  		//var extra = getCookie(cookieName + "Extra");
   	  		//alert(checkedIndices);
   	  		//alert(extra);
   	  		//if (extra != null && extra != "") {	// cookie longer than 3000 and split into several cookies
	   	  		//try {
	   	  			//for (i=1; i <= extra; i++) {
	   	  				//alert(cookieName + i);
	   	  				//checkedIndices += getCookie(cookieName + i);
	   	  			//}
	   	  		//} catch(err) {alert(err);}
	   	  	//}

			if (checkedIndices.indexOf("|" + index + "|") != -1) {
				return "checked";
			} else {
				return "";
			}
		}
	};

	function submitTriggerAll(obj, ttlCount, cookieName) {
		//alert("obj.name=");
		if (obj.checked == true) {
			selectAll(ttlCount ,cookieName);
		} else {
			deselectAll(cookieName);
		}
	};

	function selectAll(count, cookieName) {
	    var len = document.forms[0].select.length;
	    var obj = document.forms[0].select;
	    if (len>0){
		  for(i=0; i< obj.length; i++){
		    obj[i].checked = true;
		  }
		}else{
		  obj.checked = true;
		}
		setAllCheckboxCookie(count, cookieName);
		return false;
	};

	function deselectAll(cookieName) {
	    var len = document.forms[0].select.length;
	    var obj = document.forms[0].select;
	    if (len>0){
		  for(i=0; i< obj.length; i++){
		    obj[i].checked = false;
		  }
		}else{
		  obj.checked = false;
		}
		resetCheckboxCookie(cookieName);
		return false;
	};

	function generateCheckbox(index, cookieName) {
		document.write("<input type=\"checkbox\" name=\"select\" value=\"" + index + "\" " + getCheckStatus(index, cookieName) + " onClick=\"submitCheckStatus(this, '" + cookieName + "');\">");
	};

	function generatURLink(index, cookieName){
		//document.write("/CIS//CIS/StudentSel?Oid="+getCheckStatus(index, cookieName)");
		OpenWindow('/CIS/StudentSel?Oid='+getCheckStatus(index, cookieName) );
	};

	function generateDisabledAndCheckedCheckbox(index, cookieName) {
		document.write("<input disabled checked type=\"checkbox\" name=\"select\" value=\"" + index + "\" " + getCheckStatus(index, cookieName) + " onClick=\"submitCheckStatus(this, '" + cookieName + "');\">");
	};

	function generateTriggerAll(ttlCount, cookieName) {
		document.write("<input type=\"checkbox\" name=\"triggerAll\" value=\"trigger\" " + getCheckStatus('trigger', cookieName) + " onClick=\"submitTriggerAll(this," + ttlCount + ", '" + cookieName + "');\">");
	};

	function checkSelectForModify(cookieName) {
		var iCount;
		iCount = getCookie(cookieName + "Count");
		if (iCount < 1) {
			alert("請選取一項以茲修改");
			return false;
		} else if (iCount > 1) {
			alert("僅能選取一項以茲修改");
			return false;
		} else {
			return true;
		}
	};

	function checkSelectForDelete(cookieName) {
		var iCount;
		iCount = getCookie(cookieName + "Count");
		if (iCount < 1) {
			alert("請選取一項以茲刪除");
			return false;
		} else if (iCount > 1) {
			alert("僅能選取一項以茲刪除");
			return false;
		} else {
			return true;
		}
	};

	function checkSelectForView(cookieName) {
		var iCount;
		iCount = getCookie(cookieName + "Count");
		if (iCount < 1) {
			alert("請選取一項以茲檢視");
			return false;
		} else if (iCount > 1) {
			alert("僅能選取一項以茲檢視");
			return false;
		} else {
			//alert(location.pathname+location.search);
			//setCookie("BackPage", location.pathname+location.search);
			return true;
		}
	};
	
	function checkSelectFor(cookieName, funName) {
		var iCount;
		iCount = getCookie(cookieName + "Count");
		if (iCount < 1) {
			alert("請選取一項以茲" + funName);
			return false;
		} else if (iCount > 1) {
			alert("僅能選取一項以茲" + funName);
			return false;
		} else {
			return true;
		}
	};
	
	function checkMultiSelectFor(cookieName, funName) {
		var iCount;
		iCount = getCookie(cookieName + "Count");
		if (iCount < 1) {
			alert("請至少選取一項以茲" + funName);
			return false;
		} else {
			return true;
		}
	};
	
//<!-- bellow codes is used for Ajax to query something -->
	var READ_STATE_UNINITIALIZED = 0;
	var READ_STATE_LOADING = 1;
	var READ_STATE_LOADED = 2;
	var READ_STATE_INTERACTIVE = 3;
	var READ_STATE_COMPLETE = 4;
	
	var RESPONSE_STATUS_OK = 200;
	var haveSubCategory = new Boolean(true);
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
	
	function initReq(method, url, asynch, resProcess) {
		try {
			request.onreadystatechange = resProcess;
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
	
	function generateReport(oid, label){
		var url = "/CIS/AjaxGlobal?method=DocReport&Oid=" + oid + "&sessionNumber=" + new Date().getTime() + Math.floor(Math.random()*999);
		document.write("<a href=\"" + url + "\"><img src=\"/CIS/pages/images/printer.gif\" alt=\"" + label + "\" style=\"border-style:none;\"/></a>");	
	};
	
	function showDetail(serviceNo, suggestion){
		//window.alert("suggestion:" + suggestion);
		label = new String(suggestion);
		if(label.length > 20){
			document.write("<a href=\"javascript:getPaper(\'" + serviceNo + "\');\">" + label.substring(0, 18) + "<font color=\"red\">...詳細資料</font></a>");	
		}else{
			document.write("<a href=\"javascript:getPaper(\'" + serviceNo + "\');\">" + label + "</a>");	
		}
	};
	
	var mypath = location.pathname;
	var urlprefix=mypath.substring(0,mypath.indexOf("/", 1));
	function getPaper(serviceNo){
		orig_sno = serviceNo;
		var url = urlprefix + "/AjaxGlobal";
		var jsonQuery = '{"method":"getAssessPaper", "serviceNo":"' + serviceNo + '"}';
		//window.alert(jsonQuery);
		doJSONRequest(url, jsonQuery, displayDetail);
	}
	
	function fillPaperDetail() {
		var snoObj = document.getElementById("serviceNo");
		var sno = snoObj.value;
		
		var cname = document.getElementById("cname");
		var unit= document.getElementById("unit");
		
		if(handleState()){
			window.alert("sno:" + sno + ", orig:" + orig_sno);
			if(request.responseText && sno == orig_sno){
				window.alert(request.responseText);
				var jsonObj = JSON.parse(request.responseText);
				if(jsonObj != null) {
					cname.value = jsonObj.cname;
					unit.value = jsonObj.unitName;
				}
			}
		}
		snoObj.disabled = false;
	};


	function displayDetail(serviceNo) {
	    var loadingMessage;
	    var fullWin = document.getElementById('loadMsg');
	    var dispWin = document.getElementById('loadIco');
	    
		fullWin.style.width=document.body.scrollWidth;
		if (navigator.appName.indexOf("Microsoft")!=-1) {
			var scrollHeight=document.body.scrollHeight;
			var clientHeight=document.body.clientHeight;
			if (scrollHeight>clientHeight){
				fullWin.style.height=scrollHeight;// IE
			}else{
				fullWin.style.height=clientHeight;// IE
			}
			
		}else{
			fullWin.style.height=document.body.scrollHeight;// FF, ns
		}
		
		if(document.body.scrollWidth > 500){
			dispWin.style.left = Math.floor((document.body.scrollWidth - 500)/2);
		}else{
			dispWin.style.left = 0;
		}
		
		var myheight = 0;
		if (navigator.appName.indexOf("Microsoft")!=-1) {
			var scrollHeight=document.body.scrollHeight;
			var clientHeight=document.body.clientHeight;
			//if (scrollHeight>clientHeight){
				//myheight=scrollHeight;// IE
			//}else{
				myheight=clientHeight;// IE
			//}
			
		}else{
			myheight=document.body.clientHeight;// FF, ns
		}
		
		if(myheight > 300){
			dispWin.style.top = Math.floor((myheight - 300)/2);
		}else{
			dispWin.style.top = 0;
		}
	
	    
		if(handleState()){
			//window.alert("sno:" + sno + ", orig:" + orig_sno);
			if(request.responseText){
				//window.alert(request.responseText);
				var jsonObj = JSON.parse(request.responseText);
				if(jsonObj != null) {
					var paper = jsonObj.paper;

					loadingMessage = '<table width="100%" cellspacing="5" class="empty-border">' + 
			      	'<tr><td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">服務編號： </td>' +
			      	'<td width="280" bgcolor="#ADDFFF">' + paper.serviceNo + '</td></tr>' + 
			        '<tr><td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">服務人員：</td>' +
			        '<td width="280" bgcolor="#ADDFFF" height="30" align="left" valign="middle">' + jsonObj.cname + '</td></tr>' +
			        '<tr><td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">所屬單位：</td>' + 
			      	'<td width="280" bgcolor="#ADDFFF">' + jsonObj.unitName + '</td></tr>' +
			        '<tr><td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">服務日期： </td>' +  
			      	'<td width="280" bgcolor="#ADDFFF">' + paper.serviceDate + '</td></tr>' + 
			        '<tr><td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">服務時段： </td>' +  
			      	'<td width="280" bgcolor="#ADDFFF">' + paper.srvTime + '</td></tr>' + 
			        '<tr><td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">服務對象：</td>' +
			      	'<td width="280" bgcolor="#ADDFFF">' + jsonObj.reporterKind + '</td></tr>' + 
			        '<tr><td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">服務品質：</td>' + 
			      	'<td width="280" bgcolor="#ADDFFF">' + paper.score + '分</td></tr>' + 
			        '<tr><td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">洽辦事項：</td>' +
			      	'<td width="280" bgcolor="#ADDFFF">' + paper.srvEvent + '</td></tr>' + 
			        '<tr><td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">具體事實：</td>' +
			      	'<td width="280" bgcolor="#ADDFFF">' + paper.description + '</td></tr>' + 
			        '<tr><td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">建議事項：</td>' +
			      	'<td width="280" bgcolor="#ADDFFF">' + paper.suggestion + '</td></tr></table>';
					
					
				  	document.getElementById('loadMsgSub').innerHTML='<table width="100%" class="hairLineTable"><tr><td>' + 
				  	loadingMessage + '</td></tr><tr height="50"><td class="hairLineTdF" align="right" onClick="closeDetail();">' +
				  	'<font color="red" valign="middle"><b>關閉</b></font>' + 
				  	'<img src="' + urlprefix + '/pages/images/deleteRed.jpg" height="30" width="30" align="absmiddle"/></td>'+
				  	+'</tr></table>';
					
				    document.getElementById('loadMsg').style.display='inline';
				    document.getElementById('loadIco').style.display='inline';
				}
			}
		}

	}
	
	function closeDetail(){
		document.getElementById('loadMsg').style.display='none';
	    document.getElementById('loadIco').style.display='none';
	}

	
// -->
</script>
