<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF"><input type="button" class="gCancelSmall" value="檢視" onClick="showObj('oldterm')"/> 歷年選課與成績 </td>
	</tr>
</table>
<table class="hairLineTable" width="99%" id="oldterm">
	<tr>
		<td class="hairLineTdF">學年</td>
		<td class="hairLineTdF">學期</td>
		<td class="hairLineTdF" nowrap>課程代碼</td>
		<td class="hairLineTdF" nowrap>課程名稱</td>
		<td class="hairLineTdF">選別</td>		
		<td class="hairLineTdF">學分</td>
		<td class="hairLineTdF">型態</td>
		<td class="hairLineTdF">學期成績</td>
	</tr>	
<c:forEach items="${scorehist}" var="s">
	<tr>
		<td class="hairLineTdF">
		
		
		<input autocomplete="off" autocomplete="off" style="ime-mode:disabled; font-size:18px;" 
		size="1" type="text" name="school_year" value="${s.school_year}" maxlength="3"/>
		</td>
		
		<td class="hairLineTdF">
		<input autocomplete="off" autocomplete="off" style="ime-mode:disabled; font-size:18px;" 
		size="1" type="text" name="school_term" value="${s.school_term}" maxlength="3"/>
		</td>
		
		<td class="hairLineTdF">
		
		<input autocomplete="off" autocomplete="off" style="ime-mode:disabled; font-size:18px;" 
		size="2" type="text" name="cscode" value="${s.cscode}"/>
		</td>
		
		<td class="hairLineTdF" style="font-size:13px;">${s.chi_name}</td>
		
		<td class="hairLineTdF">
		<select style="font-size:18px" name="opt">			
			<option <c:if test="${s.opt=='1'}">selected</c:if> value="1">必修</option>
			<option <c:if test="${s.opt=='2'}">selected</c:if> value="2">選修</option>
			<option <c:if test="${s.opt=='3'}">selected</c:if> value="3">通識</option>
		</select>		
		</td>		
		
		<td class="hairLineTdF">
		<input autocomplete="off" autocomplete="off" style="ime-mode:disabled; font-size:18px;" 
		size="1" type="text" name="credit" value="${s.credit}"/>		
		</td>
		
		<td class="hairLineTdF">		
		<select style="font-size:18px" name="evgr_type">
			<c:forEach items="${CourseType}" var="c">
			<option <c:if test="${s.evgr_type==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
			</c:forEach>
		</select>
		</td>
		
		<td class="hairLineTdF" nowrap>
		<input type="hidden" name="Oid" id="Oid${s.Oid}" value="">
		<input autocomplete="off" autocomplete="off" style="ime-mode:disabled; font-size:18px;" 
		size="1" type="text" name="score" value="${s.score}" maxlength="3"/>
		<input type="submit" name="method" value="<bean:message key='Modify'/>" 
		onClick="act('Oid${s.Oid}', '${s.Oid}');" class="gSubmitSmall">
		<input type="submit" name="method" value="<bean:message key='Delete'/>" 
		onClick="act('Oid${s.Oid}', '${s.Oid}');" class="gCancelSmall">
		</td>
	</tr>
</c:forEach>
</table>

<script>
function act(id, value){
	document.getElementById(id).value=value;
}
</script>