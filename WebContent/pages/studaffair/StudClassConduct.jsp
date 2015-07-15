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
// -->
</style>
<!-- Begin Content Page Table Header -->
<form action="/CIS/StudAffair/StudClassConduct.do" method="post" name="ccdForm">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.StudClassConductMaintain" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <tr>
        <td  height="30" colspan="6" align="center" valign="middle">班級：
        <c:if test="${StudClassConductInit.departClass != ''}">
        	<c:set var="campusSel" value="${StudClassConductInit.campus}"/>
	  		<c:set var="schoolSel" value="${StudClassConductInit.school}"/>
	  		<c:set var="deptSel"   value="${StudClassConductInit.dept}"/>
	  		<c:set var="classSel"  value="${StudClassConductInit.departClass}"/>
			<%@ include file="/pages/studaffair/include/ClassSelectSAF.jsp" %>
        </c:if>
        </td>
      </tr>
	</table>
		</td>		
	</tr>
	
	<script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='Query'/>" >');</script>
	
	<!-- Test if have Query Result  -->
	<c:if test="${StudClassConductList != null}" >
	    <tr><td height="10"></td></tr>
	    <c:if test="${StudClassConductInit != null}">
	    	<tr><td height="10"><font class="blue_13">&nbsp;
	    		<c:out value="${StudClassConductInit.departClass}" />&nbsp;&nbsp;
	    		<c:out value="${StudClassConductInit.depClassName}" /></font>
	    	</td></tr>
	    </c:if>

	    <%@include file="/pages/studaffair/include/Displaytag4Checkbox.inc"%>
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${StudClassConductList}" export="false" id="row" pagesize="0" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty StudClassConductList}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
	        	<display:column title="<script>generateTriggerAll(${fn:length(StudClassConductList)}, 'StudClassConduct');</script>" class="center" >
	          	<script>generateCheckbox("${row.oid}", "StudClassConduct");</script></display:column>
 	        	<display:column title="學號"		property="studentNo"		sortable="true" 	class="left" />
 	        	<display:column title="姓名"		property="studentName"		sortable="true"  	class="left" />
 	        	<display:column title="導師"		property="teacherScore"		sortable="true" 	class="right" />
 	        	<display:column title="系主任"	property="deptheaderScore"	sortable="true"  	class="right" />
	        	<display:column title="教官"		property="militaryScore"	sortable="true"  	class="right" />
	        	<display:column title="勤惰"		property="dilgScore"		sortable="true"  	class="right" />
	        	<display:column title="獎懲" 	property="desdScore" 		sortable="false" 	class="right" />
	        	<display:column title="評審" 	property="meetingScore" 	sortable="true"  	class="right" />
	        	<display:column title="合計" 	property="totalScore"		sortable="false" 	class="right" />
 	        	<display:column title="代碼"		property="comCode1"			sortable="true" 	class="center" />
 	        	<display:column title="評語1"	property="comShortName1"	sortable="true"  	class="left" />
 	        	<display:column title="代碼"		property="comCode2"			sortable="true" 	class="center" />
 	        	<display:column title="評語2"	property="comShortName2"	sortable="true"  	class="left" />
 	        	<display:column title="代碼"		property="comCode3"			sortable="true" 	class="center" />
 	        	<display:column title="評語3"	property="comShortName3"	sortable="true"  	class="left" />
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>

	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Modify'/>">&nbsp;&nbsp;');
	</script>
	</c:if>
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
