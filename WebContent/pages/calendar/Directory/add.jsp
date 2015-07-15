<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<table id="new${v.count}" width="100%" style="display:none;">
	<tr>
		<td>			
		
		
		<table class="hairLineTable" width="99%">			
			<tr>
				<td class="hairLineTdF" width="1">
				<select name="type" id="type" style="font-size:14pt;">
	    			<option value="0">普通事項</option>
	    			<option value="1">重要事項</option>
	    		</select>
			    </td>
			    <td class="hairLineTdF" width="50" align="center" nowrap>名稱</td>		
			    <td class="hairLineTdF">		
	    		<input type="hidden" name="color" id="color${v.count}" type="text" readonly size="3" onfocus="colordialog(event);" style="font-size:14pt;">
				<input type="text" name="name" id="n${v.count}" style="font-size:14pt;" />
				</td>
			</tr>
		</table>
			
		<table class="hairLineTable" width="99%">	
			<tr>				
				<td class="hairLineTdF" nowrap>				
				<select id="s${v.count}" onChange="cp('beginTime${v.count}', this.id, 't${v.count}');" style="font-size:14pt;">				
				<option value="00:">凌晨  0時</option>
				<option value="01:">凌晨  1時</option>
				<option value="02:">凌晨  2時</option>
				<option value="03:">凌晨  3時</option>
				<option value="04:">凌晨  4時</option>
				<option value="05:">凌晨  5時</option>
				<option value="06:">凌晨  6時</option>				
				<option value="07:">上午  7時</option>
				<option value="08:">上午  8時</option>
				<option value="" selected>開始時間</option>
				<option value="09:">上午  9時</option>
				<option value="10:">上午10時</option>
				<option value="11:">上午11時</option>
				<option value="12:">中午12時</option>				
				<option value="13:">下午  1時</option>
				<option value="14:">下午  2時</option>
				<option value="15:">下午  3時</option>
				<option value="16:">下午  4時</option>
				<option value="17:">下午  5時</option>
				<option value="18:">下午  6時</option>				
				<option value="19:">晚上 7時</option>
				<option value="20:">晚上 8時</option>
				<option value="21:">晚上 9時</option>
				<option value="22:">晚上10時</option>
				<option value="23:">晚上11時</option>				
				</select>
				<select style="display:inline;" id="t${v.count}" onChange="cp('beginTime${v.count}', 's${v.count}', this.id);" style="font-size:14pt;">
					<option value="00">整點</option>
					<option value="10">10分</option>
					<option value="20">20分</option>
					<option value="30">30分</option>
					<option value="40">40分</option>
					<option value="50">50分</option>				
				</select>
				<input type="text" name="beginTime" id="beginTime${v.count}"/>
				</td>
				<td class="hairLineTdF" nowrap width="30">至</td>
				<td class="hairLineTdF" nowrap width="100%">				
				<select id="s1${v.count}" onChange="cp('endTime${v.count}', this.id, 't${v.count}');" style="font-size:14pt;">				
				<option value="00:">凌晨  0時</option>
				<option value="01:">凌晨  1時</option>
				<option value="02:">凌晨  2時</option>
				<option value="03:">凌晨  3時</option>
				<option value="04:">凌晨  4時</option>
				<option value="05:">凌晨  5時</option>
				<option value="06:">凌晨  6時</option>				
				<option value="07:">上午  7時</option>
				<option value="08:">上午  8時</option>
				<option value="" selected>結束時間</option>
				<option value="09:">上午  9時</option>
				<option value="10:">上午10時</option>
				<option value="11:">上午11時</option>
				<option value="12:">中午12時</option>				
				<option value="13:">下午  1時</option>
				<option value="14:">下午  2時</option>
				<option value="15:">下午  3時</option>
				<option value="16:">下午  4時</option>
				<option value="17:">下午  5時</option>
				<option value="18:">下午  6時</option>				
				<option value="19:">晚上 7時</option>
				<option value="20:">晚上 8時</option>
				<option value="21:">晚上 9時</option>
				<option value="22:">晚上10時</option>
				<option value="23:">晚上11時</option>				
				</select>
				<select style="display:inline;" id="t1${v.count}" onChange="cp('endTime${v.count}', 's1${v.count}', this.id);" style="font-size:14pt;">
					<option value="00">整點</option>
					<option value="10">10分</option>
					<option value="20">20分</option>
					<option value="30">30分</option>
					<option value="40">40分</option>
					<option value="50">50分</option>				
				</select>
				<input type="text" name="endTime" id="endTime${v.count}"/>
				</td>
			</tr>
		</table>
		<table class="hairLineTable" width="99%">		
			<tr>
				<td class="hairLineTdF" width="50" align="center" nowrap>地點</td>
				<td class="hairLineTdF" width="75%">
				<input type="text" name="place" style="font-size:14pt; width:50%"/>
				
				</td>
			</tr>
			
		</table>
		
		<table class="hairLineTable" width="99%">			
			<tr>
				<td  class="hairLineTdF">
						<select id="addcase${v.count}">
						<option value="">選擇群組</option>
						<c:forEach items="${myGroup}" var="g">
						<option value="${g.Oid}">${g.name}</option>
						</c:forEach>
						</select>
						<input type="button" class="gCancelSmall" value="加入群組"
						onClick="inputMember('members${v.count}', 'addcase${v.count}')"/>
						
				
				</td>
			</tr>
			<tr>
				<td class="hairLineTdF"><textarea id="members${v.count}" name="members" style="height:62px; width:100%;"></textarea></td>
			</tr>
			<tr>				    
			    <td class="hairLineTd">
			    <table cellpadding="0" cellspacing="0" style="padding:0px;">
			    	<tr>
			    		<td><input type="checkbox" checked disabled></td>
			    		<td nowrap>自動分析參與者並以郵件通知</td>
			    		<td nowrap style="cursor:pointer;" id="mailHelp${v.count}" valign="baseline"
			    		onMouseOver="showHelpMessage('選擇群組或輸入任何格式的文字<BR>'+
			    		'系統會分析文字中的「中文姓名」<BR>'+
			    		'對應人事資料批次寄送email邀請<BR>'+'可至個人群組管理建立個人群組', 'inline', this.id)" 
				 		onMouseOut="showHelpMessage('', 'none', this.id)">
				 		<img src="images/icon/icon_info.gif"/>
				 		</td>
			    	</tr>
			    </table>			    
			    </td>
			</tr>
		</table>
		
		<table class="hairLineTable" width="99%">				
			<tr>				    
			    <td class="hairLineTd">
			    <table cellpadding="0" cellspacing="0">
			    	<tr height="20">
			    		
			    		<td nowrap>附註</td>
			    	</tr>
			    </table>	
			    </td>
			</tr>
			<tr>
				<td class="hairLineTdF"><textarea name="note" style="height:62px; width:100%;"></textarea></td>
			</tr>
			<tr>				    
			    <td class="hairLineTd">
			    <table cellpadding="0" cellspacing="0" style="padding:0px;">
			    	<tr>
			    		<td nowrap width="250" id="file${v.count}"></td>
			    		<td>
			    		
			    		<input type="button" name="method"value="加入檔案" class="gGreenSmall" onClick="opFile('file${v.count}');">
			    		
			    		
			    		<input type="hidden" name="beginDate" value="${m.realDate}" />
			    		<input type="submit" name="method"value="<bean:message key='Create'/>" class="gSubmitlSmall" 
			    			onClick="return confirmSubmit('確定建立'+document.getElementById('n${v.count}').value+'嗎？');">
			    		
			    		
			    		</td>			    		
			    	</tr>
			    </table>			    
			    </td>
			</tr>		
		</table>
		
		</td>
	</tr>
</table>
