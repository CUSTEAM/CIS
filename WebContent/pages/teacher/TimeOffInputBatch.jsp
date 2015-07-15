<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<%@ include file="/pages/include/ajax.js" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript" src="<%=basePath%>pages/include/json2.js"></script>

<style type="text/css">
<!--
.style1 {
	font-size: 24px;
	font-weight: bold;
}
.div1 {
	font-size: 13px;
	font-weight: normal;
	height: auto;
	width: auto;
	border: 1px solid #FF6600;
	margin: 3px;
}

.grid1 {
	font-size: 13px;
	font-weight: normal;
	height: auto;
	border: 1px solid #FF6600;
	margin: 3px;
	background-color: #FFFFCC;
}

.scoreprn {
	background-color: #cfe69f;
	border: 1px solid #cfe69f;
	position: absolute;
	margin-top: -25%;
	margin-left: -25%;
	margin: auto;
	z-index: 32767;
}
.caution{
	font-size: 90%;
	color: red;
	background-color: #f3ffc7;
	margin: 12px;
	text-align: left;
}

.timeoffButton {
	background-color: #F0E0FE;
	font: normal 8pt;
	border: 1px outset gray;
	margin: auto;
	vertical-align: middle;
	line-height: normal;
}
-->
</style>
<style type="text/css">
<!--
.style2 {
	font-size: 15px;
}
-->
</style>

<script type="text/javascript">
<!--
noMenu = false;
openMenu();
function resetTimeOut(){
	if(myTimeOut != null) {
		globalTimeOut = myTimeOut;
		myTimeOut = null;
	}
}

resetTimeOut;
//-->
</script>
<form action="/CIS/Teacher/TimeOffInput.do" method="post" name="inForm" id="inForm" onSubmit="init('資料傳送中.....');">
<input type="hidden" name="openClass" value="${TimeOffInputInfo.openClass}">
<input type="hidden" name="mode" value="${TimeOffInputInfo.mode}">

<!-- Begin Content Page Table Header -->
<table width="100%" cellpadding="0" cellspacing="0">
<c:if test="${TimeOffInputInfo.mode=='onLine'}">
<script>generateTableBanner('教師上課點名系統 :: 線上點名');</script>
</c:if>	  
<c:if test="${TimeOffInputInfo.mode=='normal'}">
<script>generateTableBanner('教師上課點名系統 :: 點名輸入');</script>
</c:if>	  
<c:if test="${TimeOffInputInfo.mode=='redirect'}">
<script>generateTableBanner('教師上課點名系統 :: 補點名');</script>
</c:if>	  
<c:if test="${TimeOffInputInfo.mode=='redirect'}">
	<input type="hidden" name="dtOid" id="dtOid" value="${TimeOffInputInfo.dtOid}">
	<input type="hidden" name="tdate" id="tdate" value="${TimeOffInputInfo.tdate}">
</c:if>
<c:if test="${TimeOffInputInfo.mode!='redirect'}">
	<tr>
		<td width="100%" align="center" valign="top" class="decorate">
<!-- End Content Page Table Header -->

		<table width="100%" border="0" cellpadding="5" cellspacing="1">
      <!--DWLayoutTable-->
      <!-- c:choose -->
      <!-- c:when test="${(TimeOffInEditB == null  && TimeOffFormMapB == null) && TeachClasses != null}" -->
			<tr>
	        <td  height="35" colspan="2" align="left" valign="middle" bgcolor="#CCCCFF">
	       	<font color="blue">${TimeOffInputInfo.teacherName}&nbsp;老師</font>
	　　			請選擇要輸入的班級及日期：&nbsp;&nbsp;
	        </td>
		  	</tr>
		 	<tr><td colspan="2">
		  		<Select name="dtOid" id="dtOid" onChange="setTeachDateOption();">
		  		<option value="">請選擇授課班級科目</option>
		  		<c:forEach items="${TeachClasses}" var="clazz">
		  		<c:if test="${clazz.dtimeOid==TimeOffInputInfo.dtOid}">
		  			<option value="${clazz.dtimeOid}" selected>
		  			<font color=red>${clazz.className2}(${clazz.dtimeOid})</font><font color="blue>"> ${clazz.chiName2}</font>
		  			</option>
		  		</c:if>
		  		<c:if test="${clazz.dtimeOid!=TimeOffInputInfo.dtOid}">
		  			<option value="${clazz.dtimeOid}">
		  			<font color=red>${clazz.className2}(${clazz.dtimeOid})</font><font color="blue>"> ${clazz.chiName2}</font>
		  			</option>
		  		</c:if>
		  		</c:forEach>
		  		</Select>
		  		<Select name="tdate" id="tdate" onChange="getClassBook()"; style="display: inline;">
		  		<option value=""></option>
		  		</Select>
		  	</td></tr>
		  	<tr><td align=left>
		  	<font color=red>老師請注意:點名輸入時間為８天(包含例假日)</font>
		  	</td>
		  	<td align=right>
		  	<span style="font-size:90%;*font-size:85%;float:right;display:inline;margin:3px 5px 0;" onClick="swHelp();">
			<img src="images/info.png" width="20" height="20" align="middle">
			<font color=blue style="font-family:標楷體 新細明體;font-size:12px;">使用說明</font>
			</span>
		  	</td></tr>
		  	<tr><td colspan="2" id="helptd" style="border-top: 1px #ff7340 solid;
