<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/Calendar.inc" %>
<script>
history.go(1);
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="${requestScope.actionName}" method="post" onsubmit="init('系統處理中...')">
	<script>
		generateTableBanner('<div class="gray_15"><B> 建 議 選 課 清 單 </B></div>');
	</script>
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${suggestCourse}" export="false" id="row" pagesize="100" sort="list" excludedParams="*" class="list">
		        <%@ include file="../include/NoBanner.jsp" %>		        
		        <display:column titleKey="Course.label.className" property="className" sortable="false" class="center" />
		        <display:column title="班級代碼" property="classNo" sortable="false" class="center" />
		        <display:column title="科目名稱" property="csnoName" sortable="false" class="center" />
		        <display:column title="科目代碼" property="cscode" sortable="false" class="center" />
		        <display:column title="選別" property="optName" sortable="false" class="center" />
		        <display:column title="學分數" property="credit" sortable="false" class="center" />
		        <display:column title="時數" property="hour" sortable="false" class="center" />
		    </display:table>
		</td>
	</tr>
  	<script>
  		generateTableBanner('<html:submit property="method" styleClass="CourseButton"><bean:message key="Back"/></html:submit>');			  
 	</script>
</html:form>
</table>