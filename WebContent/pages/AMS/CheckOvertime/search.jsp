<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		
		
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">
				排班期間
				</td>
				<td class="hairLineTdF">
				<input id="startDate" name="begin" type="text" size="8" value="${CheckOvertimeForm.map.begin}"
				onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				</td>
				<td class="hairLineTdF" width="30" align="center">
				至
				</td>
				<td class="hairLineTdF">
				<input id="endDate" name="end" type="text" size="8" value="${CheckOvertimeForm.map.end}"
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