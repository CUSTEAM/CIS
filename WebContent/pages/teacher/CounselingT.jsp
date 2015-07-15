<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>

<script type="text/javascript" src="<%=basePath%>pages/include/json2.js"></script>

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

<form action="/CIS/Teacher/Tutor/Counseling.do" method="post" name="csForm">
<input type="hidden" name="studentNo" value="${StudCounselStudentT.studentNo}"/>
<input type="hidden" name="departClass" value="${StudCounselStudentT.departClass}"/>
<input type="hidden" name="cscode" value="${StudCounselStudentT.cscode}"/>
<input type="hidden" name="courseName" value="${StudCounselStudentT.courseName}"/>

<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="CounselingRecordMaintain" bundle="TCH"/></B></div>');</script>	  
<tr><td height="10"></td></tr>
<tr><td>${StudCounselStudentT.studentNo} ${StudCounselStudentT.studentName}</td></tr>
	<!-- Test if have Query Result  -->
	<c:if test="${StudCounselingsT != null}" >
	    <%@include file="/pages/studaffair/include/Displaytag4Checkbox.inc"%>
	    <tr><td height="10"></td></tr>
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${StudCounselingsT}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty StudCounselingsT}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
	        	<display:column title="<script>generateTriggerAll(${fn:length(StudCounselingsT)}, 'CounselingsT');</script>" class="center" >
	          	<script>generateCheckbox("${row.oid}", "CounselingsT");</script></display:column>
	          	<c:if test="${row.ctype=='T'}">
	          	<display:column title="輔導類別" class="left" sortable="true">職涯輔導</display:column>
	          	</c:if>
	          	<c:if test="${row.ctype=='U'}">
	          	<display:column title="輔導類別" class="left" sortable="true">學習輔導</display:column>
	          	</c:if>
	        	<display:column title="輔導項目"		property="itemName"		sortable="true"  	class="left" />
	        	<display:column title="輔導日期"		property="cdate"		format="{0,date,yyyy-MM-dd}" 	sortable="true"  	class="center" />
	        	<display:column title="輔導內容" class="left">${fn:substring(row.content, 0, 20)}</display:column>
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>
	  	<c:if test="${empty StudCounselingsT}">
				<script>
				generateTableBanner('<Input type="button" name="method" value="<bean:message key='Cancel'/>" onclick="window.location.href=\'<%=basePath%>/Teacher/Tutor/CounselingT.do\';">&nbsp;&nbsp;' +
				'<Input type="submit" name="method" value="<bean:message key='Create'/>" >');
				</script>
	  	</c:if>
	  	<c:if test="${not empty StudCounselingsT}">
			<script>
			generateTableBanner('<Input type="button" name="method" value="<bean:message key='BackOneLevel'/>" onclick="window.location.href=\'<%=basePath%>/Teacher/Tutor/CounselingT.do\';">&nbsp;&nbsp;' + 
			'<Input type="submit" name="method" value="<bean:message key='Create'/>" >&nbsp;&nbsp;' + 
			'<INPUT type="submit" name="method" value="<bean:message key='Modify'/>" onclick="return checkSelectForModify(\'CounselingsT\');">&nbsp;&nbsp;'+
			'<INPUT type="submit" name="method" value="<bean:message key='Delete'/>" onclick="return checkSelectForDelete(\'CounselingsT\');">');
			</script>
		</c:if>
	</c:if>

	<!-- List All Counselings of student -->
	<c:if test="${StudAllCounselingsT != null}" >
	    <tr><td height="30"></td></tr>
		<tr><td style="background-color: #FFE87C; line-height:36px;">
		<font color="#800517">${StudCounselStudentT.studentName} 的歷年輔導紀錄</font></td></tr>
		<tr>
		    <td style="border: 3px solid #FFE87C;"><table width="100%" bgcolor="#FFE87C" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${StudAllCounselingsT}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty StudAllCounselingsT}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
	        	<display:column title="學年"		property="schoolYear"		sortable="true"  	class="left" />
	        	<display:column title="學期"		property="schoolTerm"		sortable="true"  	class="left" />
	        	<display:column title="班級"		property="className"		sortable="true"  	class="left" />
	          	<c:if test="${row.ctype=='T'}">
	          	<display:column title="輔導類別" class="left" sortable="true">職涯輔導</display:column>
	          	</c:if>
	          	<c:if test="${row.ctype=='U'}">
	          	<display:column title="輔導類別" class="left" sortable="true">學習輔導</display:column>
	          	</c:if>
	        	<display:column title="輔導項目"	property="itemName"		sortable="true"  	class="left" />
	        	<display:column title="輔導日期"	property="cdate"		format="{0,date,yyyy-MM-dd}" 	sortable="true"  	class="center" />
	        	<display:column title="輔導內容" 	property="content"		sortable="false"  	class="left"/>
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>
	</c:if>


<!-- Begin Content Page Table Footer -->
</table>		
</form>
<%@ include file="/pages/studaffair/include/MyCalendarAD.inc" %>	
