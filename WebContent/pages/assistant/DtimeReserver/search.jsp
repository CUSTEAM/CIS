<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>		
		<td>		
		
		
		<table class="hairLineTable" cellpadding="5" cellspacing="5">
			<tr>
				<td class="hairLineTdF" style="font-size:18px;">入學年度</td>
				<td class="hairLineTdF"><input style="font-size:18px;" type="text" name="year" size="3" value="${DtimeReserveManagerForm.map.year}" /></td>
				<td class="hairLineTdF" nowrap style="font-size:12px;">
				<img src="images/16-IChat-bubble.jpg" />如100學年4年級學生輸入97, 100學年2年級的學生則輸入98,以此類推
				</td>
				
			</tr>
		</table>
		<table class="hairLineTable" cellpadding="5" cellspacing="5">
			<tr>
				<td class="hairLineTdF">				
				<c:set var="campusSel" value="${DtimeReserveManagerForm.map.campusInCharge2}"/>
	  			<c:set var="schoolSel" value="${DtimeReserveManagerForm.map.schoolInCharge2}"/>
	  			<c:set var="deptSel"   value="${DtimeReserveManagerForm.map.deptInCharge2}"/>
	  			<c:set var="classSel"  value="${DtimeReserveManagerForm.map.classInCharge2}"/>
	  			<c:set var="classLess"  value="${DtimeReserveManagerForm.map.classLess}"/>
	  			<%@include file="/pages/include/ClassSelect4_dept4all.jsp"%>
				</td>		
				<td class="hairLineTdF">
				<select name="grade" style="font-size:18px;">
					<option <c:if test="${DtimeReserveManagerForm.map.grade=='1'}">selected</c:if> value="1">一年級</option>
					<option <c:if test="${DtimeReserveManagerForm.map.grade=='2'}">selected</c:if> value="2">二年級</option>
					<option <c:if test="${DtimeReserveManagerForm.map.grade=='3'}">selected</c:if> value="3">三年級</option>
					<option <c:if test="${DtimeReserveManagerForm.map.grade=='4'}">selected</c:if> value="4">四年級</option>
				</select>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>

	<tr>
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" 
					value="<bean:message key='Query'/>" 
					id="Query" class="gSubmit"
					onMouseOver="showHelpMessage('依照以上條件查詢', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
					
		
		
		</td>
	</tr>
</table>
		