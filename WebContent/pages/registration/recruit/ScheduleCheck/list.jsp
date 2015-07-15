<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">				
		<img src="images/printer.gif" border="0"/>		
		<select onchange="jumpMenu('parent',this,0)">
			<option value="javascript:void(0)">選擇報表</option>
			<option value="/CIS/Print/registration/ScheduleDoc.do">招生活動明細表</option>
			<option value="/CIS/Print/registration/ScheduleListDoc.do">招生活動統計表</option>
			
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
<display:table name="${scheduleChecks}" export="true" id="row" pagesize="20" sort="list" excludedParams="*" class="list">
<display:column title="學年" property="school_year" sortable="true"  />
<display:column title="科系" property="deptName" sortable="true" class="left" />
<display:column title="活動明細" property="link" sortable="true" class="center" />
<display:column title="招生學校" property="schoolName" sortable="true" class="left" href="../Print/registration/ScheduleDoc.do" paramId="Oid" paramProperty="Oid"/>
<display:column title="招生學校" property="schoolName" sortable="true" class="left" href="../Print/registration/ScheduleDoc.do" paramId="Oid" paramProperty="Oid"/>
<display:column title="活動名稱" property="name" sortable="true" class="left" />
<display:column title="項目" property="itemName" sortable="true" class="left" />
<display:column title="日期" property="someday" sortable="true" class="left" />
</display:table>