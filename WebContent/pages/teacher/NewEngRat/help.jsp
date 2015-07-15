<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">
			<table >
				<tr>
					
					<td width="100%" class="gray_15">
					<INPUT type="button" value="查看說明" class="gCancel" onClick="showObj('help')">

					<table id="help" style="display:none" width="100%">
						<tr>
							<td >
							<!-- bean:message key="EngRat.help" bundle="COU"/-->
							<table width="100%">
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td class="gray_15">平時考共6個欄位, 輸入任一欄立即自動運算<b>平時成績</b>，佔總成績30%。如果不滿意自動運算的結果，或是您已經完成計算，可以跳過運算過程直接輸入<b>平時成績</b>欄位。</td></tr>
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td class="gray_15">補救教學佔<b>平時成績</b> 30%, 倘若沒有補救教學請留空白, 則不會進行計算。</td></tr>
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td class="gray_15">期中考佔總成績 30%。</td></tr>
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td class="gray_15">期末考佔總成績 40%。</td></tr>
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td class="gray_15">英檢成績佔<b>期末成績</b> 50%, 倘若沒有英檢成績請留空白, 則不會進行計算。</td></tr>
							<tr><td valign="top"><img src="images/icon/icon_info_exclamation.gif" /></td><td class="gray_15"><b>如果不滿意以上自動運算的結果，或是您已經完成計算，<b>您可以跳過所有運算過程直接輸入總成績欄位。</b></td></tr>
							<tr><td valign="top"><img src="images/icon/exclamation.gif" /></td><td class="gray_15">活動 欄位的輸入的分數將會直接影響 總成績，請在總成績完成後進行。</td></tr>
							<tr><td valign="top"><img src="images/icon/exclamation.gif" /></td><td class="gray_15"><b>全班的期末考成績欄位輸入完成後，將會自動在一般成績系統上註明「成績已上傳」。</b></td></tr>
							<tr><td valign="top"><img src="images/icon/exclamation.gif" /></td><td class="gray_15"><b>英文成績系統與一般成績系統計算方式完全不同，使用英文成績系統儲存之後請不要重複執行一般成績系統。</b></td></tr>
							
							</table>
							</td>
						</tr>
					</table>

					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<c:if test="${empty engRats}">
<table class="hairLineTable" width="99%">
	<tr>
		<td class="hairLineTdF">
		
		<table>
			<tr>
				<td>&nbsp;&nbsp;<img src="images/action_stop.gif"></td>
				<td class="gray_15">您未教授英語課程</td>
			</tr>
		</table>					
		
		</td>
	</tr>
</table>
</c:if>