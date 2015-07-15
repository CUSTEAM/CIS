<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/AllClassInCharge.js" %>
<%@ include file="/pages/include/ClassSelection.js" %>
<%@ include file="/pages/include/tooltips.js" %>

<DIV id="TipLayer" style="visibility:hidden;position:absolute;z-index:1000;top:-100"></DIV>

<script>
history.go(1);
Style[0]=["white","black","#00B333","#E8E8FF","","","","","","","","","","",150,"",2,2,10,10,51,0.5,75,"simple","gray"];
var FiltersEnabled = 1;
applyCssFilter();
function addCourseCheck() {
	var iCount;
	iCount = getCookie("seldDataInfoOpt2Count");
	if (iCount == 0) {
		alert("請至少勾選一門科目進行加選!!");
		return false;
	} else {
		if(confirm("確定加選[" + iCount + "]門科目?"))
			return true;
		else 
			return false;	
	}
}
</script>

<table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Student/OnlineAddDelCourse" method="post" onsubmit="init('系統處理中...')">
	<%--
	<script>generateTableBanner('<div class="gray_15"><B><bean:message key="student.onlineAddDelCourse.banner" bundle="STD"/></B></div>');</script>
	<tr>
		<td>
			<table class="empty-border">
				<tr>
        			<td valign="top" id="maincontent"><div>
        				開課班級：
						<select name="campusInCharge" onchange="fillSchools();" disabled="true">
							<option value="All"><bean:message key="AllCampuses" /></option>
							<c:forEach items="${AllCampuses}" var="campus">
							<option value="${campus.idno}" <c:if test="${campus.idno==StdClass.campusNo}">selected</c:if>>${campus.name}</option>	
							</c:forEach>
						</select>
						<select name="schoolInCharge" onchange="fillDepts();"></select>
						<select name="deptInCharge" onchange="fillClasses();"></select>
						<select name="classInCharge"></select>
          			</td>	
          		</tr>	          								
			</table>
			<script>
				generateTableBanner('<INPUT type="submit" name="method" value="<bean:message key='course.courseSearch.btn.opencsSearch' bundle="COU" />" class="CourseButton">&nbsp;'
								+ '<INPUT type="submit" name="method" value="<bean:message key='Back' />" class="CourseButton">');
			</script>
		</td>
	</tr> 
	 --%>
	
	<script>
		generateTableBanner('<div class="gray_15"><B> 可 加 退 選 課 程 資 料 </B></div>');
	</script>
	<tr>
		<td>
			<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
			<display:table name="${opencsInfo}" export="false" id="row" pagesize="10000" sort="list" excludedParams="*" class="list">
		        <%@ include file="../include/NoBanner.jsp" %>	
		        <c:if test="${row.canChoose != '1'}">
		        <display:column title="<script>generateTriggerAll(${fn:length(opencsInfo)}, 'opencsInfo'); </script>" class="center">
					<script>generateCheckbox("${row.oid}", "seldDataInfoOpt2")</script>
		        </display:column>	    
		        </c:if>   
		        <c:if test="${row.canChoose == '1'}">
		        <display:column title="" class="center">
		        	<img src="images/remove.png" id="searchNorm" onmouseover="stm(['訊息', '${row.errorMessage}'], Style[0])" onmouseout="htm()">	
		        </display:column>	    
		        </c:if> 
		        <display:column titleKey="Course.label.className" property="ClassName" sortable="true" class="center" />
		        <display:column titleKey="Course.label.courseName" property="chi_name" sortable="true" class="left" />
		        <display:column titleKey="Course.label.techName" property="cname" sortable="true" class="center" />
		        <display:column titleKey="Course.label.opt" property="opt2" sortable="true" class="center" />
		        <display:column title="型態" property="elearningName" sortable="true" class="center" />
		        <display:column title="通識分類" sortable="false" class="center">
		        	<c:if test="${row.literacyTypeName != ''}">
		        		<img src="images/credit-card.png" id="searchNorm" onmouseover="stm(['分類名稱', '${row.literacyTypeName}'], Style[0])" onmouseout="htm()">
		        	</c:if>
		        </display:column>
		        <display:column titleKey="Course.label.credit" property="credit" sortable="false" class="center" />
		        <display:column titleKey="Course.label.hours" property="thour" sortable="false" class="center" />
		        <display:column titleKey="Course.label.stuSelect" property="stu_select" sortable="false" class="center" />
		        <display:column titleKey="Course.label.selLimit" property="Select_Limit" sortable="false" class="center" />
		        <display:column titleKey="Course.label.classTime" property="time2" sortable="true" class="left" />
		    </display:table>
		</td>
	</tr>
  	<script>
  		generateTableBanner('<html:submit property="method" styleClass="CourseButton" onclick="return addCourseCheck();"><bean:message key="course.courseSearch.btn.addOpencs" bundle="COU" /></html:submit>&nbsp;'
  						+ '<INPUT type="submit" name="method" value="<bean:message key='Back' />" class="CourseButton">');
 	</script>
 	  
</html:form>
</table>

