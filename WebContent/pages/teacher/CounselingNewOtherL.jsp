<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/CodeSelection.js" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>

<script type="text/javascript" src="<%=basePath%>pages/include/json2.js"></script>


<style type="text/css">
<!--
.input {
	width:20px;
}
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.style2 {color: #FF0000}
-->
</style>
			<!-- for calendar use -->
			<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none; z-index:65530;">
				<tr>
					<td id="ds_calclass"></td>
				</tr>
			</table>
			

<form action="/CIS/Teacher/CounselingL.do" method="post" name="bpForm">

<c:set var="tcnt" value="1"/>

<table width="100%" cellpadding="0" cellspacing="0">

<!-- Begin Content Page Table Header -->
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="CounselingRecordEdit" bundle="TCH"/></B></div>');</script>	  
	<tr>
		<td align="left"> 
<!-- End Content Page Table Header -->

	<table cellspacing="5" class="empty-border" width="100%">
	
	<c:choose>
	<c:when test="${StudCounselingNewOtherL != null}">
		<tr>
			<td><table width="100%"><tr>
			<td width="80" align="right">學號：</td>
	        <td  height="30" colspan="4" align="left" valign="middle" class="hairLineTd">
				<input name="studentNo" id="studentNo" size="8" onkeyup="getStudentName();">&nbsp;&nbsp;
				<div id="studentName"></div>
	        </td>
			<td>&nbsp;</td>
	        </tr></table></td>
        </tr>
        <tr>
	      	<td><table width="100%"><tr>
			<td width="80" align="right" class="td_lable_normal">輔導日期：</td>
			<td width="90">
	            <input type="text" name="cdate" size="10" value="${StudCounselingNewOtherL.cdate}" onclick="ds_sh(this), this.value='';" onfocus="ds_sh(this), this.value='';"
	                           autocomplete="off" style="ime-mode:disabled" readonly="ture"/>
			</td>
			<td width="20">	  
				<img src="<%=basePath%>pages/images/date.gif" align="middle" style="text-align:center;" onClick="ds_sh(document.getElementById('cdate')), document.getElementById('cdate').value='';" />
			</td>
			<td>&nbsp;</td>
			
	      	</tr>
	      	</table></td>
        </tr>
        <tr>
        	<td><div  style="z-index:-1;"><table width="100%"><tr>
        	<td width="80" align="left" valign="middle">輔導項目：</td>
        	<td align="left" valign="middle" class="hairLineTd">
        	<select name="itemNo" id="itemNo" style="z-index:1;">
        	<c:forEach items="${StudCounselCodeL}" var="code">
        		<c:if test="${code.itemNo==StudCounselingNewOtherL.itemNo}">
        		<option value="${code.itemNo}" selected="selected">${code.itemName}</option>
        		</c:if>
        		<c:if test="${code.itemNo!=StudCounselingNewOtherL.itemNo}">
        		<option value="${code.itemNo}">${code.itemName}</option>
        		</c:if>
        	</c:forEach>
        	</select>
        	</td>
 			<td>&nbsp;</td>
        	</tr></table></div></td>
        </tr>
        <tr>
        	<td><table width="100%"><tr>
        	<td width="80" align="left" valign="middle">輔導內容：</td>
        	<td align="left" valign="middle" class="hairLineTd">
        	<textarea name="content" rows="3" cols="60">${StudCounselingNewOtherL.content}</textarea>
        	</td>
 			<td>&nbsp;</td>
        	</tr></table></td>
        </tr>
	</c:when>
	
	<c:otherwise>
		<tr>
			<td><table width="100%"><tr>
				<td width="80" align="right">學號：</td>
		        <td  height="30" colspan="4" align="left" valign="middle" class="hairLineTd">
				<input name="studentNo" id="studentNo" size="8" onkeyup="getStudentName(this);">&nbsp;&nbsp;
				<input name="studentName" id="studentName" readonly style="border-style:none;">&nbsp;&nbsp;
		        </td>
			<td>&nbsp;</td>
		    </tr></table></td>
        </tr>
        <tr>
	      	<td><table width="100%"><tr>
			<td width="80" align="right" class="td_lable_normal">輔導日期：</td>
			<td width="90">
	            <input type="text" name="cdate" size="10" value="" onclick="ds_sh(this), this.value='';" onfocus="ds_sh(this), this.value='';"
	            autocomplete="off" style="ime-mode:disabled" readonly="ture"/>
			</td>
			<td width="20">	  
				<img src="<%=basePath%>pages/images/date.gif" align="middle" style="text-align:center;" onClick="ds_sh(document.getElementById('cdate')), document.getElementById('cdate').value='';" />
			</td>
			<td>&nbsp;</td>
	      	</tr>
	      	</table></td>
        </tr>
        <tr>
	        <td align="left" style="z-index:0;"><table width="100%"><tr>
	        	<td width="80" align="right" valign="middle">輔導項目：</td>
	        	<td align="left" valign="middle"><div  style="z-index:0;">
	        	<select name="itemNo" id="itemNo">
	        	<c:forEach items="${StudCounselCodeL}" var="code">
	        		<option value="${code.itemNo}">${code.itemName}</option>
	        	</c:forEach>
	        	</select></div>
	        	</td>
			<td>&nbsp;</td>
	        </tr></table></td>
        </tr>
        <tr>
	        <td align="left"><table width="100%"><tr>
	        	<td width="80" align="right" valign="middle">輔導內容：</td>
	        	<td align="left" valign="middle">
	        	<textarea name="content" rows="3" cols="60"></textarea>
	        	</td>
			<td>&nbsp;</td>
	        </tr></table></td>
        </tr>
	</c:otherwise>
	
	</c:choose>	
	</table>
	</td>
	</tr>
	
	<input type="hidden" name="opmode" value="" id="opmode">	
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Save'/>" id="ok">&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Cancel'/>" id="cancel">');
	//generateTableBanner('<INPUT type="button" name="method" value="<bean:message key='Cancel'/>" id="cancel" onClick="chgopmode(0);document.forms[0].submit();">');
	</script>
</table>
</form>

<script>
<!--
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
	
	var orig_No = '';
	function getStudentName(studObj){
		document.getElementById("studentName").value = "";			
		studNo = new String(studObj.value);
		//window.alert(studNo);
		if(studNo.length>5){
			orig_No = studNo;
		 	var mypath = location.pathname;
		 	var urlprefix=mypath.substring(0,mypath.indexOf("/", 1));
			var url = urlprefix + "/AjaxGlobal";
			var jsonQuery = '{"method":"getStudent", "studentNo":"' + studNo + '"}';
			//window.alert(jsonQuery);
			doJSONRequest(url, jsonQuery, fillStudName);
		}
	}

	function fillStudName() {
		var studNoObj = document.getElementById("studentNo");
		var newStudNoValue = studNoObj.value;
		if(handleState()){
			if(request.responseText && newStudNoValue == orig_No){
				var jsonObj = JSON.parse(request.responseText);
				if(jsonObj != null) {
					document.getElementById("studentName").value = jsonObj.studentName;
				}
			}
		}
	};
	
history.go(1);
//-->
</script>
<%@ include file="/pages/studaffair/include/MyCalendarAD.inc" %>	
