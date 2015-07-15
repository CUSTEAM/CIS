<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<script language="javascript">
var schoolName=${Credential.javaScriptArray.schoolName};
var schoolId=${Credential.javaScriptArray.schoolId};
var deptName=${Credential.javaScriptArray.deptName};
var deptId=${Credential.javaScriptArray.deptId};
var classLabel=${Credential.javaScriptArray.className};
var classId=${Credential.javaScriptArray.classId};

function fillSchools() {
	with(document.forms[0]) {
		schoolInCharge2.options.length = 0;
		deptInCharge2.options.length = 0;
		classInCharge2.options.length = 0;
		if (campusInCharge2.selectedIndex > 0) {
			opt = new Option("<bean:message key="AllSchools" />", "");
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
}

function fillDepts(){
	with(document.forms[0]) {
		deptInCharge2.options.length = 0;
		classInCharge2.options.length = 0;
		if (campusInCharge2.selectedIndex > 0 && schoolInCharge2.selectedIndex > 0) {
			opt = new Option("<bean:message key='AllDepts' />", "");
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
}

function fillClasses(){
	with(document.forms[0]) {
		classInCharge2.options.length = 0;
		if (campusInCharge2.selectedIndex > 0 && schoolInCharge2.selectedIndex > 0 && deptInCharge2.selectedIndex > 0) {
			opt = new Option("<bean:message key='AllClasses' />", "");
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
}

function restoreSelections(school, dept, clazz){
	with(document.forms[0]) {
		//alert(campusInCharge.selectedIndex);
		if (campusInCharge2.selectedIndex > 0) {
			opt = new Option("<bean:message key='AllSchools' />", "");
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
				opt = new Option("<bean:message key='AllDepts' />", "");
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
					opt = new Option("<bean:message key='AllClasses' />", "");
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
}
</script>



<select style="font-size:18px;" name="campusInCharge2" id="campusInCharge2" onchange="fillSchools();">
  	<option value=""><bean:message key="AllCampuses" /></option>
	<c:if test="${not empty Credential}">
      <c:forEach items="${Credential.campusInCharge}" var="campus">
		<OPTION value="${campus.idno}" <c:if test="${campus.idno==campusSel}">selected</c:if>>${campus.name}</option>
	  </c:forEach>
    </c:if>
</select>
<select style="font-size:18px;" name="schoolInCharge2" id="schoolInCharge2" onchange="fillDepts();"></select>    
<select style="font-size:18px;" name="deptInCharge2" id="deptInCharge2" onchange="fillClasses();"></select>    
<select name="classInCharge2" id="classInCharge2" style="display:none"></select>
<input type="hidden" name="classLess" id="classLess" value="${classLess}"/>
   
   

   
    
    
<script>
restoreSelections('${schoolSel}', '${deptSel}', '${classSel}');
</script>