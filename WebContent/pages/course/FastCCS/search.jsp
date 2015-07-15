<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
		
		<table class="hairlineTable">
			<tr>
				<td class="hairlineTdF" width="75">學生學號</td>
				<td class="hairlineTdF">		
				
				<input type="text" name="student_no" id="student_no" 
				autocomplete="off" style="ime-mode:disabled" autocomplete="off"
				value="${FastCCSForm.map.student_no}" onClick="this.value=''" style="font-size:18px;"
				onkeyup="getAny(this.value, 'student_no', 'student_name', 'stmd', 'no')"/>		
				
				<input type="text" autocomplete="off" name="student_name" id="student_name"
				style="ime-mode:disabled; background-color:transparent; border:none; width:200px; font-size:18px;"
				value="${FastCCSForm.map.student_name}" readonly />
				</td>
				<td class="hairlineTdF">
				<input type="submit"
			   name="method" id="Query"
			   value="<bean:message
			   key='Query'/>"
			   class="gGreen" 
			   onMouseOver="showHelpMessage('查詢已選課程，右方選單預設為本學期。<br>非本學期請自行點選', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)" />
		<select name="Sterm" style="font-size:16px;">
			<option <c:if test="${FastCCSForm.map.Sterm=='1'}">selected</c:if> value="1">查詢第1學期</option>
			<option <c:if test="${FastCCSForm.map.Sterm=='2'}">selected</c:if> value="2">查詢第2學期</option>
		</select>
				</td>
			</tr>
		</table>
		
		<table class="hairlineTable">
			<tr>
				<td class="hairlineTdF" width="75">課程編號</td>
				<td class="hairlineTdF">		
				
				<input type="text" name="Dtime_oid" id="Dtime_oid"  
				autocomplete="off" style="ime-mode:disabled; font-size:18px;" autocomplete="off"
				value="${FastCCSForm.map.Dtime_oid}" onClick="this.value=''"
				onMouseOver="showHelpMessage('課程編號已包含: 班級代碼、課程代碼、學分、時數、學期等...', 'inline', this.id)" 
			   	onMouseOut="showHelpMessage('', 'none', this.id)"
				onkeyup="getAny(this.value, 'Dtime_oid', 'chi_name', 'dtimeOid', 'no')"/>		
				
				<input type="text" autocomplete="off" name="chi_name" id="chi_name" 
				style="ime-mode:disabled; background-color:transparent; border:none; font-size:18px; width:200px;"
				value="${FastCCSForm.map.chi_name}" readonly />
				</td>
				<td class="hairlineTdF">
				<input type="submit"
			   name="method" id="AddCourse"
			   value="<bean:message
			   key='AddCourse'/>"
			   class="gSubmit" 
			   onMouseOver="showHelpMessage('立即加選', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)" />
			   
			   <input type="submit"
			   name="method" id="Rob"
			   value="<bean:message
			   key='Rob'/>"
			   class="gCancel" 
			   onMouseOver="showHelpMessage('不考慮各種問題<br>強制加選', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)" />
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
			   
		
		
		
		</td>
	</tr>
</table>