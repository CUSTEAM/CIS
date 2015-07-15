<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<table class="hairlineTable">
	<tr>
		<td class="hairlineTdF" nowrap>班級代碼</td>
		<td class="hairlineTdF">
		<input type="text" id="depart_class" name="classLess" style="font-size:18;"
	 	size="6" autocomplete="off" class="upInput" style="ime-mode:disabled" autocomplete="off" 
	 	value="${ScoreNotUploadForm.map.classLess}" 
		onkeyup="if(this.value.length>2)getAny(this.value, 'depart_class', 'className', 'Class', 'no')" 
		onClick="this.value='', document.getElementById('className').value='';"/>
		<input type="text" name="ClassName" value="${ScoreNotUploadForm.map.ClassName}" 
		size="8" style="font-size:18;" class="upInput" id="className"/>
		</td>
		<td class="hairlineTdF">
		<select name="type" style="font-size:18;">
			<option <c:if test="${ScoreNotUploadForm.map.type=='score2'}">selected</c:if> value="score2">期中考</option>
			<option <c:if test="${ScoreNotUploadForm.map.type=='score3'}">selected</c:if> value="score3">期末考</option>
			<option <c:if test="${ScoreNotUploadForm.map.type=='score'}">selected</c:if> value="score">總成績</option>
		</select>
		</td>
		<td class="hairlineTdF">
		<select name="scope" style="font-size:18;">
			<option  <c:if test="${ScoreNotUploadForm.map.scope=='all'}">selected</c:if> value="all">全部班級</option>
			<option <c:if test="${ScoreNotUploadForm.map.scope=='non'}">selected</c:if> value="non">非畢業班</option>
			<option <c:if test="${ScoreNotUploadForm.map.scope=='gra'}">selected</c:if> value="gra">畢業班</option>
		</select>
		</td>
		<td class="hairlineTdF">
		<select name="target" style="font-size:18;">
			<option  <c:if test="${ScoreNotUploadForm.map.target=='non'}">selected</c:if> value="non">未輸入完成</option>
			<option <c:if test="${ScoreNotUploadForm.map.target=='all'}">selected</c:if> value="all">所有課程</option>
		</select>
		</td>
	</tr>
</table>

<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %> 