<script language="javascript">
<!--	
	function fillSchools() {
		with(document.forms[0]) {
			schoolInCharge.options.length = 0;
			deptInCharge.options.length = 0;
			classInCharge.options.length = 0;
			if (campusInCharge.selectedIndex > 0) {
				opt = new Option("<bean:message key="SelSchools" />", "All");
				schoolInCharge.options.add(opt);
				for (i=0; i < schoolId[campusInCharge.selectedIndex-1].length; i++) {
					opt = new Option(schoolName[campusInCharge.selectedIndex-1][i],
									   schoolId[campusInCharge.selectedIndex-1][i]);
					schoolInCharge.options.add(opt);
				}
			} else {
				opt = new Option("", "");
				schoolInCharge.options.add(opt);
			}
			opt = new Option("", "");
			deptInCharge.options.add(opt);
			opt = new Option("", "");
			classInCharge.options.add(opt);			
		}
	};
	
	function fillDepts() {
		with(document.forms[0]) {
			deptInCharge.options.length = 0;
			classInCharge.options.length = 0;
			if (campusInCharge.selectedIndex > 0 && schoolInCharge.selectedIndex > 0) {
				opt = new Option("<bean:message key='SelDepts' />", "All");
				deptInCharge.options.add(opt);
				for (i=0; i < deptId[campusInCharge.selectedIndex-1][schoolInCharge.selectedIndex-1].length; i++) {
					opt = new Option(deptName[campusInCharge.selectedIndex-1][schoolInCharge.selectedIndex-1][i],
									   deptId[campusInCharge.selectedIndex-1][schoolInCharge.selectedIndex-1][i]);
					deptInCharge.options.add(opt);
				}
			} else {			
				opt = new Option("", "");
				deptInCharge.options.add(opt);
			}
			opt = new Option("", "");
			classInCharge.options.add(opt);
		}
	};

	function fillClasses() {
		with(document.forms[0]) {
			classInCharge.options.length = 0;
			if (campusInCharge.selectedIndex > 0 && schoolInCharge.selectedIndex > 0 && deptInCharge.selectedIndex > 0) {
				opt = new Option("<bean:message key='SelClasses' />", "All");
				classInCharge.options.add(opt);
				for (i=0; i < classId[campusInCharge.selectedIndex-1][schoolInCharge.selectedIndex-1][deptInCharge.selectedIndex-1].length; i++) {
					opt = new Option(classLabel[campusInCharge.selectedIndex-1][schoolInCharge.selectedIndex-1][deptInCharge.selectedIndex-1][i],
									    classId[campusInCharge.selectedIndex-1][schoolInCharge.selectedIndex-1][deptInCharge.selectedIndex-1][i]);
					classInCharge.options.add(opt);
				}
			} else {
				opt = new Option("", "");
				classInCharge.options.add(opt);
			}			
		}
	};
	
	function restoreSelections(school, dept, clazz) {
		with(document.forms[0]) {
			//alert(campusInCharge.selectedIndex);
			if (campusInCharge.selectedIndex > 0) {
				opt = new Option("<bean:message key='SelSchools' />", "All");
				schoolInCharge.options.add(opt);
				for (i=0; i < schoolId[campusInCharge.selectedIndex-1].length; i++) {
					opt = new Option(schoolName[campusInCharge.selectedIndex-1][i],
									   schoolId[campusInCharge.selectedIndex-1][i]);
					schoolInCharge.options.add(opt);
					if (opt.value == school) {
						opt.selected = true;
					}
				}
				if (schoolInCharge.selectedIndex > 0) {
					opt = new Option("<bean:message key='SelDepts' />", "All");
					deptInCharge.options.add(opt);
					for (i=0; i < deptId[campusInCharge.selectedIndex-1][schoolInCharge.selectedIndex-1].length; i++) {
						opt = new Option(deptName[campusInCharge.selectedIndex-1][schoolInCharge.selectedIndex-1][i],
										   deptId[campusInCharge.selectedIndex-1][schoolInCharge.selectedIndex-1][i]);
						deptInCharge.options.add(opt);
						if (opt.value == dept) {
							opt.selected = true;
						}	
					}
					if (deptInCharge.selectedIndex > 0) {
						opt = new Option("<bean:message key='SelClasses' />", "All");
						classInCharge.options.add(opt);
						for (i=0; i < classId[campusInCharge.selectedIndex-1][schoolInCharge.selectedIndex-1][deptInCharge.selectedIndex-1].length; i++) {
							opt = new Option(classLabel[campusInCharge.selectedIndex-1][schoolInCharge.selectedIndex-1][deptInCharge.selectedIndex-1][i],
											    classId[campusInCharge.selectedIndex-1][schoolInCharge.selectedIndex-1][deptInCharge.selectedIndex-1][i]);
							classInCharge.options.add(opt);
							if (opt.value == clazz) {
								opt.selected = true;
							}
						}
					} 
				}
			}
		}	
	};
	
	function fillSchoolsForLiteracyClassSearch() {
		with(document.forms[0]) {
			schoolInCharge.options.length = 0;
			//deptInCharge.options.length = 0;
			//classInCharge.options.length = 0;
			if (campusInCharge.selectedIndex > 0) {
				opt = new Option("<bean:message key="SelSchools" />", "All");
				schoolInCharge.options.add(opt);
				for (i=0; i < schoolId[campusInCharge.selectedIndex-1].length; i++) {
					opt = new Option(schoolName[campusInCharge.selectedIndex-1][i],
									   schoolId[campusInCharge.selectedIndex-1][i]);
					schoolInCharge.options.add(opt);
				}
			} else {
				opt = new Option("", "");
				schoolInCharge.options.add(opt);
			}
			opt = new Option("", "");
			//deptInCharge.options.add(opt);
			opt = new Option("", "");
			//classInCharge.options.add(opt);			
		}
	};

function generateClassSelection(campusNo, schoolNo, deptNo, classNo) {
    document.write("<SELECT name=\"campusInCharge\" onchange=\"fillSchools();\">");
  	document.write("<OPTION value=\"All\"><bean:message key='AllCampuses' /></OPTION>");
	document.write("<c:if test='${not empty Credential}'>");
    document.write("  <c:forEach items='${Credential.campusInCharge}' var='campus'>");
	document.write("	<OPTION value=${campus.idno} <c:if test='${campus.idno==\"" + campusNo + "\"}'>selected</c:if>>${campus.name}</OPTION>");
	document.write("  </c:forEach>");
    document.write("</c:if></SELECT><SELECT name=\"schoolInCharge\" onchange=\"fillDepts();\">");
    document.write("</SELECT><SELECT name=\"deptInCharge\" onchange=\"fillClasses();\">");
    document.write("</SELECT><SELECT name=\"classInCharge\" />");
};
// -->
</script>