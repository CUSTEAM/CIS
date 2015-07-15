<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" cellpadding="0" cellspacing="0">
	
	<tr>		
		<td>		
		
		
		<table class="hairLineTable" cellpadding="5" cellspacing="5">
			<tr>
				<td class="hairLineTdF" id="pifname">
				編號姓名
				</td>
				<td class="hairLineTd" align="left">
				<input type="text" autocomplete="off" style="ime-mode:disabled; font-size:20px;" autocomplete="off" 
				onClick="this.value='', document.getElementById('fscname').value='';"
				name="idno" id="fsidno" size="12" value="${TechlimitManagerForm.map.idno[0]}" onFocus="chInput(this.id)"
				onkeyup="if(this.value.length>=2)GgetAny(this.value, 'fsidno', 'fscname', 'dempl', 'no')" 
				onMouseOver="showHelpMessage('身分證號輸入這裡, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 自動完成姓名', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)" />
 				
 				<input onFocus="chInput(this.id)" style="font-size:20px;"
				onClick="this.value='', document.getElementById('fsidno').value='';"
				onMouseOver="showHelpMessage('姓名輸入這裡, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 自動完成身分證號', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)"
				type="text" name="cname" id="fscname" size="10" value="${TechlimitManagerForm.map.cname[0]}"
	 			onkeyup="if(this.value.length>0)GgetAny(this.value, 'fscname', 'fsidno', 'dempl', 'name')" />
				</td>
				<td class="hairLineTdF" width="30" align="center">
				<img src="images/icon/icon_info.gif"  id="helpIdName"
				onMouseOver="showHelpMessage('身分證號輸入左側空格, 姓名輸入右側,<br>若您貼上文字, 請按一下鍵盤右側的方向鍵, <BR>即可自動完成輸入', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)"/>
				</td>
			</tr>
		</table>
		
		</td>		
	</tr>
	<tr>
		<td>		
		
		
		<table class="hairLineTable" cellpadding="5" cellspacing="5">
			<tr>
				<td class="hairLineTdF" id="pifname">
				教師職稱
				</td>
				<td class="hairLineTdF" id="pifname">
				<select style="ime-mode:disabled; font-size:20px;" name="codeEmpl">
					
					<option value="">所有教職員職稱</option>
					<c:forEach items="${codes}" var="c">
					<option <c:if test="${c.idno==TechlimitManagerForm.map.codeEmpl[0]}">selected</c:if> value="${c.idno}">${c.name}</option>
					</c:forEach>
					
					<input type="hidden" name="Oid" />
					<input type="hidden" name="time" />
					<input type="hidden" name="time_over" />
				</select>
				</td>
			</tr>
		</table>
		
		<table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
			<tr>
				<td id="ds_calclass"></td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	<tr>
		<td class="fullColorTable" align="center">
		<input type="submit" name="method" 
					value="<bean:message key='Query'/>" 
					id="Query" class="gSubmit"
					onMouseOver="showHelpMessage('依照以上條件查詢', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
		</td>
	</tr>
</table>
