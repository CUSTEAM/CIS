<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/pages/include/AllClassInCharge.js" %>
<%@ include file="/pages/include/ClassSelection2.js" %>
  <SELECT name="campusInCharge2" onchange="fillSchools(), getLess1('campusInCharge2')">
  	<OPTION value="All"><bean:message key="AllCampuses" /></OPTION>
	<c:if test="${not empty Credential}">
      <c:forEach items="${AllCampuses}" var="campus">
		<OPTION value="${campus.idno}" <c:if test="${campus.idno==DtimestampManagerForm.map.campusInCharge2}">selected</c:if>>${campus.name}</OPTION>
	  </c:forEach>
    </c:if></SELECT><SELECT name="schoolInCharge2" onchange="fillDepts(), getLess2('schoolInCharge2')"></SELECT>
    <SELECT name="deptInCharge2" onchange="fillClasses(), getLess3('deptInCharge2')" style="display:none;">
    </SELECT>    
    <SELECT name="classInCharge2" onChange="getLess4('classInCharge2')" style="display:none;"></SELECT><input type="text"
    size="4" name="Cidno" id="classLess" value="${DtimestampManagerForm.map.Cidno}" onclick="this.value=''" style="display:none;"/>
    <input type="text"
    size="4" name="Sidno" id="classLess1" value="${DtimestampManagerForm.map.Sidno}" onclick="this.value=''" style="display:none;"/>
    <script>restoreSelections('${schoolSel}', '${deptSel}', '${classSel}');</script>			    
<script>
		function getLess1(Oid){

			if(document.getElementById(Oid).value=="All"){
				document.getElementById("classLess").value="";
			}else{
				document.getElementById("classLess").value=document.getElementById(Oid).value;
			}
		}

		function getLess2(Oid){

			if(document.getElementById(Oid).value=="All"){
				document.getElementById("classLess1").value="";
			}else{
				document.getElementById("classLess1").value=document.getElementById(Oid).value;
			}
		}

		

	</script>