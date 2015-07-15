<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">

</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/ClassCadre" method="post">
	<script>
		generateTableBanner('<div class="gray_15"><B><bean:message key="teacher.classCadre.banner" bundle="TCH"/></B></div>');
	</script>
	<c:if test="${not empty classListInfo}">	
    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
    <tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
	 			<tr>
	 				<td align="center">
      					<display:table name="${classListInfo}" export="false" id="row" pagesize="10000" sort="list" excludedParams="*" class="list">
       						<%@ include file="../include/NoBanner.jsp" %>
        					<display:column title="<script>generateTriggerAll(${fn:length(classListInfo)}, 'TeachDtimeInfo');</script>" class="center" >
          						<script>generateCheckbox("${row.oid}", "TeachDtimeInfo");</script>
          					</display:column>
        					<display:column titleKey="Course.label.className" property="classNo" sortable="true" class="center" />
        					<display:column titleKey="Course.label.courseName" property="className" sortable="true" class="center" />
      					</display:table>
      				</td>
      			</tr>
    		</table>
    	</td>
    </tr>    
   	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='teacher.classCadre.btn.add' bundle="TCH" />" class="CourseButton">&nbsp;');
   	</script>
	</c:if>
</html:form>
</table>