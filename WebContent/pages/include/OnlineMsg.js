setInterval("sendOnlineMsgDyna(Math.floor(Math.random()*(9999-1)));",10000);
setTimeout("showHelpMessage('<img src=images/indicator/indicator1.gif>', 'inline', 'myPic');",3000);
var MsgRequest=null;
try{var MsgReqDyna=new XMLHttpRequest();}catch(e){var MsgReqDyna=false;} 
function CrOnlineMsgDyna(){	
	if (window.ActiveXObject){
		try{
			MsgReqDyna=new ActiveXObject("Msxml2.XMLHTTP");
		}catch (e){			
			MsgReqDyna=new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
}

		
/**
 * 開始連線
 */
function sendOnlineMsgDyna(r){
	CrOnlineMsgDyna();
	MsgReqDyna.open("GET","/CIS/getOnleneMsg?"+r,true);
	MsgReqDyna.onreadystatechange=msgDyna;
	MsgReqDyna.send(null);
}

/**
 * 連線成功開始取值並顯示
 */
function msgDyna(){
	if(MsgReqDyna.readyState==4){
		if(MsgReqDyna.status==200){
			if(MsgReqDyna.responseXML.getElementsByTagName("ctMsg").length>1){
				showHelpMessage(MsgReqDyna.responseXML.getElementsByTagName("ctMsg")[0].firstChild.data, 'inline', 'myPic');
			}else{
				document.getElementById("helpMessage").style.display="none";
			}
			
			
		}
	}
}
 		