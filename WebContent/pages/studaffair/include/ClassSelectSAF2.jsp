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
	
	function fillcourse() {
		with(document.forms[0]) {
			if(classInChargeSAF.value != "") {
				document.forms[0].submit();
			}
		}		
	};
	
// -->
</script>
  <c:if test="${tcnt == null}">
  	<c:set var="tcnt" value="1"/>
  </c:if>
  <SELECT name="campusInChargeSAF" tabindex="${tcnt}" onchange="fillSchools();">
  	<OPTION value="All"><bean:message key="AllCampuses" /></OPTION>
	<c:if test="${not empty Credential}">
      <c:forEach items="${Credential.campusInChargeSAF}" var="campus">
		<OPTION value="${campus.idno}" <c:if test="${campus.idno==campusSel}">selected</c:if>>${campus.name}</OPTION>
	  </c:forEach>
    </c:if></SELECT>
    <c:set var="tcnt" value="${tcnt + 1}"/>
    <SELECT name="schoolInChargeSAF" tabindex="${tcnt}" onchange="fillDepts();">
    </SELECT>
    <c:set var="tcnt" value="${tcnt + 1}"/>
    <SELECT name="deptInChargeSAF" tabindex="${tcnt}" onchange="fillClasses();">
    </SELECT>
    <c:set var="tcnt" value="${tcnt + 1}"/>
    <SELECT name="classInChargeSAF" tabindex="${tcnt}" onchange="fillcourse();"></SELECT>
    <c:set var="tcnt" value="${tcnt + 1}"/>	   		    
    <script>restoreSelections('${schoolSel}', '${deptSel}', '${classSel}');</script>			    
