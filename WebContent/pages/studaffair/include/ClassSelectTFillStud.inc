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
		clearOptions("studentNo");			
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
		clearOptions("studentNo");			
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
		clearOptions("studentNo");			
	};
	
	var orig_clazz = '';
	function fillStudents(){
		clearOptions("studentNo");			
		var clazzObj = document.getElementById("classInChargeT");
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
		var newClazzValue = document.getElementById("classInChargeT").value;
		if(handleState()){
			if(request.responseText && newClazzValue == orig_clazz){
				var jsonObj = JSON.parse(request.responseText);
				if(jsonObj != null) {
					createOptions("studentNo", jsonObj, "studentNo", "studentNameNo", "");
				}
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
    </c:if></SELECT>
    <SELECT name="schoolInChargeT" onchange="fillDepts();"></SELECT>
    <SELECT name="deptInChargeT" onchange="fillClasses();"></SELECT>
    <SELECT name="classInChargeT" id="classInChargeT" onchange="fillStudents();"></SELECT>
    &nbsp;&nbsp;學生：<SELECT name="studentNo" id="studentNo"></SELECT>	   		    
    <script>restoreSelections('${schoolSel}', '${deptSel}', '${classSel}');</script>			    
