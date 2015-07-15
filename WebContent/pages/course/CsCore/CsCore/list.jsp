<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">開課班級</td>
		<td class="hairLineTdF">課程名稱</td>
		<td class="hairLineTdF">課程資訊</td>
	</tr>

<c:forEach items="${cores}" var="c">
	<tr>
		<td class="hairLineTdF"><a target="_blank" href="/CIS/ClassCourseSearch_guide.do?classInCharge=${c.ClassNo}">${c.ClassName}</a></td>
		<td class="hairLineTdF" nowrap>
		<a target="_top" href="../Course/CsCoreSystem/CsCore.do?Oid=${c.Oid}">${c.cscode} - ${c.chi_name}</a>
		</td>
		<td class="hairLineTdF" nowrap><a target="_blank" href="/CIS/Print/teacher/SylDoc.do?Oid=${c.Oid}">課程大綱</a> - 
		<a target="_blank" href="/CIS/Print/teacher/IntorDoc.do?Oid=${c.Oid}">中英文簡介</a></td>
	</tr>
</c:forEach>
</table>