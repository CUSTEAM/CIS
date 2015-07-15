<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<display:table name="${result}" export="true" id="row" sort="list" excludedParams="*" class="list">
	<display:column title="單位名稱" property="name" sortable="true" class="left" />
	<display:column title="員工編號" property="idno" sortable="false" class="left" />
	<display:column title="員工姓名" property="cname" sortable="false" class="left" />
	<display:column title="班別"  property="WorkShift" sortable="true" class="left" />
	
	<display:column title="上班超時"  property="in" sortable="true" class="right" />
	<display:column title="下班超時"  property="out" sortable="true" class="right" />
	
	
	
	<display:column title="累計超時工作"  property="all" sortable="true" class="right" />
	<display:column title="統計"  property="total" class="right" />
	<display:column title="申報加班次數"  property="app" sortable="true" class="right" />
	
</display:table>