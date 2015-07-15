// <!-- <![CDATA[
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


var quotationEdit = {};
var autoOrigtext = "";	//記錄之前輸入的商品名稱


/**
** 建立自動完成的下拉突顯視窗,需要以下 HTML DOM
** 	<div id="popup" style="position:absolute;">
**		<table id="name_table" bgcolor="#FFFAFA" border="0" cellspacing="0" cellpadding="0">
**		 	<tbody id="name_body"></tbody>
**		</table>
**	</div> 
**
**/
function createSelItems(inputObj, field_name, value_field, jsonObj, func){
	clearNames();
	var size = jsonObj.length;
	var len = 0;
	for(var i=0; i<size; i++){
		vv = new String(jsonObj[i][field_name] + ";" + jsonObj[i][value_field]);
		if(vv.length > len){
			len = vv.length;
		}
		if(i>10) break;
	}
	setOffsets(inputObj, len);
	
	var nameTableBody = document.getElementById("name_body");
	//window.alert(size);
	var row, cell, txtNode, divNode;
	jArray = new Array(size);
	
	row = document.createElement("tr");
	cell = document.createElement("td");
	cell.onmouseout = function(){this.className='fullColorTrF';};
	cell.onmouseover = function(){this.className='fullColorTr';};
	cell.onclick = function(){clearNames();};
	cell.style.border.bottom = " 1px dotted #aaaaaa";
	cell.setAttribute("align", "right");
	cell.setAttribute("color", "red");
	imgNode = document.createElement("img");
	imgNode.src = basePath + "pages/images/icon/action_stop.gif";
	cell.appendChild(imgNode);
	txtNode = document.createTextNode("關閉");
	//window.alert("append close!");
	cell.appendChild(txtNode);
	row.appendChild(cell);
	nameTableBody.appendChild(row);
	
	var rcnt = 0;
	
	for(var i=0; i<size; i++){
		var json = JSON.stringify(jsonObj[i], null, null);
		//window.alert(json);
		var nextNode = jsonObj[i][field_name] + ";" + jsonObj[i][value_field];
		row = document.createElement("tr");
		cell = document.createElement("td");
		cell.onmouseout = function(){this.className='fullColorTrF';};
		cell.onmouseover = function(){this.className='fullColorTr';};
		//cell.setAttribute("bgcolor", "#FFFAFA");
		//cell.setAttribute("border", "1");
		//cell.setAttribute("width", "20");
		cell.setAttribute("id", jsonObj[i][value_field]);
		if(func){
			//cell.onclick = function(){populateName(inputObj, this); func(json);};
			cell.onclick = function(){populateName(inputObj, this); func(this);};
		}else{
			cell.onclick = function(){populateName(inputObj, this);};
		}
		
		txtNode = document.createTextNode(nextNode);
		cell.appendChild(txtNode);
		row.appendChild(cell);
		nameTableBody.appendChild(row);
		rcnt = rcnt + 1;
		if(rcnt > 10) break;
	}
}

function setOffsets(inputObj, len){
	var inputField = inputObj;
	var popupDiv = document.getElementById("popup");
	var nameTable = document.getElementById("name_table");
	
	var end = inputField.offsetWidth;
	var left = calculateOffsetLeft(inputField);
	var top = calculateOffsetTop(inputField) + inputField.offsetHeight;
	
	//popupDiv.style.border = "blue 1px solid";
	popupDiv.style.left = left + "px";
	popupDiv.style.top = top + "px";
	/*
	if(end >= len*12){
		nameTable.style.width = end + "px";
	}else{
		nameTable.style.width = len + "px";
	}
	*/
}

function calculateOffsetLeft(field){
	return calculateOffset(field, "offsetLeft");
}

function calculateOffsetTop(field){
	return calculateOffset(field, "offsetTop");
}

function calculateOffset(field, attr){
	var offset = 0;
	while(field){
		offset += field[attr];
		field = field.offsetParent;
	}
	return offset;
}

function populateName(inputObj, cell){
	//window.alert(cell.getAttribute("onclick"));
	str = new String(cell.firstChild.nodeValue);
	val = str.split(";", 2);
	inputObj.value = val[0];
	clearNames();
}

function clearNames(){
	var nameTableBody = document.getElementById("name_body");
	var popupDiv = document.getElementById("popup");
	var ind = nameTableBody.childNodes.length;
	if(ind >0){
		for(var i=ind-1; i>=0; i--){
			nameTableBody.removeChild(nameTableBody.childNodes[i]);
		}
		popupDiv.style.border = "none";
	}
}


