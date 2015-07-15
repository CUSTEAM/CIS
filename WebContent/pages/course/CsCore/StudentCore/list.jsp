<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">學生班級</td>
		<td class="hairLineTdF">學號</td>
		<td class="hairLineTdF">姓名</td>
		<td class="hairLineTdF" align="right">必修應修</td>
		<td class="hairLineTdF" align="right">選修應修</td>
		<td class="hairLineTdF" align="right">通識應修</td>
		<!-- td class="hairLineTdF" align="right">全部應修</td-->
		<td class="hairLineTdF" align="right">已得學分</td>
		
	</tr>

<c:forEach items="${students}" var="s">
	<tr>
		<td class="hairLineTdF" style="font-size:14pt;">${s.ClassName}</td>
		<td class="hairLineTdF"><a target="_top" href="../Course/CsCoreSystem/StudentCore.do?student_no=${s.student_no}"><font size="+1">${s.student_no}</font></a></td>
		<td class="hairLineTdF"><a target="_top" href="../Course/CsCoreSystem/StudentCore.do?student_no=${s.student_no}"><font size="+1">${s.student_name}</font></a></td>
		
		
		
		
		<td class="hairLineTdF" align="right" style="font-size:14pt;">${grade.opt1}</td>
		<td class="hairLineTdF" align="right" style="font-size:14pt;">${grade.opt2}</td>
		<td class="hairLineTdF" align="right" style="font-size:14pt;">${grade.opt3}</td>
		<!-- td class="hairLineTdF" align="right" style="font-size:14pt;">${grade.opt1+grade.opt2+grade.opt3}</td-->
		<td class="hairLineTdF" align="right" style="font-size:14pt;">${s.credit}</td>
	</tr>
</c:forEach>
</table>