overflow:scroll;border-bottom: 3px #ff7340 outset;
border-left: 1px #0746ff solid;
border-right: 3px #0746ff outset;">
<div id="ophelp" style="background: #fffff0;
font-size: 12px;
text-decoration: none;
line-height: 180%;
display: none;">
1.登入後由左方操作選單中,選取『線上點名』或『點名輸入』進入點名畫面<br>
(1)	<font color=red>線上點名</font>：系統會自動判斷<font color=red>今天</font>是否有授課？最接近的一節課為何？
如果系統顯示的並非老師要輸入的課程，老師可由上方課程選單，選取要
輸入的課程，如選單內無課程可供選取則表示今天沒有授課。<br>
(2)	<font color=red>點名輸入</font>：會顯示<font color=red>八日內</font>應點名之課程及時間選項，提供老師點名畫面
資訊，老師可由課程選單及時間選項，選取要輸入的課程。<br>
<font color=red>※	導師應點名課程包含班會及系時間，一科目多教師之課程（例如專題製 作…）每位老師均應點名。</font><br>
<br>
2.	在點名畫面中會顯示所有上課學生及上課節次，每位學生對應每一節課均有
一個按鈕可供點選缺曠狀態，如果該生在該節課已請假，則只會顯示請假
狀態，而不會有按鈕。
<br><br>
3.	在點名畫面中每一個按鈕均有三種狀態循環顯示，如下圖所示<br>
<img src="images/tfInput.jpg"><br>
，初始狀態為<font color=blue>「空白」</font>：<font color=red>無曠缺</font>，按一下轉換為<font color=blue>「曠課」</font>，再按一下轉換為<font color=blue>「遲到/早退」</font>，
再按一下還原為「空白」無曠缺的狀態
 <br><br>
4.	<font color=red>完成點名後請記得點選畫面最下方的『開始上傳』</font>，當畫面顯示『新增成功』
，即表示您已完成點名動作
<br><br>
5.	點名完畢後可由左方操作選單中選取『點名記錄查詢』，顯示8日內未點名
課程記錄，如有未點名課程，可直接點選<font color=red>「科目名稱」</font>進入點名畫面。
<br><br>
6.	列印點名單，可由左方操作選單點選<font color=red>『點名單列印』</font>，進入後選取要列印的
科目，當點名單畫面顯示後可點選最下方之「列印」按鈕列印點名單，或點選
「取消」按鈕回到點名單列印之科目選取畫面；每位教師應於開學前及開學
第三週（加退選完成）列印點名單，做為上課點名的紙本依據。
<br><br>
7.	教師任教的科目如果沒有學生曠缺，也請教師進入系統，按「開始上傳」
按鈕完成點名動作，才不會被誤認為沒有點名。<br>
				
				</div>
		  	
		  	</td></tr>
 	    </table>
		</td>
	</tr>
</c:if>
      <!-- /c:when -->
 	<!-- /c:choose -->
      
    <tr><td id="classBookBody" style="display:none;">
	</td></tr>
<!-- Begin Content Page Table Footer -->
				
</table>
<!-- End Content Page Table Footer -->
</form>

<html:messages id="msg" message="true">
<script>window.alert("${msg}");</script>
</html:messages>

<script>
<c:if test="${TeachClasses != null}">
var tdateArray =  ${TimeOffPeriod.tdate};
var tweekArray =  ${TimeOffPeriod.tweek};
</c:if>
var opmode = "${TimeOffInputInfo.mode}";

function swHelp(){
	var helpObj = document.getElementById("ophelp");
	var tdObj = document.getElementById("helptd");
	if(helpObj.style.display=='none'){
		tdObj.style.background="#fffff0";
		helpObj.style.display='inline';
	}else if(helpObj.style.display=='inline'){
		tdObj.style.background="";
		helpObj.style.display='none';
	}
}

function setTeachDateOption(){
	with(document.forms[0]){
		tdate.options.length = 0;
		if (dtOid.selectedIndex > 0) {
			opt = new Option("請選擇", "");
			tdate.options.add(opt);
			for (i=0; i < tdateArray[dtOid.selectedIndex-1].length; i++) {
				opt = new Option(tdateArray[dtOid.selectedIndex-1][i] + tweekArray[dtOid.selectedIndex-1][i],
								tdateArray[dtOid.selectedIndex-1][i]);
				// opt.onChange = "getClassBook()";
				tdate.options.add(opt);
			}
		} else {
			opt = new Option("", "");
			tdate.options.add(opt);
		}
		tdate.style.display = "inline";
		document.getElementById("classBookBody").style.display = "none";
		if(opmode == 'onLine'){
			getTodayClassBook();
		}
		//document.getElementById("button1").style.display = "none";
		
	}
}

