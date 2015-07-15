<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pages/include/AllClassInCharge.js" %>
<%@ include file="/pages/include/ClassSelection0.js" %>

<table width="100%">
 <tr><td align="left">
  <SELECT name="campusInCharge" onchange="form.classCode.value='';fillSchools();">
  	<OPTION value="All"><bean:message key="AllCampuses" /></OPTION>
	<c:if test="${not empty Credential}">
      <c:forEach items="${AllCampuses}" var="campus">
		<OPTION value="${campus.idno}" <c:if test="${campus.idno==campusSel}">selected</c:if>>${campus.name}</OPTION>
	  </c:forEach>
    </c:if></SELECT><SELECT name="schoolInCharge" onchange="form.classCode.value='';fillDepts();">
    </SELECT><SELECT name="deptInCharge"  onchange="form.classCode.value='';fillClasses();">
    </SELECT><SELECT name="classInCharge" onchange="if(this.value=='All'){form.classCode.value='';}else{form.classCode.value=this.value;}"></SELECT></td></tr>
 <tr><td align="left">
    班級代碼&nbsp;
  <input type="text" size="10" name="classCode" value="" ></td></tr>
</table>		    
