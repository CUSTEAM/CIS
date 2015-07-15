<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>		
		<td>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">開課學年</td>
				<td class="hairLineTdF">
				<input type="text" id="year" name="year" style="font-size:18;" value="${DtimeReserverSyncForm.map.year}"
				 size="8" autocomplete="off" style="ime-mode:disabled" autocomplete="off" />
				</td>
				<td class="hairLineTdF">
				<select name="term" style="font-size:18;">
					<option <c:if test="${DtimeReserverSyncForm.map.term=='1'}">selected</c:if> value="1">第1學期</option>
					<option <c:if test="${DtimeReserverSyncForm.map.term=='2'}">selected</c:if> value="2">第2學期</option>
				</select>
				</td>
			</tr>
		</table>
		
		<table class="hairLineTable">
			<tr>
				<td class="hairLineTdF">執行範圍</td>
				<td class="hairLineTdF">
				<input type="text" id="classNo" name="depart_class" style="font-size:18;"
				 size="8" autocomplete="off" style="ime-mode:disabled" autocomplete="off" value="${DtimeReserverSyncForm.map.depart_class}" 
		 		 onkeyup="if(this.value.length>2)getAny(this.value, 'classNo', 'className', 'Class', 'no')"
		 		 onclick="this.value='', document.getElementById('className').value=''"/>
		 		 <input type="text" name="ClassName" value="${DtimeReserverSyncForm.map.ClassName}" style="font-size:18;"
				 id="className" onclick="this.value='', document.getElementById('classNo').value=''"/>
		 		 </td>
		 		 <td width="30" align="center" class="hairlineTdF">
		 		<img src="images/icon/icon_info.gif" id="ch" onMouseOver="showHelpMessage('輸入班級, 系統會根據項目找尋', 'inline', this.id)" 
				 onMouseOut="showHelpMessage('', 'none', this.id)"/>
				</td>
			</tr>
		</table>
		
		</td>
	</tr>
</table>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">
		<OL>
		
			<li>學年學期
				<ul>
				<li>輸入指定開課學年
				<li>輸入指定開課學期
				<li>請注意跨年的學年與學期
			</ul>	
			
			<li>班級範圍				
			<ul>
				<li>輸入執行的班級範圍
				<li>不輸入代表全校課程同時執行
			</ul>
			
			<li>執行時機
			<ul>
				<li>指定學年學期課程規劃確定完成
				<li>可重複執行，但排課或選課將回復成課程規劃的初始狀態
				<li>執行前可備份課程主檔、排課、選課規則和多教師
				<li>(Dtime, Dtime_class, Opencs, Dtime_teacher)
			</ul>
		</OL>
		
		</td>
	</tr>
</table>