var orig_dtOid = '';
var orig_tdate = '';
function getClassBook(){
	var oidObj = document.getElementById("dtOid");
	var tdateObj = document.getElementById("tdate");
	var oid = oidObj.value;
	var selDate = tdateObj.value;
	var cbBody = document.getElementById("classBookBody");
	orig_dtOid = oid;
	orig_tdate = selDate;
	if(oid != "" && selDate != ""){
 		var mypath = location.pathname;
 		var urlprefix=mypath.substring(0,mypath.indexOf("/", 1));
		var url = urlprefix + "/AjaxGlobal";
		var jsonQuery = '{"method":"getTchTimeOffInputData", "dtOid":"' + oid + '","tdate":"' + selDate + '"}';
		//window.alert(jsonQuery);
		doJSONRequest(url, jsonQuery, fillClassBook);
		
		
		var inHTML = "<table cellpadding=10><tr><td>";
		inHTML += "<img src=\"/CIS/pages/images/ajax-loader2.gif\" align=\"middle\">";
		inHTML += "&nbsp;&nbsp;<font color=\"red\">資料擷取中.....</font></td></tr></table>";
		cbBody.innerHTML = inHTML;
		
		/*
		var row, cell, txtNode, img, font;
		row =document.createElement("tr");
		cell = document.createElement("td");
		img = document.createElement("img");
		img.setAttribute("src", "/CIS/pages/images/ajax-loader2.gif");
		img.setAttribute("align", "middle");
		cell.appendChild(img);
		font = document.createElement("font");
		font.setAttribute("color", "red");
		txtNode = document.createTextNode("資料擷取中.....");
		font.appendChild(txtNode);
		cell.appendChild(font);
		
		row.appendChild(cell);
		cbBody.appendChild(row);
		*/
		
		cbBody.style.display = "inline";
		oidObj.disabled = true;
		tdateObj.disabled = true;
	}else{
		//cbBody.innerHTML = "";
		cbBody.style.display = "none";
	}
}

function clears(){
	var nameTableBody = document.getElementById("classBookBody");
	var ind = nameTableBody.childNodes.length;
	if(ind >0){
		for(var i=ind-1; i>=0; i--){
			nameTableBody.removeChild(nameTableBody.childNodes[i]);
		}
	}
	
	var iform = document.getElementById("inForm");
	var childName = "";
	window.alert("form elements:" + iform.childNodes.length);
	ind = iform.childNodes.length
	for(var k=ind-1; k>=0; k--){
		childName = iform.childNodes[k].name;
		window.alert("child elements:" + childName);
		if(childName == 'studentNo' || childName == 'st0' || childName == 'st1'
		 || childName == 'st2' || childName == 'st3' || childName == 'st4' || childName == 'st5'
		  || childName == 'st6' || childName == 'st7' || childName == 'st8' || childName == 'st9'
		   || childName == 'st10' || childName == 'st11' || childName == 'st12' || childName == 'st13'
		    || childName == 'st14' || childName == 'st15'){
			iform.removeChild(iform.childNodes[k]);
		}
	}
	window.alert("form elements:" + iform.childNodes.length);
}



