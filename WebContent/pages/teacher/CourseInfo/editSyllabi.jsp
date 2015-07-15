<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
.t_area{
padding: 9px;
width:99%;
border: solid 1px #EfEfEf;
height:100px;
overflow-y:visible;
outline: 0;
font: normal 16px/100% Verdana, Tahoma, sans-serif;
line-height:150%;
background: #FFFFFF;

}
.t_text{
padding: 9px;
width:99%;
border: solid 1px #EbEbEb;
overflow-y:visible;
outline: 0;
font: normal 16px/100% Verdana, Tahoma, sans-serif;
line-height:150%;
background: #FFFFFF;
}
</style> 
<table width="99%" class="hairLineTable">	
	<tr>
		<td class="hairLineTdF">正在編輯 ${bDtime.ClassName} - ${bDtime.chi_name}</td>
	</tr>
</table>

<table width="99%" class="hairLineTable">
	<tr>
		<td colspan="2" class="hairLineTdF" nowrap>
		
		
		
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td>				
				<div class="selectOnFocus">
				<select style="font-size:18px;" name="savedtimeoid" id="savedtimeoid">
					<option>選擇歷年資料範本</option>
					<c:forEach items="${old}" var="o">
					<option value="${o.Oid}">${o.school_year}學年第${o.school_term}學期 ${o.chi_name} - ${o.ClassName}</option>
					</c:forEach>
				</select>
				</div>
				</td>
				<td nowrap>
				&nbsp;
				<input type="hidden" name="dtimeoid" value="${bDtime.Oid}" />
				<input type="button" class="gGreenSmall" value="套用" 
				id="cp1" class="gSubmitSmall" onMouseOver="showHelpMessage('套用後請點擊右方儲存', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"
				onClick="getSyl(document.getElementById('savedtimeoid').value);" />
				<input type="submit" name="method" 
					value="<bean:message key='SaveSyl'/>" 
					id="SaveSyl3" class="gSubmitSmall"
					onMouseOver="showHelpMessage('立即儲存', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">		
				<input type="button" value="返回列表" onClick="window.location='/CIS/Teacher/CourseInfo.do'" class="gCancelSmall">
				</td>
			</tr>
		</table>
		
		
		
		
		
		
		</td>
	</tr>
</table>



<table width="99%" class="hairLineTable">	
	<tr>
		<td class="hairLineTdF">課程目標</td>
	</tr>
	<tr>
		<td class="hairLineTdF"><textarea class="t_area" name="objectives" id="obj">${bDtime.Syllabi.obj}</textarea></td>
	</tr>
</table>

<table width="99%" class="hairLineTable">	
	<tr>
		<td class="hairLineTdF">課程綱要</td>
	</tr>
	<tr>
		<td class="hairLineTdF"><textarea class="t_area" name="syllabus" id="syl">${bDtime.Syllabi.syl}</textarea></td>
	</tr>
</table>

<table width="99%" class="hairLineTable">	
	<tr>
		<td class="hairLineTdF">先修科目/先備能力</td>
	</tr>
	<tr>
		<td class="hairLineTdF"><textarea class="t_area" name="prerequisites" id="pre">${bDtime.Syllabi.pre}</textarea></td>
	</tr>
</table>

<table width="99%" class="hairLineTable">
	<tr>
		<td class="hairLineTdF">
		<input type="submit" name="method" 
					value="<bean:message key='SaveSyl'/>" 
					id="SaveSyl1" class="gSubmit"
					onMouseOver="showHelpMessage('立即儲存', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
		
		
		<input type="button" value="返回列表" onClick="window.location='/CIS/Teacher/CourseInfo.do'" class="gCancel">
		
		</td>
	</tr>
</table>



<div id="oldsyl">
<table width="99%" class="hairLineTable">	
	<tr>
		<td class="hairLineTdF" nowrap>週次</td>
		<td class="hairLineTdF" nowrap>章節主題</td>
		<td class="hairLineTdF" width="100%">內容綱要</td>
		<td class="hairLineTdF" nowrap>時數</td>
	</tr>
	
	<c:forEach items="${bDtime.Syllabi_sub}" var="s">
	<tr>
		<td class="hairLineTdF">		
		<input class="t_text" style="width:100px;" type="text" name="week" value="${s.week}"/ >		
		</td>
		<td class="hairLineTdF">		
		<input class="t_text" style="width:100px;" type="text" name="topic" value="${s.topic}"/ >		
		</td>
		<td class="hairLineTdF">		
		<input class="t_text" style="width:100%;" type="text" name="content" value="${s.content}"/ >		
		</td>
		<td class="hairLineTdF">		
		<input class="t_text" style="width:100px;" type="text" name="hours" value="${s.hours}"/ >		
		</td>
		
	</tr>
	</c:forEach>
</table>
</div>

<div id="newsyl"></div>

