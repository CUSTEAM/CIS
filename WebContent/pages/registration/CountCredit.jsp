<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<script src="/CIS/pages/include/decorate.js"></script>



<table width="100%" cellpadding="0" cellspacing="0" border="0">
<!-- 標題 start -->
<html:form action="/Registration/CountCredit" method="post" onsubmit="init('處理中, 請稍後')">
	<tr height="30">
		<td class="fullColorTable">		
		<div style="float:left; padding:0 5 0 5;"><img src="images/icon/table.gif"/></div>
		<div nowrap style="float:left;"><font class="gray_15">歷年成績排名</font></div>		
		</td>
	</tr>	
	<tr>
		<td>
		
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>		
				<td>			
				
				<table class="hairLineTable" cellpadding="5" cellspacing="5">
					<tr>
						<td class="hairLineTdF" nowrap>
						
						<select name="type" onChange="chmod(this.value)">							
							<option value='1'>畢業</option>
							<option value='0'>未畢業</option>
						</select>
						<input type="text" name="year" id="year" size="4" style="display:inline;" value="${CountCreditForm.map.year}"/>
						</td>
						
						
						
						<td class="hairLineTdF" nowrap>班級</td>
						<td class="hairLineTdF">
						
						<input type="text" id="classNo" name="classLess" style="font-size:18;"
						 size="8" autocomplete="off" style="ime-mode:disabled" autocomplete="off" value="${CountCreditForm.map.classLess}" 
				 		 onkeyup="if(this.value.length>2)getAny(this.value, 'classNo', 'className', 'Class', 'no')"
				 		 onclick="this.value='', document.getElementById('className').value=''"/>
				 		 <input type="text" name="className" value="${CountCreditForm.map.className}" style="font-size:18;"
						 id="className" onclick="this.value='', document.getElementById('classNo').value=''"/>
				 		 </td>
				 		 <td width="30" align="center" class="hairlineTdF">
				 		<img src="images/icon/icon_info.gif" id="ch" onMouseOver="showHelpMessage('輸入班級, 系統會根據項目找尋', 'inline', this.id)" 
						 onMouseOut="showHelpMessage('', 'none', this.id)"/>
						</td>	
						
						
						
						
						
						
<script>
function chmod(id){
	if(id=="0"){
		document.getElementById("year").style.display='none';
	}else{
		document.getElementById("year").style.display='inline';
	}
	
	
}


</script>						
						
						
						
						
						
						
						
						
									
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td class="fullColorTable" align="center">		
				<input type="submit" name="method" value="<bean:message key='Query'/>" onClick="setTimeout('uninit()', Math.floor(Math.random()*(300-1)));"
				id="Query" class="gSubmit" onMouseOver="showHelpMessage('依照以上條件查詢', 'inline', this.id)" 
				onMouseOut="showHelpMessage('', 'none', this.id)">
							
				
				</td>
			</tr>
		</table>
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		</td>
	</tr>
	<tr>
		<td>
		
		</td>
	</tr>
	
</html:form>
</table>
<%@ include file="/pages/include/AjaxUniMod.jsp" %>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>