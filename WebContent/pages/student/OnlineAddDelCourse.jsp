<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/tooltips.js" %>

<DIV id="TipLayer" style="visibility:hidden;position:absolute;z-index:1000;top:-100"></DIV>

<script>
history.go(1);
Style[0]=["white","black","#00B333","#E8E8FF","","","","","","","","","","",150,"",2,2,10,10,51,0.5,75,"simple","gray"];
Text[0]=["教學大綱說明", "點選此處了解此課程之綱要說明..."];
Style[1]=["white","black","#00B333","#E8E8FF","","","","","","","","","","",150,"",2,2,10,10,51,0.5,75,"simple","gray"];
Text[1]=["中英文簡介說明", "點選此處了解此課程之簡介說明..."];
var FiltersEnabled = 1;
applyCssFilter();
function removeCourseCheck() {
	var iCount;
	iCount = getCookie("seldDataInfoOpt2Count");
	if (iCount == 0) {
		alert("請勾選[可退選科目]內清單進行退選!!");
		return false;
	} else {
		if(confirm("確定退選[" + iCount + "]門科目?"))
			return true;
		else 
			return false;	
	}
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Student/OnlineAddDelCourse" method="post" onsubmit="init('系統處理中...')">
	<script>generateTableBanner('<div class="gray_15"><B><bean:message key="student.onlineAddDelCourse.banner" bundle="STD"/></B></div>');</script>
	<script>generateTableBanner('<div class="gray_15">[<font color="red"><a href="http://www.cust.edu.tw/www/info/class/" target="_blank">網路選課注意事項</a></font>]</div>');</script>
	<tr>
		<td>
       		<table width="100%" cellspacing="5" class="empty-border">
		 		<tr>
		 			<td>
		 				<bean:message key="Name" />：&nbsp;
		 				<html:text property="name" size="12" disabled="true" />
		 			</td>
		 	 		<td>
		 	 			<bean:message key="StudentNo" />：&nbsp;
		 	 			<html:text property="stdNo" size="12" disabled="true" />
		 	 		</td>
		     		<td><bean:message key="Class"/>：&nbsp;
		 				<html:text property="classNo" size="5" disabled="true" />&nbsp;
		   				<html:text property="deptClassName" size="20" disabled="true" />
		   			</td>
		     	</tr>
    		</table>
    	</td>
    </tr>

	<tr>
    	<th align="center"><font color="red">總選課數：${totalCourses}&nbsp;&nbsp;&nbsp;總學分數：${totalCredits}&nbsp;&nbsp;&nbsp;總時數：${totalHours}</font></th>
    </tr>
    <c:if test="${not empty seldDataInfoOpt1}">
	<script>generateTableBanner('<div class="gray_15"><B><bean:message key="student.onlineAddDelCourse.selectedCourseOpt1" bundle="STD"/></B></div>');</script>
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${seldDataInfoOpt1}" export="false" id="row" pagesize="10000" sort="list" excludedParams="*" class="list">
		        <%@ include file="../include/NoBanner.jsp" %>
		        <display:column titleKey="Course.label.className" property="className" sortable="false" class="center" />
		        <display:column titleKey="Course.label.courseNumber" property="csCode" sortable="false" class="center" />
		        <display:column titleKey="Course.label.courseName" sortable="false" class="left">
		        	${row.csName}&nbsp;
		        	<html:link page="/Student/OnlineAddDelCourse.do?method=readCourseIntro" paramName="row" paramId="oid" paramProperty="dtimeOid" onmouseover="stm(Text[1], Style[1])" onmouseout="htm()">
						[<font color="blue">中英文簡介</font>]
					</html:link>&nbsp;
					<html:link page="/Student/OnlineAddDelCourse.do?method=readCourseSyllabus" paramName="row" paramId="oid" paramProperty="dtimeOid" onmouseover="stm(Text[0], Style[0])" onmouseout="htm()">
						[<font color="blue">教學大綱</font>]
					</html:link>
				</display:column>		        
		        <display:column titleKey="Course.label.opt" property="optName" sortable="false" class="center" />
		        <display:column title="型態" property="elearning" sortable="false" class="center" />
		        <display:column titleKey="Course.label.credit" property="credit" sortable="false" class="center" />
		        <display:column titleKey="Course.label.hours" property="hour" sortable="false" class="center" />
		    </display:table>
		</td>
	</tr>
	</c:if>
	
	<c:if test="${not empty seldDataInfoOpt2}">
    <script>generateTableBanner('<div class="gray_15"><B><bean:message key="student.onlineAddDelCourse.selectedCourseOpt2" bundle="STD"/></B></div>');</script>
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${seldDataInfoOpt2}" export="false" id="row" pagesize="10000" sort="list" excludedParams="*" class="list">
		        <%@ include file="../include/NoBanner.jsp" %>
		        <c:if test="${row.canChoose != '1'}">
		        <display:column title="<script>generateTriggerAll(${fn:length(seldDataInfoOpt2)}, 'seldDataInfoOpt2'); </script>" class="center">
					<script>generateCheckbox("${row.seldOid}", "seldDataInfoOpt2")</script>
		        </display:column>	    
		        </c:if>   
		        <c:if test="${row.canChoose == '1'}">
		        <display:column title="" class="center">	
		        	&nbsp;&nbsp;&nbsp;&nbsp;				
		        </display:column>	    
		        </c:if> 
		        <display:column titleKey="Course.label.className" property="className" sortable="false" class="center" />		        
		        <display:column titleKey="Course.label.courseNumber" property="csCode" sortable="false" class="center" />
		        <display:column titleKey="Course.label.courseName" sortable="false" class="left">
		        	${row.csName}&nbsp;
		        	<html:link page="/Student/OnlineAddDelCourse.do?method=readCourseIntro" paramName="row" paramId="oid" paramProperty="dtimeOid" onmouseover="stm(Text[1], Style[1])" onmouseout="htm()">
						[<font color="blue">中英文簡介</font>]
					</html:link>&nbsp;
					<html:link page="/Student/OnlineAddDelCourse.do?method=readCourseSyllabus" paramName="row" paramId="oid" paramProperty="dtimeOid" onmouseover="stm(Text[0], Style[0])" onmouseout="htm()">
						[<font color="blue">教學大綱</font>]
					</html:link>
				</display:column>	
		        <display:column titleKey="Course.label.opt" property="optName" sortable="false" class="center" />
		        <display:column title="型態" property="elearning" sortable="false" class="center" />
		        <display:column title="通識分類" sortable="false" class="center">
		        	<c:if test="${row.literacyTypeName != ''}">
		        		<img src="images/credit-card.png" id="searchNorm" onmouseover="stm(['分類名稱', '${row.literacyTypeName}'], Style[0])" onmouseout="htm()">
		        	</c:if>
		        </display:column>
		        <display:column titleKey="Course.label.credit" property="credit" sortable="false" class="center" />
		        <display:column titleKey="Course.onlineAddRemoveCourse.stuSel" property="stuSelect" sortable="false" class="center" />
		        <display:column titleKey="Course.onlineAddRemoveCourse.selLimit" property="selectLimit" sortable="false" class="center" />
		        <display:column titleKey="Course.label.hours" property="hour" sortable="false" class="center" />
		        <display:column titleKey="Course.label.errorMessage" sortable="false" class="center">	 
		        	<font color="red"><c:out value="${row.errorMessage}" /></font>
		        </display:column>
		    </display:table>
		</td>
	</tr>	
	</c:if>
	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='course.onlineAddRemoveCourse.chooseAddCourse' bundle="COU" />" class="CourseButton">&nbsp;'
   						  + '<INPUT type="submit" name="method" value="<bean:message key='course.onlineAddRemoveCourse.deleteCourse' bundle="COU" />" onclick="return removeCourseCheck();" class="CourseButton">&nbsp;'
   						  + '<INPUT type="submit" name="method" value="<bean:message key='course.onlineAddRemoveCourse.deleteCourseList' bundle="COU" />" class="CourseButton">&nbsp;'
   						  + '<INPUT type="submit" name="method" value="<bean:message key='course.onlineAddRemoveCourse.suggest' bundle="COU" />" class="CourseButton">&nbsp;');
   	</script>
	
	<c:if test="${opencsList != null && not empty opencsList}">
	<script>generateTableBanner('<div class="gray_15"><B><bean:message key="student.onlineAddDelCourse.openCourse" bundle="STD"/></B></div>');</script>
    <%@include file="/pages/include/Displaytag4Checkbox.inc"%>
	<tr>
		<td>
			<table width="100%" cellpadding="0" cellspacing="0">
	 			<tr>
	 				<td align="center">
      					<display:table name="${opencsList}" export="false" id="row" pagesize="10000" sort="list" excludedParams="*" class="list">
	        				<%@ include file="../include/NoBanner.jsp" %>
        					<display:column title="<script>generateTriggerAll(${fn:length(opencsList)}, 'opencsInfo');</script>" class="center" >
        						<c:if test="${row.opt eq '2'}">
          						<script>generateCheckbox("${row.seldOid}", "opencsInfo");</script>
          						</c:if>
          						<c:if test="${row.opt eq '1'}">
          						<script>generateDisabledAndCheckedCheckbox("${row.seldOid}", "opencsInfo");</script>
          						</c:if>
          					</display:column>
					        <display:column titleKey="Course.label.className" property="className" sortable="false" class="center" />
					        <display:column titleKey="Course.label.courseName" sortable="false" class="left">
					        	<html:link page="/Student/OnlineAddDelCourse.do?method=readCourseIntro" paramName="row" paramId="index" paramProperty="index">
	   								<c:out value="${row.chiName}" />
	 	 						</html:link>
					        </display:column>	
					        <display:column titleKey="Course.label.techName" property="techName" sortable="false" class="center" />
					        <display:column titleKey="Course.label.opt" property="optName" sortable="false" class="center" />
					        <display:column titleKey="Course.label.credit" property="credit" sortable="false" class="center" />
					        <display:column titleKey="Course.label.hours" property="hour" sortable="false" class="center" />
					        <display:column titleKey="Course.onlineAddRemoveCourse.stuSel" property="stdSelected" sortable="false" class="center" />
					        <display:column titleKey="Course.onlineAddRemoveCourse.selLimit" property="stdLimit" sortable="false" class="center" />
					        <display:column titleKey="Course.label.classTime" property="classTime" sortable="false" class="center" />
      					</display:table>
      				</td>
      			</tr>
    		</table>
    	</td>
    </tr>
    <tr align="center"><td><strong><bean:message key="course.onlineAddRemoveCourse.optTotal" bundle="COU" />${requestScope.optCount}</strong></td></tr>
   	<script>
   		generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='course.onlineAddRemoveCourse.addCourse' bundle="COU" />" class="CourseButton">&nbsp;'
   						  + '<INPUT type="submit" name="method" value="<bean:message key='course.onlineAddRemoveCourse.deleteCourse' bundle="COU" />" onclick="return checkSelectForModify(\'seldDataInfoOpt2\');" class="CourseButton">');
   	</script>
	</c:if>
 </html:form>
</table>