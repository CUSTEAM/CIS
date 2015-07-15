<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">班別代碼</td>
				<td class="hairLineTd"><input type="text" name="id" size="2" style="ime-mode:disabled" /></td>
				<td class="hairLineTdF" width="30" align="center">
				<img src="images/icon/icon_info.gif"  id="idHelp"
				onMouseOver="showHelpMessage('班別代碼建議輸入英文字母, 以2字為限', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)"/>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">班別名稱</td>
				<td class="hairLineTd"><input type="text" name="name" size="24"/></td>
				<td class="hairLineTdF" width="30" align="center">
				<img src="images/icon/icon_info.gif"  id="nameHelp"
				onMouseOver="showHelpMessage('班別名稱建議輸入中文, 儘量以20字以內', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)"/>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
		<td>
		
		<table class="hairLineTable"  width="99%" align="left">
			<tr>
				<td class="hairLineTdF" colspan="2" id="mon"
				onMouseOver="showHelpMessage('星期一的執勤期間, 空白將視為週休', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)">星期一</td>
				<td class="hairLineTdF" colspan="2" id="tue"
				onMouseOver="showHelpMessage('星期二的執勤期間, 空白將視為週休', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)">星期二</td>
				<td class="hairLineTdF" colspan="2" id="wed"
				onMouseOver="showHelpMessage('星期三的執勤期間, 空白將視為週休', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)">星期三</td>
				<td class="hairLineTdF" colspan="2" id="thu"
				onMouseOver="showHelpMessage('星期四的執勤期間, 空白將視為週休', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)">星期四</td>
				<td class="hairLineTdF" colspan="2" id="fri"
				onMouseOver="showHelpMessage('星期五的執勤期間, 空白將視為週休', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)">星期五</td>
				<td class="hairLineTdF" colspan="2" id="sat"
				onMouseOver="showHelpMessage('星期六的執勤期間, 空白將視為週休', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)">星期六</td>
				<td class="hairLineTdF" colspan="2" id="sun"
				onMouseOver="showHelpMessage('星<br>期<br>日<br>的<br>執<br>勤<br>期<br>間<br>,<br>空<br>白<br>將<br>視<br>為<br>週<br>休', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)">星期日</td>
			</tr>
			<tr>
				<%for(int i=0; i<7; i++){ %>
				<td class="hairLineTdF">應上</td>
				<td class="hairLineTdF">應下</td>
				<%} %>
			</tr>
			<tr>
				<td class="hairLineTdF"><input type="text" name="in1" size="5" class="smallInput" /></td>
				<td class="hairLineTdF"><input type="text" name="out1" size="5" class="smallInput" /></td>
				
				<td class="hairLineTdF"><input type="text" name="in2" size="5" class="smallInput" /></td>
				<td class="hairLineTdF"><input type="text" name="out2" size="5" class="smallInput" /></td>
				
				<td class="hairLineTdF"><input type="text" name="in3" size="5" class="smallInput" /></td>
				<td class="hairLineTdF"><input type="text" name="out3" size="5" class="smallInput" /></td>
				
				<td class="hairLineTdF"><input type="text" name="in4" size="5" class="smallInput" /></td>
				<td class="hairLineTdF"><input type="text" name="out4" size="5" class="smallInput" /></td>
				
				<td class="hairLineTdF"><input type="text" name="in5" size="5" class="smallInput" /></td>
				<td class="hairLineTdF"><input type="text" name="out5" size="5" class="smallInput" /></td>
				
				<td class="hairLineTdF"><input type="text" name="in6" size="5" class="smallInput" /></td>
				<td class="hairLineTdF"><input type="text" name="out6" size="5" class="smallInput" /></td>
				
				<td class="hairLineTdF"><input type="text" name="in7" size="5" class="smallInput" /></td>
				<td class="hairLineTdF"><input type="text" name="out7" size="5" class="smallInput" /></td>
			</tr>			
			
		</table>
		
		
		
		</td>
	</tr>
	<tr>
		<td id="help" style="display:none;">
		<table class="hairLineTable" width="99%">
			<tr>
				<td class="hairLineTdF">
				<OL>
				
					<li>建立班別
					<ul>
						<li>班別代碼不可空白
						<li>班別名稱不可空白
						<li>時間格式為24小時制的半形數字「HH:mm」如「08:20」或「8:20」以及「16:20」
						<li>同一天的 應上/應下 不可空白，空白將視為週休
						<li>按下「新增」鍵完成新增
					</ul>
					
					
					<li>修改班別						
					<ul>
						<li>班別代碼(會影響出缺勤記錄)不可更改
						<li>班別名稱可更改
						<li>應上/應下時間(會影響出缺勤記錄)不可更改
					</ul>
					
					<li>刪除班別只要將名稱清除後按下修改即可刪除
					<li>各項操作如有矛盾情況均不會繼續執行								
				</OL>
				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	
	
	
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" 
					value="<bean:message key='Create'/>" 
					id="Create" class="gGreen"
					onMouseOver="showHelpMessage('建立新班別', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
		
		<input type="button" class="gCancle" value="返回" id="goDir"
					onclick="location='/CIS/AMS/Directory.do';"
					onMouseOver="showHelpMessage('返回排班表與人員管理', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
					
		<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
					onMouseOver="showHelpMessage('顯示說明', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
		
		</td>
	</tr>
</table>