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
	
	function fillcourse() {
		with(document.forms[0]) {
			if(classInCharge2.value != "") {
				document.forms[0].submit();
			}
		}		
	};
// -->
</script>
  <SELECT name="campusInCharge2" onchange="fillSchools();">
  	<OPTION value="All"><bean:message key="AllCampuses" /></OPTION>
	<c:if test="${not empty Credential}">
      <c:forEach items="${Credential.campusInCharge}" var="campus">
		<OPTION value="${campus.idno}" <c:if test="${campus.idno==campusSel}">selected</c:if>>${campus.name}</OPTION>
	  </c:forEach>
    </c:if></SELECT><SELECT name="schoolInCharge2" onchange="fillDepts();">
    </SELECT><SELECT name="deptInCharge2" onchange="fillClasses();">
    </SELECT><SELECT name="classInCharge2" onchange="fillcourse();"></SELECT>	   		    
    <script>restoreSelections('${schoolSel}', '${deptSel}', '${classSel}');</script>			    
