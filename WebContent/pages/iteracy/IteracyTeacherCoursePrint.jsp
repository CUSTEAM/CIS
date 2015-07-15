<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Literacy/LiteracyTeacherCoursePrint" method="post" onsubmit="init('系統處理中...')">
	<script>
		generateTableBanner('<div class="gray_15"><B>通 識 中 心 教 師 課 表 列 印</B></div>');
	</script>
    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>    
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
	 			<tr>
	 				<td align="center">
      					<display:table name="${employeeList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
       						<%@ include file="../include/NoBanner.jsp" %>
        					<display:column titleKey="courseSearch.teacherName" property="cname" sortable="true" class="center" />
        					<display:column titleKey="courseSearch.teacherEngName" property="ename" sortable="true" class="center" />
        					<display:column titleKey="courseSearch.teacherTitle" property="sname" sortable="true" class="center" />
        					<display:column title="教師課表" sortable="false" class="center" href="/CIS/DeptAssistant/AssistantTeacherSchedOfficeHoursPrint.do" paramId="Oid" paramProperty="oid">
        						<img src='images/ico_file_excel1.png' border='0' alt="教師課表下載">
        					</display:column>        					
      					</display:table>
      				</td>
      			</tr>
    		</table>
    	</td>
    </tr>    
</html:form>
</table>