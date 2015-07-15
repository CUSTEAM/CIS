<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%">
	<tr>
		<td>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF" nowrap>學校名稱</td>
				<td class="hairLineTdF" nowrap>
					<input type="text" name="name" id="school_name" value="${SchoolManagerForm.map.name}" size="20"/>
					<input type="text" name="no" id="school_code" value="${SchoolManagerForm.map.no}" size="6" style="ime-mode:disabled" autocomplete="off" autocomplete="off"/>
				</td>
			</tr>
		</table>		
		</td>
	</tr>
	
	
	
	
</table>

<script>
function clearSchool(){

	document.getElementById("school_code").value="";
	document.getElementById("school_name").value="";
}
</script>
<c:import url="include/AjaxUniMod.jsp"/>
<%@ include file="/pages/include/ajaxGetMate.jsp" %>