<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<input type="hidden" name="ans_oid" value="${ans_oid}" />
<input type="hidden" name="ans" id="ans"/>
<table class="hairLineTable" width="99%" id="mydept">				
	<tr>
		<td class="hairLineTdF" width="150" nowrap>請問您所屬的系所</td>
		<td class="hairLineTdF">			
		<select name="dept" style="font-size:18px;">
			<c:forEach items="${depts}" var="d">
			<option <c:if test="${d.id2==dept}">selected</c:if> value="${d.id2}">${d.name}</option>
			</c:forEach>
		</select>			
		若非您所屬的系，此欄位可自由調整							
		</td>
	</tr>			
</table>


<table class="hairLineTable" width="99%" id="mydept">				
	<tr>
		<td class="hairLineTdF" width="150" nowrap>請問您目前的身分</td>
		<td class="hairLineTdF">			
		
		<select name="category" style="font-size:18px;">			
			<option value="0">目前未兼行政職位</option>
			<option <c:if test="${myduty!=''}">selected</c:if> value="1">目前有兼任行政職位</option>
		</select>			
		若非您目前的身份，此欄位可自由調整							
		</td>
	</tr>			
</table>

<table class="hairLineTable" width="99%" id="mydept">				
	<tr>
		<td class="hairLineTdF" width="150" nowrap>請問您在本校年資</td>
		<td class="hairLineTdF">			
		
		<select name="years" style="font-size:18px;">			
			<option <c:if test="${years=='1'}">selected</c:if> value="1">1年以下</option>
			<option <c:if test="${years=='2'}">selected</c:if> value="2">1~2年</option>
			<option <c:if test="${years=='3'}">selected</c:if> value="3">3年以下</option>
			<option <c:if test="${years=='5'}">selected</c:if> value="5">3~5年</option>
			<option <c:if test="${years=='6'}">selected</c:if> value="6">6~10年</option>
			<option <c:if test="${years=='10'}">selected</c:if> value="10">10年以上</option>
			<option <c:if test="${years=='20'}">selected</c:if> value="20">20年以上</option>
		</select>			
		為避免系統資料誤差，此欄位請自由調整							
		</td>
	</tr>			
</table>


<table class="hairLineTable" width="99%" id="mydept">				
	<tr>
		
	</tr>
	<c:forEach items="${qs}" var="q">
	<tr>
		<td class="hairLineTdF" wisth="50%">${q.question}</td>
		<td class="hairLineTdF" nowrap><input onClick="setAns();" type="radio" name="q${q.Oid}" value="5">非常滿意</td>
		<td class="hairLineTdF" nowrap><input onClick="setAns();" type="radio" name="q${q.Oid}" value="4">滿意</td>
		<td class="hairLineTdF" nowrap><input onClick="setAns();" type="radio" name="q${q.Oid}" value="3">普通</td>
		<td class="hairLineTdF" nowrap><input onClick="setAns();" type="radio" name="q${q.Oid}" value="2">不滿意</td>
		<td class="hairLineTdF" nowrap><input onClick="setAns();" type="radio" name="q${q.Oid}" value="1">非常不滿意</td>
		<td class="hairLineTdF" nowrap><input onClick="setAns();" type="radio" name="q${q.Oid}" value=" " checked>未曾接觸</td>
	</tr>	
	</c:forEach>
	<tr>
		<td class="hairLineTdF" colspan="7" align="center"><INPUT type="submit" name="method" value="<bean:message key='Save'/>" class="gSubmit"></td>
</table>

<script>
function setAns(){
	var str="";
	var radio;
	<c:forEach items="${qs}" var="q">	
　　	radio = document.all.q${q.Oid};
　　	for(i=0;i<radio.length;i++){
     　　if(radio[i].checked)
       　　	str=str+radio[i].value;
　　	}
	</c:forEach>
	document.getElementById("ans").value=str;
}
</script>