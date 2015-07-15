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
 			document.getElementById('content').innerHTML ='';
 			var xmldoc = request.responseXML;
 			
 			
 			var dilgs = xmldoc.getElementsByTagName("dilgPrompt");
 			var modobj = xmldoc.getElementsByTagName("mode");
 			var studentNo = "";
  			//if(modobj.length == 0) {
 			//	alert("nodata");
 			//} else {
 			//	alert("Mode:" + xmldoc.getElementsByTagName("mode").length);
 			//}
 			
 			var mode=xmldoc.getElementsByTagName("mode")[0].firstChild.data;
 			if(mode!='notfound') {		
 			studentNo=xmldoc.getElementsByTagName("studentNo")[0].firstChild.data;
 			var studentName=xmldoc.getElementsByTagName("studentName")[0].firstChild.data;
 			var departClass=xmldoc.getElementsByTagName("departClass")[0].firstChild.data;
 			var deptClassName=xmldoc.getElementsByTagName("deptClassName")[0].firstChild.data;
			}
			
			daynite = chkDayNite(studentNo);
					
 			var tmpString='<br>';
 			tmpString=tmpString+'<table border="0" align="center" cellpadding="3" cellspacing="1" bgcolor="#CFE69F" width="100%" class="table1">';
 			if(mode == 'all') {
				tmpString = tmpString + '<tr><td colspan=17 bgcolor=#ffd700>' + studentNo + '&nbsp;&nbsp;';
				tmpString = tmpString + studentName + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
				tmpString = tmpString + departClass + '&nbsp;&nbsp';
				tmpString = tmpString + deptClassName + '</td></tr>';
			
 				tmpString=tmpString+'<tr>';
  				tmpString=tmpString+'<td align="center" bgcolor="#f0fcd7">日期</td>';
				tmpString=tmpString+'<td width="30" align="center" bgcolor="#f0fcd7">升旗</td>';
				tmpString=tmpString+'<td width="30" align="center" bgcolor="#f0fcd7">1</td>';
				tmpString=tmpString+'<td width="30" align="center" bgcolor="#f0fcd7">2</td>';
				tmpString=tmpString+'<td width="30" align="center" bgcolor="#f0fcd7">3</td>';
				tmpString=tmpString+'<td width="30" align="center" bgcolor="#f0fcd7">4</td>';
 				tmpString=tmpString+'<td width="30" align="center" bgcolor="#f0fcd7">5</td>';
				tmpString=tmpString+'<td width="30" align="center" bgcolor="#f0fcd7">6</td>';
				tmpString=tmpString+'<td width="30" align="center" bgcolor="#f0fcd7">7</td>';
				tmpString=tmpString+'<td width="30" align="center" bgcolor="#f0fcd7">8</td>';
				tmpString=tmpString+'<td width="30" align="center" bgcolor="#f0fcd7">9</td>';
 				tmpString=tmpString+'<td width="30" align="center" bgcolor="#f0fcd7">10</td>';
				if(daynite==true) {
				tmpString=tmpString+'<td width="30" align="center" bgcolor="#f0fcd7">11</td>';
				tmpString=tmpString+'<td width="30" align="center" bgcolor="#f0fcd7">12</td>';
				tmpString=tmpString+'<td width="30" align="center" bgcolor="#f0fcd7">13</td>';
				tmpString=tmpString+'<td width="30" align="center" bgcolor="#f0fcd7">14</td>';
 				tmpString=tmpString+'<td width="30" align="center" bgcolor="#f0fcd7">15</td>';
 				} else {
 				tmpString=tmpString+'<td width="30" align="center" bgcolor="#f0fcd7">N1</td>';
				tmpString=tmpString+'<td width="30" align="center" bgcolor="#f0fcd7">N2</td>';
				tmpString=tmpString+'<td width="30" align="center" bgcolor="#f0fcd7">N3</td>';
				tmpString=tmpString+'<td width="30" align="center" bgcolor="#f0fcd7">N4</td>';
 				tmpString=tmpString+'<td width="30" align="center" bgcolor="#f0fcd7">N5</td>';
 				}
 				tmpString=tmpString+'</tr>';
 				
 				var timeoff = xmldoc.getElementsByTagName("dilgInfo");
 				var current;
 				var status;
 				var tfdate;
 				var absName;
 				//alert("Timeoff length=" + timeoff.length);
 				for(var i=0; i<timeoff.length; i++) {
 					tmpString=tmpString+'<tr>';
 					
 					current = timeoff[i];
 					if(current.getElementsByTagName("ddate").length > 0) {
 						tfdate = current.getElementsByTagName("ddate")[0].firstChild.data;
 						tmpString=tmpString+'<td align="center" bgcolor="#f0fcd7">' + tfdate + '</td>';
 						for(var j=0; j <=15; j++){
 						absName = "abs" + j;
 							status = current.getElementsByTagName(absName)[0].firstChild.data;
 							if(status == 'null') status="";
 							tmpString=tmpString+'<td align="center" bgcolor="#f0fcd7">' + status + '</td>';	
 						}
 						tmpString=tmpString+'</tr>';
 					} else {
 						tmpString='<br><br><tr><td align=center><font color=red>查無曠缺記錄</font></td></tr>';
 					}
 				}
 			
 			} else if(mode=='subject') {
				tmpString = tmpString + '<tr><td bgcolor=#ffd700>' + studentNo + '&nbsp;&nbsp;';
				tmpString = tmpString + studentName + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
				tmpString = tmpString + departClass + '&nbsp;&nbsp';
				tmpString = tmpString + deptClassName + '</td></tr>';
			
 				var timeoff = xmldoc.getElementsByTagName("dilgInfo");
 				var current;
 				var subjectName;
 				var period, tfLimit, timeOff;
 				var warnning, absType, dtimeClass;
 				//alert("Timeoff length=" + timeoff.length);
 				for(var i=0; i<timeoff.length; i++) {
 					
 					current = timeoff[i];
 					if(current.getElementsByTagName("subjectName").length > 0) {
  						tmpString=tmpString+'<tr><td><table width="100%" style="background:silver; border:1px 1px 1px 2px solid">';
  						
 						tmpString=tmpString+'<tr>';
  						tmpString=tmpString+'<td align="center" bgcolor="#f0fcd7">科       目</td>';
						tmpString=tmpString+'<td align="center" bgcolor="#f0fcd7">每週節數</td>';
						tmpString=tmpString+'<td align="center" bgcolor="#f0fcd7">扣考節數</td>';
						tmpString=tmpString+'<td align="center" bgcolor="#f0fcd7">缺課節數</td>';
						tmpString=tmpString+'<td align="center" bgcolor="#f0fcd7">遠距缺課</td>';
						//tmpString=tmpString+'<td align="center" bgcolor="#f0fcd7">備XXX註</td>';
 						tmpString=tmpString+'</tr>';
 				
   						tmpString=tmpString+'<tr>';
 						subjectName = current.getElementsByTagName("subjectName")[0].firstChild.data;
 						tmpString=tmpString+'<td width="40%" align="left" bgcolor="#f0fcd7"><font color=blue>' + subjectName + '</font></td>';
 						period = current.getElementsByTagName("period")[0].firstChild.data;
 						tmpString=tmpString+'<td width="10%" align="center" bgcolor="#f0fcd7">' + period + '</td>';
 						tfLimit = current.getElementsByTagName("tfLimit")[0].firstChild.data;
 						tmpString=tmpString+'<td width="10%" align="center" bgcolor="#f0fcd7">' + tfLimit + '</td>';
 						timeOff = current.getElementsByTagName("timeOff")[0].firstChild.data;
 						tmpString=tmpString+'<td width="10%" align="center" bgcolor="#f0fcd7">' + timeOff + '</td>';
 						elearn = current.getElementsByTagName("elearnDilg")[0].firstChild.data;
 						tmpString=tmpString+'<td width="10%" align="center" bgcolor="#f0fcd7">' + elearn + '</td>';
 						//warnning = current.getElementsByTagName("warnning")[0].firstChild.data;
 						//if(warnning=='yes') {
 							//tmpString=tmpString+'<td width="20%" align="center" bgcolor="#f0fcd7"><font color=red>扣考</font></td>';
 							//tmpString=tmpString+'<td width="20%" align="center" bgcolor="#f0fcd7"><font color=red></font></td>';
 						//} else if(warnning=='no') {
 							//tmpString=tmpString+'<td width="20%" align="center" bgcolor="#f0fcd7">&nbsp;</td>';
 						//}
  						tmpString=tmpString+'</tr>';
 
  						absType = current.getElementsByTagName("absType")[0].firstChild.data;
  						dtimeClass = current.getElementsByTagName("dtimeClass")[0].firstChild.data;
 						
 						tmpString=tmpString+'<tr><td colspan="6">';
 						tmpString += dtimeClass + '<br>' + absType;
 						tmpString += '</td></tr></table></td></tr>';
 					} else {
  						tmpString=tmpString+'<tr>';
 						tmpString='<br><br><tr><td align=center><font color=red>查無曠缺記錄</font></td></tr>';
 					}
 					
 				}
 				
 			} else if(mode=='notfound') {
 				tmpString='<br><br><tr><td align=center><font color=red>查無此學生</font></td></tr>';
 			}
 			tmpString = tmpString + '</table>';
 			document.getElementById('content').innerHTML =tmpString;
 		}			
 	}
 }
 	function getTimeOff(mode) { 
  	 	var studno = document.getElementById("studno");
 		var studentno = studno.value;
 		var mypath = location.pathname;
 		var urlprefix=mypath.substring(0,mypath.indexOf("/", 1));
 		//alert("urlprefix:" + urlprefix);
		httpRequest("GET",urlprefix + '/AjaxGetStudentTimeOff?mode='+encodeURIComponent(mode) + '&studentno=' + studentno+'&'+Math.floor(Math.random()*999),true,parseResult);
			 	
	}

//-->
</script>