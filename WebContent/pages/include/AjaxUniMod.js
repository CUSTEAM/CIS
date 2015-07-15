/**
 * 基本設定
 */
try{
	var XMLHttpReqDyna=new XMLHttpRequest();
}catch(e){
	var XMLHttpReqDyna=false;
}  		
var objectId=''; // 被動作的物件
var targetObjectId=''; // 連鎖反應的物件
var tid='';
var XMLHttpRequest=null;
	
function createXMLHttpRequestDyna(){
		if(window.ActiveXObject){ 
			try{
				XMLHttpReqDyna=new ActiveXObject("Msxml2.XMLHTTP");
				}catch(e){
				  alert('shit! AJAX contect ERROR!');
					try{
						XMLHttpReqDyna=new ActiveXObject("Microsoft.XMLHTTP");
						}catch(e){
               alert('shit! AJAX contect ERROR!');
            }
		}
	}
}
		
/**
 * 開始連線
 */
function sendDyna(url){
	createXMLHttpRequestDyna();
	XMLHttpReqDyna.open("GET",url,true);
	XMLHttpReqDyna.onreadystatechange=proceDyna;
	XMLHttpReqDyna.send(null);
}

/**
 * 連線成功開始取值並顯示
 */
function proceDyna(){
 		
if(XMLHttpReqDyna.readyState==4){
	if(XMLHttpReqDyna.status==200){

/**
 * 取得顯示x座標
 */
function gt_cnameLeftDyna(ed) {
	var tmp = ed.offsetLeft;
	ed = ed.offsetParent
while(ed) {
	tmp += ed.offsetLeft;
	ed = ed.offsetParent;
	}
return tmp;
}

/**
 * 取得顯示y座標
 */
function gt_cnameTopDyna(ed) {
	var tmp = ed.offsetTop;
	ed = ed.offsetParent
while(ed) {
	tmp += ed.offsetTop;
	ed = ed.offsetParent;
	}
return tmp+24;
}				
				
/**
 * 定義座標
 */
document.getElementById('Acsname').style.left=gt_cnameLeftDyna(document.getElementById(objectId));	//y座標
document.getElementById('Acsname').style.top=gt_cnameTopDyna(document.getElementById(objectId));	//x座標 				
 				
/**
 * 取得xml中的資料
 */
var tmp=XMLHttpReqDyna.responseXML.getElementsByTagName("no").length;
var tmpString='<table cellpadding="0" cellspacing="0">'; 				
 				
/**
 * 顯示資料 (查詢指標為'no')
 */ 
document.getElementById('AcsnameInfo').style.display = "inline"; 
document.getElementById('Acsname').style.display = "inline";
if(tid=='no'){
 				
/**
 * 自動顯示
 */
if(tmp==1){
	document.getElementById(objectId).value=(XMLHttpReqDyna.responseXML.getElementsByTagName("no")[0].firstChild.data);
	document.getElementById(targetObjectId).value=(XMLHttpReqDyna.responseXML.getElementsByTagName("name")[0].firstChild.data);
	
	document.getElementById('AcsnameInfo').style.display = "none";
	document.getElementById('Acsname').style.display = "none";
	
	//alert("shit! AJAX connect ERROR! \n"+document.getElementById(objectId).value);
	//alert("o`shit!");					
	document.getElementById(objectId).maxLength=document.getElementById(objectId).value.length;
}				
 				
for(i=0; i<tmp; i++){ 				

	tmpString=tmpString+'<tr height="20" onmousemove=this.className="fullColorTr" onMouseOut=this.className="fullColorTrF"><td id='
	+(XMLHttpReqDyna.responseXML.getElementsByTagName("no")[i].firstChild.data)+
	' onclick="document.getElementById(objectId).value=this.id, document.getElementById(targetObjectId).value=this.innerHTML">'+
	(XMLHttpReqDyna.responseXML.getElementsByTagName("name")[i].firstChild.data)+'</td>';
	
	tmpString=tmpString+'<td id='
	+(XMLHttpReqDyna.responseXML.getElementsByTagName("name")[i].firstChild.data)+
	' onclick="document.getElementById(targetObjectId).value=this.id, document.getElementById(objectId).value=this.innerHTML">'+
	(XMLHttpReqDyna.responseXML.getElementsByTagName("no")[i].firstChild.data)+'</td></tr>';

}
tmpString=tmpString+'</table>';  				

}else{
	for(i=0; i<tmp; i++){

	tmpString=tmpString+'<tr height="20" onmousemove=this.className="fullColorTr" onMouseOut=this.className="fullColorTrF"><td id='
	+(XMLHttpReqDyna.responseXML.getElementsByTagName("no")[i].firstChild.data)+
	' onclick="document.getElementById(targetObjectId).value=this.id, document.getElementById(objectId).value=this.innerHTML">'+
	(XMLHttpReqDyna.responseXML.getElementsByTagName("name")[i].firstChild.data)+'</td>';

	tmpString=tmpString+'<td id='
	+(XMLHttpReqDyna.responseXML.getElementsByTagName("name")[i].firstChild.data)+
	' onclick="document.getElementById(objectId).value=this.id, document.getElementById(targetObjectId).value=this.innerHTML">'+
	(XMLHttpReqDyna.responseXML.getElementsByTagName("no")[i].firstChild.data)+'</td></tr>';

	}
tmpString=tmpString+'</table>'; 
}				
	
	document.getElementById('AcsnameInfo').innerHTML =tmpString
	document.getElementById('Acsname').style.display = "inline";
	
	}else{
		window.alert("shit! AJAX connect error!"+" XMLHttpReq.readyState="+XMLHttpReq.readyState+" XMLHttpReqDyna.status="+XMLHttpReqDyna.status);
		}
	}
}