function fillClassBook_DOM(){
	if(handleState()){
		if(request.responseText){
			//window.alert(request.responseText);
			var cbBody = document.getElementById("classBookBody");
			var button = document.getElementById("button1");
			var oidObj = document.getElementById("dtOid");
			var tdateObj = document.getElementById("tdate");
			var oid = oidObj.value;
			var selDate = tdateObj.value;
			if(oid == orig_dtOid && selDate == orig_tdate){
				clears();
				var jsonObj = JSON.parse(request.responseText);
				//window.alert("resetCategory:" + jsonObj);
				//var size = jsonObj.length;
				
				var init = jsonObj[0];
				var clazz = init.clazz;
				var depClassName = init.depClassName;
				var cscode = init.cscode;
				var cscodeName = init.cscodeName;
				var tfYear = init.tfYear;
				var tfMonth = init.tfMonth;
				var tfDay = init.tfDay;
				var daynite = init.daynite;
				//window.alert(JSON.stringify(jsonObj[1]));
				var TimeOffSubjInUse = JSON.parse(JSON.stringify(jsonObj[1]));
				var classBook = JSON.parse(JSON.stringify(jsonObj[2]));
				status = new Array(16);
				var stno = "";
				var typ = 0;
				var cols = TimeOffSubjInUse.length + 1;
				tfType = new Array("曠課", "病假", "事假", "遲到", "公假", "喪假", "婚假", "產假");
				
				var row, cell, gtable, grid, grow, gcell, txtNode, img, font, inHTML, tinput;
				var hwidth = 220;
				var bwidth = 50;
				row =document.createElement("tr");
				cell = document.createElement("td");
				font = document.createElement("font");
				font.className="blue_13";
				inHTML = "班級：" + clazz + "  " + depClassName + "  ";
				inHTML += "科目：" + cscode + "  " + cscodeName;
				txtNode = document.createTextNode(inHTML);
				font.appendChild(txtNode);
				cell.appendChild(font);
				row.appendChild(cell);
				cbBody.appendChild(row);
				
				row =document.createElement("tr");
				cell = document.createElement("td");
				font = document.createElement("font");
				//font.className="blue_13";
				inHTML = "日期：" + tfYear + " 年  " + tfMonth + " 月 " + tfDay + " 日";
				txtNode = document.createTextNode(inHTML);
				font.appendChild(txtNode);
				cell.appendChild(font);
				row.appendChild(cell);
				cbBody.appendChild(row);

				row =document.createElement("tr");
				cell = document.createElement("td");
				gtable = document.createElement("table");
				gtable.setAttribute("width", (hwidth+(cols-1)*bwidth));
				gtable.className="grid1";
				grid = document.createElement("tbody");
				gtable.appendChild(grid);
				grow =document.createElement("tr");
				gcell = document.createElement("td");
				gcell.setAttribute("width", hwidth);
				gcell.setAttribute("align", "center");
				gcell.setAttribute("valign", "middle");
				txtNode = document.createTextNode("節次");
				gcell.appendChild(txtNode);
				grow.appendChild(gcell);
				
				for(var k=0; k<TimeOffSubjInUse.length; k++){
					for(var p=TimeOffSubjInUse[k].begin; p<=TimeOffSubjInUse[k].end; p++){
						gcell = document.createElement("td");
						gcell.setAttribute("width", bwidth);
						gcell.setAttribute("align", "center");
						gcell.setAttribute("valign", "middle");
					
						if(daynite == '2' && p > 10){
							if(p==11) inHTML = "N1";
							else if(p==12) inHTML = "N2";
							else if(p==13) inHTML = "N3";
							else if(p==14) inHTML = "N4";
							else if(p==15) inHTML = "N5";
							else  inHTML = p;
						}else{
							inHTML = p;
						}
						txtNode = document.createTextNode(inHTML);
						gcell.appendChild(txtNode);
						grow.appendChild(gcell);
					}
				}
				gcell = document.createElement("td");
				grow.appendChild(gcell);
				grid.appendChild(grow);
				
				//window.alert(classBook[0].status1);
				for(var i = 0; i<classBook.length; i++){
					tinput = document.createElement("input");
					tinput.setAttribute("type", "hidden");
					tinput.setAttribute("name", "studentNo");
					tinput.setAttribute("value", classBook[i].studentNo);
					document.forms[0].appendChild(tinput);
					
					grow =document.createElement("tr");
					gcell = document.createElement("td");
					gcell.setAttribute("width", hwidth);
					gcell.setAttribute("align", "left");
					gcell.setAttribute("valign", "middle");
					txtNode = document.createTextNode(classBook[i].studentNo + " " + classBook[i].studentName);
					gcell.appendChild(txtNode);
					grow.appendChild(gcell);
					
					stno = classBook[i].studentNo;
					var evs = "";
					//var status = classBook[i].status;
					for(var j = 0; j <= 15; j++){
						switch(j){
						case 0: 
							evs = classBook[i].status0;
							break;
						case 1: 
							evs = classBook[i].status1;
							break;
						case 2: 
							evs = classBook[i].status2;
							break;
						case 3: 
							evs = classBook[i].status3;
							break;
						case 4: 
							evs = classBook[i].status4;
							break;
						case 5: 
							evs = classBook[i].status5;
							break;
						case 6: 
							evs = classBook[i].status6;
							break;
						case 7: 
							evs = classBook[i].status7;
							break;
						case 8: 
							evs = classBook[i].status8;
							break;
						case 9: 
							evs = classBook[i].status9;
							break;
						case 10: 
							evs = classBook[i].status10;
							break;
						case 11: 
							evs = classBook[i].status11;
							break;
						case 12: 
							evs = classBook[i].status12;
							break;
						case 13: 
							evs = classBook[i].status13;
							break;
						case 14: 
							evs = classBook[i].status14;
							break;
						case 15: 
							evs = classBook[i].status15;
							break;
						default:
							evs = "x";
						}
					
					
						switch(evs){
						case 'x':
							tinput = document.createElement("input");
							tinput.setAttribute("type", "hidden");
							tinput.setAttribute("name", "st" + j);
							tinput.setAttribute("value", evs);
							document.forms[0].appendChild(tinput);
							break;
						case '2':
						case '5':
							typ = eval(evs) - 2;
							gcell = document.createElement("td");
							gcell.setAttribute("width", bwidth);
							gcell.setAttribute("align", "center");
							gcell.setAttribute("valign", "middle");
							tinput = document.createElement("input");
							tinput.setAttribute("type", "button");
							tinput.setAttribute("id", stno + "_" + j);
							tinput.setAttribute("value", tfType[typ]);
							tinput.setAttribute("onClick", "setTimeOffStatus(this);");
							//tinput.onclick = function(){setTimeOffStatus(this);};
							gcell.appendChild(tinput);
							grow.appendChild(gcell);

							tinput = document.createElement("input");
							tinput.setAttribute("type", "hidden");
							tinput.setAttribute("name", "st" + j);
							tinput.setAttribute("id", "in_" + stno + "_" + j);
							tinput.setAttribute("value", evs);
							document.forms[0].appendChild(tinput);
			          		break;
						case '':
							gcell = document.createElement("td");
							gcell.setAttribute("width", bwidth);
							gcell.setAttribute("align", "center");
							gcell.setAttribute("valign", "middle");
							tinput = document.createElement("input");
							tinput.setAttribute("type", "button");
							tinput.setAttribute("id", stno + "_" + j);
							tinput.setAttribute("value", " ");
							tinput.setAttribute("onClick", "setTimeOffStatus(this);");
							//tinput.onclick = setTimeOffStatus(this);
							gcell.appendChild(tinput);
							grow.appendChild(gcell);

							tinput = document.createElement("input");
							tinput.setAttribute("type", "hidden");
							tinput.setAttribute("name", "st" + j);
							tinput.setAttribute("id", "in_" + stno + "_" + j);
							tinput.setAttribute("value", "");
							document.forms[0].appendChild(tinput);
			          		break;
						
						
						case '3':
						case '4':
						case '6':
						case '7':
						case '8':
						case '9':
							typ = eval(evs) - 2;
							gcell = document.createElement("td");
							gcell.setAttribute("width", bwidth);
							gcell.setAttribute("align", "center");
							gcell.setAttribute("valign", "middle");
							txtNode = document.createTextNode(tfType[typ]);
							gcell.appendChild(txtNode);
							grow.appendChild(gcell);

							tinput = document.createElement("input");
							tinput.setAttribute("type", "hidden");
							tinput.setAttribute("name", "st" + j);
							tinput.setAttribute("id", "in_" + stno + "_" + j);
							tinput.setAttribute("value", evs);
							document.forms[0].appendChild(tinput);
			          		break;
						case 'w':
							gcell = document.createElement("td");
							gcell.setAttribute("width", bwidth);
							gcell.setAttribute("align", "center");
							gcell.setAttribute("valign", "middle");
							txtNode = document.createTextNode("退選");
							gcell.appendChild(txtNode);
							grow.appendChild(gcell);

							tinput = document.createElement("input");
							tinput.setAttribute("type", "hidden");
							tinput.setAttribute("name", "st" + j);
							tinput.setAttribute("id", "in_" + stno + "_" + j);
							tinput.setAttribute("value", evs);
							document.forms[0].appendChild(tinput);
			          		break;
						case 'n':
							gcell = document.createElement("td");
							gcell.setAttribute("width", bwidth);
							gcell.setAttribute("align", "center");
							gcell.setAttribute("valign", "middle");
							txtNode = document.createTextNode("扣考");
							gcell.appendChild(txtNode);
							grow.appendChild(gcell);

							tinput = document.createElement("input");
							tinput.setAttribute("type", "hidden");
							tinput.setAttribute("name", "st" + j);
							tinput.setAttribute("id", "in_" + stno + "_" + j);
							tinput.setAttribute("value", evs);
							document.forms[0].appendChild(tinput);
			          		break;
						case 'e':
							gcell = document.createElement("td");
							gcell.setAttribute("width", bwidth);
							gcell.setAttribute("align", "center");
							gcell.setAttribute("valign", "middle");
							txtNode = document.createTextNode("異常");
							gcell.appendChild(txtNode);
							grow.appendChild(gcell);

							tinput = document.createElement("input");
							tinput.setAttribute("type", "hidden");
							tinput.setAttribute("name", "st" + j);
							tinput.setAttribute("id", "in_" + stno + "_" + j);
							tinput.setAttribute("value", evs);
							document.forms[0].appendChild(tinput);
			          		break;
						default:
							gcell = document.createElement("td");
							gcell.setAttribute("width", bwidth);
							gcell.setAttribute("align", "center");
							gcell.setAttribute("valign", "middle");
							txtNode = document.createTextNode(evs);
							gcell.appendChild(txtNode);
							grow.appendChild(gcell);

							tinput = document.createElement("input");
							tinput.setAttribute("type", "hidden");
							tinput.setAttribute("name", "st" + j);
							tinput.setAttribute("id", "in_" + stno + "_" + j);
							tinput.setAttribute("value", evs);
							document.forms[0].appendChild(tinput);
						}
					}
					gcell = document.createElement("td");
					grow.appendChild(gcell);
					grid.appendChild(grow);
				
					//	 產生一行節次
					/*
					
					if(((i+1) % 10) == 0){
						inHTML += "<tr><td width=\"120\">節次</td>";
						for(var k=0; k<TimeOffSubjInUse.length; k++){
							for(var p=TimeOffSubjInUse[k].begin; p<=TimeOffSubjInUse[k].end; p++){
								inHTML += "<td width=\"30\" align=\"left\" valign=\"top\">";
								if(daynite == '2' && p > 10){
									if(p==11) inHTML += "N1";
									else if(p==12) inHTML += "N2";
									else if(p==13) inHTML += "N3";
									else if(p==14) inHTML += "N4";
									else if(p==15) inHTML += "N5";
									else  inHTML += p;
								}else{
									inHTML += p;
								}
								inHTML += "</td>";
							}
						}
						inHTML += "<td></td>";
						inHTML += "</tr>";
					}
					
					*/
				}
				cell.appendChild(gtable);
				row.appendChild(cell);
				cbBody.appendChild(row);
				cbBody.style.display = "inline";
				button.style.display = "inline";
			}
			oidObj.disabled = false;
			tdateObj.disabled = false;
		}
	}
}

