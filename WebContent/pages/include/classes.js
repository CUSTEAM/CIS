var geturl="http://cap.cust.edu.tw/CIS/getDepartClass";
var XMLHttpReqDyna;
var XMLHttpRequest=null;

//建立HttpRequestDyna
function createXMLHttpRequestDyna(){
	try{
		XMLHttpReqDyna=new XMLHttpRequest();
	}catch(e){
		XMLHttpReqDyna=false;
	}
	if (window.ActiveXObject){
		try{
			XMLHttpReqDyna=new ActiveXObject("Msxml2.XMLHTTP");
		}catch (e){
			XMLHttpReqDyna=new ActiveXObject("Microsoft.XMLHTTP");
		}
	}	
	if (window.XMLHttpRequest){
		XMLHttpReqDyna=new XMLHttpRequest();
	}	
}

//傳送器
function sendDyna(url){
	createXMLHttpRequestDyna();
	try{
		XMLHttpReqDyna.open("GET",url,true);
	}catch(e){
		alert("ajax 錯誤1");
	}
	XMLHttpReqDyna.onreadystatechange=proceDyna;	
	XMLHttpReqDyna.send(null);		
}

//開始主要流程
function proceDyna(){	
	
	var tmp=0;
	var tmpString="";
	
	if(XMLHttpReqDyna.readyState==4){		
		//回傳代碼正常狀態200
 		if(XMLHttpReqDyna.status==200){
 		
 			//取校區						
			tmp=XMLHttpReqDyna.responseXML.getElementsByTagName("CampusNo").length;
			tmpString="";			
			var CampusStart="";
			var CampusEnd="";
			var CampusNo="";
			var CampusName="";
			var command="";
			
			for(i=0; i<tmp; i++){				
				CampusNo=XMLHttpReqDyna.responseXML.getElementsByTagName("CampusNo")[i].firstChild.data;				
				command="      \"document.getElementById('CampusNo').value='"+CampusNo+"', getClass()\"       ";
				CampusName=XMLHttpReqDyna.responseXML.getElementsByTagName("CampusName")[i].firstChild.data;				
				
				if(CampusNo==document.getElementById("CampusNo").value){
					CampusStart="<table><tr><td><b class='xa1'></b><b class='xa2'></b><b class='xa3'></b><b class='xa4'></b>";
					CampusEnd="<b class='xa4'></b><b class='xa3'></b><b class='xa2'></b><b class='xa1'></b></td></tr></table>";
					tmpString=tmpString+CampusStart+"<div class='CampusDivOn' onClick="+command+">"+CampusName+"</div>"+CampusEnd;
				}else{
					CampusStart="<table><tr><td><b class='xb1'></b><b class='xb2'></b><b class='xb3'></b><b class='xb4'></b>";
					CampusEnd="<b class='xb4'></b><b class='xb3'></b><b class='xb2'></b><b class='xb1'></b></td></tr></table>";
					tmpString=tmpString+CampusStart+"<div class='CampusDiv' onClick="+command+">"+CampusName+"</div>"+CampusEnd;
				}
							
			}
			document.getElementById("CampusAreaTd").innerHTML=tmpString;			
			
			//取學制
			tmp=XMLHttpReqDyna.responseXML.getElementsByTagName("SchoolNo").length;
			tmpString="";			
			var SchoolStart="";
			var SchoolEnd="";
			var SchoolNo="";
			var SchoolName="";
			var command="";			
			for(i=0; i<tmp; i++){				
				SchoolNo=XMLHttpReqDyna.responseXML.getElementsByTagName("SchoolNo")[i].firstChild.data;				
				command="      \"document.getElementById('SchoolNo').value='"+SchoolNo+"', getClass()\"       ";
				SchoolName=XMLHttpReqDyna.responseXML.getElementsByTagName("SchoolName")[i].firstChild.data;
				
				if(SchoolNo==document.getElementById("SchoolNo").value){
					SchoolStart="<table><tr><td><b class='xa1'></b><b class='xa2'></b><b class='xa3'></b><b class='xa4'></b>";
					SchoolEnd="<b class='xa4'></b><b class='xa3'></b><b class='xa2'></b><b class='xa1'></b></td></tr></table>";
					tmpString=tmpString+SchoolStart+"<div class='SchoolDivOn' onClick="+command+">"+SchoolName+"</div>"+SchoolEnd;				
				}else{
					SchoolStart="<table><tr><td><b class='xb1'></b><b class='xb2'></b><b class='xb3'></b><b class='xb4'></b>";
					SchoolEnd="<b class='xb4'></b><b class='xb3'></b><b class='xb2'></b><b class='xb1'></b></td></tr></table>";
					tmpString=tmpString+SchoolStart+"<div class='SchoolDiv' onClick="+command+">"+SchoolName+"</div>"+SchoolEnd;
				}
							
			}
			document.getElementById("SchoolAreaTd").innerHTML=tmpString;
			
			//取科系
			tmp=XMLHttpReqDyna.responseXML.getElementsByTagName("DeptNo").length;
			tmpString="";			
			var DeptStart="";
			var DeptEnd="";
			var DeptNo="";
			var DeptName="";
			var command="";			
			for(i=0; i<tmp; i++){				
				DeptNo=XMLHttpReqDyna.responseXML.getElementsByTagName("DeptNo")[i].firstChild.data;				
				command="      \"document.getElementById('DeptNo').value='"+DeptNo+"', getClass()\"       ";
				DeptName=XMLHttpReqDyna.responseXML.getElementsByTagName("DeptName")[i].firstChild.data;				
				
				if(DeptNo==document.getElementById("DeptNo").value){
					DeptStart="<table><tr><td><b class='xa1'></b><b class='xa2'></b><b class='xa3'></b><b class='xa4'></b>";
					DeptEnd="<b class='xa4'></b><b class='xa3'></b><b class='xa2'></b><b class='xa1'></b></td></tr></table>";
					tmpString=tmpString+DeptStart+"<div class='DeptDivOn' onClick="+command+">"+DeptName+"</div>"+DeptEnd;
				
				}else{
					DeptStart="<table><tr><td><b class='xb1'></b><b class='xb2'></b><b class='xb3'></b><b class='xb4'></b>";
					DeptEnd="<b class='xb4'></b><b class='xb3'></b><b class='xb2'></b><b class='xb1'></b></td></tr></table>";
					tmpString=tmpString+DeptStart+"<div class='DeptDiv' onClick="+command+">"+DeptName+"</div>"+DeptEnd;
				}
							
			}
			document.getElementById("DeptAreaTd").innerHTML=tmpString;
			
			//取年級
			tmp=XMLHttpReqDyna.responseXML.getElementsByTagName("Grade").length;
			tmpString="";
			var GradeStart="";
			var GradeEnd="";
			var Grade="";
			var GradeName="";
			var command="";			
			for(i=0; i<tmp; i++){				
				Grade=XMLHttpReqDyna.responseXML.getElementsByTagName("Grade")[i].firstChild.data;				
				command="      \"document.getElementById('Grade').value='"+Grade+"', getClass()\"       ";
				GradeName=XMLHttpReqDyna.responseXML.getElementsByTagName("GradeName")[i].firstChild.data;				
				
				
				if(Grade==document.getElementById("Grade").value){
					GradeStart="<table><tr><td><b class='xa1'></b><b class='xa2'></b><b class='xa3'></b><b class='xa4'></b>";
					GradeEnd="<b class='xa4'></b><b class='xa3'></b><b class='xa2'></b><b class='xa1'></b></td></tr></table>";					
					tmpString=tmpString+GradeStart+"<div class='GradeDivOn' onClick="+command+">"+GradeName+"</div>"+GradeEnd;
				}else{
					GradeStart="<table><tr><td><b class='xb1'></b><b class='xb2'></b><b class='xb3'></b><b class='xb4'></b>";
					GradeEnd="<b class='xb4'></b><b class='xb3'></b><b class='xb2'></b><b class='xb1'></b></td></tr></table>";					
					tmpString=tmpString+GradeStart+"<div class='GradeDiv' onClick="+command+">"+GradeName+"</div>"+GradeEnd;
				}
				
				
							
			}
			document.getElementById("GradeAreaTd").innerHTML=tmpString;
			
			//取班級
			tmp=XMLHttpReqDyna.responseXML.getElementsByTagName("ClassNo").length;
			tmpString="";
			var ClassStart="";
			var ClassEnd="";
			var ClassNo="";
			var ClassName="";
			var command="";			
			for(i=0; i<tmp; i++){				
				ClassNo=XMLHttpReqDyna.responseXML.getElementsByTagName("ClassNo")[i].firstChild.data;				
				command="      \"document.getElementById('ClassNo').value='"+ClassNo+"', getClass()\"       ";
				ClassName=XMLHttpReqDyna.responseXML.getElementsByTagName("ClassName")[i].firstChild.data;				
				
				if(ClassNo==document.getElementById("ClassNo").value){
					ClassStart="<table><tr><td><b class='xa1'></b><b class='xa2'></b><b class='xa3'></b><b class='xa4'></b>";
					ClassEnd="<b class='xa4'></b><b class='xa3'></b><b class='xa2'></b><b class='xa1'></b></td></tr></table>";
					tmpString=tmpString+ClassStart+"<div class='ClassDivOn' onClick="+command+">"+ClassName+"</div>"+ClassEnd;
				}else{					
					ClassStart="<table><tr><td><b class='xb1'></b><b class='xb2'></b><b class='xb3'></b><b class='xb4'></b>";
					ClassEnd="<b class='xb4'></b><b class='xb3'></b><b class='xb2'></b><b class='xb1'></b></td></tr></table>";
					tmpString=tmpString+ClassStart+"<div class='ClassDiv' onClick="+command+">"+ClassName+"</div>"+ClassEnd;
				}							
			}
			document.getElementById("ClassAreaTd").innerHTML=tmpString;
			document.getElementById("ClassNo").value="";							
			
		}			
				
 	}
}

function getClass(){	
	document.getElementById("classInCharge").value="";	
	var n = Math.floor(Math.random()*1000);
	var CampusNo=document.getElementById("CampusNo").value;
	var SchoolNo=document.getElementById("SchoolNo").value;
	var DeptNo=document.getElementById("DeptNo").value;
	var Grade=document.getElementById("Grade").value;
	var ClassNo=document.getElementById("ClassNo").value;
	document.getElementById("classInCharge").value=ClassNo;
	sendDyna(geturl+"?CampusNo="+CampusNo+"&SchoolNo="+SchoolNo+"&DeptNo="+DeptNo+"&Grade="+Grade+"&ClassNo="+ClassNo+"&"+n);
	
	if(document.getElementById("classInCharge").value!=""){
		//document.forms[0].submit();
		document.getElementById("myShow").style.display = 'inline';	
			
	}
}