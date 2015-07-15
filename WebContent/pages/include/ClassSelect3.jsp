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
			schoolInCharge3.options.length = 0;
			deptInCharge3.options.length = 0;
			classInCharge3.options.length = 0;
			if (campusInCharge3.selectedIndex > 0) {
				opt = new Option("<bean:message key="AllSchools" />", "All");
				schoolInCharge3.options.add(opt);
				for (i=0; i < schoolId[campusInCharge3.selectedIndex-1].length; i++) {
					opt = new Option(schoolName[campusInCharge3.selectedIndex-1][i],
									   schoolId[campusInCharge3.selectedIndex-1][i]);
					schoolInCharge3.options.add(opt);
				}
			} else {
				opt = new Option("", "");
				schoolInCharge3.options.add(opt);
			}
			opt = new Option("", "");
			deptInCharge3.options.add(opt);
			opt = new Option("", "");
			classInCharge3.options.add(opt);			
		}
	};
	
	function fillDepts() {
		with(document.forms[0]) {
			deptInCharge3.options.length = 0;
			classInCharge3.options.length = 0;
			if (campusInCharge3.selectedIndex > 0 && schoolInCharge3.selectedIndex > 0) {
				opt = new Option("<bean:message key='AllDepts' />", "All");
				deptInCharge3.options.add(opt);
				for (i=0; i < deptId[campusInCharge3.selectedIndex-1][schoolInCharge3.selectedIndex-1].length; i++) {
					opt = new Option(deptName[campusInCharge3.selectedIndex-1][schoolInCharge3.selectedIndex-1][i],
									   deptId[campusInCharge3.selectedIndex-1][schoolInCharge3.selectedIndex-1][i]);
					deptInCharge3.options.add(opt);
				}
			} else {			
				opt = new Option("", "");
				deptInCharge3.options.add(opt);
			}
			opt = new Option("", "");
			classInCharge3.options.add(opt);
		}
	};

	function fillClasses() {
		with(document.forms[0]) {
			classInCharge3.options.length = 0;
			if (campusInCharge3.selectedIndex > 0 && schoolInCharge3.selectedIndex > 0 && deptInCharge3.selectedIndex > 0) {
				opt = new Option("<bean:message key='AllClasses' />", "All");
				classInCharge3.options.add(opt);
				for (i=0; i < classId[campusInCharge3.selectedIndex-1][schoolInCharge3.selectedIndex-1][deptInCharge3.selectedIndex-1].length; i++) {
					opt = new Option(classLabel[campusInCharge3.selectedIndex-1][schoolInCharge3.selectedIndex-1][deptInCharge3.selectedIndex-1][i],
									    classId[campusInCharge3.selectedIndex-1][schoolInCharge3.selectedIndex-1][deptInCharge3.selectedIndex-1][i]);
					classInCharge3.options.add(opt);
				}
			} else {
				opt = new Option("", "");
				classInCharge3.options.add(opt);
			}			
		}
	};
	
	function restoreSelections(school, dept, clazz) {
		with(document.forms[0]) {
			//alert(campusInCharge.selectedIndex);
			if (campusInCharge3.selectedIndex > 0) {
				opt = new Option("<bean:message key='AllSchools' />", "All");
				schoolInCharge3.options.add(opt);
				for (i=0; i < schoolId[campusInCharge3.selectedIndex-1].length; i++) {
					opt = new Option(schoolName[campusInCharge3.selectedIndex-1][i],
									   schoolId[campusInCharge3.selectedIndex-1][i]);
					schoolInCharge3.options.add(opt);
					if (opt.value == school) {
						opt.selected = true;
					}
				}
				if (schoolInCharge3.selectedIndex > 0) {
					opt = new Option("<bean:message key='AllDepts' />", "All");
					deptInCharge3.options.add(opt);
					for (i=0; i < deptId[campusInCharge3.selectedIndex-1][schoolInCharge3.selectedIndex-1].length; i++) {
						opt = new Option(deptName[campusInCharge3.selectedIndex-1][schoolInCharge3.selectedIndex-1][i],
										   deptId[campusInCharge3.selectedIndex-1][schoolInCharge3.selectedIndex-1][i]);
						deptInCharge3.options.add(opt);
						if (opt.value == dept) {
							opt.selected = true;
						}	
					}
					if (deptInCharge3.selectedIndex > 0) {
						opt = new Option("<bean:message key='AllClasses' />", "All");
						classInCharge3.options.add(opt);
						for (i=0; i < classId[campusInCharge3.selectedIndex-1][schoolInCharge3.selectedIndex-1][deptInCharge3.selectedIndex-1].length; i++) {
							opt = new Option(classLabel[campusInCharge3.selectedIndex-1][schoolInCharge3.selectedIndex-1][deptInCharge3.selectedIndex-1][i],
											    classId[campusInCharge3.selectedIndex-1][schoolInCharge3.selectedIndex-1][deptInCharge3.selectedIndex-1][i]);
							classInCharge3.options.add(opt);
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
  <SELECT name="campusInCharge3" onchange="fillSchools();">
  	<OPTION value="All"><bean:message key="AllCampuses" /></OPTION>
	<c:if test="${not empty Credential}">
      <c:forEach items="${Credential.campusInCharge}" var="campus">
		<OPTION value="${campus.idno}" <c:if test="${campus.idno==campusSel}">selected</c:if>>${campus.name}</OPTION>
	  </c:forEach>
    </c:if></SELECT><SELECT name="schoolInCharge3" onchange="fillDepts();">
    </SELECT><SELECT name="deptInCharge3" onchange="fillClasses();">
    </SELECT><SELECT name="classInCharge3"></SELECT>	   		    
    <script>restoreSelections('${schoolSel}', '${deptSel}', '${classSel}');</script>			    