function setTimeOffStatus(button){
	var bval = button.value;
	//window.alert(button.id);
	var inputObj = document.getElementById("in_" + button.id);
	if(bval == '  '){
		button.value = "曠課";
		inputObj.value = "2";
	}else if(bval=='曠課'){
		button.value = "遲到/早退";
		inputObj.value = "5";
	}else{
		button.value = "  ";
		inputObj.value = "";
	}
}

function hiddenClassBook(){
	var cbBody = document.getElementById("classBookBody");
	//var button = document.getElementById("button1");
	//var inHTML = "<table cellpadding=10><tr><td>";
	//inHTML += "<img src=\"/CIS/pages/images/ajax-loader2.gif\" align=\"middle\">";
	//inHTML += "&nbsp;&nbsp;<font color=\"red\">資料傳送中.....</font></td></tr></table>";
	//cbBody.innerHTML = inHTML;
	cbBody.style.display = "none";
	document.getElementById("dtOid").disabled = true;
	document.getElementById("tdate").disabled = true;
	//window.alert(document.iForm.name);
	//button.style.display = "none";
	return true;
}

function fillClassBook(){
	if(handleState()){
		if(request.responseText){
			//window.alert(request.responseText);
			var cbBody = document.getElementById("classBookBody");
			var button = document.getElementById("button1");
			var oidObj = document.getElementById("dtOid");
			var tdateObj = document.getElementById("tdate");
			var oid = oidObj.value;
			var selDate = tdateObj.value;
			if(oid == orig_dtOid && selDate == orig_tdate){
				var jsonObj = JSON.parse(request.responseText);
				//window.alert("resetCategory:" + jsonObj);
				//var size = jsonObj.length;
				
				var init = jsonObj[0];
				var clazz = init.clazz;
				var depClassName = init.depClassName;
				var cscode = init.cscode;
				var cscodeName = init.cscodeName;
				var tfYear = init.tfYear;
				var tfMonth = init.tfMonth;
				var tfDay = init.tfDay;
				var daynite = init.daynite;
				//window.alert(JSON.stringify(jsonObj[2]));
				var TimeOffSubjInUse = JSON.parse(JSON.stringify(jsonObj[1]));
				var classBook = JSON.parse(JSON.stringify(jsonObj[2]));
				status = new Array(16);
				//var inHTML = "<form action=\"/CIS/Teacher/TimeOffInput.do\" method=\"post\" name=\"iForm\" onSubmit=\"hiddenClassBook();\">";
				var inHTML = "";
				//inHTML += "<input type=\"hidden\" name=\"openClass\" value=\"${TimeOffInputInfo.openClass}\">";
				//inHTML += "<input type=\"hidden\" name=\"mode\" value=\"${TimeOffInputInfo.mode}\">";
				inHTML += "<table class=\"style2\">";
				var stno = "";
				var typ = 0;
				var cols = 1;
				for(var k=0; k<TimeOffSubjInUse.length; k++){
					cols = cols + (TimeOffSubjInUse[k].end - TimeOffSubjInUse[k].begin + 1);
				}
				tfType = new Array("重病","曠課", "病假", "事假", "遲到/早退", "公假", "喪假", "婚假", "產假");
				
				//inHTML += "<tr><td>1:升旗 , 2:曠課 , 3:病假 , 4:事假 , 5:遲到早退 , 6:公假 , 7:喪假 , 8:婚假 , 9:產假 <br>";
				//inHTML += "狀態： w:退選, n:扣考, e:異常, x:禁止輸入</td></tr>";
				//inHTML += "<tr><td><font color=red>老師請注意:點名輸入時間為８天(包含例假日)</font></td></tr>";
				inHTML += "<tr><td colspan=2>";
				inHTML += "<font class=\"blue_13\">班級：" + clazz + "&nbsp;&nbsp;" + depClassName + "&nbsp;&nbsp;";
				inHTML += "科目：" + cscode + "&nbsp;&nbsp;" + cscodeName + "</font>";
				inHTML += "</td></tr>";
				inHTML += "<tr><td colspan=2 class=\"style2\">";
				inHTML += "日期：" + tfYear + " 年  " + tfMonth + " 月 " + tfDay + " 日";
				inHTML += "</td></tr>";
				inHTML += "<tr><td  class=\"style2\">";
				inHTML += "<table class=\"style2\" width=\"" + (150+(cols-1)*60) + "\" border=\"1\" cellpadding=\"2\" cellspacing=\"1\" bgcolor=\"#FFFFEE\" id=\"tb1\">";
				inHTML += "<tr><td class=\"style2\" width=\"150\" align=\"center\">節次</td>";
				for(var k=0; k<TimeOffSubjInUse.length; k++){
					for(var p=TimeOffSubjInUse[k].begin; p<=TimeOffSubjInUse[k].end; p++){
						inHTML += "<td class=\"style2\" width=\"60\" align=\"center\" valign=\"top\">";
						if(daynite == '2' && p > 10){
							if(p==11) inHTML += "N1";
							else if(p==12) inHTML += "N2";
							else if(p==13) inHTML += "N3";
							else if(p==14) inHTML += "N4";
							else if(p==15) inHTML += "N5";
							else  inHTML += p;
						}else{
							inHTML += p;
						}
						inHTML += "</td>";
					}
				}
				//inHTML += "<td></td>";
				inHTML += "</tr>";
				/*
				inHTML += "<tr><td width=\"120\">科目名稱</td>";
				for(var k=0; k<TimeOffSubjInUse.length; k++){
					for(var p=TimeOffSubjInUse[k].begin; p<=TimeOffSubjInUse[k].end; p++){
						inHTML += "<td width=\"30\" align=\"left\" valign=\"top\">";
						inHTML += cscodeName;
						inHTML += "</td>";
					}
				}
				inHTML += "</tr>";
				*/
				
				//window.alert(classBook[0].status1);
				for(var i = 0; i<classBook.length; i++){
					inHTML += "<input type=\"hidden\" name=\"studentNo\" value=\"" + classBook[i].studentNo + "\">";
					inHTML += "<tr><td class=\"style2\" width=\"150\">" + classBook[i].studentNo + " " + classBook[i].studentName + "</td>";
					stno = classBook[i].studentNo;
					var evs = "";
					//var status = classBook[i].status;
					for(var j = 0; j <= 15; j++){
						switch(j){
						case 0: 
							evs = classBook[i].status0;
							break;
						case 1: 
							evs = classBook[i].status1;
							break;
						case 2: 
							evs = classBook[i].status2;
							break;
						case 3: 
							evs = classBook[i].status3;
							break;
						case 4: 
							evs = classBook[i].status4;
							break;
						case 5: 
							evs = classBook[i].status5;
							break;
						case 6: 
							evs = classBook[i].status6;
							break;
						case 7: 
							evs = classBook[i].status7;
							break;
						case 8: 
							evs = classBook[i].status8;
							break;
						case 9: 
							evs = classBook[i].status9;
							break;
						case 10: 
							evs = classBook[i].status10;
							break;
						case 11: 
							evs = classBook[i].status11;
							break;
						case 12: 
							evs = classBook[i].status12;
							break;
						case 13: 
							evs = classBook[i].status13;
							break;
						case 14: 
							evs = classBook[i].status14;
							break;
						case 15: 
							evs = classBook[i].status15;
							break;
						default:
							evs = "x";
						}
					
					
						switch(evs){
						case 'x':
							inHTML += "<input type=\"hidden\" name=\"st" + j + "\" value=\"" + evs +"\">";
							break;
						case '2':
						case '5':
							typ = eval(evs) - 1;
			          		inHTML += "<td class=\"style2\" width=\"60\" align=\"center\">";
			          		inHTML += "<input type=\"button\" id=\"" + stno + "_" + j + "\" value=\"" + tfType[typ] + "\" onClick=\"setTimeOffStatus(this);\" class=\"timeoffButton\">";
			          		inHTML += "</td>";
			          		inHTML += "<input type=\"hidden\" name=\"st" + j + "\" id=\"in_" + stno + "_" + j + "\" value=\"" + evs + "\">";
			          		break;
						case '':
			          		inHTML += "<td class=\"style2\" width=\"60\" align=\"center\">";
			          		inHTML += "<input type=\"button\" id=\"" + stno + "_" + j + "\" value=\"  \" onClick=\"setTimeOffStatus(this);\" class=\"timeoffButton\">";
			          		inHTML += "</td>";
			          		inHTML += "<input type=\"hidden\" name=\"st" + j + "\" id=\"in_" + stno + "_" + j + "\" value=\"\">";
			          		break;
						
						
						case '3':
						case '4':
						case '6':
						case '7':
						case '8':
						case '9':
							typ = eval(evs) - 1;
			          		inHTML += "<td class=\"style2\" width=\"60\" align=\"center\">" + tfType[typ];
			          		inHTML += "</td>";
			          		inHTML += "<input type=\"hidden\" name=\"st" + j + "\" id=\"in_" + stno + "_" + j + "\" value=\"" + evs + "\">";
			          		break;
						case 'w':
			          		inHTML += "<td class=\"style2\" width=\"60\" align=\"center\">退選</td>";
			          		inHTML += "<input type=\"hidden\" name=\"st" + j + "\" id=\"in_" + stno + "_" + j + "\" value=\"" + evs + "\">";
			          		break;
						case 'n':
			          		inHTML += "<td class=\"style2\" width=\"60\" align=\"center\">扣考</td>";
			          		inHTML += "<input type=\"hidden\" name=\"st" + j + "\" id=\"in_" + stno + "_" + j + "\" value=\"" + evs + "\">";
			          		break;
						case 'e':
			          		inHTML += "<td class=\"style2\" width=\"60\" align=\"center\">異常</td>";
			          		inHTML += "<input type=\"hidden\" name=\"st" + j + "\" id=\"in_" + stno + "_" + j + "\" value=\"" + evs + "\">";
			          		break;
						default:
			          		inHTML += "<td class=\"style2\" width=\"60\" align=\"center\">" + evs;
			          		inHTML += "<input type=\"hidden\" name=\"st" + j + "\" id=\"in_" + stno + "_" + j + "\" value=\"" + evs + "\">";
			          		inHTML += "</td>";
						}
					}
					//inHTML += "<td></td>";
					inHTML += "</tr>";
				
					//	 產生一行節次
					/*
					
					if(((i+1) % 10) == 0){
						inHTML += "<tr><td width=\"120\">節次</td>";
						for(var k=0; k<TimeOffSubjInUse.length; k++){
							for(var p=TimeOffSubjInUse[k].begin; p<=TimeOffSubjInUse[k].end; p++){
								inHTML += "<td width=\"30\" align=\"left\" valign=\"top\">";
								if(daynite == '2' && p > 10){
									if(p==11) inHTML += "N1";
									else if(p==12) inHTML += "N2";
									else if(p==13) inHTML += "N3";
									else if(p==14) inHTML += "N4";
									else if(p==15) inHTML += "N5";
									else  inHTML += p;
								}else{
									inHTML += p;
								}
								inHTML += "</td>";
							}
						}
						inHTML += "<td></td>";
						inHTML += "</tr>";
					}
					
					*/
				}
				inHTML += "</table></td><td>&nbsp;</td></tr>";
				
				inHTML += "<tr><td colspan=2>";
				inHTML += "<INPUT type=\"submit\" name=\"method\" value=\"<bean:message key='StartUpload'/>\">&nbsp;&nbsp; ";
				inHTML += "<INPUT type=\"submit\" name=\"method\" value=\"<bean:message key='Cancel'/>\">";
				inHTML += "</td></tr>";
				inHTML += "</table>";
				cbBody.innerHTML = inHTML;
				cbBody.style.display = "inline";
				//button.style.display = "inline";
				//window.alert(inHTML);
			}
			oidObj.disabled = false;
			tdateObj.disabled = false;
		}
	}
}

