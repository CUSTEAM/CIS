<script language="javascript">
<!--	
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
			//alert(campusInCharge2.selectedIndex);
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