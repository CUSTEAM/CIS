<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">點擊課程名稱開啟自我學習評量問卷, <b>依規定必須全部</b>作答完畢方可解除系統鎖定</td>
	</tr>
</table>

<html:form action="/Student/CoanswServlet4self" method="post" onsubmit="init('儲存中, 請稍後')" style="margin:0px;">
<c:forEach items="${mySeQuestion}" var="q">
<table>
	<tr>
		<td>

		<table onClick="showQust('sq${q.dtimeOid}')" style="cursor:pointer;">
			<tr>
				<td width="30" align="center"><img src="images/user.gif" /></td>
				<td width="200" style="font-size:18px; white-space:nowrap;">${q.chi_name}</td>
				
				<td nowrap>${q.opt}</td>
				<td>-</td>
				<td nowrap>${q.credit}學分</td>
				<td>-</td>
				<td nowrap>${q.cname} 老師
				<input type="hidden" name="dtimeOid" value="${q.dtimeOid}"/>
				<input type="hidden" name="answer" id="seanswer${q.dtimeOid}" />
				<input type="hidden" name="ansLength" value="${fn:length(q.question_se)}" />
				</td>
			</tr>
		</table>

		</td>
	</tr>
	<tr>
		<td id="sq${q.dtimeOid}" style="display:none">
		<c:forEach items="${q.question_se}" var="s">
		<table class="hairLineTable" width="99%">
		
			<tr>
				<td class="hairLineTdF" width="50%">
				${s.options}
				</td>				
				
				<c:forEach items="${s.subPlay}" var="ss">				
				<td class="hairLineTdF">
				<input type="checkBox" id="check${q.dtimeOid}${s.Oid}${ss.Oid}" value="${ss.value}" 
				onClick="secheckSel${q.dtimeOid}${s.Oid}${ss.Oid}(this.id)">${ss.options}			
				
<script>
function secheckSel${q.dtimeOid}${s.Oid}${ss.Oid}(id){

<c:forEach items="${s.subPlay}" var="ss1">
	document.getElementById('check${q.dtimeOid}${s.Oid}${ss1.Oid}').checked=false;
</c:forEach>				
	document.getElementById(id).checked=true;
	document.getElementById('checked${q.dtimeOid}${s.Oid}').value=document.getElementById(id).value;				
	var tmp="";
<c:forEach items="${q.question_se}" var="s1">
	tmp=tmp+document.getElementById('checked${q.dtimeOid}${s1.Oid}').value;
</c:forEach>
	document.getElementById('seanswer${q.dtimeOid}').value=tmp;
}
</script>
				
				
				
				</td>
				</c:forEach>
				<input type="hidden" id="checked${q.dtimeOid}${s.Oid}" />
				</td>
			</tr>

						
		</table>
		</c:forEach>
		<table width="100%">
			<tr>
				<td align="center">
				<input type="submit" class="gSubmit" value="填好一份"/>
							
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
</table>
</c:forEach>
</html:form>