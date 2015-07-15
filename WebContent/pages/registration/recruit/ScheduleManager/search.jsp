<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%">
	<tr>
		<td>		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">學年度</td>
				<td class="hairLineTdF">
				<input type="text" name="school_year" size="3" value="${ScheduleManagerForm.map.school_year}" 
				autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				</td>
			</tr>
		</table>		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">活動名稱</td>
				<td class="hairLineTdF">
				<input type="text" name="name" size="50" value="${ScheduleManagerForm.map.name}"/>
				</td>
			</tr>
		</table>		
		</td>
	</tr>	
	
	<tr>
		<td>		
			
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">預定日期</td>
				<td class="hairLineTd">
					<input type="text" name="someday" id="someday" value="${ScheduleManagerForm.map.someday}"
					size="10" onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
				 	onMouseOver="showHelpMessage('西元年/月/日','inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)">
				</td>
				<td class="hairLineTdF" width="30" align="center">
					<img src="images/date.gif" />
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">預定時間</td>
				<td class="hairLineTd">
				<input type="text" size="6" name="sometime" value="${ScheduleManagerForm.map.sometime}" style="ime-mode:disabled" autocomplete="off"
				id="sometime" onMouseOver="showHelpMessage('24小時制 hh:mm, 例如: 13:30','inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"/>
				</td>
				<td class="hairLineTdF" width="30" align="center">
					<img src="images/icon_clock.gif" />
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
	<tr>
		<td>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF" nowrap>項目</td>
				<td class="hairLineTdF">
					<select name="item">
						<option value="">選擇項目</option>
						<c:forEach items="${items}" var="i">
							<option <c:if test="${ScheduleManagerForm.map.item==i.id}">selected</c:if> value="${i.id}">${i.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF" nowrap>學校名稱</td>
				<td class="hairLineTdF" nowrap>
					<input type="text" name="school_name" id="school_name" value="${ScheduleManagerForm.map.school_name}" onClick="clearSchool();"
					onkeyup="if(this.value.length>1)getAny(this.value, 'school_name', 'school_code', 'Recruitschool', 'name')" size="20"/>
					<input type="text" name="school_code" id="school_code" value="${ScheduleManagerForm.map.school_code}"
					onkeyup="if(this.value.length>1)getAny(this.value, 'school_code', 'school_name', 'Recruitschool', 'no')" size="6"
					style="ime-mode:disabled" autocomplete="off" autocomplete="off" readonly/>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
</table>
<script>
function clearSchool(){
	document.getElementById("school_code").value="";
	document.getElementById("school_name").value="";
}
</script>