<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>

<table width="200" id="showTable" class="hairLineTable" style="display:none; position:absolute; right:20px; top:20px; filter:Alpha(opacity: 0.8);filter:alpha(opacity=80);-moz-opacity:0.8; z-index:32768;">
	<tr height="50">
		<td class="hairLineTdF" id="showScore">		
		</td>
	</tr>
	<tr>
		<td class="hairLineTdF">
		<input type="button" value="關閉" class="gCancel" onClick="showObj('showTable');"/>
		</td>
	</tr>
</table>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/EngRat" method="post" onsubmit="init('儲存中, 請稍後')">	
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/font.gif"></div>
		<div nowrap style="float:left;"><font class="gray_15"><bean:message key="EngRat.title"/></font></div>		
		</td>
	</tr>	
	<tr>
		<td>			
		<%@ include file="EngRat/help.jsp" %>		
		<%@ include file="EngRat/ListClass.jsp" %>		
		</td>
	</tr>
	<tr height="30">
	
		<td class="fullColorTable" align="center">
		<c:if test="${!empty engRats}">
		
		<INPUT type="submit" name="method" value="<bean:message key='Save' bundle="COU"/>" class="gSubmit">
		<INPUT type="button" value="說明" class="gCancel" onClick="showObj('help')">
		</c:if>
		
		
		</td>
	</tr>
	
</html:form>
</table>
<script>
function countScore(Oid){

	score=document.getElementById("score"+Oid).value;
	//score1=document.getElementById("score1"+Oid).value;
	//score2=document.getElementById("score2"+Oid).value;
	
	
	//線上
	onlineTmp=0;
	online=0;
	
	score11=document.getElementById("score11"+Oid).value;
	score12=document.getElementById("score12"+Oid).value;
	score13=document.getElementById("score13"+Oid).value;
	score14=document.getElementById("score14"+Oid).value;
	
	if(score11!=''){onlineTmp=onlineTmp+1; online=online+(score11/240)*100}
	if(score12!=''){onlineTmp=onlineTmp+1; online=online+(score12/240)*100}
	if(score13!=''){onlineTmp=onlineTmp+1; online=online+(score13/240)*100}
	if(score14!=''){onlineTmp=onlineTmp+1; online=online+(score14/240)*100}
	
	if(score11=='' && score12=='' && score13=='' && score14==''){
		document.getElementById("onlineAvg"+Oid).value='';
		onlineTmp=0;
		online=0;
	}
	
	if(online>0){
		document.getElementById("onlineAvg"+Oid).value=ForDight(online/onlineTmp, 1);
	}
	
	//平時
	normalTmp=0;
	normal=0;
	
	score01=document.getElementById("score01"+Oid).value;
	score02=document.getElementById("score02"+Oid).value;
	score03=document.getElementById("score03"+Oid).value;
	score04=document.getElementById("score04"+Oid).value;
	score05=document.getElementById("score05"+Oid).value;
	score06=document.getElementById("score06"+Oid).value;
	
	if(score01=='' && score02=='' && score03=='' && score04=='' && score05=='' && score06==''){
		normal='';
		score1='';
		document.getElementById("score1"+Oid).value='';
	}
	
	onlineAvg=document.getElementById("onlineAvg"+Oid).value;
	if(score01!=""){normalTmp=normalTmp+1; normal=normal+(score01*1)}
	if(score02!=""){normalTmp=normalTmp+1; normal=normal+(score02*1)}
	if(score03!=""){normalTmp=normalTmp+1; normal=normal+(score03*1)}
	if(score04!=""){normalTmp=normalTmp+1; normal=normal+(score04*1)}
	if(score05!=""){normalTmp=normalTmp+1; normal=normal+(score05*1)}
	if(score06!=""){normalTmp=normalTmp+1; normal=normal+(score06*1)}
	//if(onlineAvg!=""){normalTmp=normalTmp+1; normal=normal+(onlineAvg*1)}	
	if(onlineAvg!=""){
		//normalTmp=normalTmp+1; 
		normal=normal+(onlineAvg*onlineTmp)
	}
	
	
	
	
	if(normal>0){
		document.getElementById("score1"+Oid).value=ForDight(normal/(normalTmp+onlineTmp), 1);		
	}
	
	score2=0+document.getElementById("score2"+Oid).value;
	score1=0+document.getElementById("score1"+Oid).value;
	score3=0+document.getElementById("score3"+Oid).value;	
	
	//若有TOEIC
	if(document.getElementById("score15"+Oid).value!=""){
		
		var toeic=parseInt(document.getElementById("score15"+Oid).value);	
		
		
		var score15=parseInt(document.getElementById("score3"+Oid).value);	
		
		var realtoeic;
		
		
		if(toeic>=225){toeic=100}
		if(toeic>=210&&toeic<=224){realtoeic=96}
		if(toeic>=200&&toeic<=209){realtoeic=92}
		if(toeic>=190&&toeic<=199){realtoeic=88}
		if(toeic>=180&&toeic<=189){realtoeic=84}
		if(toeic>=170&&toeic<=179){realtoeic=80}
		if(toeic>=160&&toeic<=169){realtoeic=76}
		if(toeic>=150&&toeic<=159){realtoeic=72}
		if(toeic>=140&&toeic<=149){realtoeic=68}
		if(toeic>=130&&toeic<=139){realtoeic=64}
		if(toeic>=120&&toeic<=129){realtoeic=58}
		if(toeic>=110&&toeic<=119){realtoeic=52}
		if(toeic>=100&&toeic<=109){realtoeic=46}
		
		if(toeic>=0&&toeic<=99){realtoeic=40}
		
		
		
		
		
		document.getElementById('showTable').style.display="inline";
		document.getElementById('showTable').style.left=getLeft(document.getElementById("score15"+Oid))+5;	//y座標
 		document.getElementById('showTable').style.top=getTop(document.getElementById("score15"+Oid))-5;	//x座標		
		//score15=document.getElementById("score15"+Oid).value;		
		score3=(score3*1+realtoeic)/2
		showScore(realtoeic, score15, score3);
	}
	
	total=(score1*0.3)+(score2*0.3)+(score3*0.4);//總成績
	
	//若有補救
	//只要總成績及格不會因補救教學而不及格(0分除外)
	score17=document.getElementById("score17"+Oid).value;
	total1=total;
	
	if(score17!=''){
		//alert(score17);
		if(score17=='0'){
		total=total*0.85;
			}else{
				
				if(total>=60){
					if( (total*0.85)+(score17*1)<60 ){
						total=60;
					}else{
						total=(total*0.85)+(score17*1);
					}		
				}	
			}
	
	}else{
		//alert(total1);
		total=total1;
	}
	
	
	//若有活動
	score18=document.getElementById("score18"+Oid).value;
	if(score18!=''){
		total=total+score18*1;
	}
	
	//若總分大於100算100
	if(total>100){
		total=100;
	}
	
	document.getElementById("score"+Oid).value=ForDight(total, 1);
	
}

