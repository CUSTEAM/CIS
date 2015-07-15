<script language="javascript">
<!--
	var schoolName = ${Credential.javaScriptArrayT.schoolName};
	var schoolId   = ${Credential.javaScriptArrayT.schoolId};
	var deptName   = ${Credential.javaScriptArrayT.deptName};
	var deptId     = ${Credential.javaScriptArrayT.deptId};
	var classLabel = ${Credential.javaScriptArrayT.className};
	var classId    = ${Credential.javaScriptArrayT.classId};
	
	function fillSchools() {
		with(document.forms[0]) {
			schoolInChargeT.options.length = 0;
			deptInChargeT.options.length = 0;
			classInChargeT.options.length = 0;
			if (campusInChargeT.selectedIndex > 0) {
				opt = new Option("<bean:message key="AllSchools" />", "All");
				schoolInChargeT.options.add(opt);
				for (i=0; i < schoolId[campusInChargeT.selectedIndex-1].length; i++) {
					opt = new Option(schoolName[campusInChargeT.selectedIndex-1][i],
									   schoolId[campusInChargeT.selectedIndex-1][i]);
					schoolInChargeT.options.add(opt);
				}
			} else {
				opt = new Option("", "");
				schoolInChargeT.options.add(opt);
			}
			opt = new Option("", "");
			deptInChargeT.options.add(opt);
			opt = new Option("", "");
			classInChargeT.options.add(opt);			
		}
	};
	
	function fillDepts() {
		with(document.forms[0]) {
			deptInChargeT.options.length = 0;
			classInChargeT.options.length = 0;
			if (campusInChargeT.selectedIndex > 0 && schoolInChargeT.selectedIndex > 0) {
				opt = new Option("<bean:message key='AllDepts' />", "All");
				deptInChargeT.options.add(opt);
				for (i=0; i < deptId[campusInChargeT.selectedIndex-1][schoolInChargeT.selectedIndex-1].length; i++) {
					opt = new Option(deptName[campusInChargeT.selectedIndex-1][schoolInChargeT.selectedIndex-1][i],
									   deptId[campusInChargeT.selectedIndex-1][schoolInChargeT.selectedIndex-1][i]);
					deptInChargeT.options.add(opt);
				}
			} else {			
				opt = new Option("", "");
				deptInChargeT.options.add(opt);
			}
			opt = new Option("", "");
			classInChargeT.options.add(opt);
		}
	};

	function fillClasses() {
		with(document.forms[0]) {
			classInChargeT.options.length = 0;
			if (campusInChargeT.selectedIndex > 0 && schoolInChargeT.selectedIndex > 0 && deptInChargeT.selectedIndex > 0) {
				opt = new Option("<bean:message key='AllClasses' />", "All");
				classInChargeT.options.add(opt);
				for (i=0; i < classId[campusInChargeT.selectedIndex-1][schoolInChargeT.selectedIndex-1][deptInChargeT.selectedIndex-1].length; i++) {
					opt = new Option(classLabel[campusInChargeT.selectedIndex-1][schoolInChargeT.selectedIndex-1][deptInChargeT.selectedIndex-1][i],
									    classId[campusInChargeT.selectedIndex-1][schoolInChargeT.selectedIndex-1][deptInChargeT.selectedIndex-1][i]);
					classInChargeT.options.add(opt);
				}
			} else {
				opt = new Option("", "");
				classInChargeT.options.add(opt);
			}			
		}
	};
	
	function restoreSelections(school, dept, clazz) {
		with(document.forms[0]) {
			//alert(campusInCharge.selectedIndex);
			if (campusInChargeT.selectedIndex > 0) {
				opt = new Option("<bean:message key='AllSchools' />", "All");
				schoolInChargeT.options.add(opt);
				for (i=0; i < schoolId[campusInChargeT.selectedIndex-1].length; i++) {
					opt = new Option(schoolName[campusInChargeT.selectedIndex-1][i],
									   schoolId[campusInChargeT.selectedIndex-1][i]);
					schoolInChargeT.options.add(opt);
					if (opt.value == school) {
						opt.selected = true;
					}
				}
				if (schoolInChargeT.selectedIndex > 0) {
					opt = new Option("<bean:message key='AllDepts' />", "All");
					deptInChargeT.options.add(opt);
					for (i=0; i < deptId[campusInChargeT.selectedIndex-1][schoolInChargeT.selectedIndex-1].length; i++) {
						opt = new Option(deptName[campusInChargeT.selectedIndex-1][schoolInChargeT.selectedIndex-1][i],
										   deptId[campusInChargeT.selectedIndex-1][schoolInChargeT.selectedIndex-1][i]);
						deptInChargeT.options.add(opt);
						if (opt.value == dept) {
							opt.selected = true;
						}	
					}
					if (deptInChargeT.selectedIndex > 0) {
						opt = new Option("<bean:message key='AllClasses' />", "All");
						classInChargeT.options.add(opt);
						for (i=0; i < classId[campusInChargeT.selectedIndex-1][schoolInChargeT.selectedIndex-1][deptInChargeT.selectedIndex-1].length; i++) {
							opt = new Option(classLabel[campusInChargeT.selectedIndex-1][schoolInChargeT.selectedIndex-1][deptInChargeT.selectedIndex-1][i],
											    classId[campusInChargeT.selectedIndex-1][schoolInChargeT.selectedIndex-1][deptInChargeT.selectedIndex-1][i]);
							classInChargeT.options.add(opt);
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
  <SELECT name="campusInChargeT" onchange="fillSchools();">
  	<OPTION value="All"><bean:message key="AllCampuses" /></OPTION>
	<c:if test="${not empty Credential}">
      <c:forEach items="${Credential.campusInChargeT}" var="campus">
		<OPTION value="${campus.idno}" <c:if test="${campus.idno==campusSel}">selected</c:if>>${campus.name}</OPTION>
	  </c:forEach>
    </c:if></SELECT><SELECT name="schoolInChargeT" onchange="fillDepts();">
    </SELECT><SELECT name="deptInChargeT" onchange="fillClasses();">
    </SELECT><SELECT name="classInChargeT"></SELECT>	   		    
    <script>restoreSelections('${schoolSel}', '${deptSel}', '${classSel}');</script>			    
