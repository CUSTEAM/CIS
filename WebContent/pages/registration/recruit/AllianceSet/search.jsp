<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%">
	<tr>
		<td>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">學年度</td>
				<td class="hairLineTdF">
				<input type="hidden" name="Oid" value=""/>
				<input type="text" name="school_year" size="3" value="${AllianceSetForm.map.school_year[0]}" 
				autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">招生學制</td>
				<td class="hairLineTdF">				
				<select name="SchoolNo">
					<option value="">所有學制</option>
					<c:forEach items="${schools}" var="s">
					<option <c:if test="${AllianceSetForm.map.SchoolNo[0]==s.idno}">selected</c:if> value="${s.idno}">${s.name}</option>
					</c:forEach>
				</select>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">招生科系</td>
				<td class="hairLineTdF">
				
				<select name="DeptNo">
					<option value="">所有科系</option>
					<c:forEach items="${depts}" var="d">
					<option <c:if test="${AllianceSetForm.map.DeptNo[0]==d.idno}">selected</c:if> value="${d.idno}">${d.name}</option>
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
				<td class="hairLineTdF">聯招報名人數</td>
				<td class="hairLineTdF">
				<input type="text" name="enroll" size="3" value="${AllianceSetForm.map.enroll[0]}" 
				autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">聯招錄取人數</td>
				<td class="hairLineTdF">
				<input type="text" name="real" size="3" value="${AllianceSetForm.map.real[0]}" 
				autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	
	
</table>