//變色
function chengColor(id, Oid){
	
	//for(var i=0; i<document.getElementsByName("onlineTd"+Oid).length; i++){
		document.getElementById("onlineTd"+Oid).className="fullColorTableSubF";
		document.getElementById("dayTd"+Oid).className="fullColorTableSubF";
		//document.getElementById("endayTd"+Oid).className="fullColorTableSubF";
		//document.getElementById("spTd"+Oid).className="fullColorTableSubF";
		
		document.getElementById(id).className="fullColorTableSub";
	//}
}

//開啟&收合 線上、平時
function changEdit(name, Oid){
	for(var i=0; i<document.getElementsByName("online"+Oid).length; i++){
	
		document.getElementsByName("online"+Oid)[i].style.display="none";
		document.getElementsByName("day"+Oid)[i].style.display="none";
		//document.getElementsByName("enday"+Oid)[i].style.display="none";
		//document.getElementsByName("special"+Oid)[i].style.display="none";
		
		document.getElementsByName(name)[i].style.display="inline";
	}
}

//開啟&收合 課程
function showInfo(id){
	if(document.getElementById(id).style.display=='none'){
		document.getElementById(id).style.display='inline';
		}else{
			document.getElementById(id).style.display='none';
		}
	}

//四捨五入至小數第n位
function ForDight(Dight,n){
	try{
		Dight = Math.round (Dight*Math.pow(10,n))/Math.pow(10,n);
		return Dight;
	}catch(e){
		return "搞錯了笨蛋";
	}
} 
</script>

