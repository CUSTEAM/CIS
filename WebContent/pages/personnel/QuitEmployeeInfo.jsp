<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Personnel/QuitEmployeeInfo" method="post">
	<script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.MaintainQuitEmployeeInfo" bundle="PSN"/></B></div>');</script>	  
		<tr><td>
	       <table width="100%" cellspacing="5" class="empty-border">
			 <tr><td align="right"><bean:message key="Name"/>&nbsp;
			 		<input type="text" name="cname3" 	 size="12" value="${QuitEmployeeListForm.map.cname3}"></td>
			     <td align="center"><bean:message key="Idno" />&nbsp;
			     	<input type="text" name="idNo3"		 size="12" value="${QuitEmployeeListForm.map.idNo3}"></td>
				 <td align="left">員工分類&nbsp;
				 	<c:set var="categorySel"  value="${QuitEmployeeListForm.map.category3}"/>
	  			    <%@ include file="/pages/include/EmpCategorySelect2.jsp" %></td>
	  			 <td></td></tr>
     		</table></td></tr>
	<script>generateTableBanner('<INPUT type="submit" class="CourseButton" name="method" value="<bean:message key='Query'/>" >');</script>
	<c:if test="${QuitEmployeeInfoList != null}" >
	    <tr><td height="10"></td></tr>
	    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
		<tr><td><table width="100%" cellpadding="0" cellspacing="0">
		 <tr><td align="center">
	      <display:table name="${QuitEmployeeInfoList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	        <c:if test="${empty QuitEmployeeInfoList}" >
	        	<%@ include file="../include/NoBanner.jsp" %>
	        </c:if>
	        <display:column title="<script>generateTriggerAll(${fn:length(QuitEmployeeInfoList)}, 'QuitEmployeeInfo');</script>" class="center" >
	          <script>generateCheckbox("${row.oid}", "QuitEmployeeInfo");</script></display:column>
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
       		generateTableBanner('<INPUT type="submit" class="CourseButton" name="method" value="<bean:message key='Delete'/>" onclick="return checkSelectForDelete(\'QuitEmployeeInfo\');">&nbsp;&nbsp;'+
       							'<INPUT type="submit" class="CourseButton" name="method" value="<bean:message key='Modify'/>" onclick="return checkSelectForModify(\'QuitEmployeeInfo\');">&nbsp;&nbsp;'+
       							'<INPUT type="submit" class="CourseButton" name="method" value="<bean:message key='View'/>"   onclick="return checkSelectForView(\'QuitEmployeeInfo\');"');
       	</script>	      	
	</c:if>
  </html:form>
</table>
