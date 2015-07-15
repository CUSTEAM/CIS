<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
	<tr>
		<td id="ds_calclass"></td>
	</tr>
</table>
<table class="hairLineTable" width="99%">
	
	<tr>
		<td colspan="4" align="center" class="hairLineTd">學生資訊</td>
		<td colspan="4" align="center" class="hairLineTd">休學資訊</td>
		<td colspan="4" align="center" class="hairLineTd">復學資訊</td>	
	</tr>
	
	<tr>
		<td class="hairLineTdF" align="center"><img src="images/icon/user_cross.gif"/></td>
		<td class="hairLineTdF" nowrap>辦理時就讀班級</td>
		<td class="hairLineTdF" nowrap>學號</td>
		<td class="hairLineTdF" nowrap>姓名</td>
		
		<td class="hairLineTdF" nowrap>學年</td>
		<td class="hairLineTdF" nowrap>學期</td>
		<td class="hairLineTdF" nowrap>日期</td>
		<td class="hairLineTdF" nowrap><font size="-2">文號/備註</font></td>
		
		<td class="hairLineTdF" nowrap>學年</td>
		<td class="hairLineTdF" nowrap>學期</td>
		<td class="hairLineTdF" nowrap>日期</td>
		<td class="hairLineTdF" nowrap6><font size="-2">文號/備註</font></td>	
	</tr>
	
<script>
function checkDelete(id){
	if(document.getElementById(id).value=="D"){
		document.getElementById(id).value="";
	}else{
		document.getElementById(id).value="D";
	}	
}

function checkModify(id){
	document.getElementById(id).value="M";
}

function clearClasses(Oid){
	document.getElementById("depart_class"+Oid).value="";
	document.getElementById("depart_className"+Oid).value="";
}
</script>
<c:forEach items="${students}" var="s">
	<tr onClick="checkModify('CheckChange${s.Oid}')">
		<td class="hairLineTdF" align="center">
			<input type="checkBox" onClick="checkDelete('CheckOid${s.Oid}')"/>
			<input type="hidden" name="CheckOid" id="CheckOid${s.Oid}"/>
			<input type="hidden" name="CheckChange" id="CheckChange${s.Oid}"/>
			<input type="hidden" name="StudentNo" value="${s.student_no}"/>			
		</td>
		<td class="hairLineTdF">		
			<input class="smallInput" type="text" id="depart_class${s.Oid}" name="depart_class" size="6" 
			autocomplete="off" style="ime-mode:disabled" autocomplete="off"
			value="${s.depart_class}" onkeyup="if(this.value.length>2)getAny(this.value, 'depart_class${s.Oid}', 
			'depart_className${s.Oid}', 'Class', 'no')"/><input class="smallInput"
			type="text" name="depart_className" id="depart_className${s.Oid}" value="${s.ClassName}" 
			size="12" onkeyup="getAny(this.value, 'depart_className${s.Oid}', 'depart_class${s.Oid}', 'Class', 'name')"/>		
		</td>
		
		<td class="hairLineTdF" nowrap>${s.student_no}</td>
		<td class="hairLineTdF" nowrap>${s.student_name}</td>
		<td class="hairLineTdF">
			<input type="hidden" name="Oid" value="${s.Oid}" />
			<input type="text" class="smallInput" name="occur_year" value="${s.occur_year}" size="3" /> 		
		</td>
		<td class="hairLineTdF">
			<input type="text" class="smallInput" name="occur_term" value="${s.occur_term}" size="2" />
		</td>
		<td class="hairLineTdF">
		<input name="occur_date" id="occur_date${s.Oid}" type="text" size="9" class="smallInput" value="${s.occur_date}"
		onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
		</td>
		<td class="hairLineTdF">
		<input name="occur_docno" type="text" size="6" class="smallInput" value="${s.occur_docno}" />
		</td>		
		
		<td class="hairLineTdF">
			<input type="text" class="smallInput" name="recov_year" value="${s.recov_year}" size="3" />
		</td>
		<td class="hairLineTdF">
			<input type="text" class="smallInput" name="recov_term" value="${s.recov_term}" size="2" />
		</td>
		<td class="hairLineTdF">
		<input name="recov_date" id="recov_date${s.Oid}" type="text" size="9" class="smallInput" value="${s.recov_date}"
		onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
		</td>
		<td class="hairLineTdF">
		<input name="recov_docno" type="text" size="6" class="smallInput" value="${s.recov_docno}" />
		</td>
	
	</tr>		
</c:forEach>
</table>