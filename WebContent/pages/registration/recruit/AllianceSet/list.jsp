<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">學年</td>
		<td class="hairLineTdF">聯招學制</td>
		<td class="hairLineTdF">本校科系</td>
		<td class="hairLineTdF">報名人數</td>
		<td class="hairLineTdF">錄取人數</td>
		<td class="hairLineTdF">錄取率</td>
		<td class="hairLineTdF">刪除</td>
	</tr>
	<c:forEach items="${alls}" var="a">
	<tr>
		<td class="hairLineTdF">
		<input type="hidden" name="Oid" value="${a.Oid}"/>
		<input type="text" name="school_year" value="${a.school_year}" size="4"/>
		</td>
		<td class="hairLineTdF">
		<select name="SchoolNo">
			<option value="">所有學制</option>
			<c:forEach items="${schools}" var="s">
			<option <c:if test="${a.SchoolNo==s.idno}">selected</c:if> value="${s.idno}">${s.name}</option>
			</c:forEach>
		</select>
		
		</td>
		<td class="hairLineTdF">
		<select name="DeptNo">
			<option value="">所有科系</option>
			<c:forEach items="${depts}" var="d">
			<option <c:if test="${a.DeptNo==d.idno}">selected</c:if> value="${d.idno}">${d.name}</option>
			</c:forEach>
		</select>
		</td>
		<td class="hairLineTdF"><input type="text" name="enroll" value="${a.enroll}"/></td>
		<td class="hairLineTdF"><input type="text" name="real" value="${a.realin}"/></td>
		<td class="hairLineTdF" align="right">${a.engage}%</td>
		<td class="hairLineTdF"><a href="../Regstration/Recruit/Config/AllianceSet.do?dOid=${a.Oid}" target="_self">刪除</a></td>
	</tr>
	</c:forEach>
</table>