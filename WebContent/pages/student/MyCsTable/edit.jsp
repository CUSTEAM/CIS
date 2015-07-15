<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="99%" class="hairLineTable">
	<tr>
		<td class="hairLineTdF"></td>	
		<td class="hairLineTdF" width="14%" align="center">星期一</td>
		<td class="hairLineTdF" width="14%" align="center">星期二</td>
		<td class="hairLineTdF" width="14%" align="center">星期三</td>
		<td class="hairLineTdF" width="14%" align="center">星期四</td>
		<td class="hairLineTdF" width="14%" align="center">星期五</td>
		<td class="hairLineTdF" width="14%" align="center">星期六</td>
		<td class="hairLineTdF" width="14%" align="center">星期日</td>
	</tr>
	
	<c:forEach begin="1" end="14" var="c">
	
	<c:if test="${c==11}">
	<tr>
		<td class="hairLineTdF">夜</td>	
		<td class="hairLineTdF" width="14%" align="center">星期一</td>
		<td class="hairLineTdF" width="14%" align="center">星期二</td>
		<td class="hairLineTdF" width="14%" align="center">星期三</td>
		<td class="hairLineTdF" width="14%" align="center">星期四</td>
		<td class="hairLineTdF" width="14%" align="center">星期五</td>
		<td class="hairLineTdF" width="14%" align="center">星期六</td>
		<td class="hairLineTdF" width="14%" align="center">星期日</td>
	</tr>
	</c:if>
	
	<tr height="75">
		<td class="hairLineTdF">${c}</td>		
		<c:forEach begin="1" end="7" var="w">		
		<td class="hairLineTdF" style="font-size:14px;" valign="middle">			
			<div style="cursor:pointer; text-align:center;" id="td${w}${c}"onClick="setCs(this.id, '${w}', '${c}')">
			<table><tr><td><img src="images/16-em-plus.png" /></td><td>加選</td></tr></table>
			</div>				
			<c:forEach items="${allClass}" var="a">				
			<c:if test="${a.week==w && (c>=a.begin && c<=a.end)}">
			<script>showObj("td${w}${c}");</script>
			<div id="a${w}${c}" style="cursor:pointer; width:100%;">
			${a.chi_name}${a.opt}<br>${a.cname}老師<br>${a.place}&nbsp;<a href="/CIS/Student/MyCsTable.do?delOid=${a.dtOid}">退選</a>			
			</div>			
			</c:if>			
			</c:forEach>
		</td>		
		</c:forEach>
	</tr>
	</c:forEach>
</table>

<div class="ds_box" id="miniForm"  style="display:none; padding:5px; width:1000px;">
<table><tr><td><input type="button" class="gCancel" value="關閉" onClick="closeMiniForm();"/></td></tr></table>

<div id="dinfo">
<img src="images/indicator/loading2.gif"/>
</div>

<input type="hidden" name="DtimeOid" id="choseOid">
<input type="hidden" name="week" id="week">
<input type="hidden" name="begin" id="begin">
<input type="hidden" name="end" id="end">
<input type='submit' name='method' value=<bean:message key='AddCourse'/> id="AddCourse" style="display:none;"/>
</div>

<script>
var objectId="dinfo";//容器物件

function setRange(begin, end, week){
	document.getElementById("begin").value=begin;
	document.getElementById("end").value=end;
	document.getElementById("week").value=week;
}

function closeMiniForm(){	
	document.getElementById("loadMsg").style.display="none";
	document.getElementById('miniForm').style.display="none";
}

//加選
function setCs(id, week, begin){
	showClass();		
	sendDyna("/CIS/Print/MyCourse.do?term=${schedule.term}&level=${schedule.level}&week="+week+"&begin="+begin+"&"+Math.floor(Math.random()*999));
	document.getElementById('miniForm').style.left=100;//y座標
	document.getElementById('miniForm').style.top=getTop(document.getElementById(id));	//x座標
	//document.getElementById('loadMsg').style.height=(document.getElementById('loadMsg').style.height+document.getElementById("dinfo").style.height);
}

//查課
function searchCs(term, name, id){
	showClass();		
	sendDyna("/CIS/Print/MyCourseSearh.do?level=${schedule.level}&term="+term+"&name="+encodeURIComponent(name)+"&"+Math.floor(Math.random()*999));
	document.getElementById('miniForm').style.left=10;//y座標
	document.getElementById('miniForm').style.top=getTop(document.getElementById(id));	//x座標
}

