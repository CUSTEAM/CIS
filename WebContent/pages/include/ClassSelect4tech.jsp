<script language="javascript">
<!--
	var schoolName = ${Credential.javaScriptArraySAF.schoolName};
	var schoolId   = ${Credential.javaScriptArraySAF.schoolId};
	var deptName   = ${Credential.javaScriptArraySAF.deptName};
	var deptId     = ${Credential.javaScriptArraySAF.deptId};
	var classLabel = ${Credential.javaScriptArraySAF.className};
	var classId    = ${Credential.javaScriptArraySAF.classId};

	function fillSchools() {
		with(document.forms[0]) {
			schoolInChargeSAF.options.length = 0;
			deptInChargeSAF.options.length = 0;
			classInChargeSAF.options.length = 0;
			if (campusInChargeSAF.selectedIndex > 0) {
				opt = new Option("<bean:message key="AllSchools" />", "All");
				schoolInChargeSAF.options.add(opt);
				for (i=0; i < schoolId[campusInChargeSAF.selectedIndex-1].length; i++) {
					opt = new Option(schoolName[campusInChargeSAF.selectedIndex-1][i],
									   schoolId[campusInChargeSAF.selectedIndex-1][i]);
					schoolInChargeSAF.options.add(opt);
				}
			} else {
				opt = new Option("", "");
				schoolInChargeSAF.options.add(opt);
			}
			opt = new Option("", "");
			deptInChargeSAF.options.add(opt);
			opt = new Option("", "");
			classInChargeSAF.options.add(opt);
		}
	};

	function fillDepts() {
		with(document.forms[0]) {
			deptInChargeSAF.options.length = 0;
			classInChargeSAF.options.length = 0;
			if (campusInChargeSAF.selectedIndex > 0 && schoolInChargeSAF.selectedIndex > 0) {
				opt = new Option("<bean:message key='AllDepts' />", "All");
				deptInChargeSAF.options.add(opt);
				for (i=0; i < deptId[campusInChargeSAF.selectedIndex-1][schoolInChargeSAF.selectedIndex-1].length; i++) {
					opt = new Option(deptName[campusInChargeSAF.selectedIndex-1][schoolInChargeSAF.selectedIndex-1][i],
									   deptId[campusInChargeSAF.selectedIndex-1][schoolInChargeSAF.selectedIndex-1][i]);
					deptInChargeSAF.options.add(opt);
				}
			} else {
				opt = new Option("", "");
				deptInChargeSAF.options.add(opt);
			}
			opt = new Option("", "");
			classInChargeSAF.options.add(opt);
		}
	};

	function fillClasses() {
		with(document.forms[0]) {
			classInChargeSAF.options.length = 0;
			if (campusInChargeSAF.selectedIndex > 0 && schoolInChargeSAF.selectedIndex > 0 && deptInChargeSAF.selectedIndex > 0) {
				opt = new Option("<bean:message key='AllClasses' />", "All");
				classInChargeSAF.options.add(opt);
				for (i=0; i < classId[campusInChargeSAF.selectedIndex-1][schoolInChargeSAF.selectedIndex-1][deptInChargeSAF.selectedIndex-1].length; i++) {
					opt = new Option(classLabel[campusInChargeSAF.selectedIndex-1][schoolInChargeSAF.selectedIndex-1][deptInChargeSAF.selectedIndex-1][i],
									    classId[campusInChargeSAF.selectedIndex-1][schoolInChargeSAF.selectedIndex-1][deptInChargeSAF.selectedIndex-1][i]);
					classInChargeSAF.options.add(opt);
				}
			} else {
				opt = new Option("", "");
				classInChargeSAF.options.add(opt);
			}
		}
	};

	function restoreSelections(school, dept, clazz) {
		with(document.forms[0]) {
			//alert(campusInCharge.selectedIndex);
			if (campusInChargeSAF.selectedIndex > 0) {
				opt = new Option("<bean:message key='AllSchools' />", "All");
				schoolInChargeSAF.options.add(opt);
				for (i=0; i < schoolId[campusInChargeSAF.selectedIndex-1].length; i++) {
					opt = new Option(schoolName[campusInChargeSAF.selectedIndex-1][i],
									   schoolId[campusInChargeSAF.selectedIndex-1][i]);
					schoolInChargeSAF.options.add(opt);
					if (opt.value == school) {
						opt.selected = true;
					}
				}
				if (schoolInChargeSAF.selectedIndex > 0) {
					opt = new Option("<bean:message key='AllDepts' />", "All");
					deptInChargeSAF.options.add(opt);
					for (i=0; i < deptId[campusInChargeSAF.selectedIndex-1][schoolInChargeSAF.selectedIndex-1].length; i++) {
						opt = new Option(deptName[campusInChargeSAF.selectedIndex-1][schoolInChargeSAF.selectedIndex-1][i],
										   deptId[campusInChargeSAF.selectedIndex-1][schoolInChargeSAF.selectedIndex-1][i]);
						deptInChargeSAF.options.add(opt);
						if (opt.value == dept) {
							opt.selected = true;
						}
					}
					if (deptInChargeSAF.selectedIndex > 0) {
						opt = new Option("<bean:message key='AllClasses' />", "All");
						classInChargeSAF.options.add(opt);
						for (i=0; i < classId[campusInChargeSAF.selectedIndex-1][schoolInChargeSAF.selectedIndex-1][deptInChargeSAF.selectedIndex-1].length; i++) {
							opt = new Option(classLabel[campusInChargeSAF.selectedIndex-1][schoolInChargeSAF.selectedIndex-1][deptInChargeSAF.selectedIndex-1][i],
											    classId[campusInChargeSAF.selectedIndex-1][schoolInChargeSAF.selectedIndex-1][deptInChargeSAF.selectedIndex-1][i]);
							classInChargeSAF.options.add(opt);
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
  <SELECT id="campusInChargeSAF" name="campusInChargeSAF" onchange="fillSchools(), getLess1('campusInChargeSAF');">
  	<OPTION value="All"><bean:message key="AllCampuses" /></OPTION>
	<c:if test="${not empty Credential}">
      <c:forEach items="${Credential.campusInChargeSAF}" var="campus">
		<OPTION value="${campus.idno}" <c:if test="${campus.idno==campusSel}">selected</c:if>>${campus.name}</OPTION>
	  </c:forEach>
    </c:if></SELECT><SELECT id="schoolInChargeSAF" name="schoolInChargeSAF" onchange="fillDepts(), getLess2('schoolInChargeSAF');">
    </SELECT><SELECT id="deptInChargeSAF" name="deptInChargeSAF" onchange="fillClasses(), getLess3('deptInChargeSAF');">
    </SELECT><SELECT id="classInChargeSAF" name="classInChargeSAF" onchange="getLess4('classInChargeSAF')"></SELECT>

    <input type="text" size="4" name="classLess" id="classLess" value="${OpenCourseForm.map.classLess}"/>
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
				document.getElementById("classLess").value=document.getElementById("campusInChargeSAF").value+"";
			}else{
				document.getElementById("classLess").value=document.getElementById("campusInChargeSAF").value+document.getElementById(Oid).value;
			}
		}

		function getLess3(Oid){

			if(document.getElementById(Oid).value=="All"){
				document.getElementById("classLess").value=document.getElementById("campusInChargeSAF").value+document.getElementById("schoolInChargeSAF").value+"";
			}else{
				document.getElementById("classLess").value=document.getElementById("campusInChargeSAF").value+document.getElementById("schoolInChargeSAF").value+document.getElementById(Oid).value;
			}
		}

		function getLess4(Oid){

			if(document.getElementById(Oid).value=="All"){
				document.getElementById("classLess").value=document.getElementById("campusInChargeSAF").value+document.getElementById("schoolInChargeSAF").value+"";
			}else{
				document.getElementById("classLess").value=document.getElementById(Oid).value;
			}
		}

	</script>