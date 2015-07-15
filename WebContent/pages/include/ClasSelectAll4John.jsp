<%@ include file="/pages/include/AllClassInCharge.js" %>
<%@ include file="/pages/include/ClassSelection2.js" %>
  <SELECT name="campusInCharge2" onchange="fillSchools(), getLess1('campusInCharge2')">
  	<OPTION value="All"><bean:message key="AllCampuses" /></OPTION>
	<c:if test="${not empty Credential}">
      <c:forEach items="${AllCampuses}" var="campus">
		<OPTION value="${campus.idno}" <c:if test="${campus.idno==campusSel}">selected</c:if>>${campus.name}</OPTION>
	  </c:forEach>
    </c:if></SELECT><SELECT name="schoolInCharge2" onchange="fillDepts(), getLess2('schoolInCharge2')">
    </SELECT><SELECT name="deptInCharge2" onchange="fillClasses(), getLess3('deptInCharge2')">
    </SELECT><SELECT name="classInCharge2" onChange="getLess4('classInCharge2')"></SELECT><input type="text" style="display:none;"
    size="4" name="classLess" id="classLess" value="${sOpenCourseForm.map.classLess}" onclick="this.value=''"/>
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
				document.getElementById("classLess").value=document.getElementById("campusInCharge2").value+"";
			}else{
				document.getElementById("classLess").value=document.getElementById("campusInCharge2").value+document.getElementById(Oid).value;
			}
		}

		function getLess3(Oid){

			if(document.getElementById(Oid).value=="All"){
				document.getElementById("classLess").value=document.getElementById("campusInCharge2").value+document.getElementById("schoolInCharge2").value+"";
			}else{
				document.getElementById("classLess").value=document.getElementById("campusInCharge2").value+document.getElementById("schoolInCharge2").value+document.getElementById(Oid).value;
			}
		}

		function getLess4(Oid){

			if(document.getElementById(Oid).value=="All"){
				document.getElementById("classLess").value=document.getElementById("campusInCharge2").value+document.getElementById("schoolInCharge2").value+"";
			}else{
				document.getElementById("classLess").value=document.getElementById(Oid).value;
			}
		}

	</script>