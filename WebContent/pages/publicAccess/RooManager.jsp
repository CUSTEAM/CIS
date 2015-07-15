<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<script>
history.go(1);
</script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Personnel/RooManager" method="post" onsubmit="init('系統處理中...')">
<!-- 標題列 start-->		
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/building_go.gif" id="piftitle"></div>
		<div nowrap style="float:left;"><font class="gray_15">教室資訊 / 預約</font></div>		
		</td>
	</tr>
	
	
	
	<tr>
		<td>
		
		<table width="100%">
		<tr>
		<td>
		
		
		
		
		
		<table id="boroView" <c:if test="${RooManagerForm.map.boro==''}"> style="display:inline" </c:if> width="100%">
										
					
			<tr>
				<td>
				<%@ include file="RoomManager/search.jsp"%>
				</td>
			</tr>
			
			
			
			<c:if test="${nabbrs!=null}">
			<tr>
				<td>
				
				<%@ include file="RoomManager/list.jsp"%>
				
				</td>
			</tr>
			
			
			
			</c:if>
			
		</table>
		
		</div>
		</div>
		<div class="last">
		<span class="first"></span>
		<span class="last"></span>
		</div>
		</div>
		</td>
		</tr>
		</table>
		
		</td>
	</tr>	

<!-- 我們部門的申請列表 start -->
<c:if test="${!empty myBoro}">	
	<tr>
		<td>
		
		<table width="100%">
		<tr>
		<td>
		<div class="modulecontainer filled nomessages">
		<div class="first">
		<span class="first"></span>
		<span class="last"></span>
		</div>
		<div>
		<div>
		
		<table width="100%">
			<tr>
				<td width="1">
				<img src="images/building_error.gif">
				</td>
				<td width="100%" align="left">
				申請收件
				</td>
			</tr>
			<tr>
				<td colspan="2">				
				
				<display:table name="${myBoro}" pagesize="10" id="row" sort="list" class="list">
				<display:column title="場地" property="room_id" sortable="true" class="center" />				
				<display:column title="用途" property="title" sortable="true" class="center" />
				<display:column title="人數" property="heads" sortable="true" class="center" />
				<display:column title="日期" property="boro_date" sortable="true" class="center" />
				<display:column title="開始" property="begin" sortable="true" class="center" />
				<display:column title="結束" property="end" sortable="true" class="center" />
				
				
				<display:column title="申請單位" property="unitName" sortable="true" class="center" />
				<display:column title="申請書" property="doc" sortable="true" class="center" />
				<display:column title="審核" property="checkIn" sortable="true" class="center" />							
				</display:table>
				
				</td>
			</tr>
			<tr>
				<td align="center"colspan="2">
				
				<INPUT type="submit" name="method" value="<bean:message key='CheckIn'/>" class="CourseButton">
				
				</td>
			</tr>
			
		</table>
		
		</div>
		</div>
		<div class="last">
		<span class="first"></span>
		<span class="last"></span>
		</div>
		</div>
		</td>
		</tr>
		</table>		
		
		</td>
	</tr>
</c:if>	
<!-- 我們部門的申請列表 end -->
	
	
	
	
	
	
	
<!-- 全部的申請列表 start -->
<c:if test="${!empty allBoro}">	
	<tr>
		<td>
		
		<table width="100%">
		<tr>
		<td>
		<div class="modulecontainer filled nomessages">
		<div class="first">
		<span class="first"></span>
		<span class="last"></span>
		</div>
		<div>
		<div>
		
		<table width="100%">
			<tr>
				<td width="1">
				<img src="images/building_link.gif">
				</td>
				<td width="100%" align="left">
				申請列表
				</td>
			</tr>
			<tr>
				<td colspan="2">				
				
				<display:table name="${allBoro}" pagesize="10" id="row" sort="list" class="list">
				<display:column title="場地" property="room_id" sortable="true" class="left" />			
				<display:column title="用途" property="title" sortable="true" class="left" />
				<display:column title="人數" property="heads" sortable="true" class="right" />
				<display:column title="日期" property="boro_date" sortable="true" class="center" />
				<display:column title="開始" property="begin" sortable="true" class="center" />
				<display:column title="結束" property="end" sortable="true" class="center" />				
				<display:column title="申請單位" property="unitName" sortable="true" class="left" />
				<display:column title="申請書" property="doc" sortable="true" class="center" />
				<display:column title="審核" property="checkIn" sortable="true" class="left" />							
				</display:table>
				
				</td>
			</tr>
			
			
		</table>
		
		
		</td>
		</tr>
		</table>		
		
		</td>
	</tr>
	

