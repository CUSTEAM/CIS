<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable" align="left">
	<tr>
		<td class="hairLineTdF" nowrap>收件人名稱</td>
		<td class="hairLineTdF" nowrap>
			<input type="text" name="name" id="school_name" value="${MailListManagerForm.map.name}" onClick="clearSchool();"
			onkeyup="if(this.value.length>1)getAny(this.value, 'school_name', 'school_code', 'cnamEmail', 'name')" size="8"/>
			<input type="text" name="mailaddress" id="school_code" value="${MailListManagerForm.map.mailaddress}"
			size="36" style="ime-mode:disabled" autocomplete="off" autocomplete="off"/>			
		</td>
	</tr>
</table>




<script>
function clearSchool(){
	document.getElementById("school_code").value="";
	document.getElementById("school_name").value="";
}
</script>