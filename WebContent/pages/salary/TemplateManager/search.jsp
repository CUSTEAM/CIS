<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td>
		<table>
			<tr>
				<td>
				
				<table class="hairLineTable">
					<tr>
						<td class="hairLineTdF">
						編號姓名
						</td>
						<td class="hairLineTd" align="left">
						<input type="text" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
						name="idno" id="idno" size="12" onFocus="chBigInput(this.id)" class="bigInput"
						onkeyup="if(this.value.length>=2)GgetAny(this.value, 'idno', 'fscname', 'dempl', 'no')" 
						onMouseOver="showHelpMessage('身分證號輸入這裡, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 自動完成姓名', 'inline', this.id)" 
						onMouseOut="showHelpMessage('', 'none', this.id)"/><input onMouseOver="showHelpMessage('姓名輸入這裡, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 自動完成身分證號', 'inline', this.id)" 
						onMouseOut="showHelpMessage('', 'none', this.id)" class="bigInput"
						type="text" name="fscname" id="fscname" size="10" onFocus="chBigInput(this.id)"
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
			   id="continue"
			   onMouseOver="showHelpMessage('查詢', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)"
			   value="<bean:message key='Continue'/>"
			   class="gSubmit">
						   
		<INPUT type="submit"
			   name="method" id="Cancel"
			   value="<bean:message
			   key='Cancel'/>"
			   class="gCancle" 
			   onMouseOver="showHelpMessage('重設所有查詢條件', 'inline', this.id)" 
			   onMouseOut="showHelpMessage('', 'none', this.id)" />
		</td>
	</tr>
</table>		