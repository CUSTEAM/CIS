<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ include file="/taglibs.jsp"%>
<!-- 修改基本資料 -->
<%@ include file="editGroupBase.jsp"%>
	
<!-- 修改基本規則 -->
<%@ include file="editGroupRule.jsp"%>
	
<!-- 修改課程組合 -->
<%@ include file="editGroupCourse.jsp"%>
<br>
<table width="99%" class="hairLineTable">
	<tr>
		<td class="hairLineTdF">
		<img src="images/printer.gif" border="0">
			<select name="reportType" onchange="jumpMenu('parent',this,0)">
			<option value="javascript:void(0)">報表選擇</option>
			<option value="/CIS/List4CsGroup?type=groupSet">課程列表</option>
			<option value="/CIS/ListCsGroup4Now">本學期已開學程</option>
			</select>    							

		<script>
		<!--
		function jumpMenu(targ,selObj,restore){
				eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'")
				eval(targ+".location.target='_blank'");
				if (restore) selObj.selectedIndex=0;
			}
		//-->
		</script>
		</td>
	</tr>
</table>
<!-- 完成 -->	
<table cellspacing="0" cellpadding="0" width="100%">	
	<tr height="30">
		<td class="fullColorTable" align="center">
		<INPUT type="submit" name="method" value="<bean:message key='Complete'/>" class="CourseButton" />
		</td>
	</tr>
	
</table>

<script>
//顯示三種編輯介面
function showEdit(id){
	document.getElementById("groupBase").style.display="none";
	document.getElementById("groupRule").style.display="none";
	document.getElementById("groupCourse").style.display="none";
	
	document.getElementById(id).style.display="inline";
}
</script>