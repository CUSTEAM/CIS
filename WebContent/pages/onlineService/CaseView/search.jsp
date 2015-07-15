<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>
<table class="hairLineTable" align="left">
	<tr>
		<td class="hairLineTdF">
		服務編號
		</td>
		<td class="hairLineTd">
		<input size="10" type="text" autocomplete="off" name="doc_no" value="${CaseViewForm.map.doc_no}" style="ime-mode:disabled" autocomplete="off">
		</td>
	</tr>
</table>

<table class="hairLineTable">
	<tr>
		<td class="hairLineTdF" id="pifname" onMouseOver="showHelpMessage('無論是否在學皆可查到', 'inline', this.id)" 
			onMouseOut="showHelpMessage('', 'none', this.id)">
		學號 / 姓名
		</td>
		<td class="hairLineTd">
		<input type="text" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
		name="student_no" id="studentNo" size="12" value="${CaseViewForm.map.student_no}"
		onkeyup="if(this.value.length>=2)GgetAny(this.value, 'studentNo', 'studentName', 'gstmd', 'no')"  /><input 		
		type="text" id="studentName" size="6" value="${CaseViewForm.map.studentName}" name="studentName"
 			onkeyup="if(this.value.length>1)GgetAny(this.value, 'studentName', 'studentNo', 'gstmd', 'name')" />
			</td>
			<td class="hairLineTdF" width="30" align="center">
			<img src="images/16-exc-mark.gif" id="n" onMouseOver="showHelpMessage('學號輸入左邊, 姓名輸入右邊,<br>若貼上文字, 請按一下鍵盤右側的方向鍵,<br>以便自動完成', 'inline', this.id)" 
			onMouseOut="showHelpMessage('', 'none', this.id)"/>
			</td>
		</tr>
	</table>
	
	</td>
</tr>
<tr>
	<td>
	<table class="hairLineTable" align="left">
		<tr>
			<td class="hairLineTdF">目前狀態</td>
			<td class="hairLineTd">
			<select name="aStatus" class="CourseButton" onChange="document.getElementById('checkOid${m.Oid}').value='*'">
			<option <c:if test="${CaseViewForm.map.status==''}">selected</c:if> value="">全部</option>
			<option <c:if test="${CaseViewForm.map.aStatus=='W'}">selected</c:if> value="W">等待中</option>
			<option <c:if test="${CaseViewForm.map.aStatus=='O'}">selected</c:if> value="O">處理中</option>
			<option <c:if test="${CaseViewForm.map.aStatus=='F'}">selected</c:if> value="F">已完成</option>
			<option <c:if test="${CaseViewForm.map.aStatus=='B'}">selected</c:if> value="B">已暫停</option>
			<option <c:if test="${CaseViewForm.map.aStatus=='R'}">selected</c:if> value="R">已退件</option>
			<option <c:if test="${CaseViewForm.map.aStatus=='M'}">selected</c:if> value="M">已寄出</option>
			<option <c:if test="${CaseViewForm.map.aStatus=='C'}">selected</c:if> value="C">已結案</option>			
		</select>	
		</td>
	</tr>
</table>

<table class="hairLineTable" align="left">
	<tr>
		<td class="hairLineTdF">取件方法</td>
		<td class="hairLineTd">
		<select name="get_method" class="CourseButton">
			<option <c:if test="${CaseViewForm.map.get_method==''}">selected</c:if> value="">全部</option>
			<option <c:if test="${CaseViewForm.map.get_method=='1'}">selected</c:if> value="1">台北校區</option>
			<option <c:if test="${CaseViewForm.map.get_method=='2'}">selected</c:if> value="2">新竹校區</option>
			<option <c:if test="${CaseViewForm.map.get_method=='M'}">selected</c:if> value="M">郵寄(郵資另計)</option>				
			</select>	
			</td>
			<td class="hairLineTdF" width="30" align="center"><img src="images/icon/icon_info.gif" id="m"
			onMouseOver="showHelpMessage('請選擇狀態或取件方法','inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)" /></td>
		</tr>
	</table>
	
	</td>
</tr>

<tr>
	<td>
	
	<table class="hairLineTable" align="left">
		<tr>
			<td class="hairLineTdF">申請日期範圍</td>
			<td class="hairLineTd">
			<input type="text" name="send_time_start" id="send_time_start"
			size="8" value="${CaseViewForm.map.send_time_start}"
	 	onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off">
		<img src="images/icon/date_go.gif" />
		<input type="text" name="send_time_end" id="send_time_end"
		size="8" value="${CaseViewForm.map.send_time_end}"onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off">
			</td>
			<td class="hairLineTdF" width="30" align="center"><img src="images/icon/icon_info_exclamation.gif" id="d1"
			onMouseOver="showHelpMessage('查詢請用西元年, 例:<br>2009-01-01(98/1/1)<br>2009 (民國98年)<br>%-07 (7月)<br>%-%-08 (8日)',
		 	'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)" /></td>
		</tr>
	</table>
	
	<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
		<tr>
			<td id="ds_calclass"></td>
		</tr>
	</table>
	
	
	</td>
</tr>

<tr>
	<td>		
	
	<table class="hairLineTable" align="left">
		<tr>
			<td class="hairLineTdF">預定日期範圍</td>
			<td class="hairLineTd">
			<input type="text" name="expect_time_start" id="expect_time_start"
			size="8" value="${CaseViewForm.map.expect_time_start}"
	 	onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off">
		<img src="images/icon/date_go.gif" />
		<input type="text" name="expect_time_end" id="expect_time_end"
		size="8" value="${CaseViewForm.map.expect_time_end}"
		 	onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off">
			</td>
		</tr>
	</table>
	
	</td>
</tr>
<tr>
	<td>		
	
	<table class="hairLineTable" align="left">
		<tr>
			<td class="hairLineTdF">實際日期範圍</td>
			<td class="hairLineTd">
			<input type="text" name="complete_time_start" id="complete_time_start"
			size="8" value="${CaseViewForm.map.complete_time_start}"
	 	onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off">
		<img src="images/icon/date_go.gif" />
		<input type="text" name="complete_time_end" id="complete_time_end"
		size="8" value="${CaseViewForm.map.complete_time_end}"
	 	onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off">
		</td>
	</tr>
</table>