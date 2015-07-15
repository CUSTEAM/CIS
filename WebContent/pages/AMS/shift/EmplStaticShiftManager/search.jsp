<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%">
	<tr>
		<td>
		
		<table class="hairLineTable" align="left">
			<tr>				
				<td class="hairLineTdF" align="center" nowrap>				
				固定班別				
				</td>
				<td class="hairLineTdF">			
				
				<select name="sid">
					<option <c:if test="${EmplStaticShiftManagerForm.map.sid==''}">selected</c:if> value="all">全部班別</option>
					<c:forEach items="${allShift}" var="as">
					<option <c:if test="${EmplStaticShiftManagerForm.map.sid==as.id}">selected</c:if> value="${as.id}">${as.name}</option>				
					</c:forEach>
				</select>
				
				
				</td>
				<td class="hairLineTdF" width="30" align="center">
				<img src="images/icon/icon_info.gif"  id="helpId"
				onMouseOver="showHelpMessage('班別代碼', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)"/>
				</td>
			</tr>
		</table>
		
						
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF" nowrap>
				單位
				</td>
				<td class="hairLineTd">
				<select name="sunit">
					<option <c:if test="${EmplStaticShiftManagerForm.map.sunit==''}">selected</c:if> value="">全部單位</option>
					<c:forEach items="${allUnit}" var="c">
					<option <c:if test="${EmplStaticShiftManagerForm.map.sunit==c.idno}">selected</c:if> value="${c.idno}">${c.name}</option>
					</c:forEach>
				</select>
				</td>
				</td>
				<td class="hairLineTdF" width="30" align="center">
				<img src="images/icon/icon_info.gif"  id="helpUnit"
				onMouseOver="showHelpMessage('任(專)職單位', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)"/>
				</td>
			</tr>
		</table>
					
		
		</td>
	</tr>	
	<tr>		
		<td>		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF" id="pifname" onMouseOver="showHelpMessage('無論是否在職皆可查到', 'inline', this.id)" 
 						onMouseOut="showHelpMessage('', 'none', this.id)">
				編號姓名
				</td>
				<td class="hairLineTd" align="left">
				<input type="text" autocomplete="off" style="ime-mode:disabled" autocomplete="off"
				name="sidno" id="fsidno" size="12" value="${EmplStaticShiftManagerForm.map.sidno}" onFocus="chInput(this.id)"
				onkeyup="if(this.value.length>=2)GgetAny(this.value, 'fsidno', 'fscname', 'dempl', 'no')" 
				onMouseOver="showHelpMessage('身分證號輸入這裡, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 自動完成姓名', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)" /><input onFocus="chInput(this.id)"
				
				onMouseOver="showHelpMessage('姓名輸入這裡, 若您貼上文字, 請按一下鍵盤右側的方向鍵, 自動完成身分證號', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)"
				type="text" name="fscname" id="fscname" size="10" value="${EmplStaticShiftManagerForm.map.fscname}"
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
</table>
