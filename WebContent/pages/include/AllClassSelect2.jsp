<%@ include file="/pages/include/AllClassInCharge.js" %>
<%@ include file="/pages/include/ClassSelection2.js" %>
  <SELECT name="campusInCharge2" onchange="fillSchools();">
  	<OPTION value="All"><bean:message key="AllCampuses" /></OPTION>
	<c:if test="${not empty Credential}">
      <c:forEach items="${AllCampuses}" var="campus">
		<OPTION value="${campus.idno}" <c:if test="${campus.idno==campusSel}">selected</c:if>>${campus.name}</OPTION>
	  </c:forEach>
    </c:if></SELECT><SELECT name="schoolInCharge2" onchange="fillDepts();">
    </SELECT><SELECT name="deptInCharge2" onchange="fillClasses();">
    </SELECT><SELECT name="classInCharge2"></SELECT>	   		    
    <script>restoreSelections('${schoolSel}', '${deptSel}', '${classSel}');</script>			    
