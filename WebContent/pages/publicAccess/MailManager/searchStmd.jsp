<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="/taglibs.jsp"%>
<div id="stmdSearch" style="display:none">
<table width="100%">
	<tr>
		<td>		
		<table class="hairLineTable" width="99%" width="99%">
			<tr>
				<td class="hairLineTdF" nowrap>學生班級</td>
				<td class="hairLineTdF" nowrap>
				<input type="hidden" name="Oid" />		
				<select id="Cidno" style="font-size:16px;">					
					<c:forEach items="${AllCampuses}" var="ac">					
					<option value="${ac.idno}">${ac.name}校區</option>
					</c:forEach>					
				</select>				
				<select id="Sidno" style="font-size:16px;">
					<option value="__">所有學制</option>
					<c:forEach items="${AllSchools}" var="ac">					
					<option value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>						
				<select id="Didno"style="font-size:16px;">
					<option value="_">所有科系</option>
					<c:forEach items="${AllDepts}" var="ac">					
					<option value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>				
				<select id="Grade" style="font-size:16px;">
					<option value="_">所有年級</option>
					<option value="1">一年級</option>
					<option value="2">二年級</option>
					<option value="3">三年級</option>
					<option value="4">四年級</option>
					<option value="5">五年級</option>
				</select>			
				<select id="ClassNo" style="font-size:16px;">
					<option value="_">所有班級</option>
					<option value="1">甲班</option>
					<option value="2">乙班</option>
					<option value="3">丙班</option>
					<option value="4">丁班</option>
				</select>				
				</td>
				<td class="hairLineTdF" width="100%"></td>
			</tr>
		</table>
		
		
		</td>
	</tr>
	<tr>
		<td>
		
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF" id="pifname" nowrap>姓名或學號 </td>
				<td class="hairLineTdF" nowrap>
				<input type="text" autocomplete="off" id="stNameNo" style="font-size:18px;" />
				</td>				
				<td class="hairLineTdF" width="100%"></td>
			</tr>
		</table>
		
		</td>
	</tr>				
</table>

<table width="100%">
	<tr>
		<td>
		<table class="hairLineTable" align="left" width="99%">
			<tr>
				<td class="hairLineTdF" width="100%">
					<input type="button" value="找尋學生" id="gm" name="gm" onClick="getST();" class="gSubmit" />
					顯示500筆學生資料
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>