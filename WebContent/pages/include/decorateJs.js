function replaceHtml(el, html) {
    var oldEl = typeof el === "string" ? document.getElementById(el) : el;
    /*@cc_on // 原始的 innerHTML 在 IE 中的性能好
        oldEl.innerHTML = html;
        return oldEl;
    @*/
    var newEl = oldEl.cloneNode(false);
    newEl.innerHTML = html;
    oldEl.parentNode.replaceChild(newEl, oldEl);
    /* 移除舊元素，返回新的元素引用。*/
    return newEl;
}

function coverSer(){
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

function unCoverScr(){
	document.getElementById('loadMsg').style.display='none';
}

function init(message) {
    var loadingMessage;
  	if (message==null){
  		loadingMessage="Loading";
  	}else{
  		loadingMessage=message;
  	}
		
  	coverSer();
  	
  	document.getElementById('loadMsgSub').innerHTML='<table width="300" class="hairLineTable"><tr height="50"><td class="hairLineTdF" '+
  	'align="center"><table><tr><td><img src="images/indicator/indicator'+Math.floor(Math.random()*5)+'.gif"/></td><td>'+
  	loadingMessage+'</td></tr></table></td></tr></table>';
	
  	
    document.getElementById('loadIco').style.display='inline';
    try{
  		document.getElementById('AcsnameInfo').style.display = "none";
 		document.getElementById('Acsname').style.display = "none";
 	}catch(e){
 		
 	}
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


 //取得顯示x座標
function getLeft(ed) {
	var tmp = ed.offsetLeft;
	ed = ed.offsetParent
while(ed) {
	tmp += ed.offsetLeft;
	ed = ed.offsetParent;
	}
return tmp;
}


//取得顯示y座標
function getTop(ed) {
	var tmp = ed.offsetTop;
	ed = ed.offsetParent
while(ed) {
	tmp += ed.offsetTop;
	ed = ed.offsetParent;
	}
return tmp+24;
}