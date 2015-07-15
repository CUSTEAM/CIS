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
<form action="/CIS/StudAffair/StudInspected.do" method="post" name="ccdForm">
<table width="100%" cellpadding="0" cellspacing="0">

<script>generateTableBanner('<div class="gray_15"><B><bean:message key="SAFTitle.StudInspectedMaintain" bundle="SAF"/></B></div>');</script>	  
	<tr>
		<td>
<!-- End Content Page Table Header -->

	<table width="100%" cellspacing="5">
      <!--DWLayoutTable-->
      <tr>
      	<td colspan="3">請輸入查詢條件(4選1)或直接按新增按鈕建立定察資料</td>
      </tr>
      <tr>
        <td width="10%" align="left" valign="middle">學號：
        </td>
        <td width="15%" align="left" valign="middle">
			<input type="text" name="studentNo" size="10">
        </td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td width="10%" align="left" valign="middle">姓名：
        </td>
        <td width="15%" align="left" valign="middle">
			<input type="text" name="studentName" size="10">
        </td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td width="10%" align="left" valign="middle">班級：
        </td>
        <td width="15%" align="left" valign="middle">
			<input type="text" name="clazz" size="8">
        </td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td width="10%" align="left" valign="middle">定察年度：
        </td>
        <td width="15%" align="left" valign="middle">
			<input type="text" name="downYear" size="3">
        </td>
        <td>&nbsp;</td>
      </tr>
	</table>
		</td>		
	</tr>
	
	<script>generateTableBanner('<Input type="submit" name="method" value="<bean:message key='Query'/>" >&nbsp;&nbsp;'
								+ '<Input type="submit" name="method" value="<bean:message key='Create'/>" >');</script>
	
	<!-- Test if have Query Result  -->
	<c:if test="${StudInspectedList != null}" >

	    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
		<tr>
		    <td><table width="100%" cellpadding="0" cellspacing="0">
		      <tr><td align="center">  
	      		<display:table name="${StudInspectedList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	  			<c:if test="${empty StudInspectedList}">
	     			<%@ include file="../include/NoBanner.jsp" %>
	  			</c:if>
	        	<display:column title="<script>generateTriggerAll(${fn:length(StudInspectedList)}, 'StudInspected');</script>" class="center" >
	          	<script>generateCheckbox("${row.oid}", "StudInspected");</script></display:column>
 	        	<display:column title="班級"		property="departClass"		sortable="true" 	class="left" />
 	        	<display:column title="班級名稱"	property="deptClassName"	sortable="true" 	class="left" />
 	        	<display:column title="學號"		property="studentNo"		sortable="true" 	class="left" />
 	        	<display:column title="姓名"		property="studentName"		sortable="true"  	class="left" />
 	        	<display:column title="定察年度"	property="downYear"			sortable="true"  	class="left" />
	        	<display:column title="定察學期"	property="downTerm"			sortable="false"  	class="left" />
	        	<display:column title="註銷年度"	property="upYear"			sortable="true"  	class="left" />
	        	<display:column title="註銷學期" 	property="upTerm" 			sortable="false" 	class="left" />
	        	</display:table>
 	         </td></tr>	      
	      	</table>
	      	</td>
	    </tr>

	<script>
	generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Modify'/>" onclick="return checkSelectForModify(\'StudInspected\');">&nbsp;&nbsp;' + 
						'<INPUT type="submit" name="method" value="<bean:message key='Delete'/>"  onclick="return checkSelectForDelete(\'StudInspected\');">');
	</script>
	</c:if>
	
<!-- Begin Content Page Table Footer -->
</table>		
</form>