function getTodayClassBook(){
	var dtObj =  document.getElementById("dtOid");
	var dateObj = document.getElementById("tdate");
	
	document.getElementById("tdate").value = onLine_date;
	for(var i=0; i<dateObj.length; i++){
		//window.alert(dateObj.options[i].value + ":" + onLine_date);
		if(dateObj.options[i].value == onLine_date){
		//window.alert(dateObj.options[i].value + ":ok:" + onLine_date);
			dateObj.options[i].selected = true;
			break;
		}
	}
	
	//window.alert('call:' + document.getElementById("dtOid").value + ':' + onLine_date + ':' + document.getElementById("tdate").value );
	document.getElementById("tdate").style.display = 'inline';
	getClassBook();
}
</script>

<c:if test="${TeachClasses != null && TimeOffInputInfo.mode=='onLine' && TimeOffInputInfo.dtOid != null}">
<script>
var onLine_oid = ${TimeOffInputInfo.dtOid};
//window.alert("'" + "${TimeOffInputInfo.tdate}" + "'");
onLine_date = new String("${TimeOffInputInfo.tdate}");
function setOnLine(){
	var dtObj =  document.getElementById("dtOid");
	var dateObj = document.getElementById("tdate");
	
	for(var i=0; i<dtObj.length; i++){
		if(dtObj.options[i].value == onLine_oid){
			dtObj.options[i].selected = true;
			break;
		}
	}
	document.getElementById("dtOid").value = onLine_oid;
	setTeachDateOption();
	document.getElementById("tdate").value = onLine_date;
	for(var i=0; i<dateObj.length; i++){
		//window.alert(dateObj.options[i].value + ":" + onLine_date);
		if(dateObj.options[i].value == onLine_date){
		//window.alert(dateObj.options[i].value + ":ok:" + onLine_date);
			dateObj.options[i].selected = true;
			break;
		}
	}
	
	//window.alert('call:' + document.getElementById("dtOid").value + ':' + onLine_date + ':' + document.getElementById("tdate").value );
	document.getElementById("tdate").style.display = 'inline';
	getClassBook();
}

