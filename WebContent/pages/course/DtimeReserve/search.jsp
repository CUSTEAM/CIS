<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>		
		<td>		
		
		
		<table class="hairLineTable" cellpadding="5" cellspacing="5">
			<tr>
				<td class="hairLineTdF" style="font-size:18px;">學年</td>
				<td class="hairLineTdF"><input style="font-size:18px;" type="text" name="year" size="3" value="${DtimeReserveManagerForm.map.year}" /></td>
				
				<!-- td class="hairLineTdF">
				<select name="optType" style="font-size:18px;">
					<option <c:if test="${DtimeReserveManagerForm.map.optType==''}">selected</c:if> value="">所有選別</option>
					<option <c:if test="${DtimeReserveManagerForm.map.optType=='1'}">selected</c:if> value="1">必修</option>
					<option <c:if test="${DtimeReserveManagerForm.map.optType=='2'}">selected</c:if> value="2">選修</option>
				</select>
				</td-->
				
				<td class="hairLineTdF" style="font-size:18px;">班級</td><td class="hairLineTdF">				
				<c:set var="campusSel" value="${DtimeReserveManagerForm.map.campusInCharge2}"/>
	  			<c:set var="schoolSel" value="${DtimeReserveManagerForm.map.schoolInCharge2}"/>
	  			<c:set var="deptSel"   value="${DtimeReserveManagerForm.map.deptInCharge2}"/>
	  			<c:set var="classSel"  value="${DtimeReserveManagerForm.map.classInCharge2}"/>
	  			<%@include file="/pages/include/ClassSelect4_dept4all.jsp"%>
				</td>
				<!-- td class="hairLineTdF" nowrap>
				<input type="text" id="classNo" name="classLess" size="6" 
				autocomplete="off" style="ime-mode:disabled" autocomplete="off"
		 		value="${DtimeReserveManagerForm.map.classLess}" style="font-size:18px;"
		 		onkeyup="if(this.value.length>2)getAny(this.value, 'classNo', 'className', 'Class', 'no')"/>		 		
		 		<input type="text" name="classInCharge2" id="className"
		 		value="${DtimeReserveManagerForm.map.classInCharge2}" style="font-size:18px;"
		 		size="12" onkeyup="getAny(this.value, 'className', 'classNo', 'Class', 'name')"/>
				</td-->
				
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td id="help" style="display:none;">
		<%@ include file="help.jsp"%>		
		</td>
	</tr>
	<tr>
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" 
					value="<bean:message key='Query'/>" 
					id="Query" class="gSubmit"
					onMouseOver="showHelpMessage('依照以上條件查詢', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
					
		<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
					onMouseOver="showHelpMessage('按下可開啟或關閉說明', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
		
		</td>
	</tr>
</table>
		