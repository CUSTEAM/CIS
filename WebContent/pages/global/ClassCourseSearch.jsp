<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ include file="/taglibs.jsp"%>
<%@ include file="../Main/style.jsp"%>
<html:html locale="true">
<head>
	<html:base />
	<title><bean:message key="LoginForm.title" /></title>
	<link href="/CIS/pages/images/css/Istyle.css" type="text/css" rel="stylesheet">
	<link href="/CIS/pages/images/css/decorate_blue.css" type="text/css" rel="stylesheet">
	<link href="/CIS/pages/images/css/home_blue.css" type="text/css" rel="stylesheet">
	<link href="/CIS/pages/images/css/global.css" type="text/css" rel="stylesheet">
	
	<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
	<meta HTTP-EQUIV="expires" CONTENT="-1">
	<meta name="ROBOTS" content="none">
</head>

<body>
<script src="/CIS/pages/include/decorate.js"></script>
<table class="transparent"; id="loadMsg" style="display: none;">
	<tr>
		<td align="center" valign="middle">
			<img src="/CIS/pages/images/indicator/indicator1.gif" style="display: none;"/>
			<img src="/CIS/pages/images/indicator/indicator2.gif" style="display: none;"/>
			<img src="/CIS/pages/images/indicator/indicator3.gif" style="display: none;"/>
			<img src="/CIS/pages/images/indicator/indicator4.gif" style="display: none;"/>
			<img src="/CIS/pages/images/indicator/indicator0.gif" style="display: none;"/>
		</td>
	</tr>
</table>

<table id="helpMessage" class="ds_box" style="display:none">
	<tr>
		<td id="helpMessageInfo">
		
		
		</td>
	</tr>
</table>

<script>
document.write('<table class="non_transparent_new" id="loadIco" style="display:none;top:'+
Math.floor(Math.random()*(window.screen.availHeight-300))+';'+
'left:'+Math.floor(Math.random()*(window.screen.availWidth-300))+';">')
</script>
	<tr height="100"><td><table><tr><td id="loadMsgSub"></td></tr></table></td></tr>
</table>
	<table width="99%" class="hairLineTable">
		<tr>
			<html:form action="/ClassCourseSearch" method="post" enctype="multipart/form-data" onsubmit="init1('系統處理中...')">
				<td class="hairLineTdF">



					<table width="99%" class="hairLineTable">
						<tr height="30">
							<td class="fullColorTable" width="100%">
								<div style="float:left; padding:0 5 0 5;">
									<img src="/CIS/pages/images/icon/folder_magnify.gif"
										id="piftitle">
								</div>
								<div nowrap style="float:left;">
									<font class="gray_15">課程查詢</font>
								</div>
							</td>
						</tr>



					<tr>
						<td class="hairLineTdF">
							<%@ include file="ListCourse/search.jsp"%>
						</td>
					</tr>
					<tr height="30">
		<td class="fullColorTable" align="center" width="100%">


		<INPUT type="submit"
			   name="method"
			   id="Query"
			   onMouseOver="showHelpMessage('查詢後可供列表', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)"
			   value="<bean:message key='Query'/>"
			   class="gSubmit">
			   
		<INPUT type="button"
			   name="method" id="Create"
			   value="查看說明" onClick="showObj('help');"
			   class="gCancel" 
			   onMouseOver="showHelpMessage('查看說明能更快獲得所需要的資料', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)" />
			   
		<INPUT type="submit"
			   name="method"
			   id="Clear"
			   onMouseOver="showHelpMessage('清除查詢', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)"
			   value="<bean:message key='Clear'/>"
			   class="gGreen">
			   
		
			   
			   
		
			   
		</td>
	</tr>

						<c:if test="${!empty csses}">

							<tr>
								<td class="hairLineTdF">
								<%@ include file="ListCourse/list.jsp"%>
								</td>
							</tr>

							<tr height="30">
								<td class="fullColorTable" align="center">


								</td>
							</tr>
						</c:if>
					</table>
			
		</td>
		</tr>
		</html:form>
	</table>

	<script>
	function jumpMenu(targ,selObj,restore){
		eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'")
		eval(targ+".location.target='_blank'");
		if (restore) selObj.selectedIndex=0;
}
</script>



<%@ include file="/pages/include/AjaxUniMod.jsp"%>
<%@ include file="/pages/include/ajaxGetMate.jsp"%>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp"%>
































