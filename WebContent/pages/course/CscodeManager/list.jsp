<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="99%" class="hairlineTable">
	<tr>
		<td class="hairlineTdF" nowrap>科目代碼</td>
		<td class="hairlineTdF" nowrap>中文名稱</td>
		<td class="hairlineTdF" nowrap>英文名稱</td>
		<td class="hairlineTdF" nowrap></td>
	</tr>
	<c:forEach items="${css}" var="c">
	<tr>
		<td class="hairlineTdF">
		<input type="hidden" name="Oid" id="check${c.Oid}"/>
		<input type="text" style="ime-mode:disabled" autocomplete="off" name="cscode"size="8" value="${c.cscode}" onMouseDown="document.getElementById('check${c.Oid}').value='${c.Oid}'" />
		</td>
		<td class="hairlineTdF">
		<input type="text" autocomplete="off" name="chi_name"size="16" value="${c.chi_name}" onMouseDown="document.getElementById('check${c.Oid}').value='${c.Oid}'"/>
		</td>
		<td class="hairlineTdF" nowrap width="100%">
		<input type="text" style="width:99%;" autocomplete="off" name="eng_name"size="16" value="${c.eng_name}" onMouseDown="document.getElementById('check${c.Oid}').value='${c.Oid}'"/>
		</td>
		<td class="hairlineTdF">
		<input type="submit" name="method" 
		value="<bean:message key='Modify'/>" 
		id="AddHoliday" class="gCancel">
		</td>
	</tr>
	</c:forEach>
</table>