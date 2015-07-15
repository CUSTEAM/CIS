<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<table width="640" id="news" style="display:none" class="ds_box">
	<tr>		
		<td align="right" width="640">		
		<table width="100%">
			<tr>
				<td align="right" width="30" align="center"><img style="cursor:pointer;" src="images/icon/icon_info_exclamation.gif"></td>
				<td align="right" nowrap>功能說明</td>
				<td align="right" width="100%"></td>
				<td align="right" width="1">
				<input type="button" value="關閉" class="gSubmitlSmall" onClick="document.getElementById('news').style.display='none', document.getElementById('loadMsg').style.display='none'"/></td>
			</tr>
		</table>		
		</td>
	</tr>
	<tr>
		<td width="640" style="cursor:pointer;">
		<div id="newsInfo" style="width:100%">		
		<img src="images/indicator/loading2.gif" />		
		</div>
		</td>
	</tr>
	<tr>
		<td align="right" width="640" style="cursor:pointer;">
		<div id="newsInfosub">
		<img src="images/indicator/loading2.gif" />
		</div>
		</td>
	</tr>
</table>

<script>
var objectId='newsInfo';
try{
	var XMLHttpReqDyna=new XMLHttpRequest();	
	}catch(e){
	var XMLHttpReqDyna=false;
	}

var XMLHttpRequest=null;

function createXMLHttpRequestDyna(){
	if (window.ActiveXObject) {
        XMLHttpReqDyna = new ActiveXObject("Microsoft.XMLHTTP");
    }
    else if (window.ActiveXObject) {
        XMLHttpReqDyna = new ActiveXObject("Msxml2.XMLHTTP");
    }
    
    if(!XMLHttpReqDyna) {
    	alert("瀏覽器不支援");
	}	
}

function proceDyna1(){
	if(XMLHttpReqDyna.readyState==4){
 		if(XMLHttpReqDyna.status==200){
 			document.getElementById(objectId).innerHTML="";
 			document.getElementById(objectId).innerHTML=(XMLHttpReqDyna.responseXML.getElementsByTagName("Content")[0].firstChild.data);
 			document.getElementById(objectId+"sub").innerHTML=("<font size=1>最後編輯日期: "+XMLHttpReqDyna.responseXML.getElementsByTagName("EditStamp")[0].firstChild.data+"</font>"); 			
 		}
 	}
}

function getNews(Oid, objId) {	
	objectId=objId;
	sendDyna1('/CIS/getModuleHelp?ModuleOid='+Oid+'&'+Math.floor(Math.random()*999));
}
	
function sendDyna1(url){	
	createXMLHttpRequestDyna();
	try{
		XMLHttpReqDyna.open("GET",url,true);
	}catch(e){
		alert("ajax error");
	}	
	XMLHttpReqDyna.onreadystatechange=proceDyna1;
	XMLHttpReqDyna.send(null);
}	
 	
</script>



<script>
function gt_cnameLeftDyna(ed) {
				var tmp = ed.offsetLeft;
				ed = ed.offsetParent
			while(ed) {
				tmp += ed.offsetLeft;
				ed = ed.offsetParent;
				}
			return tmp;
			}
	
	function gt_cnameTopDyna(ed) {
					var tmp = ed.offsetTop;
					ed = ed.offsetParent
				while(ed) {
					tmp += ed.offsetTop;
					ed = ed.offsetParent;
					}
				return tmp+24;
				}
			
	function showNews(oId){		
		document.getElementById('news').style.left=gt_cnameLeftDyna(document.getElementById(oId));	//y座標
 		document.getElementById('news').style.top=gt_cnameTopDyna(document.getElementById(oId));	//x座標
		document.getElementById('news').style.display="inline";
		
		document.getElementById('loadMsg').style.display="inline";
		
		
	document.getElementById('loadMsg').style.width=document.body.scrollWidth;
	//alert(document.getElementById('loadMsg').style);
	//document.getElementById('loadMsg').style.height=document.body.clientHeight;
	if (navigator.appName.indexOf("Microsoft")!=-1) {
	
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
}
</script>