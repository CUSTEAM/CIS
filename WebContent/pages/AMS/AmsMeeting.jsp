<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/AllClassInCharge.js" %>
<%@ include file="/pages/include/ClassSelection.js" %>
<%@ include file="/pages/include/tooltips.js" %>

<script>
history.go(1);
function check() {
	if (document.getElementById("meetingDate").value == "") {
		alert("集會日期不可為空白");
		document.getElementById("meetingDate").focus();
		return false;	
	} else if (document.getElementById("meetingName").value == "") {
		alert("集會名稱不可為空白");
		document.getElementById("meetingName").focus();
		return false;
	}
	
	var startNode = document.getElementById("startNode").value;
	var endNode = document.getElementById("endNode").value;
	if (startNode > endNode) {
		alert("起訖節次輸入有誤,起始節次不該大於結束節次");
		document.getElementById("startNode").focus();
		return false;
	}
	return true;
}

function delCheck() {
	var iCount = getCookie("amsMeetingDataCount");
	if (iCount == 0) {
		alert("請至少勾選一項重要集會進行刪除!!");
		return false;
	} else {
		if(confirm("確定刪除[" + iCount + "]項重要集會?"))
			return true;
		else 
			return false;
	}
	return true;
}

function modifyCheck() {
	var iCount = getCookie("amsMeetingDataCount");
	if (iCount == 0) {
		alert("請至少勾選一項重要集會進行修改!!");
		return false;
	} else if (iCount != 1) {
		alert("請勾選一項重要集會進行修改!!");
		return false;
	}
	return true;
}
</script>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/AMS/AmsMeeting" method="post" onsubmit="init('系統建立資料中,請稍候...')">
<!-- 標題列 Start -->
	<tr>
		<td class="fullColorTable" width="100%">
	    	<table width="100%" cellpadding="0" cellspacing="0" border="0">
	      		<tr height="30">
	        		<script>generateTableBanner('<div class="gray_15"><B>重 要 集 會 維 護</B></div>');</script>
	      		</tr>
	    	</table>
	  	</td>
	</tr>
<!-- 標題列 End -->

	<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	<tr>
		<td>
	    	<table width="100%" cellpadding="0" cellspacing="0">
		    	<tr>
		      		<td align="center">  
	            		<display:table name="${AmsMeetings}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
		  		      		<c:if test="${empty AmsMeetings}">
		     		    		<%@ include file="../include/NoBanner.jsp" %>
		  		      		</c:if>
		              		<display:column title="<script>generateTriggerAll(${fn:length(AmsMeetings)}, 'amsMeetingData');</script>" class="center" >
		                		<script>generateCheckbox("${row.oid}", "amsMeetingData");</script></display:column>
		                	<display:column title="學年學期" sortable="true" class="center">
		                		<c:out value="${row.schoolYear}"/>.<c:out value="${row.schoolTerm}"/>
		                	</display:column>
	 	              		<display:column title="集會名稱" property="name" sortable="true" class="center" />
	 	              		<display:column title="日期" sortable="true" class="center">
	 	              			<fmt:formatDate pattern="yyyy/MM/dd" value="${row.meetingDate}"/>
	 	              		</display:column>
		              		<display:column title="起始節次" property="startNode" sortable="false" class="center" />
		              		<display:column title="結束節次" property="endNode" sortable="false" class="center" />
		              		<display:column title="員工身分" sortable="false" class="center">
		              			<c:choose>
		              				<c:when test="${row.emplType == 'A'}">全體教職員</c:when>
		              				<c:when test="${row.emplType == '1'}">專任教師</c:when>
		              				<c:when test="${row.emplType == '2'}">兼任教師</c:when>
		              				<c:when test="${row.emplType == '3'}">職員工</c:when>
		              				<c:when test="${row.emplType == '4'}">軍護教師</c:when>
		              				<c:otherwise>&nbsp;</c:otherwise>
		              			</c:choose>
		              		</display:column>
		              		<display:column title="基數" property="base" sortable="false" class="center" />
		              		<display:column title="下載" sortable="false" class="center">
		              			<html:link page="/AMS/AmsMeeting.do?method=emplList" paramName="row" paramId="oid" paramProperty="oid">
		              				<img src='images/ico_file_excel1.png' border='0' title='出席人員名單下載'/>
		              			</html:link>
		              		</display:column>	              	              
 	            		</display:table>
 	          		</td>
 	        	</tr>	      
	      	</table>
		</td>
	</tr>
	<tr>
    	<td class="hairLineTd" align="center">
        	<input type="submit" name="method" value="<bean:message key='Create'/>" class="CourseButton"/>
          	<input type="submit" name="method" value="<bean:message key='Modify'/>" onClick="return modifyCheck()" class="CourseButton"/>
          	<input type="submit" name="method" value="<bean:message key='Delete'/>" onClick="return delCheck()" class="CourseButton"/>
        </td>
	</tr>
</html:form>
</table>