<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">				
		<img src="images/printer.gif" border="0"/>		
		<select onchange="jumpMenu('parent',this,0)">
			<option value="javascript:void(0)">選擇報表</option>
			<option value="/CIS/Print/registration/RecruitQueryList.do">學生報表</option>
		</select>
		<script>
			function jumpMenu(targ,selObj,restore){
				eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'")
				eval(targ+".location.target='_blank'");
				if (restore) selObj.selectedIndex=0;
		}
		</script>
		</td>
	</tr>
</table>
<display:table name="${stds}" export="true" id="row" pagesize="10" sort="list" excludedParams="*" class="list">
<display:column title="入學年月" property="entrance" sortable="true"  />
<display:column title="班級" property="ClassName" sortable="true"  />
<display:column title="學號"  property="student_no" sortable="true"  />
<display:column title="姓名" property="student_name" sortable="true" class="left" />
<display:column title="畢業學校" property="SchoolName" sortable="true" class="left" />
<display:column title="畢業科系" property="grad_dept" sortable="true" class="left" />

<display:column title="入學身份" property="IdName" sortable="true" class="left" />
</display:table>