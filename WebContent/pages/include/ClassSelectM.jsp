<script language="javascript">
<!--
	var schoolName = ${Credential.javaScriptArrayM.schoolName};
	var schoolId   = ${Credential.javaScriptArrayM.schoolId};
	var deptName   = ${Credential.javaScriptArrayM.deptName};
	var deptId     = ${Credential.javaScriptArrayM.deptId};
	var classLabel = ${Credential.javaScriptArrayM.className};
	var classId    = ${Credential.javaScriptArrayM.classId};
	
	function fillSchools() {
		with(document.forms[0]) {
			schoolInChargeM.options.length = 0;
			deptInChargeM.options.length = 0;
			classInChargeM.options.length = 0;
			if (campusInChargeM.selectedIndex > 0) {
				opt = new Option("<bean:message key="AllSchools" />", "All");
				schoolInChargeM.options.add(opt);
				for (i=0; i < schoolId[campusInChargeM.selectedIndex-1].length; i++) {
					opt = new Option(schoolName[campusInChargeM.selectedIndex-1][i],
									   schoolId[campusInChargeM.selectedIndex-1][i]);
					schoolInChargeM.options.add(opt);
				}
			} else {
				opt = new Option("", "");
				schoolInChargeM.options.add(opt);
			}
			opt = new Option("", "");
			deptInChargeM.options.add(opt);
			opt = new Option("", "");
			classInChargeM.options.add(opt);			
		}
	};
	
	function fillDepts() {
		with(document.forms[0]) {
			deptInChargeM.options.length = 0;
			classInChargeM.options.length = 0;
			if (campusInChargeM.selectedIndex > 0 && schoolInChargeM.selectedIndex > 0) {
				opt = new Option("<bean:message key='AllDepts' />", "All");
				deptInChargeM.options.add(opt);
				for (i=0; i < deptId[campusInChargeM.selectedIndex-1][schoolInChargeM.selectedIndex-1].length; i++) {
					opt = new Option(deptName[campusInChargeM.selectedIndex-1][schoolInChargeM.selectedIndex-1][i],
									   deptId[campusInChargeM.selectedIndex-1][schoolInChargeM.selectedIndex-1][i]);
					deptInChargeM.options.add(opt);
				}
			} else {			
				opt = new Option("", "");
				deptInChargeM.options.add(opt);
			}
			opt = new Option("", "");
			classInChargeM.options.add(opt);
		}
	};

	function fillClasses() {
		with(document.forms[0]) {
			classInChargeM.options.length = 0;
			if (campusInChargeM.selectedIndex > 0 && schoolInChargeM.selectedIndex > 0 && deptInChargeM.selectedIndex > 0) {
				opt = new Option("<bean:message key='AllClasses' />", "All");
				classInChargeM.options.add(opt);
				for (i=0; i < classId[campusInChargeM.selectedIndex-1][schoolInChargeM.selectedIndex-1][deptInChargeM.selectedIndex-1].length; i++) {
					opt = new Option(classLabel[campusInChargeM.selectedIndex-1][schoolInChargeM.selectedIndex-1][deptInChargeM.selectedIndex-1][i],
									    classId[campusInChargeM.selectedIndex-1][schoolInChargeM.selectedIndex-1][deptInChargeM.selectedIndex-1][i]);
					classInChargeM.options.add(opt);
				}
			} else {
				opt = new Option("", "");
				classInChargeM.options.add(opt);
			}			
		}
	};
	
	function restoreSelections(school, dept, clazz) {
		with(document.forms[0]) {
			//alert(campusInCharge.selectedIndex);
			if (campusInChargeM.selectedIndex > 0) {
				opt = new Option("<bean:message key='AllSchools' />", "All");
				schoolInChargeM.options.add(opt);
				for (i=0; i < schoolId[campusInChargeM.selectedIndex-1].length; i++) {
					opt = new Option(schoolName[campusInChargeM.selectedIndex-1][i],
									   schoolId[campusInChargeM.selectedIndex-1][i]);
					schoolInChargeM.options.add(opt);
					if (opt.value == school) {
						opt.selected = true;
					}
				}
				if (schoolInChargeM.selectedIndex > 0) {
					opt = new Option("<bean:message key='AllDepts' />", "All");
					deptInChargeM.options.add(opt);
					for (i=0; i < deptId[campusInChargeM.selectedIndex-1][schoolInChargeM.selectedIndex-1].length; i++) {
						opt = new Option(deptName[campusInChargeM.selectedIndex-1][schoolInChargeM.selectedIndex-1][i],
										   deptId[campusInChargeM.selectedIndex-1][schoolInChargeM.selectedIndex-1][i]);
						deptInChargeM.options.add(opt);
						if (opt.value == dept) {
							opt.selected = true;
						}	
					}
					if (deptInChargeM.selectedIndex > 0) {
						opt = new Option("<bean:message key='AllClasses' />", "All");
						classInChargeM.options.add(opt);
						for (i=0; i < classId[campusInChargeM.selectedIndex-1][schoolInChargeM.selectedIndex-1][deptInChargeM.selectedIndex-1].length; i++) {
							opt = new Option(classLabel[campusInChargeM.selectedIndex-1][schoolInChargeM.selectedIndex-1][deptInChargeM.selectedIndex-1][i],
											    classId[campusInChargeM.selectedIndex-1][schoolInChargeM.selectedIndex-1][deptInChargeM.selectedIndex-1][i]);
							classInChargeM.options.add(opt);
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
  <SELECT name="campusInChargeM" onchange="fillSchools();">
  	<OPTION value="All"><bean:message key="AllCampuses" /></OPTION>
	<c:if test="${not empty Credential}">
      <c:forEach items="${Credential.campusInChargeM}" var="campus">
		<OPTION value="${campus.idno}" <c:if test="${campus.idno==campusSel}">selected</c:if>>${campus.name}</OPTION>
	  </c:forEach>
    </c:if></SELECT><SELECT name="schoolInChargeM" onchange="fillDepts();">
    </SELECT><SELECT name="deptInChargeM" onchange="fillClasses();">
    </SELECT><SELECT name="classInChargeM"></SELECT>	   		    
    <script>restoreSelections('${schoolSel}', '${deptSel}', '${classSel}');</script>			    
