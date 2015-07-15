<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script>
function clearOid(id){
	document.getElementById(id).value="1";
}
</script>
<c:forEach items="${allAct}" var="a">
<table>
	<tr>
		<td>

		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF" width="30" align="center">刪除</td>
				<td class="hairLineTdF" width="30" align="center"><input type="checkbox" onClick="clearOid('Oid${a.Oid}');"/>
				<input id="Oid${a.Oid}" type="hidden" name="delOid"/></td>
			
				<td class="hairLineTdF">活動名稱</td>
				<td class="hairLineTdF"><input type="hidden" name="Oid" value="${a.Oid}" /><input type="text" name="name" value="${a.name}"/></td>
			</tr>
		</table>
		</td>
		<td>
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">報名期間</td>
				<td class="hairLineTdF">
				<input type="text" name="sign_start" id="sign_start" size="4" readonly value="${a.sign_start}"
				onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				<input type="text" name="sign_end" id="sign_end" size="4" readonly value="${a.sign_end}"
				onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>	
		<td>
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">評審期間</td>
				<td class="hairLineTdF">
				<input type="text" name="judge_start" id="judge_start" size="4" readonly value="${a.judge_start}"
				onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				<input type="text" name="judge_end" id="judge_end" size="4" readonly value="${a.judge_end}"
				onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
				</td>
			</tr>
		</table>
		</td>
		
		<td>
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">活動結束</td>
				<td class="hairLineTdF">
				<input type="text" name="end" id="end" size="4" readonly value="${a.end}"
				onclick="ds_sh(this), this.value='';" autocomplete="off" style="ime-mode:disabled" autocomplete="off"/>
		
				</td>
			</tr>
		</table>
		</td>
		
	</tr>
</table>
</c:forEach>