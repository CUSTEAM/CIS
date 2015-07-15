<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="/taglibs.jsp"%>
<table cellspacing="0" cellpadding="0" width="100%">	
	<tr>
		<td>
		<table class="hairLineTable" width="99%">
	    	<tr>
	    		<td class="hairLineTdF" nowrap>對象</td>
	    		<td class="hairLineTdF" nowrap>
	    		<select style="font-size:18px;" onChange="if(this.value=='e'){searchOpt('emplSearch')}else{searchOpt('stmdSearch')}">
	    			<option value="e">教職員</option>
	    			<option value="s">學生</option>
	    		</select>
	    		</td>
	    		<td class="hairLineTdF" width="100%" nowrap>尋找教職員或學生</td>
	    	</tr>
	    </table>		
		</td>
	</tr>	
	<tr>
		<td>		
		<%@ include file="searchEmpl.jsp"%>
		</td>
	</tr>
		<td>		
		<%@ include file="searchStmd.jsp"%><br>
		</td>
	</tr>
	</tr>
		<td id="editor" style="display:none;">		
		<%@ include file="edit.jsp"%><br>
		</td>
	</tr>
</table>

<table width="100%" id="cont" style="display:none; position:absolute; right:0px; top:150px; z-index:32768;">
	<tr>
		<td width="33%"></td>
		<td width="100%"><div id="members"></div></td>
		<td width="33%"></td>
	</tr>
</table>

<script>
function searchOpt(input){	
	document.getElementById("emplSearch").style.display="none";
	document.getElementById("stmdSearch").style.display="none";
	document.getElementById(input).style.display="inline";
}

//ajax start
function getMB() {	
	group=document.getElementById("group").value;
	myGroup=document.getElementById("myGroup").value;	
	cname=encodeURIComponent(document.getElementById("cname").value);
	sname=encodeURIComponent(document.getElementById("sname").value);
	unit=document.getElementById("unit").value;
	category=document.getElementById("category").value;
	Tutor=document.getElementById("Tutor").value;
	pcode=document.getElementById("pcode").value;
	Director=document.getElementById("Director").value;	
	sendDyna('/CIS/shMember?group='+group+'&myGroup='+myGroup+
			'&cname='+cname+'&sname='+sname+'&unit='+unit+
			'&category='+category+'&Tutor='+Tutor+'&pcode='+
			pcode+'&Director='+Director+'&type=e&'+
			Math.floor(Math.random()*999));
	document.getElementById("editor").style.display='inline';
}

function getST() {
	Cidno=document.getElementById("Cidno").value;
	Sidno=document.getElementById("Sidno").value;
	Didno=document.getElementById("Didno").value;
	Grade=document.getElementById("Grade").value;
	ClassNo=document.getElementById("ClassNo").value;	
	stNameNo=document.getElementById("stNameNo").value;
	
	sendDyna('/CIS/shMember?Cidno='+Cidno+'&Sidno='+Sidno+
	'&Didno='+Didno+'&Grade='+Grade+'&ClassNo='+ClassNo+
	'&stNameNo='+stNameNo+'&type=s&'+
	Math.floor(Math.random()*999));
	document.getElementById("editor").style.display='inline';	
}

try{
	var XMLHttpReqDyna=new XMLHttpRequest();
}catch(e){
	var XMLHttpReqDyna=false;
}  		

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

var tmp, tmpString, name, email;
var names, emails;
/**
 * 連線成功開始取值並顯示
 */
function proceDyna(){


	if(XMLHttpReqDyna.readyState==4){
		
		if(XMLHttpReqDyna.status==200){
			
			names=XMLHttpReqDyna.responseXML.getElementsByTagName("name");
			emails=XMLHttpReqDyna.responseXML.getElementsByTagName("email");
			unames=XMLHttpReqDyna.responseXML.getElementsByTagName("uname");
			
			tmp=XMLHttpReqDyna.responseXML.getElementsByTagName("name").length;
			tmpString='<table class="hairLineTable" align="left"><tr><td class="hairLineTdF">'+
			'<input type="button" class="gGreen" value="全部加入" onClick="addAllMembers();"/>&nbsp;'+
			'<input type="button" class="gSubmit" value="關閉選單" onClick="unCoverScr(); showObj(\'cont\');"/></td></tr>'; 				
			 				
			
			
			for(i=0; i<tmp; i++){
				try{
					name=names[i].firstChild.data;
					email=emails[i].firstChild.data;
					uname=unames[i].firstChild.data;					
					tmpString=tmpString+'<tr><td class="hairLineTdF" style="cursor:pointer"><div onClick="addMembers(\''+name+'\',\''+email+'\'); this.style.display=\'none\'">'+
					name+","+email+", "+uname+'</div></td></tr>';
				}catch(e){
					
				}
			}
			tmpString=tmpString+'</table>';
			document.getElementById('members').innerHTML=tmpString;			
			document.getElementById('members').style.display = "inline";
			showObj("cont");
			coverSer();
		
		}else{
			window.alert("shit! AJAX connect error!"+" XMLHttpReq.readyState="+XMLHttpReq.readyState+" XMLHttpReqDyna.status="+XMLHttpReqDyna.status);
		}
	}
}

function addMembers(name, email){
	document.getElementById("emailist").value=document.getElementById("emailist").value+name+","+email+"; ";
}

function addAllMembers(){
	for(i=0; i<names.length; i++){document.getElementById("emailist").value=
		document.getElementById("emailist").value+names[i].firstChild.data+","+emails[i].firstChild.data+"; ";
	}
	unCoverScr(); 
	showObj("cont");
}

</script>