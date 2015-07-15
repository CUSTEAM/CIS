<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="99%" class="hairLineTable">
	<tr>
		<td class="hairLineTdF"></td>
		<c:forEach begin="1" end="7" var="w">		
		<td class="hairLineTdF" width="14%" align="center">
			<c:choose>
			<c:when test="${w=='1'}">星期一</c:when>
			<c:when test="${w=='2'}">星期二</c:when>
			<c:when test="${w=='3'}">星期三</c:when>
			<c:when test="${w=='4'}">星期四</c:when>
			<c:when test="${w=='5'}">星期五</c:when>
			<c:when test="${w=='6'}">星期六</c:when>
			<c:when test="${w=='7'}">星期日</c:when>
			<c:otherwise>?</c:otherwise>
			</c:choose>
		</td>
		</c:forEach>
	</tr>
	
	
	
	
	<c:forEach begin="1" end="14" var="c">
	
	<tr height="75">
		<td class="hairLineTdF">${c}</td>
		
		<c:forEach begin="1" end="7" var="w">
		
		<td class="hairLineTdF" style="font-size:14px;" valign="top">
			
			
			<div style="display:inline; float:right; cursor:pointer;" id="td${w}${c}" 
			onClick="setCs(this.id, '${w}', '${c}', '', '', '', '', '', '')"
			onMouseOver="showHelpMessage('星期${w}, 第${c}節', 'inline', this.id)" 
			onMouseOut="showHelpMessage('', 'none', this.id)"><img src="images/16-em-plus.png" /></div>
			
			<c:forEach items="${allClass}" var="a">				
			<c:if test="${a.week==w && (c>=a.begin && c<=a.end)}">
			<script>showObj("td${w}${c}")</script>
			<div id="a${w}${c}" onClick="setCs(this.id, '${w}', '${a.begin}', '${a.end}', '${a.techid}', '${a.cname}', '${a.place}', '${a.dcOid}', '${a.dtOid}')" style="cursor:pointer; width:100%;">
			${a.chi_name}<br>
			${a.cname}, ${a.place}
			</div>			
			</c:if>			
			</c:forEach>
			
			
			
					
		</td>		
		</c:forEach>
	</tr>
	</c:forEach>
</table>

<div class="ds_box" id="miniForm" style="display:none;">
<table class="hairLineTable" id="contBox">
	<tr>
		<td class="hairLineTdF" nowrap>課程名稱</td>
		<td class="hairLineTdF">
		<input type="hidden" name="Dtime_reserve_oid" id="Dtime_reserve_oid" size="1"/>
		<input type="hidden" name="Oid" id="Oid" size="1"/>
		
		<select id="selOid" style="font-size:18px; width:100%;" onChange="document.getElementById('Dtime_reserve_oid').value=this.value">
			<option value="">選擇已建立課程</option>
			<c:forEach items="${allCs}" var="ac">
			<option value="${ac.Oid}">${ac.chi_name}</option>
			</c:forEach>
		</select>
		</td>
	</tr>
	<tr>
		<td class="hairLineTdF">任課教師</td>
		<td class="hairLineTdF">		
		<input type="text" style="font-size:18px; width:100%;"
				onkeyup="getAny(this.value, 'cname', 'techid', 'empl', 'name')" name="cname" id="cname"/>				
				<input type="hidden" name="techid" id="techid"/>
		
		</td>
	</tr>
	<tr>
		<td class="hairLineTdF">上課時間</td>
		<td class="hairLineTdF">
		
		<table>
			<tr>
				<td>自第</td>
				<td><input type="text" name="begin" style="font-size:18px;" id="begin" size="1" readonly/></td>
				<td>節至第</td>
				<td><input type="text" name="end" style="font-size:18px;" id="end" size="1"/></td>
				<td>節</td>
			</tr>
		</table>
		<input type="hidden" name="week" id="week" />		
		</td>
	</tr>
	<tr>
		<td class="hairLineTdF">上課地點</td>
		<td class="hairLineTdF">
		<input type="text" style="font-size:18px;" name="place" id="place" 
		onkeyup="getAny(this.value, 'placehiden', 'place', 'Nabbr', 'id')" name="cname" id="cname${ad.dcOid}"/>
		<input type="hidden" id="placehiden">
		
		</td>
	</tr>
	<tr>
		<td class="hairLineTdF" colspan="2">
		<input type="submit" name="method" value="<bean:message key='Save'/>" 
		id="Save" class="gSubmit">
		<input type="submit" name="method" value="<bean:message key='Delete'/>" 
		id="Delete" class="gGreen">
		<input type="button" class="gCancel" value="關閉" onClick="closeMiniForm();"/>
		</td>
	</tr>
	
</table>

</div>

<script>
function closeMiniForm(){	
	document.getElementById("loadMsg").style.display="none";
	document.getElementById('miniForm').style.display="none";
}

function setCs(id, week, begin, end, techid, cname, place, dcOid, dtOid){	
	document.getElementById("Oid").value=dcOid;
	document.getElementById("Dtime_reserve_oid").value=dtOid;
	document.getElementById("week").value=week;
	document.getElementById("place").value=place;
	document.getElementById("begin").value=begin;
	document.getElementById("end").value=end;
	document.getElementById("techid").value=techid;
	document.getElementById("cname").value=cname;
	document.getElementById("selOid").value=dtOid;
	
	if(dcOid==""){
		document.getElementById("Delete").style.display="none";
	}else{
		document.getElementById("Delete").style.display="inline";
	}
	showClass('inline', id);
}

function showClass(mode, id){		
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
		document.getElementById('loadMsg').style.width=document.body.scrollWidth;
		document.getElementById('loadMsg').style.display="inline";
		
		/*
		if(getLeft(document.getElementById(id))>800){
			document.getElementById('miniForm').style.left=getLeft(document.getElementById(id))-300;	//y座標
		}else{
			document.getElementById('miniForm').style.left=getLeft(document.getElementById(id))+5;	//y座標
		}
		*/
		document.getElementById('miniForm').style.left=getLeft(document.getElementById(id))-305;	//y座標
 		document.getElementById('miniForm').style.top=getTop(document.getElementById(id))-15;	//x座標
		document.getElementById('miniForm').style.display=mode;
	}
</script>