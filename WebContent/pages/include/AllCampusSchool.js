<script language="javascript">
<!--
	var schoolName = ${AllClassesJsArray.schoolName};
	var schoolId   = ${AllClassesJsArray.schoolId};
	
	function fillSchools() {
		with(document.forms[0]) {
			schoolInCharge.options.length = 0;
			if (campusInCharge.selectedIndex > 0) {
				opt = new Option("<bean:message key="AllSchools" />", "All");
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
		}
	};
	
	function restoreSelections(school) {
		with(document.forms[0]) {
			//alert(campusInCharge.selectedIndex);
			if (campusInCharge.selectedIndex > 0) {
				opt = new Option("<bean:message key='AllSchools' />", "All");
				schoolInCharge.options.add(opt);
				for (i=0; i < schoolId[campusInCharge.selectedIndex-1].length; i++) {
					opt = new Option(schoolName[campusInCharge.selectedIndex-1][i],
									   schoolId[campusInCharge.selectedIndex-1][i]);
					schoolInCharge.options.add(opt);
					if (opt.value == school) {
						opt.selected = true;
					}
				}
			}
		}	
	};
	
// -->
</script>