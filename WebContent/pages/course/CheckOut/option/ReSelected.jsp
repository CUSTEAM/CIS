<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<tr>
	<td>
	<display:table name="${reSelect}" export="true" id="row" sort="list" excludedParams="*" class="list">
		<display:column title="學年" property="school_year" sortable="true" class="left" />
		<display:column title="學號" property="student_no" sortable="true" class="left" />
		<display:column title="姓名" property="student_name" sortable="true" class="left" />
		<display:column title="班級" property="ClassName" sortable="true" class="left" />
		<display:column title="型態" property="name" sortable="true" class="left" />
		<display:column title="課程代碼" property="cscode" sortable="true" class="left" />
		<display:column title="課程名稱" property="chi_name" sortable="true" class="left" />
		<display:column title="成績" property="score" sortable="true" class="left" />
		
	</display:table>
	
	
	</td>
</tr>
<tr height="30">
	<td class="fullColorTable">
	
	</td>
</tr>