</c:if>
<!-- 全部的申請列表 start -->
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	<tr height="30">
		<td class="fullColorTable">	


<!-- 怪怪？只能在這裡 start  -->
<div id="boroForm" style="display:none; position: absolute;	z-index: 32767">
<table class="hairLineTable" width="780">
<tr>
<td class="hairLineTdF">
<table width="100%">

	<tr>
		<td align="left">
		
		<input name="place" id="place" type="hidden" />
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">						
				單位
				</td>
				<td class="hairLineTd">
				<input name="boro_unit_name" type="text" value="${boro_unit_name}" disabled size="6"/>
										
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF" width="50">						
				申請人
				</td>
				<td class="hairLineTd">
				<input " type="text" value="${boro_username}" disabled size="4"/>
				
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">						
				電話
				</td>
				<td class="hairLineTd">
				<input type="text" name="boro_tel" size="8"/>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">						
				手機
				</td>
				<td class="hairLineTd">
				<input type="text" name="boro_mobile" size="8"/>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	
	<tr>
		<td align="left">
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF" width="85">						
				會議名稱/用途
				</td>
				<td class="hairLineTd">
				<input type="text" name="title" />
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF" width="60">						
				日期時間
				</td>
				<td class="hairLineTd">
				<input type="text" name="boro_date" id="boro_date" size="4" onclick="ds_sh(this), this.value='';" 
				autocomplete="off" style="ime-mode:disabled" autocomplete="off" readonly />
				
				<select name="begin">
					<option value="1">第1節</option>
					<option value="2">第2節</option>
					<option value="3">第3節</option>
					<option value="4">第4節</option>
					<option value="5">第5節</option>
					<option value="6">第6節</option>
					<option value="7">第7節</option>
					<option value="8">第8節</option>
					<option value="9">第9節</option>
					<option value="10">第10節</option>
					<option value="11">第11節</option>
					<option value="12">第1節</option>
					<option value="13">第2節</option>
					<option value="14">第3節</option>
					<option value="15">第4節</option>
				</select>
				至
				<select name="end">
					<option value="1">第1節</option>
					<option value="2">第2節</option>
					<option value="3">第3節</option>
					<option value="4">第4節</option>
					<option value="5">第5節</option>
					<option value="6">第6節</option>
					<option value="7">第7節</option>
					<option value="8">第8節</option>
					<option value="9">第9節</option>
					<option value="10">第10節</option>
					<option value="11">第11節</option>
					<option value="12">第1節</option>
					<option value="13">第2節</option>
					<option value="14">第3節</option>
					<option value="15">第4節</option>
				</select>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">						
				人數
				</td>
				<td class="hairLineTd">
				<input type="text" name="heads" size="3" />
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	<tr>
		<td>
		
		<table class="hairLineTable" align="left" width="98%">
			<tr>
				<td class="hairLineTdF">
				
				
				
				支援事項/器材<br>
				
				<FCK:editor instanceName="remark" toolbarSet="Basic"
				basePath="/pages/include/fckeditor">
					<jsp:attribute name="value">
					</jsp:attribute>
					<jsp:body>
						<FCK:config 
						SkinPath="skins/office2003/"
						ImageBrowserURL="/CIS/pages/include/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=/CIS/Connector" 
						LinkBrowserURL="/CIS/pages/include/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=/CIS/Connector" 
						FlashBrowserURL="/CIS/pages/include/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=/CIS/Connector" 
						ImageUploadURL="/CIS/Simpleuploader?Type=Image" 
						LinkUploadURL="/CIS/Simpleuploader?Type=File" 
						FlashUploadURL="/CIS/Simpleuploader?Type=Flash"/>
					</jsp:body>
				</FCK:editor>
				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td align="center">
			<INPUT type="submit" name="method" value="<bean:message key='Borrowing'/>" class="Gsubmit">
			<input type="button" value="取消"  onClick="cancelAll()" class="gCancel"/>
		</td>
	</tr>
	
