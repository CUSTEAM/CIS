<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/ajax.js %>

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
<form action="/CIS/StudAffair/StudTimeOff.do" method="post" name="timeoffForm">
<input type="hidden" name="daynite" value="1">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.StudTimeOffMaintain" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <!-- 
      <tr>
        <td height="33" colspan="6" align="left" valign="top" class="menuIndex"><c:out value="學生請假資料維護"/> -&gt; <span class="style2">歷年成績資料維護</span></td>
  	    </tr>

  	  <tr><td><font color="red">請注意選擇您的部制!</font></td></tr>
  	  <tr>
  	  	<td height="30" align="center" valign="middle">選擇部制：
  	  	<c:if test="${StudTimeoffInit.daynite == '1'}">
  	  		<input type="radio" name="daynite" value="1" checked />日間部&nbsp;&nbsp;
  	  	</c:if>
  	  	<c:if test="${StudTimeoffInit.daynite != '1'}">
  	  		<input type="radio" name="daynite" value="1" />日間部&nbsp;&nbsp;
  	  	</c:if>
  	  	<c:if test="${StudTimeoffInit.daynite == '2'}">
  	  		<input type="radio" name="daynite" value="2" checked />進修部&nbsp;&nbsp;
  	  	</c:if>
  	  	<c:if test="${StudTimeoffInit.daynite != '2'}">
  	  		<input type="radio" name="daynite" value="2" />進修部&nbsp;&nbsp;
  	  	</c:if>
  	  	<c:if test="${StudTimeoffInit.daynite == '3'}">
  	  		<input type="radio" name="daynite" value="3" checked />專校學院&nbsp;&nbsp;
  	  	</c:if>
  	  	<c:if test="${StudTimeoffInit.daynite != '3'}">
  	  		<input type="radio" name="daynite" value="3" />專校學院&nbsp;&nbsp;
  	  	</c:if>
  	  	</td>
  	  </tr>
  	  -->
      <tr>
        <td  height="30" colspan="6" align="center" valign="middle">日期：
        <c:if test="${StudTimeoffInit.tfYear != ''}">
          <input name="tfYear" type="text" size="3" maxlength="4" value="${StudTimeoffInit.tfYear}" />年
        </c:if>
        <c:if test="${StudTimeoffInit.tfYear == ''}">
          <input name="tfYear" type="text" size="3" maxlength="4" value="" />年
        </c:if>
        <c:if test="${StudTimeoffInit.tfMonth != ''}">
          <input name="tfMonth" type="text" size="2" maxlength="3" value="${StudTimeoffInit.tfMonth}" />月
        </c:if>
        <c:if test="${StudTimeoffInit.tfMonth == ''}">
          <input name="tfMonth" type="text" size="2" maxlength="3" value="" />月
        </c:if>
        <c:if test="${StudTimeoffInit.tfDay != ''}">
          <input name="tfDay" type="text" size="2" maxlength="3" value="${StudTimeoffInit.tfDay}" />日
        </c:if>
        <c:if test="${StudTimeoffInit.tfDay == ''}">
          <input name="tfDay" type="text" size="2" maxlength="3" value="" />日
        </c:if>
        <img src="spacer.gif" alt="spacer" width="20" height="6"/>學號：
        <c:if test="${StudTimeoffInit.studentNo != ''}">
          <input name="studentNo" type="text" size="7" maxlength="10" value="${StudTimeoffInit.studentNo}" />
        </c:if>
        <c:if test="${StudTimeoffInit.studentNo == ''}">
          <input name="student_no" type="text" size="7" maxlength="10" />
        </c:if>
        </td>
      </tr>
      <!-- 
      <tr>
        <td  height="34" colspan="6" align="center" valign="middle" bgcolor="#CCCCFF">
			<Input type="submit" name="scrHistAction" value="<bean:message key='score.histadd' bundle='SCR'/>" >
          <img src="spacer.gif" alt="spacer" width="20" height="6"/>
		<Input type="submit" name="scrHistAction" value="<bean:message key='score.histqry' bundle='SCR'/>" >
        </tr>
      <tr>
        <td  height="33" colspan="6" align="left" valign="middle">&nbsp;</td>
        </tr>
      -->
	</table>
		</td>		
	</tr>
	
	<script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='Create'/>" >&nbsp;&nbsp;'
							  + '<Input type="submit" name="method" value="<bean:message key='Query'/>" >');</script>
	
	<!-- Test if have Query Result  -->
	<c:if test="${StudTimeOffList != null}" >
	    <tr><td height="10"></td></tr>
	    <c:if test="${TimeOffStuMap != null}">
	    	<tr><td height="10"><font class="blue_13">&nbsp;
		    	<c:out value="${TimeOffStuMap.studentNo}"/>&nbsp;&nbsp;	    	
	    		<c:out value="${TimeOffStuMap.studentName}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<c:out value="${TimeOffStuMap.departClass}" />&nbsp;&nbsp;
	    		<c:out value="${TimeOffStuMap.depClassName}" /></font>
	    	</td></tr>
	    	<tr><td bgcolor="#CCCCCC"><font class="blue_13">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	<c:set var="count" value="1"/>
	    	<c:forEach items="${StudTimeOffType}" var="tfType" begin="1">
	    		<c:out value="${fn:substring(tfType.name, 1, fn:length(tfType.name))}"/>[ ${TimeOffStuMap.tfCal[count]} ]
	    		&nbsp;&nbsp;&nbsp;
	    		<c:set var="count" value="${count + 1}"/>
	    	</c:forEach>
	    	</font>
	    	</td></tr>
	    </c:if>
	    
	    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
		<tr><td><table width="100%" cellpadding="0" cellspacing="0">
		 <tr><td align="center">  
	  		<c:choose>
	  		<c:when test="${StudTimeoffInit.daynite == '1'}">
	      		<display:table name="${StudTimeOffList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	        	<display:column title="<script>generateTriggerAll(${fn:length(StudTimeOffList)}, 'StudTimeOff');</script>" class="center" >
	          	<script>generateCheckbox("${row.oid}", "StudTimeOff");</script></display:column>
 	        	<display:column title="日期"	property="sddate"	sortable="true" class="left" />
 	        	<display:column title="班級"	property="deptClassName"	sortable="true" class="left" />
	        	<display:column title="姓名"	property="studentName"	sortable="true" class="left" />
 	        	<display:column title="升旗"	property="absName0"		sortable="false" class="center" />
	        	<display:column title="１"	property="absName1"		sortable="false" class="center" />
	        	<display:column title="２"	property="absName2"		sortable="false" class="center" />
	        	<display:column title="３" 	property="absName3" 	sortable="false" class="center" />
	        	<display:column title="４" 	property="absName4" 	sortable="false" class="center" />
	        	<display:column title="５" 	property="absName5"		sortable="false" class="center" />
	        	<display:column title="６" 	property="absName6"		sortable="false" class="center" />
	        	<display:column title="７" 	property="absName7"		sortable="false" class="center" />
	        	<display:column title="８" 	property="absName8"		sortable="false" class="center" />
	        	<display:column title="９" 	property="absName9"		sortable="false" class="center" />
	        	<display:column title="10" 	property="absName10"	sortable="false" class="center" />
	        	<display:column title="11" 	property="absName11"	sortable="false" class="center" />
	        	<display:column title="12" 	property="absName12"	sortable="false" class="center" />
	        	<display:column title="13" 	property="absName13"	sortable="false" class="center" />
	        	<display:column title="14" 	property="absName14"	sortable="false" class="center" />
	        	<display:column title="15" 	property="absName15"	sortable="false" class="center" />
	        	</display:table>
	        </c:when>
	  		<c:when test="${StudTimeoffInit.daynite == '2'}">
	      		<display:table name="${StudTimeOffList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	        	<display:column title="<script>generateTriggerAll(${fn:length(StudTimeOffList)}, 'StudTimeOff');</script>" class="center" >
	          	<script>generateCheckbox("${row.oid}", "StudTimeOff");</script></display:column>
 	        	<display:column title="日期"	property="sddate"	sortable="true" class="left" />
 	        	<display:column title="班級"	property="deptClassName"	sortable="true" class="left" />
	        	<display:column title="姓名"	property="studentName"	sortable="true" class="left" />
  	        	<display:column title="升旗"	property="abs0"		sortable="false" class="center" />
	        	<display:column title="１"	property="absName1"		sortable="false" class="center" />
	        	<display:column title="２"	property="absName2"		sortable="false" class="center" />
	        	<display:column title="３" 	property="absName3" 	sortable="false" class="center" />
	        	<display:column title="４" 	property="absName4" 	sortable="false" class="center" />
	        	<display:column title="５" 	property="absName5"		sortable="false" class="center" />
	        	<display:column title="６" 	property="absName6"		sortable="false" class="center" />
	        	<display:column title="７" 	property="absName7"		sortable="false" class="center" />
	        	<display:column title="８" 	property="absName8"		sortable="false" class="center" />
	        	<display:column title="９" 	property="absName9"		sortable="false" class="center" />
	        	<display:column title="10" 	property="absName10"	sortable="false" class="center" />
	        	<display:column title="N1" 	property="absName11"	sortable="false" class="center" />
	        	<display:column title="N2" 	property="absName12"	sortable="false" class="center" />
	        	<display:column title="N3" 	property="absName13"	sortable="false" class="center" />
	        	<display:column title="N4" 	property="absName14"	sortable="false" class="center" />
	        	<display:column title="N5" 	property="absName15"	sortable="false" class="center" />
	        	</display:table>
	        </c:when>
	  		<c:when test="${StudTimeoffInit.daynite == '3'}">
	      		<display:table name="${StudTimeOffList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	        	<display:column title="<script>generateTriggerAll(${fn:length(StudTimeOffList)}, 'StudTimeOff');</script>" class="center" >
	          	<script>generateCheckbox("${row.oid}", "StudTimeOff");</script></display:column>
 	        	<display:column title="日期"	property="sddate"	sortable="true" class="left" />
 	        	<display:column title="班級"	property="deptClassName"	sortable="true" class="left" />
	        	<display:column title="姓名"	property="studentName"	sortable="true" class="left" />
  	        	<display:column title="升旗"	property="absName0"		sortable="false" class="center" />
	        	<display:column title="１"	property="absName1"		sortable="false" class="center" />
	        	<display:column title="２"	property="absName2"		sortable="false" class="center" />
	        	<display:column title="３" 	property="absName3" 	sortable="false" class="center" />
	        	<display:column title="４" 	property="absName4" 	sortable="false" class="center" />
	        	<display:column title="５" 	property="absName5"		sortable="false" class="center" />
	        	<display:column title="６" 	property="absName6"		sortable="false" class="center" />
	        	<display:column title="７" 	property="absName7"		sortable="false" class="center" />
	        	<display:column title="８" 	property="absName8"		sortable="false" class="center" />
	        	<display:column title="９" 	property="absName9"		sortable="false" class="center" />
	        	<display:column title="10" 	property="absName10"	sortable="false" class="center" />
	        	<display:column title="11" 	property="absName11"	sortable="false" class="center" />
	        	<display:column title="12" 	property="absName12"	sortable="false" class="center" />
	        	<display:column title="13" 	property="absName13"	sortable="false" class="center" />
	        	<display:column title="14" 	property="absName14"	sortable="false" class="center" />
	        	<display:column title="15" 	property="absName15"	sortable="false" class="center" />
	        	</display:table>
	        </c:when>
	        <c:otherwise>
	     		<%@ include file="../include/NoBanner.jsp" %>
	        </c:otherwise>
	        </c:choose>
 	      </td></tr>	      
	      			</table>
	      		</td>
	      	</tr>
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Modify'/>" onclick="return checkSelectForModify(\'StudTimeOff\');">&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Delete'/>" onclick="return checkSelectForDelete(\'StudTimeOff\');">');
	</script>
	</c:if>
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
