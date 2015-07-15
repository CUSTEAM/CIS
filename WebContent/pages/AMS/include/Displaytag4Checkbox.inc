<%@ page language="java" contentType="text/html;charset=UTF-8" %>
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
			alert("請選取一項以資茲改");
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
	
	function checkSelectForRevoke(cookieName) {
		var iCount;
		iCount = getCookie(cookieName + "Count");
		if (iCount < 1) {
			alert("請選取一張假單以茲銷假");
			return false;
		} else if (iCount > 1) {
			alert("僅能選取一張假單以茲銷假");
			return false;
		} else {
			return true;
		}
	};

	function generateReport(oid, label){
		var url = "/CIS/AjaxGlobal?method=DocReport&Oid=" + oid + "&sessionNumber=" + new Date().getTime() + Math.floor(Math.random()*999);
		document.write("<a href=\"" + url + "\"><img src=\"/CIS/pages/images/printer.gif\" alt=\"" + label + "\" style=\"border-style:none;\"/></a>");	
	};
	//LEO-20091211
	function generateMeetingAskLeave(oid, label){
		var url = "/CIS/AjaxGlobal?method=MeetingAskLeave&Oid=" + oid + "&sessionNumber=" + new Date().getTime() + Math.floor(Math.random()*999);
		document.write("<a href=\"" + url + "\"><img src=\"/CIS/pages/images/printer.gif\" alt=\"" + label + "\" style=\"border-style:none;\"/></a>");	
	};
	
	
// -->
</script>
