<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
function courseCheck() {
	var iCount;
	iCount = getCookie("teacherListCount");
	if (iCount == 0) {
		alert("請勾選至少一位老師進行課程大綱新增或修改!!");
		return false;
	} else if(iCount > 1) {
		alert("請勾選一位老師進行課程大綱新增或修改!!");
		return false;
	}
	return true;
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/DeptAssistant/AssistantTeachSyllabus" method="post" onsubmit="init('系統處理中...')">
	<script>
		generateTableBanner('<div class="gray_15"><B>任 教 科 目 課 程 綱 要 維 護 - <font color="red" size="+1">${schoolYear}</font>&nbsp;學年度</B></div>');
		generateTableBanner('<div class="gray_15"><B>${assistantEmpl.unit2}教師清單</B></div>');
	</script>
	<!-- c:if test="${not empty teacherDtime}" -->	
    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>    
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
	 			<tr>
	 				<td align="center">
      					<display:table name="${teacherList}" export="false" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
       						<%@ include file="../include/NoBanner.jsp" %>
        					<display:column title="" class="center">
          						<script>generateCheckbox("${row.oid}", "teacherList");</script>
          					</display:column>
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
   	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='teacher.syllabusSearch.btn.search' bundle="TCH" />" onclick="return courseCheck();" class="CourseButton">&nbsp;');
   	</script>
	<!-- /c:if -->
</html:form>
</table>