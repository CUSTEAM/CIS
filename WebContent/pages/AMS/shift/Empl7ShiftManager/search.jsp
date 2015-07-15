<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%">
	<tr>		
		<td>		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF" id="pifname" onMouseOver="showHelpMessage('快', 'inline', this.id)" 
 						onMouseOut="showHelpMessage('', 'none', this.id)">
				編號姓名
				</td>
				<td class="hairLineTd" align="left">
				<input type="text" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
				name="id" id="fsidno" size="12" value="${Empl7ShiftManagerForm.map.id}" onFocus="chInput(this.id)"
				onkeyup="if(this.value.length>=2)GgetAny(this.value, 'fsidno', 'fscname', 'dempl', 'no')" 
				onMouseOver="showHelpMessage('身分證號輸入這裡, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 自動完成姓名', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)" /><input onFocus="chInput(this.id)"
				
				onMouseOver="showHelpMessage('姓名輸入這裡, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 自動完成身分證號', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)"
				type="text" name="name" id="fscname" size="10" value="${Empl7ShiftManagerForm.map.name}"
	 			onkeyup="if(this.value.length>0)GgetAny(this.value, 'fscname', 'fsidno', 'dempl', 'name')" />
				</td>
				<td class="hairLineTdF" width="30" align="center">
				<img src="images/icon/icon_info.gif"  id="helpIdName"
				onMouseOver="showHelpMessage('身分證號輸入左側空格, 姓名輸入右側,<br>若您貼上文字, 請按一下鍵盤右側的方向鍵, <BR>即可自動完成輸入', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)"/>
				</td>
			</tr>
		</table>
		
		</td>		
	</tr>
	<tr>
		<td>
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">
				排班期間
				</td>
				<td class="hairLineTdF">
				<input id="startDate" name="startDate" type="text" size="4" value="${Empl7ShiftManagerForm.map.startDate}"
				onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				</td>
				<td class="hairLineTdF" width="30" align="center">
				至
				</td>
				<td class="hairLineTdF">
				<input id="endDate" name="endDate" type="text" size="4" value="${Empl7ShiftManagerForm.map.endDate}"
				onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				</td>
				<td class="hairLineTdF" width="30" align="center">
				<img src="images/icon/icon_info.gif"  id="helpDate"
				onMouseOver="showHelpMessage('開始日期輸入左邊, 結束日期輸入右邊', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)"/>
				</td>
			</tr>
		</table>
		
		<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
			<tr>
				<td id="ds_calclass"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>