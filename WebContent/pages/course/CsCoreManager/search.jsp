<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td>

		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">適用系所</td>
				<td class="hairLineTd">
				<select name="deptNo">
					<option value="">全部系所</option>
					<c:forEach items="${DeptList}" var="d">
					<option <c:if test="${CsCoreManagerForm.map.deptNo==d.idno}">selected</c:if> value="${d.idno}">${d.name}</option>		
					</c:forEach>		
				</select>
				</td>
			</tr>
		</table>		
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">課程關鍵字</td>
				<td class="hairLineTd">
				<input type="text" name="key_word" value="${CsCoreManagerForm.map.key_word}"/>
				</td>
				<td class="hairLineTdF" width="30" align="center">
				<img src="images/icon/icon_info.gif"  id="keyWordHelp"
				onMouseOver="showHelpMessage('關鍵字的設計請勿過於含糊', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)"/>
				</td>
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
				
					<li>建立核心課程
					<ul>
						<li>選擇適用科系
						<li>輸入關鍵字, 注意關鍵字的組成建立
						<li>建議同時勿使用相類似的關鍵字
						<li>建議同時勿使用太短的關鍵字
						<li>按下「新增」鍵完成初步新增
						<li>開始編輯核心課程細節 (參考第3段)
					</ul>
										
					<li>查詢核心課程						
					<ul>
						<li>選擇適用科系
						<li>輸入關鍵字, 注意關鍵字的組成建立
						<li>按下「查詢」取得列表
					</ul>
					
					<li>修改或刪除核心課程						
					<ul>
						<li>點選列表中的文字進入細節
						<li>按下「修改」即可修改資料 (能力值必須等於100)
						<li>按下「刪除」即可刪除
					</ul>
					<li>各項操作如有矛盾情況均不會繼續執行								
				</OL>
				
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" class="gSubmit" value="<bean:message key='Query'/>" id="Query"
					onMouseOver="showHelpMessage('查詢相關核心課程', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
		
		<input type="submit" name="method" 
					value="<bean:message key='Create'/>" 
					id="Create" class="gGreen"
					onMouseOver="showHelpMessage('建立新核心課程', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
		
		
					
		<input type="button" class="gCancle" value="說明" id="gradHelp" onClick="showObj('help')" 
					onMouseOver="showHelpMessage('顯示說明', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>
		
		</td>
	</tr>
</table>