</table>
</div>




</td>
</tr>
</table>
</div>




<!-- 怪怪？只能在這裡 End -->

<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display:none;">
	<tr>
		<td id="ds_calclass"></td>
	</tr>
</table>
				

		
		
		</td>
	</tr>	
	
</html:form>
</table>














<script>
function showForm(id, roomid){
	
	<c:forEach items="${nabbrs}" var="n">
	document.getElementById('info${n.nabbrOid}').style.display="none";	
	</c:forEach>
	
	document.getElementById('info'+id).style.display="inline";
	document.getElementById('accept'+id).style.display="inline";
	document.getElementById("place").value=roomid;
}
</script>

<script>
function show(id){
	if(document.getElementById("boroView").style.display=="none"){
		document.getElementById(id).value="1";//那一項功能要張開
		document.getElementById("boroView").style.display="inline";
	}else{
		document.getElementById(id).value="";
		document.getElementById("boroView").style.display="none";	
	}
}
</script>

<script>
function agree(id){

	

	document.getElementById("nabbr"+id).style.display="inline";	
	document.getElementById("accept"+id).style.display='none';	
	document.getElementById("boroForm").style.left=getLeft(document.getElementById("nabbr"+id));	//y座標
 	document.getElementById("boroForm").style.top=getTop(document.getElementById("nabbr"+id))+16;	//x座標
	
	document.getElementById("boroForm").style.display="inline";
	
	
	document.getElementById('loadMsg').style.width=document.body.scrollWidth;
	/*
	if (navigator.appName.indexOf("Microsoft")!=-1) {
		document.getElementById('loadMsg').style.height=document.body.clientHeight;			
	}else{
		document.getElementById('loadMsg').style.height=document.body.scrollHeight;// FF, ns
	}
	*/
	
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
	
	document.getElementById('loadMsg').style.display='inline';
}

function unagree(id){

	
	
	document.getElementById('info'+id).style.display="none";

}

function cancelAll(){

	<c:forEach items="${nabbrs}" var="n">
	document.getElementById("info"+${n.nabbrOid}).style.display='none';
	document.getElementById('nabbr${n.nabbrOid}').style.display="inline";
	</c:forEach>
	document.getElementById("boroForm").style.display="none";
	document.getElementById('loadMsg').style.display='none';

}
</script>

<%@ include file="/pages/include/MyCalendar.jsp" %>





<table id="MapMessage" class="ds_box" style="display:none" style="position:inside; left:170px;  top:10px; z-index:32768">
	<tr>
		<td id="MapInfo">
		
		
		</td>
	</tr>
</table>
<script>
/*
	function getMapLeft(ed) {
					//var tmp = ed.offsetLeft;
					var tmp=document.body.scrollHeight
					ed = ed.offsetParent
				while(ed) {
					tmp += ed.offsetLeft;
					ed = ed.offsetParent;
					}
				return tmp;
				}
*/	
	function getMapTop(ed) {
		var tmp = ed.offsetTop;
		ed = ed.offsetParent
	while(ed) {
		tmp += ed.offsetTop;
		ed = ed.offsetParent;
		}
	return tmp+24;
	}



	function showMapTimeTable(info, mode, id){
		
		//document.getElementById('MapMessage').style.left=getLeft(document.getElementById(id))+5;	//y座標
 		document.getElementById('MapMessage').style.top=getTop(document.getElementById(id))-5;	//x座標
		document.getElementById('MapMessage').style.display=mode;
		document.getElementById('MapInfo').innerHTML="";
		
		document.getElementById('MapInfo').innerHTML=info;
		
		document.getElementById('loadMsg').style.width=document.body.scrollWidth;
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
	
	document.getElementById('loadMsg').style.display='inline';
	
	}
	
	
	function closeTimeTable(){	
	document.getElementById('loadMsg').style.display='none';
	document.getElementById('MapMessage').style.display='none';	
	}
</script>
