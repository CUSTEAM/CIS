<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<tr>
	<td align="center">
	<table class="hairLineTable" width="99%">
		<tr>
			<td class="hairLineTdF">
			<a href="/CIS/pages/course/export/CheckThour.jsp">建立報表</a>
			</td>
		</tr>
	</table>
	<display:table name="${techs}" export="true" id="row" sort="list" excludedParams="*" class="list">
		<display:column title="系所名稱" property="name" sortable="true" class="left" />
		<display:column title="教師姓名" property="cname" sortable="true" class="left" />
		<display:column title="職稱" property="sname" sortable="true" class="left" />
		<display:column title="基本時數" property="time" sortable="true" class="left" />
		<display:column title="可超時數" property="time_over" sortable="true" class="left" />
		<display:column title="排課時數" property="total" sortable="true" class="left" />
		<display:column title="排課天數" property="day" sortable="true" class="left" />
		<display:column title="備註"/>
	</display:table>
	</td>
</tr>
<tr height="30">
	<td class="fullColorTable">
	
	</td>
</tr>
