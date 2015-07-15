<script language="javascript">
<!--
	var schoolName = ${Credential.javaScriptArrayC.schoolName};
	var schoolId   = ${Credential.javaScriptArrayC.schoolId};
	var deptName   = ${Credential.javaScriptArrayC.deptName};
	var deptId     = ${Credential.javaScriptArrayC.deptId};
	var classLabel = ${Credential.javaScriptArrayC.className};
	var classId    = ${Credential.javaScriptArrayC.classId};
	
	function fillSchools() {
		with(document.forms[0]) {
			schoolInChargeC.options.length = 0;
			deptInChargeC.options.length = 0;
			classInChargeC.options.length = 0;
			if (campusInChargeC.selectedIndex > 0) {
				opt = new Option("<bean:message key="AllSchools" />", "All");
				schoolInChargeC.options.add(opt);
				for (i=0; i < schoolId[campusInChargeC.selectedIndex-1].length; i++) {
					opt = new Option(schoolName[campusInChargeC.selectedIndex-1][i],
									   schoolId[campusInChargeC.selectedIndex-1][i]);
					schoolInChargeC.options.add(opt);
				}
			} else {
				opt = new Option("", "");
				schoolInChargeC.options.add(opt);
			}
			opt = new Option("", "");
			deptInChargeC.options.add(opt);
			opt = new Option("", "");
			classInChargeC.options.add(opt);			
		}
	};
	
	function fillDepts() {
		with(document.forms[0]) {
			deptInChargeC.options.length = 0;
			classInChargeC.options.length = 0;
			if (campusInChargeC.selectedIndex > 0 && schoolInChargeC.selectedIndex > 0) {
				opt = new Option("<bean:message key='AllDepts' />", "All");
				deptInChargeC.options.add(opt);
				for (i=0; i < deptId[campusInChargeC.selectedIndex-1][schoolInChargeC.selectedIndex-1].length; i++) {
					opt = new Option(deptName[campusInChargeC.selectedIndex-1][schoolInChargeC.selectedIndex-1][i],
									   deptId[campusInChargeC.selectedIndex-1][schoolInChargeC.selectedIndex-1][i]);
					deptInChargeC.options.add(opt);
				}
			} else {			
				opt = new Option("", "");
				deptInChargeC.options.add(opt);
			}
			opt = new Option("", "");
			classInChargeC.options.add(opt);
		}
	};

	function fillClasses() {
		with(document.forms[0]) {
			classInChargeC.options.length = 0;
			if (campusInChargeC.selectedIndex > 0 && schoolInChargeC.selectedIndex > 0 && deptInChargeC.selectedIndex > 0) {
				opt = new Option("<bean:message key='AllClasses' />", "All");
				classInChargeC.options.add(opt);
				for (i=0; i < classId[campusInChargeC.selectedIndex-1][schoolInChargeC.selectedIndex-1][deptInChargeC.selectedIndex-1].length; i++) {
					opt = new Option(classLabel[campusInChargeC.selectedIndex-1][schoolInChargeC.selectedIndex-1][deptInChargeC.selectedIndex-1][i],
									    classId[campusInChargeC.selectedIndex-1][schoolInChargeC.selectedIndex-1][deptInChargeC.selectedIndex-1][i]);
					classInChargeC.options.add(opt);
				}
			} else {
				opt = new Option("", "");
				classInChargeC.options.add(opt);
			}			
		}
	};
	
	function restoreSelections(school, dept, clazz) {
		with(document.forms[0]) {
			//alert(campusInCharge.selectedIndex);
			if (campusInChargeC.selectedIndex > 0) {
				opt = new Option("<bean:message key='AllSchools' />", "All");
				schoolInChargeC.options.add(opt);
				for (i=0; i < schoolId[campusInChargeC.selectedIndex-1].length; i++) {
					opt = new Option(schoolName[campusInChargeC.selectedIndex-1][i],
									   schoolId[campusInChargeC.selectedIndex-1][i]);
					schoolInChargeC.options.add(opt);
					if (opt.value == school) {
						opt.selected = true;
					}
				}
				if (schoolInChargeC.selectedIndex > 0) {
					opt = new Option("<bean:message key='AllDepts' />", "All");
					deptInChargeC.options.add(opt);
					for (i=0; i < deptId[campusInChargeC.selectedIndex-1][schoolInChargeC.selectedIndex-1].length; i++) {
						opt = new Option(deptName[campusInChargeC.selectedIndex-1][schoolInChargeC.selectedIndex-1][i],
										   deptId[campusInChargeC.selectedIndex-1][schoolInChargeC.selectedIndex-1][i]);
						deptInChargeC.options.add(opt);
						if (opt.value == dept) {
							opt.selected = true;
						}	
					}
					if (deptInChargeC.selectedIndex > 0) {
						opt = new Option("<bean:message key='AllClasses' />", "All");
						classInChargeC.options.add(opt);
						for (i=0; i < classId[campusInChargeC.selectedIndex-1][schoolInChargeC.selectedIndex-1][deptInChargeC.selectedIndex-1].length; i++) {
							opt = new Option(classLabel[campusInChargeC.selectedIndex-1][schoolInChargeC.selectedIndex-1][deptInChargeC.selectedIndex-1][i],
											    classId[campusInChargeC.selectedIndex-1][schoolInChargeC.selectedIndex-1][deptInChargeC.selectedIndex-1][i]);
							classInChargeC.options.add(opt);
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
  <SELECT name="campusInChargeC" onchange="fillSchools();">
  	<OPTION value="All"><bean:message key="AllCampuses" /></OPTION>
	<c:if test="${not empty Credential}">
      <c:forEach items="${Credential.campusInChargeC}" var="campus">
		<OPTION value="${campus.idno}" <c:if test="${campus.idno==campusSel}">selected</c:if>>${campus.name}</OPTION>
	  </c:forEach>
    </c:if></SELECT><SELECT name="schoolInChargeC" onchange="fillDepts();">
    </SELECT><SELECT name="deptInChargeC" onchange="fillClasses();">
    </SELECT><SELECT name="classInChargeC"></SELECT>	   		    
    <script>restoreSelections('${schoolSel}', '${deptSel}', '${classSel}');</script>			    
