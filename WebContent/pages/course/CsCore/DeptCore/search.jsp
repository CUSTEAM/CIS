<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable">
	<tr>
		<td class="hairLineTdF">適用系所</td>
		<td class="hairLineTd">
		
		
		<select name="deptNo">
			<c:forEach items="${DeptList}" var="d">
			<option <c:if test="${StudentsCoreForm.map.deptNo==d.idno}">selected</c:if> value="${d.idno}">${d.name}</option>		
			</c:forEach>		
		</select>
		
		
		</td>
	</tr>
</table>