<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<display:table name="${schedules}" export="false" pagesize="20" sort="list" excludedParams="*" class="list" style="width:100%;">
	<display:column title="學年" property="school_year" sortable="true" class="center" />
	<display:column title="日期" property="someday" href="../Regstration/Recruit/ScheduleManager.do" paramId="Oid" paramProperty="Oid" sortable="true" class="left" />
	<display:column title="活動名稱" property="name" href="../Regstration/Recruit/ScheduleManager.do" paramId="Oid" paramProperty="Oid" sortable="true" class="left" />
	<display:column title="項目" property="itemName" href="../Regstration/Recruit/ScheduleManager.do" paramId="Oid" paramProperty="Oid" sortable="true" class="left" />
	<display:column title="目標學校" property="schoolName" href="../Regstration/Recruit/ScheduleManager.do" paramId="Oid" paramProperty="Oid" sortable="true" class="left" />
	<display:column title="主辦系所" property="deptName" href="../Regstration/Recruit/ScheduleManager.do" paramId="Oid" paramProperty="Oid" sortable="true" class="left" />
	<display:column title="連絡人" property="leader" href="../Regstration/Recruit/ScheduleManager.do" paramId="Oid" paramProperty="Oid" sortable="true" class="left" />	
</display:table>