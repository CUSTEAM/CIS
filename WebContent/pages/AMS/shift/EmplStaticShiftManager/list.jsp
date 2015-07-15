<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="99%" class="hairLineTable">
	<tr>
		<td class="hairLineTdF" align="center" colspan="2"><font size="+1">姓名</font></td>
		<td class="hairLineTdF" align="center"><font size="+1">職稱</font></td>
		<td class="hairLineTdF" align="center"><font size="+1">班別</font></td>
		<td class="hairLineTdF" align="center"><font size="+1">開始日期</font></td>
		<td class="hairLineTdF" align="center"><font size="+1">結束日期</font></td>
	</tr>
<script>
function setAllColumn(){
	setAll('startDate', document.getElementById('startDate').value);
	setAll('endDate', document.getElementById('endDate').value);
	setAll('id', document.getElementById('allId').value);
}
</script>
	<tr>		
		<td class="hairLineTd" colspan="3" align="center">
		<input type="button" class="gGreen" value="向下填滿" onClick="setAllColumn();" id="setAllId" 
		onMouseOver="showHelpMessage('填滿所有班別<br><font size=-2>(先選左邊欄位)</font>', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"/>
		<input type="hidden" name="idno"/>
		
		</td>
		<td class="hairLineTd" nowrap>
		
		<select name="id" id="allId" class="courseButton">
			<option value="">無須刷卡</option>
			<c:forEach items="${allShift}" var="as">
			<option value="${as.id}">${as.name}</option>				
			</c:forEach>
		</select>
		
		</td>
		
		<td class="hairLineTd" nowrap>
		<input id="startDate" name="startDate" type="text" size="4"
		onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
		</td>
		
		<td class="hairLineTd" nowrap>
		<input id="endDate" name="endDate" id="endDate" type="text" size="4" 
		onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
		
		<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
			<tr>
				<td id="ds_calclass"></td>
			</tr>
		</table>
		</td>
	</tr>
	
<script>
function setAll(name, value){
	//alert(value);
	var a=document.getElementsByName(name);
	for(i=0; i<a.length; i++){
		a[i].value=value;
	}
}
</script>	
	
	<c:forEach items="${empls}" var="e">
	<tr>
		<td class="hairLineTdF" align="center">
		<c:choose>
			<c:when test="${e.category=='1'}"><img src="images/user_gray.gif"/></c:when>
			<c:when test="${e.category=='2'}"><img src="images/user_suit.gif"/></c:when>
			<c:when test="${e.sex=='1'}"><img src="images/icon/user.gif"/></c:when>
			<c:when test="${e.sex=='2'}"><img src="images/user_female.gif"/></c:when>
			
		</c:choose>
		</td>
		<td class="hairLineTdF" nowrap><font size="+1">${e.cname}</font><input type="hidden" name="idno" value="${e.idno}"/></td>
		<td class="hairLineTdF" nowrap><font size="+1">
		<c:choose>
			<c:when test="${fn:length(e.sname)>6}">${fn:substring(e.sname, 0, 6)}..略</c:when>
			<c:when test="${fn:length(e.sname)<6}">${e.sname}</c:when>
		</c:choose>
		</font>
		</td>
		<td class="hairLineTdF">
		<select name="id" class="courseButton">
			<option value="">無須刷卡</option>
			<c:forEach items="${allShift}" var="as">
			<option <c:if test="${as.id==e.WorkShift}">selected</c:if> value="${as.id}">${as.name}</option>				
			</c:forEach>
		</select>		
		</td>		
		
		<td class="hairLineTdF">
		<input type="text" name="startDate" id="startDate${e.idno}"
		size="4" value=""
	 	onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off">
		</td>
		
		<td class="hairLineTdF">
		<input type="text" name="endDate" id="endDate${e.idno}"size="4" value=""
	 	onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off">
		</td>		
	</tr>

</c:forEach>
</table>