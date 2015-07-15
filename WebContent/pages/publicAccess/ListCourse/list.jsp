<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<display:table name="${csses}" export="false" id="row" sort="list" excludedParams="*" class="list" >
	        
	<display:column title="學期" property="Sterm" sortable="true" class="left" />
  	<display:column title="課程編號" property="Oid" sortable="true" class="left" />
  	<display:column title="課程名稱" property="chi_name" sortable="true" class="left"/>
  	<display:column title="開課班級(課表)" property="ClassName" sortable="true" class="left" />
  	<display:column title="授課教師(課表)" property="cname" sortable="true" class="left" />
  	<display:column title="選別" property="opt" sortable="true" class="left" />
  	<display:column title="學分" property="credit" sortable="true" class="left" />
  	<display:column title="時數" property="thour" sortable="true" class="left" />
  	<display:column title="選修" property="open" sortable="true" class="left" />
  	<display:column title="男/女" property="Select_Limit" class="right" sortable="true" />
  	<display:column title="大綱" property="Syl" class="center" sortable="true" />
  	<display:column title="簡介" property="Int" class="center" sortable="true" />
  	<display:column title="時間地點(教室課表)" property="time" sortable="true" class="left" />
</display:table>