<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Personnel/ListCourse" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
<!-- 標題列 start-->	
	<tr height="30">
		<td class="fullColorTable" width="100%">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/folder_magnify.gif" id="piftitle"></div>
		<div nowrap style="float:left;"><font class="gray_15">課程查詢</font></div>
		</td>
	</tr>	
	<tr>
		<td>
		<%@ include file="ListCourse/search.jsp"%>		
		</td>
	</tr>
	<c:if test="${!empty csses}">
	<tr>
		<td>		
		<%@ include file="ListCourse/list.jsp"%>		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">

		
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
function techTable(Oid, id, type){

	document.getElementById('MapMessage').style.top=getTop(document.getElementById(id))-5;	//x座標
	document.getElementById('MapMessage').style.left=100;	//x座標
	showObj('MapMessage');
	document.getElementById('MapInfo').innerHTML="";
	
	
	
	if(type=='room'){
		document.getElementById('MapInfo').innerHTML='<table><tr><td align=right><input type=button class=gSubmit value=關閉課表 onClick=closeTimeTable(); /></td></tr><tr><td>'+
		'<iframe src=/CIS/RoomTimetable4HTML?room_id='+Oid+' width=780 height=960 marginwidth=0 marginheight=0 hspace=0 vspace=0 frameborder=0 scrolling=no/>'+
		'</td></tr></table>';
	}
	
	if(type=='tech'){
		document.getElementById('MapInfo').innerHTML='<table><tr><td align=right><input type=button class=gSubmit value=關閉課表 onClick=closeTimeTable(); /></td></tr><tr><td>'+
		'<iframe src=/CIS/RoomTimetable4HTML?getTt='+Oid+' width=780 height=960 marginwidth=0 marginheight=0 hspace=0 vspace=0 frameborder=0 scrolling=no/>'+
		'</td></tr></table>';
	}
						
	if(type=='class'){
		document.getElementById('MapInfo').innerHTML='<table><tr><td align=right><input type=button class=gSubmit value=關閉課表 onClick=closeTimeTable(); /></td></tr><tr><td>'+
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



<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxGetEmplOrDempl.jsp" %>