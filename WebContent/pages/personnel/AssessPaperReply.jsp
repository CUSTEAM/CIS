<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript" src="<%=basePath%>pages/include/json2.js"></script>

<!-- Calendar view 1.2 -->
<link type="text/css" href="<%=basePath%>pages/include/calendarview-1.2/stylesheets/calendarview.css" rel="stylesheet" />	
<script type="text/javascript" src="<%=basePath%>pages/include/calendarview-1.2/javascripts/prototype.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/include/calendarview-1.2/javascripts/calendarview.js"></script>
<script>
      function setupCalendars() {
        // Embedded Calendar
        //Calendar.setup(
        //  {
        //    dateField: 'embeddedDateField',
        //    parentElement: 'embeddedCalendar'
        //  }
        //)

        // Popup Calendar
        Calendar.setup(
          {
            //dateField: 'startDate',
            dateField: 'sdate',
            triggerElement: 'sCalImg',
            dateFormat: '%Y-%m-%d'
          }
        )
      }

      Event.observe(window, 'load', function() { setupCalendars() })
</script>
<!-- End Calendar view 1.2 -->


<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.style2 {color: #FF0000}
// -->
</style>

<!-- Begin Content Page Table Header -->
<form action="/CIS/Student/AssessPaperReply.do" method="post" name="ceForm">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="Title.AssessPaperReply" bundle="PSN"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<c:set var="paper" value="${AssessPaper}"/>
	
	<c:choose>
	<c:when test="${AssessPaperEdit != null}">
	<c:set var="formback" value="${AssessPaperEdit}"/>
	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <tr>
         <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">服務編號： </td>
      	<td>
      		<input type="text" name="serviceNo" value="${formback.serviceNo}" maxlength="9" onkeyup="getPaper();">(編號中不會有英文字母「O」)
     	</td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">服務人員：</td>
        <td  height="30" align="left" valign="middle">
        <input type="text" name="cname" id="cname" value="${formback.cname}" readonly="readonly" style="border: none;">
        </td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">所屬單位：</td> 
      	<td>
      	<input type="text" name="unit" id="unit" value="${formback.unit}"  readonly="readonly" style="border: none;">
      	</td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">服務日期： </td> 
      	<td>
			<table width="100%"><tr>
	        <td  width="90" class="hairlineTdF">
	            <input type="text" name="sdate" id="sdate" size="10" readonly="readonly" maxlength="10" value="${formback.sdate}">
	        </td>
			<td width="20">	  
	     			<img src="<%=basePath%>pages/images/cal.gif" name="sCalImg" id="sCalImg" width="20" height="20"
			      		align="top" style="cursor:hand" alt="點選此處選擇日期">&nbsp;
			</td>
			<td>&nbsp;</td>
			
	      	</tr>
	      	</table>
	     </td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">服務時段：</td> 
      	<td>
		<c:set var="times">早上,中午,下午,晚上</c:set>
      	<c:forTokens var="ttime" items="${times}" delims=",">
      		<c:if test="${formback.srvTime==ttime}">
      		<input type="radio" name="srvTime" value="${ttime}" checked="checked">${ttime}
      		</c:if>
      		<c:if test="${formback.srvTime!=ttime}">
      		<input type="radio" name="srvTime" value="${ttime}">${ttime}
      		</c:if>
      	</c:forTokens>
      	</td>
      </tr>
      <tr>
         <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">洽辦事項： </td>
      	<td>
      		<input type="text" name="srvEvent" value="${formback.srvEvent}"><font color="red">必填</font>
     	</td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">服務品質：</td>
      	<td>
      	<c:set var="scores">5,4,3,2,1</c:set>
      	<c:forTokens var="score" items="${scores}" delims=",">
      		<c:if test="${formback.score==score}">
      		<input type="radio" name="score" value="${score}" checked="checked">${scoreName[score-1]}
      		</c:if>
      		<c:if test="${formback.score!=score}">
      		<input type="radio" name="score" value="${score}">${scoreName[score-1]}
      		</c:if>
      	</c:forTokens>
     	</td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">具體事實：</td>
      	<td>
      		<font color="red">點選服務品質「非常滿意」、「不滿意」、「非常不滿意」請務必填寫具體事實：</font><br/>
      		<textarea rows="2" cols="50" name="description">${formback.description}</textarea>
     	</td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">建議事項：</td>
      	<td>
      		<font color="red">點選服務品質「不滿意」、「非常不滿意」請務必填寫建議事項(200字以內)：</font><br/>
      		<textarea rows="2" cols="50" name="suggestion">${formback.suggestion}</textarea>
     	</td>
      </tr>
	</table>
	</c:when>
	
	<c:otherwise>
	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <tr>
         <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">服務編號： </td>
      	<td>
      		<input type="text" name="serviceNo" value="" maxlength="9" onkeyup="getPaper();">(編號中不會有英文字母「O」)
     	</td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">服務人員：</td>
        <td  height="30" align="left" valign="middle">
        <input type="text" name="cname" id="cname" value=""  size="10" readonly="readonly" style="border: none;">
        </td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">所屬單位：</td> 
      	<td>
      	<input type="text" name="unit" id="unit" value="" size="10" readonly="readonly" style="border: none;">
      	</td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">服務日期： </td> 
      	<td>
			<table width="100%"><tr>
	        <td  width="90" class="hairlineTdF">
	            <input type="text" name="sdate" id="sdate" size="10" readonly="readonly" maxlength="10" value="${formback.cdate}">
	        </td>
			<td width="20">	  
	     			<img src="<%=basePath%>pages/images/cal.gif" name="sCalImg" id="sCalImg" width="20" height="20"
			      		align="top" style="cursor:hand" alt="點選此處選擇日期">&nbsp;
			</td>
			<td>&nbsp;</td>
			
	      	</tr>
	      	</table>
	     </td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">服務時段：</td> 
      	<td>
      		<input type="radio" name="srvTime" value="早上" checked="checked">早上
     		<input type="radio" name="srvTime" value="中午">中午
      		<input type="radio" name="srvTime" value="下午">下午
     		<input type="radio" name="srvTime" value="晚上">晚上
      	</td>
      </tr>
      <tr>
         <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">洽辦事項： </td>
      	<td>
      		<input type="text" name="srvEvent" value=""><font color="red">必填</font>
     	</td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">服務品質：</td>
      	<td>
      		<input type="radio" name="score" value="5" checked="checked">非常滿意
     		<input type="radio" name="score" value="4">滿意
      		<input type="radio" name="score" value="3">普通
     		<input type="radio" name="score" value="2">不滿意
      		<input type="radio" name="score" value="1">非常不滿意
     	</td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">具體事實：</td>
      	<td>
      		<font color="red">點選服務品質「非常滿意」、「不滿意」、「非常不滿意」請務必填寫具體事實：</font><br/>
      		<textarea rows="2" cols="50" name="description"></textarea>
     	</td>
      </tr>
      <tr>
        <td  height="30" width="70" align="left" valign="middle" bgcolor="#d8bfd8">建議事項：</td>
      	<td>
      		<font color="red">點選服務品質「不滿意」、「非常不滿意」請務必填寫建議事項(200字以內)：</font><br/>
      		<textarea rows="2" cols="50" name="suggestion"></textarea>
     	</td>
      </tr>
	</table>
	
	
	</c:otherwise>
	</c:choose>

		</td>		
	</tr>
	
	<script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='Save'/>" >&nbsp;&nbsp;'+
	'<Input type="submit" name="method" value="<bean:message key='Cancel'/>" >');</script>
	
	<!-- Test if have Query Result  -->
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
<script>
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
	
