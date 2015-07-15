function generateTableHeader(title) {
	document.write("<table width=\"" + width + "\" cellpadding=\"0\" cellspacing=\"0\">");
	document.write("<tr><td width=\"100%\" height=\"30\" valign=\"top\" class=\"decorate\" align=\"center\">");
	document.write("<div id=\"nifty\"><div class=\"rtop\"><div class=\"r1f\"></div><div class=\"r2f\"></div><div class=\"r3f\"></div><div class=\"r4f\"></div></div>");
	document.write(title + "</div></td></tr><tr><td width=\"100%\" align=\"center\" valign=\"top\" class=\"decorate\">");
};

function generateTableFooter(title) {
	document.write("</td></tr><tr><td width=\"100%\" height=\"30\" valign=\"bottom\" align=\"center\" class=\"decorate\">" + title);
	document.write("<div class=\"rtop\"><div class=\"r4f\"></div><div class=\"r3f\"></div><div class=\"r2f\"></div><div class=\"r1f\"></div></div></td></tr></table>");
};

function generateTableBanner(title) {
	document.write("<tr><td width=\"100%\" height=\"30\" valign=\"center\" align=\"center\" class=\"fullColorTable\">" + title + "</td></tr>");
};

function init(message) {
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
  	'align="center"><table><tr><td><img src="images/indicator/indicator'+Math.floor(Math.random()*5)+'.gif"/></td><td>'+
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

function uninit(){
	document.getElementById('loadMsg').style.display='none';
    document.getElementById('loadIco').style.display='none';
}

function callloadMsg() {
  	document.getElementById('loadMsg').style.height=document.body.scrollHeight;
	document.getElementById('loadMsg').style.width=document.body.scrollWidth;
    document.getElementById('loadMsg').style.display='inline';
    document.getElementById('loadIco').style.display='inline';
  }

function externallinks() {
   if (!document.getElementsByTagName) return;
      var anchors = document.getElementsByTagName("a");
      for (var i=0; i<anchors.length; i++) {
      var anchor = anchors[i];
      if (anchor.getAttribute("href") &&
      anchor.getAttribute("rel") == "external")
      anchor.target = "_blank";
    }
}

//自動顯示或隱藏物件
function showObj(id){		
	//若IE
	try{		
		if(document.getElementById(id).style.display=="none"){
			document.getElementById(id).style.display="inline";
			return;
		}else{
			document.getElementById(id).style.display="none";
			return;
		}
	}catch(e){
		//若FireFox	
		try{
			if(document.getElementById(id).style.display=="block" || document.getElementById(id).style.display==""){
				document.getElementById(id).style.display="inline";
				return;
			}
			document.getElementById(id).style.display="block";
			return;
		}catch(e){
		//若其他
		
		return;
		
		
		}	
	}
}

//變換所有的文字方塊
function chInput(id){	
	var input=document.getElementsByTagName("input");
	
	for (i=0; i<input.length; i++){
		if(input[i].getAttribute("type")=="text"){
			input[i].className="textNonFocus";
		}
	}
	
	input=document.getElementsByTagName("div");
	for (i=0; i<input.length; i++){
		if(input[i].getAttribute("type")=="border"){
			input[i].className="selectNonFocus";
		}				
	}	
	if(document.getElementById(id).getAttribute("type")=="text"){//若是文字欄位
		document.getElementById(id).className="textOnFocus";
	}else{
		document.getElementById("select_"+id).className="selectOnFocus";
	}	
}

function chBigInput(id){	
	var input=document.getElementsByTagName("input");
	
	for (i=0; i<input.length; i++){
		if(input[i].getAttribute("type")=="text"){
			input[i].className="bigInput";
		}
	}
	
	input=document.getElementsByTagName("div");
	for (i=0; i<input.length; i++){
		if(input[i].getAttribute("type")=="border"){
			input[i].className="selectNonFocus";
		}				
	}	
	if(document.getElementById(id).getAttribute("type")=="text"){//若是文字欄位
		document.getElementById(id).className="bigInputOnFocus";
	}else{
		document.getElementById("select_"+id).className="selectOnFocus";
	}	
}

//判斷瀏覽器
function getBrowser() {   
   //if (window.XMLHttpRequest) { // Mozilla, Safari,...
      //return "FF";
   //}
   
   //if (window.ActiveXObject) { // IE
      //return "IE";
   //}
   
	var isIE = navigator.userAgent.search("MSIE") > -1; 
	var isIE7 = navigator.userAgent.search("MSIE 7") > -1; 
	var isFirefox = navigator.userAgent.search("Firefox") > -1; 
	var isOpera = navigator.userAgent.search("Opera") > -1; 
	var isSafari = navigator.userAgent.search("Safari") > -1;//Google瀏覽器是用這核心 
	
	if (isIE7) { 
	    //alert('isIE7');
	    return "IE";
	} 
	if (isIE) { 
	    //alert('isIE'); 
	    return "IE";
	} 
	if (isFirefox) { 
	    //alert('isFirefox');
	    return "FF";
	} 
	if (isOpera) { 
	    //alert('isOpera');
	    return "OP";
	} 
	if (isSafari) { 
	    //alert('isSafari');
	    return "SF";
	}
}

//按鍵確認
function confirmSubmit(str){
	var agree=confirm(str);
	if (agree)
		return true ;
	else
		return false ;
}

function clearid(id){
	document.getElementById(id).value="";	
}

function jumpMenu(targ,selObj,restore){
	eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'")
	eval(targ+".location.target='_blank'");
	if (restore) selObj.selectedIndex=0;
}