<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%">
	<tr>
		<td>
		

		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">活動名稱</td>
				
				<td class="hairLineTdF">
				<select name="act_oid">
					<c:forEach items="${allAction}" var="a">
					<option <c:if test="${EvaluatorManagerForm.map.act_oid==a.Oid}" >selected</c:if> value="${a.Oid}">${a.name}</option>
					</c:forEach>
				
				</select>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">編號姓名</td>
				<td class="hairLineTd" align="left">
				<input type="text" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
				name="techid" id="fsidno" size="12" value="" onFocus="chInput(this.id)"
				onkeyup="if(this.value.length>=2)GgetAny(this.value, 'fsidno', 'fscname', 'dempl', 'no')" 
				onMouseOver="showHelpMessage('身分證號輸入這裡, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 自動完成姓名', 'inline', this.id)" 
 						onMouseOut="showHelpMessage('', 'none', this.id)"
				 /><input onFocus="chInput(this.id)"
				
				onMouseOver="showHelpMessage('姓名輸入這裡, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 自動完成身分證號', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)"
				type="text" name="fscname" id="fscname" size="10" value=""
	 			onkeyup="if(this.value.length>0)GgetAny(this.value, 'fscname', 'fsidno', 'dempl', 'name')" />
				</td>
				<td class="hairLineTdF" width="30" align="center">
				<img src="images/icon/icon_info.gif"  id="helpid"
				onMouseOver="showHelpMessage('身分證號輸入左側空格, 姓名輸入右側,<br>若您貼上文字, 請按一下鍵盤右側的方向鍵, 自動完成輸入', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)"/>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>