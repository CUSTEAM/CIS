<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Personnel/EmployeeInfo" method="post">
	<script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.DeleteEmployeeInfo" bundle="PSN"/></B></div>');</script>	  
	  <tr height="50"><td align="center" class="blue_15">
	  	確定要刪除以下員工嗎 ?&nbsp;
	  	<input type="submit" class="CourseButton" name="method" value="<bean:message key='DeleteConfirm'/>">&nbsp;&nbsp;
	  	<input type="submit" class="CourseButton" name="method" value="<bean:message key='Cancel'/>"></td></tr>
	  <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	  <tr><td><table width="100%" cellpadding="0" cellspacing="0">
		 <tr><td align="center">
	      <display:table name="${EmployeeInfoDelete}" export="false" id="row" pagesize="10000" sort="list" excludedParams="*" class="list">
	        <%@ include file="../include/NoBanner.jsp" %>
	        <display:column title="<script>generateTriggerAll(${fn:length(EmployeeInfoDelete)}, 'EmployeeDelete');</script>" class="center" >
	          <script>generateCheckbox("${row.oid}", "EmployeeDelete");</script></display:column>
	        <display:column titleKey="Student.Name"		property="cname"		sortable="true" class="center" />
	        <display:column titleKey="Student.Idno" 	property="idno"			sortable="true" class="center" />
	        <display:column titleKey="Student.Sex" 		property="sex2" 		sortable="true" class="center" />
	        <display:column title="單     位" 				property="unit2"		sortable="true" />
	        <display:column title="職     級" 				property="pcode2"		sortable="true" />
	        <display:column title="員工分類" 				property="category2"	sortable="true" class="center" />
	        <display:column titleKey="Student.Status" 	property="status2"		sortable="true" class="center" />
	      </display:table></td></tr>
	    </table></td></tr>
       	<script>generateTableBanner('');</script>
  </html:form>
</table>	      	
<script>history.go(1);</script>	  