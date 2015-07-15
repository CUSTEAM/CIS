<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<input type="hidden" name="Oid"/>
<table class="hairlineTable" width="99%">
	<tr>
		<td class="hairlineTdF" nowrap>科目代碼</td>
		<td class="hairlineTdF" nowrap>中文名稱</td>
		<td class="hairlineTdF" nowrap>英文名稱</td>
	</tr>		
	<tr>		
		<td class="hairlineTdF">
			<input type="text" name="cscode" id="cscode" size="8"
			autocomplete="off" style="ime-mode:disabled" autocomplete="off"
			value="${CscodeManagerForm.map.cscode[0]}" onClick="this.value='';"				
			onFocus="chInput(this.id)" />				
		</td>		
		<td class="hairlineTdF">
			<input type="text" autocomplete="off" name="chi_name" onClick="this.value='';"
			id="chi_name" size="16" value="${CscodeManagerForm.map.chi_name[0]}"
			onFocus="chInput(this.id)" />
		</td>
		<td class="hairlineTdF" width="100%">
			<input type="text" autocomplete="off" name="eng_name" onClick="this.value='';"
			id="eng_name" style="width:99%;" value="${CscodeManagerForm.map.eng_name[0]}"
			onFocus="chInput(this.id)"/>
		</td>
	</tr>
</table>