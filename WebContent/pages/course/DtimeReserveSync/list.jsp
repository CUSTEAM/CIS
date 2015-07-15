<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<display:table name="${reserve}" export="true" id="row" sort="list" excludedParams="*" class="list">
	<display:column title="班級名稱" property="ClassName" sortable="true" class="left" />
	<display:column title="課程名稱" property="chi_name" sortable="false" class="left" />
	<display:column title="選別" property="opt" sortable="false" class="left" />
	<display:column title="學分"  property="credit" sortable="true" class="left" />
	<display:column title="時數"  property="thour" sortable="true" class="right" />
	<display:column title="教師"  property="cname" sortable="true" class="right" />
	<display:column title="排課數"  property="cnt" sortable="true" class="right" />
	<display:column title="跨選規則數"  property="cnt1" sortable="true" class="right" />
</display:table>