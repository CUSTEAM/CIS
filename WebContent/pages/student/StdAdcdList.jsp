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
		generateTableBanner('<div class="gray_15"><B> 我 的 加 退 選 歷 程 紀 錄 </B></div>');
	</script>
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${seldAdcdList}" export="false" id="row" pagesize="10000" sort="list" excludedParams="*" class="list">
		        <%@ include file="../include/NoBanner.jsp" %>		        
		        <display:column titleKey="Course.label.className" property="className" sortable="false" class="center" />
		        <display:column titleKey="Course.label.courseName" property="courseName" sortable="false" class="center" />
		        <display:column titleKey="Course.label.opt" property="optName" sortable="false" class="center" />
		        <display:column titleKey="Course.label.adddraw" property="adddraw" sortable="false" class="center" />
		        <display:column titleKey="Course.label.lastModify" property="lastModified" sortable="false" class="center" />		        
		    </display:table>
		</td>
	</tr>
  	<script>
  		generateTableBanner('<html:submit property="method" styleClass="CourseButton"><bean:message key="Back"/></html:submit>');			  
 	</script>
</html:form>
</table>