<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" width="30" align="center" nowrap>
		<img src="images/icon/icon_info_exclamation.gif" />
		</td>
		<td class="hairLineTdF" width="100%">
		查看${aStudent.ClassName}, ${aStudent.student_name} 核心能力, 或 <a href=javascript:history.back()><b>回到學生列表</b></a>
		</td>
	</tr>
</table>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF" width="50%">經標準化且基準為6之雷達圖</td>		
		<td class="hairLineTdF" width="50%">未經標準化且基準為0之雷達圖</td>
	</tr>
	<tr>
		<td class="hairLineTdF" width="50%">
		
		<OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" wmode="transparent"
			 codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0"
			 WIDTH="100%" HEIGHT="280" id="charts" ALIGN="center">
			<param name="wmode" value="transparent" />
			<PARAM NAME=movie VALUE=course/export/charts.swf?library_path=/CIS/pages/course/export/charts_library/&xml_source=/CIS/Print/course/StudentChartPlus.do?student_no=${student_no}&<%=(int)(Math.random()*49)%>">
			<PARAM NAME=quality VALUE=high>
			<PARAM NAME=bgcolor VALUE=#f0fcd7>
			<EMBED src="course/export/charts.swf?library_path=/CIS/pages/course/export/charts_library/&xml_source=/CIS/Print/course/StudentChartPlus.do?student_no=${student_no}&<%=(int)(Math.random()*49)%>"
			quality=high bgcolor=#cccccc  WIDTH="100%" HEIGHT="350" NAME="charts" ALIGN="center"
			TYPE="application/x-shockwave-flash" PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer"></EMBED>
		</OBJECT>
		
		
		</td>
		
		<td width="50%" class="hairLineTdF">
		<OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" wmode="transparent"
			 codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0"
			 WIDTH="100%" HEIGHT="280" id="charts" ALIGN="center">
			<param name="wmode" value="transparent" />
			<PARAM NAME=movie VALUE=course/export/charts.swf?library_path=/CIS/pages/course/export/charts_library/&xml_source=/CIS/Print/course/StudentChartNoPlus.do?student_no=${student_no}&<%=(int)(Math.random()*49)%>">
			<PARAM NAME=quality VALUE=high>
			<PARAM NAME=bgcolor VALUE=#f0fcd7>
			<EMBED src="course/export/charts.swf?library_path=/CIS/pages/course/export/charts_library/&xml_source=/CIS/Print/course/StudentChartNoPlus.do?student_no=${student_no}&<%=(int)(Math.random()*49)%>"
			quality=high bgcolor=#cccccc  WIDTH="100%" HEIGHT="350" NAME="charts" ALIGN="center"
			TYPE="application/x-shockwave-flash" PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer"></EMBED>
		</OBJECT>
		
		</td>
	</tr>
	
</table>