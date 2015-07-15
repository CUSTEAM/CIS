<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/include/AllClassInCharge.js" %>
<%@ include file="/pages/include/ClassSelection2.js" %>



<c:set var="campusSel" value="${StudentCoreForm.map.campusInCharge2}"/>
<c:set var="schoolSel" value="${StudentCoreForm.map.schoolInCharge2}"/>
<c:set var="deptSel"   value="${StudentCoreForm.map.deptInCharge2}"/>
<c:set var="classSel"  value="${StudentCoreForm.map.classInCharge2}"/>

<select style="font-size:18px;" name="campusInCharge2" id="campusInCharge2" onchange="fillSchools(), getLess1('campusInCharge2')">
  	<OPTION value="All"><bean:message key="AllCampuses" /></OPTION>
	<c:if test="${not empty Credential}">
      <c:forEach items="${AllCampuses}" var="campus">
		<OPTION value="${campus.idno}" <c:if test="${campus.idno==campusSel}">selected</c:if>>${campus.name}</OPTION>
	  </c:forEach>
    </c:if></SELECT><select style="font-size:18px;" name="schoolInCharge2" id="schoolInCharge2" onchange="fillDepts(), getLess2('schoolInCharge2')">
    </SELECT><select style="font-size:18px;" name="deptInCharge2" id="deptInCharge2" onchange="fillClasses(), getLess3('deptInCharge2')">
    </SELECT><select style="font-size:18px;" name="classInCharge2" id="classInCharge2" onChange="getLess4('classInCharge2')"></SELECT><input type="hidden"
    size="4" name="classLess" id="classLess" value="${StudentCoreForm.map.classLess}" onclick="this.value=''"/>
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