function getCscodeName(mfObj, isSelItem){
	autoOrigtext = mfObj.value;
	var url = "/CIS/AjaxGlobal";
	var jsonQuery = '{"method":"getCourseByCode", "sname":"' + mfObj.value + '"}';
	if(mfObj.value.length > 0){
		orig_text = mfObj.value;
		var field_name = "sname";
		doJSONRequest(url, jsonQuery, setAutoInput_Name);
	}
	
}
/*
** 輸入欄位自動完成
** inputObj : 要自動完成的輸入欄位 
** field_name : 從 JSON Object 要取出的欄位名稱
** orig_text : 在 AJAX 回應前輸入欄位的值
** isSelItem : 要建立選單還是使用自動完成
** func : 後續要處理的功能
**
*/
function setAutoInput_Name(){
// parameters: inputObj, field_name, orig_text, isSelItem, func
	if(handleState()){
		var field_name = "cscode";
		var value_field = "chi_name";
		var orig_text = autoOrigtext;
		var isSelItem = true;
		var func = chgCourseName;
		var inputObj = document.getElementById("cscode");
		
		//window.alert("inputObj:" + inputObj.id);
		if(request.responseText){
			var jsonObj = JSON.parse(request.responseText);
			//window.alert(JSON.stringify(jsonObj, null, null));
			if(jsonObj){
				input = new String(inputObj.value);
				first = new String(jsonObj[0][field_name]);
				
				suggestion = new String(first.substring(input.length, first.length-1));
				var new_input = inputObj.value;
				//window.alert(orig_text + ":" + new_input);
				if(orig_text == new_input){
					//window.alert(JSON.stringify(jsonObj, null, null));
					if(!isSelItem){
						//for Firefox & Opera
						if(document.getSelection()){
							var initial_len = new_input.length;
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
					}else if(isSelItem){	//create select List
						createSelItems(inputObj, field_name, value_field, jsonObj, func);
					}else{
						clearNames();
					}
				}else{
				}
			}
			
		}else{
			clearNames();
		}
	}
}

function chgCourseName(cell){
	//window.alert("chgAgentId:" + cell.id);
	//var json = new JSON.parse(jsonObj2);
	var agentObj = document.getElementById("cscodeName");
	sid = new String(cell.id);
	agentObj.value = sid;
}


function getCscode(mfObj, isSelItem){
	autoOrigtext = mfObj.value;
	var url = "/CIS/AjaxGlobal";
	var jsonQuery = '{"method":"getCourseByName", "sname":"' + mfObj.value + '"}';
	if(mfObj.value.length > 0){
		orig_text = mfObj.value;
		var field_name = "sname";
		doJSONRequest(url, jsonQuery, setAutoInput_Cscode);
	}
	
}

/*
** 輸入欄位自動完成
** inputObj : 要自動完成的輸入欄位 
** field_name : 從 JSON Object 要取出的欄位名稱
** orig_text : 在 AJAX 回應前輸入欄位的值
** isSelItem : 要建立選單還是使用自動完成
** func : 後續要處理的功能
**
*/
function setAutoInput_Cscode(){
// parameters: inputObj, field_name, orig_text, isSelItem, func
	if(handleState()){
		var field_name = "chi_name";
		var value_field = "cscode";
		var orig_text = autoOrigtext;
		var isSelItem = true;
		var func = chgCourseCode;
		var inputObj = document.getElementById("cscodeName");
		
		//window.alert("inputObj:" + inputObj.id);
		if(request.responseText){
			var jsonObj = JSON.parse(request.responseText);
			//window.alert(JSON.stringify(jsonObj, null, null));
			if(jsonObj){
				input = new String(inputObj.value);
				first = new String(jsonObj[0][field_name]);
				
				suggestion = new String(first.substring(input.length, first.length-1));
				var new_input = inputObj.value;
				//window.alert(orig_text + ":" + new_input);
				if(orig_text == new_input){
					if(!isSelItem){
						//for Firefox & Opera
						if(document.getSelection()){
							var initial_len = new_input.length;
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
					}else if(isSelItem){	//create select List
						createSelItems(inputObj, field_name, value_field, jsonObj, func);
					}else{
						clearNames();
					}
				}else{
				}
			}
			
		}else{
			clearNames();
		}
	}
}
function chgCourseCode(cell){
	//window.alert("chgAgentId:" + cell.id);
	//var json = new JSON.parse(jsonObj2);
	var agentObj = document.getElementById("cscode");
	sid = new String(cell.id);
	agentObj.value = sid;
}



//更新代理人ID
/*
function chgAgentId(jsonObj2){
	window.alert("chgAgentId:" + jsonObj2);
	var json = new JSON.parse(jsonObj2);
	var agentObj = document.getElementById("agent");
	var idsObj = document.getElementById("ids");
	sid = new String(json.idno);
	agentObj.value = json.idno;
	idsObj.value = sid.substring(0, 5) + "#####";
}
*/

/**
 包含文件 用法：$import('../include/mian.js', 'js');
            $import('../style/style.css', 'css');
**/
function $import(path, type){
	var i, base, src = "common.js";
	var scripts = document.getElementsByTagName("script");

 	for (i = 0; i < scripts.length; i++) {
		if (scripts[i].src.match(src)) {
			base = scripts[i].src.replace(src, "");
          	break;
      	}
  	}
 
  	if (type == "css") {
      	document.write("<" + "link href=\"" + base + path + "\" rel=\"stylesheet\" type=\"text/css\"></" + "link>");
  	} else {
      	document.write("<" + "script src=\"" + base + path + "\"></" + "script>");
  	}
}

// ]]> -->
