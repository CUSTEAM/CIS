<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table>
	<tr>
		<td>
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">活動名稱</td>
				<td class="hairLineTdF"><input type="hidden" name="Oid" /><input type="text" name="name" /></td>
			</tr>
		</table>
		</td>
	
		<td>
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">報名期間</td>
				<td class="hairLineTdF">
				<input type="text" name="sign_start" id="judge_start" size="4" readonly
				onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				<input type="text" name="sign_end" id="judge_start" size="4" readonly
				onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		
		<td>
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">評審期間</td>
				<td class="hairLineTdF">
				<input type="text" name="judge_start" id="judge_start" size="4" readonly
				onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				<input type="text" name="judge_end" id="judge_end" size="4" readonly
				onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				</td>
			</tr>
		</table>
		</td>
		
		<td>

		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">活動結束</td>
				<td class="hairLineTdF">
				<input type="text" name="end" id="end" size="4" readonly onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
		
				</td>
			</tr>
		</table>

<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
	<tr>
		<td id="ds_calclass"></td>
	</tr>
</table>
</td></tr></table>