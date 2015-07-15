<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<tr>
	<td>
	<display:table name="${ListSeld4Class}" export="true" id="row" sort="list" excludedParams="*" class="list">
		<display:column title="班級代碼" property="ClassNo" sortable="true" class="left" />
		<display:column title="學生班級" property="ClassName" sortable="true" class="left" />
		<display:column title="學號" property="student_no" sortable="true" class="left" />
		
		<display:column title="姓名" property="student_name" sortable="true" class="left" />
		<display:column title="開課班級" property="depart_class" sortable="true" class="left" />
		<display:column title="課程名稱" property="chi_name" sortable="true" class="left" />
		<display:column title="選別" property="opt" sortable="true" class="left" />
		<display:column title="學分" property="credit" class="left" />
		<display:column title="時數" property="thour" class="left" />
		
	</display:table>
	
	<table class="hairLineTable" width="99%">
		<tr>
			<td class="hairLineTdF">
			<a href="/CIS/Print/course/ListSeld4Class.do">產生報表</a>
			</td>
		</tr>
	</table>
	
	</td>
</tr>
<tr height="30">
	<td class="fullColorTable">
	
	</td>
</tr>