<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table class="hairLineTable"align="left">
			<tr>
				<td class="hairLineTdF">班級代碼</td>
				<td class="hairLineTdF"><input type="text" size="6" name="ClassNo" value="${CManagerForm.map.ClassNo}"/></td>				
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">班級名稱</td>
				<td class="hairLineTdF"><input type="text" size="10" name="ClassName" value="${CManagerForm.map.ClassName}"/></td>				
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">班級簡稱</td>
				<td class="hairLineTdF"><input type="text" size="10" name="ShortName" value="${CManagerForm.map.ShortName}"/></td>				
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">
				<select name="Type" />
					<option <c:if test="${CManagerForm.map.Type=='%'}">selected</c:if> value="%">所有班級</option>
					<option <c:if test="${CManagerForm.map.Type=='P'}">selected</c:if> value="P">實體班級</option>
					<option <c:if test="${CManagerForm.map.Type=='V'}">selected</c:if> value="V">虛擬班級</option>
					<option <c:if test="${CManagerForm.map.Type=='E'}">selected</c:if> value="E">延修班級</option>
					<option <c:if test="${CManagerForm.map.Type=='O'}">selected</c:if> value="O">廢止班級</option>
				</select>
				</td>				
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td>
		
		<table class="hairLineTable"align="left">
			<tr>
				<td class="hairLineTdF">
				<select name="CampusNo" />
					<option <c:if test="${CManagerForm.map.CampusNo==''}">selected</c:if> value="">所有校區</option>
					<c:forEach items="${AllCampus}" var="ac">					
					<option <c:if test="${CManagerForm.map.CampusNo==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>
				</td>				
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">
				<select name="SchoolType" />
					<option <c:if test="${CManagerForm.map.SchoolType==''}">selected</c:if> value="">所有部制</option>
					<c:forEach items="${AllSchoolType}" var="ac">					
					<option <c:if test="${CManagerForm.map.SchoolType==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>
				</td>				
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">
				<select name="SchoolNo" />
					<option <c:if test="${CManagerForm.map.SchoolNo==''}">selected</c:if> value="">所有學制</option>
					<c:forEach items="${AllSchool}" var="ac">					
					<option <c:if test="${CManagerForm.map.SchoolNo==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>
				</td>				
			</tr>
		</table>
		
		
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">				
				<select name="DeptNo" />
					<option <c:if test="${CManagerForm.map.DeptNo==''}">selected</c:if> value="">所有科系</option>
					<c:forEach items="${AllDept}" var="ac">					
					<option <c:if test="${CManagerForm.map.DeptNo==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>
				</td>				
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">
				
				<select name="Grade">
					<option <c:if test="${CManagerForm.map.Grade==''}">selected</c:if> value="">所有年級</option>
					<option <c:if test="${CManagerForm.map.Grade=='1'}">selected</c:if> value="1">一年級</option>
					<option <c:if test="${CManagerForm.map.Grade=='2'}">selected</c:if> value="2">二年級</option>
					<option <c:if test="${CManagerForm.map.Grade=='3'}">selected</c:if> value="3">三年級(二技延一)</option>
					<option <c:if test="${CManagerForm.map.Grade=='4'}">selected</c:if> value="4">四年級(二技延二)</option>
					<option <c:if test="${CManagerForm.map.Grade=='5'}">selected</c:if> value="5">五年級(四技延一)</option>
					<option <c:if test="${CManagerForm.map.Grade=='6'}">selected</c:if> value="6">六年級(四技延二或五專延一)</option>
					<option <c:if test="${CManagerForm.map.Grade=='7'}">selected</c:if> value="7">七年級(五專延二)</option>
				</select>
				
				</td>				
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center" width="100%">


		<INPUT type="submit"
			   name="method"
			   id="Query"
			   onMouseOver="showHelpMessage('查詢後可供列表, 或維護班級型態', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)"
			   value="<bean:message key='Query'/>"
			   class="gSubmit">   
			   
		<INPUT type="submit"
			   name="method" id="Create"
			   value="<bean:message
			   key='Create'/>"
			   class="gCancle" 
			   onMouseOver="showHelpMessage('填滿所有空格即建立班級和存取權限<br>再由建立者分發權', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)" />
			   
			   
		<INPUT type="submit"
			   name="method" id="Clear"
			   value="<bean:message
			   key='Clear'/>"
			   class="gCancle" 
			   onMouseOver="showHelpMessage('重新設定查詢條件', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)" />	   
			   
		</td>
	</tr>
</table>