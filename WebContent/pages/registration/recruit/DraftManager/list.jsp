<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">學年</td>
		<td class="hairLineTdF">學校名稱</td>
		<td class="hairLineTdF">聯招學制</td>
		<td class="hairLineTdF">本校科系</td>
		<td class="hairLineTdF">核定名額</td>
		<td class="hairLineTdF">錄取名額</td>
		<td class="hairLineTdF">報到人數</td>
		<td class="hairLineTdF">錄取率</td>
		<td class="hairLineTdF">報到率</td>
		<td class="hairLineTdF">刪除</td>
	</tr>
	<c:forEach items="${alls}" var="a">
	<tr>
		<td class="hairLineTdF">
		<input type="hidden" class="smallInput" name="Oid" value="${a.Oid}"/>
		<input type="text" class="smallInput" name="school_year" value="${a.school_year}" size="3"/>		
		</td>
		
		<td class="hairLineTdF" nowrap>
		
		<!-- input type="text" class="smallInput" disabled value="${a.name}" size="18"/>
		<input type="text" class="smallInput" disabled value="${a.school_code}" size="6"/-->		
		${a.name}
		</td>
		
		<td class="hairLineTdF">
		<select name="SchoolNo" class="smallInput">
			<option value=""></option>
			<c:forEach items="${schools}" var="s">
			<option <c:if test="${a.SchoolNo==s.idno}">selected</c:if> value="${s.idno}">${s.name}</option>
			</c:forEach>
		</select>
		
		</td>
		<td class="hairLineTdF">
		<select name="DeptNo" class="smallInput">
			<option value=""></option>
			<c:forEach items="${depts}" var="d">
			<option <c:if test="${a.DeptNo==d.idno}">selected</c:if> value="${d.idno}">${d.name}</option>
			</c:forEach>
		</select>
		</td>
		<td class="hairLineTdF"><input class="smallInput" type="text" size="4" name="standard" value="${a.standard}"/></td>
		<td class="hairLineTdF"><input class="smallInput" type="text" size="4" name="enroll" value="${a.enroll}"/></td>
		<td class="hairLineTdF"><input class="smallInput" type="text" size="4" name="real" value="${a.realin}"/></td>
		
		<td class="hairLineTdF" align="right">${a.engage}%</td>
		<td class="hairLineTdF" align="right">${a.checkin}%</td>
		<td class="hairLineTdF"><a href="../Regstration/Recruit/Config/DraftManager.do?dOid=${a.Oid}" target="_self">刪除</a></td>
	</tr>
	</c:forEach>
</table>