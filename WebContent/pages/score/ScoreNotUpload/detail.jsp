<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" width="99%">
		<input type="submit" name="method" class="gSubmit" value="<bean:message key='Save'/>" id="Save"
		onMouseOver="showHelpMessage('儲存', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)"/>
		
		<input type="button" class="gCancel" value="列印" 
		onClick="preview();">		
		
		<input type="button" class="gGreen" value="返回" 
		onClick="history.back();">
		</td>
	</tr>
</table>
<table class="hairLineTable" width="99%" id="table">
	<tr>
		<td class="hairLineTdF">學號</td>
		<td class="hairLineTdF">姓名</td>
		<td class="hairLineTdF">期中</td>
		<td class="hairLineTdF">期末</td>
		<td class="hairLineTdF">學期</td>
	</tr>
<c:forEach items="${detail}" var="d">
	<tr>
		<td class="hairLineTdF">${d.student_no}<input type="hidden" name="Oid" value="${d.Oid}" /></td>
		<td class="hairLineTdF">${d.student_name}</td>
		<td class="hairLineTdF"><input type="text" size="4" style="font-size:18px;" name="score2" value="${d.score2}" /></td>
		<td class="hairLineTdF"><input type="text" size="4" style="font-size:18px;" name="score3" value="${d.score3}" /></td>
		<td class="hairLineTdF"><input type="text" size="4" style="font-size:18px;" name="score" value="${d.score}" /></td>
	</tr>
</c:forEach>
</table>