<table width="99%" class="hairLineTable">	
	<tr>
		<td class="hairLineTdF">新增週次</td>
		<td class="hairLineTdF">新增主題</td>
		<td class="hairLineTdF" width="100%">新增內容綱要</td>
		<td class="hairLineTdF">新增時數</td>
	</tr>
	<tr>
		<td class="hairLineTdF">		
		<input class="t_text" style="width:100px;" type="text" name="week" value="${s.week}"/ >		
		</td>
		<td class="hairLineTdF">		
		<input class="t_text" style="width:100px;" type="text" name="topic" value="${s.topic}"/ >		
		</td>
		<td class="hairLineTdF">		
		<input class="t_text" style="width:100%;" type="text" name="content" value="${s.content}"/ >		
		</td>
		<td class="hairLineTdF">		
		<input class="t_text" style="width:100px;" type="text" name="hours" value="${s.hours}"/ >		
		</td>
		
	</tr>
</table>

<table width="99%" class="hairLineTable">
	<tr>
		<td class="hairLineTdF" colspan="4">
		<input type="submit" name="method" 
					value="<bean:message key='SaveSyl'/>" 
					id="SaveSyl2" class="gSubmit"
					onMouseOver="showHelpMessage('立即儲存', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
		
		
		<input type="button" value="返回列表" onClick="window.location='/CIS/Teacher/CourseInfo.do'" class="gCancel">
		
		</td>
	</tr>
</table>



<script>
var XMLHttpReqDyna;
try{XMLHttpReqDyna=new XMLHttpRequest();}catch(e){XMLHttpReqDyna=false;}
var XMLHttpRequest=null;

function getSyl(soid) {
	sendDyna("/CIS/Print/Ajax/MySyl.do?soid="+soid+"&"+Math.floor(Math.random()*999));
	setAll("week");
	setAll("topic");
	setAll("content");
	setAll("hours");
	document.getElementById("oldsyl").style.display="none";
}

function setAll(name){	
	
	var a=document.getElementsByName(name);
	for(i=0; i<a.length; i++){
		a[i].disabled=true;
	}
}

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
			document.getElementById("obj").value=(XMLHttpReqDyna.responseXML.getElementsByTagName("obj")[0].firstChild.data);
			document.getElementById("syl").value=(XMLHttpReqDyna.responseXML.getElementsByTagName("syl")[0].firstChild.data);
			document.getElementById("pre").value=(XMLHttpReqDyna.responseXML.getElementsByTagName("pre")[0].firstChild.data);			
			
			tmp=XMLHttpReqDyna.responseXML.getElementsByTagName("content").length;
			var tmpString="<table width='99%' class='hairLineTable'><tr><td class='hairLineTdF'>週次</td><td class='hairLineTdF'>章節主題</td>"+
			"<td class='hairLineTdF' width='100%'>內容綱要</td><td class='hairLineTdF'>時數</td></tr>" 
			
			
			if(tmp>0){
				for(i=0; i<tmp; i++){
					
					try{				
					tmpString=tmpString+"<tr><td class='hairLineTdF'><input class='t_text' style='width:100px;' type='text' id='week"+i+"' name='week' value='"+
					(XMLHttpReqDyna.responseXML.getElementsByTagName("week")[i].firstChild.data)+"'/></td>"
					}catch(e){"<td class='hairLineTdF'><input class='t_text' style='width:100px;' type='topic' name='week' value=''/></td>";}
					
					try{
					tmpString=tmpString+"<td class='hairLineTdF'><input class='t_text' style='width:100px;' type='topic' name='topic' value='"+
					(XMLHttpReqDyna.responseXML.getElementsByTagName("topic")[i].firstChild.data)+"'/></td>"
					}catch(e){"<td class='hairLineTdF'><input class='t_text' style='width:100px;' type='topic' name='topic' value=''/></td>";} 
					
					try{
					tmpString=tmpString+"<td class='hairLineTdF'><input class='t_text' style='width:100%;' type='text' name='content' value='"+
					(XMLHttpReqDyna.responseXML.getElementsByTagName("content")[i].firstChild.data)+"'/></td>"
					}catch(e){"<td class='hairLineTdF'><input class='t_text' style='width:100%;' type='topic' name='content' value=''/></td>";} 
					
					
					try{
					tmpString=tmpString+"<td class='hairLineTdF'><input class='t_text' style='width:100px;' type='text' name='hours' value='"+
					(XMLHttpReqDyna.responseXML.getElementsByTagName("hours")[i].firstChild.data)+"'/></td></tr>"				
					}catch(e){"<td class='hairLineTdF'><input class='t_text' style='width:100px;' type='topic' name='hours' value=''/></td><tr>";} 
				}
			}else{
			
			
			}
			
			tmpString=tmpString+"</table>";			
			
			/*
			//document.createRange在IE中無法作用
			rng = document.createRange(); 
			el = document.getElementById("newsyl"); 
			rng.setStartBefore(el); 
			htmlFrag = rng.createContextualFragment(tmpString); 
			while(el.hasChildNodes()) //清除原有内容，加入新内容 
			el.removeChild(el.lastChild); 
			el.appendChild(htmlFrag);			
			*/
			
			
			document.getElementById("newsyl").innerHTML="";
			
			document.getElementById("newsyl").innerHTML=tmpString;
			//replaceHtml("newsyl", tmpString);
		}
	}	
}


</script>

