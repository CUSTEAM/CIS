<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" width="30" align="center" nowrap>
		<img src="images/icon/icon_info_exclamation.gif" />
		</td>
		<td class="hairLineTdF" width="100%">
		查看${aStudent.ClassName}, ${aStudent.student_name} 歷年成績, 或 <a href=javascript:history.back()><b>回到學生列表</b></a>
		</td>
	</tr>
</table>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">開課班級</td>
		<td class="hairLineTdF">課程名稱</td>
		<td class="hairLineTdF">選別</td>
		<td class="hairLineTdF">學分</td>
		<td class="hairLineTdF">分數</td>
	</tr>
	<c:forEach items="${score}" var="m">
	<tr>
		<td class="hairLineTdF" width="100" nowrap>${m.ClassName}</td>
		<td class="hairLineTdF" width="200" nowrap>${m.chi_name}</td>
		<td class="hairLineTdF" width="20">${m.opt}</td>
		<td class="hairLineTdF" width="20">${m.credit}</td>
		<td class="hairLineTdF" width="20">${m.score}</td>
	</tr>
	</c:forEach>
</table>