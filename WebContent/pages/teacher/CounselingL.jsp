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

<form action="/CIS/Teacher/Counseling.do" method="post" name="csForm">
<input type="hidden" name="studentNo" value="${StudCounselStudentL.studentNo}"/>
<input type="hidden" name="departClass" value="${StudCounselStudentL.departClass}"/>
<input type="hidden" name="cscode" value="${StudCounselStudentL.cscode}"/>
<input type="hidden" name="courseName" value="${StudCounselStudentL.courseName}"/>
<input type="hidden" name="courseClass" value="${StudCounselStudentL.courseClass}"/>

<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="CounselingRecordMaintain" bundle="TCH"/></B></div>');</script>	  
<tr><td height="10"></td></tr>
<tr><td>${StudCounselStudentL.courseName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${StudCounselStudentL.studentNo} ${StudCounselStudentL.studentName}</td></tr>
	
	<!-- Test if have Query Result  -->
	<c:if test="${StudCounselingsL != null}" >
	    
	    <%@include file="/pages/studaffair/include/Displaytag4Checkbox.inc"%>
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${StudCounselingsL}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty StudCounselingsL}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
	        	<display:column title="<script>generateTriggerAll(${fn:length(StudCounselingsL)}, 'CounselingsL');</script>" class="center" >
	          	<script>generateCheckbox("${row.oid}", "CounselingsL");</script></display:column>
	        	<display:column title="輔導項目"		property="itemName"		sortable="true"  	class="left" />
	        	<display:column title="輔導日期"		property="cdate"		format="{0,date,yyyy-MM-dd}"	sortable="true"  	class="center" />
	        	<display:column title="輔導內容" class="left">${fn:substring(row.content, 0, 20)}</display:column>
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>
	  	<c:if test="${empty StudCounselingsL}">
				<script>
				generateTableBanner('<Input type="button" name="method" value="<bean:message key='Cancel'/>" onclick="window.location.href=\'<%=basePath%>/Teacher/CounselingL.do\';">&nbsp;&nbsp;' +
				'<Input type="submit" name="method" value="<bean:message key='Create'/>" >');
				</script>
	  	</c:if>
	  	<c:if test="${not empty StudCounselingsL}">
			<script>
			generateTableBanner('<Input type="button" name="method" value="<bean:message key='BackOneLevel'/>" onclick="window.location.href=\'<%=basePath%>/Teacher/CounselingL.do\';">&nbsp;&nbsp;' +
			'<Input type="submit" name="method" value="<bean:message key='Create'/>" >&nbsp;&nbsp;' + 
			'<INPUT type="submit" name="method" value="<bean:message key='Modify'/>" onclick="return checkSelectForModify(\'CounselingsL\');">&nbsp;&nbsp;'+
			'<INPUT type="submit" name="method" value="<bean:message key='Delete'/>" onclick="return checkSelectForDelete(\'CounselingsL\');">');
			</script>
		</c:if>
	</c:if>
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
