<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table style="cursor:pointer;" onClick="showObj('obj${cs.Oid}');">
	<tr>
		<td width="30" align="center" nowrap onMouseOver="showHelpMessage('按一下顯示(或關閉) 「${cs.name}」 事項的詳細內容', 'inline', 'tt${cs.Oid}')" 
		onMouseOut="showHelpMessage('', 'none', 'tt${cs.Oid}')">
		<table style="border:1px solid #808080;" width="12" height="12" bgcolor='${cs.color}'><tr><td></td></tr></table>
		</td>					
		
		<td nowrap onMouseOver="showHelpMessage('按一下顯示(或關閉) 「${cs.name}」 事項的詳細內容', 'inline', 'tt${cs.Oid}')" 
		onMouseOut="showHelpMessage('', 'none', 'tt${cs.Oid}')">
		<c:if test="${cs.type==1}">重要</c:if>
		<c:if test="${cs.type!=1}">普通</c:if>
		<c:if test="${cs.time!=''}">, ${cs.time}</c:if>
		, ${cs.name}
		<c:if test="${cs.place!=''}">, ${cs.place}</c:if>
		</td>
		
		<td width="100%" id="tt${cs.Oid}" onMouseOver="showHelpMessage('按一下顯示(或關閉) 「${cs.name}」 事項的詳細內容', 'inline', 'tt${cs.Oid}')" 
		onMouseOut="showHelpMessage('', 'none', 'tt${cs.Oid}')"></td>
		<td>
		<c:if test="${cs.sender!=CISAccount}"><img src="images/icon/tag/flag_blue.gif" 
		id="flag${cs.no}" onMouseOver="showHelpMessage('我受邀參與, 刪除<br>時只影響我的<br>行事曆', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)"/></c:if>
		<c:if test="${cs.sender==CISAccount}"><img src="images/icon/tag/flag_red.gif" 
		id="flag${cs.no}" onMouseOver="showHelpMessage('我發起的活動<br>刪除時會連帶<br>受邀者並且會<br>以email通知', 'inline', this.id)" 
		onMouseOut="showHelpMessage('', 'none', this.id)" /></c:if>
		</td>
		<td>
		<c:if test="${cs.account!='All'}">
		<input type="hidden" name="delOid" id="delOid${cs.Oid}" value=""/>					
		<input type="submit" name="method"value="<bean:message key='Delete'/>" 
		onClick="checkDel('${cs.Oid}'); return confirmSubmit('確定刪除${cs.name}嗎？');"
		class="gCancelSmall">
		</c:if>										
		</td>					
	</tr>
</table>

<table id="obj${cs.Oid}" style="display:none;" >
	<tr>
		<td><b>參與者:</b> ${cs.members}</td>
	</tr>
	<tr>
		<td><b>備註:</b> ${cs.note}</td>
	</tr>
</table>