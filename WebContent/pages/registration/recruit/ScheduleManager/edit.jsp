<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%">
	<tr>
		<td>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">學年度</td>
				<td class="hairLineTdF">
				<input type="text" name="school_year" size="3" value="${aSchedule.school_year}" 
				autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">活動名稱</td>
				<td class="hairLineTdF">
				<input type="text" name="name" size="50" value="${aSchedule.name}"/>
				<input type="hidden" name="Oid" value="${aSchedule.Oid}"/>
				</td>
				
			</tr>
		</table>
		
		</td>
	</tr>	
	<tr>
		<td>		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">活動日期</td>
				<td class="hairLineTd">
					<input type="text" name="someday" id="someday" value="${aSchedule.someday}"
					size="10" onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
				 	onMouseOver="showHelpMessage('西元年/月/日','inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)">
				</td>
				<td class="hairLineTdF" width="30" align="center">
					<img src="images/date.gif" />
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">活動時間</td>
				<td class="hairLineTd">
				<input type="text" size="6" name="sometime" value="${aSchedule.sometime}" style="ime-mode:disabled" autocomplete="off"
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
							<option <c:if test="${aSchedule.item==i.id}">selected</c:if> value="${i.id}">${i.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF" nowrap>學校名稱</td>
				<td class="hairLineTdF" nowrap>
					<input type="text" name="school_name" id="school_name" value="${aSchedule.school_name}" onClick="clearSchool();"
					onkeyup="if(this.value.length>1)getAny(this.value, 'school_name', 'school_code', 'Recruitschool', 'name')" size="20"/>
					<input type="text" name="school_code" id="school_code" value="${aSchedule.school_code}"
					onkeyup="if(this.value.length>1)getAny(this.value, 'school_code', 'school_name', 'Recruitschool', 'no')" size="6"
					style="ime-mode:disabled" autocomplete="off" autocomplete="off" readonly/>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	
	<tr>
		<td>		
		<%@ include file="mult-unit.jsp"%>		
		</td>
	</tr>
	
	<tr>
		<td>
		
		
		<table class="hairLineTable" width="98%">
			<tr>
				<td class="hairLineTdF" width="80" nowrap>主要連絡人</td>
				<td class="hairLineTdF">
				<textarea rows="1" onClick="this.rows='10'" name="CodeEmpl_unit" style="width:100%">${aSchedule.leader}</textarea>				
				</td>
			</tr>
		</table>		
		</td>
	</tr>
	<tr>
		<td>
		<table class="hairLineTable" width="98%">
			<tr>
				<td class="hairLineTdF" width="80" nowrap>參與者描述</td>
				<td class="hairLineTdF">
				<textarea rows="1" name="staff" onClick="this.rows='10'" style="width:100%">${aSchedule.staff}</textarea>				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td>
		
		<table class="hairLineTable" width="98%">
			<tr>
				<td class="hairLineTdF" width="80" nowrap>工作說明</td>
				<td class="hairLineTdF">
				<textarea rows="1" onClick="this.rows='10'" name="work_descript" style="width:100%">${aSchedule.work_descript}</textarea>				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	<tr>
		<td>
		
		<table class="hairLineTable" width="98%">
			<tr>
				<td class="hairLineTdF" width="80" nowrap>執行結果說明</td>
				<td class="hairLineTdF">
				<textarea rows="1" onClick="this.rows='10'" name="result_descript" style="width:100%">${aSchedule.result_descript}</textarea>				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	<tr>
		<td>
		
		<table class="hairLineTable" width="98%">
			<tr>
				<td class="hairLineTdF" width="80" nowrap>對方建議事項</td>
				<td class="hairLineTdF">
				<textarea rows="1" onClick="this.rows='10'" name="feedback" style="width:100%">${aSchedule.feedback}</textarea>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	<tr>
		<td>
		
		<table class="hairLineTable" width="98%">
			<tr>
				<td class="hairLineTdF" width="80" nowrap>相關網址連結</td>
				<td class="hairLineTdF">
				<input type="text" name="url" style="width:100%" value="${aSchedule.url}"/>		
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












