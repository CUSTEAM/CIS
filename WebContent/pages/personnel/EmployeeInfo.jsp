<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Personnel/EmployeeInfo" method="post">
	<script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.MaintainEmployeeInfo" bundle="PSN"/></B></div>');</script>	  
		<tr><td>
	       <table width="100%" cellspacing="5" class="empty-border">
			 <tr><td align="right"><bean:message key="Name"/>&nbsp;
			 		<input type="text" name="cname2" 	 size="12" value="${EmployeeListForm.map.cname2}"></td>
			     <td align="center"><bean:message key="Idno" />&nbsp;
			     	<input type="text" name="idNo2"		 size="12" value="${EmployeeListForm.map.idNo2}"></td>
				 <td align="left">員工分類&nbsp;
				 	<c:set var="categorySel"  value="${EmployeeListForm.map.category2}"/>
	  			    <%@ include file="/pages/include/EmpCategorySelect2.jsp" %></td>
	  			 <td></td></tr>
     		</table></td></tr>
	<script>generateTableBanner('<INPUT type="submit" class="CourseButton" name="method" value="<bean:message key='Query'/>" >&nbsp;&nbsp;'
     						  + '<INPUT type="submit" class="CourseButton" name="method" value="<bean:message key='Create' />" >');</script>
	<c:if test="${EmployeeInfoList != null}" >
	    <tr><td height="10"></td></tr>
	    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
		<tr><td><table width="100%" cellpadding="0" cellspacing="0">
		 <tr><td align="center">
	      <display:table name="${EmployeeInfoList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	        <c:if test="${empty EmployeeInfoList}" >
	        	<%@ include file="../include/NoBanner.jsp" %>
	        </c:if>
	        <display:column title="<script>generateTriggerAll(${fn:length(EmployeeInfoList)}, 'EmployeeInfo');</script>" class="center" >
	          <script>generateCheckbox("${row.oid}", "EmployeeInfo");</script></display:column>
	        <display:column titleKey="Student.Name"		property="cname"		sortable="true" class="center" />
	        <display:column titleKey="Student.Idno" 	property="idno"			sortable="true" class="center" />
	        <display:column titleKey="Student.Sex" 		property="sex2" 		sortable="true" class="center" />
	        <display:column title="單     位" 				property="unit2"		sortable="true" />
	        <display:column title="職     級" 				property="pcode2"		sortable="true" />
	        <display:column title="員工分類" 				property="category2"	sortable="true" class="center" />
	        <display:column titleKey="Student.Status" 	property="status2"		sortable="true" class="center" />
	      </display:table></td></tr>
	    </table></td></tr>
       	<script>
       		generateTableBanner('<INPUT type="submit" class="CourseButton" name="method" value="<bean:message key='Delete'/>" onclick="return checkSelectForDelete(\'EmployeeInfo\');">&nbsp;&nbsp;'+
       							'<INPUT type="submit" class="CourseButton" name="method" value="<bean:message key='Modify'/>" onclick="return checkSelectForModify(\'EmployeeInfo\');">&nbsp;&nbsp;'+
       							'<INPUT type="submit" class="CourseButton" name="method" value="<bean:message key='View'/>"   onclick="return checkSelectForView(\'EmployeeInfo\');"');
       	</script>	      	
	</c:if>
  </html:form>
</table>
