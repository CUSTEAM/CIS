<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<table cellspacing="0" cellpadding="0" width="100%">
	<tr height="5">
		<td><input type="hidden" name="Oid" value="${core.Oid}"/></td>
	</tr>
	<tr>
		<td>

		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">適用系所</td>
				<td class="hairLineTd">
				<select name="deptNo">
					<option value="">全部系所</option>
					<c:forEach items="${DeptList}" var="d">
					<option <c:if test="${d.idno==core.deptNo}">selected</c:if> value="${d.idno}">${d.name}</option>		
					</c:forEach>		
				</select>
				</td>
			</tr>
		</table>		
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF">課程關鍵字</td>
				<td class="hairLineTd">
				<input type="text" name="key_word" value="${core.key_word}"/>
				</td>
				<td class="hairLineTdF" width="30" align="center">
				<img src="images/icon/icon_info.gif"  id="keyWordHelp"
				onMouseOver="showHelpMessage('關鍵字的設計請勿過於含糊', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)"/>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" >
			<tr>
				<td class="hairLineTdF">最後適用學年</td>
				<td class="hairLineTd">
				<input type="text" id="entrance" name="entrance" value="${core.entrance}" size="2"
				onMouseOver="showHelpMessage('最後一屆<BR>使用此核心課程的學年<BR>未到期留空白即可', 'inline', this.id)" 
 				onMouseOut="showHelpMessage('', 'none', this.id)"/>
				</td>
				<td class="hairLineTdF" width="30" align="center">
				<img src="images/icon/icon_info.gif"  id="entranceHelp"
				onMouseOver="showHelpMessage('最後一屆<BR>使用此核心課程的學年<BR>未到期留空白即可', 'inline', this.id)" 
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
				<td class="hairLineTdF" colspan="2">專業能力</td>
			</tr>
		
			<tr>
				<td class="hairLineTdF" nowrap nowrap align="right">技術運用能力</td><td class="hairLineTdF">
				<input type="text" id="s1" name="s1" value="${core.s1}" size="2" 
				onKeyUp="count()" maxlength="3" style="ime-mode:disabled" autocomplete="off"/></td>
			</tr>
			<tr>
				<td class="hairLineTdF" align="right">知識掌握能力</td><td class="hairLineTdF"><input type="text" id="s2" name="s2" value="${core.s2}" size="2" 
				onKeyUp="count()" maxlength="3" style="ime-mode:disabled" autocomplete="off"/></td>
			</tr>
			<tr>
				<td class="hairLineTdF" align="right">資格取得資格</td><td class="hairLineTdF"><input type="text" id="s3" name="s3" value="${core.s3}" size="2" 
				onKeyUp="count()" maxlength="3" style="ime-mode:disabled" autocomplete="off"/></td>
			</tr>
			<tr>
				<td class="hairLineTdF" colspan="2">性質說明</td>
			</tr>
			<tr>
				<td class="hairLineTdF" colspan="2">
				<textarea name="note1" rows="5" cols="25">${core.note1}</textarea>
				</td>
			</tr>
		</table>
		
		
		
		
		<table class="hairLineTable" align="left">
			<tr>
				<td class="hairLineTdF" colspan="2">社會能力</td>
			</tr>
		
			<tr>
				<td class="hairLineTdF" nowrap nowrap align="right">主動進取能力</td><td class="hairLineTdF">
				<input type="text" id="s4" name="s4" value="${core.s4}" size="2" onKeyUp="count()" maxlength="3" style="ime-mode:disabled" autocomplete="off""/>
				</td>
			</tr>
			
			<tr>
				<td class="hairLineTdF" align="right">溝通協調能力</td><td class="hairLineTdF">
				<input type="text" name="s5" id="s5" value="${core.s5}" size="2" onKeyUp="count()" maxlength="3" style="ime-mode:disabled" autocomplete="off"/></td>
			</tr>
			
			<tr>
				<td class="hairLineTdF" align="right">團隊運作能力</td><td class="hairLineTdF">
				<input type="text" name="s6" id="s6" value="${core.s6}" size="2" onKeyUp="count()" maxlength="3" style="ime-mode:disabled" autocomplete="off"/></td>
			</tr>
			
			<tr>
				<td class="hairLineTdF" align="right">科技運用能力</td><td class="hairLineTdF">
				<input type="text" name="s7" id="s7" value="${core.s7}" size="2" onKeyUp="count()" maxlength="3" style="ime-mode:disabled" autocomplete="off"/></td>
			</tr>
			
			<tr>
				<td class="hairLineTdF" align="right">解決問題能力</td><td class="hairLineTdF">
				<input type="text" name="s8" id="s8" value="${core.s8}" size="2" onKeyUp="count()" maxlength="3" style="ime-mode:disabled" autocomplete="off"/></td>
			</tr>
			
			<tr>
				<td class="hairLineTdF" align="right">自我管理能力</td><td class="hairLineTdF">
				<input type="text" name="s9" id="s9" value="${core.s9}" size="2" onKeyUp="count()" maxlength="3" style="ime-mode:disabled" autocomplete="off"/></td>
			</tr>
			
			<tr>
				<td class="hairLineTdF" align="right">規劃創新能力</td><td class="hairLineTdF">
				<input type="text" name="sa" id="sa" value="${core.sa}" size="2" onKeyUp="count()" maxlength="3" style="ime-mode:disabled" autocomplete="off"/></td>
			</tr>
			
			<tr>
				<td class="hairLineTdF" align="right">學習分析能力</td><td class="hairLineTdF">
				<input type="text" name="sb" id="sb" value="${core.sb}" size="2" onKeyUp="count()" maxlength="3" style="ime-mode:disabled" autocomplete="off"/></td>
			</tr>			
			
			<tr>
				<td class="hairLineTdF" colspan="2">性質說明</td>
			</tr>
			
			<tr>
				<td class="hairLineTdF" colspan="2">
				<textarea name="note2" rows="5" cols="25">${core.note2}</textarea>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable" align="left" width="200">
			<tr>
				<td class="hairLineTdF" nowrap align="center">能力值</td>
			</tr>
			<tr>
				<td class="hairLineTdF" nowrap align="center">
				<font size="+4"><b><label id="disum">${core.sum}</label></b></font>
				<input type="hidden" name="sum" id="sum" value="${core.sum}" size="2" />
				</td>
			</tr>
		</table>
		
		
		</td>
	</tr>
	<tr height="5">
		<td></td>
	</tr>
	<tr height="30">
		<td class="fullColorTable" align="center">
		
		<input type="submit" name="method" class="gSubmit" value="<bean:message key='Save'/>" id="Save"
					onMouseOver="showHelpMessage('儲存以上資料', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)"/>		
					
		<input type="submit" name="method" 
					value="<bean:message key='Back'/>" 
					id="Back" class="gCancel"
					onMouseOver="showHelpMessage('返回', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
					
		<input type="submit" name="method" 
					value="<bean:message key='Delete'/>" 
					id="Delete" class="gCancle" onClick="return confirmSubmit()"
					onMouseOver="showHelpMessage('刪除以上資料', 'inline', this.id)" 
					onMouseOut="showHelpMessage('', 'none', this.id)">
		
		</td>
	</tr>
	
	
</table>
<script>
function count(){
	sum=document.getElementById("s1").value*1+
	document.getElementById("s2").value*1+
	document.getElementById("s3").value*1+
	document.getElementById("s4").value*1+
	document.getElementById("s5").value*1+
	document.getElementById("s6").value*1+
	document.getElementById("s7").value*1+
	document.getElementById("s8").value*1+
	document.getElementById("s9").value*1+
	document.getElementById("sa").value*1+
	document.getElementById("sb").value*1;
	
	document.getElementById("sum").value=sum;
	document.getElementById("disum").innerHTML=sum;	
}

function confirmSubmit()
{
var agree=confirm("確定要刪除${core.key_word}嗎？");
if (agree)
	return true ;
else
	return false ;
}
</script>