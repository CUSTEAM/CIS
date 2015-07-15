<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF"><input type="button" class="gCancelSmall" value="檢視" onClick="showObj('thisterm')"/> 本學期選課與成績</td>
	</tr>
</table>
<table class="hairLineTable" width="99%" id="thisterm" style="display:none;">
	<tr>
		<td class="hairLineTdF" nowrap>代碼</td>
		<td class="hairLineTdF" nowrap>名稱</td>
		<td class="hairLineTdF" nowrap>選別</td>		
		<td class="hairLineTdF" nowrap>學分</td>
		<td class="hairLineTdF" nowrap>時數</td>
		<td class="hairLineTdF" nowrap>平時</td>
		<td class="hairLineTdF" nowrap>期中</td>
		<td class="hairLineTdF" nowrap>期末</td>
		<td class="hairLineTdF" nowrap>總成績</td>

	</tr>
<c:forEach items="${selds}" var="s">
	<tr>
		<td class="hairLineTdF">${s.cscode}</td>
		<td class="hairLineTdF" style="font-size:13px;">${s.chi_name}</td>
		<td class="hairLineTdF">${s.opt}</td>		
		<td class="hairLineTdF">${s.credit}</td>
		<td class="hairLineTdF">${s.thour}</td>
		<td class="hairLineTdF">${s.score1}</td>
		<td class="hairLineTdF">${s.score2}</td>
		<td class="hairLineTdF">${s.score3}</td>
		<td class="hairLineTdF">${s.score}</td>
	</tr>
</c:forEach>
</table>