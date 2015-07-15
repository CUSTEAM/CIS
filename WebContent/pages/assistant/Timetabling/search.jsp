<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>		
		<td>			
		
		<table class="hairLineTable" cellpadding="5" cellspacing="5">
			<tr>
				<td class="hairLineTdF" style="font-size:18px;">開課年度</td>
								
				<td class="hairLineTdF">
				
				<input type="text" name="year" id="year" value="${TimetablingForm.map.year}" style="font-size:18px;" size="3"/>
				</td>
				
				<td class="hairLineTdF">開課學期</td>
				<td class="hairLineTdF" style="font-size:18px;">
				<select name="term" style="font-size:18px;" id="term">
					<option <c:if test="${TimetablingForm.map.term=='1'}">selected</c:if> value="1">上學期</option>
					<option <c:if test="${TimetablingForm.map.term=='2'}">selected</c:if> value="2">下學期</option>
				</select>
				</td>
				<td class="hairLineTdF" nowrap style="font-size:12px;">
				<img src="images/16-IChat-bubble.jpg" />
				課程開設的學年學期
				</td>
			</tr>
		</table>
		<table class="hairLineTable" cellpadding="5" cellspacing="5">
			<tr>
				<td class="hairLineTdF">				
				<c:set var="campusSel" value="${TimetablingForm.map.campusInCharge2}"/>
	  			<c:set var="schoolSel" value="${TimetablingForm.map.schoolInCharge2}"/>
	  			<c:set var="deptSel"   value="${TimetablingForm.map.deptInCharge2}"/>
	  			<c:set var="classSel"  value="${TimetablingForm.map.classInCharge2}"/>
	  			<c:set var="classLess"  value="${TimetablingForm.map.classLess}"/>
	  			<%@include file="/pages/include/ClassSelect4_dept4all.jsp"%>
				</td>
				<td class="hairLineTdF" nowrap style="font-size:12px;">				
				<img src="images/16-IChat-bubble.jpg" />
	  			系統自動對應班級已開設的課程
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
		