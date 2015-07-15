<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">
		<OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" wmode="transparent"
			 codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0"
			 WIDTH="100%" HEIGHT="280" id="charts" ALIGN="center">
			<param name="wmode" value="transparent" />
			<PARAM NAME=movie VALUE=course/export/charts.swf?library_path=/CIS/pages/course/export/charts_library/&xml_source=/CIS/Print/course/csCoreChart.do?Oid=<%=request.getParameter("Oid")%>&<%=(int)(Math.random()*49)%>">
			<PARAM NAME=quality VALUE=high>
			<PARAM NAME=bgcolor VALUE=#f0fcd7>
			<EMBED src="course/export/charts.swf?library_path=/CIS/pages/course/export/charts_library/&xml_source=/CIS/Print/registration/csCoreChart.do?Oid=<%=request.getParameter("Oid")%>&<%=(int)(Math.random()*49)%>"
			quality=high bgcolor=#cccccc  WIDTH="100%" HEIGHT="350" NAME="charts" ALIGN="center"
			TYPE="application/x-shockwave-flash" PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer"></EMBED>
		</OBJECT>			
		</td>
	</tr>
</table>