var orig_sno = '';
function getPaper(){
	var snoObj = document.getElementById("serviceNo");
	sno = new String(snoObj.value);
	var cname = document.getElementById("cname");
	var unit= document.getElementById("unit");
	
	if(sno.length<9) {
		cname.value = "";
		unit.value = "";
		return;
	}
	orig_sno = sno;
 	var mypath = location.pathname;
 	var urlprefix=mypath.substring(0,mypath.indexOf("/", 1));
	var url = urlprefix + "/AjaxGlobal";
	var jsonQuery = '{"method":"getAssessPaper", "serviceNo":"' + sno + '"}';
	//window.alert(jsonQuery);
	doJSONRequest(url, jsonQuery, fillPaperDetail);
		
	snoObj.disabled = true;
}

function fillPaperDetail() {
	var snoObj = document.getElementById("serviceNo");
	var sno = snoObj.value;
	
	var cname = document.getElementById("cname");
	var unit= document.getElementById("unit");
	
	if(handleState()){
		//window.alert("sno:" + sno + ", orig:" + orig_sno);
		if(request.responseText && sno == orig_sno){
			//window.alert(request.responseText);
			var jsonObj = JSON.parse(request.responseText);
			if(jsonObj != null) {
				cname.value = jsonObj.cname;
				unit.value = jsonObj.unitName;
			}
		}
	}
	snoObj.disabled = false;
};


</script>