<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>		
		<td>		
		
		
		<table class="hairLineTable" cellpadding="5" cellspacing="5">
			<tr>
				<td class="hairLineTdF">
				
				<c:set var="campusSel" value="${UpgradeForm.map.campusInCharge2}"/>
	  			<c:set var="schoolSel" value="${UpgradeForm.map.schoolInCharge2}"/>
	  			<c:set var="deptSel"   value="${UpgradeForm.map.deptInCharge2}"/>
	  			<c:set var="classSel"  value="${UpgradeForm.map.classInCharge2}"/>
	  			<c:set var="classLess"  value="${UpgradeForm.map.classLess}"/>
	  			<%@include file="/pages/include/ClassSelect4_dept.jsp"%>
				</td>
				<td class="hairLineTdF">
				<select name="grade" style="font-size:18px;">
					<option <c:if test="${UpgradeForm.map.grade==''}">selected</c:if> value=""></option>
					<option <c:if test="${UpgradeForm.map.grade=='1'}">selected</c:if> value="1">一年級</option>
					<option <c:if test="${UpgradeForm.map.grade=='2'}">selected</c:if> value="2">二年級</option>
					<option <c:if test="${UpgradeForm.map.grade=='3'}">selected</c:if> value="3">三年級</option>
					<option <c:if test="${UpgradeForm.map.grade=='4'}">selected</c:if> value="4">四年級</option>
				</select>
				</td>
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
					onMouseOver="showHelpMessage('依照以上條件檢視會受影響的學生', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
		
		<input type="submit" name="method" 
			value="<bean:message key='LevelUp'/>" 
			id="LevelUp" class="gGreen"
			onMouseOver="showHelpMessage('依照以上條件直接進行升級', 'inline', this.id)" 
			onMouseOut="showHelpMessage('', 'none', this.id)">
			
		<input type="submit" name="method" 
			value="<bean:message key='LevelDown'/>" 
			id="LevelDown" class="gCancel"
			onMouseOver="showHelpMessage('依照以上條件直接進行降級', 'inline', this.id)" 
			onMouseOut="showHelpMessage('', 'none', this.id)">
		
		<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
					onMouseOver="showHelpMessage('按下可開啟或關閉說明', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
		
		</td>
	</tr>
</table>
		