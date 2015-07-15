<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
function changeTerm() {
	var term = document.getElementById("term").value;
	if(term == "1") {
		document.getElementById("term1").style.display = "inline";
		document.getElementById("term2").style.display = "none";
	} else {
		document.getElementById("term1").style.display = "none";
		document.getElementById("term2").style.display = "inline";
	}
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/DeptAssistant/AssistantTeachSyllabus" method="post" onsubmit="init('系統處理中...')">
	<script>
		generateTableBanner('<div class="gray_15"><B><bean:message key="teacher.syllabus.banner" bundle="TCH"/> - <font color="red" size="+1">${schoolYear}</font>&nbsp;學年度</B></div>');
	</script>
	<tr>
		<td align="center">
	       	<table cellspacing="2" class="empty-border">

			</table>
		</td>
	</tr>
	<!-- c:if test="${not empty teacherDtime}"-->	
    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
    <tr align="center"><td><font color="red" size="+1"><strong>
    	<%-- 
    	<bean:message key="teacher.introduction.deadLineTitle" bundle="TCH" />
    	<bean:message key="teacher.introduction.deadLineInfo" bundle="TCH" />
    	--%>
    </strong></font></td></tr>
    <tr>
    	<td>
    		學期：&nbsp;<html:select property="term" style="width:40px;" disabled="true">
    			<html:option value="1">1</html:option>
    			<html:option value="2">2</html:option>
    		</html:select>
    	</td>
    </tr>
    <tr>
    	<td align="center">
    		<font color="red" size="+1">如果以下部分科目大綱內容相同或類似，則可以勾選多門科目進行複製。</font>
    	</td>    	
    </tr>
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
	 			<tr>
	 				<td align="center">
	 					<div id="term1" style="display:inline;">
      					<display:table name="${teacherDtime1}" export="false" id="row" pagesize="10000" sort="list" excludedParams="*" class="list">
       						<%@ include file="../include/NoBanner.jsp" %>
        					<display:column title="" class="center" >
          						<script>generateCheckbox("${row.oid}", "TeachDtimeInfo");</script>
          					</display:column>
        					<display:column titleKey="Course.label.className" property="className" sortable="true" class="center" />
        					<display:column titleKey="Course.label.courseName" property="chiName" sortable="true" class="center" />
        					<display:column titleKey="Course.label.lastModify" property="dateFormat" sortable="true" class="center" />        					
      					</display:table>
      					</div>
      					<div id="term2" style="display:none;">
      					<display:table name="${teacherDtime2}" export="false" id="row" pagesize="10000" sort="list" excludedParams="*" class="list">
       						<%@ include file="../include/NoBanner.jsp" %>
        					<display:column title="" class="center" >
          						<script>generateCheckbox("${row.oid}", "TeachDtimeInfo");</script>
          					</display:column>
        					<display:column titleKey="Course.label.className" property="className" sortable="true" class="center" />
        					<display:column titleKey="Course.label.courseName" property="chiName" sortable="true" class="center" />
        					<display:column titleKey="Course.label.lastModify" property="dateFormat" sortable="true" class="center" />        					
      					</display:table>
      					</div>
      				</td>
      			</tr>
    		</table>
    	</td>
    </tr>    
   	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='teacher.syllabus.btn.add' bundle="TCH" />" class="CourseButton">&nbsp;' +
   							'<INPUT type="submit" name="method" value="<bean:message key='Back' bundle="TCH" />" class="CourseButton">');
   	</script>
	<!--/c:if-->
</html:form>
</table>
<script type="text/javascript">
	changeTerm();
</script>