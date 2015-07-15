<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>		
		<td>			
		
		<table class="hairLineTable" cellpadding="5" cellspacing="5">
			<tr>
				<td class="hairLineTdF" nowrap>開課年度</td>
				<td class="hairLineTdF">
				
				
				<input style="font-size:18px;" type="text" name="year" id="year"
				size="1" value="${FestTimetablingForm.map.year}" /></td>
				
				<td class="hairLineTdF">
				
				<select name="term" style="font-size:18px;" id="term">
					<option <c:if test="${FestTimetablingForm.map.term=='1'}">selected</c:if> value="1">上學期</option>
					<option <c:if test="${FestTimetablingForm.map.term=='2'}">selected</c:if> value="2">下學期</option>
				</select>
				</td>
				<td class="hairLineTdF" nowrap>				
				<c:set var="campusSel" value="${FestTimetablingForm.map.campusInCharge2}"/>
	  			<c:set var="schoolSel" value="${FestTimetablingForm.map.schoolInCharge2}"/>
	  			<c:set var="deptSel"   value="${FestTimetablingForm.map.deptInCharge2}"/>
	  			<c:set var="classSel"  value="${FestTimetablingForm.map.classInCharge2}"/>
	  			<c:set var="classLess"  value="${FestTimetablingForm.map.classLess}"/>
	  			<%@include file="/pages/include/ClassSelect4_dept4all.jsp"%>
				</td>				
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center">		
		<input type="submit" name="method" value="<bean:message key='Query'/>" 
		id="Query" class="gSubmit" onMouseOver="showHelpMessage('依照以上條件查詢', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)">
					
		
		</td>
	</tr>
</table>
		