<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
  <html:form action="/Registration/EntranceDocNo" method="post">
	<script>generateTableBanner('<div class="gray_15"><B><bean:message key="header.EntranceDocNo" bundle="REG"/></B></div>');</script>	  
		<tr><td>
	       <table width="100%" cellspacing="5" class="empty-border">
			 <tr><td>起始學號&nbsp;
			 		<input type="text" name="startNo2" size="12" value="${EntranceDocNoListForm.map.startNo2}"></td>
			 	 <td>終止學號&nbsp;
			 	 	<input type="text" name="endNo2"   size="12" value="${EntranceDocNoListForm.map.endNo2}"></td></tr>
  			 <tr><td colspan="2">核准文號&nbsp;
					<input type="text" name="docNo2"   size="40" value="${EntranceDocNoListForm.map.docNo2}"></td></tr>
     	   </table></td></tr>
	<script>generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Create'/>" class="CourseButton">&nbsp;&nbsp;'
     						  + '<INPUT type="submit" name="method" value="<bean:message key='Query' />" class="CourseButton">');</script>
	<c:if test="${EntrnoList != null}" >
	    <tr><td height="10"></td></tr>
	    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
		<tr><td><table width="100%" cellpadding="0" cellspacing="0">
		 <tr><td align="center">
	      <display:table name="${EntrnoList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
	        <c:if test="${empty EntrnoList}" >
	        	<%@ include file="../include/NoBanner.jsp" %>
	        </c:if>
	        <display:column title="<script>generateTriggerAll(${fn:length(EntrnoList)}, 'EntranceDocNo');</script>" class="center" >
	          <script>generateCheckbox("${row.oid}", "EntranceDocNo");</script></display:column>
	        <display:column title="起始學號"			property="firstStno"	sortable="true" class="center" />
	        <display:column title="終止學號" 			property="secondStno" 	sortable="true" class="center" />
	        <display:column title="入 學 資 格 核 准 文 號" 	property="permissionNo" sortable="true" />
	      </display:table></td></tr>
	    </table></td></tr>
       	<script>
       		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='Delete'/>" class="CourseButton" onclick="return checkSelectForDelete(\'EntranceDocNo\');">&nbsp;&nbsp;'+
       							'<INPUT type="submit" name="method" value="<bean:message key='Modify'/>" class="CourseButton" onclick="return checkSelectForModify(\'EntranceDocNo\');">&nbsp;&nbsp;');
       	</script>	      	
	</c:if>
  </html:form>
</table>
