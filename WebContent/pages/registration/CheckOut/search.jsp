<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<table width="100%" cellspacing="0" cellpadding="0">
	<tr height="50">
		<td>
		
		<table class="hairlineTable">
			<tr>
				<td class="hairlineTdF">
					查核範圍
				</td>
				
				<td class="hairlineTdF" style="display:none;">
					<select name="Sterm" class="CourseButton" id="Sterm" onFocus="chInput(this.id)">
						<option <c:if test="${RegCheckOutForm.map.Sterm==''}" > selected</c:if> value=""></option>
						<option <c:if test="${RegCheckOutForm.map.Sterm=='1'}" > selected</c:if> value="1">第1學期</option>
						<option <c:if test="${RegCheckOutForm.map.Sterm=='2'}" > selected</c:if> value="2">第2學期</option>
					</select>
				</td>
				<td class="hairlineTd">
				   			
				 <input type="text" id="classNo" name="classLess" onFocus="chInput(this.id)"
				 size="8" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
		 		 value="${RegCheckOutForm.map.classLess}" 
		 		 onkeyup="if(this.value.length>2)getAny(this.value, 'classNo', 'className', 'Class', 'no')"
		 		 onclick="this.value='', document.getElementById('className').value=''" 
							 onMouseOver="showHelpMessage('請輸入班級, 系統會根據項目找尋', 'inline', this.id)" 
				 onMouseOut="showHelpMessage('', 'none', this.id)"/><input type="text" name="className" id="className" onFocus="chInput(this.id)"
		 		 value="${RegCheckOutForm.map.className}" size="12" onkeyup="getAny(this.value, 'className', 'classNo', 'Class', 'name')"
		 		 onclick="this.value='', document.getElementById('classNo').value=''"/>
		 		 </td>
		 		 <td width="30" align="center" class="hairlineTdF">
		 		<img src="images/16-exc-mark.gif" />
				   			
				</td>
			</tr>
		</table>
		
		<table class="hairlineTable">
			<tr>
				<td class="hairlineTdF">
					查核項目
				</td>
				<td class="hairlineTdF">
					<div name="selectBox" type="border" id="select_checkOpt">
					<select name="checkOpt" class="CourseButton" id="checkOpt" onFocus="chInput(this.id)">
						<option <c:if test="${RegCheckOutForm.map.checkOpt==''}" > selected</c:if> value="">請選擇</option>
						<option <c:if test="${RegCheckOutForm.map.checkOpt=='reSelect'}" > selected</c:if> value="reSelect">重複修課(歷史)</option>
						<option <c:if test="${RegCheckOutForm.map.checkOpt=='SelectException'}" > selected</c:if> value="SelectException">重複選課(當期)</option>
						<option <c:if test="${RegCheckOutForm.map.checkOpt=='SelectExceptionNext'}" > selected</c:if> value="SelectExceptionNext">重複選課(下學期)</option>
						<option <c:if test="${RegCheckOutForm.map.checkOpt=='entrno'}" > selected</c:if> value="entrno">入學文號</option>
					</select>
					</div>
				</td>
			</tr>
		</table>
		
		
		
		</td>
	</tr>
	<!-- 幫助列 start -->
	<tr>
		<td>		
		<table width="100%" align="center" id="help" style="display:none;">
			<tr>
				<td>				
				<table width="99%" class="hairLineTable">
					<tr>
						<td class="hairLineTdF">										
						<OL>						
							<li>標題
							<li>標題								
								<ul>
									<li>子標題
									<li>子標題
									<li>子標題
								</ul>							
							<li>標題
							<li>標題
							<li>標題
										
						</OL>
						</td>
					</tr>
				</table>				
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<!-- 幫助列 end -->
	<tr height="30">
		<td class="fullColorTable" align="center">
		<INPUT type="submit"
		   name="method"
		   value="<bean:message key='CourseCheckOpt'/>"
		   class="gSubmit">
			   
		<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
			onMouseOver="showHelpMessage('顯示說明', 'inline', this.id)" 
			onMouseOut="showHelpMessage('', 'none', this.id)"/>
		</td>
	</tr>
</table>

