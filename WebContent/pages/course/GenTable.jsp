<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
<html:form action="/Course/GenTable" method="post" enctype="multipart/form-data" onsubmit="init('系統處理中...')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/printer.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">課表查詢<font size="-1">列印</font></font></div>
		</td>
	</tr>
	
	<tr>
		<td>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF" nowrap>範圍</td>
				<td class="hairLineTdF" nowrap>上限</td>
				<td class="hairLineTdF" nowrap>最後修改</td>
				<td class="hairLineTdF"></td>
			</tr>
			
			
			
			
			<tr>
				<td class="hairLineTdF">
				<input type="hidden" name="Oid" />		
				<select name="Cidno" style="font-size:16px;">
					
					<c:forEach items="${AllCampus}" var="ac">					
					<option <c:if test="${Seld3ScheduleManagerForm.map.Cidno[0]==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}校區</option>
					</c:forEach>					
				</select>				
				<select name="Sidno" style="font-size:16px;">
					<option <c:if test="${Seld3ScheduleManagerForm.map.Sidno[0]==''}">selected</c:if> value="">所有學制</option>
					<c:forEach items="${AllSchool}" var="ac">					
					<option <c:if test="${Seld3ScheduleManagerForm.map.Sidno[0]==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>						
				<select name="Didno"style="font-size:16px;">
					<option <c:if test="${Seld3ScheduleManagerForm.map.Didno[0]==''}">selected</c:if> value="">所有科系</option>
					<c:forEach items="${AllDept}" var="ac">					
					<option <c:if test="${Seld3ScheduleManagerForm.map.Didno[0]==ac.idno}">selected</c:if> value="${ac.idno}">${ac.name}</option>
					</c:forEach>					
				</select>				
				<select name="Grade" style="font-size:16px;">
					<option <c:if test="${Seld3ScheduleManagerForm.map.Grade[0]==''}">selected</c:if> value="">所有年級</option>
					<option <c:if test="${Seld3ScheduleManagerForm.map.Grade[0]=='1'}">selected</c:if> value="1">一年級</option>
					<option <c:if test="${Seld3ScheduleManagerForm.map.Grade[0]=='2'}">selected</c:if> value="2">二年級</option>
					<option <c:if test="${Seld3ScheduleManagerForm.map.Grade[0]=='3'}">selected</c:if> value="3">三年級</option>
					<option <c:if test="${Seld3ScheduleManagerForm.map.Grade[0]=='4'}">selected</c:if> value="4">四年級</option>
					<option <c:if test="${Seld3ScheduleManagerForm.map.Grade[0]=='5'}">selected</c:if> value="5">五年級</option>
				</select>			
				<select name="ClassNo" style="font-size:16px;">
					<option <c:if test="${Seld3ScheduleManagerForm.map.ClassNo[0]=='%'}">selected</c:if> value="">所有班級</option>
					<option <c:if test="${Seld3ScheduleManagerForm.map.ClassNo[0]=='1'}">selected</c:if> value="1">甲班</option>
					<option <c:if test="${Seld3ScheduleManagerForm.map.ClassNo[0]=='2'}">selected</c:if> value="2">乙班</option>
					<option <c:if test="${Seld3ScheduleManagerForm.map.ClassNo[0]=='3'}">selected</c:if> value="3">丙班</option>
					<option <c:if test="${Seld3ScheduleManagerForm.map.ClassNo[0]=='4'}">selected</c:if> value="4">丁班</option>
				</select>
				</td>
				
				<td class="hairLineTdF">		
				<select style="font-size:18px;" name="max">
					<c:forEach begin="0" end="14" var="m">
					<option <c:if test="${Seld3ScheduleManagerForm.map.max[0]==m}">selected</c:if> value="${m}">${m}</option>
					</c:forEach>
				</select>		
				</td>
				
				<td class="hairLineTdF" nowrap></td>
				<td class="hairLineTdF" nowrap>
				
				<input type="submit"
					   name="method" id="Save"
					   value="<bean:message
					   key='Save'/>"
					   class="gSubmit" 
					   onMouseOver="showHelpMessage('存', 'inline', this.id)" 
					   onMouseOut="showHelpMessage('', 'none', this.id)" />		
				</td>
				
			</tr>
		</table>
		</td>
	</tr>
	
	
</html:form>
</table>