<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<script type="text/javascript">
history.go(1);
function searchCheck() {
	var iCount;
	iCount = getCookie("TeachDtimeInfoCount");
	if (iCount == 0) {
		alert("請選擇一個班級作查詢!!");
		return false;
	} else if(iCount > 1) {
		alert("不可複選班級作查詢!!");
		return false;
	} else {
		return true;
	}
}

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
<html:form action="/Teacher/StudentInfoSearch" method="post">
	<script>
		generateTableBanner('<div class="gray_15"><B><bean:message key="teacher.stdInfo.banner" bundle="TCH"/></B></div>');
	</script>
	<!-- c:if test="${not empty teacherDtime}"-->	
    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
    <tr>
    	<td>
    		學期：&nbsp;<html:select property="term" style="width:40px;" disabled="true">
    			<html:option value="1">1</html:option>
    			<html:option value="2">2</html:option>
    		</html:select>
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
        					<display:column titleKey="Course.label.courseNumber" property="cscode" sortable="true" class="center" />
        					<display:column titleKey="Course.label.courseName" property="chiName" sortable="true" class="center" />      					
      					</display:table>
      					</div>
      					<div id="term2" style="display:none;">
      					<display:table name="${teacherDtime2}" export="false" id="row" pagesize="10000" sort="list" excludedParams="*" class="list">
       						<%@ include file="../include/NoBanner.jsp" %>
        					<display:column title="" class="center" >
          						<script>generateCheckbox("${row.oid}", "TeachDtimeInfo");</script>
          					</display:column>
        					<display:column titleKey="Course.label.className" property="className" sortable="true" class="center" />
        					<display:column titleKey="Course.label.courseNumber" property="cscode" sortable="true" class="center" />
        					<display:column titleKey="Course.label.courseName" property="chiName" sortable="true" class="center" />      					
      					</display:table>
      					</div>
      				</td>
      			</tr>
    		</table>
    	</td>
    </tr>
   	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='teacher.stdInfo.btn.search' bundle="TCH" />" onclick="return searchCheck();" class="CourseButton">');
   	</script>
	<!--/c:if-->
	<c:if test="${stdList != null}">
    <tr><td height="10"></td></tr>
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
	 			<tr>
	 				<td align="center">
		      			<display:table name="${sessionScope.stdList}" export="false" id="row" sort="list" excludedParams="*" class="list">
		        			<c:if test="${empty stdList}" >
		        				<%@ include file="../include/NoBanner.jsp" %>
		        			</c:if>			        			
					        <display:column titleKey="Student.No" property="studentNo" sortable="true" class="center" />
					        <display:column titleKey="Student.Name"	property="studentName" sortable="true" class="center" />
					        <display:column titleKey="Student.cellPhone" property="cellPhone" sortable="true" class="center" />
					        <display:column titleKey="Student.email" property="email" sortable="true" />
					     </display:table>
					</td>
				</tr>
		    </table>
		</td>
	</tr>       		      	
	</c:if>
</html:form>
</table>
<script type="text/javascript">
	changeTerm();
</script>