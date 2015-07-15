<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
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
<html:form action="/Teacher/TeachBookSuggest" method="post">
	<script>
		generateTableBanner('<div class="gray_15"><B><bean:message key="teacher.bookSuggest.banner" bundle="TCH"/></B></div>');
	</script>
	<!-- c:if test="${not empty teacherDtime}" -->	
    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>    
    <tr>
    	<td>
    		學期：&nbsp;<html:select property="term" style="width:40px;" onchange="changeTerm();">
    			<html:option value="1">1</html:option>
    			<html:option value="2">2</html:option>
    		</html:select>&nbsp;<img src="images/gauge_leftarrow.png">&nbsp;<font color="red">可以選學期</font>
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
      					<div id="term2" style="display:inline;">
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
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='teacher.bookSuggest.btn.add' bundle="TCH" />" class="CourseButton">');
   	</script>
	<!--/c:if -->
</html:form>
</table>
<script type="text/javascript">
	changeTerm();
</script>