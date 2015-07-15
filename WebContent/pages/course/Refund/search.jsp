<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp" %>		
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td>
				<table class="hairlineTable">
					<tr>
						<td width="30" align="center" class="hairlineTdF">班級</td>
						<td class="hairlineTd">
				
						<input type="text" id="depart_class" name="depart_class" value="${CheckCsGroupForm.map.depart_class}"
				 		 size="8" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
				 		 onkeyup="if(this.value.length>1)getAny(this.value, 'depart_class', 'classLess', 'Class', 'no')"
				 		 onMouseOver="showHelpMessage('請輸入學制科系代碼, 如1643, 1543, ...', 'inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)"
				 		 onclick="this.value='', document.getElementById('classLess').value=''" /><input type="text" name="classLess" 
				 		 id="classLess" value="${CheckCsGroupForm.map.classLess}" size="16"
				 		  onkeyup="getAny(this.value, 'classLess', 'depart_class', 'Class', 'name')"
				 		  onclick="this.value='', document.getElementById('depart_class').value=''"/>
				 		 </td>
				 		 <td width="30" align="center" class="hairlineTdF">
				 		 <img src="images/16-exc-mark.gif" />
						 </td>
						
					</tr>
				</table>
				
				</td>
			</tr>
			
			
			<tr>
				<td class="fullColorTable" align="center">
				<INPUT type="submit"
						   name="method"
						   value="<bean:message key='Query'/>"
						   class="CourseButton"><INPUT type="submit"
													   name="method"
													   value="<bean:message
													   key='Clear'/>"
													   class="CourseButton"><input type="button" id="help" class="CourseButton" value="說明(H)" 
													   onMouseOver="showHelpMessage('沒有','inline', this.id)" onMouseOut="showHelpMessage('', 'none', this.id)" 
													   onClick="alert('....')"/>
				
				</td>
			</tr>
		</table>
		
<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>