setOnLine();
</script>
</c:if>

<c:if test="${TimeOffInputInfo.mode=='redirect' && TimeOffInputInfo.dtOid != null}">
<script>
var redirect_oid = ${TimeOffInputInfo.dtOid};
//window.alert("'" + "${TimeOffInputInfo.tdate}" + "'");
redirect_date = new String("${TimeOffInputInfo.tdate}");
function setRedirect(){
	var dtObj =  document.getElementById("dtOid");
	var dateObj = document.getElementById("tdate");
	
	dtObj.value = redirect_oid;
	dateObj.value = redirect_date;
	dtObj.style.display = 'none';
	dateObj.style.display = 'none';
	getClassBook();
}

setRedirect();
</script>
</c:if>


<script language="javascript">
		//<!--
				var iplimit = ${tcnt-1};
				
				function nextfocus(ntab) {
				var code = event.keyCode;
				var iptx1s = document.getElementById("1");
				if(code==46 || code==8) {
					return;
				}
				if(code==37 || code==38) {
					if(ntab==2) return;
					ntab = ntab -2;
				} else if(code==40) {
				
				}
				if(ntab > iplimit) return;
				var nextElem = document.getElementById(ntab);
				if(code==37 || code==38) {
					nextElem.value="";
				}
				nextElem.focus();
			};
		//-->
</script>

<script>
history.go(1);
</script>
