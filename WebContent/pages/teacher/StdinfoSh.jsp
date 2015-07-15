<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Teacher/StdinfoSh" method="post">	
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/16-manager-st.gif"></div>
		<div nowrap style="float:left;"><font class="gray_15">學生查詢</font></div>		
		</td>
	</tr>
	
	<tr>
		<td>
		
		<table class="hairLineTable" width="99%">
			</tr>
				<td class="hairLineTdF">
				<input type="hidden" name="Oid" />		
				<select name="Cidno" style="font-size:16px;">					
					<c:forEach items="${AllCampuses}" var="ac">					
					<option value="${ac.idno}">${ac.name}校區</option>
					</c:forEach>					
				</select>				
				<select name="Sidno" style="font-size:16px;">
					<option value="__">所有學制</option>
					<c:forEach items="${AllSchools}" var="ac">					
					<option value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>						
				<select name="Didno"style="font-size:16px;">
					<option value="_">所有科系</option>
					<c:forEach items="${AllDepts}" var="ac">					
					<option value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>				
				<select name="Grade" style="font-size:16px;">
					<option value="_">所有年級</option>
					<option value="1">一年級</option>
					<option value="2">二年級</option>
					<option value="3">三年級</option>
					<option value="4">四年級</option>
					<option value="5">五年級</option>
				</select>			
				<select name="ClassNo" style="font-size:16px;">
					<option value="_">所有班級</option>
					<option value="1">甲班</option>
					<option value="2">乙班</option>
					<option value="3">丙班</option>
					<option value="4">丁班</option>
				</select>
				
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" nowrap>學號或身分證</td>
				<td class="hairLineTdF" width="1">
				<input type="text" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
				name="studentNo" id="studentNo" size="12" value="${StdinfoShForm.map.studentNo}" style="font-size:16px;"
				onkeyup="if(this.value.length>=2)GgetAny(this.value, 'studentNo', 'studentName', 'gstmdId', 'no')" 
				onFocus="chInput(this.id)"
				onClick="this.value='';document.getElementById('studentName').value=''" />
				</td>
				
				<td class="hairLineTdF" nowrap>姓名</td>
				<td class="hairLineTdF" width="1">
				<input style="font-size:16px;" onFocus="chInput(this.id)"
				type="text" name="studentName" id="studentName" size="10" value="${StdinfoShForm.map.studentName}"
				onkeyup="if(this.value.length>1)GgetAny(this.value, 'studentName', 'studentNo', 'gstmd', 'name')" 
				onClick="this.value='';document.getElementById('studentNo').value=''" />
				</td>
				<td class="hairLineTdF" width="100%">
				<font size="-1">離校生請點選「更多條件」中的「目標」</font>
				</td>
		</table>
		
		
		
		<div style="display:none;" id="exsh">
		<table class="hairLineTable" width="99%">
			</tr>
				<td class="hairLineTdF" nowrap>目標</td>
				<td class="hairLineTdF" nowrap>			
				<select name="target" style="font-size:16px;" 
				onChange="if(this.value=='Gstmd'){document.getElementById('lst').style.display='inline'}else{document.getElementById('lst').style.display='none'}">
					<option <c:if test="${StdinfoShForm.map.stype=='1'}">selected</c:if> value="stmd">在校生</option>
					<option <c:if test="${StdinfoShForm.map.stype=='0'}">selected</c:if> value="Gstmd">離校生</option>
				</select>
				
				</td>
				<td class="hairLineTdF" width="100%">
				<div id="lst" style="display:none;">
				<select name="lbegin" style="font-size:16px;">
					<option value="">年</option>
					<c:forEach begin="${SysParameter.School_year-10}" end="${SysParameter.School_year}" var="y">
					<option value="${y}">${y}年</option>
					</c:forEach>				
				</select>
				至
				<select name="lend" style="font-size:16px;">
					<option value="">年</option>
					<c:forEach begin="${SysParameter.School_year-10}" end="${SysParameter.School_year}" var="y">
					<option value="${y}">${y}年</option>
					</c:forEach>				
				</select>
				離校
				</div>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" width="99%">
			</tr>
				<td class="hairLineTdF" nowrap>性別</td>
				<td class="hairLineTdF">
				<select name="sex" style="font-size:16px;">
					<option value="">不限</option>
					<option value="1">男</option>
					<option value="2">女</option>
				</select>
				</td>
				<td class="hairLineTdF" nowrap>生日範圍</td>
				<td class="hairLineTdF" nowrap>				
				<select name="bbegin" style="font-size:16px;">
					<option value="">年</option>
					<c:forEach begin="${SysParameter.School_year-30}" end="${SysParameter.School_year-10}" var="y">
					<option value="${y}">${y}年</option>
					</c:forEach>
				
				</select>				
				
				</td>
				<td class="hairLineTdF" nowrap>至</td>
				<td class="hairLineTdF" nowrap>
				<select name="bend" style="font-size:16px;">
					<option value="">年</option>
					<c:forEach begin="${SysParameter.School_year-30}" end="${SysParameter.School_year-10}" var="y">
					<option value="${y}">${y}年</option>
					</c:forEach>
				
				</select>				
				
				</td>
				<td class="hairLineTdF" width="100%"></td>
			</tr>
		</table>
		
		<table class="hairLineTable" width="99%">			
			<tr>
				<td class="hairLineTdF" nowrap>身份</td>
				<td class="hairLineTdF" width="100%">
				<select style="font-size:16px;" name="ident">
				<option value="">不拘</option>
				<c:forEach items="${StudentIdentity}" var="i">
				<option value="${i.idno}">${i.name}</option>
				</c:forEach>
				</select>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" width="99%">			
			<tr>
				<td class="hairLineTdF" nowrap>住址</td>
				<td class="hairLineTdF" width="1">
				<input type="text" name="addr" style="font-size:16px;">
				</td>
				<td class="hairLineTdF" width="100%">
				<font size="-1">行政區或路名關鍵字，如:高雄、大安區、南港路</font>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" width="99%">			
			<tr>
				<td class="hairLineTdF" nowrap>學歷</td>
				<td class="hairLineTdF" width="1">
				<input type="text" name="school" style="font-size:16px;">
				</td>
				<td class="hairLineTdF" width="100%">
				<font size="-1">學校關鍵字，如:育達、協和、開南</font>
				</td>
			</tr>
		</table>
		</div>
		
		
		<table class="hairLineTable" width="99%">			
			<tr>
				<td class="hairLineTdF">
				<input type="submit" name="method" id="Query" value="<bean:message key='Query'/>" 
				onClick="enlb(),disp();" class="gSubmit" />				
				<input type="button" class="gGreen" value="更多條件" onClick="showObj('exsh');" />
				<input type="checkbox" checked disabled/>遵守個資法並同意系統記錄操作過程
				</td>
			</tr>
		</table>
		
<script>
function disp(){
	setTimeout("document.getElementById('Query').disabled=false",20000);
}

function enlb(){
	setTimeout("document.getElementById('Query').disabled=true",500);
	
}
</script>		
			
		</td>
	</tr>
	
</html:form>
	<tr height="30">
		<td class="fullColorTable"></td>
	</tr>
</table>

<%@ include file="/pages/include/AjaxGetStmdOrGstmd.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>
<%@ include file="/pages/include/AjaxUniMod.jsp" %>