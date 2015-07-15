<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
	<tr>
		<td id="ds_calclass"></td>
	</tr>
</table>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td>
		<table>
			<tr>
				<td>
				
				
				<table class="hairLineTable">
					<tr>
						<td class="hairLineTdF">
						薪資日期
						</td>
						<td class="hairLineTd">						
						<input type="text" name="sdate" id="sdate" value="${BatchSalyManagerForm.map.sdate}" onFocus="chInput(this.id)"
						 onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
						</td>
						<td class="hairLineTdF" width="30" align="center">
						<img src="images/date.gif" />
						</td>
					</tr>
				</table>
				
				
				
				<table class="hairLineTable">
					<tr>
						<td class="hairLineTdF">
						編號姓名
						</td>
						<td class="hairLineTd" align="left">
						<input type="text" autocomplete="off" style="ime-mode:disabled" autocomplete="off" onClick="this.value=''; document.getElementById('fscname').value='';"
						name="idno" id="idno" size="12" value="${BatchSalyManagerForm.map.idno}" onFocus="chInput(this.id)"
						onkeyup="if(this.value.length>=2)GgetAny(this.value, 'idno', 'fscname', 'dempl', 'no')" 
						onMouseOver="showHelpMessage('身分證號輸入這裡, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 自動完成姓名', 'inline', this.id)" 
						onMouseOut="showHelpMessage('', 'none', this.id)"/><input onMouseOver="showHelpMessage('姓名輸入這裡, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 自動完成身分證號', 'inline', this.id)" 
						onMouseOut="showHelpMessage('', 'none', this.id)" value="${BatchSalyManagerForm.map.fscname}"
						type="text" name="fscname" id="fscname" size="10" onFocus="chInput(this.id)" onClick="this.value=''; document.getElementById('idno').value='';"
			 			onkeyup="if(this.value.length>0)GgetAny(this.value, 'fscname', 'idno', 'dempl', 'name')"/>
						</td>
						<td class="hairLineTdF" width="30" align="center">
						<img src="images/16-exc-mark.gif" />
						</td>
					</tr>
				</table>				
			
				</td>
			</tr>
		</table>
		
		
		
		
		
		
		
		
		
		</td>
	</tr>
	<tr class="fullColorTr" height="30">
		<td align="center">
		<INPUT type="submit"
						   name="method"
						   value="<bean:message key='Query'/>"
						   class="CourseButton"><INPUT type="submit"
													   name="method" value="<bean:message
													   key='Create'/>" id="addAll" class="CourseButton" onkeyup="if(this.value.length>=2)GgetAny(this.value, 'idno', 'fscname', 'dempl', 'no')" 
													   onMouseOver="showHelpMessage('同一日期若已建立薪資<br>會將舊有資料複蓋', 'inline', this.id)"
													   onMouseOut="showHelpMessage('', 'none', this.id)"/>
		</td>
	</tr>
</table>		