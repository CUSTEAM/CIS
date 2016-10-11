<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
.t_area{
padding: 9px;
width:99%;
border: solid 1px #EbEbEb;
height:200px;
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
				<input type="button" class="gGreenSmall" value="套用" id="cp" 
				onMouseOver="showHelpMessage('套用後請點選右方儲存', 'inline', this.id)" 
				onMouseOut="showHelpMessage('', 'none', this.id)" 
				onClick="getIntro(document.getElementById('savedtimeoid').value);" />
				
				<input type="submit" name="method" 
				value="<bean:message key='SaveInt'/>" 
				id="SaveInt1" class="gSubmitSmall"
				onMouseOver="showHelpMessage('立即儲存', 'inline', this.id)" 
				onMouseOut="showHelpMessage('', 'none', this.id)">		
				<input type="button" value="返回列表" onClick="window.location='/CIS/Teacher/CourseInfo.do'" class="gCancelSmall">
				<input type="hidden" name="dtimeoid" value="${aDtime.Oid}" />				
				</td>
			</tr>
		</table>		
		</td>
	</tr>
</table>

<table width="99%" class="hairLineTable">	
	<tr>
		<td class="hairLineTdF">課程用書</td>
	</tr>
	<tr>
		<td class="hairLineTdF"><input type="text" class="t_text" name=book value="${aDtime.Introduction.book}" /></td>
	</tr>
</table>
<!-- Leo20120330 新增科目英文名稱欄位 -->
<table width="99%" class="hairLineTable">	
	<tr>
		<td class="hairLineTdF">科目英文名稱</td>
	</tr>
	<tr>
		<td class="hairLineTdF"><input type="text" class="t_text" name=CsEnglishName readonly value="${CsEnglishName}" /></td>
	</tr>
</table>

<table width="99%" class="hairLineTable">	
	<tr>
		<td width="50%" valign="top" class="hairLineTdF">中文課程簡介</td>
		
		<td width="50%" valign="top" class="hairLineTdF">英文課程簡介</td>
	</tr>
	
	<tr>
		<td width="50%" valign="top" class="hairLineTdF">	
		<textarea class="t_area" cols="20" name="chi" id="chi">${aDtime.Introduction.chi}</textarea>
		</td>
		
		<td width="50%" valign="top" class="hairLineTdF">
		<textarea class="t_area" cols="20" name="eng" id="eng">${aDtime.Introduction.eng}</textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2" class="hairLineTdF">
		<input type="submit" name="method" 
					value="<bean:message key='SaveInt'/>" 
					id="SaveInt" class="gSubmit"
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

function getIntro(soid) {
	sendDyna("/CIS/Print/Ajax/MyIntor.do?soid="+soid+"&"+Math.floor(Math.random()*999));
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
			//alert();
			document.getElementById("chi").value=(XMLHttpReqDyna.responseXML.getElementsByTagName("chi")[0].firstChild.data);
			document.getElementById("eng").value=(XMLHttpReqDyna.responseXML.getElementsByTagName("eng")[0].firstChild.data);
			document.getElementById("book").value=(XMLHttpReqDyna.responseXML.getElementsByTagName("book")[0].firstChild.data);
		}
	}	
}
</script>
