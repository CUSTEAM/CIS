<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html:form action="/Student/CoanswServlet" method="post" onsubmit="init('儲存中, 請稍後')" style="margin:0px;">
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">點擊課程名稱開啟學生反應教學意見問卷, <b>依規定必須全部</b>作答完畢方可解除系統鎖定<br>評分對象如有異動，以權責單位最後輸入系統為準</td>
	</tr>
</table>

<c:forEach items="${myQuestion}" var="q">
<table>
	<tr>
		<td>

		<table onClick="showQust('q${q.dtimeOid}')" style="cursor:pointer;">
			<tr>
				<td width="30" align="center"><input type="button" class="greenSmall" value="作答"></td>
				<td width="200" style="font-size:18px; white-space:nowrap;">${q.chi_name}</td>

				<td nowrap>${q.opt}</td>
				<td>-</td>
				<td nowrap>${q.credit}學分</td>
				<td>-</td>
				<td nowrap>${q.cname} 老師<input type="hidden" id="check${q.dtimeOid}" value="0"/>
				<input type="hidden" name="dtimeOid" value="${q.dtimeOid}"/>
				<input type="hidden" name="answer" id="answer${q.dtimeOid}" />
				<input type="hidden" name="ansLength" value="${fn:length(q.question)}" />
				</td>
			</tr>
		</table>

		</td>
	</tr>
	<tr>
		<td id="q${q.dtimeOid}" style="display:none">
		<c:set var="num" value="0"/>
		<table class="hairLineTable">
		<c:forEach items="${q.question}" var="s">
			<tr>
				<td class="hairLineTdF">
				<c:set var="num" value="${num+1}"/>
				${num}.
				${s.options}
				</td>

				<c:forEach items="${s.subPlay}" var="ss">
				<td class="hairLineTdF" nowrap>
				<input type="checkBox" id="check${q.dtimeOid}${s.Oid}${ss.Oid}" value="${ss.value}"
				onClick="checkSel${q.dtimeOid}${s.Oid}${ss.Oid}(this.id)<c:if test="${num==4}">, document.getElementById('x${q.dtimeOid}').value=${ss.value}, checkSn(${q.dtimeOid})</c:if>">






				${ss.options}

<script>

function checkSn(Oid){
	
	var a=parseInt(document.getElementById('x'+Oid).value);
	var b=parseInt(document.getElementById('y'+Oid).value);
	
	if(b==0){
		return;
	}
	var c=a-b;		
		if(c>=2||c<=-2){
			document.getElementById('sub'+Oid).style.display='inline';
		}else{
			document.getElementById('sub'+Oid).style.display='none';
		}
}


function checkSel${q.dtimeOid}${s.Oid}${ss.Oid}(id){

<c:forEach items="${s.subPlay}" var="ss1">
	document.getElementById('check${q.dtimeOid}${s.Oid}${ss1.Oid}').checked=false;
</c:forEach>
	document.getElementById(id).checked=true;
	document.getElementById('checked${q.dtimeOid}${s.Oid}').value=document.getElementById(id).value;
	var tmp="";
<c:forEach items="${q.question}" var="s1">
	tmp=tmp+document.getElementById('checked${q.dtimeOid}${s1.Oid}').value;
</c:forEach>
	document.getElementById('answer${q.dtimeOid}').value=tmp;
}
</script>



				</td>
				</c:forEach>
				<input type="hidden" size="1" id="checked${q.dtimeOid}${s.Oid}" />
				</td>
			</tr>

		</c:forEach>
		<tr>
				<td class="hairLineTdF" nowrap>
				11. 老師的教學方式很無趣<input type="hidden" id="x${q.dtimeOid}" value="0"/><input type="hidden" id="y${q.dtimeOid}" value="0"/>
				</td>
				<td class="hairLineTdF"><input type=radio name="a${q.dtimeOid}" onClick="document.getElementById('y${q.dtimeOid}').value=5, checkSn(${q.dtimeOid})">非常同意</td>
				<td class="hairLineTdF"><input type=radio name="a${q.dtimeOid}" onClick="document.getElementById('y${q.dtimeOid}').value=4, checkSn(${q.dtimeOid})" >同意</td>
				<td class="hairLineTdF"><input type=radio name="a${q.dtimeOid}" onClick="document.getElementById('y${q.dtimeOid}').value=3, checkSn(${q.dtimeOid})">尚可</td>
				<td class="hairLineTdF"><input type=radio name="a${q.dtimeOid}" onClick="document.getElementById('y${q.dtimeOid}').value=2, checkSn(${q.dtimeOid})">不同意</td>
				<td class="hairLineTdF"><input type=radio name="a${q.dtimeOid}" onClick="document.getElementById('y${q.dtimeOid}').value=1, checkSn(${q.dtimeOid})">非常不同意</td>
			</tr>
		</table>

		<table width="100%">
			<tr>
				<td align="center">
				<input type="submit" class="gSubmit" value="填好一份" id="sub${q.dtimeOid}" style="display:none;"/>
				</td>
			</tr>
		</table>

		</td>
	</tr>

</table>
</c:forEach>
</html:form>