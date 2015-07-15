<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>

<script type="text/javascript" src="<%=basePath%>pages/include/json2.js"></script>
<script type="text/javascript" src="<%=basePath%>pages/studaffair/include/MyCalendarAD.js"></script>

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
<c:set var="idenType" value="${CounselingReportInit.idenType}" />
<c:set var="reportType" value="${CounselingReportInit.reportType}" />
<form action="/CIS/StudAffair/CounselingReport.do" method="post" name="csForm">
<input type="hidden" name="idenType" value="${CounselingReportInit.idenType}"/>

<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="CounselingRecordMaintain" bundle="TCH"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
		<tr>
			<td width="80" align="right" class="td_lable_normal">輔導期間：</td>
	        <td  height="30" colspan="4" align="left" valign="middle">
	         	學年<input type="text" size="3" name="schoolYear" id="schoolYear" value="${CounselingReportInit.schoolYear}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	         	學期<input type="text" size="1" name="schoolTerm" id="schoolTerm" value="${CounselingReportInit.schoolTerm}">
	        </td>
	    </tr>
	    
        <c:choose>
        <c:when test="${idenType=='T' || idenType=='L'}">
			<!-- 
		      <tr>
				<td width="80" align="right" class="td_lable_normal">班級：</td>
		        <td  height="30" colspan="4" align="left" valign="middle">
		         	<select name="departClass" id="departClass">
		        	<c:forEach items="${ClassInCharge}" var="clazz">
		        		<option value="${clazz.ClassNo}">${clazz.Name}</option>
		        	</c:forEach>
		        	</select>
		        </td>
		      </tr>
		    -->
		      <c:if test="${idenType=='T'}">
		      <tr>
				<td width="80" align="right" class="td_lable_normal">報表種類：</td>
		        <td  height="30" colspan="4" align="left" valign="middle">
			        <select name="reportType" id="reportType">
			        	<c:choose>
			        	<c:when test="${reportType=='1'}">
			        	<option value="1" selected="selected">學習輔導記錄</option>
			        	<option value="2">職涯輔導記錄</option>
			        	<option value="5">輔導次數統計</option>
			        	</c:when>
			        	<c:when test="${reportType=='2'}">
			        	<option value="1">學習輔導記錄</option>
			        	<option value="2" selected="selected">職涯輔導記錄</option>
			        	<option value="5">輔導次數統計</option>
			        	</c:when>
			        	<c:when test="${reportType=='5'}">
			        	<option value="1">學習輔導記錄</option>
			        	<option value="2">職涯輔導記錄</option>
			        	<option value="5" selected="selected">輔導次數統計</option>
			        	</c:when>
			        	<c:otherwise>
			        	<option value="1">學習輔導記錄</option>
			        	<option value="2">職涯輔導記錄</option>
			        	<option value="5">輔導次數統計</option>
			        	</c:otherwise>
			        	</c:choose>
			        </select>
		        </td>
		      </tr>
			</c:if>
 		      <c:if test="${idenType=='L'}">
		      <tr>
				<td width="80" align="right" class="td_lable_normal">報表種類：</td>
		        <td  height="30" colspan="4" align="left" valign="middle">
			        <select name="reportType" id="reportType">
			        	<c:choose>
			        	<c:when test="${reportType=='3'}">
			        	<option value="3" selected="selected">學習輔導記錄</option>
			        	<option value="6">輔導次數統計</option>
			        	</c:when>
			        	<c:when test="${reportType=='6'}">
			        	<option value="3">學習輔導記錄</option>
			        	<option value="6" selected="selected">輔導次數統計</option>
			        	</c:when>
			        	<c:otherwise>
			        	<option value="3">學習輔導記錄</option>
			        	<option value="6">輔導次數統計</option>
			        	</c:otherwise>
			        	</c:choose>
			        </select>
		        </td>
		      </tr>
			</c:if>
        </c:when>
        <c:when test="${idenType=='C' || idenType=='A' || idenType=='S'}">
	      <tr>
			<td width="80" align="right" class="td_lable_normal">班級：</td>
	        <td  height="30" colspan="4" align="left" valign="middle">請選擇部制班級：
	        	<c:set var="campusSel" value="${CounselingReportInit.campusInCharge}"/>
		  		<c:set var="schoolSel" value="${CounselingReportInit.schoolInCharge}"/>
		  		<c:set var="deptSel"   value="${CounselingReportInit.deptInCharge}"/>
		  		<c:set var="classSel"  value="${CounselingReportInit.classInCharge}"/>
        	<!-- 系主任,系助理 -->
        	<c:if test="${idenType=='C'}">
				<%@ include file="/pages/include/ClassSelectC.jsp" %>
        	</c:if>
        	<!-- 學務 -->
        	<c:if test="${idenType=='A'}">
				<%@ include file="/pages/include/ClassSelectSAF.jsp" %>
        	</c:if>
        	<!-- 高階主管 -->
        	<c:if test="${idenType=='S'}">
				<%@ include file="/pages/include/AllClassSelect1.jsp" %>
        	</c:if>
        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;或全校系所：
        	<Select name="department" onchange="restoreSelections('', '', '');">
        	<option value=""></option>
        	<c:forEach items="${depts}" var="dept">
        	<option value="${dept.idno}">${dept.name}</option>
        	</c:forEach>
        	</Select> 
	        </td>
	      </tr>
	      <tr>
			<td width="80" align="right" class="td_lable_normal">報表種類：</td>
	        <td  height="30" colspan="4" align="left" valign="middle">
		        <select name="reportType" id="reportType">
		        <c:forEach items="${CounselReportType}" var="repType">
		        	<c:if test="${repType[0]==reportType}">
		        	<option value="${repType[0]}" selected="selected">${repType[1]}</option>
		        	</c:if>
		        	<c:if test="${repType[0]!=reportType}">
		        	<option value="${repType[0]}">${repType[1]}</option>
		        	</c:if>
		        </c:forEach>
		        </select>
	        </td>
	      </tr>
        </c:when>
        </c:choose>
	</table>
		</td>		
	</tr>
	
	<script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='Query'/>" >');</script>
	
	<!-- Test if have Query Result  -->
	<c:if test="${CounselingReoport != null}" >
	
	    <tr><td height="10"></td></tr>
	    <tr><td><font color="red">共： ${fn:length(CounselingReoport)} 筆資料</font></td></tr>
	    <c:if test="${reportType=='5'}">
	    <tr><td><font color="blue">
	    	導師輔導次數共： ${CounselingReportInit.total} 次, 職涯輔導： ${CounselingReportInit.countT} 次, 學習輔導： ${CounselingReportInit.countU} 次
	    </font></td></tr>
	    </c:if>
	    <c:if test="${reportType=='6'}">
	    <tr><td><font color="blue">
	    	老師輔導次數共： ${CounselingReportInit.countL} 次
	    </font></td></tr>
	    </c:if>
	    <%@include file="/pages/studaffair/include/Displaytag4Checkbox.inc"%>
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${CounselingReoport}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty CounselingReoport}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
 	        	<display:column title="學年"			property="schoolYear"	sortable="true" 	class="left" />
 	        	<display:column title="學期"			property="schoolTerm"	sortable="true" 	class="left" />
 	        	<display:column title="班級"			property="className"	sortable="true" 	class="left" />
				<c:if test="${idenType!='T' && (reportType=='1' || reportType=='2' || reportType=='5')}">
 	        	<display:column title="導師"			property="teacherName"	sortable="true" 	class="left" />
				</c:if>
				<c:if test="${idenType!='L' && (reportType=='3' || reportType=='4' || reportType=='6')}">
 	        	<display:column title="輔導課程"		property="courseName"	sortable="true" 	class="left" />
 	        	<display:column title="老師"			property="teacherName"	sortable="true" 	class="left" />
				</c:if>
				<c:if test="${idenType=='L' && (reportType=='3' || reportType=='6')}">
 	        	<display:column title="輔導課程"		property="courseName"	sortable="true" 	class="left" />
				</c:if>
				<c:if test="${reportType=='1' || reportType=='2' || reportType=='3' || reportType=='4'}">
 	        	<display:column title="學號"			property="studentNo"	sortable="true"  	class="left" />
 	        	<display:column title="姓名"			property="studentName"	sortable="true"  	class="left" />
	        	<display:column title="輔導項目"		property="itemName"		sortable="true"  	class="left" />
	        	<display:column title="輔導日期"		property="cdate"		format="{0,date,yyyy-MM-dd}" 	sortable="true"  	class="center" />
	        	<display:column title="輔導內容" 		property="content" 		sortable="false"  	class="left" />
				</c:if>
				<c:if test="${reportType=='5'}">
 	        	<display:column title="學生數"		property="countStudent"	    sortable="true"  	class="right" />
 	        	<display:column title="已輔導學生數"	property="countStudent_UT"	sortable="true"  	class="right" />
 	        	<display:column title="學習輔導次數"	property="countU"	sortable="true"  	class="right" />
 	        	<display:column title="職涯輔導次數"	property="countT"	sortable="true"  	class="right" />
				</c:if>
				<c:if test="${reportType=='6'}">
 	        	<display:column title="已輔導學生數"	property="countL_UT"	sortable="true"  	class="right" />
 	        	<display:column title="輔導次數"		property="countL"	sortable="true"  	class="right" />
				</c:if>
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>
	<script>
	generateTableBanner('<INPUT type="button" name="method" value="<bean:message key='PreviewPrint'/>" onClick="genReport();"></a>&nbsp;&nbsp;');
	</script>
	</c:if>
	<c:if test="${reportType=='5'}">
    	<tr>
    		<td><br>輸出Excel報表:
    			<a href="<%=basePath%>pages/studaffair/CounselingRpt.jsp?type=excel">
    			<img src="<%=basePath%>pages/images/ico_file_excel.png" border="0">
    			</a>
    		</td>
    	</tr>
	</c:if>
	<c:if test="${reportType=='6'}">
    	<tr>
    		<td><br>輸出Excel報表:
    			<a href="<%=basePath%>pages/studaffair/ CounselingRpt0.jsp?type=excel">
    			<img src="<%=basePath%>pages/images/ico_file_excel.png" border="0">
    			</a>
    		</td>
    	</tr>
	</c:if>
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
<script language="javascript">
// <!-- <![CDATA[
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

function initReq(method, url, asynch, resProcess) {
	try {
		if(resProcess==null){
			//request.onreadystatechange = new function(){};
		}else{
			request.onreadystatechange = resProcess;
		}
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
		window.alert("initReq err!");
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


function genReport(){

	var reportType = document.getElementById("reportType").value;
	var mypath = location.pathname;
 	var urlprefix=mypath.substring(0,mypath.indexOf("/", 1));
	var querys = "?method=getCounselingReport&reportType=" + reportType;
	//querys = querys + "&period=" + period + "&qscope=" + qscope;
	//alert("querys:" + querys);
	//httpRequest("GET",urlprefix + '/AjaxGlobal'+ querys, true, null);

	//var url = urlprefix + "/AjaxGlobal";
	//var jsonQuery = '{"method":"getCounselingReport"}';
	//doJSONRequest(url, jsonQuery, setAutoInput_agent);
	subwin = window.open(urlprefix + '/AjaxGlobal'+ querys, "預覽列印",
			"width=600,height=600,scrollbars=yes,resizable=yes");


}
// ]]> -->
</script>