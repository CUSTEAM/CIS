<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>		
		<td>		
		
		
		<table class="hairLineTable" cellpadding="5" cellspacing="5">
			<tr>
				<td class="hairLineTdF" style="font-size:18px;"  nowrap>開課學年</td>
				<td class="hairLineTdF"><input style="font-size:18px;" type="text" name="year" size="3" value="${DtimereserveOptionForm.map.year}" /></td>
				<!-- td class="hairLineTdF">
				<select name="optType" style="font-size:18px;">
					<option <c:if test="${DtimeReserveManagerForm.map.optType==''}">selected</c:if> value="">所有選別</option>
					<option <c:if test="${DtimeReserveManagerForm.map.optType=='1'}">selected</c:if> value="1">必修</option>
					<option <c:if test="${DtimeReserveManagerForm.map.optType=='2'}">selected</c:if> value="2">選修</option>
				</select>
				</td-->
				<td class="hairLineTdF">
				
				<c:set var="campusSel" value="${DtimereserveOptionForm.map.campusInCharge2}"/>
	  			<c:set var="schoolSel" value="${DtimereserveOptionForm.map.schoolInCharge2}"/>
	  			<c:set var="deptSel"   value="${DtimereserveOptionForm.map.deptInCharge2}"/>
	  			<c:set var="classSel"  value="${DtimereserveOptionForm.map.classInCharge2}"/>
	  			<c:set var="classLess"  value="${DtimereserveOptionForm.map.classLess}"/>
	  			<%@include file="/pages/include/ClassSelect4_dept.jsp"%>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr height="40">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" 
					value="<bean:message key='Query'/>" 
					id="Query" class="gSubmit"
					onMouseOver="showHelpMessage('依照以上條件查詢', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">		
		</td>
	</tr>
</table>
		