<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.style2 {color: #FF0000}

A.blue_t13:link {
	FONT-SIZE: 13.5px;
	COLOR: #3366cc;
	LINE-HEIGHT: 18px;
	TEXT-DECORATION: none
}

A.blue_t13:visited {
	FONT-SIZE: 13.5px;
	COLOR: #3366dd;
	LINE-HEIGHT: 18px;
	TEXT-DECORATION: none
}

A.blue_t13:hover {
	FONT-SIZE: 13.5px;
	COLOR: #ff99cc;
	LINE-HEIGHT: 18px
}

A.blue_t13:active {
	FONT-SIZE: 13.5px;
	COLOR: #333333;
	LINE-HEIGHT: 18px;
	TEXT-DECORATION: none
}

// -->
</style>

<script>
	function generateTFUpload(dtOid, tdate, label){
		var url = "/CIS/Teacher/TimeOffInput.do?mode=redirect&dtOid=" + dtOid + "&tdate=" + tdate + "&sessionNumber=" + new Date().getTime() + Math.floor(Math.random()*999);
		document.write("<a href=\"" + url + "\" class=\"blue_t13\">" + label + "</a>");	
	}
	
	function chgView(){
		viewObj = document.getElementById("TFNPB8View");
		if(viewObj.style.display=='none'){
			viewObj.style.display='inline';
		}else{
			viewObj.style.display='none';
		}
	}
</script>

<!-- Begin Content Page Table Header -->
<form action="/CIS/StudAffair/StudBonusPenalty.do" method="post" name="bpForm">
<table width="100%" cellpadding="0" cellspacing="0">
<script>generateTableBanner('<div class="gray_15"><B><bean:message key="Title.TimeOffUploadQuery" bundle="TCH"/></B></div>');</script>	  

	<!-- Test if have Query Result  -->
	<c:if test="${TimeOffnotUpload != null}" >
		<c:set var="TFNotUpload" value="${TimeOffnotUpload.notUpload8List}"/>
		<c:choose>
		<c:when test="${TimeOffnotUpload.total == 0}">
	    	<tr><td height="10"><font class="blue_13">
	    	${TimeOffnotUpload.teacherName}&nbsp;&nbsp;
	   		應點名總次數:${TimeOffnotUpload.total}&nbsp;&nbsp;
	    	未點名次數:${TimeOffnotUpload.notUpload}
	    	</td></tr>
		</c:when>
		
		<c:otherwise>
	    	<tr><td height="10"><font class="blue_13">
	    	${TimeOffnotUpload.teacherName}&nbsp;老師：&nbsp;&nbsp;
	    	應點名總次數:${TimeOffnotUpload.total}&nbsp;&nbsp;
	    	未點名次數:${TimeOffnotUpload.notUpload}
	    	</td></tr>
		
			<tr>
			    <td><table width="100%" cellpadding="0" cellspacing="0">
			      <tr><td align="center">  
		      		<display:table name="${TFNotUpload}" export="false" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
		  			<c:if test="${empty TFNotUpload}">
		     			<%@ include file="../include/NoBanner.jsp" %>
		  			</c:if>
 	        		<display:column title="科目"		sortable="true" class="left">
 	        		<script>generateTFUpload("${row.dtimeOid}", "${row.teachDate}", "${row.subjectName}");</script></display:column>
	 	        	<!-- display:column title="科目"		property="subjectName"		sortable="true" 	class="left" / -->
	 	        	<display:column title="班級"		property="deptClassName"	sortable="true"  	class="left" />
		        	<display:column title="上課日期"	property="teachDate"		sortable="true"  	class="left" />
		        	<display:column title="星期"		property="teachWeek"		sortable="true"  	class="left" />
		        	</display:table>
	 	         </td></tr>	      
		      	</table>
		      	</td>
		    </tr>
		
		</c:otherwise>
		</c:choose>
		
		<c:if test="${not empty TimeOffnotUpload.notUploadBefore8List}">
			<c:set var="TFNPB8" value="${TimeOffnotUpload.notUploadBefore8List}"/>
			<tr><td>&nbsp;</td></tr>
			<tr><td onClick="chgView();">
			<img src="/CIS/pages/images/24-member-search.gif" align="middle">
			<font style="color:darkred; background:lightblue;">觀看已過期未點名資料</font></td></tr>
			<tr><td id="TFNPB8View" style="display:none;"><table  width="100%" cellspacing="0" class="list">
				<tr>
				<th>科目</th><th>班級</th><th>上課日期</th><th>星期</th>
			<c:forEach items="${TFNPB8}" var="npRec">
				<tr>
				<td>${npRec.subjectName}</td>
				<td>${npRec.deptClassName}</td>
				<td>${npRec.teachDate}</td>
				<td>${npRec.teachWeek}</td>
				</tr>
			</c:forEach>
				</tr>
			</table></td></tr>
		</c:if>
	</c:if>	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
