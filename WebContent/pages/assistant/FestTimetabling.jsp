<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>



<table width="100%" cellpadding="0" cellspacing="0" border="0">
<!-- 標題 start -->
<html:form action="/DepAssistant/FestTimetabling" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/application_view_tile.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">快速排課</font></div>		
		</td>
	</tr>	
	<tr>
		<td>
		<%@ include file="FastTimetabling/search.jsp"%>		
		</td>
	</tr>
	
	<c:if test="${allClass!=null}">
	<tr>
		<td>
		<%@ include file="FastTimetabling/edit.jsp"%>	
		</td>
	</tr>
	</c:if>
	
</html:form>
</table>

<table id="MapMessage" class="ds_box" style="display:none" style="position:inside; left:170px;  top:10px; z-index:32768">
	<tr>
		<td id="MapInfo">
		
		
		</td>
	</tr>
</table>

<script>
function getMapTop(ed) {
	var tmp = ed.offsetTop;
	ed = ed.offsetParent
	while(ed) {
		tmp += ed.offsetTop;
		ed = ed.offsetParent;
	}
	return tmp+24;
}
function techTable(Oid, id, type, year, term){

	document.getElementById('MapMessage').style.top=getTop(document.getElementById(id))-5;	//x座標
	document.getElementById('MapMessage').style.left=100;	//x座標
	showObj('MapMessage');
	document.getElementById('MapInfo').innerHTML="";
	
	
	
	if(type=='room'){
		document.getElementById('MapInfo').innerHTML='<table><tr><td align=left><input type=button class=gSubmit value=關閉課表 onClick=closeTimeTable(); /></td></tr><tr><td>'+
		'<iframe src=/CIS/ReserveTechTable?year='+year+'&term='+term+'&room_id='+Oid+' width=780 height=960 marginwidth=0 marginheight=0 hspace=0 vspace=0 frameborder=0 scrolling=no/>'+
		'</td></tr></table>';
	}
	
	if(type=='tech'){
		document.getElementById('MapInfo').innerHTML='<table><tr><td align=left><input type=button class=gSubmit value=關閉課表 onClick=closeTimeTable(); /></td></tr><tr><td>'+
		'<iframe src=/CIS/ReserveTechTable?year='+year+'&term='+term+'&getTt='+Oid+' width=780 height=960 marginwidth=0 marginheight=0 hspace=0 vspace=0 frameborder=0 scrolling=no/>'+
		'</td></tr></table>';
	}
						
	if(type=='class'){
		document.getElementById('MapInfo').innerHTML='<table><tr><td align=left><input type=button class=gSubmit value=關閉課表 onClick=closeTimeTable(); /></td></tr><tr><td>'+
		'<iframe src=/CIS/RoomTimetable4HTML?getCt='+Oid+' width=780 height=960 marginwidth=0 marginheight=0 hspace=0 vspace=0 frameborder=0 scrolling=no/>'+
		'</td></tr></table>';	
	}					
						
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

function closeTimeTable(){	
	document.getElementById('loadMsg').style.display='none';
	document.getElementById('MapMessage').style.display='none';	
}

</script>


<table width="200" id="showTable" class="hairLineTable" style="position:absolute; right:20px; top:20px; filter:Alpha(opacity: 0.8);filter:alpha(opacity=80);-moz-opacity:0.8; z-index:1;">
	<tr height="50">
		<td class="hairLineTdF" id="showInfo">
		<font size="-1">*請先指定開課學年</font>
		<table>
			<tr>
				<td><input type="text" id="tname" size="10" style="font-size:18px;" value="教師姓名" onkeyup="getAny(this.value, 'tname', 'tid', 'empl', 'name')" onClick="this.value=''"/>				
				<input type="hidden" id="tid" size="10" style="font-size:18px;" /></td>
				
				<td><input type="button" id="x1" class="gCancelSmall" onClick="techTable(document.getElementById('tid').value, this.id, 'tech', document.getElementById('year').value, document.getElementById('term').value)" value="教師課表" /></td>
			</tr>
			<tr>
				<td>
				<input type="text" id="rid" size="10" onkeyup="getAny(this.value, 'rname', 'rid', 'Nabbr', 'id')" style="font-size:18px;" value="教室代碼" onClick="this.value=''"/>
				<input type="hidden" id="rname" size="10" style="font-size:18px;" />
				</td>				
				<td><input type="button" id="x2" class="gGreenSmall" onClick="techTable(document.getElementById('rid').value, this.id, 'room', document.getElementById('year').value, document.getElementById('term').value)" value="教室課表" /></td>
			</tr>
		</table>
		
		</td>
	</tr>
</table>

<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>