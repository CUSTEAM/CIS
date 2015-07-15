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

	function chkDate(dstText){
		var vdate = dstText.match(/(\d{2})\/(\d{2})\/(\d{2})/);
		if(!vdate || !dstText) return false;
		var year = RegExp.$1;
		var month = RegExp.$2;
		var day = RegExp.$3;
		//alert("chkDate->" + year+":" + month+":"+day);
		var wyear = year + 1911;
		if(((wyear%4 == 0)&&(wyear%100 != 0))||(wyear%400 == 0)) yflag = true;
		if(month > 12) return false;
		else{
			switch(month){
			case 2:
				if(yflag){
					if(day > 29) return false;
				}else{
					if(day > 28) return false;
				}
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				if(day > 30) return false;
			default:
				if(day > 31) return false;
			}
		}
		return true;
	}
	
	function precheck(){
		var isValidate = true;
	
		var campus = document.getElementsByName("campusInCharge")[0];
		var campusv = campus.value;
		var school = document.getElementsByName("schoolInCharge")[0];
		var schoolv = school.value;
		var depart = document.getElementsByName("deptInCharge")[0];
		var departv = depart.value;
		var clazz = document.getElementsByName("classInCharge")[0];
		var clazzv = clazz.value;
		var period = document.getElementsByName("period")[0];
		var periodv = period.value;
		//var dateStart = document.getElementsByName("DateStart")[0];
		//var dateStartv = dateStart.value;
		var dateEnd = document.getElementsByName("DateEnd")[0];
		var dateEndv = dateEnd.value;
		
		//if ("All" == campusv || "All" == schoolv || "All" == departv || "All" == clazzv) {
		if ("All" == campusv || "All" == schoolv || "All" == departv ) {
			alert("班級選擇範圍過大!");
			campus.focus();
			isValidate = false;
		}
		if(isNaN(periodv)){
			alert("嚴重曠缺節數只能輸入數字!");
			WeekStart.focus();
			isValidate = false;
		}
		//if(!(chkDate(dateStartv) || chkDate(dateEndv))){
		if(!(chkDate(dateEndv))){
			alert("日期輸入格式錯誤!");
			isValidate = false;
		}
		return isValidate;
	}

function parseResult() {
 	if(request.readyState==READ_STATE_COMPLETE){ 
 		if(request.status==RESPONSE_STATUS_OK){
 			var xmldoc = request.responseXML;
 			
 			
 			var departClass = xmldoc.getElementsByTagName("departClass")[0].firstChild.data;
 			var recnum = xmldoc.getElementsByTagName("recnum")[0].firstChild.data;
 			
			if(departClass != 'notFound'){
 				document.getElementById(departClass).innerHTML ='';
					
	 			var tmpString='';
	 	 		var timeoff = xmldoc.getElementsByTagName("dilgInfo");
	 			
	 			tmpString=tmpString+'<table border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#FF0000" width="100%" class="table1">';
	 			
		 		if(recnum == 0) {
	 				tmpString='<tr><td align=center><font color=red>該班無嚴重曠缺學生!!!</font></td></tr>';
	 			}else{
		 			tmpString=tmpString+'<tr>';
		  			tmpString=tmpString+'<td align="center" bgcolor="#f0fcd7">學        號</td>';
					tmpString=tmpString+'<td align="center" bgcolor="#f0fcd7">姓        名</td>';
					tmpString=tmpString+'<td align="center" bgcolor="#f0fcd7">家        長</td>';
					tmpString=tmpString+'<td align="center" bgcolor="#f0fcd7">電        話</td>';
					tmpString=tmpString+'<td align="center" bgcolor="#f0fcd7">累計曠課</td>';
		 			tmpString=tmpString+'</tr>';
		 				
		 			var current;
		 			var studentNo, studentName, parentName, TEL;
		 			var period;
		 			//alert("Timeoff length=" + timeoff.length);
		 			for(var i=0; i<timeoff.length; i++) {
		 				tmpString=tmpString+'<tr>';
		 					
		 				current = timeoff[i];
		 					studentNo = current.getElementsByTagName("studentNo")[0].firstChild.data;
		 					tmpString=tmpString+'<td align="center" bgcolor="#f0fcd7">' + studentNo + '</td>';
		 					studentName = current.getElementsByTagName("studentName")[0].firstChild.data;
		 					tmpString=tmpString+'<td align="center" bgcolor="#f0fcd7">' + studentName + '</td>';
		 					parentName = current.getElementsByTagName("parentName")[0].firstChild.data;
		 					tmpString=tmpString+'<td align="center" bgcolor="#f0fcd7">' + parentName + '</td>';
		 					TEL = current.getElementsByTagName("TEL")[0].firstChild.data;
		 					tmpString=tmpString+'<td align="center" bgcolor="#f0fcd7">' + TEL + '</td>'; 						
		 					period = current.getElementsByTagName("period")[0].firstChild.data;
		 					tmpString=tmpString+'<td align="center" bgcolor="#f0fcd7">' + period + '</td>';
		 					tmpString=tmpString+'</tr>';
		 			}
	 			}
	 			tmpString = tmpString + '</table>';
	 			document.getElementById(departClass).innerHTML =tmpString;
	 		}
	 	}			
 	}
 }
// 	function getTimeOffSerious(depart_class, dateStart, dateEnd, period, qscope) { 
 	function getTimeOffSerious(depart_class, dateEnd, period, qscope) { 
 		if(precheck()){
	 	 	//var studno = document.getElementById("studno");
	 		//var studentno = studno.value;
	 		var mypath = location.pathname;
	 		var urlprefix=mypath.substring(0,mypath.indexOf("/", 1));
	 		//var querys = "?method=GetTimeOffSerious&departClass=" + depart_class + "&DateStart=" + dateStart + "&DateEnd=" + dateEnd;
	 		var querys = "?method=GetTimeOffSerious&departClass=" + depart_class + "&DateEnd=" + dateEnd;
	 		querys = querys + "&period=" + period + "&qscope=" + qscope;
	 		//alert("querys:" + querys);
			httpRequest("GET",urlprefix + '/AjaxGlobal'+ querys,true,parseResult);
		}
	}

	function hiddenMe(obj){
		obj.innerHTML="";
		//obj.style.visibility="hidden";
	}

//-->
</script>
