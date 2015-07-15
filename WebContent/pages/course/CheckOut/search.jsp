<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<table width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		
		<table id="pif" class="hairlineTable">
			<tr>
				<td class="hairlineTdF">
				<select name="sterm" style="font-size:18;">
  					<option value="1" <c:if test="${CheckOutForm.map.sterm=='1'}" > selected</c:if>>第1學期</option>
  					<option value="2" <c:if test="${CheckOutForm.map.sterm=='2'}" > selected</c:if>>第2學期</option>
  				</select>
				</td>
				<td class="hairlineTd">
 			   	<input type="text" id="classNo" name="classLess" style="font-size:18;"
				 size="8" autocomplete="off" style="ime-mode:disabled" autocomplete="off" value="${CheckOutForm.map.classLess}" 
		 		 onkeyup="if(this.value.length>2)getAny(this.value, 'classNo', 'className', 'Class', 'no')"
		 		 onclick="this.value='', document.getElementById('className').value=''"/>
		 		 <input type="text" name="className" value="${CheckOutForm.map.className}" style="font-size:18;"
				 id="className" onclick="this.value='', document.getElementById('classNo').value=''"/>
		 		 </td>
		 		 <td width="30" align="center" class="hairlineTdF">
		 		<img src="images/icon/icon_info.gif" id="ch" onMouseOver="showHelpMessage('輸入班級, 系統會根據項目找尋', 'inline', this.id)" 
				 onMouseOut="showHelpMessage('', 'none', this.id)"/> 			   			
				</td>				
			</tr>
		</table>
		
		</td>
	</tr>
	
	<tr>
		<td>
		
		<%@ include file="option.jsp"%>
		</td>
	</tr>
	<tr>
		<td>
		<%@ include file="help.jsp"%>
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable">		
		
		</td>
	</tr>
</table>