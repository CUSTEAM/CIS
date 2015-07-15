<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:if test="${tutorQuestion!=null}">	
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF"><b>依規定</b>必須將導師評量作答完畢方可解除系統鎖定</td>
	</tr>
</table>

<table>
<html:form action="/Student/CoanswServlet4tutor" method="post" onsubmit="init('儲存中, 請稍後')">
	<tr>
		<td>

		<table>
			<tr>
				<td class="hairLineTdF" width="30" align="center"><img src="images/user_gray.gif" /></td>
				<td style="font-size:18px;">${myTotur.ClassName}</td>
				<td style="font-size:18px;">-</td>
				<td style="font-size:18px;">${myTotur.cname}老師</td>
				<td>評分對象如有異動，以權責單位最後輸入系統為準</td>
			</tr>
		</table>

		</td>
	</tr>
	<tr>
		<td id="tutorQuestion">

		<table class="hairLineTable">
		<c:forEach items="${tutorQuestion}" var="s">
			<tr>
				<td class="hairLineTdF">
				${s.options}
				</td>				
				
				<c:forEach items="${s.subPlay}" var="ss">				
				<td class="hairLineTdF" nowrap>
				<input type="checkBox" id="check${s.Oid}${ss.Oid}" value="${ss.value}" 
				onClick="checkSel${s.Oid}${ss.Oid}(this.id)">${ss.options}				
				
				<script>
				function checkSel${s.Oid}${ss.Oid}(id){
				
				<c:forEach items="${s.subPlay}" var="ss1">
					document.getElementById('check${s.Oid}${ss1.Oid}').checked=false;
				</c:forEach>				
					document.getElementById(id).checked=true;
					document.getElementById('checked${s.Oid}t').value=document.getElementById(id).value;				
					var tmp="";
				<c:forEach items="${q.question}" var="s1">
					tmp=tmp+document.getElementById('checked${s1.Oid}t').value;
				</c:forEach>
					
				}
				</script>
				
				</td>
				</c:forEach>
				
				<input type="hidden" size="1"  name="tAnswer" id="checked${s.Oid}t" />			
				</td>
			</tr>

		</c:forEach>				
		</table>
		
		<table width="100%">
			
			<tr>
				<td align="center">				
				
				<input type="submit" class="gSubmit" value="填好一份"/>	
				</td>
			</tr>
			</html:form>
		</table>
		</td>
	</tr>
</table>
</c:if>