<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/pages/include/Displaytag4Checkbox.inc"%>
<display:table name="${rooms}" pagesize="10" id="row" sort="list" class="list">
<display:column title="<script>generateTriggerAll(${fn:length(rooms)}, 'roms'); </script>" class="center" >
<script>generateCheckbox("${row.Oid}", "roms")</script>
</display:column>        
<display:column title="編號" property="room_id" sortable="true" class="center" />
<display:column title="單位" property="unitName" sortable="true" class="left" />
<display:column title="系所" property="deptName" sortable="true" class="left" />         
<display:column title="大樓" property="buildName" sortable="true" class="center" />		
<display:column title="樓層" property="floor" sortable="true" class="center" />		
<display:column title="座位" property="seat" sortable="true" class="center" />
<display:column title="租借" property="boro" sortable="true" class="center" />
<display:column title="名稱" property="name2" sortable="true" class="center" />
</display:table>

<table class="hairLineTable" width="99%">
  	<tr>
    	<td class="hairLineTdF">    							
		<img src="images/printer.gif" border="0">
		<select name="reportType" onchange="jumpMenu('parent',this,0)" id="inSchool"
		 onMouseOver="showHelpMessage('校內用的表格', 'inline', this.id)"
		 onMouseOut="showHelpMessage('', 'none', this.id)">
			<option value="javascript:void(0)">校內表格</option>
			<option value="javascript:void(0)">通用報表 - 不分學期</option>
			<option value="/CIS/RoomTimetable?term=1">教室課表 - 第1學期</option>
			<option value="/CIS/RoomTimetable?term=2">教室課表 - 第2學期</option>
		</select>    							
 		</td>
	</tr>
</table>

<script>
    							
function jumpMenu(targ,selObj,restore){
	eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'")
	eval(targ+".location.target='_blank'");
	if (restore) selObj.selectedIndex=0;
}
</script>