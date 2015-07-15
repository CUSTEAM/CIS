<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%">
	<tr>
		<td>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">招生科系</td>
				<td class="hairLineTdF">
				
				<select name="DeptNo">
					<option value="">所有科系</option>
					<c:forEach items="${depts}" var="d">
					<option <c:if test="${ScheduleCheckForm.map.DeptNo==d.idno}">selected</c:if> value="${d.idno}">${d.name}</option>
					</c:forEach>
				</select>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td>
		
		
		
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">查詢期間</td>
				<td class="hairLineTd">
					<input type="text" name="startDay" id="startDay" value="${ScheduleCheckForm.map.startDay}"
					size="10" onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
				 	onMouseOver="showHelpMessage('西元年/月/日','inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)">
				</td>
				<td class="hairLineTdF" width="30" align="center">至</td>
				<td class="hairLineTd">
					<input type="text" name="endDay" id="endDay" value="${ScheduleCheckForm.map.endDay}"
					size="10" onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
				 	onMouseOver="showHelpMessage('西元年/月/日','inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)">
				</td>
				<td class="hairLineTdF" width="30" align="center">
					<img src="images/date.gif" />
				</td>
			</tr>
		</table>
		
		<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
			<tr>
				<td id="ds_calclass"></td>
			</tr>
		</table>
		
		
		</td>
	</tr>
</table>
