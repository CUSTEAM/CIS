<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">問卷期間自</td>
				<td class="hairLineTdF">		
				<input type="text" name="CoanswStart" value="${SetCsquestionaryForm.map.CoanswStart}" id="CoanswStart" style="font-size:18px;"
				onclick="ds_sh(this), dateEdit();" autocomplete="off" style="ime-mode:disabled" autocomplete="off" size="6"/>		
				</td>
				<td class="hairLineTdF" width="30" align="center">至</td>
				<td class="hairLineTdF">		
				<input type="text" name="CoanswEnd" value="${SetCsquestionaryForm.map.CoanswEnd}" id="CoanswEnd" style="font-size:18px;"
				onclick="ds_sh(this), dateEdit();" autocomplete="off" style="ime-mode:disabled" autocomplete="off" size="6"/>
				<input type="hidden" name="editDate" id="editDate" />
				</td>
				<td class="hairLineTdF"><INPUT type="submit" name="method" value="<bean:message key='SetQuestDate'/>" class="gSubmit"></td>			
			</tr>
		</table>
		<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
			<tr>
				<td id="ds_calclass"></td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		</td>
	</tr>
</table>