function showClass(){
	document.getElementById("dinfo").innerHTML="<img src='images/indicator/loading2.gif'/>";
	
	//座標	
	if (getBrowser()=='IE') {
		var scrollHeight=document.body.scrollHeight;
		var clientHeight=document.body.clientHeight;
		
		if (scrollHeight>clientHeight){
			document.getElementById('loadMsg').style.height=scrollHeight;// IE
		}else{
			document.getElementById('loadMsg').style.height=clientHeight;// IE
		}}else{		
		document.getElementById('loadMsg').style.height=document.body.scrollHeight;// FF, ns
	}
	
	document.getElementById('loadMsg').style.width=document.body.scrollWidth+1024;
	document.getElementById('loadMsg').style.height=document.body.scrollHeight+8192;
	document.getElementById('loadMsg').style.display="inline";
	document.getElementById('miniForm').style.display="inline";
	document.getElementById(objectId).style.display="inline";
}

//ajax
var XMLHttpReqDyna;
try{XMLHttpReqDyna=new XMLHttpRequest();}catch(e){XMLHttpReqDyna=false;}
var XMLHttpRequest=null;

	
function createXMLHttpRequestDyna(){	
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

//加選查詢連線
function sendDyna(url){
	createXMLHttpRequestDyna();
	XMLHttpReqDyna.open("GET",url,true);
	XMLHttpReqDyna.onreadystatechange=proceDyna;
	XMLHttpReqDyna.send(null);
}

//取值
function proceDyna(){
 		
	if(XMLHttpReqDyna.readyState==4){
		if(XMLHttpReqDyna.status==200){
			
			
			//var week0, week1, begin0, begin1, end0, end1;
			if(XMLHttpReqDyna.responseXML.getElementsByTagName("error").length<1){
				str="<table class='hairLineTable' width='99%'><tr>"+
				"<td class='hairLineTdF' nowrap>選擇</td>"+				
				"<td class='hairLineTdF' nowrap>課程編號</td>"+
				"<td class='hairLineTdF' nowrap>節次</td>"+		
				"<td class='hairLineTdF' nowrap>開課班級</td>"+
				"<td class='hairLineTdF' nowrap>課程名稱</td>"+			
				"<td class='hairLineTdF' nowrap>授課教師</td>"+	
				"<td class='hairLineTdF' nowrap>已選/上限</td>"+
				"<td class='hairLineTdF' nowrap>選別</td>"+
				"<td class='hairLineTdF' nowrap>學分</td>"+
				"<td class='hairLineTdF' nowrap>時數</td>"+
				"<td class='hairLineTdF' nowrap>型態</td>"+
				"<td class='hairLineTdF' nowrap style='font-size:12px;'>大綱與簡介</td>"+
				"</tr>";
				
				for(i=0; i<XMLHttpReqDyna.responseXML.getElementsByTagName("Oid").length; i++){
					
					if(XMLHttpReqDyna.responseXML.getElementsByTagName("week1")[i].firstChild.data!="null"){						
						week0=(XMLHttpReqDyna.responseXML.getElementsByTagName("week0")[i].firstChild.data);
						begin0=(XMLHttpReqDyna.responseXML.getElementsByTagName("begin0")[i].firstChild.data);
						end0=(XMLHttpReqDyna.responseXML.getElementsByTagName("end0")[i].firstChild.data);
						dclass="<font size=-2>週"+chWeek(week0)+"第"+begin0+"節~"+end0+"節<br>";
						week1=(XMLHttpReqDyna.responseXML.getElementsByTagName("week1")[i].firstChild.data);
						begin1=(XMLHttpReqDyna.responseXML.getElementsByTagName("begin1")[i].firstChild.data);
						end1=(XMLHttpReqDyna.responseXML.getElementsByTagName("end1")[i].firstChild.data);
						dclass=dclass+"週"+chWeek(week1)+"第"+begin1+"節~"+end1+"節</font>";
						
					}else{						
						week0=(XMLHttpReqDyna.responseXML.getElementsByTagName("week0")[i].firstChild.data);
						begin0=(XMLHttpReqDyna.responseXML.getElementsByTagName("begin0")[i].firstChild.data);
						end0=(XMLHttpReqDyna.responseXML.getElementsByTagName("end0")[i].firstChild.data);
						dclass="週"+chWeek(week0)+"第"+begin0+"節~"+end0+"節";	
					}					
					
					try{str=str+"<tr>"+
					"<td class='hairLineTdF'><input type='button' value='加選' class='gSubmitSmall' onClick='clickAdd()' onMouseOver='addtime("+(XMLHttpReqDyna.responseXML.getElementsByTagName("Oid")[i].firstChild.data)+ "), setRange("+(XMLHttpReqDyna.responseXML.getElementsByTagName("begin")[i].firstChild.data)+
					", "+(XMLHttpReqDyna.responseXML.getElementsByTagName("end")[i].firstChild.data)+", "+(XMLHttpReqDyna.responseXML.getElementsByTagName("week")[i].firstChild.data)+")' /></td>"+					
					"<td class='hairLineTdF' nowrap>"+(XMLHttpReqDyna.responseXML.getElementsByTagName("Oid")[i].firstChild.data)+"</td>"+
					"<td class='hairLineTdF' nowrap style='font-size:12px;'>"+dclass+"</td>"+
					"<td class='hairLineTdF' nowrap style='font-size:12px;'>"+(XMLHttpReqDyna.responseXML.getElementsByTagName("ClassName")[i].firstChild.data)+"</td>"+
					"<td class='hairLineTdF' nowrap>"+(XMLHttpReqDyna.responseXML.getElementsByTagName("chi_name")[i].firstChild.data)+"</td>"+				
					"<td class='hairLineTdF' nowrap>"+(XMLHttpReqDyna.responseXML.getElementsByTagName("cname")[i].firstChild.data)+"</td>"+
					"<td class='hairLineTdF' nowrap align='right'>"+(XMLHttpReqDyna.responseXML.getElementsByTagName("limit")[i].firstChild.data)+"</td>"+				
					"<td class='hairLineTdF' nowrap>"+(XMLHttpReqDyna.responseXML.getElementsByTagName("opt")[i].firstChild.data)+"</td>"+
					"<td class='hairLineTdF' nowrap align='center'>"+(XMLHttpReqDyna.responseXML.getElementsByTagName("credit")[i].firstChild.data)+"</td>"+
					"<td class='hairLineTdF' nowrap align='center'>"+(XMLHttpReqDyna.responseXML.getElementsByTagName("thour")[i].firstChild.data)+"</td>"+
					"<td class='hairLineTdF' nowrap>"+(XMLHttpReqDyna.responseXML.getElementsByTagName("elearning")[i].firstChild.data)+"</td>"+					
					"<td class='hairLineTdF' nowrap style='font-size:12px;'><a href='/CIS/Print/teacher/SylDoc.do?Oid="+(XMLHttpReqDyna.responseXML.getElementsByTagName("Oid")[i].firstChild.data)+"'>大綱</a>"+
					" - <a href='/CIS/Print/teacher/IntorDoc.do?Oid="+(XMLHttpReqDyna.responseXML.getElementsByTagName("Oid")[i].firstChild.data)+"'>簡介</a></td>"+				
					"</tr>";}catch(e){}
				}
				str=str+"</table>";
			}else{
				str="<table class='hairLineTable' width='99%'><tr><td class='hairLineTdF'>無適合課程</td></tr></table>";
			}
			
			document.getElementById(objectId).innerHTML=str;			
		}
	}	
}

function addtime(Oid){
	document.getElementById("choseOid").value=Oid;
}

function chWeek(week){
	if(week=='1'){return "一"}
	if(week=='2'){return "二"}
	if(week=='3'){return "三"}
	if(week=='4'){return "四"}
	if(week=='5'){return "五"}
	if(week=='6'){return "六"}
	if(week=='7'){return "日"}
}

function clickAdd(){
	//延遲0~300ms防止多人同時
	setTimeout("document.getElementById('AddCourse').click();", Math.floor(Math.random()*(300-1)));	
	//document.getElementById("AddCourse").click();
}

//禁止使用enter送出表單
document.onkeydown = function(event) {
    var target, code, tag;
    if (!event) {
        event = window.event; //爛IE
        target = event.srcElement;
        code = event.keyCode;
        if (code == 13) {
            tag = target.tagName;
            if (tag == "TEXTAREA") { return true; }
            else { return false; }
        }
    }
    else {
        target = event.target; //w3c標準瀏覽器
        code = event.keyCode;
        if (code == 13) {
            tag = target.tagName;
            if (tag == "INPUT") { return false; }
            else { return true; } 
        }
    }
};
</script>