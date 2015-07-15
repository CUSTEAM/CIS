<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<script language="javascript">
<!--
	var READ_STATE_UNINITIALIZED = 0;
	var READ_STATE_LOADING = 1;
	var READ_STATE_LOADED = 2;
	var READ_STATE_INTERACTIVE = 3;
	var READ_STATE_COMPLETE = 4;
	
	var RESPONSE_STATUS_OK = 200;
	var haveSubCategory = new Boolean(true);
	var request = null;
	
	function httpRequest(method, url, asynch, resHandle) {
		if(window.XMLHttpRequest) {
			request = new XMLHttpRequest();
		} else if(window.ActiveXObject) {
			request = new ActiveXObject("Msxml2.XMLHTTP");
			if(!request) {
				request = new ActiveXObject("Microsoft.XMLHTTP");
			}
		}
		if(request) {
			if(method.toLowerCase() != "post") {
				initReq(method, url, asynch, resHandle);
			} else {
				var args = arguments[4];
				if(args !== null && args.length > 0) {
					initReq(method, url, asynch, resHandle, args);
				}
			}
		} else {
			window.alert("Error to retrived Request Object!!!");
		}
	}
	
	function initReq(method, url, asynch, resProcess) {
		try {
			request.onreadystatechange = resProcess;
			request.open(method, url, asynch);
			request.setRequestHeader("Cache-Control", "no-cache");
			request.setRequestHeader("Pragma", "no-cache");
			// IE will CACHED request, so should be appended following code
			// (Firefox not)
			request.setRequestHeader("If-Modified-Since", "0");
			if(method.toLowerCase() == "post") {
				request.setRequestHeader("Content-type", "application/x-www-form-urlencoded;");
				request.send(arguments[4]);
			} else {
				request.send(null);
			}
		} catch(err) {
			window.alert(err.message);
		}
	}
	
	function handleState() {
		if(request.readyState == READ_STATE_COMPLETE){
			if(request.status == RESPONSE_STATUS_OK){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
		return false;
	}
	
	function doJSONRequest(url, jsonQuery, responfunc) {
		//alert("doJsonRequest");
		if(window.XMLHttpRequest) {
			request = new XMLHttpRequest();
		} else if(window.ActiveXObject) {
			request = new ActiveXObject("Msxml2.XMLHTTP");
			if(!request) {
				request = new ActiveXObject("Microsoft.XMLHTTP");
			}
		}
		if(request) {
		
			initJSONReq(url, jsonQuery, responfunc);
		} else {
			window.alert("Error to retrived Request Object!!!");
		}
	}
	
	function initJSONReq(url, jsonQuery, func) {
		try {
			request.onreadystatechange = func;
			url = url + "?sessionNumber=" + new Date().getTime() + Math.floor(Math.random()*999);
			//window.alert(url);
			request.open("post", url, true);
			request.setRequestHeader("Cache-Control", "no-cache");
			request.setRequestHeader("Pragma", "no-cache");
			// IE will CACHED request, so should be appended following code
			// (Firefox not)
			request.setRequestHeader("If-Modified-Since", "0");
			request.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
			request.send(jsonQuery);
			//window.alert("jsonQuery have sent!");
		} catch(err) {
			window.alert(err.message);
		}
	}
	
	function getJson(filterFun){
		if(filterFun){
			jsonObj = JSON.parse(request.responseText,filterFun);
		}else{
			jsonObj = JSON.parse(request.responseText);
		}
	}
	
	/*
		建立Select 的 Option 項目
		objId : Select Object id
		jsonObj = [{"text":apple, "value":1}, {},...]
		value_field : 選項的值, 在jsonObj 中所取用的欄位
		label_field : 選項的標題, 如果有設定則選用 jsonObj 中的欄位, 若無則以選項的值為標題
		selOption : 預設的選項值
	*/
	function createOptions(objId, jsonObj, value_field, label_field, selOption){
		var option = null;
		var selObj = document.getElementById(objId);
		clearOptions(objId);
		//window.alert("option json length:" + jsonObj.length + ", value_field:" + value_field+ ", label_field:" + label_field + ", selOption:" + selOption);
		option = document.createElement("option");
		option.text = "";
		option.value = "";
		selObj.add(option);
		for(var i=0; i<jsonObj.length; i++){
			option = document.createElement("option");
			//window.alert("jsonObj[" + i + "][" + value_field + "]=" + jsonObj[i][value_field]);
			if(jsonObj[i][value_field] != null){
				if(jsonObj[i][label_field] != null){
					option.text = jsonObj[i][label_field];
					option.value = jsonObj[i][value_field];
				}else{
					option.text = jsonObj[i][value_field];
					option.value = jsonObj[i][value_field];
				}
				selObj.add(option);
			}
			if(jsonObj[i][value_field] != null){
				if(jsonObj[i][label_field] != null){
					if(jsonObj[i][value_field] == selOption){
						option.selected = true;
					}else{
						option.selected = false;
					}
				}else{
					if(jsonObj[i][value_field] == selOption){
						option.selected = true;
					}else{
						option.selected = false;
					}
				}
			}
		}
	}
	
	/*
	**	清除選單
	**	objId : Select 選單的 id
	*/
	function clearOptions(objId){
		var selObj = document.getElementById(objId);
		if(selObj != null){
			//window.alert("clear options:" + selObj.length);
			while(selObj.length > 0){
				selObj.remove(0);
			}
		}
	}
	
	function setAutoComplete(inputId, url, jsonQuery){
		var input = document.getElementById(inputId).value;
		
		var func = function(){
			if(request.responseText){
				var jsonObj = JSON.parse(request.responseText, null);
				var inputObj = document.getElementById(inputId);
				var first = jsonObj[0].name;
				var suggestion = first.subString(input.length, first.length-1);
				var new_input = document.getElementById(inputId).value;
				if(input == new_input){
					//for Firefox & Opera
					if(document.getSelection()){
						var initial_len = input.length;
						inputObj.value += suggestion;
						inputObj.selectionStart = initial_len;
						inputObj.selectionEnd = inputObj.value.length;
					}else if(document.selection){
						//for IE
						var sel = document.selection.createRange();
						sel.text = suggestion;
						sel.move("character", -suggestion.length);
						sel.findText(suggestion);
						sel.select();
					}
				}
				//create select List
			
			}
			
		}
		
		if(input.length > 0){
			doJSONRequest(url, jsonQuery, func);
		}
	}

	var schoolName = ${AllClassesJsArray.schoolName};
	var schoolId   = ${AllClassesJsArray.schoolId};
	var deptName   = ${AllClassesJsArray.deptName};
	var deptId     = ${AllClassesJsArray.deptId};
	var classLabel = ${AllClassesJsArray.className};
	var classId    = ${AllClassesJsArray.classId};

	function fillSchools() {
		with(document.forms[0]) {
			schoolInCharge.options.length = 0;
			deptInCharge.options.length = 0;
			classInCharge.options.length = 0;
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
			opt = new Option("", "");
			deptInCharge.options.add(opt);
			opt = new Option("", "");
			classInCharge.options.add(opt);			
		}
		clearOptions("studentNo");			
	};
	
	function fillDepts() {
		with(document.forms[0]) {
			deptInCharge.options.length = 0;
			classInCharge.options.length = 0;
			if (campusInCharge.selectedIndex > 0 && schoolInCharge.selectedIndex > 0) {
				opt = new Option("<bean:message key='AllDepts' />", "All");
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
		clearOptions("studentNo");			
	};

	function fillClasses() {
		with(document.forms[0]) {
			classInCharge.options.length = 0;
			if (campusInCharge.selectedIndex > 0 && schoolInCharge.selectedIndex > 0 && deptInCharge.selectedIndex > 0) {
				opt = new Option("<bean:message key='AllClasses' />", "All");
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
		clearOptions("studentNo");			
	};
	
	function restoreSelections(school, dept, clazz) {
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
				if (schoolInCharge.selectedIndex > 0) {
					opt = new Option("<bean:message key='AllDepts' />", "All");
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
						opt = new Option("<bean:message key='AllClasses' />", "All");
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
	


	var orig_clazz = '';
	function fillStudents(){
		clearOptions("studentNo");			
		var clazzObj = document.getElementById("classInCharge");
		var clazz = clazzObj.value;
		orig_clazz = clazz;
		if(clazz != ""){
	 		var mypath = location.pathname;
	 		var urlprefix=mypath.substring(0,mypath.indexOf("/", 1));
			var url = urlprefix + "/AjaxGlobal";
			var jsonQuery = '{"method":"getStudents", "clazz":"' + clazz + '"}';
			//window.alert(jsonQuery);
			doJSONRequest(url, jsonQuery, fillStudSelect);
			
			
		}
	}

	function fillStudSelect() {
		var newClazzValue = document.getElementById("classInCharge").value;
		if(handleState()){
			if(request.responseText && newClazzValue == orig_clazz){
				var jsonObj = JSON.parse(request.responseText);
				if(jsonObj != null) {
					createOptions("studentNo", jsonObj, "studentNo", "studentNameNo", "");
				}
			}
		}
	};
	







// -->
</script>
  <SELECT name="campusInCharge" onchange="fillSchools();">
  	<OPTION value="All"><bean:message key="AllCampuses" /></OPTION>
	<c:if test="${not empty Credential}">
      <c:forEach items="${Credential.campusInCharge}" var="campus">
		<OPTION value="${campus.idno}" <c:if test="${campus.idno==campusSel}">selected</c:if>>${campus.name}</OPTION>
	  </c:forEach>
    </c:if></SELECT>
    <SELECT name="schoolInCharge" onchange="fillDepts();"></SELECT>
    <SELECT name="deptInCharge" onchange="fillClasses();"></SELECT>
    <SELECT name="classInCharge" id="classInCharge" onchange="fillStudents();"></SELECT>
    &nbsp;&nbsp;學生：<SELECT name="studentNo" id="studentNo"></SELECT>	   		    
    <script>restoreSelections('${schoolSel}', '${deptSel}', '${classSel}');</script>			    
<!-- 
	//include bellow lines in your file
	<c:set var="campusSel" value="${StudCounselingNewOtherL.campusInCharge}"/>
	<c:set var="schoolSel" value="${StudCounselingNewOtherL.schoolInCharge}"/>
	<c:set var="deptSel"   value="${StudCounselingNewOtherL.deptInCharge}"/>
	<c:set var="classSel"  value="${StudCounselingNewOtherL.classInCharge}"/>
	<%@ include file="/pages/studaffair/include/ClassSelectTFillStudL.inc" %>
 -->
 