<script>
function getOnlines(id){
if(document.getElementById(id).value>(240*1))
document.getElementById(id).value='240';
}

function getUsual(id){
if(document.getElementById(id).value>(100*1))
document.getElementById(id).value='100';
}







function countScoreUsual(Oid){

	score=document.getElementById("score"+Oid).value;
	//score1=document.getElementById("score1"+Oid).value;
	//score2=document.getElementById("score2"+Oid).value;
	score3=document.getElementById("score3"+Oid).value;
	
	//線上
	onlineTmp=0;
	online=0;
	
	score11=document.getElementById("score11"+Oid).value;
	score12=document.getElementById("score12"+Oid).value;
	score13=document.getElementById("score13"+Oid).value;
	score14=document.getElementById("score14"+Oid).value;
	
	if(score11!=''){onlineTmp=onlineTmp+1; online=online+(score11/240)*100}
	if(score12!=''){onlineTmp=onlineTmp+1; online=online+(score12/240)*100}
	if(score13!=''){onlineTmp=onlineTmp+1; online=online+(score13/240)*100}
	if(score14!=''){onlineTmp=onlineTmp+1; online=online+(score14/240)*100}
	
	
	
	if(online>0){
		document.getElementById("onlineAvg"+Oid).value=ForDight(online/onlineTmp, 1);
	}
	
	//平時
	/*
	normalTmp=0;
	normal=0;
	
	score01=document.getElementById("score01"+Oid).value;
	score02=document.getElementById("score02"+Oid).value;
	score03=document.getElementById("score03"+Oid).value;
	score04=document.getElementById("score04"+Oid).value;
	score05=document.getElementById("score05"+Oid).value;
	score06=document.getElementById("score06"+Oid).value;
	
	onlineAvg=document.getElementById("onlineAvg"+Oid).value;
	
	if(score01!=""){normalTmp=normalTmp+1; normal=normal+(score01*1)}
	if(score02!=""){normalTmp=normalTmp+1; normal=normal+(score02*1)}
	if(score03!=""){normalTmp=normalTmp+1; normal=normal+(score03*1)}
	if(score04!=""){normalTmp=normalTmp+1; normal=normal+(score04*1)}
	if(score05!=""){normalTmp=normalTmp+1; normal=normal+(score05*1)}
	if(score06!=""){normalTmp=normalTmp+1; normal=normal+(score06*1)}
	//if(onlineAvg!=""){normalTmp=normalTmp+1; normal=normal+(onlineAvg*1)}	
	if(onlineAvg!=""){
		//normalTmp=normalTmp+1; 
		normal=normal+(onlineAvg*onlineTmp)
	}
	
	if(normal>0){
		document.getElementById("score1"+Oid).value=ForDight(normal/(normalTmp+onlineTmp), 1);		
	}
	*/
	
	score2=0+document.getElementById("score2"+Oid).value;
	score1=0+document.getElementById("score1"+Oid).value;
	
	//若有TOEIC
	if(document.getElementById("score15"+Oid).value!=""){
		score15=document.getElementById("score15"+Oid).value;		
		score3=(score3*1+score15*1)/2
		
		showScore(score15, score3);
	}
	
	
	total=(score1*0.3)+(score2*0.3)+(score3*0.4);//總成績
	
	//若有補救
	//只要總成績及格不會因補救教學而不及格(0分除外)
	var total1=total;
	score17=document.getElementById("score17"+Oid).value;
	if(score17!=''){
	
		if(score17=='0'){
		total=total*0.85;
			}else{
				
				if(total>=60){
					if( (total*0.85)+(score17*1)<60 ){
						total=60;
					}else{
						total=(total*0.85)+(score17*1);
					}		
				}
			
			}
	}else{
		total=total1;
	}	
	
	//若有活動
	score18=document.getElementById("score18"+Oid).value;
	if(score18!=''){
		total=total+score18*1;
	}
	
	//若總分大於100算100
	if(total>100){
		total=100;
	}
	
	document.getElementById("score"+Oid).value=ForDight(total, 1);
	
}

function showScore(toeic, score15, score3){
	document.getElementById("showScore").innerHTML="TOEIC換算後為: "+toeic+"<br>校內期末考成績為: "+score15+"<br>因此以期末考成績以: "+score3+"<br>進行總成績40%運算";
}
</script>