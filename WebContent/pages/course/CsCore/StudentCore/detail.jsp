<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" colspan="16">
		<b>${astudent.student_name}</b> 同學取得的核心課程成績如下
		</td>
	</tr>
	<tr>
		<td class="hairLineTdF" nowrap>核心關鍵字</td>
		<td class="hairLineTdF" nowrap>課程名稱</td>
		<td class="hairLineTdF" nowrap>技術</td>
		<td class="hairLineTdF" nowrap>知識</td>
		
		<td class="hairLineTdF" nowrap>資格</td>
		<td class="hairLineTdF" nowrap>進取</td>
		<td class="hairLineTdF" nowrap>溝通</td>
		<td class="hairLineTdF" nowrap>團隊</td>
		<td class="hairLineTdF" nowrap>科技</td>
		<td class="hairLineTdF" nowrap><font size="-2">解決問題</font></td>
		<td class="hairLineTdF" nowrap><font size="-2">自我管理</font></td>
		<td class="hairLineTdF" nowrap>規劃</td>
		<td class="hairLineTdF" nowrap>學習</td>
		
		
		<td class="hairLineTdF" nowrap>學分</td>
		<td class="hairLineTdF" nowrap>成績</td>	
	</tr>
	
	
	<c:forEach items="${scores}" var="s">
	<tr>
		<td class="hairLineTdF" nowrap>${s.key_word}</td>
		<td class="hairLineTdF">${s.ClassName}-${s.chi_name}</td>
		<td class="hairLineTdF">${s.s1}</td>
		<td class="hairLineTdF">${s.s2}</td>
		
		<td class="hairLineTdF"nowrap>${s.s3}</td>
		<td class="hairLineTdF"nowrap>${s.s4}</td>
		<td class="hairLineTdF"nowrap>${s.s5}</td>
		<td class="hairLineTdF"nowrap>${s.s6}</td>
		<td class="hairLineTdF"nowrap>${s.s7}</td>
		<td class="hairLineTdF"nowrap>${s.s8}</td>
		<td class="hairLineTdF"nowrap>${s.s9}</td>
		<td class="hairLineTdF" nowrap>${s.sa}</td>
		<td class="hairLineTdF" nowrap>${s.sb}</td>
		
		
		<td class="hairLineTdF" nowrap>${s.credit}</td>
		<td class="hairLineTdF" nowrap>${s.score}</td>	
	</tr>
	</c:forEach>
</table>






