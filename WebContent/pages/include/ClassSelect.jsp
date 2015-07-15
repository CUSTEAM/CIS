<%@ include file="/pages/include/ClassInCharge.js" %>
<%@ include file="/pages/include/ClassSelection.js" %>
  <SELECT name="campusInCharge" onchange="fillSchools();">
  	<OPTION value="All"><bean:message key="AllCampuses" /></OPTION>
	<c:if test="${not empty Credential}">
      <c:forEach items="${Credential.campusInCharge}" var="campus">
		<OPTION value="${campus.idno}" <c:if test="${campus.idno==campusSel}">selected</c:if>>${campus.name}</OPTION>
	  </c:forEach>
    </c:if></SELECT><SELECT name="schoolInCharge" onchange="fillDepts();">
    </SELECT><SELECT name="deptInCharge" onchange="fillClasses();">
    </SELECT><SELECT name="classInCharge"></SELECT>	   		    
    <script>restoreSelections('${schoolSel}', '${deptSel}', '${classSel}');</script>			    
