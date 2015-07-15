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
<form action="/CIS/Teacher/Tutor/Counseling.do" method="post" name="csForm">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="CounselingRecordMaintain" bundle="TCH"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5" class="empty-border">
      <!--DWLayoutTable-->
      <tr>
		<td width="80" align="right" class="td_lable_normal">班級：</td>
        <td  height="30" colspan="4" align="left" valign="middle">
        <c:if test="${StudCounselingInit.departClass != ''}">
        	<c:set var="campusSel" value="${StudCounselingInit.campus}"/>
	  		<c:set var="schoolSel" value="${StudCounselingInit.school}"/>
	  		<c:set var="deptSel"   value="${StudCounselingInit.dept}"/>
	  		<c:set var="classSel"  value="${StudCounselingInit.departClass}"/>
			<%@ include file="/pages/studaffair/include/ClassSelectTFillStud.inc" %>
        </c:if>
        </td>
      </tr>
      <tr>
		<td width="80" align="right" class="td_lable_normal">輔導日期：</td>
		<td width="85">
			<INPUT type="text" name="dateStart" id="dateStart" size="8" value="${StudCounselingInit.dateStart}"
				  autocomplete="off" style="ime-mode:disabled">
		</td>
		<td width="30">	  
			<img src="pages/images/date.gif" align="middle" style="text-align:center;" onClick="ds_sh('validDateStart'), document.getElementById('validDateStart').value='';" />
		</td>
		<td width="105">
			~ &nbsp;<INPUT type="text" name="dateEnd" id="dateEnd" size="8" value="${StudCounselingInit.dateEnd}"
		 	  	  autocomplete="off" style="ime-mode:disabled">
		</td>
		<td>
		 	<img src="pages/images/date.gif" align="middle" style="text-align:center;" onClick="ds_sh('validDateEnd'), document.getElementById('validDateEnd').value='';" />
		</td>
      </tr>
	</table>
		</td>		
	</tr>
	
	<script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='Create'/>" >&nbsp;&nbsp;'
							  + '<Input type="submit" name="method" value="<bean:message key='Query'/>" >');</script>
	
	<!-- Test if have Query Result  -->
	<c:if test="${CounselingsT != null}" >
	    <tr><td height="10"></td></tr>
	    
	    <%@include file="/pages/studaffair/include/Displaytag4Checkbox.inc"%>
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${StudCounselingsT}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty StudCounselingsT}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
	        	<display:column title="<script>generateTriggerAll(${fn:length(StudCounselingsT)}, 'CounselingsT');</script>" class="center" >
	          	<script>generateCheckbox("${row.oid}", "CounselingsT");</script></display:column>
 	        	<display:column title="學年"			property="schoolYear"	sortable="true" 	class="left" />
 	        	<display:column title="學期"			property="schoolYear"	sortable="true" 	class="left" />
 	        	<display:column title="班級"			property="departClass"	sortable="true" 	class="left" />
 	        	<display:column title="姓名"			property="studentName"	sortable="true"  	class="left" />
	        	<display:column title="輔導項目"		property="itemName"		sortable="true"  	class="left" />
	        	<display:column title="輔導日期"		property="cdate"		sortable="true"  	class="center" />
	        	<display:column title="輔導內容" sortable="false"  	class="left">${fn:substring(row.content, 0, 20)}</display:column>
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>
	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Modify'/>" onclick="return checkSelectForModify(\'CounselingsT\');">&nbsp;&nbsp;'+
	'<INPUT type="submit" name="method" value="<bean:message key='Delete'/>" onclick="return checkSelectForDelete(\'CounselingsT\');">');
	</script>
	</c:if>
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