<script>
function init1(message) {
    var loadingMessage;
  	if (message==null){
  		loadingMessage="Loading";
  	}else{
  		loadingMessage=message;
  	}
	document.getElementById('loadMsg').style.width=document.body.scrollWidth;
	//document.getElementById('loadMsg').style.height=document.body.clientHeight;
	if (navigator.appName.indexOf("Microsoft")!=-1) {
		//alert("IE");
		//document.getElementById('loadMsg').style.height=document.body.scrollHeight;// IE
		//document.getElementById('loadMsg').style.height=document.body.clientHeight;
		var scrollHeight=document.body.scrollHeight;
		var clientHeight=document.body.clientHeight;
		if (scrollHeight>clientHeight){
			document.getElementById('loadMsg').style.height=scrollHeight;// IE
		}else{
			document.getElementById('loadMsg').style.height=clientHeight;// IE
		}
		
	}else{
		//alert("not IE");		
		//document.getElementById('loadMsg').style.height=document.body.clientHeight;
		//document.getElementById('loadMsg').style.height=document.body.offsetHeight;
		document.getElementById('loadMsg').style.height=document.body.scrollHeight;// FF, ns
	}
	
	

  	document.getElementById('loadMsgSub').innerHTML='<table width="300" class="hairLineTable"><tr height="50"><td class="hairLineTdF" '+
  	'align="center"><table><tr><td><img src="/CIS/pages/images/indicator/indicator'+Math.floor(Math.random()*5)+'.gif"/></td><td>'+
  	//' align="center"><img src=/CIS/pages/downloads/course/0003347866.gif>&nbsp;&nbsp;'+  	
  	loadingMessage+'</td></tr></table></td></tr></table>';
	//document.getElementById('loadMsgSub').innerHTML='<img src="images/indicator.gif"/>';
	
    document.getElementById('loadMsg').style.display='inline';
    document.getElementById('loadIco').style.display='inline';
    try{
  		document.getElementById('AcsnameInfo').style.display = "none";
 		document.getElementById('Acsname').style.display = "none";
 	}catch(e){
 		
 	}
}

	function getLeft(ed) {
					var tmp = ed.offsetLeft;
					ed = ed.offsetParent
				while(ed) {
					tmp += ed.offsetLeft;
					ed = ed.offsetParent;
					}
				return tmp;
				}
	
	function getTop(ed) {
					var tmp = ed.offsetTop;
					ed = ed.offsetParent
				while(ed) {
					tmp += ed.offsetTop;
					ed = ed.offsetParent;
					}
				return tmp+24;
				}



	function showHelpMessage(info, mode, id){
		
		document.getElementById('helpMessage').style.left=getLeft(document.getElementById(id))+5;	//y座標
 		document.getElementById('helpMessage').style.top=getTop(document.getElementById(id))-5;	//x座標
		document.getElementById('helpMessage').style.display=mode;
		document.getElementById('helpMessageInfo').innerHTML="";
		
		document.getElementById('helpMessageInfo').innerHTML=info;
	
	}
</script>


<table id="helpMessage" class="ds_box" style="display:none">
	<tr>
		<td id="helpMessageInfo">
		
		
		</td>
	</tr>
</table>


<table id="MapMessage" class="ds_box" style="display:none" style="position:inside; left:170px;  top:10px; z-index:32768">
	<tr>
		<td id="MapInfo">
		
		
		</td>
	</tr>
</table>

<script>
function getMapTop(ed) {
	var tmp = ed.offsetTop;
	ed = ed.offsetParent
	while(ed) {
		tmp += ed.offsetTop;
		ed = ed.offsetParent;
	}
	return tmp+24;
}
function techTable(Oid, id, type){

	document.getElementById('MapMessage').style.top=getTop(document.getElementById(id))-5;	//y座標
	document.getElementById('MapMessage').style.left=100;	//x座標
	//document.getElementById('MapMessage').style.display=inline;
	showObj('MapMessage');
	document.getElementById('MapInfo').innerHTML="";
	
	if(type=='room'){
		document.getElementById('MapInfo').innerHTML='<table><tr><td align=right><input type=button class=gSubmit value=關閉課表 onClick=closeTimeTable(); /></td></tr><tr><td>'+
		'<iframe src=/CIS/RoomTimetable4HTML?room_id='+Oid+' width=780 height=960 marginwidth=0 marginheight=0 hspace=0 vspace=0 frameborder=0 scrolling=no/>'+
		'</td></tr></table>';
	}
	
	if(type=='tech'){
		document.getElementById('MapInfo').innerHTML='<table><tr><td align=right><input type=button class=gSubmit value=關閉課表 onClick=closeTimeTable(); /></td></tr><tr><td>'+
		'<iframe src=/CIS/RoomTimetable4HTML?getTt='+Oid+' width=780 height=960 marginwidth=0 marginheight=0 hspace=0 vspace=0 frameborder=0 scrolling=no/>'+
		'</td></tr></table>';
	}
						
	if(type=='class'){
		document.getElementById('MapInfo').innerHTML='<table><tr><td align=right><input type=button class=gSubmit value=關閉課表 onClick=closeTimeTable(); /></td></tr><tr><td>'+
		'<iframe src=/CIS/RoomTimetable4HTML?getCt='+Oid+' width=780 height=960 marginwidth=0 marginheight=0 hspace=0 vspace=0 frameborder=0 scrolling=no/>'+
		'</td></tr></table>';	
	}					
						
	document.getElementById('loadMsg').style.width=document.body.scrollWidth;
	
	if (navigator.appName.indexOf("Microsoft")!=-1) {
		var scrollHeight=document.body.scrollHeight;
		var clientHeight=document.body.clientHeight;
		if (scrollHeight>clientHeight){
			document.getElementById('loadMsg').style.height=scrollHeight;// IE
		}else{
			document.getElementById('loadMsg').style.height=clientHeight;// IE
		}
	
	
	}else{
		document.getElementById('loadMsg').style.height=document.body.scrollHeight;// FF, ns
	}
	
	document.getElementById('loadMsg').style.display='inline';
		
}

function closeTimeTable(){	
	document.getElementById('loadMsg').style.display='none';
	document.getElementById('MapMessage').style.display='none';	
}

</script>

</body>
</html:html>
