<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">		
		<OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" wmode="transparent"
			 codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0"
			 WIDTH="100%" HEIGHT="280" id="charts" ALIGN="center">
			<param name="wmode" value="transparent" />
			<PARAM NAME=movie VALUE=/CIS/pages/course/export/charts.swf?library_path=/CIS/pages/course/export/charts_library/&xml_source=/CIS/Print/course/DepTotalChart.do?DeptNo=${DeptNo}&<%=(int)(Math.random()*49)%>">
			<PARAM NAME=quality VALUE=high>
			<PARAM NAME=bgcolor VALUE=#f0fcd7>
			<EMBED src="/CIS/pages/course/export/charts.swf?library_path=/CIS/pages/course/export/charts_library/&xml_source=/CIS/Print/registration/DepTotalChart.do?DeptNo=${DeptNo}&<%=(int)(Math.random()*49)%>"
			quality=high bgcolor=#cccccc  WIDTH="100%" HEIGHT="350" NAME="charts" ALIGN="center"
			TYPE="application/x-shockwave-flash" PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer"></EMBED>
		</OBJECT>
		</td>
	</tr>
</table>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" width="100%">關鍵字</td>
		<td class="hairLineTdF" nowrap>技術</td>
		<td class="hairLineTdF" nowrap>知識</td>
		<td class="hairLineTdF" nowrap>資格</td>
		<td class="hairLineTdF" nowrap>進取</td>
		<td class="hairLineTdF" nowrap>溝通</td>
		<td class="hairLineTdF" nowrap>團隊</td>
		<td class="hairLineTdF" nowrap>科技</td>
		<td class="hairLineTdF" nowrap>解決問題</td>
		<td class="hairLineTdF" nowrap>自我管理</td>
		<td class="hairLineTdF" nowrap>規劃</td>
		<td class="hairLineTdF" nowrap>學習</td>
	</tr>
	<c:forEach items="${dcores}" var="d">
	<tr>
		<td class="hairLineTdF"><a href="../Course/CsCoreSystem/StudentsCore.do?Oid=${c.Oid}">${d.key_word}</a></td>
		<td class="hairLineTdF">${d.s1}</td>
		<td class="hairLineTdF">${d.s2}</td>
		<td class="hairLineTdF">${d.s3}</td>
		<td class="hairLineTdF">${d.s4}</td>
		<td class="hairLineTdF">${d.s5}</td>
		<td class="hairLineTdF">${d.s6}</td>
		<td class="hairLineTdF">${d.s7}</td>
		<td class="hairLineTdF">${d.s8}</td>
		<td class="hairLineTdF">${d.s9}</td>
		<td class="hairLineTdF">${d.sa}</td>
		<td class="hairLineTdF">${d.sb}</td>
	</tr>	
	</c:forEach>
</table>