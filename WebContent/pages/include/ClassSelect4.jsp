<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<script language="javascript">
<!--
	var schoolName = ${Credential.javaScriptArray.schoolName};
	var schoolId   = ${Credential.javaScriptArray.schoolId};
	var deptName   = ${Credential.javaScriptArray.deptName};
	var deptId     = ${Credential.javaScriptArray.deptId};
	var classLabel = ${Credential.javaScriptArray.className};
	var classId    = ${Credential.javaScriptArray.classId};

	function fillSchools() {
		with(document.forms[0]) {
			schoolInCharge2.options.length = 0;
			deptInCharge2.options.length = 0;
			classInCharge2.options.length = 0;
			if (campusInCharge2.selectedIndex > 0) {
				opt = new Option("<bean:message key="AllSchools" />", "All");
				schoolInCharge2.options.add(opt);
				for (i=0; i < schoolId[campusInCharge2.selectedIndex-1].length; i++) {
					opt = new Option(schoolName[campusInCharge2.selectedIndex-1][i],
									   schoolId[campusInCharge2.selectedIndex-1][i]);
					schoolInCharge2.options.add(opt);
				}
			} else {
				opt = new Option("", "");
				schoolInCharge2.options.add(opt);
			}
			opt = new Option("", "");
			deptInCharge2.options.add(opt);
			opt = new Option("", "");
			classInCharge2.options.add(opt);
		}
	};

	function fillDepts() {
		with(document.forms[0]) {
			deptInCharge2.options.length = 0;
			classInCharge2.options.length = 0;
			if (campusInCharge2.selectedIndex > 0 && schoolInCharge2.selectedIndex > 0) {
				opt = new Option("<bean:message key='AllDepts' />", "All");
				deptInCharge2.options.add(opt);
				for (i=0; i < deptId[campusInCharge2.selectedIndex-1][schoolInCharge2.selectedIndex-1].length; i++) {
					opt = new Option(deptName[campusInCharge2.selectedIndex-1][schoolInCharge2.selectedIndex-1][i],
									   deptId[campusInCharge2.selectedIndex-1][schoolInCharge2.selectedIndex-1][i]);
					deptInCharge2.options.add(opt);
				}
			} else {
				opt = new Option("", "");
				deptInCharge2.options.add(opt);
			}
			opt = new Option("", "");
			classInCharge2.options.add(opt);
		}
	};

	function fillClasses() {
		with(document.forms[0]) {
			classInCharge2.options.length = 0;
			if (campusInCharge2.selectedIndex > 0 && schoolInCharge2.selectedIndex > 0 && deptInCharge2.selectedIndex > 0) {
				opt = new Option("<bean:message key='AllClasses' />", "All");
				classInCharge2.options.add(opt);
				for (i=0; i < classId[campusInCharge2.selectedIndex-1][schoolInCharge2.selectedIndex-1][deptInCharge2.selectedIndex-1].length; i++) {
					opt = new Option(classLabel[campusInCharge2.selectedIndex-1][schoolInCharge2.selectedIndex-1][deptInCharge2.selectedIndex-1][i],
									    classId[campusInCharge2.selectedIndex-1][schoolInCharge2.selectedIndex-1][deptInCharge2.selectedIndex-1][i]);
					classInCharge2.options.add(opt);
				}
			} else {
				opt = new Option("", "");
				classInCharge2.options.add(opt);
			}
		}
	};

	function restoreSelections(school, dept, clazz) {
		with(document.forms[0]) {
			//alert(campusInCharge.selectedIndex);
			if (campusInCharge2.selectedIndex > 0) {
				opt = new Option("<bean:message key='AllSchools' />", "All");
				schoolInCharge2.options.add(opt);
				for (i=0; i < schoolId[campusInCharge2.selectedIndex-1].length; i++) {
					opt = new Option(schoolName[campusInCharge2.selectedIndex-1][i],
									   schoolId[campusInCharge2.selectedIndex-1][i]);
					schoolInCharge2.options.add(opt);
					if (opt.value == school) {
						opt.selected = true;
					}
				}
				if (schoolInCharge2.selectedIndex > 0) {
					opt = new Option("<bean:message key='AllDepts' />", "All");
					deptInCharge2.options.add(opt);
					for (i=0; i < deptId[campusInCharge2.selectedIndex-1][schoolInCharge2.selectedIndex-1].length; i++) {
						opt = new Option(deptName[campusInCharge2.selectedIndex-1][schoolInCharge2.selectedIndex-1][i],
										   deptId[campusInCharge2.selectedIndex-1][schoolInCharge2.selectedIndex-1][i]);
						deptInCharge2.options.add(opt);
						if (opt.value == dept) {
							opt.selected = true;
						}
					}
					if (deptInCharge2.selectedIndex > 0) {
						opt = new Option("<bean:message key='AllClasses' />", "All");
						classInCharge2.options.add(opt);
						for (i=0; i < classId[campusInCharge2.selectedIndex-1][schoolInCharge2.selectedIndex-1][deptInCharge2.selectedIndex-1].length; i++) {
							opt = new Option(classLabel[campusInCharge2.selectedIndex-1][schoolInCharge2.selectedIndex-1][deptInCharge2.selectedIndex-1][i],
											    classId[campusInCharge2.selectedIndex-1][schoolInCharge2.selectedIndex-1][deptInCharge2.selectedIndex-1][i]);
							classInCharge2.options.add(opt);
							if (opt.value == clazz) {
								opt.selected = true;
							}
						}
					}
				}
			}
		}
	};
// -->
</script>
  <SELECT name="campusInCharge2" id="campusInCharge2" onchange="fillSchools(), getLess1('campusInCharge2')">
  	<OPTION value="All"><bean:message key="AllCampuses" /></OPTION>
	<c:if test="${not empty Credential}">
      <c:forEach items="${Credential.campusInCharge}" var="campus">
		<OPTION value="${campus.idno}" <c:if test="${campus.idno==campusSel}">selected</c:if>>${campus.name}</OPTION>
	  </c:forEach>
    </c:if></SELECT><SELECT name="schoolInCharge2" id="schoolInCharge2" onchange="fillDepts(), getLess2('schoolInCharge2')">
    </SELECT><SELECT name="deptInCharge2" id="deptInCharge2" onchange="fillClasses(), getLess3('deptInCharge2')">
    </SELECT><SELECT name="classInCharge2" onchange="getLess4('classInCharge2')"></SELECT><input type="text"
    size="4" name="classLess" id="classLess" value="${OpenCourseForm.map.classLess}" onclick="this.value=''"
    onMouseOver="showHelpMessage('您可以直接輸入班級代碼, 無需點選四層下拉選單 <br>'+ 
						   '例如: 164, 1643, 164321, ___3, 1__3, ____2...有更豐富的查詢方法', 'inline', this.id)" 
					 	   onMouseOut="showHelpMessage('', 'none', this.id)"/>
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