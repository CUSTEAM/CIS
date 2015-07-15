<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<tr>
	<td align="center">
	<c:if test="${CheckGist!=null}">

	<!-- %
	java.util.Random rand;
	rand=new java.util.Random();
	%-->

	<!-- OBJECT classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" 
	codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0"
	WIDTH="400"
	HEIGHT="275"
	id="charts"
	ALIGN="center"
	style="z-index:-99">

	<PARAM NAME=movie VALUE="course/export/charts.swf?library_path=/CIS/pages/course/export/charts_library/&xml_source=/CIS/AjaxGetChart4Gist?<!--%=rand%>">
	<PARAM NAME=quality VALUE=high>
	<PARAM NAME=bgcolor VALUE=#f0fcd7>
	<PARAM NAME=wmode VALUE=transparent>
	<EMBED src="course/export/charts.swf?library_path=/CIS/pages/course/export/charts_library/&xml_source=/CIS/AjaxGetChart4Gist?<!--%=rand%>"
       quality=high
       bgcolor=#666666
       WIDTH="400"
       HEIGHT="250"
       NAME="charts"
       ALIGN=""
       swLiveConnect="true"
       TYPE="application/x-shockwave-flash"
       PLUGINSPAGE="http://www.macromedia.com/go/getflashplayer">
	</EMBED>

	</OBJECT>
	-->
	
	<table width="98%" class="hairLineTable">
  				<tr>
    				<td class="hairLineTdF">
    					<table>
    						<tr>
    							<td>
    							<a href="course/export/list4CheckGist.jsp?type=excel">列印報表</a>
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>
		<display:table name="${CheckGist}" export="true" id="row" sort="list" excludedParams="*" class="list">
			
			<display:column title="開課班級" property="ClassName" sortable="true" class="left" />
			
			<display:column title="科目名稱" property="chi_name" sortable="true" class="left" />
			
			<display:column title="教師姓名" property="cname" sortable="true" class="left" />
			<display:column title="綱要字數" property="syllabi" sortable="true" class="right" />		
			<display:column title="每周綱要" property="syl_sub" sortable="true" class="right" />
			<display:column title="檢視大綱" property="syl" sortable="true" class="center" />
			<display:column title="中文簡介字數" property="chi" sortable="true" class="right" />
			<display:column title="英文簡介字數" property="eng" sortable="true" class="right" />
			<display:column title="檢視簡介" property="intr" sortable="true" class="center" />
			<display:column title="未編輯" property="err" sortable="true" class="center" />
		</display:table>

		<table width="98%" class="hairLineTable">
  				<tr>
    				<td class="hairLineTdF">
    					<table>
    						<tr>
    							<td>
    							<a href="course/export/list4CheckGist.jsp?type=excel">列印報表</a>
    							</td>
    						</tr>
    					</table>
    				</td>
  				</tr>
			</table>

		</td>
	</tr>
	
	<tr height="30">
		<td class="fullColorTable">
	
		</td>
	</tr>
	</c:if>
