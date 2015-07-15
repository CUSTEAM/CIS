<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" cellspacing="0" cellpadding="0">	
	<tr height="60">
		<td>
		
		<table class="hairLineTable"align="left" width="99%">
			<tr>
				<td class="hairLineTdF" colspan="7">班級資料即時查詢</td>
			</tr>
			<tr>
				<td class="hairLineTdF">
				<select name="CampusNo" style="font-size:16px;">
					<option <c:if test="${ListStudentForm.map.CampusNo==''}">selected</c:if> value="">所有校區</option>
					<c:forEach items="${AllCampus}" var="ac">					
					<option <c:if test="${ListStudentForm.map.CampusNo==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}校區</option>
					</c:forEach>					
				</select>
				</td>				
			
				<td class="hairLineTdF">
				<select name="SchoolType" style="font-size:16px;">
					<option <c:if test="${ListStudentForm.map.SchoolType==''}">selected</c:if> value="">所有部制</option>
					<c:forEach items="${AllSchoolType}" var="ac">					
					<option <c:if test="${ListStudentForm.map.SchoolType==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>
				</td>				
			
				<td class="hairLineTdF">
				<select name="SchoolNo" style="font-size:16px;">
					<option <c:if test="${ListStudentForm.map.SchoolNo==''}">selected</c:if> value="">所有學制</option>
					<c:forEach items="${AllSchool}" var="ac">					
					<option <c:if test="${ListStudentForm.map.SchoolNo==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>
				</td>				
		
				<td class="hairLineTdF">				
				<select name="DeptNo" style="font-size:16px;">
					<option <c:if test="${ListStudentForm.map.DeptNo==''}">selected</c:if> value="">所有科系</option>
					<c:forEach items="${AllDept}" var="ac">					
					<option <c:if test="${ListStudentForm.map.DeptNo==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>
				</td>				
			
				<td class="hairLineTdF">
				
				<select name="Grade" style="font-size:16px;">
					<option <c:if test="${ListStudentForm.map.Grade==''}">selected</c:if> value="">所有年級</option>
					<option <c:if test="${ListStudentForm.map.Grade=='1'}">selected</c:if> value="1">一年級</option>
					<option <c:if test="${ListStudentForm.map.Grade=='2'}">selected</c:if> value="2">二年級</option>
					<option <c:if test="${ListStudentForm.map.Grade=='3'}">selected</c:if> value="3">三年級(二技延一)</option>
					<option <c:if test="${ListStudentForm.map.Grade=='4'}">selected</c:if> value="4">四年級(二技延二)</option>
					<option <c:if test="${ListStudentForm.map.Grade=='5'}">selected</c:if> value="5">四技延一</option>
					<option <c:if test="${ListStudentForm.map.Grade=='6'}">selected</c:if> value="6">四技延二或五專延一</option>
					<option <c:if test="${ListStudentForm.map.Grade=='7'}">selected</c:if> value="7">五專延二</option>
				</select>
				
				</td>				
			
				<!-- td class="hairLineTdF">
				<select name="Type" style="font-size:16px;">
					<option <c:if test="${ListStudentForm.map.Type=='%'}">selected</c:if> value="%">所有班級</option>
					<option <c:if test="${ListStudentForm.map.Type=='P'}">selected</c:if> value="P">正常班級</option>					
					<option <c:if test="${ListStudentForm.map.Type=='V'}">selected</c:if> value="V">虛擬班級</option>
					<option <c:if test="${ListStudentForm.map.Type=='E'}">selected</c:if> value="E">延修班級</option>
					<option <c:if test="${ListStudentForm.map.Type=='O'}">selected</c:if> value="O">廢止班級</option>
				</select>
				</td-->
				<td class="hairLineTdF" width="100%">
				
				</td>			
			</tr>
			
			<tr>
				<td class="hairLineTdF" colspan="7">
				<table width="99%" class="hairLineTable" id="help" style="display:none;">
					<tr>
						<td class="hairLineTdF">
						查詢條件能夠進行交叉比對查詢，例如: <br>
						「台北校區→所有部制→所有學制→資訊管理系→所有年級→所有班級」<b>可以查詢到所有資管系的學生</b>，<br>
						「所有校區→所有部制→碩士班→所有科系→所有年級→延修班級」<b>可以查詢到所有碩士班延修的學生</b>，<br>
						「台北校區→日間部→所有學制→所有科系→一年級→所有班級」<b>可以查詢到台北校區日間部的新生</b>。<br>
						您可以利用各種條件進行查詢，若不想看到過多班級，<b>請點選「所有班級」欄位選擇「正常班級」</b>。<br><br>
						<b>正在進行的學籍異動或是查詢條件例外的學生人數，仍以各部制註冊單位公佈為標準</b>。
						</td>
					</tr>
				</table>
				<INPUT type="submit"
					   name="method"
					   id="Query"
					   onMouseOver="showHelpMessage('查詢後可供列表', 'inline', this.id)" 
					   onMouseOut="showHelpMessage('', 'none', this.id)"
					   value="<bean:message key='Query'/>"
					   class="gSubmit">   
					   
				<INPUT type="button"
					   name="method" id="Create"
					   value="查看說明" onClick="showObj('help');"
					   class="gCancle" 
					   onMouseOver="showHelpMessage('查看說明能更快獲得所需要的資料', 'inline', this.id)" 
					   onMouseOut="showHelpMessage('', 'none', this.id)" />
					   
					   
				<INPUT type="submit"
					   name="method" id="Clear"
					   value="<bean:message
					   key='Clear'/>"
					   class="gGreen" 
					   onMouseOver="showHelpMessage('重新設定查詢條件', 'inline', this.id)" 
					   onMouseOut="showHelpMessage('', 'none', this.id)" />
				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	
	
	<tr>
		<td class="fullColorTable" align="center" width="100%">


			   
			   
		</td>
	</tr>
	
</table>