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
<form action="/CIS/StudAffair/StudBonusPenalty.do" method="post" name="bpForm">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.StudBonusPenaltyMaintain" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <tr>
        <td  height="30" colspan="6" align="center" valign="middle">學號：
        <c:if test="${StudBonusPenaltyInit.studentNo != ''}">
          <input name="studentNo" type="text" size="7" maxlength="10" value="${StudBonusPenaltyInit.studentNo}" />
        </c:if>
        <c:if test="${StudBonusPenaltyInit.studentNo == ''}">
          <input name="studentNo" type="text" size="7" maxlength="10" />
        </c:if>
        <img src="spacer.gif" alt="spacer" width="20" height="6"/>日期：
        <c:if test="${StudBonusPenaltyInit.bpYear != ''}">
          <input name="tfYear" type="text" size="3" maxlength="4" value="${StudBonusPenaltyInit.bpYear}" />年
        </c:if>
        <c:if test="${StudBonusPenaltyInit.bpYear == ''}">
          <input name="tfYear" type="text" size="3" maxlength="4" value="" />年
        </c:if>
        <c:if test="${StudBonusPenaltyInit.bpMonth != ''}">
          <input name="tfMonth" type="text" size="2" maxlength="3" value="${StudBonusPenaltyInit.bpMonth}" />月
        </c:if>
        <c:if test="${StudBonusPenaltyInit.bpMonth == ''}">
          <input name="tfMonth" type="text" size="2" maxlength="3" value="" />月
        </c:if>
        <c:if test="${StudBonusPenaltyInit.bpDay != ''}">
          <input name="tfDay" type="text" size="2" maxlength="3" value="${StudBonusPenaltyInit.bpDay}" />日
        </c:if>
        <c:if test="${StudBonusPenaltyInit.bpDay == ''}">
          <input name="tfDay" type="text" size="2" maxlength="3" value="" />日
        </c:if>
        </td>
      </tr>
	</table>
		</td>		
	</tr>
	
	<script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='Create'/>" >&nbsp;&nbsp;'
							  + '<Input type="submit" name="method" value="<bean:message key='Query'/>" >');</script>
	
	<!-- Test if have Query Result  -->
	<c:if test="${StudBonusPenaltyList != null}" >
	    <tr><td height="10"></td></tr>
	    <c:if test="${BonusPenaltyStuMap != null}">
	    	<tr><td height="10"><font class="blue_13">&nbsp;
		    	<c:out value="${BonusPenaltyStuMap.studentNo}"/>&nbsp;&nbsp;	    	
	    		<c:out value="${BonusPenaltyStuMap.studentName}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<c:out value="${BonusPenaltyStuMap.departClass}" />&nbsp;&nbsp;
	    		<c:out value="${BonusPenaltyStuMap.depClassName}" /></font>
	    	</td></tr>
	    	<tr><td bgcolor="#CCCCCC"><font class="blue_13">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	<c:set var="count" value="1"/>
	    	<c:forEach items="${BonusPenaltyCode}" var="bpType" begin="1">
	    		<c:out value="${bpType.name}"/>[ ${BonusPenaltyStuMap.bpCal[count]} ]
	    		&nbsp;&nbsp;&nbsp;
	    		<c:set var="count" value="${count + 1}"/>
	    	</c:forEach>
	    	</font>
	    	</td></tr>
	    </c:if>
	    
	    <%@include file="/pages/studaffair/include/Displaytag4Checkbox.inc"%>
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${StudBonusPenaltyList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty StudBonusPenaltyList}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
	        	<display:column title="<script>generateTriggerAll(${fn:length(StudBonusPenaltyList)}, 'StudBonusPenalty');</script>" class="center" >
	          	<script>generateCheckbox("${row.oid}", "StudBonusPenalty");</script></display:column>
 	        	<display:column title="日期"		property="sddate"		sortable="true" 	class="left" />
 	        	<display:column title="文號"		property="no"			sortable="true"  	class="center" />
	        	<display:column title="原因"		property="reason"		sortable="true"  	class="center" />
	        	<display:column title="種類1"	property="desdName1"	sortable="true"  	class="center" />
	        	<display:column title="次數1" 	property="cnt1" 		sortable="false" 	class="center" />
	        	<display:column title="種類2" 	property="desdName2" 	sortable="true"  	class="center" />
	        	<display:column title="次數2" 	property="cnt2"			sortable="false" 	class="center" />
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Modify'/>" onclick="return checkSelectForModify(\'StudBonusPenalty\');">&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Delete'/>" onclick="return checkSelectForDelete(\'StudBonusPenalty\');">');
	</script>
	</c:if>
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
