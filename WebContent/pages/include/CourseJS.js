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

  	document.getElementById('loadMsgSub').innerHTML='<table width="300" class="hairLineTable"><tr height="50"><td class="hairLineTdF"'+
  	' align="center"><table><tr><td><img src="images/indicator.gif"/></td><td>'+
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

function showObj(id){
	if(document.getElementById(id).style.display=="none"){
		document.getElementById(id).style.display="inline";
	}else{
		document.getElementById(id).style.display="none";
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